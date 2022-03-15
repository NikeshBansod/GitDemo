package com.reliance.gstn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.reliance.gstn.admin.service.AdditionalChargeDetailsService;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.State;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.GSTINDetailsService;
import com.reliance.gstn.service.GenerateInvoiceService;
import com.reliance.gstn.service.ManageServiceCatalogueService;
import com.reliance.gstn.service.ProductService;
import com.reliance.gstn.service.StateService;
import com.reliance.gstn.service.UnitOfMeasurementService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.AmountInWords;
import com.reliance.gstn.util.AmountInWordsModified;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/*author @kshay*/

@Controller
public class WizardGenerateInvoiceController {
	
	private static final Logger logger = Logger.getLogger(WizardGenerateInvoiceController.class);
	
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
	
	@RequestMapping(value="/wGenerateInvoice", method = RequestMethod.GET)
	public String getWizardGenerateInvoicePage(Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String pageRedirect = null;
		boolean isGstinPresent = false;
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
					pageRedirect = PageRedirectConstants.WIZARD_Generate_Invoice_PAGE;
				}else{
					pageRedirect = PageRedirectConstants.WIZARD_GENERATE_INVOICE_EXCEPTION_PAGE;
				}				
			}else{
				pageRedirect = PageRedirectConstants.WIZARD_GENERATE_INVOICE_EXCEPTION_PAGE;
			}			
			model.addAttribute("userDetails", user);
			model.addAttribute("isGstinPresent", isGstinPresent);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return pageRedirect;
	}
		
	@RequestMapping(value = "/getWizardMyInvoices", method = RequestMethod.GET)
	public String getMyInvoicesPage(Model model, HttpServletRequest httpRequest) {
		return PageRedirectConstants.WIZARD_GET_MY_INVOIVES_PAGE;
	}

	@RequestMapping(value = {"/getWizardInvoiceDetails","/getWizardInvoiceCNDNDetails","/getWizardDashboardInvoiceDetails"},method=RequestMethod.POST)
	public String getWizardInvoiceDetailsById(@RequestParam Integer id, HttpServletRequest httpRequest, Model model) {
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
			logger.error("Error in:",e);
		}
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
		model.addAttribute("invoiceDetails", invoiceDetails);
		model.addAttribute("gstinDetails", gstinDetails);
		model.addAttribute("userMaster", user);
		model.addAttribute("customerStateCode", customerStateCode.getStateCode());
		model.addAttribute("gstMap", gstMap);
		model.addAttribute("amtInWords", AmountInWordsModified.convertNumberToWords(""+invoiceDetails.getInvoiceValueAfterRoundOff()));
		
		if(uri.equals("/getWizardInvoiceDetails")){
			pageRedirect = PageRedirectConstants.WIZARD_VIEW_INVOICE_PAGE;
		}else if(uri.equals("/getWizardInvoiceCNDNDetails")){
			pageRedirect = PageRedirectConstants.WIZARD_INVOICE_CN_DN_PAGE;
		}
		else if(uri.equals("/getWizardDashboardInvoiceDetails")){
			System.out.println("baloo");
			pageRedirect = PageRedirectConstants.WIZARD_DASHBOARD_VIEW_INVOICE_PAGE;
		}	
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value = {"/getWizardCnDn"},method=RequestMethod.POST)
	public String getCnDnById(@RequestParam Integer id, HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		String uri = GSTNUtil.getLastURIPart(httpRequest.getRequestURI());
		String pageRedirect=null;
		

		
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
			logger.error("Error in:",e);
		}
		model.addAttribute("gstinDetails", gstinDetails);
		model.addAttribute("customerStateCode", customerStateCode.getStateCode());
		model.addAttribute("userMaster", user);
		model.addAttribute("invoiceDetails", invoiceDetails);
		model.addAttribute(GSTNConstants.RESPONSE, response);	
		if(uri.equals("/getWizardCnDn")){
			pageRedirect = PageRedirectConstants.WIZARD_PRE_INVOICE_CN_DN_PAGE;
		}
		
		logger.info("Exit");
		return pageRedirect;
	}
	

}
