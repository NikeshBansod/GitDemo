package com.reliance.gstn.external.wizard.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.reliance.gstn.external.wizard.service.WithoutInvoiceEwayBillService;
import com.reliance.gstn.model.EwayBillWIItem;
import com.reliance.gstn.model.EwayBillWITransaction;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.NicUserDetails;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.EWayBillService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

@Controller
public class WithoutInvoiceEwayBillController {
	
	private static final Logger logger = Logger.getLogger(WithoutInvoiceEwayBillController.class);

	@Autowired
	private WithoutInvoiceEwayBillService withoutInvoiceEwayBillService;
	
	@Autowired
	private EWayBillService eWayBillService;
	
	@Autowired
	private UserMasterService userMasterService;

	@Value("${client_id}")
	private String mobileClientId;

	@Value("${secret_key}")
	private String mobileSecretKey;

	@Value("${app_code}")
	private String mobileAppCode;
	
	@Value("${ip_usr}")
	private String ipUsr;

	@Value("${desktop.ewaybill.client_id}")
	private String desktopEwaybillClientId;

	@Value("${desktop.ewaybill.secret_key}")
	private String desktopEwaybillSecretKey;

	@Value("${desktop.ewaybill.app_code}")
	private String desktopEwaybillAppCode;
	
	@RequestMapping(value = "/getWIEwayBillPage", method = RequestMethod.POST)
	public String getWIEwayBillPage(@RequestParam String nicUserId, @RequestParam String nicPwd, @RequestParam String gstin, Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");	
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		EwayBillWIItem ewayBillWIItem = new EwayBillWIItem();
		String clientId = null, secretKey = null, appCode = null;
		if(loginMaster.getLoggedInThrough().equalsIgnoreCase("MOBILE")){
			clientId = mobileClientId;
			secretKey = mobileSecretKey;
			appCode = mobileAppCode;
		}else{
			clientId = desktopEwaybillClientId;
			secretKey = desktopEwaybillSecretKey;
			appCode = desktopEwaybillAppCode;
		}
		model.addAttribute("ewayBillWIItem", ewayBillWIItem);
		model.addAttribute("clientId", clientId);
		model.addAttribute("secretKey", secretKey);
		model.addAttribute("appCode", appCode);
		model.addAttribute("userId", loginMaster.getOrgUId());
		model.addAttribute("nicId", nicUserId);
		model.addAttribute("nicPassword", nicPwd);
		model.addAttribute("gstin", gstin);
		model.addAttribute("ipUsr", ipUsr);
		model.addAttribute("loggedInFrom", loginMaster.getLoggedInThrough());
		logger.info("Exit");
		//return PageRedirectConstants.GET_WITHOUT_INVOICE_EWAY_BILL_PAGE;
		return PageRedirectConstants.GET_GENERICE_EWAY_BILL_PAGE;
	}
	
	@RequestMapping(value = { "/saveWIEWayBillItem" }, method = RequestMethod.POST)
	public String saveWIEWayBillItem(@ModelAttribute("ewayBillWITransaction") EwayBillWITransaction ewayBillWITransaction, BindingResult result, Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = null;
		String pageRedirect = PageRedirectConstants.GET_WITHOUT_INVOICE_EWAY_BILL_PAGE;
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		try{			
			response = withoutInvoiceEwayBillService.saveWIEWayBillItem(ewayBillWITransaction,loginMaster);			
			model.addAttribute(GSTNConstants.RESPONSE, response);
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		model.addAttribute("msg", "done");
		logger.info("Exit");
		return pageRedirect;
	}

	@RequestMapping(value = "/generateEwayBills", method = RequestMethod.GET)
	public String generateEwayBill(Model model) {	
		return PageRedirectConstants.GET_GENERICE_EWAY_BILL_PAGE;
	}
		
	@RequestMapping(value = "/loginGenerateEwayBill", method = RequestMethod.GET)
	public String loginGenerateEwayBill(Model model, HttpServletRequest httpRequest) {
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		UserMaster user = null;
		try{
		user = userMasterService.getUserMasterById(loginMaster.getuId());
		} catch(Exception e){
			
		}		
		model.addAttribute("email_id", user.getDefaultEmailId());
		model.addAttribute("mobile_number", user.getUserId());
		model.addAttribute("clientId", mobileClientId);
		model.addAttribute("secretKey", mobileSecretKey);
		model.addAttribute("appCode", mobileAppCode);
		model.addAttribute("userId", loginMaster.getOrgUId());
		model.addAttribute("ipUsr", ipUsr);
		return PageRedirectConstants.GET_GENERICE_EWAY_BILL_LOGIN;
	}

	@RequestMapping(value ="/wizardGetGenericEWayBills", method = RequestMethod.GET)
	public String wizardGetGenericEWayBills(Model model, HttpServletRequest httpRequest) {	
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.GET_WIZARD_PRE_EWAY_BILL_PAGE;
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		String response = GSTNConstants.ALLOWED_ACCESS;
		model.addAttribute(GSTNConstants.RESPONSE, response);
		model.addAttribute("clientId", desktopEwaybillClientId);
		model.addAttribute("secretKey", desktopEwaybillSecretKey);
		model.addAttribute("appCode", desktopEwaybillAppCode);
		model.addAttribute("ipUsr", ipUsr);
		model.addAttribute("userId", loginMaster.getOrgUId());
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value ="/getGenericEWayBills", method = RequestMethod.GET)
		public String getGenericEWayBills(Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.LIST_EWAY_BILL_PAGE;
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		model.addAttribute("clientId", mobileClientId);
		model.addAttribute("secretKey", mobileSecretKey);
		model.addAttribute("appCode", mobileAppCode);
		model.addAttribute("ipUsr", ipUsr);
		model.addAttribute("userId", loginMaster.getOrgUId());
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value = "/wizardLoginGenerateEwayBill", method = RequestMethod.GET)
	public String wizardLoginGenerateEwayBill(Model model, HttpServletRequest httpRequest) {
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		UserMaster user = null;
		try{
		user = userMasterService.getUserMasterById(loginMaster.getuId());
		} catch(Exception e){
			
		}		
		model.addAttribute("email_id", user.getDefaultEmailId());
		model.addAttribute("mobile_number", user.getUserId());
		model.addAttribute("clientId", desktopEwaybillClientId);
		model.addAttribute("secretKey", desktopEwaybillSecretKey);
		model.addAttribute("appCode", desktopEwaybillAppCode);
		model.addAttribute("userId", loginMaster.getOrgUId());
		model.addAttribute("ipUsr", ipUsr);
		return PageRedirectConstants.GET_WIZARD_GENERICE_EWAY_BILL_LOGIN;
	}

	@RequestMapping(value = { "/cancelGenEWayBillPage" }, method = RequestMethod.POST)
	public String cancelGenEWayBillPage(@RequestParam("ewayBillNo") String ewayBillNo, @RequestParam("gstin") String gstin,
			HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.GET_GEN_CANCEL_EWAY_BILL_PAGE;
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		NicUserDetails nicUserDetails = null;		
		try {
				nicUserDetails = eWayBillService.getNicDetailsFromGstinAndOrgId(gstin,loginMaster.getOrgUId());
				if(nicUserDetails != null){
					model.addAttribute("userId", loginMaster.getOrgUId());
					model.addAttribute("gstin", gstin);
					model.addAttribute("nicUserDetails", nicUserDetails);
					model.addAttribute("ewaybillno", ewayBillNo);
					model.addAttribute("clientId", mobileClientId);
					model.addAttribute("secretKey", mobileSecretKey);
					model.addAttribute("appCode", mobileAppCode);
					model.addAttribute("ipUsr", ipUsr);
					model.addAttribute("loggedInFrom", loginMaster.getLoggedInThrough());
				} 
		} catch (Exception e) {
			logger.error("Error in:", e);
		}	
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value = "/getPreviewWIEwayBill", method = RequestMethod.POST)
	public String previewWIEwayBillbyNo (@RequestParam String id,@RequestParam Integer userId, HttpServletRequest httpRequest, Model model){
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.WIZARD_PRE_EWAY_BILL_PAGE_DETAILED;
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		model.addAttribute("ewaybillNo", id);
		model.addAttribute("clientId", mobileClientId);
		model.addAttribute("secretKey", mobileSecretKey);
		model.addAttribute("appCode", mobileAppCode);
		model.addAttribute("ipUsr", ipUsr);
		model.addAttribute("userId", loginMaster.getOrgUId());
		logger.info("Exit");
		return pageRedirect;
	}

	@RequestMapping(value = { "/cancelWizardGenEWayBillPage" }, method = RequestMethod.POST)
	public String cancelWizardGenEWayBillPage(@RequestParam("ewayBillNo") String ewayBillNo, @RequestParam("gstin") String gstin,
			HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.GET_WIZARD_GEN_CANCEL_EWAY_BILL_PAGE;
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		NicUserDetails nicUserDetails = null;		
		try {
				nicUserDetails = eWayBillService.getNicDetailsFromGstinAndOrgId(gstin,loginMaster.getOrgUId());
				if(nicUserDetails != null){
					model.addAttribute("userId", loginMaster.getOrgUId());
					model.addAttribute("gstin", gstin);
					model.addAttribute("nicUserDetails", nicUserDetails);
					model.addAttribute("ewaybillno", ewayBillNo);
					model.addAttribute("clientId", desktopEwaybillClientId);
					model.addAttribute("secretKey", desktopEwaybillSecretKey);
					model.addAttribute("appCode", desktopEwaybillAppCode);
					model.addAttribute("ipUsr", ipUsr);
					model.addAttribute("loggedInFrom", loginMaster.getLoggedInThrough());
				} 
		} catch (Exception e) {
			logger.error("Error in:", e);
		}	
		logger.info("Exit");
		return pageRedirect;
	}

	@RequestMapping(value = "/getpreviewgenericewaybill", method = RequestMethod.POST)
	public String previewGenericEwayBillbyNo (@RequestParam String id,@RequestParam Integer userId, HttpServletRequest httpRequest, Model model){
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.PREVIEW_GENERIC_EWAY_BILL_DETAILED;
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		model.addAttribute("ewaybillNo", id);
		model.addAttribute("clientId", mobileClientId);
		model.addAttribute("secretKey", mobileSecretKey);
		model.addAttribute("appCode", mobileAppCode);
		model.addAttribute("ipUsr", ipUsr);
		model.addAttribute("userId", loginMaster.getOrgUId());
		model.addAttribute("loggedInThrough", loginMaster.getLoggedInThrough());
		
		//dashboard variable for back button
		String conditionValue=httpRequest.getParameter("conditionValue");
		model.addAttribute("conditionValue", conditionValue);
		String startdate=httpRequest.getParameter("startdate");
		String enddate=httpRequest.getParameter("enddate");
		String onlyMonth=httpRequest.getParameter("onlyMonth");
		String onlyYear=httpRequest.getParameter("onlyYear");
		
		model.addAttribute("onlyMonth",onlyMonth);
		model.addAttribute("onlyYear",onlyYear);
		model.addAttribute("dash_startdate",startdate);
		model.addAttribute("dash_enddate",enddate);
		
		logger.info("Exit");
		return pageRedirect;
	}

}
