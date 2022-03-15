/**
 * 
 */
package com.reliance.gstn.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.reliance.gstn.admin.model.AdditionalChargeDetails;
import com.reliance.gstn.admin.service.AdditionalChargeDetailsService;
import com.reliance.gstn.model.CnDnAdditionalDetails;
import com.reliance.gstn.model.CompleteInvoice;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.GstinLocation;
import com.reliance.gstn.model.InventoryProductSave;
import com.reliance.gstn.model.InventoryProductTable;
import com.reliance.gstn.model.InvoiceAdditionalChargeDetails;
import com.reliance.gstn.model.InvoiceCnDnDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.InvoiceServiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.ManageServiceCatalogue;
import com.reliance.gstn.model.Product;
import com.reliance.gstn.model.ReviseAndReturnHistory;
import com.reliance.gstn.model.State;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.GSTINDetailsService;
import com.reliance.gstn.service.GenerateInvoiceService;
import com.reliance.gstn.service.InventoryService;
import com.reliance.gstn.service.ManageServiceCatalogueService;
import com.reliance.gstn.service.ProductService;
import com.reliance.gstn.service.ReviseAndReturnService;
import com.reliance.gstn.service.StateService;
import com.reliance.gstn.service.UnitOfMeasurementService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.AmountInWordsModified;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.GenerateInvoicePdf;
//import com.reliance.gstn.util.LoggerUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;
import com.reliance.gstn.util.SendEmailWithPdf;


/**
 * @author Nikesh.Bansod
 *
 */
@Controller
public class GenerateInvoiceController {
	
	private static final Logger logger = Logger.getLogger(GenerateInvoiceController.class);
	
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
	
	@Autowired
	GenerateInvoiceService generateInvoiceService;
	
	@Autowired
	private UserMasterService userMasterService;
	
	@Autowired
	private GSTINDetailsService gstinDetailsService;
		
	@Autowired
	private StateService stateService;
	
	@Autowired
	public UnitOfMeasurementService unitOfMeasurementService;
	
	@Value("${${env}.PATH_FOR_INVOICE_PDF}")
	private String invoiceDirectoryPath;
	
	@Value("${${env}.EMAIL_FROM}")
	private String emailFrom;
	
	@Value("${${env}.EMAIL_SMTP_HOSTNAME}")
	private String emailSmtpHostName;
	
	@Autowired
	private TaxCalculationController taxCalculation;
	
	@Autowired
	public AdditionalChargeDetailsService additionalChargeDetailsService;
	
	@Autowired
	public ManageServiceCatalogueService manageServiceCatalogueService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	ReviseAndReturnService reviseAndReturnService;
	
	@Autowired
	InventoryController inventoryController;
	
	@RequestMapping(value = {"/generateInvoice","/createInvoice","/createBillOfSupply","/createRCInvoice","/createEComInvoice","/createEComBillOfSupply"}, method = RequestMethod.GET)
	public String getGenerateInvoicePage(Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String pageRedirect = null;
		boolean isGstinPresent = false;
		String uri = GSTNUtil.getLastURIPart(httpRequest.getRequestURI());
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
			UserMaster user = userMasterService.getUserMasterById( loginMaster.getuId());
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
					
					if(uri.equals("/generateInvoice")){
						pageRedirect = PageRedirectConstants.GENERATE_INVOICE_PAGE;
					}else if(uri.equals("/createInvoice")){
						model.addAttribute("documentType", "invoice");
						pageRedirect = PageRedirectConstants.CREATE_INVOICE_PAGE;
					}else if(uri.equals("/createBillOfSupply")){
						model.addAttribute("documentType", "billOfSupply");
						pageRedirect = PageRedirectConstants.CREATE_INVOICE_PAGE;
					}else if(uri.equals("/createRCInvoice")){
						model.addAttribute("documentType", "rcInvoice");
						pageRedirect = PageRedirectConstants.CREATE_INVOICE_PAGE;
					}else if(uri.equals("/createEComInvoice")){
						model.addAttribute("documentType", "eComInvoice");
						pageRedirect = PageRedirectConstants.CREATE_INVOICE_PAGE;
					}else if(uri.equals("/createEComBillOfSupply")){
						model.addAttribute("documentType", "eComBillOfSupply");
						pageRedirect = PageRedirectConstants.CREATE_INVOICE_PAGE;
					}
				}else{
					pageRedirect = PageRedirectConstants.GENERATE_INVOICE_EXCEPTION_PAGE;
				}
				
			}else{
				pageRedirect = PageRedirectConstants.GENERATE_INVOICE_EXCEPTION_PAGE;
			}
			
			model.addAttribute("userDetails", user);
			model.addAttribute("isGstinPresent", isGstinPresent);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return pageRedirect;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/addGeneratedInvoice", method = RequestMethod.POST)
	public @ResponseBody String addGeneratedInvoice(@RequestBody @Valid InvoiceDetails invoiceDetails, BindingResult result, HttpServletRequest httpRequest) {
		logger.info("Entry");
		Map<String, String> mapResponse = new HashMap<String, String>();
		String renderData = null;
		
		if (!result.hasErrors()){
			try {
				renderData = taxCalculation.invoiceServerSideValidation(invoiceDetails, httpRequest);
				logger.info("ACCESS : "+renderData);
				if(renderData.equals(GSTNConstants.ALLOWED_ACCESS)){
					
					LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
					String logoImagePath = userMasterService.getOrgLogoPath(loginMaster.getOrgUId());
					invoiceDetails = taxCalculation.calculationOnInvoiceDetails(invoiceDetails);
					invoiceDetails.setInvoiceDate(GSTNUtil.convertStringInddMMyyyyToTimestamp(invoiceDetails.getInvoiceDateInString()));
					if(!invoiceDetails.getInvoicePeriodFromDateInString().equals("") ){
						invoiceDetails.setInvoicePeriodFromDate(GSTNUtil.convertStringInddMMyyyyToTimestamp(invoiceDetails.getInvoicePeriodFromDateInString()));
					}
					if(!invoiceDetails.getInvoicePeriodToDateInString().equals("")){
						invoiceDetails.setInvoicePeriodToDate(GSTNUtil.convertStringInddMMyyyyToTimestamp(invoiceDetails.getInvoicePeriodToDateInString()));
					}
					invoiceDetails.setCreatedBy(loginMaster.getuId().toString());
					invoiceDetails.setStatus("1");
					invoiceDetails.setReferenceId(loginMaster.getuId());
					invoiceDetails.setOrgUId(loginMaster.getOrgUId());
					invoiceDetails.setGeneratedThrough(loginMaster.getLoggedInThrough());
					//invoiceDetails.setInvoiceNumber(generateInvoiceSequenceOld(loginMaster,invoiceDetails.getInvoiceDateInString(),invoiceDetails.getInvoiceFor()));
					//invoiceDetails.setInvoiceNumber(generateInvoiceSequence(invoiceDetails.getGstnStateIdInString(),loginMaster,invoiceDetails.getLocation()));
					if(loginMaster.getInvoiceSequenceType().equals("Auto")){
						invoiceDetails.setInvoiceNumber(generateInvoiceSequenceXLogic("S",invoiceDetails.getInvoiceDateInString(),invoiceDetails.getGstnStateIdInString(),loginMaster,invoiceDetails.getLocation()));
					}else{
						invoiceDetails.setInvoiceNumber(invoiceDetails.getInvoiceNumber());
					}
					invoiceDetails = setTextFieldsForServicesAndAdditionalCharges(invoiceDetails);
					mapResponse = generateInvoiceService.addGenerateInvoice(invoiceDetails);
					if(mapResponse.get(GSTNConstants.RESPONSE).equals(GSTNConstants.SUCCESS)){
						Integer uniqSeq = inventoryController.getSessionUniqueSequence(loginMaster, httpRequest);
						InventoryProductSave inventoryProductSave = callInvertoryProductChanges(invoiceDetails,mapResponse.get("InvoiceNumber"),uniqSeq,invoiceDetails.getInvoiceDateInString());
						if(inventoryProductSave.getProductList().size() > 0){
							Map<String,Object> invSave = inventoryService.saveInventoryDetails(inventoryProductSave, loginMaster.getuId());
						}
						
						sendMailUsingWorkerThread(mapResponse.get("InvoicePkId"),mapResponse.get("InvoiceNumber"),httpRequest,logoImagePath);
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
		}
		logger.info("Exit");
		return new Gson().toJson(mapResponse);
	}
	
	
	

	public void sendMailUsingWorkerThread(String InvoicePkId, String InvoiceNumber, HttpServletRequest httpRequest,String logoImagePath) {
		
		Thread r = new WorkerThread(InvoicePkId,InvoiceNumber,httpRequest,logoImagePath);
		r.start();
		
	}

	private InventoryProductSave callInvertoryProductChanges(InvoiceDetails invoiceDetails,String invoiceNumber,Integer uniqueSequence,String transactionDate) throws Exception {
		List<InventoryProductTable> productList = new ArrayList<InventoryProductTable>();
		InventoryProductTable productTable = null;
		Product product = null;
		InventoryProductSave inventoryProduct = new InventoryProductSave();
		inventoryProduct.setUniqueSequenceid(uniqueSequence);
		inventoryProduct.setInventoryType(GSTNConstants.INVOICE_DEBIT);
		inventoryProduct.setNarration(GSTNUtil.getInvoiceDocumentType(invoiceDetails.getDocumentType()));
		inventoryProduct.setReason("Created for invoice number : "+invoiceNumber);
		for(InvoiceServiceDetails items : invoiceDetails.getServiceList()){
			if(items.getBillingFor().equals("Product")){
				product = productService.getProductById(items.getServiceId());
				if(product != null){
					Double dbCurrentStock = product.getCurrentStock();
					Double dbRate = product.getPurchaseRate();
					Double currentStock = dbCurrentStock - (Double)items.getQuantity();
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
					productTable.setTransactionDate(transactionDate);
					productTable.setActionFrom(GSTNConstants.INVOICE_DEBIT);
					productList.add(productTable);
				}
				
			}
		}
		inventoryProduct.setProductList(productList);
		// TODO Auto-generated method stub
		System.out.println("Final JSON from invoice : "+new Gson().toJson(inventoryProduct));
		return inventoryProduct;
	}

	/*
	 * 
	 * This method is created for assigning text field data like service description text , sac code ,UOM which is set from front-end.
	 * So here we shall be assigning text from backend and not from front-end
	 * The issue was raised by Poonam - INFOSEC
	 * */
	public InvoiceDetails setTextFieldsForServicesAndAdditionalCharges(InvoiceDetails invoiceDetails) throws Exception {
		logger.info("Entry");
		//Additional Charges - Start
		AdditionalChargeDetails addChargeDetailsObj = null;
		if(invoiceDetails.getAddChargesList() != null && invoiceDetails.getAddChargesList().size() > 0){
			for(InvoiceAdditionalChargeDetails addChg : invoiceDetails.getAddChargesList()){
				addChargeDetailsObj = additionalChargeDetailsService.getAdditionalChargeDetailsById(addChg.getAdditionalChargeId());
				addChg.setAdditionalChargeName(addChargeDetailsObj.getChargeName());
				//addChg.setAdditionalChargeAmount(addChargeDetailsObj.getChargeValue());
			}	
		}
		//Additional Charges - End
		
		//Service/Product - Start
		Product productObj = null;
		ManageServiceCatalogue manageServiceCatalogueObj = null;
		if(invoiceDetails.getServiceList() != null && invoiceDetails.getServiceList().size() > 0){
			for(InvoiceServiceDetails serviceOrProduct : invoiceDetails.getServiceList()){
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
		return invoiceDetails;
	}
	
	public String generateInvoiceSequenceXLogic(String generatedFrom,String invoiceDateInString, String gstinNo,LoginMaster loginMaster, String location) {
		logger.info("Entry");
		StringBuffer invoiceNumberSB = new StringBuffer();
		invoiceNumberSB.append(generatedFrom);
		invoiceNumberSB.append(GSTNUtil.convertStringDateToOtherStringFormat(invoiceDateInString,"dd-MM-yyyy","yyMM"));
		invoiceNumberSB.append("/1/");
		try{
			List<GSTINDetails> gstinList = gstinDetailsService.listGstinDetails(loginMaster.getPrimaryUserUId());
			GSTINDetails gstinDetails = null;
			if(null != gstinList){
				if(gstinList.size() == 1){
					gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(gstinNo,loginMaster.getPrimaryUserUId());
					List<GstinLocation> gstinLocationSet = gstinDetails.getGstinLocationSet();
					if(gstinLocationSet != null){
						if(gstinLocationSet.size() < 1){
							invoiceNumberSB.append(gstinDetails.getUniqueSequence()+location+"/");
						}
					}
				}else{
					gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(gstinNo,loginMaster.getPrimaryUserUId());
					invoiceNumberSB.append(gstinDetails.getUniqueSequence()+location+"/");
				}
			}
			//get last invoice number created by current employee - Start
			logger.info("Check for invoice number for "+invoiceNumberSB);
			String latestInvoiceCount = null;
			if(generatedFrom.equals("S")){
				latestInvoiceCount = generateInvoiceService.getLatestInvoiceNumber(invoiceNumberSB.toString(),loginMaster.getOrgUId());
			}else{
				latestInvoiceCount = generateInvoiceService.getLatestCnDnInvoiceNumber(invoiceNumberSB.toString(),loginMaster.getOrgUId());
			}
			
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

	public String generateInvoiceSequence(String gstinNo, LoginMaster loginMaster, String location) {
		logger.info("Entry");
		StringBuffer invoiceNumberSB = new StringBuffer();
		GSTINDetails gstinDetails = null;
		try{
			gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(gstinNo,loginMaster.getPrimaryUserUId());
			invoiceNumberSB.append(GSTNUtil.getCurrentYearAndCurrentMonth());
			invoiceNumberSB.append(gstinDetails.getUniqueSequence());
			/*invoiceNumberSB.append(loginMaster.getUniqueSequence());*/
			invoiceNumberSB.append(location);
			
			//get last invoice number created by current employee - Start
			logger.info("Check for invoice number for "+invoiceNumberSB);
			String latestInvoiceCount = generateInvoiceService.getLatestInvoiceNumber(invoiceNumberSB.toString(),loginMaster.getOrgUId());
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

	
	@RequestMapping(value = "/checkDateForInvoice", method = RequestMethod.POST)
	public @ResponseBody String checkDateForInvoice(@RequestParam("inputDate") String dateInString, HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		Map<String, String> response = null;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			response = generateInvoiceService.compareInvoiceDate(GSTNUtil.convertStringToTimestamp(dateInString),idsValuesToFetch);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return new Gson().toJson(response);
	}
	
	@RequestMapping(value = {"/getMyInvoices", "/getInvoices", "/getBOS", "/getRCInvoice", "/getEComInvoice", "/getEComBOS", "/getSalesEntry", "/getPOWO", "/getRCSelfInvoice", "/getDC", "/getAdvances", "/getExports", "/getISDInvoice"}, method = RequestMethod.GET)
	public String getMyInvoicesPage(Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String uri = GSTNUtil.getLastURIPart(httpRequest.getRequestURI());
		String pageRedirect = PageRedirectConstants.GET_MY_INVOIVES_PAGE;
		
		if((uri.equals("/getMyInvoices")) || (uri.equals("/getInvoices"))){
			model.addAttribute("documentType", "invoice");
		}else if(uri.equals("/getBOS")){
			model.addAttribute("documentType", "billOfSupply");
		}else if(uri.equals("/getRCInvoice")){
			model.addAttribute("documentType", "rcInvoice");
		}else if(uri.equals("/getEComInvoice")){
			model.addAttribute("documentType", "eComInvoice");
		}else if(uri.equals("/getEComBOS")){
			model.addAttribute("documentType", "eComBillOfSupply");
		}else if((uri.equals("/getSalesEntry")) || (uri.equals("/getPOWO")) || (uri.equals("/getRCSelfInvoice"))  || (uri.equals("/getDC")) || (uri.equals("/getAdvances")) || (uri.equals("/getExports")) || (uri.equals("/getISDInvoice"))){
			
		}
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value="/getInvoiceDetailsList", method = RequestMethod.POST)
	public @ResponseBody String getInvoiceDetailsList(HttpServletRequest httpRequest){
		logger.info("Entry");
		List<InvoiceDetails> invoiceList = null;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			invoiceList = generateInvoiceService.getInvoiceDetailsByOrgUId(loginMaster.getOrgUId());
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return new Gson().toJson(invoiceList);
	}
	
	@RequestMapping(value="/getInvoiceDetailList", method = RequestMethod.POST)
	public @ResponseBody String getInvoiceDetailList(HttpServletRequest httpRequest){
		logger.info("Entry");
		List<Object[]> invoiceList = null;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			invoiceList = generateInvoiceService.getInvoiceDetailByOrgUId(loginMaster.getOrgUId());
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return new Gson().toJson(invoiceList);
	}
	
	@RequestMapping(value = {"/getInvoiceDetails","/getInvoiceCNDNDetails","/getInvCnDnDetails"},method=RequestMethod.POST)
	public String getInvoiceDetailsById(@RequestParam Integer id, HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		
		String uri = GSTNUtil.getLastURIPart(httpRequest.getRequestURI()); 
		String pageRedirect = null;
		InvoiceDetails invoiceDetails = null;
		GSTINDetails gstinDetails = null;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
		UserMaster user = null;
		State customerStateCode = null;
		Map<String,Map<String,Double>> gstMap = new HashMap<String,Map<String,Double>>();
		
		try {
			boolean isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(id,loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				user = userMasterService.getUserMasterById( loginMaster.getuId());
				if(!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
					user = userMasterService.getUserMasterById(user.getReferenceId());
				}
				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(id);
				gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(invoiceDetails.getGstnStateIdInString(),loginMaster.getPrimaryUserUId());
				customerStateCode = stateService.getStateByStateName(invoiceDetails.getCustomerDetails().getCustState());
				gstMap = GSTNUtil.convertListToMap(invoiceDetails.getServiceList());
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:",e);
		}
		//dashboard variable start
		String conditionValue=httpRequest.getParameter("conditionValue");
		String startdate=httpRequest.getParameter("startdate");
		String enddate=httpRequest.getParameter("enddate");
		String onlyMonth=httpRequest.getParameter("onlyMonth");
		String onlyYear=httpRequest.getParameter("onlyYear");
		
		model.addAttribute("onlyMonth",onlyMonth);
		model.addAttribute("onlyYear",onlyYear);
		
		model.addAttribute("dash_conditionValue",conditionValue);
		model.addAttribute("dash_startdate",startdate);
		model.addAttribute("dash_enddate",enddate);
		//dashboard variable end
		
		
		model.addAttribute("invoiceDetails", invoiceDetails);
		model.addAttribute("gstinDetails", gstinDetails);
		model.addAttribute("userMaster", user);
		model.addAttribute("customerStateCode", customerStateCode.getStateCode());
		model.addAttribute("gstMap", gstMap);
		//String invoiceValue = new BigDecimal(invoiceDetails.getInvoiceValue()).setScale(2, RoundingMode.DOWN).toPlainString();
		
		model.addAttribute("amtInWords", AmountInWordsModified.convertNumberToWords(""+invoiceDetails.getInvoiceValueAfterRoundOff()));
		model.addAttribute("loggedInThrough", loginMaster.getLoggedInThrough());
		if(uri.equals("/getInvoiceDetails")){
		
			pageRedirect = PageRedirectConstants.VIEW_INVOICE_PAGE;
		}else if(uri.equals("/getInvoiceCNDNDetails")){
			pageRedirect = PageRedirectConstants.INVOICE_CN_DN_PAGE;
		}else if(uri.equals("/getInvCnDnDetails")){
			pageRedirect = PageRedirectConstants.NEWUI_INVOICE_CN_DN_PAGE;
		}
		
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value="/convertAmountInWords",method=RequestMethod.POST)
	public @ResponseBody String convertAmountInWordsPost(@RequestParam("amtInNumber") String amtInNumber, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String amtInWords = null;
		try {
			amtInWords = AmountInWordsModified.convertNumberToWords(amtInNumber);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(amtInWords);
		
	}
	
	private void sendInvoicePdfInMailToCustomer(String invoiceId, String invoiceNumber,HttpServletRequest httpRequest,String logoImagePath) {
		logger.info("Entry");
		InvoiceDetails invoiceDetails = null;
		GSTINDetails gstinDetails = null;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
		UserMaster user = null;
		State customerStateCode = null;
		Map<String,Map<String,Double>> gstMap = new HashMap<String,Map<String,Double>>();
		String amtInWords = null;
		Map<String,String> mapResponse = new HashMap<String,String>();
		try {
			user = userMasterService.getUserMasterById( loginMaster.getuId());
			if(!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
				user = userMasterService.getUserMasterById(user.getReferenceId());
			} 
			invoiceDetails = generateInvoiceService.getInvoiceDetailsById(Integer.parseInt(invoiceId));
			
			if(invoiceDetails.getInvoicePeriodFromDate() != null)
				invoiceDetails.setInvoicePeriodFromDateInString(formatter.format(invoiceDetails.getInvoicePeriodFromDate()));
			if(invoiceDetails.getInvoicePeriodToDate() != null)
				invoiceDetails.setInvoicePeriodToDateInString(formatter.format(invoiceDetails.getInvoicePeriodToDate())); 	
						
			gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(invoiceDetails.getGstnStateIdInString(),loginMaster.getPrimaryUserUId());
			customerStateCode = stateService.getStateByStateName(invoiceDetails.getCustomerDetails().getCustState());
			gstMap = GSTNUtil.convertListToMap(invoiceDetails.getServiceList());
			amtInWords = AmountInWordsModified.convertNumberToWords(""+invoiceDetails.getInvoiceValueAfterRoundOff());
			mapResponse = GenerateInvoicePdf.generatePdf(invoiceDetails, gstinDetails, user, customerStateCode.getStateCode(), gstMap, amtInWords,invoiceDirectoryPath,logoImagePath);
			if(mapResponse.get(GSTNConstants.STATUS).equals(GSTNConstants.SUCCESS)){
				//call email method to send pdf to customer
				
				boolean isFilePresent = GSTNUtil.checkIfFileExists(mapResponse.get(GSTNConstants.PDF_PATH));
				if(isFilePresent){
					sendInvoicePdfToCustomer(emailFrom,invoiceDetails.getCustomerEmail(),user.getDefaultEmailId(),"nikesh.bansod@ril.com",GSTNUtil.getDocumentType(invoiceDetails.getDocumentType())+" from "+user.getOrganizationMaster().getOrgName()+" to "+invoiceDetails.getCustomerDetails().getCustName()+" - "+invoiceDetails.getInvoiceNumber(),emailSmtpHostName,mapResponse.get(GSTNConstants.PDF_PATH),mapResponse.get(GSTNConstants.PDF_FILENAME));	
				}else{
					logger.info("Pdf File : "+mapResponse.get(GSTNConstants.PDF_PATH)+" doesnot exist. Hence mail was not send");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		
	}
	
	private Map<String,String> generateInvoicePdf(Integer invoiceId, String invoiceNumber,HttpServletRequest httpRequest,String logoImagePath) {
		logger.info("Entry");
		InvoiceDetails invoiceDetails = null;
		GSTINDetails gstinDetails = null;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
		UserMaster user = null;
		State customerStateCode = null;
		Map<String,Map<String,Double>> gstMap = new HashMap<String,Map<String,Double>>();
		String amtInWords = null;
		Map<String,String> mapResponse = new HashMap<String,String>();
		try {
			user = userMasterService.getUserMasterById( loginMaster.getuId());
			if(!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
				user = userMasterService.getUserMasterById(user.getReferenceId());
			}
			invoiceDetails = generateInvoiceService.getInvoiceDetailsById(invoiceId);
			gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(invoiceDetails.getGstnStateIdInString(),loginMaster.getPrimaryUserUId());
			customerStateCode = stateService.getStateByStateName(invoiceDetails.getCustomerDetails().getCustState());
			gstMap = GSTNUtil.convertListToMap(invoiceDetails.getServiceList());
			amtInWords = AmountInWordsModified.convertNumberToWords(""+invoiceDetails.getInvoiceValueAfterRoundOff());
			mapResponse = GenerateInvoicePdf.generatePdf(invoiceDetails, gstinDetails, user, customerStateCode.getStateCode(), gstMap, amtInWords,invoiceDirectoryPath,logoImagePath);
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return mapResponse;
	}
	
	private String sendInvoicePdfToCustomer(String from, String to, String cc, String bcc, String subject, String hostName , String filePath, String fileName){
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try{
			//SendEmailWithPdf.sendMail(user.getDefaultEmailId(),invoiceDetails.getCustomerEmail(),"nikesh.bansod@ril.com","nikesh.bansod@ril.com","Invoice Subject","hostName",mapResponse.get(GSTNConstants.PDF_PATH),mapResponse.get(GSTNConstants.PDF_FILENAME));
			SendEmailWithPdf.sendMail(from,to,cc,bcc,subject,hostName,filePath,fileName);
			response = GSTNConstants.SUCCESS;
		}catch(Exception e){
			logger.error("Failed to send mail from :"+from +" to "+to+ "having fileName : "+filePath,e);
		}
		logger.info("Exit");
		return response;
	}
	
	@RequestMapping(value="/sendMailToCustomerFromPreview",method=RequestMethod.POST)
	public @ResponseBody String sendMailToCustomerFromPreviewPost(@RequestParam("id") String invoiceId, HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		InvoiceDetails invoiceDetails = null;
		String pdfFilePath = null;
		String pdfFileName = null;
		Map<String,String> mapResponse = new HashMap<String,String>();
		System.out.println("invoiceId : "+invoiceId);
		boolean canSendMail = false;
		UserMaster user = null;
		String logoImagePath = null;
		String finalResponse = "You donot have rights to download the above invoice.";
		try{
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
			user = userMasterService.getUserMasterById( loginMaster.getuId());
			if(!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
				user = userMasterService.getUserMasterById(user.getReferenceId());
			}
			logoImagePath = userMasterService.getOrgLogoPath(loginMaster.getOrgUId());
			boolean isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(Integer.parseInt(invoiceId),loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(Integer.parseInt(invoiceId)); 
				pdfFilePath = GSTNUtil.generateInvoicePdfFileName(invoiceDetails, invoiceDirectoryPath);
				pdfFileName = invoiceDetails.getInvoiceNumber()+".pdf";
				//first check if pdf file is present 
				//if present get the file path and send mail 
				// if not present generate the invoice pdf and call send mail
				boolean isFilePresent = GSTNUtil.checkIfFileExists(pdfFilePath);
				if(!isFilePresent){
					mapResponse = generateInvoicePdf( invoiceDetails.getId(), invoiceDetails.getInvoiceNumber(), httpRequest,logoImagePath);
					if(mapResponse.get(GSTNConstants.STATUS).equals(GSTNConstants.SUCCESS)){
						canSendMail = true;
					}else{
						logger.info("Since pdf file is not generated at path "+mapResponse.get(GSTNConstants.PDF_PATH)+" mail was not send");
					}
				}else{
					canSendMail = true;
				}
				
				if(canSendMail){
					sendInvoicePdfToCustomer(emailFrom,invoiceDetails.getCustomerEmail(),user.getDefaultEmailId(),"nikesh.bansod@ril.com",GSTNUtil.getDocumentType(invoiceDetails.getDocumentType())+" from "+user.getOrganizationMaster().getOrgName()+" to "+invoiceDetails.getCustomerDetails().getCustName()+" - "+invoiceDetails.getInvoiceNumber(), emailSmtpHostName, pdfFilePath, pdfFileName);
				}
				finalResponse = "Email sent successfully";
			}
		}catch(Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(finalResponse);
	}
	
	@RequestMapping(value = "/addCnDn", method = RequestMethod.POST)
	public @ResponseBody String addCnDn(@RequestBody InvoiceCnDnDetails invoiceCNDNDetails, HttpServletRequest httpRequest) {
		logger.info("Entry");
		Map<String, String> mapResponse = new HashMap<String, String>();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			boolean isInvoiceEditAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(invoiceCNDNDetails.getId(),loginMaster.getOrgUId());
			if(isInvoiceEditAllowed){
				invoiceCNDNDetails.setCreatedBy(loginMaster.getuId().toString());
				mapResponse = generateInvoiceService.addCNDN(invoiceCNDNDetails,invoiceCNDNDetails.getId());
			}
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(mapResponse);
	}
	
	@RequestMapping(value = "/getInvoiceDetailsForCnDn", method=RequestMethod.POST)
	public @ResponseBody String getInvoiceDetailsByIdJson(@RequestParam("invIdt") String invoiceId, HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
	
		InvoiceDetails invoiceDetails = null;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
		System.out.println("getInvoiceDetails : "+invoiceId);
		Integer invoiceid = Integer.parseInt(invoiceId);
		try {
			boolean isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(invoiceid,loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(invoiceid);
			}else{
				invoiceDetails = new InvoiceDetails();
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:",e);
		}

		
		logger.info("Exit");
		return new Gson().toJson(invoiceDetails);
	}
	
	@RequestMapping(value = "/addInvoiceCnDn", method = RequestMethod.POST)
	public @ResponseBody String addInvoiceCnDn(@RequestBody InvoiceDetails invoiceDetails, HttpServletRequest httpRequest) {
		logger.info("Entry");
		Map<String, String> mapResponse = new HashMap<String, String>();
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
		System.out.println("getInvoiceDetails : "+invoiceDetails.getId());
		String invoiceNumber = null;
		String cnDnType = invoiceDetails.getCnDnType();
		String cnDnFooter = invoiceDetails.getFooterNote();
		Map<String, String> clientMapResponse = new HashMap<String,String>();
		boolean cndnValidationSuccess = true;
		String cndnTypeCreated = invoiceDetails.getCnDnType();
		String rrType = invoiceDetails.getCreateDocType();
		String invoiceSeqStartAppend = "D";
		String newInvoiceNumber = invoiceDetails.getInvoiceNumber();
		try {
			String logoImagePath = userMasterService.getOrgLogoPath(loginMaster.getOrgUId());
			boolean isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(invoiceDetails.getId(),loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				if(invoiceDetails.getCnDnType().equals(GSTNConstants.CREDIT_NOTE)){
					invoiceSeqStartAppend = "C";
					clientMapResponse = generateInvoiceService.verifyInvoiceCnDn(invoiceDetails);
					if(clientMapResponse.get(GSTNConstants.RESPONSE).equals(GSTNConstants.FAILURE)){
						cndnValidationSuccess = false;
						mapResponse.put(GSTNConstants.RESPONSE, clientMapResponse.get(GSTNConstants.RESPONSE));
					}
				}
				
				if(cndnValidationSuccess){
					invoiceDetails = taxCalculation.getInvoiceDetailsForCnDn(invoiceDetails);
					//invoiceNumber = generateCnDnSequence(invoiceDetails.getInvoiceNumber(),loginMaster,cnDnType);
					if(loginMaster.getInvoiceSequenceType().equals("Auto")){
						invoiceNumber = generateInvoiceSequenceXLogic(invoiceSeqStartAppend,invoiceDetails.getInvoiceDateInString(),invoiceDetails.getGstnStateIdInString(),loginMaster,invoiceDetails.getLocation());
					}else{
						invoiceNumber = newInvoiceNumber;
					}
					mapResponse = generateInvoiceService.addInvoiceCnDn(invoiceDetails,loginMaster,invoiceNumber,cnDnFooter);
					if(mapResponse.get(GSTNConstants.RESPONSE).equals(GSTNConstants.SUCCESS)){
						sendCnDnInvoicePdfInMailToCustomer(mapResponse.get("InvoicePkId"),mapResponse.get("InvoiceNumber"),httpRequest,logoImagePath,Integer.parseInt(mapResponse.get("iterationNo")),cnDnType);
						updateRRHistory(rrType,cndnTypeCreated,invoiceNumber,mapResponse.get("invoiceIterationNo"),mapResponse.get("iterationNo"),invoiceDetails.getInvoiceNumber(),mapResponse.get("InvoicePkId"),loginMaster.getOrgUId(),loginMaster.getuId());
						//modeOfCreation -- iteration no -- CN/DN -- sales returns -- invoicePkId -- cndnPkNumber
						updateCnDnCreationInInvoiceAndInventory("EDIT",invoiceDetails,cndnTypeCreated,rrType,mapResponse.get("InvoicePkId"),mapResponse.get("InvoiceNumber"),loginMaster,httpRequest);
					}
					
					
				}
			
			}else{
				mapResponse.put(GSTNConstants.RESPONSE, "accessDeny");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(mapResponse);
	}
	
	private String updateRRHistory(String rrType, String cndnTypeCreated, String cndnNumber, String iterationNo,String cndnIterationNo,
			String invoiceNumber, String invoicePkId, Integer orgUId, Integer getuId) {
		ReviseAndReturnHistory rrHistory = new ReviseAndReturnHistory();
		rrHistory.setRrType(rrType);
		rrHistory.setDocumentType(cndnTypeCreated);
		rrHistory.setCreatedDocNo(cndnNumber);
		String cnPkId = generateInvoiceService.getCnDnPkIdByCnDnNoAndIterationNo(cndnNumber,orgUId,cndnIterationNo);
		rrHistory.setCreatedDocumentPkId(Integer.parseInt(cnPkId));
		rrHistory.setOriginalInvoiceNo(invoiceNumber);
		rrHistory.setOriginalInvoicePkId(Integer.parseInt(invoicePkId));
		rrHistory.setIterationNo(Integer.parseInt(iterationNo));
		rrHistory.setCnDnIterationNo(Integer.parseInt(cndnIterationNo));
		rrHistory.setRefOrgId(orgUId);
		rrHistory.setCreatedBy(getuId+"");
		rrHistory.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
		return reviseAndReturnService.updateRRHistory(rrHistory);
	}

	private Map<String,Object> updateCnDnCreationInInvoiceAndInventory(String modeOfCreation, InvoiceDetails invoiceDetails, String cndnTypeCreated,String rrType,String invoicePkId, String cndnInvoiceNumber, LoginMaster loginMaster, HttpServletRequest httpRequest) {
		logger.info("Entry");
		//int iterationNo = invoiceDetails.getIterationNo();
		Integer invoiceId = Integer.parseInt(invoicePkId);
		InventoryProductSave inventoryProductSave = null;
		Map<String,Object> invSave = null;
		try{
			InvoiceDetails dbInvoiceDetails = generateInvoiceService.getInvoiceDetailsById(invoiceId);
		if(rrType.equals(GSTNConstants.RR_SALES_RETURN) || rrType.equals(GSTNConstants.RR_QUANTITY_CHANGE)){
			Integer uniqSeq = inventoryController.getSessionUniqueSequence(loginMaster, httpRequest);
			if(cndnTypeCreated.equals(GSTNConstants.CREDIT_NOTE)){
				//increase inventory & donot update invoice related data 
				inventoryProductSave = callInvertoryProductChanges(dbInvoiceDetails.getServiceList(),invoiceDetails.getCnDnList(), GSTNConstants.CN_CREDIT, GSTNConstants.CREDIT_NOTE,cndnInvoiceNumber, uniqSeq);
				
			}else if(cndnTypeCreated.equals(GSTNConstants.DEBIT_NOTE)){
				//decrese inventory and donot update invoice related data
				inventoryProductSave = callInvertoryProductChanges(dbInvoiceDetails.getServiceList(),invoiceDetails.getCnDnList(), GSTNConstants.DN_DEBIT, GSTNConstants.CREDIT_NOTE,cndnInvoiceNumber, uniqSeq);
			}
			
			if(inventoryProductSave.getProductList().size() > 0){
			    invSave = inventoryService.saveInventoryDetails(inventoryProductSave, loginMaster.getuId());
			}
		}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return invSave;
	}
	
	private InventoryProductSave callInvertoryProductChanges(List<InvoiceServiceDetails> dbServiceList,List<InvoiceCnDnDetails> cnDnList, String transactionType, String cndnType,String cndnInvoiceNumber, Integer uniqSeq) throws Exception {
		List<InventoryProductTable> productList = new ArrayList<InventoryProductTable>();
		InventoryProductTable productTable = null;
		Product product = null;
		InventoryProductSave inventoryProduct = new InventoryProductSave();
		inventoryProduct.setUniqueSequenceid(uniqSeq);
		inventoryProduct.setInventoryType(transactionType);
		inventoryProduct.setNarration(cndnType);
		inventoryProduct.setReason("Created for "+cndnType+" : "+cndnInvoiceNumber);
		for(InvoiceServiceDetails invoiceItems : dbServiceList){
			if(invoiceItems.getBillingFor().equals("Product")){
				for(InvoiceCnDnDetails items : cnDnList){
					if(items.getServiceId().equals(invoiceItems.getId())){
						product = productService.getProductById(invoiceItems.getServiceId());
						if(product != null){
							Double dbCurrentStock = 0d;
							Double dbRate = 0d;
							Double currentStock = 0d;
							Double currentStockValue = 0d;
							if(cndnType.equals(GSTNConstants.CREDIT_NOTE)){
								dbCurrentStock = product.getCurrentStock();
								dbRate = product.getPurchaseRate();
								currentStock = dbCurrentStock + (Double)items.getQuantity();
								currentStockValue = currentStock * dbRate;
							}else{
								dbCurrentStock = product.getCurrentStock();
								dbRate = product.getPurchaseRate();
								currentStock = dbCurrentStock - (Double)items.getQuantity();
								currentStockValue = currentStock * dbRate;
							}
							
						
							productTable = new InventoryProductTable();
							productTable.setId(product.getId());
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
							productTable.setTransactionDate(GSTNUtil.convertCurrentDateToddMMYYYY());
							productTable.setActionFrom(transactionType);
							productList.add(productTable);
						}
					}
				}
			}
		}
		
		inventoryProduct.setProductList(productList);
		// TODO Auto-generated method stub
		System.out.println("Final JSON from invoice : "+new Gson().toJson(inventoryProduct));
		return inventoryProduct;
	}

	private void sendCnDnInvoicePdfInMailToCustomer(String invoiceId, String invoiceNumber, HttpServletRequest httpRequest,String logoImagePath, Integer iterationNo, String cnDnType) {
		logger.info("Entry");
		InvoiceDetails invoiceDetails = null;
		GSTINDetails gstinDetails = null;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
		UserMaster user = null;
		State customerStateCode = null;
		Map<String,Map<String,Double>> gstMap = new HashMap<String,Map<String,Double>>();
		String amtInWords = null;
		Map<String,String> mapResponse = new HashMap<String,String>();
		try {
			user = userMasterService.getUserMasterById( loginMaster.getuId());
			if(!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
				user = userMasterService.getUserMasterById(user.getReferenceId());
			}
			invoiceDetails = generateInvoiceService.getInvoiceDetailsById(Integer.parseInt(invoiceId));
			gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(invoiceDetails.getGstnStateIdInString(),loginMaster.getPrimaryUserUId());
			customerStateCode = stateService.getStateByStateName(invoiceDetails.getCustomerDetails().getCustState());
			gstMap = GSTNUtil.convertListToMapForCnDn(invoiceDetails.getCnDnList(),iterationNo);
			amtInWords = AmountInWordsModified.convertNumberToWords(getCnDnAmtInWords(iterationNo,invoiceDetails.getCnDnAdditionalList()));
			mapResponse = GenerateInvoicePdf.generateCnDnPdf(invoiceDetails, gstinDetails, user, customerStateCode.getStateCode(), gstMap, amtInWords,invoiceDirectoryPath,logoImagePath,iterationNo);
			if(mapResponse.get(GSTNConstants.STATUS).equals(GSTNConstants.SUCCESS)){
				//call email method to send pdf to customer
				
				boolean isFilePresent = GSTNUtil.checkIfFileExists(mapResponse.get(GSTNConstants.PDF_PATH));
				if(isFilePresent){
					String subject = null;
					if(cnDnType.equals(GSTNConstants.CREDIT_NOTE)){
						subject = "Credit Note "+invoiceNumber+" from "+user.getOrganizationMaster().getOrgName()+" to "+invoiceDetails.getCustomerDetails().getCustName();
					}else{
						subject = "Debit Note "+invoiceNumber+" from "+user.getOrganizationMaster().getOrgName()+" to "+invoiceDetails.getCustomerDetails().getCustName();
					}
					sendInvoicePdfToCustomer(emailFrom,invoiceDetails.getCustomerEmail(),user.getDefaultEmailId(),"nikesh.bansod@ril.com",subject,emailSmtpHostName,mapResponse.get(GSTNConstants.PDF_PATH),mapResponse.get(GSTNConstants.PDF_FILENAME));	
				}else{
					logger.info("Pdf File : "+mapResponse.get(GSTNConstants.PDF_PATH)+" doesnot exist. Hence mail was not send");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		
	}

	private String getCnDnAmtInWords(Integer iterationNo, List<CnDnAdditionalDetails> cnDnAdditionalList) {
		Double amount = null;
		for(CnDnAdditionalDetails a : cnDnAdditionalList){
			if(a.getIterationNo() == iterationNo){
				amount = a.getInvoiceValueAfterRoundOff();
			}
		}
		return amount+"";
	}

	private String generateCnDnSequence(String oldInvoiceNumber,LoginMaster loginMaster,String cnDnType) {
		logger.info("Entry");
		StringBuffer invoiceNumberSB = new StringBuffer();
		//eg.C17A0A1A200001(C/D->1,A0->GSTIN,A1->User,A2->Location,0000001->7digit no)
		
		try{
			if(cnDnType.equals("creditNote")){
				invoiceNumberSB.append("C");
			}else{
				invoiceNumberSB.append("D");
			}
			//invoiceNumberSB.append("--");
			invoiceNumberSB.append(GSTNUtil.getCurrentYearAndCurrentMonth());//(GSTNUtil.getCurrentYear());
			invoiceNumberSB.append(oldInvoiceNumber.substring(4,5));//oldInvoiceNumber.substring(2, 4)
			//invoiceNumberSB.append(loginMaster.getUniqueSequence());
			invoiceNumberSB.append(oldInvoiceNumber.substring(5,6));//oldInvoiceNumber.substring(6, 8)
			
			
			//get last invoice number created by current employee - Start
			logger.info("Check for invoice number for "+invoiceNumberSB);
			String latestInvoiceCount = generateInvoiceService.getLatestCnDnInvoiceNumber(invoiceNumberSB.toString(),loginMaster.getOrgUId());
			String count = null;
			if(latestInvoiceCount == null){
				count = "1";
			}else{
				count = GSTNUtil.incrementCnDnCount(latestInvoiceCount);
				//GSTNUtil.incrementInvoiceCount(latestInvoiceCount);
			}
			//get last invoice number created by current employee - End
			invoiceNumberSB.append(count);
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return invoiceNumberSB.toString();
	}

	@RequestMapping(value = "/verifyInvoiceCnDn", method = RequestMethod.POST)
	public @ResponseBody String verifyInvoiceCnDn(@RequestBody InvoiceDetails invoiceDetails, HttpServletRequest httpRequest) {
		logger.info("Entry");
		Map<String, String> mapResponse = new HashMap<String, String>();
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
		System.out.println("getInvoiceDetails : "+invoiceDetails.getId());
		try {
			boolean isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(invoiceDetails.getId(),loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				mapResponse = generateInvoiceService.verifyInvoiceCnDn(invoiceDetails);
			}else{
				mapResponse.put(GSTNConstants.RESPONSE, GSTNConstants.FAILURE);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(mapResponse);
	}
	
	@RequestMapping(value = {"/getCnDn"},method=RequestMethod.POST)
	public String getCnDnById(@RequestParam Integer id, HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.PRE_INVOICE_CN_DN_PAGE;
		InvoiceDetails invoiceDetails = null;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
		System.out.println("getCnDnById : "+id);
		String response = GSTNConstants.NOT_ALLOWED_ACCESS;
		UserMaster user = null;
		GSTINDetails gstinDetails = null;
		State customerStateCode = null;
		try {
			boolean isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(id,loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				user = userMasterService.getUserMasterById( loginMaster.getuId());
				if(!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
					user = userMasterService.getUserMasterById(user.getReferenceId());
				}
				
				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(id);
				gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(invoiceDetails.getGstnStateIdInString(),loginMaster.getPrimaryUserUId());
				customerStateCode = stateService.getStateByStateName(invoiceDetails.getCustomerDetails().getCustState());
				response = GSTNConstants.ALLOWED_ACCESS;
			}else{
				invoiceDetails = new InvoiceDetails();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:",e);
		}
		model.addAttribute("gstinDetails", gstinDetails);
		model.addAttribute("customerStateCode", customerStateCode.getStateCode());
		model.addAttribute("userMaster", user);
		model.addAttribute("invoiceDetails", invoiceDetails);
		model.addAttribute(GSTNConstants.RESPONSE, response);
		
		
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value="/sendCnDnMailToCustomerFromPreview",method=RequestMethod.POST)
	public @ResponseBody String sendCnDnMailToCustomerFromPreviewPost(@RequestParam("id") Integer invoiceId, @RequestParam("iterationNo") Integer iterationNo, @RequestParam("cId") Integer cndnInvoiceId,HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		InvoiceDetails invoiceDetails = null;
		Map<String,String> mapResponse = new HashMap<String,String>();
		String status = GSTNConstants.FAILURE;
		String response = null;
		System.out.println("invoiceId : "+invoiceId + ",iteration No : "+iterationNo+", CndnInvoiceId : "+cndnInvoiceId);
		UserMaster user = null;
		try{
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			user = userMasterService.getUserMasterById( loginMaster.getuId());
			if(!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
				user = userMasterService.getUserMasterById(user.getReferenceId());
			}
			boolean isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(invoiceId,loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(invoiceId);
				Map<String,String> verifyCnDn = verifyCnDnAgainstOrg(invoiceDetails,cndnInvoiceId,iterationNo);
				String logoImagePath = userMasterService.getOrgLogoPath(loginMaster.getOrgUId());
				if(verifyCnDn.get(GSTNConstants.RESPONSE).equals(GSTNConstants.SUCCESS)){
					String pdfFilePath = verifyCnDn.get("pdfFilePath");
					String pdfFileName = verifyCnDn.get("pdfFileName");
					System.out.println("Go ahead");
					//first check if pdf file is present 
					//if present get the file path and send mail 
					// if not present generate the invoice pdf and call send mail
					boolean isFilePresent = GSTNUtil.checkIfFileExists(pdfFilePath);
					if(!isFilePresent){
						sendCnDnInvoicePdfInMailToCustomer(invoiceId.toString(),verifyCnDn.get("cndnInvoiceNumber"),httpRequest,logoImagePath,iterationNo, verifyCnDn.get("cnDnType"));
						
					}else{
						String subject = null;
						if(verifyCnDn.get("cnDnType").equals(GSTNConstants.CREDIT_NOTE)){
							subject = "Credit Note "+verifyCnDn.get("cndnInvoiceNumber")+" document from "+user.getOrganizationMaster().getOrgName()+" to "+invoiceDetails.getCustomerDetails().getCustName();
						}else{
							subject = "Debit Note "+verifyCnDn.get("cndnInvoiceNumber")+" document from "+user.getOrganizationMaster().getOrgName()+" to "+invoiceDetails.getCustomerDetails().getCustName();
						}
						sendInvoicePdfToCustomer(emailFrom,invoiceDetails.getCustomerEmail(),user.getDefaultEmailId(),"nikesh.bansod@ril.com",subject,emailSmtpHostName,pdfFilePath,pdfFileName);	
					
					}
					
					
				}
				status = GSTNConstants.SUCCESS;
				response = "Email sent successfully";
			}else{
				response = "Failed to send email";
			}
		}catch(Exception e) {
			logger.error("Error in:",e);
		}
		mapResponse.put(GSTNConstants.STATUS, status);
		mapResponse.put(GSTNConstants.RESPONSE, response);
		logger.info("Exit");
		return new Gson().toJson(mapResponse);
	}

	private Map<String,String> verifyCnDnAgainstOrg(InvoiceDetails invoiceDetails, Integer cndnInvoiceId, Integer iterationNo) {
		Map<String,String> mapResponse = new HashMap<String,String>();
		String isAccessible = GSTNConstants.FAILURE;
		String cndnType = null;
		for(CnDnAdditionalDetails a : invoiceDetails.getCnDnAdditionalList()){
			if((a.getIterationNo().equals(iterationNo)) && (a.getId().equals(cndnInvoiceId))){
				cndnType = a.getCnDnType();
				isAccessible = GSTNConstants.SUCCESS;
				mapResponse = GSTNUtil.generateCnDnPdfFileName(invoiceDetails.getCnDnAdditionalList(), invoiceDirectoryPath, iterationNo);
				break;
			}
		}
		mapResponse.put(GSTNConstants.RESPONSE, isAccessible);
		mapResponse.put("cnDnType",cndnType);
		return mapResponse;
	}
	
	@RequestMapping(value = "/getCndnDetailsInPreview", method = RequestMethod.POST)
	public @ResponseBody String getCndnDetailsInPreview(@RequestParam("id") Integer invoiceId, @RequestParam("iterationNo") Integer iterationNo, @RequestParam("cId") Integer cndnInvoiceId,HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		String amtInWords = null;
		
		
		CompleteInvoice completeInvoice = new CompleteInvoice();
		InvoiceDetails invoiceDetails = null;
		boolean isInvoiceAllowed = false;
		try {
			
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
			isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(invoiceId,loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(invoiceId);
				completeInvoice.setInvoiceNoForDashboard(invoiceDetails.getInvoiceNumber());
				Map<String, Map<String, Double>> gstMap = GSTNUtil.convertListToMapForCnDn(invoiceDetails.getCnDnList(), iterationNo);
				invoiceDetails = modifyInvoiceDetailsAsPerCnDn(invoiceDetails,iterationNo,cndnInvoiceId);
				
				amtInWords = AmountInWordsModified.convertNumberToWords(""+invoiceDetails.getInvoiceValueAfterRoundOff());	
			
			
				//set value in CompleteInvoice - Start	
				completeInvoice.setInvoiceDetails(invoiceDetails);
				completeInvoice.setAmtInWords(amtInWords);
				completeInvoice.setGstMap(gstMap);
				
				completeInvoice.setRenderData(GSTNConstants.ALLOWED_ACCESS);
				//set value in CompleteInvoice - End
			}else{
				completeInvoice.setRenderData(GSTNConstants.NOT_ALLOWED_ACCESS);
			}
	
	   
		
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(completeInvoice);
	}

	private InvoiceDetails modifyInvoiceDetailsAsPerCnDn(InvoiceDetails invoiceDetails, Integer iterationNo,Integer cndnInvoiceId) {
		String invoiceNumber = null;
		double invoiceValue = 0d;
		double invoiceValueAfterRoundOff = 0d;
		double totalTax = 0d;
		double amountAfterDiscount = 0d;
		String cnDnType = null;
		String footerNote = null;
		String cndnOnRRType = null;
		
		
		for(CnDnAdditionalDetails cndnAdditionalDetails : invoiceDetails.getCnDnAdditionalList()){
			if((cndnAdditionalDetails.getIterationNo().equals(iterationNo)) && (cndnAdditionalDetails.getId().equals(cndnInvoiceId))){
				invoiceNumber = cndnAdditionalDetails.getInvoiceNumber();
				invoiceValue = cndnAdditionalDetails.getInvoiceValue();
				invoiceValueAfterRoundOff = cndnAdditionalDetails.getInvoiceValueAfterRoundOff();
				totalTax = cndnAdditionalDetails.getTotalTax();
				amountAfterDiscount = cndnAdditionalDetails.getAmountAfterDiscount();
				cnDnType = cndnAdditionalDetails.getCnDnType();
				footerNote = cndnAdditionalDetails.getFooter();
				cndnOnRRType = cndnAdditionalDetails.getReason();
			}
		}
		
		//create cndnList
		List<InvoiceCnDnDetails> cnDnDetailsList = new ArrayList<InvoiceCnDnDetails>();
		for(InvoiceCnDnDetails a : invoiceDetails.getCnDnList()){
			if(a.getIterationNo().equals(iterationNo)){
				if(cndnOnRRType.equals(GSTNConstants.RR_DISCOUNT_CHANGE)){
					a.setAmountAfterDiscount(a.getPreviousAmount() - a.getOfferAmount());
				}else{
					a.setAmountAfterDiscount(a.getPreviousAmount());
				}
				
				cnDnDetailsList.add(a);
			}
		}
		
		invoiceDetails.setCnDnList(cnDnDetailsList);
		invoiceDetails.setInvoiceNumber(invoiceNumber);
		invoiceDetails.setInvoiceValue(invoiceValue);
		invoiceDetails.setInvoiceValueAfterRoundOff(invoiceValueAfterRoundOff);
		invoiceDetails.setTotalTax(totalTax);
		invoiceDetails.setAmountAfterDiscount(amountAfterDiscount);
		invoiceDetails.setCnDnType(cnDnType);
		invoiceDetails.setAddChargesList(null);
		invoiceDetails.setFooterNote(footerNote);
		
		return invoiceDetails;
	}
	
	@RequestMapping(value = "/delInv",method=RequestMethod.POST)
	public @ResponseBody String deleteInvoice(@RequestParam Integer id, HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		boolean isInvoiceAllowed = false;	
		Map<String,String> mapResponse = new HashMap<String,String>();
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
		try {
			InvoiceDetails invoiceDetail = generateInvoiceService.getInvoiceDetailsById(id);
			
			isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(id,loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				
				/*if (!UOMUtil.isMapLoaded()) {
					List<UnitOfMeasurement> unitOfMeasurementList = (List<UnitOfMeasurement>) unitOfMeasurementService.getUnitOfMeasurement();
					UOMUtil.setUOMMap(unitOfMeasurementList);
				}*/
				
				String response = generateInvoiceService.deleteInvoice(loginMaster, invoiceDetail);
				
				if(response.equalsIgnoreCase("Success")){
					model.addAttribute("apiResponse",response);
					mapResponse.put(GSTNConstants.STATUS, GSTNConstants.SUCCESS);
					mapResponse.put(GSTNConstants.RESPONSE, "Invoice is deleted successfully");
				}else{
					model.addAttribute("apiResponse","error");
					mapResponse.put(GSTNConstants.STATUS, GSTNConstants.FAILURE);
					mapResponse.put(GSTNConstants.RESPONSE, "Error while deleting Invoice");
				}
				
			}else{
				mapResponse.put(GSTNConstants.STATUS, GSTNConstants.ACCESSVIOLATION);
				mapResponse.put(GSTNConstants.RESPONSE, "You do not have access to delete this invoice.");
			}
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
	
		logger.info("Exit");
		return new Gson().toJson(mapResponse);

	}
	
	
	@RequestMapping(value = "/genTinyUrl",method=RequestMethod.POST)
	public @ResponseBody String genTinyUrl(@RequestParam String invId, HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		String response = "";
		
		try {
			
			response = generateInvoiceService.getTinyUrl(invId);
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return new Gson().toJson(response);
	}
	
	public Map<String,String> getInvoicePdfPath(String invoiceId){
		logger.info("Entry");
		InvoiceDetails invoiceDetails = null;
		String pdfFilePath = null;
		Map<String,String> mapResponse = new HashMap<String,String>();
		System.out.println("invoiceId : "+invoiceId);
		
		try{
				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(Integer.parseInt(invoiceId)); 
				pdfFilePath = GSTNUtil.generateInvoicePdfFileName(invoiceDetails, invoiceDirectoryPath);
			
		}catch(Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		mapResponse.put(GSTNConstants.PDF_PATH, pdfFilePath);
		
		return mapResponse;
	}
	
	@RequestMapping(value = "/downloadInvoices", method = { RequestMethod.POST })
	public void downloadEWayBill(@RequestParam Integer id, HttpServletRequest httpRequest, HttpServletResponse response) {
		logger.info("download Invoice Entry");
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		boolean isInvoiceAllowed = false;	
		InvoiceDetails invoiceDetails = null;
		String pdfFilePath = null;
		String pdfFileName = null;
		boolean canDownloadPdf = false;
		try {
			isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(id,loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(id); 
				pdfFilePath = GSTNUtil.generateInvoicePdfFileName(invoiceDetails, invoiceDirectoryPath);
				pdfFileName = invoiceDetails.getInvoiceNumber()+".pdf";
				
				boolean isFilePresent = GSTNUtil.checkIfFileExists(pdfFilePath);
				if(!isFilePresent){
					UserMaster user = null;
					String logoImagePath = null;
					user = userMasterService.getUserMasterById( loginMaster.getuId());
					if(!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
						user = userMasterService.getUserMasterById(user.getReferenceId());
					}
					logoImagePath = userMasterService.getOrgLogoPath(loginMaster.getOrgUId());
					Map<String,String> mapResponse = generateInvoicePdf( invoiceDetails.getId(), invoiceDetails.getInvoiceNumber(), httpRequest,logoImagePath);
					if(mapResponse.get(GSTNConstants.STATUS).equals(GSTNConstants.SUCCESS)){
						canDownloadPdf = true;
					}else{
						logger.info("Since pdf file is not generated at path "+mapResponse.get(GSTNConstants.PDF_PATH)+" pdf was not downloaded");
					}
				}else{
					canDownloadPdf = true;
				}
				
				if(canDownloadPdf){
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);
					Path file = Paths.get(pdfFilePath);
					try {
						Files.copy(file, response.getOutputStream());
						response.getOutputStream().flush();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error in download :",e);
		}	
		logger.info("download Invoice Exit");
	}
	
	public class WorkerThread extends Thread {
		String invoiceId;
		String invoiceNumber;
		HttpServletRequest httpRequest;
		String logoImagePath;
		WorkerThread(String invoiceId, String invoiceNumber, HttpServletRequest httpRequest, String logoImagePath) {
			this.invoiceId=invoiceId;
			this.invoiceNumber=invoiceNumber;
			this.httpRequest=httpRequest;
			this.logoImagePath=logoImagePath;
		}
		public void run() {
			sendInvoicePdfInMailToCustomer(invoiceId, invoiceNumber, httpRequest, logoImagePath);
		}
	}
	
	
	@RequestMapping(value={"/getOnlyInvoicesList","/getOnlyBillOfSupplyList","/getRCInvoicesList","/getEComInvoicesList","/getEComBillOfSupplyList"}, method = RequestMethod.POST)
	public @ResponseBody String getDocumentListByDocType(@RequestParam("documentType") String documentType, HttpServletRequest httpRequest){
		logger.info("Entry");
		//String uri = GSTNUtil.getLastURIPart(httpRequest.getRequestURI());
		List<Object[]> invoiceList = null;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			invoiceList = generateInvoiceService.getDocumentListByDocType(loginMaster.getOrgUId(),documentType);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return new Gson().toJson(invoiceList);
	}
	
	@RequestMapping(value = "/downloadOldInvoices", method = { RequestMethod.POST })
	public void downloadOldInvoices(@RequestParam("id") Integer id, @RequestParam("iterationNo") Integer iterationNo,HttpServletRequest httpRequest, HttpServletResponse response) {
		logger.info("download Old Invoice Entry");
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		boolean isInvoiceAllowed = false;	
		InvoiceDetails invoiceDetails = null;
		String pdfFilePath = null;
		String pdfFileName = null;
		boolean canDownloadPdf = false;
		try {
			isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(id,loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(id);
				invoiceDetails.setIterationNo(iterationNo);
				pdfFilePath = GSTNUtil.generateInvoicePdfFileName(invoiceDetails, invoiceDirectoryPath);
				pdfFileName = invoiceDetails.getInvoiceNumber()+".pdf";
				
				boolean isFilePresent = GSTNUtil.checkIfFileExists(pdfFilePath);
				if(!isFilePresent){
					//this old pdf code needs to be developed.
					//As of now we assume that pdf is generated at beginning 
					UserMaster user = null;
					String logoImagePath = null;
					user = userMasterService.getUserMasterById( loginMaster.getuId());
					if(!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
						user = userMasterService.getUserMasterById(user.getReferenceId());
					}
					logoImagePath = userMasterService.getOrgLogoPath(loginMaster.getOrgUId());
					Map<String,String> mapResponse = generateOldInvoicePdf( invoiceDetails.getId(), iterationNo, httpRequest,logoImagePath);
					if(mapResponse.get(GSTNConstants.STATUS).equals(GSTNConstants.SUCCESS)){
						canDownloadPdf = true;
					}else{
						logger.info("Since pdf file is not generated at path "+mapResponse.get(GSTNConstants.PDF_PATH)+" pdf was not downloaded");
					}
				}else{
					canDownloadPdf = true;
				}
				
				if(canDownloadPdf){
					response.setContentType("application/pdf");
					response.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);
					Path file = Paths.get(pdfFilePath);
					try {
						Files.copy(file, response.getOutputStream());
						response.getOutputStream().flush();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error in download :",e);
		}	
		logger.info("download Old Invoice Exit");
	}
	
	private Map<String,String> generateOldInvoicePdf(Integer invoiceId, Integer iterationNo,HttpServletRequest httpRequest,String logoImagePath) {
		logger.info("Entry");
		InvoiceDetails invoiceDetails = null;
		GSTINDetails gstinDetails = null;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
		UserMaster user = null;
		State customerStateCode = null;
		Map<String,Map<String,Double>> gstMap = new HashMap<String,Map<String,Double>>();
		String amtInWords = null;
		Map<String,String> mapResponse = new HashMap<String,String>();
		try {
			user = userMasterService.getUserMasterById( loginMaster.getuId());
			if(!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
				user = userMasterService.getUserMasterById(user.getReferenceId());
			}
			invoiceDetails = getOldInvoiceDetailsById(invoiceId,iterationNo);
			gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(invoiceDetails.getGstnStateIdInString(),loginMaster.getPrimaryUserUId());
			customerStateCode = stateService.getStateByStateName(invoiceDetails.getCustomerDetails().getCustState());
			gstMap = GSTNUtil.convertListToMap(invoiceDetails.getServiceList());
			amtInWords = AmountInWordsModified.convertNumberToWords(""+invoiceDetails.getInvoiceValueAfterRoundOff());
			mapResponse = GenerateInvoicePdf.generatePdf(invoiceDetails, gstinDetails, user, customerStateCode.getStateCode(), gstMap, amtInWords,invoiceDirectoryPath,logoImagePath);
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return mapResponse;
	}

	private InvoiceDetails getOldInvoiceDetailsById(Integer id, Integer iterationNo) {
		InvoiceDetails oldInvoiceDetails = null;
		List<InvoiceDetails>invoiceDetailsInListObject = reviseAndReturnService.getOldInvoiceDetailsByIdByNativeQuery(id,iterationNo);
		List<InvoiceServiceDetails>serviceProductDetailsinListObject = reviseAndReturnService.getOldServiceProductDetailsByIdByNativeQuery(id,iterationNo);
		List<InvoiceAdditionalChargeDetails> addChargesInListObject = reviseAndReturnService.getOldAdditionalCharges(id,iterationNo);
		
		oldInvoiceDetails = invoiceDetailsInListObject.get(0); //history table dont have primary key so we need to use native query
		oldInvoiceDetails.setServiceList(serviceProductDetailsinListObject);
		oldInvoiceDetails.setAddChargesList(addChargesInListObject);
		return oldInvoiceDetails;
	}
	
	@RequestMapping(value = "/downloadcndninvoices", method = { RequestMethod.POST })
	public void downloadCnDnBill(@RequestParam("id") Integer invoiceId, @RequestParam("iterationNo") Integer iterationNo, @RequestParam("cId") Integer cndnInvoiceId,HttpServletRequest httpRequest, HttpServletResponse response, Model model) {
		logger.info("download Invoice Entry");
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		boolean isInvoiceAllowed = false;	
		InvoiceDetails invoiceDetails = null;
		String pdfFilePath = null;
		String pdfFileName = null;
		boolean canDownloadPdf = false;
		try {
			isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(invoiceId,loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(invoiceId);
				Map<String,String> verifyCnDn = verifyCnDnAgainstOrg(invoiceDetails,cndnInvoiceId,iterationNo);
				String logoImagePath = userMasterService.getOrgLogoPath(loginMaster.getOrgUId());
				if(verifyCnDn.get(GSTNConstants.RESPONSE).equals(GSTNConstants.SUCCESS)){
					pdfFilePath = verifyCnDn.get("pdfFilePath");
					 pdfFileName = verifyCnDn.get("pdfFileName");
					System.out.println("Go ahead");
					//first check if pdf file is present 
					//if present get the file path and send mail 
					// if not present generate the invoice pdf and call send mail
					boolean isFilePresent = GSTNUtil.checkIfFileExists(pdfFilePath);
					if(!isFilePresent){
						sendCnDnInvoicePdfInMailToCustomer(invoiceId.toString(),verifyCnDn.get("cndnInvoiceNumber"),httpRequest,logoImagePath,iterationNo, verifyCnDn.get("cnDnType"));
						canDownloadPdf = true;
					}else{
						canDownloadPdf = true;
					}
					
					if(canDownloadPdf){
						response.setContentType("application/pdf");
						response.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);
						Path file = Paths.get(pdfFilePath);
						try {
							Files.copy(file, response.getOutputStream());
							response.getOutputStream().flush();
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
				
			}
		} catch (Exception e) {
			logger.error("Error in download :",e);
		}	
		logger.info("download Invoice Exit");
	}
	
	@RequestMapping(value="/checkIfInvNumAlreadyPresent",method=RequestMethod.POST)
	public @ResponseBody String checkIfInvNumAlreadyPresent(@RequestParam("invoiceNumber") String invoiceNumber, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String isInvoicePresent = "FALSE";
		Map<String,String> mapResponse = new HashMap<String,String>();
		try {
			LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
			isInvoicePresent = generateInvoiceService.checkInvoiceNumberPresent(invoiceNumber,loginMaster.getOrgUId());
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		mapResponse.put("allowed_access", isInvoicePresent);
		return new Gson().toJson(mapResponse);
		
	}
	
}
