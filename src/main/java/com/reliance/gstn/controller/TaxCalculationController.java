/**
 * 
 */
package com.reliance.gstn.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.admin.model.AdditionalChargeDetails;
import com.reliance.gstn.admin.service.AdditionalChargeDetailsService;
import com.reliance.gstn.admin.service.HSNService;
import com.reliance.gstn.model.CompleteInvoice;
import com.reliance.gstn.model.CompletePurchaseEntry;
import com.reliance.gstn.model.CustomerDetails;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.InvoiceAdditionalChargeDetails;
import com.reliance.gstn.model.InvoiceCnDnDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.InvoiceServiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.ManageServiceCatalogue;
import com.reliance.gstn.model.Product;
import com.reliance.gstn.model.PurchaseEntryAdditionalChargeDetails;
import com.reliance.gstn.model.PurchaseEntryDetails;
import com.reliance.gstn.model.PurchaseEntryServiceOrGoodDetails;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.GSTINDetailsService;
import com.reliance.gstn.service.GenerateInvoiceService;
import com.reliance.gstn.service.GenericService;
import com.reliance.gstn.service.ManageOffersService;
import com.reliance.gstn.service.ManageServiceCatalogueService;
import com.reliance.gstn.service.ProductService;
import com.reliance.gstn.service.SACService;
import com.reliance.gstn.service.StateService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.AmountInWordsModified;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.LoginUtil;

/**
 * @author Nikesh.Bansod
 *
 */
@Controller
public class TaxCalculationController {
	
	private static final Logger logger = Logger.getLogger(TaxCalculationController.class);
	
	@Autowired
	public AdditionalChargeDetailsService additionalChargeDetailsService;
	
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
	
	@Autowired
	public StateService stateService;
	
	@Value("${sgst_ratio}")
	private String sgstOrUgstRatio;
	
	@Value("${cgst_ratio}")
	private String cgstRatio;
	
	@Value("${diff_percentage_ratio}")
	private String diffPercentageRatio;
	
	@Autowired
	private GSTINDetailsService gstinDetailsService;
	
	@Autowired
	private UserMasterService userMasterService;
	
	@Autowired
	private GenericService genericService;
	
	@Value(value = "${get_prim_id_list_query}")
	private String getPrimIdsListQuery;
	
	@Autowired
	GenerateInvoiceService generateInvoiceService;

	
	@RequestMapping(value = "/calculateTaxAmount", method = RequestMethod.POST)
	public @ResponseBody String calculateTaxAmount(@RequestBody InvoiceServiceDetails serviceTax, HttpServletRequest httpRequest) {
		
		logger.info("[ Amount : "+serviceTax.getAmount()+",CalculationBasedOn :"+serviceTax.getCalculationBasedOn()+",BillingFor: "+serviceTax.getBillingFor()+",ServiceId :"+serviceTax.getServiceId()+"]");
		boolean validateClientValues = false;
		if(serviceTax != null){
			
			try {
				validateClientValues = checkClientValuesWithServerValues(serviceTax,httpRequest);
				if(validateClientValues){
					
					if(serviceTax.getBillingFor().equals(GSTNConstants.PRODUCT)){
						serviceTax = calculateTaxForProduct(serviceTax);
					}else{
						serviceTax = calculateTaxForService(serviceTax);
					}
				}else{
					logger.error("User tried to manipulate the data");
					serviceTax.setIsValid("Invalid Input");
				}
			} catch (Exception e) {
				logger.error("Error",e);
			}
			
		}
		return new Gson().toJson(serviceTax);
	}

	private InvoiceServiceDetails calculateTaxForService(InvoiceServiceDetails serviceTax) {
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
					if((serviceTax.getTypeOfExport().equals(GSTNConstants.EXPORT_TYPE_WITH_BOND)) || (serviceTax.getTypeOfExport().equals(GSTNConstants.EXPORT_TYPE_WITH_IGST))){
						if(serviceTax.getTypeOfExport().equals(GSTNConstants.EXPORT_TYPE_WITH_IGST)){
							igstAmount = ((service.getServiceIgst() * amountAfterDiscount) / 100);
							if(serviceTax.getDiffPercent().equals("Y")){
								taxAmount = ((Double.parseDouble(diffPercentageRatio) * igstAmount)/100);
							}else{
								taxAmount = igstAmount;
							}
							
							category = GSTNConstants.CATEGORY_EXPORT_WITH_IGST;
							igstPercent = service.getServiceIgst();
						}
					}else{	
					
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

	private InvoiceServiceDetails calculateTaxForProduct(InvoiceServiceDetails serviceTax) {
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
				if((serviceTax.getTypeOfExport().equals(GSTNConstants.EXPORT_TYPE_WITH_BOND)) || (serviceTax.getTypeOfExport().equals(GSTNConstants.EXPORT_TYPE_WITH_IGST))){
					if(serviceTax.getTypeOfExport().equals(GSTNConstants.EXPORT_TYPE_WITH_IGST)){
						igstAmount =  ((product.getProductIgst() * amountAfterDiscount) / 100);
						taxAmount = igstAmount;
						category = GSTNConstants.CATEGORY_EXPORT_WITH_IGST;
						igstPercent = product.getProductIgst();
					}
				}else{
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
					
				}
			}
			
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

	@RequestMapping(value = "/calculateTaxAmountPost", method = RequestMethod.POST)
	public @ResponseBody String calculateTaxAmountGet(@RequestBody InvoiceServiceDetails serviceTax, HttpServletRequest httpRequest) {
		System.out.println("calculateTaxAmount");
		boolean validateClientValues = false;
		if(serviceTax != null){
			
			try {
				validateClientValues = checkClientValuesWithServerValues(serviceTax, httpRequest);
				if(validateClientValues){
					
					if(serviceTax.getBillingFor().equals(GSTNConstants.PRODUCT)){
						serviceTax = calculateTaxForProduct(serviceTax);
					}else{
						serviceTax = calculateTaxForService(serviceTax);
					}
				}else{
					logger.error("User tried to manipulate the data");
					serviceTax.setIsValid("Invalid Input");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return new Gson().toJson(serviceTax);
	}
	
/*	@RequestMapping(value = "/calculateTaxForAdditionalCharges", method = RequestMethod.POST)
	public @ResponseBody String calculateTaxForAdditionalChargesPost(@RequestBody InvoiceAdditionalChargeDetails invoiceAdditionalCharge, HttpServletRequest httpRequest) {
		logger.info("Entry");
		System.out.println("Additional Charge ID : "+invoiceAdditionalCharge.getAdditionalChargeId());
		AdditionalChargeDetails addChargeDetail = null;
		try {
			addChargeDetail = additionalChargeDetailsService.getAdditionalChargeDetailsById(invoiceAdditionalCharge.getAdditionalChargeId());
			if(addChargeDetail != null){
				invoiceAdditionalCharge = calculateTaxForAdditionalChgs(invoiceAdditionalCharge,addChargeDetail);
			}
			
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(invoiceAdditionalCharge);
	}*/
	
	@RequestMapping(value = "/calculateTaxOnInvoicePreview", method = RequestMethod.POST)
	public @ResponseBody String calculateTaxOnInvoicePreview(@RequestBody @Valid InvoiceDetails invoiceDetails, BindingResult result, HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		String amtInWords = null;

		CompleteInvoice completeInvoice = new CompleteInvoice();
		String renderData = GSTNConstants.NOT_ALLOWED_ACCESS;
		if (!result.hasErrors()){
			try {
				renderData = invoiceServerSideValidation(invoiceDetails, httpRequest);
				completeInvoice.setRenderData(renderData);
				logger.info("ACCESS : "+renderData);
				if(renderData.equals(GSTNConstants.ALLOWED_ACCESS)){
					invoiceDetails = calculationOnInvoiceDetails(invoiceDetails);
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
				}
				
			} catch (Exception e) {
				logger.error("Error in:",e);
			}
		}else{
			completeInvoice.setRenderData(renderData);
			logger.info("ACCESS DENIED");
			logger.error("Error in:"+result.getAllErrors());
		}
		
		logger.info("Exit");
		return new Gson().toJson(completeInvoice);
	}
	
	public String invoiceServerSideValidation(InvoiceDetails invoiceDetails, HttpServletRequest httpRequest) throws Exception {
		logger.info("Entry");
		String renderData = GSTNConstants.NOT_ALLOWED_ACCESS;
		
		//First check for services/products if it belongs to current organization or not - Start
		boolean isServiceOrProductValid = false;
		boolean isAllowed = true;
		boolean isAddChargeValid = true;
		boolean isAddChargeAllowed = true;
		boolean isGstnNumberValid = false;
		boolean isCustomerValid = false;
		for(InvoiceServiceDetails aa : invoiceDetails.getServiceList()){
			
			// if isServiceOrProductValid is false it means data is not manipulated at client side - allow it to go for normal flow
			if(isAllowed){
				isServiceOrProductValid = checkClientValuesWithServerValues(aa, httpRequest);
				logger.info("Service/Product Response : "+aa.getBillingFor()+", id : "+aa.getServiceId() + ",isServiceOrProductValid : "+isServiceOrProductValid);
				if(!isServiceOrProductValid){
					isAllowed = false;
					break;
				}
				
			}
		}
		
		//First check for services/products if it belongs to current organization or not - End
		
		//Secondly check for additional charges if it belongs to current organization or not - Start
		
		//check for additional charges
	/*	if(invoiceDetails.getAddChargesList() != null && invoiceDetails.getAddChargesList().size() > 0){
			for(InvoiceAdditionalChargeDetails addChg : invoiceDetails.getAddChargesList()){
				// if isServiceOrProductValid is false it means data is not manipulated at client side - allow it to go for normal flow
				if(isAddChargeAllowed){
					isAddChargeValid = checkAddChargesClientValuesWithServerValues(addChg, httpRequest);
					logger.info("Additional Charge Response -  id : "+addChg.getAdditionalChargeId() + ",isAddChargeValid : "+isAddChargeValid);
					if(!isAddChargeValid){
						isAddChargeAllowed = false;
						break;
					}
					
				}
			}	
		}else{
			logger.info("No additional charge present for current invoice .Hence skipped validation and set isAddChargeValid = true");
			isAddChargeValid = true;
		}*/
		
		
		//Secondly check for additional charges if it belongs to current organization or not - End
		
		//Third - check for gstn - Start
			isGstnNumberValid = checkGstnNumberWithServerValues(invoiceDetails.getGstnStateIdInString(),httpRequest);
		//Third - check for gstn - End
		
		//Fourth - check for customer - Start
			isCustomerValid = checkCustomerDetailsWithServerValues(invoiceDetails.getCustomerDetails(),httpRequest);
		//Fourth - check for customer - End
		
		
		if(isAllowed && isAddChargeValid && isGstnNumberValid && isCustomerValid){
			logger.info("ACCESS ALLOWED");
			renderData = GSTNConstants.ALLOWED_ACCESS;
		}
		
		logger.info("Exit");
		return renderData;
	}
	
	public boolean checkCustomerDetailsWithServerValues(CustomerDetails customerDetails, HttpServletRequest httpRequest) throws Exception {
		logger.info("Entry");
		boolean isCustomerValid = false;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		Integer custId = customerDetails.getId();
		isCustomerValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(),getPrimIdsListQuery,custId,"CustomerDetails","refOrgId");
		
		if(isCustomerValid){
			isCustomerValid = true;
		}
		
		logger.info("Exit");
		return isCustomerValid;
	}

	public boolean checkGstnNumberWithServerValues(String gstnStateIdInString, HttpServletRequest httpRequest) throws Exception {
		logger.info("Entry");
		boolean isGstnNumberValid = false;
		GSTINDetails gstinDetails = null;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(gstnStateIdInString, loginMaster.getPrimaryUserUId());
		if(gstinDetails != null){
			isGstnNumberValid = true;
		}
		logger.info("Exit");
		return isGstnNumberValid;
	}

	private boolean checkAddChargesClientValuesWithServerValues(InvoiceAdditionalChargeDetails addChg, HttpServletRequest httpRequest) throws Exception {
		logger.info("Entry");
		boolean validateClientValues = false;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		boolean isMappingValid = false;
		String idsValuesToFetch = null;
		AdditionalChargeDetails addChgForVerify = null;
		try{
			
			idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			logger.info("idsValuesToFetch - "+idsValuesToFetch);
			isMappingValid = genericService.checkUserMappingValidation(idsValuesToFetch,getPrimIdsListQuery,addChg.getAdditionalChargeId(),"AdditionalChargeDetails","referenceId");
			
			if(isMappingValid){
				logger.info("Add Charges id - "+addChg.getAdditionalChargeId()+" belongs to current organization. Hence valid.");
				addChgForVerify = additionalChargeDetailsService.getAdditionalChargeDetailsById(addChg.getAdditionalChargeId());
				logger.info("ClientSide Charge Value - "+addChg.getAdditionalChargeAmount()+", ServerSide Charge Value - "+addChgForVerify.getChargeValue());
				if(addChgForVerify.getChargeValue() == addChg.getAdditionalChargeAmount()){
					logger.info("ClientSide Charge Value and ServerSide Charge Value matches. Hence valid.");
					validateClientValues = true;
				}
				
			}
			
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		
		logger.info("Exit");
		return validateClientValues;
	}

	public boolean checkClientValuesWithServerValues(InvoiceServiceDetails serviceOrProduct, HttpServletRequest httpRequest) throws Exception {
		logger.info("Entry");
		logger.info("InvoiceServiceDetails : PreviousAmount() - "+serviceOrProduct.getPreviousAmount()+", serviceOrProduct.getOfferAmount() - "+serviceOrProduct.getOfferAmount());
		boolean validateClientValues = false;
		boolean checkFirst = false;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		boolean isMappingValid = false;
		String idsValuesToFetch = null;
		boolean checkSecond = false;
		double ssamount = 0d;
		double csamount = 0d;
		
		//checkFirst -  previousAmount > offerAmount - Start
		if(serviceOrProduct.getPreviousAmount() <= serviceOrProduct.getOfferAmount()){
			logger.info("checkFirst : "+checkFirst);
			checkFirst = true;
		}
		//checkFirst -  previousAmount > offerAmount - End
		
		//if checkFirst = false it means previousAmount > offerAmount and hence allow him to go ahead
		if(!checkFirst){
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
			
			//if checkSecond = true it means the product or service id belongs to that organization and hence allow him to go ahead
			if(checkSecond){
				/*if(serviceOrProduct.getCalculationBasedOn().equals(GSTNConstants.CALCUATION_BASED_ON_AMOUNT)){
					logger.info("CALCUATION_BASED_ON_"+GSTNConstants.CALCUATION_BASED_ON_AMOUNT);
					if(serviceOrProduct.getBillingFor().equals(GSTNConstants.PRODUCT)){
						Product product = productService.getProductById(serviceOrProduct.getServiceId());
						if(serviceOrProduct.getRate() == product.getProductRate()){
							ssamount = ((product.getProductRate())*(serviceOrProduct.getQuantity()));
							csamount = ((serviceOrProduct.getRate())*(serviceOrProduct.getQuantity()));
							BigDecimal ssbd = new BigDecimal(ssamount).setScale(4, BigDecimal.ROUND_DOWN);
							BigDecimal csbd = new BigDecimal(csamount).setScale(4, BigDecimal.ROUND_DOWN);
							BigDecimal pabd = new BigDecimal(serviceOrProduct.getPreviousAmount()).setScale(4, BigDecimal.ROUND_DOWN);
							logger.info("Product : ServerSideAmount - "+ssbd+", ClientSideAmount - "+csbd+", ClientSidePreviousAmount - "+pabd);
							if((ssbd.equals(csbd)) && (pabd.equals(ssbd))){
								logger.info("Product : ServerSideAmount matches with ClientSideAmount and  ClientSidePreviousAmount. Hence valid.");
								validateClientValues = true;
							}
						}
						
					}else{
						ManageServiceCatalogue service = manageServiceCatalogueService.getManageServiceCatalogueById(serviceOrProduct.getServiceId());
						if(serviceOrProduct.getRate() == service.getServiceRate()){
							ssamount = ((service.getServiceRate())*(serviceOrProduct.getQuantity()));
							csamount = ((serviceOrProduct.getRate())*(serviceOrProduct.getQuantity()));
							BigDecimal ssbd = new BigDecimal(ssamount).setScale(4, BigDecimal.ROUND_DOWN);
							BigDecimal csbd = new BigDecimal(csamount).setScale(4, BigDecimal.ROUND_DOWN);
							BigDecimal pabd = new BigDecimal(serviceOrProduct.getPreviousAmount()).setScale(4, BigDecimal.ROUND_DOWN);
							logger.info("Service : ServerSideAmount - "+ssbd+", ClientSideAmount - "+csbd+", ClientSidePreviousAmount - "+pabd);
							if((ssbd.equals(csbd)) && (pabd.equals(ssbd))){
								logger.info("Service : ServerSideAmount matches with ClientSideAmount and  ClientSidePreviousAmount. Hence valid.");
								validateClientValues = true;
							}
						}
						
					}
					
				}else{
					//serviceOrProduct.setQuantity(1);
					logger.info("As CALCUATION_BASED_ON = "+GSTNConstants.CALCUATION_BASED_ON_LUMPSUM+", No need to validate for rate ,quantity and amount at server side ");
					validateClientValues = true;
				}*/
				validateClientValues = true;
			}
			
		}
		
		logger.info("Exit");
		return validateClientValues;
	}

	public InvoiceDetails calculationOnInvoiceDetails(InvoiceDetails invoiceDetails) throws Exception{
		logger.info("Entry");
		double additionalChargesValue = 0;
		double preAmountAfterDiscount = 0;
		double amountAfterDiscount = 0;
		double totalTax = 0;
		double invoiceValue = 0;
		double invoiceValueAfterRoundOff = 0;
		double otherCharges = 0;
		double totalDiscounts = 0;
		try {
			
			//calculate total additional charge value - Start
			if((invoiceDetails.getAddChargesList() != null) && (invoiceDetails.getAddChargesList().size() > 0)){
				for(InvoiceAdditionalChargeDetails aa : invoiceDetails.getAddChargesList()){
					additionalChargesValue = additionalChargesValue + aa.getAdditionalChargeAmount();
				}
			}
			//calculate total additional charge value - End
			
			//calculate total amountAfterDiscount value - Start
			for(InvoiceServiceDetails bb : invoiceDetails.getServiceList()){
				double offerAmount = 0;
				if(bb.getOfferAmount() != 0){
					if(bb.getDiscountTypeInItem().equals("Percentage")){
					    offerAmount = ((bb.getPreviousAmount() * bb.getOfferAmount())/100);
					}else{
					    offerAmount = bb.getOfferAmount();	
					}
				}
				bb.setOfferAmount(offerAmount);
				preAmountAfterDiscount = preAmountAfterDiscount + (bb.getPreviousAmount() - bb.getOfferAmount());
			}
			//calculate total amountAfterDiscount value - End
			
			//set value for gstnStateId, DeliveryStateId, typeOfExport - Start 
			for(InvoiceServiceDetails bb : invoiceDetails.getServiceList()){
				bb.setGstnStateId(invoiceDetails.getGstnStateId());
				bb.setDeliveryStateId(invoiceDetails.getDeliveryPlace());
				bb.setTypeOfExport(invoiceDetails.getTypeOfExport());
				if(additionalChargesValue > 0){
					bb.setAdditionalAmount(GSTNUtil.convertToDouble(additionalChargesValue * ((bb.getPreviousAmount() - bb.getOfferAmount()) / preAmountAfterDiscount)));
					totalDiscounts = totalDiscounts + bb.getOfferAmount();
				}
				if(bb.getBillingFor().equals(GSTNConstants.PRODUCT)){
					bb = calculateTaxForProduct(bb);
				}else{
					bb = calculateTaxForService(bb);
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
	
	@RequestMapping(value = "/calculateTaxOnCnDnInvoicePreview", method = RequestMethod.POST)
	public @ResponseBody String calculateTaxOnCnDnInvoicePreview(@RequestBody InvoiceDetails invoiceDetails, HttpServletRequest httpRequest) {
		logger.info("Entry");
		//2380
		String amtInWords = null;
		CompleteInvoice completeInvoice = new CompleteInvoice();
		InvoiceDetails existingInvoiceDetails = null;
		boolean isInvoiceAllowed = false;
		boolean isServicesValidated = false;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
			isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(invoiceDetails.getId(),loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				existingInvoiceDetails = generateInvoiceService.getInvoiceDetailsById(invoiceDetails.getId());
				isServicesValidated = checkForServicesServerSideValidation(invoiceDetails,existingInvoiceDetails);
				if(isServicesValidated){
					invoiceDetails = mergeCnDnDetailsInServiceList(invoiceDetails,existingInvoiceDetails);
					invoiceDetails = calculationOnCnDnInvoiceDetails(invoiceDetails);	
					invoiceDetails.setCnDnList(GSTNUtil.changeObjectFromServiceToCnDn(invoiceDetails.getServiceList()));
					amtInWords = AmountInWordsModified.convertNumberToWords(""+invoiceDetails.getInvoiceValueAfterRoundOff());	
				
				
					//set value in CompleteInvoice - Start	
					completeInvoice.setInvoiceDetails(invoiceDetails);
					completeInvoice.setAmtInWords(amtInWords);
					completeInvoice.setCustomerStateCode(stateService.getStateByStateName(invoiceDetails.getCustomerDetails().getCustState()).getStateCode());
					completeInvoice.setGstinDetails(gstinDetailsService.getGstinDetailsFromGstinNo(invoiceDetails.getGstnStateIdInString(),loginMaster.getPrimaryUserUId()));
					completeInvoice.setGstMap(GSTNUtil.convertListToMap(invoiceDetails.getServiceList()));
					completeInvoice.setRenderData(GSTNConstants.ALLOWED_ACCESS);
					//set value in CompleteInvoice - End
				}else{
					completeInvoice.setRenderData(GSTNConstants.NOT_ALLOWED_ACCESS);
				}
				
			}else{
				completeInvoice.setRenderData(GSTNConstants.NOT_ALLOWED_ACCESS);
			}
	
	   
		
		} catch (Exception e) {
			logger.error("Error in:",e);
			completeInvoice.setRenderData(GSTNConstants.SERVER_SIDE_ERROR);
		}
		logger.info("Exit");
		return new Gson().toJson(completeInvoice);
	}
	
	/*
	 * This method for created for server side validation of services.
	 * Infosec issue Raised by Poonam Singh
	 * Description : It is observed that service id parameter of one user can be manipulated  and can get the respective response/data of other user
	 * Vulnerable Parameter: serviceId
	 * 
	 * */
	public boolean checkForServicesServerSideValidation(InvoiceDetails invoiceDetails, InvoiceDetails existingInvoiceDetails) {
		boolean isServicesValidated = true;
		boolean isPresent = false;
		
		for(InvoiceCnDnDetails current : invoiceDetails.getCnDnList()){
			isPresent = false;
			for(InvoiceServiceDetails existing : existingInvoiceDetails.getServiceList()){
				if(current.getServiceId().equals(existing.getId())){
					isPresent = true;
					break;
				}
			}
			
			if(!isPresent){
				isServicesValidated = false;
				break;
			}
		}
		
		
		return isServicesValidated;
	}
	

	public InvoiceDetails getInvoiceDetailsForCnDn(InvoiceDetails invoiceDetails){
		logger.info("Entry");
		InvoiceDetails existingInvoiceDetails = null;
		String modifiedDate = invoiceDetails.getInvoiceDateInString();
		try {
			existingInvoiceDetails = generateInvoiceService.getInvoiceDetailsById(invoiceDetails.getId());
			invoiceDetails = mergeCnDnDetailsInServiceList(invoiceDetails,existingInvoiceDetails);
			invoiceDetails = calculationOnCnDnInvoiceDetails(invoiceDetails);	
			invoiceDetails.setCnDnList(GSTNUtil.changeObjectFromServiceToCnDn(invoiceDetails.getServiceList()));
			invoiceDetails.setInvoiceDateInString(modifiedDate);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return invoiceDetails;
	}

	private InvoiceDetails mergeCnDnDetailsInServiceList(InvoiceDetails invoiceDetails, InvoiceDetails existingInvoiceDetails) {
		String typeChange = null;
		List<InvoiceServiceDetails> newServiceList = new ArrayList<InvoiceServiceDetails>();
		int invLineItemSize = existingInvoiceDetails.getServiceList().size();
		int cndnLineItemSize = invoiceDetails.getCnDnList().size();
		boolean considerAdditionalAmount = false;
		if(invoiceDetails != null && existingInvoiceDetails != null){
			if(invoiceDetails.getCreateDocType().equals(GSTNConstants.RR_SALES_RETURN) || invoiceDetails.getCreateDocType().equals(GSTNConstants.RR_DISCOUNT_CHANGE) || invoiceDetails.getCreateDocType().equals((GSTNConstants.RR_SALES_PRISE_CHANGE))){
				 considerAdditionalAmount = checkWhetherToSetAdditionalAmount(invoiceDetails,existingInvoiceDetails,invoiceDetails.getCreateDocType());
			}
			for(InvoiceServiceDetails existingService : existingInvoiceDetails.getServiceList()){
				for(InvoiceCnDnDetails cndn : invoiceDetails.getCnDnList()){
					if(cndn.getServiceId().equals(existingService.getId())){
						existingService.setCnDnAppliedRate(cndn.getRate());
						existingService.setQuantity(cndn.getQuantity());
						
						if(invoiceDetails.getCreateDocType().equals(GSTNConstants.RR_SALES_RETURN) || invoiceDetails.getCreateDocType().equals(GSTNConstants.RR_DISCOUNT_CHANGE) || invoiceDetails.getCreateDocType().equals((GSTNConstants.RR_SALES_PRISE_CHANGE))){
							if(!considerAdditionalAmount){
								existingService.setAdditionalAmount(0);
							}
						}
						
						//existingService.setAdditionalAmount(0);
						if(cndn.getReason().equals(GSTNConstants.RR_DISCOUNT_CHANGE)){
							typeChange = GSTNConstants.RR_DISCOUNT_CHANGE;
							double currentOffer = cndn.getTaxableValue();
							double previousOffer = existingService.getOfferAmount();
							double lastOfferAmount = Math.abs(previousOffer - currentOffer);
							if(existingService.getDiscountTypeInItem().equals("Percentage")){
								double previousOfferInPercentage = (double)((previousOffer * 100)/existingService.getPreviousAmount());
								double currentOfferInPercentage = cndn.getTaxableValue();
								double diffInPercentage = Math.abs(previousOfferInPercentage - currentOfferInPercentage);
								lastOfferAmount  = ((diffInPercentage * existingService.getPreviousAmount())/ 100);
							}
							existingService.setOfferAmount(lastOfferAmount);
							existingService.setPreviousAmount( 2 * lastOfferAmount);
						}else if(cndn.getReason().equals(GSTNConstants.RR_SALES_RETURN) || cndn.getReason().equals(GSTNConstants.RR_QUANTITY_CHANGE)){
							double taxableValue = cndn.getQuantity() * existingService.getRate();
							//set offer amount - Start
							double previousOffer = existingService.getOfferAmount();
							double previousAmout = existingService.getPreviousAmount();
							double offerAmount = 0;
							
								if(existingService.getDiscountTypeInItem().equals("Percentage")){
									double previousOfferInPercentage = (double)((previousOffer * 100)/previousAmout);
									taxableValue = cndn.getQuantity() * existingService.getRate();
									offerAmount = ((previousOfferInPercentage * taxableValue)/100);
								}
								
							//set offer amount - End
							
							existingService.setPreviousAmount(taxableValue);
							existingService.setOfferAmount(offerAmount);
							if(cndn.getReason().equals(GSTNConstants.RR_SALES_RETURN)){
								typeChange = GSTNConstants.RR_SALES_RETURN;
							}else if(cndn.getReason().equals(GSTNConstants.RR_QUANTITY_CHANGE)){
								typeChange = GSTNConstants.RR_QUANTITY_CHANGE;
							}
							
						}else if(cndn.getReason().equals(GSTNConstants.RR_SALES_PRISE_CHANGE)){
							typeChange = GSTNConstants.RR_SALES_PRISE_CHANGE;
							//UI rate-gstTaxRate , taxableValue-newSellingRate
							double taxableValue = cndn.getQuantity() * existingService.getRate();
							taxableValue = cndn.getTaxableValue() * existingService.getQuantity();
							existingService.setRate(cndn.getTaxableValue());
							existingService.setCnDnAppliedRate(cndn.getRate());
							existingService.setPreviousAmount(taxableValue);
							existingService.setOfferAmount(0);
							
						}
						
						existingService.setCess(cndn.getCess());
						//set additional fields
						existingService.setCnDnType(cndn.getCnDnType());
						existingService.setReason(cndn.getReason());
						existingService.setRegime(cndn.getRegime());
						existingService.setTaxableValue(cndn.getTaxableValue());
						existingService.setValueAfterTax(cndn.getValueAfterTax());
						existingService.setDiffPercent(cndn.getDiffPercent());
						existingInvoiceDetails.setRrTypeForCreation(cndn.getReason());
						newServiceList.add(existingService);
					}
				}
			}
		}
		
		existingInvoiceDetails.setServiceList(newServiceList);
		existingInvoiceDetails = setAdditionalChargesOnEachLineItem(existingInvoiceDetails,typeChange,invoiceDetails,invLineItemSize,cndnLineItemSize,considerAdditionalAmount);
		//existingInvoiceDetails.setAddChargesList(null);
		return existingInvoiceDetails;
	}
	
	private boolean checkWhetherToSetAdditionalAmount(InvoiceDetails invoiceDetails, InvoiceDetails existingInvoiceDetails,String createDocType) {
		boolean considerAdditionalAmount = false;
		boolean considerAdditionalAmountL1 = false;
		
		if(invoiceDetails != null && existingInvoiceDetails != null){
			//------ RR_SALES_RETURN - Start ------------------------------------------------------
			if(createDocType.equals(GSTNConstants.RR_SALES_RETURN)){
				Map<Integer,Double> usedIdQty = new HashMap<Integer,Double>();
				Map<Integer,Double> invoiceIdQty = new HashMap<Integer,Double>();
				if(existingInvoiceDetails.getServiceList().size() == invoiceDetails.getCnDnList().size()){
					considerAdditionalAmountL1 = true;
				}
				
				for(InvoiceCnDnDetails cndn : existingInvoiceDetails.getCnDnList()){
					if(usedIdQty.containsKey(cndn.getServiceId())){
						Double previousQuantity = usedIdQty.get(cndn.getServiceId());
						usedIdQty.put(cndn.getServiceId(), previousQuantity + cndn.getQuantity());
					}else{
						usedIdQty.put(cndn.getServiceId(), cndn.getQuantity());
					}
				}
				
				for(InvoiceServiceDetails existingService : existingInvoiceDetails.getServiceList()){
					if(invoiceIdQty.containsKey(existingService.getId())){
						Double previousQuantity = invoiceIdQty.get(existingService.getId());
						invoiceIdQty.put(existingService.getId(), previousQuantity + existingService.getQuantity());
					}else{
						invoiceIdQty.put(existingService.getId(), existingService.getQuantity());
					}
				}
				
				if(considerAdditionalAmountL1){
					for(InvoiceServiceDetails existingService : existingInvoiceDetails.getServiceList()){
						for(InvoiceCnDnDetails cndn : invoiceDetails.getCnDnList()){
							if(cndn.getServiceId().equals(existingService.getId())){
								Double usedQty = 0d;
								if(usedIdQty.containsKey(cndn.getServiceId())){
									usedQty = usedIdQty.get(cndn.getServiceId());
								}
								if((invoiceIdQty.get(cndn.getServiceId()) - usedQty) == cndn.getQuantity()){
									considerAdditionalAmount = true;
									continue;
								}else{
									considerAdditionalAmount = false;
								}
							}
						}
						if(!considerAdditionalAmount){
							break;
						}
					}
				}
			}
			//------ RR_SALES_RETURN - End ----------------------------------------------------------
			//------ RR_DISCOUNT_CHANGE - Start ------------------------------------------------------
			if(createDocType.equals(GSTNConstants.RR_DISCOUNT_CHANGE)){
				Map<Integer,Double> invoiceIdVsPreviousAmount = new HashMap<Integer,Double>();
				Map<Integer,Double> cnIdVsOfferAmount = new HashMap<Integer,Double>();
				//Check 1 - Iterate through invoice and Check if any discount is given in invoice line items
				for(InvoiceServiceDetails existingService : existingInvoiceDetails.getServiceList()){
					if(invoiceIdVsPreviousAmount.containsKey(existingService.getId())){
						Double previousAmount = invoiceIdVsPreviousAmount.get(existingService.getId());
						invoiceIdVsPreviousAmount.put(existingService.getId(), previousAmount + existingService.getPreviousAmount());
					}else{
						invoiceIdVsPreviousAmount.put(existingService.getId(), existingService.getPreviousAmount());
					}
					
					if(cnIdVsOfferAmount.containsKey(existingService.getId())){
						double invoiceOfferAmount = existingService.getOfferAmount();
						cnIdVsOfferAmount.put(existingService.getId(), invoiceOfferAmount + existingService.getOfferAmount());
					}else{
						cnIdVsOfferAmount.put(existingService.getId(), existingService.getOfferAmount());
					}
				}
				
				//Check 2- Iterate through CNDN's created against invoice and check for any discounts given
				for(InvoiceCnDnDetails cndn : existingInvoiceDetails.getCnDnList()){
					if(cnIdVsOfferAmount.containsKey(cndn.getServiceId())){
						Double previousOfferAmount = cnIdVsOfferAmount.get(cndn.getServiceId());
						if(cndn.getCnDnType().equals("creditNote")){
							cnIdVsOfferAmount.put(cndn.getServiceId(), previousOfferAmount + cndn.getOfferAmount());
						}else{
							cnIdVsOfferAmount.put(cndn.getServiceId(), previousOfferAmount - cndn.getOfferAmount());
						}
					}
				}
				
				//Check 3 - Iterate through current cndn created and calculate what current cndn offerAmount is
				for(InvoiceServiceDetails existingService : existingInvoiceDetails.getServiceList()){
					for(InvoiceCnDnDetails cndn : invoiceDetails.getCnDnList()){
						if(cndn.getServiceId().equals(existingService.getId())){
							
							double currentOffer = cndn.getTaxableValue();
							double previousOffer = cnIdVsOfferAmount.get(cndn.getServiceId());//existingService.getOfferAmount();
							double lastOfferAmount = Math.abs(previousOffer - currentOffer);
							if(existingService.getDiscountTypeInItem().equals("Percentage")){
								double previousOfferInPercentage = (double)((previousOffer * 100)/existingService.getPreviousAmount());
								double currentOfferInPercentage = cndn.getTaxableValue();
								double diffInPercentage = Math.abs(previousOfferInPercentage - currentOfferInPercentage);
								lastOfferAmount  = ((diffInPercentage * existingService.getPreviousAmount())/ 100);
							}
							
							if(cnIdVsOfferAmount.containsKey(cndn.getServiceId())){
								
								Double previousQuantity = cnIdVsOfferAmount.get(cndn.getServiceId());
								if(cndn.getCnDnType().equals("creditNote")){
									cnIdVsOfferAmount.put(cndn.getServiceId(), previousQuantity + lastOfferAmount);
								}else{
									cnIdVsOfferAmount.put(cndn.getServiceId(), previousQuantity - lastOfferAmount);
								}
								
							}
						}
					}
				}
				
				//check 4 - check if total offerAmounts in each line item become equal to previousAmount so that we can apply additional charges on it
				//if each line item previousAmount equals total offerAmount. And if all line items previousAmount equals offerAmount then apply additional charges 
				//even if one line item previousAmount does not equals total offerAmount then dont apply additional charges
				for(InvoiceServiceDetails existingService : existingInvoiceDetails.getServiceList()){
					if((invoiceIdVsPreviousAmount.get(existingService.getId()) - cnIdVsOfferAmount.get(existingService.getId()) == 0)){
								considerAdditionalAmount = true;
								continue;
					}else{
						considerAdditionalAmount = false;
					}
					if(!considerAdditionalAmount){
						break;
					}
				 }
			}
			//------- RR_DISCOUNT_CHANGE - End --------------------------------------------------------
			
			//------- RR_SALES_PRISE_CHANGE - Start ---------------------------------------------------
			if(createDocType.equals(GSTNConstants.RR_SALES_PRISE_CHANGE)){
				if(existingInvoiceDetails.getServiceList().size() == invoiceDetails.getCnDnList().size()){
					considerAdditionalAmount = true;
				}	
			}
			//RR_SALES_PRISE_CHANGE - End ---------------------------------------------------
			
		}
		return considerAdditionalAmount;
	}
	
	private InvoiceDetails setAdditionalChargesOnEachLineItem(InvoiceDetails existingInvoiceDetails,String typeChange,InvoiceDetails invoiceDetails,int invLineItemSize,int cndnLineItemSize, boolean considerAdditionalAmount) {
		double additionalChargesValue = 0;
		//calculate total additional charge value - Start
		if((existingInvoiceDetails != null) && (existingInvoiceDetails.getAddChargesList() != null) && (existingInvoiceDetails.getAddChargesList().size() > 0)){
			for(InvoiceAdditionalChargeDetails aa : existingInvoiceDetails.getAddChargesList()){
				additionalChargesValue = additionalChargesValue + aa.getAdditionalChargeAmount();
			}
		}
		//calculate total additional charge value - End
		
		double preAmountAfterDiscount = 0;
		for(InvoiceServiceDetails existingService : existingInvoiceDetails.getServiceList()){
			preAmountAfterDiscount = preAmountAfterDiscount + (existingService.getPreviousAmount() - existingService.getOfferAmount());
		}		
				
				
		if(typeChange.equals(GSTNConstants.RR_SALES_RETURN) || typeChange.equals(GSTNConstants.RR_DISCOUNT_CHANGE) || typeChange.equals(GSTNConstants.RR_SALES_PRISE_CHANGE)){
			if(!considerAdditionalAmount){
				existingInvoiceDetails.setAddChargesList(null);
				existingInvoiceDetails.setCndnIsAdditionalChargePresent("N");
			}
		}else if(typeChange.equals(GSTNConstants.RR_QUANTITY_CHANGE)){
			for(InvoiceServiceDetails existingService : existingInvoiceDetails.getServiceList()){
				if(additionalChargesValue > 0){
					existingService.setAdditionalAmount(GSTNUtil.convertToDouble(additionalChargesValue * ((existingService.getPreviousAmount() - existingService.getOfferAmount()) / preAmountAfterDiscount)));
				}
			}
		}
		return existingInvoiceDetails;
	}

	public InvoiceDetails calculationOnCnDnInvoiceDetails(InvoiceDetails invoiceDetails) throws Exception{
		logger.info("Entry");
		double additionalChargesValue = 0;
		double preAmountAfterDiscount = 0;
		double amountAfterDiscount = 0;
		double totalTax = 0;
		double invoiceValue = 0;
		double invoiceValueAfterRoundOff = 0;
		try {
			
			//calculate total additional charge value - Start
			if((invoiceDetails.getAddChargesList() != null) && (invoiceDetails.getAddChargesList().size() > 0)){
				for(InvoiceAdditionalChargeDetails aa : invoiceDetails.getAddChargesList()){
					additionalChargesValue = additionalChargesValue + aa.getAdditionalChargeAmount();
				}
			}
			//calculate total additional charge value - End
			
			//calculate total amountAfterDiscount value - Start
			for(InvoiceServiceDetails bb : invoiceDetails.getServiceList()){
				preAmountAfterDiscount = preAmountAfterDiscount + (bb.getPreviousAmount() - bb.getOfferAmount());
			}
			//calculate total amountAfterDiscount value - End
			
			//set value for gstnStateId, DeliveryStateId, typeOfExport - Start 
			for(InvoiceServiceDetails bb : invoiceDetails.getServiceList()){
				bb.setGstnStateId(invoiceDetails.getGstnStateId());
				bb.setDeliveryStateId(invoiceDetails.getDeliveryPlace());
				bb.setTypeOfExport(invoiceDetails.getTypeOfExport());
				if(additionalChargesValue > 0){
					bb.setAdditionalAmount(GSTNUtil.convertToDouble(additionalChargesValue * ((bb.getPreviousAmount() - bb.getOfferAmount()) / preAmountAfterDiscount)));
				}
				bb = calculateCnDnTax(bb);
				
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
				
			//set value for Final Invoice Value - End
			
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		
		return invoiceDetails;
	}

	private InvoiceServiceDetails calculateCnDnTaxOnDiscountChange(InvoiceServiceDetails serviceTax) {
		logger.info("Entry");		
		try {			
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
			
			amountAfterDiscount = previousAmount - serviceTax.getAmountAfterDiscount();
				
				
				serviceTax.setHsnSacCode(serviceTax.getHsnSacCode());
				serviceTax.setHsnSacDescription(serviceTax.getHsnSacDescription());
				if((serviceTax.getTypeOfExport().equals(GSTNConstants.EXPORT_TYPE_WITH_BOND)) || (serviceTax.getTypeOfExport().equals(GSTNConstants.EXPORT_TYPE_WITH_IGST))){
					if(serviceTax.getTypeOfExport().equals(GSTNConstants.EXPORT_TYPE_WITH_IGST)){
						
						category = GSTNConstants.CATEGORY_EXPORT_WITH_IGST;
					}
				}else{					
					if(serviceTax.getDeliveryStateId() == serviceTax.getGstnStateId()){
						
							if(stateOrUnionTerritory.equals(GSTNConstants.STATE)){
								category = GSTNConstants.CATEGORY_WITH_SGST_CSGT;								
							}else{
								category = GSTNConstants.CATEGORY_WITH_UGST_CGST;
							}
													
					}else{
						category = GSTNConstants.CATEGORY_WITH_IGST;
					}
				}
					
			serviceTax.setPreviousAmount(Double.valueOf(new DecimalFormat("#.##").format(previousAmount)));
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
		} catch (NumberFormatException e) {
			logger.error("Error in:",e);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return serviceTax;
	}

	private InvoiceServiceDetails calculateCnDnTax(InvoiceServiceDetails serviceTax) {
		logger.info("Entry");		
		try {			
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
				
				serviceTax.setHsnSacCode(serviceTax.getHsnSacCode());
				serviceTax.setHsnSacDescription(serviceTax.getHsnSacDescription());
				if((serviceTax.getTypeOfExport().equals(GSTNConstants.EXPORT_TYPE_WITH_BOND)) || (serviceTax.getTypeOfExport().equals(GSTNConstants.EXPORT_TYPE_WITH_IGST))){
					if(serviceTax.getTypeOfExport().equals(GSTNConstants.EXPORT_TYPE_WITH_IGST)){
						igstAmount = ((serviceTax.getCnDnAppliedRate() * amountAfterDiscount) / 100);
						if(serviceTax.getDiffPercent().equals("Y")){
							taxAmount = ((Double.parseDouble(diffPercentageRatio) * igstAmount)/100);
						}else{
							taxAmount = igstAmount;
						}
						category = GSTNConstants.CATEGORY_EXPORT_WITH_IGST;
						igstPercent = serviceTax.getCnDnAppliedRate();
					}
				}else{					
					if(serviceTax.getDeliveryStateId() == serviceTax.getGstnStateId()){
						//get cgst + sgst/ugst
							double sgstOrUgstInRatio = (Double.parseDouble(sgstOrUgstRatio));
							double cgstInRatio = (Double.parseDouble(cgstRatio));							
							cgstPercent = ((cgstInRatio * serviceTax.getCnDnAppliedRate()) / 100);
							if(stateOrUnionTerritory.equals(GSTNConstants.STATE)){
								sgstPercent = ((sgstOrUgstInRatio * serviceTax.getCnDnAppliedRate()) / 100);
								sgstAmount = ((sgstPercent * amountAfterDiscount ) / 100);
								if(serviceTax.getDiffPercent().equals("Y")){
									sgstAmount = ((Double.parseDouble(diffPercentageRatio) * sgstAmount)/100);
								}
								category = GSTNConstants.CATEGORY_WITH_SGST_CSGT;								
							}else{
								ugstPercent = ((sgstOrUgstInRatio * serviceTax.getCnDnAppliedRate()) / 100);
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
						igstAmount = ((serviceTax.getCnDnAppliedRate() * amountAfterDiscount) / 100);
						if(serviceTax.getDiffPercent().equals("Y")){
							taxAmount = ((Double.parseDouble(diffPercentageRatio) * igstAmount)/100);
						}else{
							taxAmount = igstAmount;
						}
						category = GSTNConstants.CATEGORY_WITH_IGST;
						igstPercent = serviceTax.getCnDnAppliedRate();
					}
				}
				
				
			if(serviceTax.getCess() != 0){
				taxAmount = taxAmount + serviceTax.getCess();
			}	
			serviceTax.setPreviousAmount(Double.valueOf(new DecimalFormat("#.##").format(previousAmount)));
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
		} catch (NumberFormatException e) {
			logger.error("Error in:",e);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return serviceTax;
	}

	/**added by @author @kshay Mohite **/
	@RequestMapping(value = "/calculateTaxForPurchaseEntryPreview", method = RequestMethod.POST)
	public @ResponseBody String calculateTaxForPurchaseEntryPreview(@RequestBody @Valid PurchaseEntryDetails purchaseEntryDetails, BindingResult result, 
			HttpServletRequest httpRequest) {
		logger.info("Entry");		
		String amtInWords = null;
		CompletePurchaseEntry completePurchaseEntry = new CompletePurchaseEntry();
		String renderData = GSTNConstants.NOT_ALLOWED_ACCESS;
		if (!result.hasErrors()){
			try {
				renderData = purchaseEntryServerSideValidation(purchaseEntryDetails, httpRequest);
				completePurchaseEntry.setRenderData(renderData);
				logger.info("ACCESS : "+renderData);
				if(renderData.equals(GSTNConstants.ALLOWED_ACCESS)){
					purchaseEntryDetails = calculationOnPurchaseEntryDetails(purchaseEntryDetails);	
					amtInWords = AmountInWordsModified.convertNumberToWords(""+purchaseEntryDetails.getInvoiceValueAfterRoundOff());	
						
					//get user details
						LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
						UserMaster user = userMasterService.getUserMasterById(loginMaster.getuId());
						if(!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
							user = userMasterService.getUserMasterById(user.getReferenceId());
						}
						
					//set value in CompleteInvoice
						completePurchaseEntry.setPurchaseEntryDetails(purchaseEntryDetails);
						completePurchaseEntry.setAmtInWords(amtInWords);
						//completeInvoice.setCustomerStateCode(stateService.getStateByStateName(invoiceDetails.getCustomerDetails().getCustState()).getStateCode());
						completePurchaseEntry.setGstinDetails(gstinDetailsService.getGstinDetailsFromGstinNo(purchaseEntryDetails.getGstnStateIdInString(),loginMaster.getPrimaryUserUId()));
						completePurchaseEntry.setGstMap(GSTNUtil.convertListToMapForPurchaseEntry(purchaseEntryDetails.getServiceList()));
				}				
			} catch (Exception e) {
				logger.error("Error in:",e);
			}
		}else{
			completePurchaseEntry.setRenderData(renderData);
			logger.info("ACCESS DENIED");
			logger.error("Error in:"+result.getAllErrors());
		}		
		logger.info("Exit");
		return new Gson().toJson(completePurchaseEntry);
	}
	

	public String purchaseEntryServerSideValidation(PurchaseEntryDetails purchaseEntryDetails, HttpServletRequest httpRequest) throws Exception {
		logger.info("Entry");
		String renderData = GSTNConstants.NOT_ALLOWED_ACCESS;	
		boolean isServiceOrProductValid = false;
		boolean isAllowed = true;
		boolean isGstnNumberValid = false;
		
		//First check for services/products if it belongs to current organization or not - Start
		for(PurchaseEntryServiceOrGoodDetails servicegoodDetails : purchaseEntryDetails.getServiceList()){
			isServiceOrProductValid = validateValuesOfClientWithServer(servicegoodDetails, httpRequest);
			if(!isServiceOrProductValid){
				isAllowed = false;
				break;
			}
		}
		
		//check for gstn
		isGstnNumberValid = checkGstnNumberWithServerValues(purchaseEntryDetails.getGstnStateIdInString(),httpRequest);
				
		if(isAllowed && isGstnNumberValid){
			logger.info("ACCESS ALLOWED");
			renderData = GSTNConstants.ALLOWED_ACCESS;
		}
		logger.info("Exit");
		return renderData;
	}

	private boolean validateValuesOfClientWithServer(PurchaseEntryServiceOrGoodDetails servicegoodDetails, HttpServletRequest httpRequest) throws Exception {
		logger.info("Entry");
		boolean validateClientValues = false;
		boolean checkFirst = false;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		boolean isMappingValid = false;
		String idsValuesToFetch = null;
		boolean checkSecond = false;
		double ssamount = 0d;
		double csamount = 0d;
		
		//checkFirst -  previousAmount > offerAmount - Start
		if(servicegoodDetails.getPreviousAmount() <= servicegoodDetails.getOfferAmount()){
			checkFirst = true;
		}
		//checkFirst -  previousAmount > offerAmount - End
		
		//if checkFirst = false it means previousAmount > offerAmount and hence allow him to go ahead
		if(!checkFirst){
			//checkSecond - check if service or product belongs to current organization - Start
			idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			if(servicegoodDetails.getBillingFor().equals(GSTNConstants.PRODUCT)){
				isMappingValid = genericService.checkUserMappingValidation(idsValuesToFetch,getPrimIdsListQuery,servicegoodDetails.getServiceId(),"Product","referenceId");
				if(isMappingValid){
					logger.info("Product id - "+servicegoodDetails.getServiceId()+" belongs to current organization. Hence valid.");
					checkSecond = true;
				}				
			}else{
				isMappingValid = genericService.checkUserMappingValidation(idsValuesToFetch,getPrimIdsListQuery,servicegoodDetails.getServiceId(),"ManageServiceCatalogue","referenceId");
				if(isMappingValid){
					logger.info("Service id - "+servicegoodDetails.getServiceId()+" belongs to current organization. Hence valid.");
					checkSecond = true;
				}
			}			
			//checkSecond - check if service or product belongs to current organization - End
			
			//if checkSecond = true it means the product or service id belongs to that organization and hence allow him to go ahead
			if(checkSecond){
				/*if(servicegoodDetails.getCalculationBasedOn().equals(GSTNConstants.CALCUATION_BASED_ON_AMOUNT)){
					if(servicegoodDetails.getBillingFor().equals(GSTNConstants.PRODUCT)){
						Product product = productService.getProductById(servicegoodDetails.getServiceId());
						if(servicegoodDetails.getRate() == product.getProductRate()){
							ssamount = ((product.getProductRate())*(servicegoodDetails.getQuantity()));
							csamount = ((servicegoodDetails.getRate())*(servicegoodDetails.getQuantity()));
							BigDecimal ssbd = new BigDecimal(ssamount).setScale(4, BigDecimal.ROUND_DOWN);
							BigDecimal csbd = new BigDecimal(csamount).setScale(4, BigDecimal.ROUND_DOWN);
							BigDecimal pabd = new BigDecimal(servicegoodDetails.getPreviousAmount()).setScale(4, BigDecimal.ROUND_DOWN);
							if((ssbd.equals(csbd)) && (pabd.equals(ssbd))){
								validateClientValues = true;
							}
						}						
					}else{
						ManageServiceCatalogue service = manageServiceCatalogueService.getManageServiceCatalogueById(servicegoodDetails.getServiceId());
						if(servicegoodDetails.getRate() == service.getServiceRate()){
							ssamount = ((service.getServiceRate())*(servicegoodDetails.getQuantity()));
							csamount = ((servicegoodDetails.getRate())*(servicegoodDetails.getQuantity()));
							BigDecimal ssbd = new BigDecimal(ssamount).setScale(4, BigDecimal.ROUND_DOWN);
							BigDecimal csbd = new BigDecimal(csamount).setScale(4, BigDecimal.ROUND_DOWN);
							BigDecimal pabd = new BigDecimal(servicegoodDetails.getPreviousAmount()).setScale(4, BigDecimal.ROUND_DOWN);
							if((ssbd.equals(csbd)) && (pabd.equals(ssbd))){
								validateClientValues = true;
							}
						}						
					}					
				}else{
					//serviceOrProduct.setQuantity(1);
					logger.info("As CALCUATION_BASED_ON = "+GSTNConstants.CALCUATION_BASED_ON_LUMPSUM+", No need to validate for rate ,quantity and amount at server side ");
					validateClientValues = true;
				}*/	
				validateClientValues = true;
			}			
		}		
		logger.info("Exit");
		return validateClientValues;
	}

	public PurchaseEntryDetails calculationOnPurchaseEntryDetails(PurchaseEntryDetails purchaseEntryDetails) throws Exception{
		logger.info("Entry");
		double additionalChargesValue = 0;
		double preAmountAfterDiscount = 0;
		double amountAfterDiscount = 0;
		double totalTax = 0;
		double invoiceValue = 0;
		double invoiceValueAfterRoundOff = 0;
		try {			
			//calculate total additional charge value - Start
			if((purchaseEntryDetails.getAddChargesList() != null) && (!purchaseEntryDetails.getAddChargesList().isEmpty()))
			{
				for(PurchaseEntryAdditionalChargeDetails aa : purchaseEntryDetails.getAddChargesList()){
					additionalChargesValue = additionalChargesValue + aa.getAdditionalChargeAmount();
				}
			}
			//calculate total additional charge value - End
			
			//calculate total amountAfterDiscount value - Start
			for(PurchaseEntryServiceOrGoodDetails bb : purchaseEntryDetails.getServiceList()){
				preAmountAfterDiscount = preAmountAfterDiscount + (bb.getPreviousAmount() - bb.getOfferAmount());
			}
			//calculate total amountAfterDiscount value - End
			
			//set value for PlaceOfSupplyId, SupplierStateId & GstnStateId Start 
			for(PurchaseEntryServiceOrGoodDetails bb : purchaseEntryDetails.getServiceList())
			{
				bb.setGstnStateId(purchaseEntryDetails.getGstnStateId());
				bb.setSupplierStateId(Integer.parseInt(purchaseEntryDetails.getSupplierStateCodeId()));
				bb.setPlaceOfSupplyId(Integer.parseInt(purchaseEntryDetails.getPlaceOfSupplyId()));
				if(additionalChargesValue > 0){
					bb.setAdditionalAmount(GSTNUtil.convertToDouble(additionalChargesValue * ((bb.getPreviousAmount() - bb.getOfferAmount()) / preAmountAfterDiscount)));
				}
				if(bb.getBillingFor().equals(GSTNConstants.PRODUCT)){
					bb = calculateTaxForProductOfPurchaseEntry(bb);
				}else{
					bb = calculateTaxForServiceOfPurchaseEntry(bb);
				}
			}
			//set value for gstnStateId, SupplierStateId - End 			
			
			//set value for total amountAfterDiscount and total tax - Start
			for(PurchaseEntryServiceOrGoodDetails cc : purchaseEntryDetails.getServiceList()){
				amountAfterDiscount = amountAfterDiscount + (cc.getPreviousAmount() + cc.getAdditionalAmount() - cc.getOfferAmount());
				totalTax = totalTax + cc.getTaxAmount();
			}			
			//set value for total amountAfterDiscount and total tax - End
		
			//set value for Final Invoice Value - Start
				invoiceValue = amountAfterDiscount + totalTax;
				invoiceValueAfterRoundOff = Math.round(invoiceValue);				
				purchaseEntryDetails.setAmountAfterDiscount(GSTNUtil.convertToDouble(amountAfterDiscount));
				purchaseEntryDetails.setTotalTax(GSTNUtil.convertToDouble(totalTax));
				purchaseEntryDetails.setInvoiceValue(GSTNUtil.convertToDouble(invoiceValue));
				purchaseEntryDetails.setInvoiceValueAfterRoundOff(invoiceValueAfterRoundOff);				
			//set value for Final Invoice Value - End			
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");		
		return purchaseEntryDetails;
	}

	private PurchaseEntryServiceOrGoodDetails calculateTaxForServiceOfPurchaseEntry(PurchaseEntryServiceOrGoodDetails serviceTax) {
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
					if(serviceTax.getSupplierStateId() == serviceTax.getGstnStateId()){
					//if(serviceTax.getSupplierStateId() == serviceTax.getPlaceOfSupplyId()){			 //commented on 8/8/2018 on business requirement
						//get cgst + sgst/ugst
							double sgstOrUgstInRatio = (Double.parseDouble(sgstOrUgstRatio));
							double cgstInRatio = (Double.parseDouble(cgstRatio));
							
							cgstPercent = ((cgstInRatio * service.getServiceIgst()) / 100);
							if(stateOrUnionTerritory.equals(GSTNConstants.STATE)){
								sgstPercent = ((sgstOrUgstInRatio * service.getServiceIgst()) / 100);
								sgstAmount = ((sgstPercent * amountAfterDiscount ) / 100);
								category = GSTNConstants.CATEGORY_WITH_SGST_CSGT;
								
							}else{
								ugstPercent = ((sgstOrUgstInRatio * service.getServiceIgst()) / 100);
								ugstAmount = ((ugstPercent * amountAfterDiscount ) / 100);
								category = GSTNConstants.CATEGORY_WITH_UGST_CGST;
							}
							cgstAmount = ((cgstPercent * amountAfterDiscount ) / 100);
							taxAmount = sgstAmount + cgstAmount + ugstAmount;								
					}else{
						//get igst
						igstAmount = ((service.getServiceIgst() * amountAfterDiscount) / 100);
						taxAmount = igstAmount;
						category = GSTNConstants.CATEGORY_WITH_IGST;
						igstPercent = service.getServiceIgst();
					}
				}
				
			if(serviceTax.getCess() != 0){
				taxAmount = taxAmount + serviceTax.getCess();
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
		} catch (NumberFormatException e) {
			logger.error("Error in:",e);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return serviceTax;
	}

	private PurchaseEntryServiceOrGoodDetails calculateTaxForProductOfPurchaseEntry(PurchaseEntryServiceOrGoodDetails serviceTax) {
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
				if(serviceTax.getSupplierStateId() == serviceTax.getGstnStateId()){
				//if(serviceTax.getSupplierStateId() == serviceTax.getPlaceOfSupplyId()){			 //commented on 8/8/2018 on business requirement 
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
			}
			
			if(serviceTax.getCess() != 0){
				taxAmount = taxAmount + serviceTax.getCess();
			}
			serviceTax.setPreviousAmount( Double.valueOf(new DecimalFormat("#.##").format(previousAmount)));
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
		} catch (NumberFormatException e) {
			logger.error("Error in:",e);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Entry");
		return serviceTax;
	}
	
}
