package com.reliance.gstn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.admin.model.AdditionalChargeDetails;
import com.reliance.gstn.admin.service.AdditionalChargeDetailsService;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.InventoryProductSave;
import com.reliance.gstn.model.InventoryProductTable;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.InvoiceServiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.ManageServiceCatalogue;
import com.reliance.gstn.model.Product;
import com.reliance.gstn.model.PurchaseEntryAdditionalChargeDetails;
import com.reliance.gstn.model.PurchaseEntryDetails;
import com.reliance.gstn.model.PurchaseEntryServiceOrGoodDetails;
import com.reliance.gstn.model.State;
import com.reliance.gstn.model.UnitOfMeasurement;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.GSTINDetailsService;
import com.reliance.gstn.service.InventoryService;
import com.reliance.gstn.service.ManageServiceCatalogueService;
import com.reliance.gstn.service.ProductService;
import com.reliance.gstn.service.PurchaseEntryService;
import com.reliance.gstn.service.StateService;
import com.reliance.gstn.service.UnitOfMeasurementService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.AmountInWords;
import com.reliance.gstn.util.AmountInWordsModified;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;
import com.reliance.gstn.util.UOMUtil;
/**
 * @author @kshay Mohite
 *
 */
@Controller
public class PurchaseEntryInvoiceController {

	private static final Logger logger = Logger.getLogger(PurchaseEntryInvoiceController.class);
	
	@Autowired
	StateService stateService;
	
	@Autowired
	private UserMasterService userMasterService;

	@Autowired
	private GSTINDetailsService gstinDetailsService;

	@Autowired
	private TaxCalculationController taxCalculation;
	
	@Autowired
	private PurchaseEntryService purchaseEntryService;

	@Autowired
	public AdditionalChargeDetailsService additionalChargeDetailsService;
	
	@Autowired
	public ManageServiceCatalogueService manageServiceCatalogueService;
	
	@Autowired
	private ProductService productService;

	@Autowired
	public UnitOfMeasurementService unitOfMeasurementService;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	InventoryController inventoryController;
	
	
	@RequestMapping(value = "/generatePurchaseEntryInvoice", method = RequestMethod.GET)
	public String getGenerateInvoicePage(Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String pageRedirect = null;
		boolean isGstinPresent = false;
		State state = new State();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
			UserMaster user = userMasterService.getUserMasterById( loginMaster.getuId());
			if(user.getOrganizationMaster() != null &&  user.getOrganizationMaster().getState() != null){
				state = stateService.getStateByStateName(user.getOrganizationMaster().getState());	
				if(state != null){
					user.getOrganizationMaster().setStateCode(state.getStateCode());
				}
			}
			List<GSTINDetails> gstinList = new ArrayList<GSTINDetails>();
			//check if gstin is present - Start
			//if user is primary redirect to add gstin page
			//if user is secondary show message Ask your concerned primary member to map gstin to your account
			if(loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
				gstinList =  gstinDetailsService.listGstinDetails(loginMaster.getuId());
			}else{
				/*String gstinUserMapped = gstinUserMappingService.getGSTINUserMappingByReferenceUserId(loginMaster.getuId());
				if(gstinUserMapped != null){
					gstinList = gstinDetailsService.getGstinDetailsByUniqueIds(gstinUserMapped);
				}*/
				gstinList = gstinDetailsService.getGstinDetailsMappedForSecondaryUser(loginMaster.getuId());
				user = userMasterService.getUserMasterById(user.getReferenceId());
			}
			//check if gstin is present - End
			
			if(gstinList != null && gstinList.size() > 0){
				isGstinPresent = true;
			}
			
			if(isGstinPresent && (loginMaster.getTermsConditionsFlag() != null)){
				if(loginMaster.getTermsConditionsFlag().toUpperCase().equals("Y")){
					pageRedirect = PageRedirectConstants.GENERATE_PURCHASE_ENTRY_INVOICE_PAGE;
				}else{
					pageRedirect = PageRedirectConstants.GENERATE_PURCHASE_ENTRY_INVOICE_EXCEPTION_PAGE;
				}
				
			}else{
				pageRedirect = PageRedirectConstants.GENERATE_PURCHASE_ENTRY_INVOICE_EXCEPTION_PAGE;
			}
			
			model.addAttribute("userDetails", user);
			model.addAttribute("isGstinPresent", isGstinPresent);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return pageRedirect;
	}

	@RequestMapping(value = "/addGeneratedPurchaseEntry", method = RequestMethod.POST)
	public @ResponseBody String addGeneratedPurchaseEntry(@RequestBody @Valid PurchaseEntryDetails purchaseEntryDetails, BindingResult result, HttpServletRequest httpRequest) {
		logger.info("Entry");
		Map<String, String> mapResponse = new HashMap<String, String>();
		String renderData = null;
		
		if (!result.hasErrors()){
			try {
				renderData = taxCalculation.purchaseEntryServerSideValidation(purchaseEntryDetails, httpRequest);
				logger.info("ACCESS : "+renderData);
				if(renderData.equals(GSTNConstants.ALLOWED_ACCESS)){
					
					LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
					purchaseEntryDetails = taxCalculation.calculationOnPurchaseEntryDetails(purchaseEntryDetails);
					if(!purchaseEntryDetails.getPurchaseDateInString().equals("") ){
						purchaseEntryDetails.setPurchaseDate(GSTNUtil.convertStringInddMMyyyyToTimestamp(purchaseEntryDetails.getPurchaseDateInString()));
					}
					if(!purchaseEntryDetails.getInvoicePeriodFromDateInString().equals("") ){
						purchaseEntryDetails.setInvoicePeriodFromDate(GSTNUtil.convertStringInddMMyyyyToTimestamp(purchaseEntryDetails.getInvoicePeriodFromDateInString()));
					}
					if(!purchaseEntryDetails.getInvoicePeriodToDateInString().equals("")){
						purchaseEntryDetails.setInvoicePeriodToDate(GSTNUtil.convertStringInddMMyyyyToTimestamp(purchaseEntryDetails.getInvoicePeriodToDateInString()));
					}
					if(purchaseEntryDetails.getReverseChargeApplicable().equalsIgnoreCase("Yes") && !purchaseEntryDetails.getSupplierDocInvoiceDateInString().equals("") || 
							purchaseEntryDetails.getSupplierDocInvoiceDateInString() != null){
						purchaseEntryDetails.setSupplierDocInvoiceDate(GSTNUtil.convertStringInddMMyyyyToTimestamp(purchaseEntryDetails.getSupplierDocInvoiceDateInString()));
					}
					purchaseEntryDetails.setCreatedBy(loginMaster.getuId().toString());
					purchaseEntryDetails.setStatus("1");
					purchaseEntryDetails.setReferenceId(loginMaster.getuId());
					purchaseEntryDetails.setOrgUId(loginMaster.getOrgUId());
					purchaseEntryDetails.setPurchaseEntryGeneratedInvoiceNumber(generatePurchaseEntryInvoiceSequence(purchaseEntryDetails.getGstnStateIdInString(),loginMaster,purchaseEntryDetails.getLocation()));
					purchaseEntryDetails = setTextFieldsForServicesAndAdditionalCharges(purchaseEntryDetails);
					mapResponse = purchaseEntryService.addGeneratePurchaseEntryInvoice(purchaseEntryDetails);
					
					if(mapResponse.get(GSTNConstants.RESPONSE).equals(GSTNConstants.SUCCESS)){
						purchaseEntryDetails = checktoAddInInventoryOrNot(purchaseEntryDetails,loginMaster);
						Integer uniqSeq = inventoryController.getSessionUniqueSequence(loginMaster, httpRequest);
						InventoryProductSave inventoryProductSave = callInvertoryProductChangesInPurchaseEntry(purchaseEntryDetails,mapResponse.get("InvoiceNumber"),uniqSeq);
						if(inventoryProductSave.getProductList().size() > 0){
							Map<String,Object> invSave = inventoryService.saveInventoryDetails(inventoryProductSave, loginMaster.getuId());
						}
						
						/*sendMailUsingWorkerThread(mapResponse.get("InvoicePkId"),mapResponse.get("InvoiceNumber"),httpRequest,logoImagePath);*/
					}
				}else{
					mapResponse.put(GSTNConstants.RESPONSE, renderData);
				}
				
			} catch (Exception e) {
				logger.error("Backend Error in:",e);
				mapResponse.put(GSTNConstants.RESPONSE, GSTNConstants.SERVER_SIDE_ERROR);
			}
		}else{
			System.out.println("Error occured"+result.getAllErrors());
			logger.error("Error in:"+result.getAllErrors());
			mapResponse.put(GSTNConstants.RESPONSE, GSTNConstants.SERVER_SIDE_ERROR);
			//mapResponse.put(GSTNConstants.RESPONSE, GSTNConstants.NOT_ALLOWED_ACCESS);
		}
		logger.info("Exit");
		return new Gson().toJson(mapResponse);
	}
	
	private PurchaseEntryDetails checktoAddInInventoryOrNot(PurchaseEntryDetails purchaseEntryDetails,LoginMaster loginMaster) throws Exception {
		Product product = null;
		for(PurchaseEntryServiceOrGoodDetails items : purchaseEntryDetails.getServiceList()){
			if(items.getBillingFor().equals("Product")){
				product = productService.getProductById(items.getServiceId());
				if(product != null){

					if (product.getInventoryUpdateFlag().equals("N") && product.getOpeningStock().equals(0.0)){
						//
						Double currStock = (Double)items.getQuantity();
						Double currStockVal = (Double)(currStock * product.getPurchaseRate()); 
						product.setOpeningStock(currStock);
						product.setOpeningStockValue(currStockVal);
						product.setCurrentStock(currStock);
						product.setCurrentStockValue(currStockVal);
						product.setUpdatedBy(loginMaster.getuId()+"");
						productService.updateProductThroughPurchaseEntry(product);
					}else{
						items.setAddInInventory("Y");
					}
					
				}
			}
		}
		return purchaseEntryDetails;
	}

	private InventoryProductSave callInvertoryProductChangesInPurchaseEntry(PurchaseEntryDetails purchaseEntryDetails,String invoiceNumber,Integer uniqSeq) throws Exception {
		List<InventoryProductTable> productList = new ArrayList<InventoryProductTable>();
		InventoryProductTable productTable = null;
		Product product = null;
		InventoryProductSave inventoryProduct = new InventoryProductSave();
		inventoryProduct.setUniqueSequenceid(uniqSeq);
		inventoryProduct.setInventoryType(GSTNConstants.PURCHASE_ENTRY_CREDIT);
		inventoryProduct.setNarration("PURCHASE ENTRY");
		inventoryProduct.setReason("Created for PurchaseEntry number : "+invoiceNumber);
		for(PurchaseEntryServiceOrGoodDetails items : purchaseEntryDetails.getServiceList()){
			if(items.getBillingFor().equals("Product") && items.getAddInInventory().equals("Y")){
				product = productService.getProductById(items.getServiceId());
				if(product != null){
					Double dbCurrentStock = product.getCurrentStock();
					Double dbRate = product.getPurchaseRate();
					Double currentStock = dbCurrentStock + (Double)items.getQuantity();
					Double currentStockValue = currentStock * dbRate;
					productTable = new InventoryProductTable();
					productTable.setId(items.getServiceId());
					productTable.setCurrentStock(currentStock);
					productTable.setCurrentStockValue(currentStockValue);
					productTable.setModifiedQty((Double)items.getQuantity());
					productTable.setModifiedStockValue((Double)items.getQuantity()* dbRate);
					productTable.setName(product.getName());
					
					productTable.setStoreId(product.getStoreId());
					/*productTable.setFromStoreId(fromStoreId);
					productTable.setToStoreId(toStoreId);*/
					productTable.setUnitOfMeasurement(product.getUnitOfMeasurement());
					productTable.setOtherUOM("");
					productTable.setTransactionDate(purchaseEntryDetails.getPurchaseDateInString());
					productTable.setActionFrom(GSTNConstants.PURCHASE_ENTRY_CREDIT);
					productList.add(productTable);
				}
				
			}
		}
		inventoryProduct.setProductList(productList);
		// TODO Auto-generated method stub
		System.out.println("Final JSON from invoice : "+new Gson().toJson(inventoryProduct));
		return inventoryProduct;
	}
	
	private String generatePurchaseEntryInvoiceSequence(String gstinNo, LoginMaster loginMaster, String location) {
		logger.info("Entry");
		StringBuffer invoiceNumberSB = new StringBuffer();
		GSTINDetails gstinDetails = null;
		try{
			gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(gstinNo,loginMaster.getPrimaryUserUId());
			invoiceNumberSB.append(GSTNUtil.getCurrentYearAndCurrentMonth());
			invoiceNumberSB.append(gstinDetails.getUniqueSequence());
			//invoiceNumberSB.append(loginMaster.getUniqueSequence());
			invoiceNumberSB.append(location);
			
			//get last invoice number created by current employee - Start
			logger.info("Check for invoice number for "+invoiceNumberSB);
			String latestInvoiceCount = purchaseEntryService.getLatestInvoiceNumber(invoiceNumberSB.toString(),loginMaster.getOrgUId());
			String count = null;
			if(latestInvoiceCount == null){
				count = "1";
			}else{
				count = GSTNUtil.incrementInvoiceCount(latestInvoiceCount);
			}
			//get last invoice number created by current employee - End
			invoiceNumberSB.append(count);
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return invoiceNumberSB.toString();
	}

	private PurchaseEntryDetails setTextFieldsForServicesAndAdditionalCharges(PurchaseEntryDetails purchaseEntryDetails) throws Exception {
		logger.info("Entry");
		//Additional Charges - Start
		AdditionalChargeDetails addChargeDetailsObj = null;
		if(purchaseEntryDetails.getAddChargesList() != null && purchaseEntryDetails.getAddChargesList().size() > 0){
			for(PurchaseEntryAdditionalChargeDetails addChg : purchaseEntryDetails.getAddChargesList()){
				addChargeDetailsObj = additionalChargeDetailsService.getAdditionalChargeDetailsById(addChg.getAdditionalChargeId());
				addChg.setAdditionalChargeName(addChargeDetailsObj.getChargeName());
				//addChg.setAdditionalChargeAmount(addChargeDetailsObj.getChargeValue());
			}	
		}
		//Additional Charges - End
		
		//Service/Product - Start
		Product productObj = null;
		ManageServiceCatalogue manageServiceCatalogueObj = null;
		if(purchaseEntryDetails.getServiceList() != null && purchaseEntryDetails.getServiceList().size() > 0){
			for(PurchaseEntryServiceOrGoodDetails serviceOrProduct : purchaseEntryDetails.getServiceList()){
				if(serviceOrProduct.getBillingFor().equals(GSTNConstants.PRODUCT)){
					productObj = productService.getProductById(serviceOrProduct.getServiceId());
					serviceOrProduct.setServiceIdInString(productObj.getName());
					serviceOrProduct.setHsnSacCode(productObj.getHsnCode());
					serviceOrProduct.setUnitOfMeasurement(productObj.getUnitOfMeasurement());					
				}else{
					manageServiceCatalogueObj = manageServiceCatalogueService.getManageServiceCatalogueById(serviceOrProduct.getServiceId());
					serviceOrProduct.setServiceIdInString(manageServiceCatalogueObj.getName());
					serviceOrProduct.setHsnSacCode(manageServiceCatalogueObj.getSacCode());
					serviceOrProduct.setUnitOfMeasurement(manageServiceCatalogueObj.getUnitOfMeasurement());
				}
			}
		}		
		//Service/Product - End		
		logger.info("Exit");
		return purchaseEntryDetails;
	}

	@RequestMapping(value = "/getMyPurchaseEntryPage", method = RequestMethod.GET)
	public String getMyPurchaseEntryInvoicesPage(Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.GET_MY_PURCHASE_ENTRIES_PAGE;
		logger.info("Exit");
		return pageRedirect;
	}

	@RequestMapping(value="/getPurchaseEntriesDetailList", method = RequestMethod.POST)
	public @ResponseBody String getPurchaseEntriesDetailList(HttpServletRequest httpRequest){
		logger.info("Entry");
		List<Object[]> purchaseEntriesList = null;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			purchaseEntriesList = purchaseEntryService.getPurchaseEntriesDetailByOrgUId(loginMaster.getOrgUId());
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return new Gson().toJson(purchaseEntriesList);
	}
	
	@RequestMapping(value = {"/getPurchaseEntryDetails"},method=RequestMethod.POST)
	public String getInvoiceDetailsById(@RequestParam Integer id, HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		String uri = GSTNUtil.getLastURIPart(httpRequest.getRequestURI()); 
		String pageRedirect = PageRedirectConstants.VIEW_PURCHASE_ENTRY_INVOICE_PAGE;
		PurchaseEntryDetails purchaseEntryDetails = null;
		GSTINDetails gstinDetails = null;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
		UserMaster user = null;
		Map<String,Map<String,Double>> gstMap = new HashMap<String,Map<String,Double>>();
		String supplierCompleteAddress = null;
		State state = new State();
		String supplierStateCode = "";
		try {
			boolean isInvoiceAllowed = purchaseEntryService.validateInvoiceAgainstOrgId(id,loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				user = userMasterService.getUserMasterById( loginMaster.getuId());
				if(!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
					user = userMasterService.getUserMasterById(user.getReferenceId());
				}
				/*if(user.getOrganizationMaster().getState() != null){
					state = stateService.getStateByStateName(user.getOrganizationMaster().getState());	
					if(state != null){
						user.getOrganizationMaster().setStateCode(state.getStateCode());
					}
				}*/
				
				purchaseEntryDetails = purchaseEntryService.getPurchaseEntryInvoiceDetailsById(id);
				if(purchaseEntryDetails.getSupplierAddress().isEmpty() || purchaseEntryDetails.getSupplierAddress() == null ){
					supplierCompleteAddress = purchaseEntryDetails.getSupplierState()+" [ "+purchaseEntryDetails.getSupplierStateCodeId()+" ]"
							+" , "+purchaseEntryDetails.getSupplierPincode();
				}else{
					supplierCompleteAddress = purchaseEntryDetails.getSupplierAddress()+" , "+purchaseEntryDetails.getSupplierState()
							+" [ "+purchaseEntryDetails.getSupplierStateCodeId()+" ]"+" , "+purchaseEntryDetails.getSupplierPincode();
				}				
				if(purchaseEntryDetails.getSupplierGstin() == null || purchaseEntryDetails.getSupplierGstin().isEmpty()){
					purchaseEntryDetails.setSupplierGstin("NA");
				}
				gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(purchaseEntryDetails.getGstnStateIdInString(),loginMaster.getPrimaryUserUId());
				state = stateService.getStateByStateName(gstinDetails.getGstinAddressMapping().getState());	
				if(state.getStateCode() != null){
					supplierStateCode = state.getStateCode();
				}
				gstMap = GSTNUtil.convertListToMapForPurchaseEntry(purchaseEntryDetails.getServiceList());
			}	
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		model.addAttribute("purchaseEntryDetails", purchaseEntryDetails);
		model.addAttribute("supplierCompleteAddress", supplierCompleteAddress);
		model.addAttribute("gstinDetails", gstinDetails);
		model.addAttribute("userMaster", user);
		model.addAttribute("gstMap", gstMap);
		model.addAttribute("amtInWords", AmountInWordsModified.convertNumberToWords(""+purchaseEntryDetails.getInvoiceValueAfterRoundOff()));
		model.addAttribute("supplierStateCode", supplierStateCode);
		logger.info("Exit");
		return pageRedirect;
	}
	

	@RequestMapping(value = "/delPurchaseEntryInv",method=RequestMethod.POST)
	public @ResponseBody String deleteInvoice(@RequestParam Integer id, HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		boolean isInvoiceAllowed = false;	
		Map<String,String> mapResponse = new HashMap<String,String>();
		System.out.println("delete invoice for ID : "+id);
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
		try {
			PurchaseEntryDetails purchaseEntryDetails = purchaseEntryService.getPurchaseEntryInvoiceDetailsById(id);			
			isInvoiceAllowed = purchaseEntryService.validateInvoiceAgainstOrgId(id,loginMaster.getOrgUId());
			if(isInvoiceAllowed){				
				if (!UOMUtil.isMapLoaded()) {
					List<UnitOfMeasurement> unitOfMeasurementList = (List<UnitOfMeasurement>) unitOfMeasurementService.getUnitOfMeasurement();
					UOMUtil.setUOMMap(unitOfMeasurementList);
				}
				
				String response = purchaseEntryService.deletePurchaseEntryInvoice(purchaseEntryDetails.getPurchaseEntryDetailsId());
				
				if(response.equalsIgnoreCase("Success")){
					model.addAttribute("apiResponse",response);
					mapResponse.put(GSTNConstants.STATUS, GSTNConstants.SUCCESS);
					mapResponse.put(GSTNConstants.RESPONSE, "Purchase Entry is deleted successfully");
				}else if(response.equalsIgnoreCase("emptyList")){
					model.addAttribute("apiResponse",response);
				}else{
					model.addAttribute("apiResponse","error");
					mapResponse.put(GSTNConstants.STATUS, GSTNConstants.FAILURE);
					mapResponse.put(GSTNConstants.RESPONSE, "Error while deleting Purchase Entry");
				}				
			}else{
				mapResponse.put(GSTNConstants.STATUS, GSTNConstants.ACCESSVIOLATION);
				mapResponse.put(GSTNConstants.RESPONSE, "You do not have access to delete this Purchase Entry.");
			}			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}	
		logger.info("Exit");
		return new Gson().toJson(mapResponse);
	}
	
}
