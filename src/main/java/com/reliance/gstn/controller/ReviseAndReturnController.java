/**
 * 
 */
package com.reliance.gstn.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.admin.service.HSNService;
import com.reliance.gstn.model.CompleteInvoice;
import com.reliance.gstn.model.CustomerDetails;
import com.reliance.gstn.model.InventoryProductSave;
import com.reliance.gstn.model.InventoryProductTable;
import com.reliance.gstn.model.InvoiceAdditionalChargeDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.InvoiceServiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.ManageServiceCatalogue;
import com.reliance.gstn.model.Product;
import com.reliance.gstn.model.ReviseAndReturn;
import com.reliance.gstn.model.ReviseAndReturnHistory;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.GSTINDetailsService;
import com.reliance.gstn.service.GenerateInvoiceService;
import com.reliance.gstn.service.GenericService;
import com.reliance.gstn.service.InventoryService;
import com.reliance.gstn.service.ManageOffersService;
import com.reliance.gstn.service.ManageServiceCatalogueService;
import com.reliance.gstn.service.ProductService;
import com.reliance.gstn.service.ReviseAndReturnService;
import com.reliance.gstn.service.SACService;
import com.reliance.gstn.service.StateService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.AmountInWordsModified;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Nikesh.Bansod
 *
 */
@Controller
public class ReviseAndReturnController {
	
	private static final Logger logger = Logger.getLogger(ReviseAndReturnController.class);
	
	
	@Autowired
	ReviseAndReturnService reviseAndReturnService;
	
	@Autowired
	GenerateInvoiceService generateInvoiceService;
	
	@Autowired
	TaxCalculationController taxCalculationController;
	
	@Value(value = "${get_prim_id_list_query}")
	private String getPrimIdsListQuery;
	
	@Autowired
	private UserMasterService userMasterService;
	
	@Autowired
	private GenericService genericService;
	
	@Autowired
	public StateService stateService;
	
	@Autowired
	private GSTINDetailsService gstinDetailsService;
	
	@Autowired
	public ProductService productService;
	
	@Autowired
	public ManageServiceCatalogueService manageServiceCatalogueService;
	
	@Autowired
	public ManageOffersService manageOffersService;
	
	@Autowired
	public SACService sacService;
	
	@Autowired
	public HSNService hsnService;
	
	@Value("${sgst_ratio}")
	private String sgstOrUgstRatio;
	
	@Value("${cgst_ratio}")
	private String cgstRatio;
	
	@Value("${diff_percentage_ratio}")
	private String diffPercentageRatio;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	InventoryController inventoryController;
	
	@Autowired
	private GenerateInvoiceController generateInvoiceController;
	
	@RequestMapping(value="rrList",method=RequestMethod.POST)
	public @ResponseBody String getRRList(){
		logger.info("Entry");
		List<ReviseAndReturn> rrList = new ArrayList<ReviseAndReturn>();
		try {
			rrList = reviseAndReturnService.listRR();
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(rrList);
	}
	
	@RequestMapping(value = {"/getRevAndRet"}, method = RequestMethod.POST)
	public String getRevAndRet(@RequestParam Integer id, Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.GET_REVISE_AND_RETURN_PAGE;
		model.addAttribute("refToFind", id);
		model.addAttribute("backRRType", "");
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value = {"/generateRevisedInvoiceFromRR","/generateNewInvoiceFromRR"}, method = RequestMethod.POST)
	public @ResponseBody String generateInvoiceFromRR(@RequestBody InvoiceDetails invoiceDetails, HttpServletRequest httpRequest) {
		logger.info("Entry");
		CompleteInvoice completeInvoice = new CompleteInvoice();
		String renderData = GSTNConstants.NOT_ALLOWED_ACCESS;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
		CompleteInvoice completeInv = new CompleteInvoice();
		Map<String,Object> mapResponse = new HashMap<>();
		try {
			boolean isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(invoiceDetails.getId(),loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				renderData = invoiceServerSideValidationAsPerRR(invoiceDetails, httpRequest);
				completeInvoice.setRenderData(renderData);
				logger.info("ACCESS : "+renderData);
				if(renderData.equals(GSTNConstants.ALLOWED_ACCESS)){
					InvoiceDetails newlyCreatedInvoice = new InvoiceDetails();
					mapResponse = setDataInNewInvoice(invoiceDetails,httpRequest,loginMaster);
					newlyCreatedInvoice = (InvoiceDetails)mapResponse.get("INVOICE");
					//newlyCreatedInvoice = setDataInNewInvoice(invoiceDetails,httpRequest);
					completeInv = callGenericInvoiceTaxCalulation(newlyCreatedInvoice,httpRequest);
					
					
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:",e);
		}
		completeInvoice.setRenderData(renderData);
		logger.info("Exit");
		return new Gson().toJson(completeInv);
	}	
	
	private CompleteInvoice callGenericInvoiceTaxCalulation(InvoiceDetails invoiceDetails,HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		String amtInWords = null;

		CompleteInvoice completeInvoice = new CompleteInvoice();
		//String renderData = GSTNConstants.NOT_ALLOWED_ACCESS;
		
			try {
					invoiceDetails = calculationOnInvoiceDetailsOnRR(invoiceDetails);
					if(invoiceDetails.getInvoiceValue() > 1000000000000000D){
						System.out.println("Invoice value greater than 15 digits");
						completeInvoice.setRenderData(GSTNConstants.TOO_LONG_INVOICE_VALUE);
					}else{
						amtInWords = AmountInWordsModified.convertNumberToWords(""+invoiceDetails.getInvoiceValueAfterRoundOff());	
						
						//get user details - Start
							LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
							UserMaster user = userMasterService.getUserMasterById(loginMaster.getuId());
							if(!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
								user = userMasterService.getUserMasterById(user.getReferenceId());
							}
						//get user details - End
							
						//set value in CompleteInvoice - Start	
							completeInvoice.setInvoiceDetails(invoiceDetails);
							completeInvoice.setAmtInWords(amtInWords);
							completeInvoice.setCustomerStateCode(stateService.getStateByStateName(invoiceDetails.getCustomerDetails().getCustState()).getStateCode());
							completeInvoice.setGstinDetails(gstinDetailsService.getGstinDetailsFromGstinNo(invoiceDetails.getGstnStateIdInString(),loginMaster.getPrimaryUserUId()));
							completeInvoice.setGstMap(GSTNUtil.convertListToMap(invoiceDetails.getServiceList()));
							//completeInvoice.setRenderData(GSTNConstants.ALLOWED_ACCESS);
							//completeInvoice.setUser(user);
					   //set value in CompleteInvoice - End
					}
				
				
			} catch (Exception e) {
				logger.error("Error in:",e);
			}
		
		
		logger.info("Exit");
		return completeInvoice;
		
	}

	private InvoiceDetails calculationOnInvoiceDetailsOnRR(InvoiceDetails invoiceDetails) {
		logger.info("Entry");
		double additionalChargesValue = 0;
		//double preAmountAfterDiscount = 0;
		double amountAfterDiscount = 0;
		double totalTax = 0;
		double invoiceValue = 0;
		double invoiceValueAfterRoundOff = 0;
		//double otherCharges = 0;
		double totalDiscounts = 0;
		try {
			
			//calculate total additional charge value - Start
			if((invoiceDetails.getAddChargesList() != null) && (invoiceDetails.getAddChargesList().size() > 0)){
				for(InvoiceAdditionalChargeDetails aa : invoiceDetails.getAddChargesList()){
					additionalChargesValue = additionalChargesValue + aa.getAdditionalChargeAmount();
				}
			}
			//calculate total additional charge value - End
			
			//set value for gstnStateId, DeliveryStateId, typeOfExport - Start 
			for(InvoiceServiceDetails bb : invoiceDetails.getServiceList()){
				totalDiscounts = totalDiscounts + bb.getOfferAmount();
				
				if(bb.getBillingFor().equals(GSTNConstants.PRODUCT)){
					bb = calculateTaxForProductonRR(bb);
				}else{
					bb = calculateTaxForServiceonRR(bb);
				}
			}
			//set value for gstnStateId, DeliveryStateId, typeOfExport - End 
			
			
			//set value for total amountAfterDiscount and total tax - Start
			for(InvoiceServiceDetails cc : invoiceDetails.getServiceList()){
				amountAfterDiscount = amountAfterDiscount + (cc.getPreviousAmount() + cc.getAdditionalAmount() - cc.getOfferAmount());
				totalTax = totalTax + cc.getTaxAmount();
			}
			
			//set value for total amountAfterDiscount and total tax - End
		
			//set value for Final Invoice Value - Start
				invoiceValue = amountAfterDiscount + totalTax;
				invoiceValueAfterRoundOff = Math.round(invoiceValue);
				
				
				invoiceDetails.setAmountAfterDiscount(GSTNUtil.convertToDouble(amountAfterDiscount));
				invoiceDetails.setTotalTax(GSTNUtil.convertToDouble(totalTax));
				invoiceDetails.setInvoiceValue(GSTNUtil.convertToDouble(invoiceValue));
				invoiceDetails.setInvoiceValueAfterRoundOff(invoiceValueAfterRoundOff);
				invoiceDetails.setOtherCharges(additionalChargesValue - totalDiscounts);
				//set value for Final Invoice Value - End
			
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		
		return invoiceDetails;
	}

	private String invoiceServerSideValidationAsPerRR(InvoiceDetails invoiceDetails, HttpServletRequest httpRequest) throws Exception {
		logger.info("Entry");
		String renderData = GSTNConstants.NOT_ALLOWED_ACCESS;
		boolean isAllowed = true;
		boolean isServiceOrProductValid = false;
		
		//A)check if serviceId belongs to this invoice - Start
		for(InvoiceServiceDetails aa : invoiceDetails.getServiceList()){
			
			// if isServiceOrProductValid is false it means data is not manipulated at client side - allow it to go for normal flow
			if(isAllowed){
				isServiceOrProductValid = checkClientValuesWithServerValuesInRR(aa, httpRequest);
				logger.info("Service/Product Response : "+aa.getBillingFor()+", id : "+aa.getServiceId() + ",isServiceOrProductValid : "+isServiceOrProductValid);
				if(!isServiceOrProductValid){
					isAllowed = false;
					break;
				}
				
			}
		}
		//A)check if serviceId belongs to this invoice - End
		
		//1) rrType= salesReturn
		//B)return quntity should not be zero
		//C)return quantity should not be more than invoice quantity		
		if(invoiceDetails.getRrTypeForCreation().equals(GSTNConstants.RR_SALES_RETURN )){
			boolean secondTest = true;
			InvoiceDetails previousInvoiceDetails = generateInvoiceService.getInvoiceDetailsById(invoiceDetails.getId());
			if(isAllowed){
				for(InvoiceServiceDetails aa : invoiceDetails.getServiceList()){
					for(InvoiceServiceDetails bb : previousInvoiceDetails.getServiceList()){
						if(aa.getServiceId().equals(bb.getServiceId())){
							if(aa.getQuantity() == 0 || (aa.getQuantity() > bb.getQuantity())){
								secondTest = false;
							}
						}
					}
				}
			}
			
			if(isAllowed && secondTest){
				logger.info("ACCESS ALLOWED");
				renderData = GSTNConstants.ALLOWED_ACCESS;
			}
		}
		
		//2) rrType= discountChange
		//A)check if serviceId belongs to this invoice 
		//B)old discount and new discount should not be same
		if(invoiceDetails.getRrTypeForCreation().equals(GSTNConstants.RR_DISCOUNT_CHANGE )){
			boolean secondTest = true;
			InvoiceDetails previousInvoiceDetails = generateInvoiceService.getInvoiceDetailsById(invoiceDetails.getId());
			if(isAllowed){
				for(InvoiceServiceDetails aa : invoiceDetails.getServiceList()){
					if(aa.getIsNew().equals("Y")){
						for(InvoiceServiceDetails bb : previousInvoiceDetails.getServiceList()){
							if(aa.getServiceId().equals(bb.getServiceId())){
								if(aa.getDiscountTypeInItem().equals("Percentage")){
									double dbOfferAmount = bb.getOfferAmount();
									double newOfferAmountInPercentage = aa.getOfferAmount();
									double newOfferAmount = ((bb.getPreviousAmount() * newOfferAmountInPercentage)/100);
									double dbOfferAmountPrecision = Double.valueOf(new DecimalFormat("#.##").format(dbOfferAmount));
									double newOfferAmountPrecision = Double.valueOf(new DecimalFormat("#.##").format(newOfferAmount));
									if(dbOfferAmountPrecision == newOfferAmountPrecision){
										secondTest = false;
										break;
									}
								}else{
									double dbOfferAmount = bb.getOfferAmount();
									double newOfferAmount = aa.getOfferAmount();
									if(dbOfferAmount == newOfferAmount){
										secondTest = false;
										break;
									}
								}
							}
						}
					}
				}
			}
			
			if(isAllowed && secondTest){
				logger.info("ACCESS ALLOWED");
				renderData = GSTNConstants.ALLOWED_ACCESS;
			}
		}
		
		//3) rrType= salesPriceChange
		//A)check if serviceId belongs to this invoice 
		//B)old sellingPrice and new sellingPrice should not be same
		if(invoiceDetails.getRrTypeForCreation().equals(GSTNConstants.RR_SALES_PRISE_CHANGE )){
			boolean secondTest = true;
			InvoiceDetails previousInvoiceDetails = generateInvoiceService.getInvoiceDetailsById(invoiceDetails.getId());
			if(isAllowed){
				for(InvoiceServiceDetails aa : invoiceDetails.getServiceList()){
					if(aa.getIsNew().equals("Y")){
						for(InvoiceServiceDetails bb : previousInvoiceDetails.getServiceList()){
							if(aa.getServiceId().equals(bb.getServiceId())){
								if(aa.getRate() == bb.getRate()){
										secondTest = false;
										break;
								}
								
							}
						}
					}
				}
			}
			
			if(isAllowed && secondTest){
				logger.info("ACCESS ALLOWED");
				renderData = GSTNConstants.ALLOWED_ACCESS;
			}
		}
		
		//4) rrType= rateChange
		//A)check if serviceId belongs to this invoice 
		//B)old rateChange and new rateChange should not be same
		if(invoiceDetails.getRrTypeForCreation().equals(GSTNConstants.RR_RATE_CHANGE )){
			boolean secondTest = true;
			InvoiceDetails previousInvoiceDetails = generateInvoiceService.getInvoiceDetailsById(invoiceDetails.getId());
			if(isAllowed){
				for(InvoiceServiceDetails aa : invoiceDetails.getServiceList()){
					if(aa.getIsNew().equals("Y")){
						for(InvoiceServiceDetails bb : previousInvoiceDetails.getServiceList()){
							if(aa.getServiceId().equals(bb.getServiceId())){
								double dbTaxRate = bb.getSgstPercentage() + bb.getUgstPercentage() + bb.getCgstPercentage() + bb.getIgstPercentage();
								if(aa.getRate() == dbTaxRate){
										secondTest = false;
										break;
								}
								
							}
						}
					}
				}
			}
			
			if(isAllowed && secondTest){
				logger.info("ACCESS ALLOWED");
				renderData = GSTNConstants.ALLOWED_ACCESS;
			}
		}
		
		//5) rrType= quantityChange
		//A)check if serviceId belongs to this invoice 
		//B)old quantity and new quantity should not be same
		if(invoiceDetails.getRrTypeForCreation().equals(GSTNConstants.RR_QUANTITY_CHANGE)){
			boolean secondTest = true;
			InvoiceDetails previousInvoiceDetails = generateInvoiceService.getInvoiceDetailsById(invoiceDetails.getId());
			if(isAllowed){
				for(InvoiceServiceDetails aa : invoiceDetails.getServiceList()){
					if(aa.getIsNew().equals("Y")){
						for(InvoiceServiceDetails bb : previousInvoiceDetails.getServiceList()){
							if(aa.getServiceId().equals(bb.getServiceId())){
								if(aa.getQuantity() == bb.getQuantity()){
										secondTest = false;
										break;
								}
								
							}
						}
					}
				}
			}
			
			if(isAllowed && secondTest){
				logger.info("ACCESS ALLOWED");
				renderData = GSTNConstants.ALLOWED_ACCESS;
			}
		}
		
		//6) rrType= partyChange
		//A))old customer and new customer should not be same
		if(invoiceDetails.getRrTypeForCreation().equals(GSTNConstants.RR_PARTY_CHANGE)){
			boolean secondTest = true;
			InvoiceDetails previousInvoiceDetails = generateInvoiceService.getInvoiceDetailsById(invoiceDetails.getId());
			if(isAllowed){
				if(invoiceDetails.getCustomerDetails() != null){
					if(previousInvoiceDetails.getCustomerDetails().getId().equals(invoiceDetails.getCustomerDetails().getId())){
						if(previousInvoiceDetails.getBillToShipIsChecked().equals("Yes")){
							if(invoiceDetails.getBillToShipIsChecked().equals("Yes")){
								secondTest = false;
							}
						}
					}
				}else{
					secondTest = false;
				}
			}
			
			if(isAllowed && secondTest){
				logger.info("ACCESS ALLOWED");
				renderData = GSTNConstants.ALLOWED_ACCESS;
			}
		}
		
		//7) rrType= itemChange
		//A))add or remove new items
		if(invoiceDetails.getRrTypeForCreation().equals(GSTNConstants.RR_ITEM_CHANGE)){
			boolean secondTest = false;
			InvoiceDetails previousInvoiceDetails = generateInvoiceService.getInvoiceDetailsById(invoiceDetails.getId());
			Set<Integer> previousServiceIdList = new HashSet<>();
			for(InvoiceServiceDetails bb : previousInvoiceDetails.getServiceList()){
				previousServiceIdList.add(bb.getServiceId());
			}
			
			if(invoiceDetails.getServiceList()!= null && (invoiceDetails.getServiceList().size() == previousInvoiceDetails.getServiceList().size())){
				for(InvoiceServiceDetails aa : invoiceDetails.getServiceList()){
					if(!previousServiceIdList.contains(aa.getServiceId())){
						secondTest = true;
					}
				}
			}else if(invoiceDetails.getServiceList()!= null && (invoiceDetails.getServiceList().size() != previousInvoiceDetails.getServiceList().size())){
				secondTest = true;
			}
			
			if(secondTest){
				logger.info("ACCESS ALLOWED");
				renderData = GSTNConstants.ALLOWED_ACCESS;
			}
		}
		
		
		//    itemChange partyChange multipleChanges 
		logger.info("Entry");
		return renderData;
	}

	private boolean checkClientValuesWithServerValuesInRR(InvoiceServiceDetails serviceOrProduct, HttpServletRequest httpRequest) throws Exception {
		logger.info("Entry");
		logger.info("checkClientValuesWithServerValuesInRR InvoiceServiceDetails : Service Id - "+serviceOrProduct.getServiceId());
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		boolean isMappingValid = false;
		String idsValuesToFetch = null;
		boolean checkSecond = false;
	
			//checkSecond - check if service or product belongs to current organization - Start
			idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			logger.info("idsValuesToFetch - "+idsValuesToFetch);
			if(serviceOrProduct.getBillingFor().equals(GSTNConstants.PRODUCT)){
				isMappingValid = genericService.checkUserMappingValidation(idsValuesToFetch,getPrimIdsListQuery,serviceOrProduct.getServiceId(),"Product","referenceId");
				if(isMappingValid){
					logger.info("Product id - "+serviceOrProduct.getServiceId()+" belongs to current organization. Hence valid.");
					checkSecond = true;
				}
				
			}else{
				isMappingValid = genericService.checkUserMappingValidation(idsValuesToFetch,getPrimIdsListQuery,serviceOrProduct.getServiceId(),"ManageServiceCatalogue","referenceId");
				if(isMappingValid){
					logger.info("Service id - "+serviceOrProduct.getServiceId()+" belongs to current organization. Hence valid.");
					checkSecond = true;
				}
			}
			
			//checkSecond - check if service or product belongs to current organization - End
			
		logger.info("Exit");
		return checkSecond;
	}

	private Map<String,Object> setDataInNewInvoice(InvoiceDetails newInvoiceDetails,HttpServletRequest httpRequest,LoginMaster loginMaster ) {
		
		Map<String,Object> mapResponse = new HashMap<>();
		InvoiceDetails oldInvoiceData = null;
		String rrType = null;
		boolean isInventoryUpdate = false;
		String creditOrDebit = "DEBIT";
		List<Product>creditProductList = new ArrayList<>();
		List<Product> debitProductList = new ArrayList<>();
		List<Integer>removedServiceItemsIds = new ArrayList<>();
		Product product = null;
		int iterationNo = 0;
		String inventory = null;
		try {
			oldInvoiceData = generateInvoiceService.getInvoiceDetailsById(newInvoiceDetails.getId());
			iterationNo = oldInvoiceData.getIterationNo();
			rrType = newInvoiceDetails.getRrTypeForCreation();
			oldInvoiceData.setModeOfCreation(newInvoiceDetails.getModeOfCreation());
			oldInvoiceData.setRrTypeForCreation(newInvoiceDetails.getRrTypeForCreation());
			oldInvoiceData.setCreateDocType(newInvoiceDetails.getCreateDocType());
			oldInvoiceData.setIterationNo((int)(oldInvoiceData.getIterationNo() + 1));
			oldInvoiceData.setUpdatedBy(loginMaster.getuId()+"");
			oldInvoiceData.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			if(rrType.equals(GSTNConstants.RR_SALES_RETURN )){
				isInventoryUpdate = true;
				creditOrDebit = "CREDIT";
				inventory = GSTNConstants.RR_SALES_RETURN;
				creditProductList = new ArrayList<>();
				//List<InvoiceServiceDetails> newServiceList = new ArrayList<InvoiceServiceDetails>();
				for(InvoiceServiceDetails newInvItem : newInvoiceDetails.getServiceList()){
					for(InvoiceServiceDetails oldInvItem :  oldInvoiceData.getServiceList()){
						if(newInvItem.getId().equals(oldInvItem.getId())){
							double oldQty = oldInvItem.getQuantity();
							double newQty = newInvItem.getQuantity();
							product = new Product(); 
							if(oldQty != newQty){
								product.setId(oldInvItem.getServiceId());
								product.setCurrentStock(oldQty - newQty);
								product.setTempCreditOrDebit("CREDIT");
								creditProductList.add(product);							
							}
							double oldPreviousAmount = oldInvItem.getPreviousAmount();
							double newPreviousAmount = oldInvItem.getRate() * newQty;
							double newOfferAmount = 0;
							oldInvItem.setQuantity(newQty);
							oldInvItem.setPreviousAmount(newPreviousAmount);
							if(oldInvItem.getOfferAmount() != 0){
								if(oldInvItem.getDiscountTypeInItem().equals("Percentage")){
									double oldOfferAmountInPercenatge = ((oldInvItem.getOfferAmount() * 100)/oldPreviousAmount);
									newOfferAmount = ((oldOfferAmountInPercenatge * newPreviousAmount)/100);
								}else{
									newOfferAmount = oldInvItem.getOfferAmount();	
								}
								oldInvItem.setOfferAmount(Double.valueOf(new DecimalFormat("#.##").format(newOfferAmount)));
							}
							//oldInvItem.setIterationNo((int)(oldInvoiceData.getIterationNo() + 1));
							//newServiceList.add(oldInvItem);
							break;
						}
					}
				}
				
				
			}else if(rrType.equals(GSTNConstants.RR_DISCOUNT_CHANGE )){
				isInventoryUpdate = false;
				inventory = GSTNConstants.RR_DISCOUNT_CHANGE;
				
				for(InvoiceServiceDetails newInvItem : newInvoiceDetails.getServiceList()){
					if(newInvItem.getIsNew().equals("Y")){
						for(InvoiceServiceDetails oldInvItem :  oldInvoiceData.getServiceList()){
							if(newInvItem.getId().equals(oldInvItem.getId())){
								//oldInvItem.setIterationNo((int)(oldInvoiceData.getIterationNo() + 1));
								double oldPreviousAmount = oldInvItem.getPreviousAmount();
								if(oldInvItem.getDiscountTypeInItem().equals("Percentage")){
									double newOfferAmount = ((newInvItem.getOfferAmount() * oldPreviousAmount)/100);
									oldInvItem.setOfferAmount(newOfferAmount);
								}else{
									oldInvItem.setOfferAmount(newInvItem.getOfferAmount());
								}
								
								break;
							}
						}
					}
					
				}	
				
			}else if(rrType.equals(GSTNConstants.RR_SALES_PRISE_CHANGE )){
				isInventoryUpdate = false;
				inventory = GSTNConstants.RR_SALES_PRISE_CHANGE;
				for(InvoiceServiceDetails newInvItem : newInvoiceDetails.getServiceList()){
					if(newInvItem.getIsNew().equals("Y")){
						for(InvoiceServiceDetails oldInvItem :  oldInvoiceData.getServiceList()){
							if(newInvItem.getId().equals(oldInvItem.getId())){
								//oldInvItem.setIterationNo((int)(oldInvoiceData.getIterationNo() + 1));
								oldInvItem.setRate(newInvItem.getRate());
								oldInvItem.setOfferAmount(0);
								oldInvItem.setPreviousAmount(oldInvItem.getQuantity() * newInvItem.getRate());
								break;
							}
						}
					}
					
				}
				
			}else if(rrType.equals(GSTNConstants.RR_RATE_CHANGE)){
				isInventoryUpdate = false;
				inventory = GSTNConstants.RR_RATE_CHANGE;
				for(InvoiceServiceDetails newInvItem : newInvoiceDetails.getServiceList()){
					if(newInvItem.getIsNew().equals("Y")){
						for(InvoiceServiceDetails oldInvItem :  oldInvoiceData.getServiceList()){
							if(newInvItem.getId().equals(oldInvItem.getId())){
								
								//oldInvItem.setIterationNo((int)(oldInvoiceData.getIterationNo() + 1));
								oldInvItem.setIsTaxRateChange("Y");
								oldInvItem.setNewTaxRate(newInvItem.getRate());
								break;
							}
						}
					}
					
				}
				
			}else if(rrType.equals(GSTNConstants.RR_QUANTITY_CHANGE )){
				isInventoryUpdate = true;
				creditOrDebit = "CREDIT";
				inventory = GSTNConstants.RR_QUANTITY_CHANGE;
				creditProductList = new ArrayList<>();
				debitProductList = new ArrayList<>();
				for(InvoiceServiceDetails newInvItem : newInvoiceDetails.getServiceList()){
					if(newInvItem.getIsNew().equals("Y")){
						for(InvoiceServiceDetails oldInvItem :  oldInvoiceData.getServiceList()){
							if(newInvItem.getId().equals(oldInvItem.getId())){
								double oldQty = oldInvItem.getQuantity();
								double newQty = newInvItem.getQuantity();
								double newQtyForInventory = 0;
								product = new Product(); 
								if(oldQty != newQty){
									String creditOrDebitQty = "DEBIT";
									if(oldQty > newQty){
										creditOrDebitQty = "CREDIT";
										newQtyForInventory = oldQty - newQty;
									}else{
										creditOrDebitQty = "DEBIT";
										newQtyForInventory = newQty - oldQty;
									}
									product.setId(oldInvItem.getServiceId());
									product.setCurrentStock(newQtyForInventory);
									product.setTempCreditOrDebit(creditOrDebitQty);
									if(creditOrDebitQty.equals("CREDIT")){
										creditProductList.add(product);	
									}else{
										debitProductList.add(product);	
									}
															
								}
								double oldPreviousAmount = oldInvItem.getPreviousAmount();
								double newPreviousAmount = oldInvItem.getRate() * newQty;
								double newOfferAmount = 0;
								oldInvItem.setQuantity(newQty);
								oldInvItem.setPreviousAmount(newPreviousAmount);
								if(oldInvItem.getOfferAmount() != 0){
									if(oldInvItem.getDiscountTypeInItem().equals("Percentage")){
										double oldOfferAmountInPercenatge = ((oldInvItem.getOfferAmount() * 100)/oldPreviousAmount);
										newOfferAmount = ((oldOfferAmountInPercenatge * newPreviousAmount)/100);
									}else{
										newOfferAmount = oldInvItem.getOfferAmount();	
									}
									oldInvItem.setOfferAmount(Double.valueOf(new DecimalFormat("#.##").format(newOfferAmount)));
								}
								//oldInvItem.setIterationNo((int)(oldInvoiceData.getIterationNo() + 1));
								break;
							}
						}
					}
					
				}
				
			}else if(rrType.equals(GSTNConstants.RR_ITEM_CHANGE)){
				isInventoryUpdate = true;
				inventory = GSTNConstants.RR_ITEM_CHANGE;
				creditProductList = new ArrayList<>();
				debitProductList = new ArrayList<>();
				List<InvoiceServiceDetails> newDnyamicServiceList = new ArrayList<>();
				List<Integer>newDnyamicServiceIds = new ArrayList<>();
				removedServiceItemsIds = new ArrayList<>();
				for(InvoiceServiceDetails newInvItem : newInvoiceDetails.getServiceList()){
					if(newInvItem.getIsNew().equals("N")){
						for(InvoiceServiceDetails oldInvItem :  oldInvoiceData.getServiceList()){
							if(newInvItem.getServiceId().equals(oldInvItem.getServiceId())){
								newDnyamicServiceList.add(oldInvItem);
								newDnyamicServiceIds.add(oldInvItem.getServiceId());
							}
						}
					}
					
					if(newInvItem.getIsNew().equals("Y")){
						newInvItem.setDeliveryStateId(oldInvoiceData.getDeliveryPlace());
						newInvItem.setGstnStateId(oldInvoiceData.getGstnStateId());
						double offerAmount = 0;
						if(newInvItem.getOfferAmount() != 0){
							if(newInvItem.getDiscountTypeInItem().equals("Percentage")){
							    offerAmount = ((newInvItem.getPreviousAmount() * newInvItem.getOfferAmount())/100);
							}else{
							    offerAmount = newInvItem.getOfferAmount();	
							}
						}
						newInvItem.setOfferAmount(offerAmount);
						newDnyamicServiceList.add(newInvItem);
						newDnyamicServiceIds.add(newInvItem.getServiceId());
						if(newInvItem.getBillingFor().equals("Product")){
							product = new Product(); 
							product.setId(newInvItem.getServiceId());
							product.setCurrentStock(newInvItem.getQuantity());
							product.setTempCreditOrDebit( "DEBIT");
							debitProductList.add(product);
						}
					}
				}
				
				for(InvoiceServiceDetails oldInvItem :  oldInvoiceData.getServiceList()){
					if(!newDnyamicServiceIds.contains(oldInvItem.getServiceId())){
						removedServiceItemsIds.add(oldInvItem.getId());
						if(oldInvItem.getBillingFor().equals("Product")){
							product = new Product(); 
							product.setId(oldInvItem.getServiceId());
							product.setCurrentStock(oldInvItem.getQuantity());
							product.setTempCreditOrDebit( "CREDIT");
							creditProductList.add(product);
						}
					}
				}
				
				oldInvoiceData.setServiceList(null);
				oldInvoiceData.setServiceList(newDnyamicServiceList);
					
				
			}else if(rrType.equals(GSTNConstants.RR_PARTY_CHANGE )){
				isInventoryUpdate = false;
				inventory = GSTNConstants.RR_PARTY_CHANGE;
				
				CustomerDetails newCustomer = newInvoiceDetails.getCustomerDetails();
				oldInvoiceData.setCustomerDetails(newCustomer);
				oldInvoiceData.setDeliveryPlace(newInvoiceDetails.getDeliveryPlace());
				oldInvoiceData.setBillToShipIsChecked(newInvoiceDetails.getBillToShipIsChecked());
				oldInvoiceData.setShipToCustomerName(newInvoiceDetails.getShipToCustomerName());
				oldInvoiceData.setShipToAddress(newInvoiceDetails.getShipToAddress());
				oldInvoiceData.setShipToCity(newInvoiceDetails.getShipToCity());
				oldInvoiceData.setShipToPincode(newInvoiceDetails.getShipToPincode());
				oldInvoiceData.setShipToState(newInvoiceDetails.getShipToState());
				oldInvoiceData.setShipToStateCode(newInvoiceDetails.getShipToStateCode());
				oldInvoiceData.setShipToStateCodeId(newInvoiceDetails.getShipToStateCodeId());
				
				for(InvoiceServiceDetails oldInvItem :  oldInvoiceData.getServiceList()){
					oldInvItem.setDeliveryStateId(newInvoiceDetails.getDeliveryPlace());
				}
				
			}else if(rrType.equals(GSTNConstants.RR_MULTIPLE_CHANGE )){
				
			}
			
			
			//set iteration no in additional charges table - Start
			for(InvoiceServiceDetails oldInvItem :  oldInvoiceData.getServiceList()){
				oldInvItem.setIterationNo(iterationNo + 1);
			}
			
			for(InvoiceAdditionalChargeDetails oldInvItem :  oldInvoiceData.getAddChargesList()){
				oldInvItem.setIterationNo(iterationNo + 1);
			}
			//set iteration no in additional charges table - End
			
			oldInvoiceData.setInvoiceDate(GSTNUtil.convertStringInddMMyyyyToTimestamp(newInvoiceDetails.getInvoiceDateInString()));
			oldInvoiceData.setInvoiceDateInString(newInvoiceDetails.getInvoiceDateInString());
			oldInvoiceData.setInvoiceNumber(newInvoiceDetails.getInvoiceNumber());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mapResponse.put("INVOICE",oldInvoiceData);
		mapResponse.put("INVENTORY_UPDATE",isInventoryUpdate);
		mapResponse.put("CREDIT_PRODUCT_LIST",creditProductList);
		mapResponse.put("DEBIT_PRODUCT_LIST",debitProductList);
		mapResponse.put("CREDIT_DEBIT",creditOrDebit);
		mapResponse.put("PREVIOUS_ITERATION",iterationNo);
		mapResponse.put("INVENTORY_TYPE",inventory);
		mapResponse.put("REMOVE_ITEMS",removedServiceItemsIds);
		return mapResponse;
	}

	public String invoiceServerSideValidation(InvoiceDetails invoiceDetails, HttpServletRequest httpRequest) throws Exception {logger.info("Entry");
	String renderData = GSTNConstants.NOT_ALLOWED_ACCESS;
	
	//First check for services/products if it belongs to current organization or not - Start
	boolean isServiceOrProductValid = false;
	boolean isAllowed = true;
	boolean isAddChargeValid = true;
	boolean isGstnNumberValid = false;
	boolean isCustomerValid = false;
	for(InvoiceServiceDetails aa : invoiceDetails.getServiceList()){
		
		// if isServiceOrProductValid is false it means data is not manipulated at client side - allow it to go for normal flow
		if(isAllowed){
			isServiceOrProductValid = taxCalculationController.checkClientValuesWithServerValues(aa, httpRequest);
			logger.info("Service/Product Response : "+aa.getBillingFor()+", id : "+aa.getServiceId() + ",isServiceOrProductValid : "+isServiceOrProductValid);
			if(!isServiceOrProductValid){
				isAllowed = false;
				break;
			}
			
		}
	}
	
	//First check for services/products if it belongs to current organization or not - End
	
	//Third - check for gstn - Start
		isGstnNumberValid = taxCalculationController.checkGstnNumberWithServerValues(invoiceDetails.getGstnStateIdInString(),httpRequest);
	//Third - check for gstn - End
	
	//Fourth - check for customer - Start
		isCustomerValid = taxCalculationController.checkCustomerDetailsWithServerValues(invoiceDetails.getCustomerDetails(),httpRequest);
	//Fourth - check for customer - End
	
	
	if(isAllowed && isAddChargeValid && isGstnNumberValid && isCustomerValid){
		logger.info("ACCESS ALLOWED");
		renderData = GSTNConstants.ALLOWED_ACCESS;
	}
	
	logger.info("Exit");
	return renderData;
	}
	
	public InvoiceServiceDetails calculateTaxForServiceonRR(InvoiceServiceDetails serviceTax) {
		logger.info("Entry");
		
		try {
			ManageServiceCatalogue service = manageServiceCatalogueService.getManageServiceCatalogueById(serviceTax.getServiceId());
			//ManageOffers offer = manageOffersService.getManageOffersById(serviceTax.getOfferId());
			String stateOrUnionTerritory = stateService.isStateOrUnionTerritory(serviceTax.getGstnStateId());
			double previousAmount = serviceTax.getPreviousAmount();
			double amountAfterDiscount = 0;
			double sgstAmount = 0;
			double ugstAmount = 0;
			double cgstAmount = 0;
			double igstAmount = 0;
			double taxAmount = 0;
			String category = GSTNConstants.CATEGORY_EXPORT_WITH_BOND;
			double sgstPercent = 0;
			double ugstPercent = 0;
			double cgstPercent = 0;
			double igstPercent = 0;
			double additionalAmount = 0;
			double advolCessAmount = 0;
			double totalCessAmount = 0;
			
			//calculate total amount based on offer amount - Start
				double offerAmount = 0;
						
				if(serviceTax.getOfferAmount() != 0){
					 offerAmount = serviceTax.getOfferAmount();
				}
				
				if(serviceTax.getAdditionalAmount() != 0){
					additionalAmount = serviceTax.getAdditionalAmount();			
				}
				amountAfterDiscount = (previousAmount + additionalAmount ) - offerAmount ;
						
			//calculate total amount based on offer amount - End
				if(service != null){
					serviceTax.setHsnSacCode(service.getSacCode());
					serviceTax.setHsnSacDescription(service.getSacDescription());
					if(serviceTax.getIsTaxRateChange()!= null && serviceTax.getIsTaxRateChange().equals("Y")){
						service.setServiceIgst((double)serviceTax.getNewTaxRate());
					}
			
						if(serviceTax.getDeliveryStateId() == serviceTax.getGstnStateId()){
							//get cgst + sgst/ugst
								double sgstOrUgstInRatio = (Double.parseDouble(sgstOrUgstRatio));
								double cgstInRatio = (Double.parseDouble(cgstRatio));
								
								cgstPercent = ((cgstInRatio * service.getServiceIgst()) / 100);
								if(stateOrUnionTerritory.equals(GSTNConstants.STATE)){
									sgstPercent = ((sgstOrUgstInRatio * service.getServiceIgst()) / 100);
									sgstAmount = ((sgstPercent * amountAfterDiscount ) / 100);
									if(serviceTax.getDiffPercent().equals("Y")){
										sgstAmount = ((Double.parseDouble(diffPercentageRatio) * sgstAmount)/100);
									}
									category = GSTNConstants.CATEGORY_WITH_SGST_CSGT;
									
								}else{
									ugstPercent = ((sgstOrUgstInRatio * service.getServiceIgst()) / 100);
									ugstAmount = ((ugstPercent * amountAfterDiscount ) / 100);
									if(serviceTax.getDiffPercent().equals("Y")){
										ugstAmount = ((Double.parseDouble(diffPercentageRatio) * ugstAmount)/100);
									}
									category = GSTNConstants.CATEGORY_WITH_UGST_CGST;
								}
								cgstAmount = ((cgstPercent * amountAfterDiscount ) / 100);
								if(serviceTax.getDiffPercent().equals("Y")){
									cgstAmount = ((Double.parseDouble(diffPercentageRatio) * cgstAmount)/100);
								}
								
								taxAmount = sgstAmount + cgstAmount + ugstAmount;
								
						}else{
							//get igst
							igstAmount = ((service.getServiceIgst() * amountAfterDiscount) / 100);
							if(serviceTax.getDiffPercent().equals("Y")){
								taxAmount = ((Double.parseDouble(diffPercentageRatio) * igstAmount)/100);
							}else{
								taxAmount = igstAmount;
							}
							category = GSTNConstants.CATEGORY_WITH_IGST;
							igstPercent = service.getServiceIgst();
						}
					
				}	
			
			if((serviceTax.getAdvolCess() != 0) || (serviceTax.getNonAdvolCess() != 0)){
				advolCessAmount = ((serviceTax.getAdvolCess() * amountAfterDiscount) / 100); 
				totalCessAmount = advolCessAmount + serviceTax.getNonAdvolCess();
				taxAmount = taxAmount + totalCessAmount;
			}
			
			serviceTax.setPreviousAmount(Double.valueOf(new DecimalFormat("#.##").format(previousAmount)));
			serviceTax.setAmountAfterDiscount(Double.valueOf(new DecimalFormat("#.##").format(amountAfterDiscount)));
			serviceTax.setSgstAmount(Double.valueOf(new DecimalFormat("#.##").format(sgstAmount)));
			serviceTax.setUgstAmount(Double.valueOf(new DecimalFormat("#.##").format(ugstAmount)));
			serviceTax.setCgstAmount(Double.valueOf(new DecimalFormat("#.##").format(cgstAmount)));
			serviceTax.setIgstAmount(Double.valueOf(new DecimalFormat("#.##").format(igstAmount)));
			serviceTax.setTaxAmount(Double.valueOf(new DecimalFormat("#.##").format(taxAmount)));
			serviceTax.setCategoryType(category);
			serviceTax.setSgstPercentage(Double.valueOf(new DecimalFormat("#.##").format(sgstPercent)));
			serviceTax.setCgstPercentage(Double.valueOf(new DecimalFormat("#.##").format(cgstPercent)));
			serviceTax.setUgstPercentage(Double.valueOf(new DecimalFormat("#.##").format(ugstPercent)));
			serviceTax.setIgstPercentage(Double.valueOf(new DecimalFormat("#.##").format(igstPercent)));
			serviceTax.setAmount(Double.valueOf(new DecimalFormat("#.##").format(amountAfterDiscount)));
			serviceTax.setAdvolCessAmount(Double.valueOf(new DecimalFormat("#.##").format(advolCessAmount)));
			serviceTax.setCess(Double.valueOf(new DecimalFormat("#.##").format(totalCessAmount)));
			
		} catch (NumberFormatException e) {
			logger.error("Error in:",e);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return serviceTax;
	}

	public InvoiceServiceDetails calculateTaxForProductonRR(InvoiceServiceDetails serviceTax) {
		logger.info("Entry");
	
		try {
			Product product = productService.getProductById(serviceTax.getServiceId());
			//ManageOffers offer = manageOffersService.getManageOffersById(serviceTax.getOfferId());
			String stateOrUnionTerritory = stateService.isStateOrUnionTerritory(serviceTax.getGstnStateId());
			double previousAmount = serviceTax.getPreviousAmount();
			double amountAfterDiscount = 0;
			double sgstAmount = 0;
			double ugstAmount = 0;
			double cgstAmount = 0;
			double igstAmount = 0;
			double taxAmount = 0;
			String category = GSTNConstants.CATEGORY_EXPORT_WITH_BOND;
			double sgstPercent = 0;
			double ugstPercent = 0;
			double cgstPercent = 0;
			double igstPercent = 0;
			double additionalAmount = 0;
			double advolCessAmount = 0;
			double totalCessAmount = 0;
			
			//calculate total amount based on offer amount - Start
			double offerAmount = 0;
				
			if(serviceTax.getOfferAmount() != 0){
				    offerAmount = serviceTax.getOfferAmount();	
			}
			
			if(serviceTax.getAdditionalAmount() != 0){
				additionalAmount = serviceTax.getAdditionalAmount();			
			}
			amountAfterDiscount = (previousAmount + additionalAmount ) - offerAmount ;
				
			//calculate total amount based on offer amount - End
			if(product != null){
				serviceTax.setHsnSacCode(product.getHsnCode());
				serviceTax.setHsnSacDescription(product.getHsnDescription());
				if(serviceTax.getIsTaxRateChange()!= null && serviceTax.getIsTaxRateChange().equals("Y")){
					product.setProductIgst((double)serviceTax.getNewTaxRate());
				}
				
					if(serviceTax.getDeliveryStateId() == serviceTax.getGstnStateId()){
						//get cgst + sgst/ugst
						
							double sgstOrUgstInRatio = (Double.parseDouble(sgstOrUgstRatio));
							double cgstInRatio = (Double.parseDouble(cgstRatio));
							cgstPercent = ((cgstInRatio * product.getProductIgst()) / 100);
							if(stateOrUnionTerritory.equals(GSTNConstants.STATE)){
								sgstPercent = ((sgstOrUgstInRatio * product.getProductIgst()) / 100);
								sgstAmount = ((sgstPercent * amountAfterDiscount ) / 100);
								category = GSTNConstants.CATEGORY_WITH_SGST_CSGT;
								
							}else{
								ugstPercent = ((sgstOrUgstInRatio * product.getProductIgst()) / 100);
								ugstAmount = ((ugstPercent * amountAfterDiscount ) / 100);
								category = GSTNConstants.CATEGORY_WITH_UGST_CGST;
							}
							cgstAmount = ((cgstPercent * amountAfterDiscount ) / 100);
							taxAmount = sgstAmount + cgstAmount + ugstAmount;
							
					}else{
						//get igst
						igstAmount =  ((product.getProductIgst() * amountAfterDiscount) / 100);
						taxAmount = igstAmount;
						category = GSTNConstants.CATEGORY_WITH_IGST;
						igstPercent = product.getProductIgst();
					}
					
				
			}//end of product = null
			
			if((serviceTax.getAdvolCess() != 0) || (serviceTax.getNonAdvolCess() != 0)){
				advolCessAmount = ((serviceTax.getAdvolCess() * amountAfterDiscount) / 100); 
				//taxAmount = taxAmount + serviceTax.getCess();
				totalCessAmount = advolCessAmount + serviceTax.getNonAdvolCess();
				taxAmount = taxAmount + totalCessAmount;
			}
			serviceTax.setPreviousAmount( Double.valueOf(new DecimalFormat("#.##").format(previousAmount)));
			serviceTax.setAmountAfterDiscount(Double.valueOf(new DecimalFormat("#.##").format(amountAfterDiscount)));
			serviceTax.setSgstAmount(Double.valueOf(new DecimalFormat("#.##").format(sgstAmount)));
			serviceTax.setUgstAmount(Double.valueOf(new DecimalFormat("#.##").format(ugstAmount)));
			serviceTax.setCgstAmount(Double.valueOf(new DecimalFormat("#.##").format(cgstAmount)));
			serviceTax.setIgstAmount(Double.valueOf(new DecimalFormat("#.##").format(igstAmount)));
			serviceTax.setTaxAmount(Double.valueOf(new DecimalFormat("#.##").format(taxAmount)));
			serviceTax.setCategoryType(category);
			serviceTax.setSgstPercentage(Double.valueOf(new DecimalFormat("#.###").format(sgstPercent)));
			serviceTax.setCgstPercentage(Double.valueOf(new DecimalFormat("#.###").format(cgstPercent)));
			serviceTax.setUgstPercentage(Double.valueOf(new DecimalFormat("#.###").format(ugstPercent)));
			serviceTax.setIgstPercentage(Double.valueOf(new DecimalFormat("#.###").format(igstPercent)));
			serviceTax.setAmount(Double.valueOf(new DecimalFormat("#.##").format(amountAfterDiscount)));
			serviceTax.setAdvolCessAmount(Double.valueOf(new DecimalFormat("#.##").format(advolCessAmount)));
			serviceTax.setCess(Double.valueOf(new DecimalFormat("#.##").format(totalCessAmount)));
		} catch (NumberFormatException e) {
			logger.error("Error in:",e);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Entry");
		return serviceTax;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = {"/createRevisedInvoice","/createNewInvoice"}, method = RequestMethod.POST)
	public @ResponseBody String createRevisedInvoice(@RequestBody InvoiceDetails invoiceDetails, HttpServletRequest httpRequest) {
		logger.info("Entry");
		Map<String, String> mapResponse = new HashMap<String, String>();
		Map<String, Object> mapResponseFromRR = new HashMap<String, Object>();
		String renderData = GSTNConstants.NOT_ALLOWED_ACCESS;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
		String uri = GSTNUtil.getLastURIPart(httpRequest.getRequestURI()); 
		String createDocType = invoiceDetails.getCreateDocType();
		String rrType = invoiceDetails.getRrTypeForCreation();
		String oldInvoiceNumber = null;
		String oldInvoicePkId = null;
		try{
			boolean isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(invoiceDetails.getId(),loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				renderData = invoiceServerSideValidationAsPerRR(invoiceDetails, httpRequest);
				logger.info("ACCESS : "+renderData);
				
				if(renderData.equals(GSTNConstants.ALLOWED_ACCESS)){
					InvoiceDetails newlyCreatedInvoice = new InvoiceDetails();

					mapResponseFromRR  = setDataInNewInvoice(invoiceDetails,httpRequest,loginMaster);
					newlyCreatedInvoice = (InvoiceDetails)mapResponseFromRR.get("INVOICE");
					invoiceDetails = calculationOnInvoiceDetailsOnRR(newlyCreatedInvoice);
					if(invoiceDetails.getInvoiceValue() > 1000000000000000D){
						System.out.println("Invoice value greater than 15 digits");
						renderData = GSTNConstants.TOO_LONG_INVOICE_VALUE;
						mapResponse.put(GSTNConstants.MESSAGE, renderData);
					}else{
						oldInvoiceNumber = invoiceDetails.getInvoiceNumber();
						oldInvoicePkId = invoiceDetails.getId()+"";
						String logoImagePath = userMasterService.getOrgLogoPath(loginMaster.getOrgUId());
						if(uri.equals("/createRevisedInvoice")){
							renderData = generateInvoiceService.saveRR(invoiceDetails);
							generateInvoiceController.sendMailUsingWorkerThread(oldInvoicePkId,oldInvoiceNumber,httpRequest,logoImagePath);
						
						}else{
							invoiceDetails = setDataForFreshNewInvoice(invoiceDetails,loginMaster);
							mapResponse = generateInvoiceService.addGenerateInvoice(invoiceDetails);
							renderData = mapResponse.get(GSTNConstants.RESPONSE);
							if(mapResponse.get(GSTNConstants.RESPONSE).equals(GSTNConstants.SUCCESS)){
								generateInvoiceController.sendMailUsingWorkerThread(mapResponse.get("InvoicePkId"),mapResponse.get("InvoiceNumber"),httpRequest,logoImagePath);
							}
						}
						
						if(renderData.equals(GSTNConstants.SUCCESS)){
							String revisedInvoiceNumber = null;
							String revisedInvoicePkId = null;
							
							if(uri.equals("/createRevisedInvoice")){
								revisedInvoiceNumber = oldInvoiceNumber;
								revisedInvoicePkId = oldInvoicePkId;
							}else{
								revisedInvoiceNumber = (String)mapResponse.get("InvoiceNumber");
								revisedInvoicePkId = (String)mapResponse.get("InvoicePkId");
							}
							
							//remove items from service table if item is removed from invoice (occurs in case of itemchange and multiplechange) - start
							List<Integer> removeItemsList = (List<Integer>)mapResponseFromRR.get("REMOVE_ITEMS");
							if(removeItemsList != null && removeItemsList.size()>0){
								String deleteStatus = generateInvoiceService.removeServiceLineItems(GSTNUtil.getServiceIdsInString(removeItemsList));
							}
							//remove items from service table if item is removed from invoice (occurs in case of itemchange and multiplechange) - end
							
							//create record in revise_and_return_history table
							updateRRHistory(rrType, createDocType, revisedInvoiceNumber,  revisedInvoicePkId,(int)mapResponseFromRR.get("PREVIOUS_ITERATION"),oldInvoiceNumber,  oldInvoicePkId, loginMaster.getOrgUId(), loginMaster.getuId());
						  //add
							if((boolean)mapResponseFromRR.get("INVENTORY_UPDATE")){
								List<Product> creditProductList = (List<Product>)mapResponseFromRR.get("CREDIT_PRODUCT_LIST");
								List<Product> debitProductList = (List<Product>)mapResponseFromRR.get("DEBIT_PRODUCT_LIST");
								InventoryProductSave inventoryProductSave = null;
								if(creditProductList != null && creditProductList.size()>0){
									Integer uniqSeq = inventoryController.getSessionUniqueSequence(loginMaster, httpRequest);
									inventoryProductSave = callInvertoryProductChanges(creditProductList,invoiceDetails.getInvoiceNumber(),(String)mapResponseFromRR.get("INVENTORY_TYPE"),"CREDIT",GSTNConstants.RR_DOCTYPE_REVISED_INVOICE, uniqSeq, GSTNUtil.convertCurrentDateToddMMYYYY());
									if(inventoryProductSave.getProductList().size() > 0){
									    inventoryService.saveInventoryDetails(inventoryProductSave, loginMaster.getuId());
									}	
								}
								if(debitProductList != null && debitProductList.size()>0){
									Integer uniqSeq = inventoryController.getSessionUniqueSequence(loginMaster, httpRequest);
									inventoryProductSave = callInvertoryProductChanges(debitProductList,invoiceDetails.getInvoiceNumber(),(String)mapResponseFromRR.get("INVENTORY_TYPE"),"DEBIT",GSTNConstants.RR_DOCTYPE_REVISED_INVOICE, uniqSeq, GSTNUtil.convertCurrentDateToddMMYYYY());
									if(inventoryProductSave.getProductList().size() > 0){
									    inventoryService.saveInventoryDetails(inventoryProductSave, loginMaster.getuId());
									}
								}
								
							}
						}
					}
				}
		  }
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:",e);
		}
		mapResponse.put(GSTNConstants.RESPONSE, renderData);
		logger.info("Exit");
		return new Gson().toJson(mapResponse);
	}
	
	private InvoiceDetails setDataForFreshNewInvoice(InvoiceDetails invoiceDetails,LoginMaster loginMaster) {

		//invoiceDetails.setId(0);
		invoiceDetails.setCreatedBy(loginMaster.getuId().toString());
		invoiceDetails.setUpdatedBy("");
		invoiceDetails.setUpdatedOn(null);
		invoiceDetails.setStatus("1");
		//invoiceDetails.setInvoiceNumber(generateInvoiceController.generateInvoiceSequence(invoiceDetails.getGstnStateIdInString(),loginMaster,invoiceDetails.getLocation()));
		if(loginMaster.getInvoiceSequenceType().equals("Auto")){
			invoiceDetails.setInvoiceNumber(generateInvoiceController.generateInvoiceSequenceXLogic("S",invoiceDetails.getInvoiceDateInString(),invoiceDetails.getGstnStateIdInString(),loginMaster,invoiceDetails.getLocation()));
		}else{
			invoiceDetails.setInvoiceNumber(invoiceDetails.getInvoiceNumber());
		}
		invoiceDetails.setReferenceId(loginMaster.getuId());
		invoiceDetails.setOrgUId(loginMaster.getOrgUId());
		invoiceDetails.setDeleteYn("N");
		invoiceDetails.setUploadToJiogst("false");
		invoiceDetails.setSaveToGstn("false");
		invoiceDetails.setSubmitToGstn("false");
		invoiceDetails.setFileToGstn("false");
		invoiceDetails.setUploadToJiogstTime(null);
		invoiceDetails.setSaveToGstnTime(null);
		invoiceDetails.setSubmitToGstnTime(null);
		invoiceDetails.setFileToGstnTime(null);
		invoiceDetails.setCnDnList(null);
		invoiceDetails.setCnDnAdditionalList(null);
		invoiceDetails.setGeneratedThrough(loginMaster.getLoggedInThrough());
		
		return invoiceDetails;
	}

	private String updateRRHistory(String rrType, String cndnTypeCreated, String revisedInvoiceNumber, String revisedInvoicePkId,int iterationNo,
			String oldInvoiceNumber, String oldInvoicePkId, Integer orgUId, Integer getuId) {
		ReviseAndReturnHistory rrHistory = new ReviseAndReturnHistory();
		rrHistory.setRrType(rrType);
		rrHistory.setDocumentType(cndnTypeCreated);
		rrHistory.setCreatedDocNo(revisedInvoiceNumber);
		rrHistory.setCreatedDocumentPkId(Integer.parseInt(revisedInvoicePkId));
		rrHistory.setOriginalInvoiceNo(oldInvoiceNumber);
		rrHistory.setOriginalInvoicePkId(Integer.parseInt(oldInvoicePkId));
		rrHistory.setIterationNo(iterationNo);
		rrHistory.setRefOrgId(orgUId);
		rrHistory.setCreatedBy(getuId+"");
		rrHistory.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
		return reviseAndReturnService.updateRRHistory(rrHistory);
	}
	
	private InventoryProductSave callInvertoryProductChanges(List<Product> rrProductList,String invoiceNumber,String inventoryType,String creditDebit, String createdDocType, Integer uniqSeq , String transactionDate) throws Exception {
		List<InventoryProductTable> productList = new ArrayList<InventoryProductTable>();
		InventoryProductTable productTable = null;
		Product product = null;
		InventoryProductSave inventoryProduct = new InventoryProductSave();
		inventoryProduct.setUniqueSequenceid(uniqSeq);
		inventoryProduct.setInventoryType(creditDebit);
		inventoryProduct.setNarration(inventoryType);
		inventoryProduct.setReason(createdDocType+" for invoice number : "+invoiceNumber);
		for(Product items : rrProductList){
			product = productService.getProductById(items.getId());
			if(product != null){
				Double dbCurrentStock = product.getCurrentStock();
				Double dbRate = product.getPurchaseRate();
				Double currentStock = 0d;
				if(items.getTempCreditOrDebit().equals("CREDIT")){
					currentStock = dbCurrentStock + (Double)items.getCurrentStock();
					inventoryType = items.getTempCreditOrDebit();
				}else{
					currentStock = dbCurrentStock - (Double)items.getCurrentStock();
					inventoryType = items.getTempCreditOrDebit();
				}
				Double currentStockValue = currentStock * dbRate;
				productTable = new InventoryProductTable();
				productTable.setId(items.getId());
				productTable.setCurrentStock(currentStock);
				productTable.setCurrentStockValue(currentStockValue);
				productTable.setModifiedQty((Double)items.getCurrentStock());
				productTable.setModifiedStockValue((Double)items.getCurrentStock()* dbRate);
				productTable.setName(product.getName());
				
				productTable.setStoreId(product.getStoreId());
				/*productTable.setFromStoreId(fromStoreId);
				productTable.setToStoreId(toStoreId);*/
				productTable.setUnitOfMeasurement(product.getUnitOfMeasurement());
				productTable.setOtherUOM("");
				productTable.setTransactionDate(transactionDate);
				productTable.setActionFrom(inventoryType);
				productList.add(productTable);
			}
				
			
		}
		inventoryProduct.setProductList(productList);
		System.out.println("Final JSON from invoice : "+new Gson().toJson(inventoryProduct));
		return inventoryProduct;
	}
	
	
	@RequestMapping(value = "/deleteInvoicefromRR", method = RequestMethod.POST)
	public @ResponseBody String deleteInvoicefromRR(@RequestBody InvoiceDetails invoiceDetails, HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		InvoiceDetails existingInvoiceDetails = null;
		boolean isInvoiceAllowed = false;
		boolean isServicesValidated = false;
		List<Product>creditProductList = new ArrayList<>();
		Map<String,String> mapResponse = new HashMap<String,String>();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
			isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(invoiceDetails.getId(),loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				existingInvoiceDetails = generateInvoiceService.getInvoiceDetailsById(invoiceDetails.getId());
				isServicesValidated = taxCalculationController.checkForServicesServerSideValidation(invoiceDetails,existingInvoiceDetails);
				if(isServicesValidated){
					//update in RRHistory
					updateRRHistory(invoiceDetails.getRrTypeForCreation(), invoiceDetails.getCreateDocType(), existingInvoiceDetails.getInvoiceNumber(),  existingInvoiceDetails.getId()+"",existingInvoiceDetails.getIterationNo(),existingInvoiceDetails.getInvoiceNumber(),  existingInvoiceDetails.getId()+"", loginMaster.getOrgUId(), loginMaster.getuId());
					  
					//CREDIT items in inventory
					Product product = null;
					for(InvoiceServiceDetails item: existingInvoiceDetails.getServiceList()){
						if(item.getBillingFor().equals("Product")){
							product = new Product(); 
							product.setId(item.getServiceId());
							product.setCurrentStock(item.getQuantity());
							product.setTempCreditOrDebit("CREDIT");
							creditProductList.add(product);	
						}
					}
					
					if(creditProductList != null && creditProductList.size() > 0){
						Integer uniqSeq = inventoryController.getSessionUniqueSequence(loginMaster, httpRequest);
						InventoryProductSave inventoryProductSave = callInvertoryProductChanges(creditProductList,existingInvoiceDetails.getInvoiceNumber(),invoiceDetails.getRrTypeForCreation(),"CREDIT",GSTNConstants.RR_DOCTYPE_DELETE_INVOICE, uniqSeq, GSTNUtil.convertCurrentDateToddMMYYYY());
						if(inventoryProductSave.getProductList().size() > 0){
						    inventoryService.saveInventoryDetails(inventoryProductSave, loginMaster.getuId());
						}
					}
		
					//delete invoice
					String response = generateInvoiceService.deleteInvoice(loginMaster, existingInvoiceDetails);
					if(response.equalsIgnoreCase("Success")){
						mapResponse.put(GSTNConstants.STATUS, GSTNConstants.SUCCESS);
						mapResponse.put(GSTNConstants.RESPONSE, "Invoice is deleted successfully");
					}else{
						mapResponse.put(GSTNConstants.STATUS, GSTNConstants.FAILURE);
						mapResponse.put(GSTNConstants.RESPONSE, "Error while deleting Invoice");
					}
				}else{
					mapResponse.put(GSTNConstants.STATUS, GSTNConstants.ACCESSVIOLATION);
					mapResponse.put(GSTNConstants.RESPONSE, "You do not have access to delete this invoice.");
				}
				
			}else{
					mapResponse.put(GSTNConstants.STATUS, GSTNConstants.ACCESSVIOLATION);
					mapResponse.put(GSTNConstants.RESPONSE, "You do not have access to delete this invoice.");
				
			}
	
	   
		
		} catch (Exception e) {
			logger.error("Error in:",e);
			mapResponse.put(GSTNConstants.STATUS, GSTNConstants.FAILURE);
			mapResponse.put(GSTNConstants.RESPONSE, GSTNConstants.SERVER_SIDE_ERROR);
		}
		logger.info("Exit");
		return new Gson().toJson(mapResponse);
	}
	
	@RequestMapping(value = {"/getRRByPartyChange"}, method = RequestMethod.POST)
	public String getRRByPartyChange(@RequestParam Integer id, Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.GET_REVISE_AND_RETURN_PARTY_CHANGE_PAGE;
		model.addAttribute("refToFind", id);
		model.addAttribute("backRRType", "partyChange");
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value = {"/getRRByItemChange"}, method = RequestMethod.POST)
	public String getRRByItemChange(@RequestParam Integer id, Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.GET_REVISE_AND_RETURN_ITEM_CHANGE_PAGE;
		model.addAttribute("refToFind", id);
		model.addAttribute("backRRType", "itemChange");
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value = {"/getRRByMultipleChanges"}, method = RequestMethod.POST)
	public String getRRByMultipleChanges(@RequestParam Integer id, Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.GET_REVISE_AND_RETURN_MULTIPLE_CHANGES_PAGE;
		try{
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
			UserMaster user = userMasterService.getUserMasterById( loginMaster.getuId());
			if(loginMaster.getUserRole().equals(GSTNConstants.SECONDARY_USER)){
				user = userMasterService.getUserMasterById(user.getReferenceId());
			}
			model.addAttribute("userDetails", user);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
	
		model.addAttribute("refToFind", id);
		model.addAttribute("backRRType", "multipleChanges");
		
		logger.info("Exit");
		return pageRedirect;
	}
	
	/*@RequestMapping(value = {"/getBackToRevAndRet"}, method = RequestMethod.POST)
	public String getRevAndRet(@RequestParam Integer id, @RequestParam("rrType") String rrType,Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.GET_REVISE_AND_RETURN_PAGE;
		model.addAttribute("refToFind", id);
		model.addAttribute("backRRType", "");
		logger.info("Exit");
		return pageRedirect;
	}*/
	
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/multipleChangesFromRR", method = RequestMethod.POST)
	public @ResponseBody String multipleChangesFromRR(@RequestBody @Valid InvoiceDetails invoiceDetails, BindingResult result, HttpServletRequest httpRequest) {
		logger.info("Entry");
		Map<String, String> mapResponse = new HashMap<String, String>();
		Map<String, Object> mapInventoryChanges = new HashMap<String, Object>();
		String renderData = null;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		boolean isInvoiceAllowed = false;
		String logoImagePath = null;
		InvoiceDetails oldInvoiceDetails = null;
		String createDocType = invoiceDetails.getCreateDocType();
		String rrType = invoiceDetails.getRrTypeForCreation();
		if (!result.hasErrors()){
			try {
				isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(invoiceDetails.getId(),loginMaster.getOrgUId());
				if(isInvoiceAllowed){
					oldInvoiceDetails = generateInvoiceService.getInvoiceDetailsById(invoiceDetails.getId());
					renderData = taxCalculationController.invoiceServerSideValidation(invoiceDetails, httpRequest);
					logger.info("ACCESS : "+renderData);
					if(renderData.equals(GSTNConstants.ALLOWED_ACCESS)){
						logoImagePath = userMasterService.getOrgLogoPath(loginMaster.getOrgUId());
						invoiceDetails = taxCalculationController.calculationOnInvoiceDetails(invoiceDetails);
						invoiceDetails.setInvoiceDate(GSTNUtil.convertStringInddMMyyyyToTimestamp(invoiceDetails.getInvoiceDateInString()));
						if(!invoiceDetails.getInvoicePeriodFromDateInString().equals("") ){
							invoiceDetails.setInvoicePeriodFromDate(GSTNUtil.convertStringInddMMyyyyToTimestamp(invoiceDetails.getInvoicePeriodFromDateInString()));
						}
						if(!invoiceDetails.getInvoicePeriodToDateInString().equals("")){
							invoiceDetails.setInvoicePeriodToDate(GSTNUtil.convertStringInddMMyyyyToTimestamp(invoiceDetails.getInvoicePeriodToDateInString()));
						}
						invoiceDetails.setStatus("1");
						invoiceDetails.setReferenceId(loginMaster.getuId());
						invoiceDetails.setOrgUId(loginMaster.getOrgUId());
						invoiceDetails.setGeneratedThrough(loginMaster.getLoggedInThrough());
						invoiceDetails = generateInvoiceController.setTextFieldsForServicesAndAdditionalCharges(invoiceDetails);
						mapInventoryChanges = getInventoryChangesForMultipleRR(invoiceDetails);
						setIterationForMultipleRRInvoice(invoiceDetails,loginMaster,oldInvoiceDetails.getIterationNo());
						if(invoiceDetails.getInvoiceValue() > 1000000000000000D){
							System.out.println("Invoice value greater than 15 digits");
							renderData = GSTNConstants.TOO_LONG_INVOICE_VALUE;
							mapResponse.put(GSTNConstants.MESSAGE, renderData);
						}else{
							if(createDocType.equals(GSTNConstants.RR_DOCTYPE_REVISED_INVOICE)){
								invoiceDetails = setDataForRevisedInvoice(invoiceDetails,loginMaster,oldInvoiceDetails.getIterationNo());
								renderData = generateInvoiceService.saveRR(invoiceDetails);
								if(renderData.equals(GSTNConstants.SUCCESS)){
									mapResponse.put(GSTNConstants.RESPONSE, GSTNConstants.SUCCESS);
									mapResponse.put("InvoiceNumber", oldInvoiceDetails.getInvoiceNumber());
									mapResponse.put("InvoicePkId", ""+oldInvoiceDetails.getId());
									generateInvoiceController.sendMailUsingWorkerThread(""+oldInvoiceDetails.getId(),oldInvoiceDetails.getInvoiceNumber(),httpRequest,logoImagePath);
								}
							}else{
								invoiceDetails = setDataForFreshNewInvoice(invoiceDetails,loginMaster);
								mapResponse = generateInvoiceService.addGenerateInvoice(invoiceDetails);
								renderData = mapResponse.get(GSTNConstants.RESPONSE);
								if(mapResponse.get(GSTNConstants.RESPONSE).equals(GSTNConstants.SUCCESS)){
									generateInvoiceController.sendMailUsingWorkerThread(mapResponse.get("InvoicePkId"),mapResponse.get("InvoiceNumber"),httpRequest,logoImagePath);
								}
							}
							
							if(renderData.equals(GSTNConstants.SUCCESS)){
								//create record in revise_and_return_history table
								String revisedInvoiceNumber = null;
								String revisedInvoicePkId = null;
								String oldInvoiceNumber = oldInvoiceDetails.getInvoiceNumber();
								String oldInvoicePkId = oldInvoiceDetails.getId()+"";
								if(createDocType.equals(GSTNConstants.RR_DOCTYPE_REVISED_INVOICE)){
									revisedInvoiceNumber = oldInvoiceDetails.getInvoiceNumber();
									revisedInvoicePkId = oldInvoiceDetails.getId()+"";
								}else{
									revisedInvoiceNumber = (String)mapResponse.get("InvoiceNumber");
									revisedInvoicePkId = (String)mapResponse.get("InvoicePkId");
								}
								
								//delete service line items - Start
								List<Integer>removeItemsList = (List<Integer>)mapInventoryChanges.get("REMOVE_ITEMS");
								if(removeItemsList != null && removeItemsList.size()>0){
									String deleteStatus = generateInvoiceService.removeServiceLineItems(GSTNUtil.getServiceIdsInString(removeItemsList));
								}
								//delete service line items - End
								
								updateRRHistory(rrType, createDocType, revisedInvoiceNumber,  revisedInvoicePkId,(int)oldInvoiceDetails.getIterationNo(),oldInvoiceNumber,  oldInvoicePkId, loginMaster.getOrgUId(), loginMaster.getuId());
								
								List<Product> creditProductList = (List<Product>)mapInventoryChanges.get("CREDIT_PRODUCT_LIST");
								List<Product> debitProductList = (List<Product>)mapInventoryChanges.get("DEBIT_PRODUCT_LIST");
								InventoryProductSave inventoryProductSave = null;
								if(creditProductList != null && creditProductList.size()>0){
									Integer uniqSeq = inventoryController.getSessionUniqueSequence(loginMaster, httpRequest);
									inventoryProductSave = callInvertoryProductChanges(creditProductList,invoiceDetails.getInvoiceNumber(),GSTNConstants.RR_MULTIPLE_CHANGE,"CREDIT",GSTNConstants.RR_DOCTYPE_REVISED_INVOICE,uniqSeq,invoiceDetails.getInvoiceDateInString());
									if(inventoryProductSave.getProductList().size() > 0){
									    inventoryService.saveInventoryDetails(inventoryProductSave, loginMaster.getuId());
									}	
								}
								if(debitProductList != null && debitProductList.size()>0){
									Integer uniqSeq = inventoryController.getSessionUniqueSequence(loginMaster, httpRequest);
									inventoryProductSave = callInvertoryProductChanges(debitProductList,invoiceDetails.getInvoiceNumber(),GSTNConstants.RR_MULTIPLE_CHANGE,"DEBIT",GSTNConstants.RR_DOCTYPE_REVISED_INVOICE,uniqSeq,invoiceDetails.getInvoiceDateInString());
									if(inventoryProductSave.getProductList().size() > 0){
									    inventoryService.saveInventoryDetails(inventoryProductSave, loginMaster.getuId());
									}
								}
							
							}
						}
						
					}else{
						mapResponse.put(GSTNConstants.RESPONSE, renderData);
					}	
				}
			} catch (Exception e) {
				logger.error("Backend Error in:",e);
				mapResponse.put(GSTNConstants.RESPONSE, GSTNConstants.SERVER_SIDE_ERROR);
			}
		}else{
			System.out.println("Error occured"+result.getAllErrors());
			logger.error("Error in:"+result.getAllErrors());
			mapResponse.put(GSTNConstants.RESPONSE, GSTNConstants.SERVER_SIDE_ERROR);
		}
		logger.info("Exit");
		return new Gson().toJson(mapResponse);
	}

	private InvoiceDetails setDataForRevisedInvoice(InvoiceDetails invoiceDetails,LoginMaster loginMaster,Integer iterationNo) {
		
		invoiceDetails.setUpdatedBy(loginMaster.getuId()+"");
		invoiceDetails.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
		invoiceDetails.setGeneratedThrough(loginMaster.getLoggedInThrough());
		return invoiceDetails;
	}
	
	private InvoiceDetails setIterationForMultipleRRInvoice(InvoiceDetails invoiceDetails,LoginMaster loginMaster,Integer iterationNo) {
		
	
		invoiceDetails.setIterationNo(iterationNo + 1);
		
		if(invoiceDetails.getServiceList() != null && invoiceDetails.getServiceList().size() > 0){
			for(InvoiceServiceDetails a : invoiceDetails.getServiceList()){
				a.setIterationNo(iterationNo + 1);
			}
		}
		
		if(invoiceDetails.getAddChargesList() != null && invoiceDetails.getAddChargesList().size() > 0){
			for(InvoiceAdditionalChargeDetails a : invoiceDetails.getAddChargesList()){
				a.setIterationNo(iterationNo + 1);
			}
		}
		return invoiceDetails;
	}

	public Map<String, Object> getInventoryChangesForMultipleRR(InvoiceDetails newInvoiceDetails) {
		logger.info("Entry");
		Map<String, Object> mapInventoryChanges = new HashMap<>();
		InvoiceDetails oldInvoiceData = null;
		List<Product>creditProductList = new ArrayList<>();
		List<Product> debitProductList = new ArrayList<>();
		List<Integer>removedServiceItemsIds = new ArrayList<>();
		Product product = null;
		Set<Integer> itemSet = new HashSet<>();
		try{
			oldInvoiceData = generateInvoiceService.getInvoiceDetailsById(newInvoiceDetails.getId());

			for (InvoiceServiceDetails newItem : newInvoiceDetails.getServiceList()) {
				for(InvoiceServiceDetails oldItem : oldInvoiceData.getServiceList()){
					if(oldItem.getServiceId().equals(newItem.getServiceId())){
						newItem.setId(oldItem.getId());
						itemSet.add(oldItem.getServiceId());
						if(oldItem.getQuantity() > newItem.getQuantity()){
							product = new Product();
							product.setId(oldItem.getServiceId());
							product.setCurrentStock(oldItem.getQuantity() - newItem.getQuantity());
							product.setTempCreditOrDebit("CREDIT");
							creditProductList.add(product);	
						}else if(oldItem.getQuantity() < newItem.getQuantity()){
							product = new Product();
							product.setId(oldItem.getServiceId());
							product.setCurrentStock(newItem.getQuantity() - oldItem.getQuantity());
							product.setTempCreditOrDebit("DEBIT");
							debitProductList.add(product);	
						}
					}
				}
			}
			
			for(InvoiceServiceDetails oldItem : oldInvoiceData.getServiceList()){
				if(!itemSet.contains(oldItem.getServiceId())){
					removedServiceItemsIds.add(oldItem.getId());
					itemSet.add(oldItem.getServiceId());
					product = new Product();
					product.setId(oldItem.getServiceId());
					product.setCurrentStock(oldItem.getQuantity());
					product.setTempCreditOrDebit("CREDIT");
					creditProductList.add(product);	
				}	
			}
			
			for(InvoiceServiceDetails newItem : newInvoiceDetails.getServiceList()){
				if(!itemSet.contains(newItem.getServiceId())){
					itemSet.add(newItem.getServiceId());
					product = new Product();
					product.setId(newItem.getServiceId());
					product.setCurrentStock(newItem.getQuantity());
					product.setTempCreditOrDebit("DEBIT");
					creditProductList.add(product);	
				}
			}
			
			mapInventoryChanges.put("CREDIT_PRODUCT_LIST",creditProductList);
			mapInventoryChanges.put("DEBIT_PRODUCT_LIST",debitProductList);
			mapInventoryChanges.put("REMOVE_ITEMS",removedServiceItemsIds);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getInventoryChangesForMultipleRR:"+e.getMessage());
		}
		logger.info("Exit");
		return mapInventoryChanges;
	}
	
	
}
