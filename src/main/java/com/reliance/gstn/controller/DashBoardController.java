/**
 * 
 */
package com.reliance.gstn.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.model.CnDnAdditionalDetails;
import com.reliance.gstn.model.Dashboard;
import com.reliance.gstn.model.EWayBill;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.InvoiceCnDnDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.Month;
import com.reliance.gstn.model.PayloadCnDnDetails;
import com.reliance.gstn.model.State;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.DashBoardService;
import com.reliance.gstn.service.EWayBillService;
import com.reliance.gstn.service.GSTINDetailsService;
import com.reliance.gstn.service.GenerateInvoiceService;
import com.reliance.gstn.service.StateService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.AmountInWordsModified;
import com.reliance.gstn.util.EncryptionUtil;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Sagar.Parab
 *
 */
@Controller
public class DashBoardController {
	
	private static final Logger logger = Logger.getLogger(DashBoardController.class);
	
	@Autowired
	private DashBoardService dashBoardService;
	
	@Autowired
	GenerateInvoiceService generateInvoiceService;
	@Autowired
	private UserMasterService userMasterService;
	@Autowired
	private GSTINDetailsService gstinDetailsService;
	@Autowired
	private StateService stateService;
	
	@Autowired
	private EWayBillService eWayBillService;
	
	@Value("${client_id}")
	private String clientId;

	@Value("${secret_key}")
	private String secretKey;

	@Value("${app_code}")
	private String appCode;
	
	@Value("${ip_usr}")
	private String ipUsr;
	
	
	@Value("${dropdownyears}")
	private int dropdownyears;
	
	@Value("${wizard.aes.encryption.key}")
	private String aesEncryptionkey;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboaredPage(Model model,HttpServletRequest request) {
		logger.info("Entry");
		model.addAttribute("dashboard", new Dashboard());
	     logger.info("Exit");
		return PageRedirectConstants.DASHBOARD_PAGE;
	}
	@RequestMapping(value = "/getDashboardDataAjax", method = RequestMethod.POST)
	public @ResponseBody String dashboaredPageData(@RequestBody Dashboard dashboard, HttpServletRequest httpRequest,Model model){
		logger.info("Entry"); 
		
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);		
		long eWaybillCount = 0;
		long cndnCount=0;
		long invoiceCount=0;	
		long invoiceEWaybillCount=0;
		String month=dashboard.getStartdate();
		try {

			if (dashboard.getStartdate().equalsIgnoreCase("All")) {
				dashboard.setStartdate(GSTNConstants.DASHBOARD_STARTMONTH);
				dashboard.setEnddate(GSTNConstants.DASHBOARD_ENDMONTH);
			}
			else {
				dashboard.setEnddate(dashboard.getStartdate());
			}
			    String year=dashboard.getYears();
				String startdate = GSTNUtil.getMonthLimitDate(dashboard.getStartdate(),dashboard.getYears(), "dd-MM-yyyy", false);
				String enddate = GSTNUtil.getMonthLimitDate(dashboard.getEnddate(),dashboard.getYears(), "dd-MM-yyyy", true);
				dashboard.setStartdate(startdate);
				dashboard.setEnddate(enddate);
				EncryptionUtil ecr = new EncryptionUtil(aesEncryptionkey);
				eWaybillCount = dashBoardService.getInvoiceCount(dashboard, loginMaster.getOrgUId(),ecr.decrypt(appCode));// for eway bill
				cndnCount = dashBoardService.getCnDnNote(dashboard,loginMaster.getOrgUId());// for Credit Debit note
				invoiceCount = dashBoardService.getGeneratedInvoiceCount(dashboard, loginMaster.getOrgUId());// for Generated Invoice count
				invoiceEWaybillCount=dashBoardService.getInvoiceEWaybillCount(dashboard, loginMaster.getOrgUId());//for generic eway bill count
				dashboard.setCndnCount(cndnCount);
                dashboard.setInvoiceCount(invoiceCount);
                dashboard.seteWaybillCount(eWaybillCount);
                dashboard.setInvoiceEWaybillCount(invoiceEWaybillCount);
                dashboard.setOnlyMonth(month);
                dashboard.setOnlyYear(year);
		      }
		catch (Exception e) {
			logger.error("Error in:", e);
		      }		
		    logger.info("Exit");
		 return new Gson().toJson(dashboard);
	   }
	
	@SuppressWarnings("unused")
	@RequestMapping(value ="/showAllRecordsList", method = RequestMethod.POST)
	public String showAllRecords(@RequestParam String startdate,@RequestParam String enddate,@RequestParam String getInvType, HttpServletRequest httpRequest,Model model){
		logger.info("Entry"); 
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);		
		String pageRedirect = null;
		Dashboard dashboard=new Dashboard();
		UserMaster user = null;
		List<Object[]> invoiceList = null;
		List<Object[]> CNDNList=null;
		List<Object[]> ewaybillList=null;
		List<Object[]> invoiceewaybillList=null;
		try {	
			EncryptionUtil ecr = new EncryptionUtil(aesEncryptionkey);
				dashboard.setStartdate(startdate);
				dashboard.setEnddate(enddate);
				
                if(getInvType.equalsIgnoreCase(GSTNConstants.DASHBOARD_TYPE_INVOICED))
                {
				invoiceList = dashBoardService.getGeneratedInvoiceList(dashboard, loginMaster.getOrgUId(),getInvType,ecr.decrypt(appCode));// for Generated Invoice count
				model.addAttribute("invoiceList", invoiceList);
				pageRedirect=PageRedirectConstants.DASHBOARD_LIST_INVOICE_PAGE;
                
                }
                else if(getInvType.equalsIgnoreCase(GSTNConstants.DASHBOARD_TYPE_CNDN))
                {
                	
                	user = userMasterService.getUserMasterById( loginMaster.getuId());
					if(!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
						user = userMasterService.getUserMasterById(user.getReferenceId());
					}
                	model.addAttribute("userMaster", user);
                	CNDNList = dashBoardService.getGeneratedInvoiceList(dashboard, loginMaster.getOrgUId(),getInvType,ecr.decrypt(appCode));
                	model.addAttribute("CNDNList", CNDNList);
                	pageRedirect=PageRedirectConstants.DASHBOARD_LIST_CNDN_PAGE;
                }
                else if(getInvType.equalsIgnoreCase(GSTNConstants.DASHBOARD_TYPE_EWAYBILL))
                {
                	ewaybillList = dashBoardService.getGeneratedInvoiceList(dashboard, loginMaster.getOrgUId(),getInvType,ecr.decrypt(appCode));
                	model.addAttribute("ewaybillList", ewaybillList);
                	pageRedirect=PageRedirectConstants.DASHBOARD_LIST_EWAYBILL_PAGE;
                }
                else if(getInvType.equalsIgnoreCase(GSTNConstants.DASHBOARD_TYPE_EWAYBILL_INVOICE))
                {
                	invoiceewaybillList = dashBoardService.getGeneratedInvoiceList(dashboard, loginMaster.getOrgUId(),getInvType,ecr.decrypt(appCode));
                	model.addAttribute("invoiceewaybillList", invoiceewaybillList);
                	pageRedirect=PageRedirectConstants.DASHBOARD_LIST_INVOICEEWAYBILL_PAGE;
                }
			
                String onlyMonth=httpRequest.getParameter("onlyMonth");
                String onlyYear=httpRequest.getParameter("onlyYear");
               
				String conditionValue=httpRequest.getParameter("conditionValue");
                model.addAttribute("conditionValue", getInvType);
				model.addAttribute("startdate", startdate);
				model.addAttribute("enddate", enddate);
				model.addAttribute("onlyMonth", onlyMonth);
				model.addAttribute("onlyYear", onlyYear);
				model.addAttribute("clientId", clientId);
				model.addAttribute("secretKey", secretKey);
				model.addAttribute("appCode", appCode);
				model.addAttribute("userId", loginMaster.getOrgUId());
				model.addAttribute("ipUsr", ipUsr);
		      }
		catch (Exception e) {
			logger.error("Error in:", e);
		      }		
		  logger.info("Exit");
		
		
		return pageRedirect;
	   }
	/*
	@RequestMapping(value = {"/getCNDNPreviewDetails"},method=RequestMethod.POST)
	public String getInvoiceDetailsById(@RequestParam Integer id ,@RequestParam Integer iterationNo,@RequestParam Integer cId,@RequestParam String cndnNumber,HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		String OrgInvoiceNumber=null;
        UserMaster user = null;
		InvoiceDetails invoiceDetails = null;
		@SuppressWarnings("unused")
		boolean isInvoiceAllowed = false;
		GSTINDetails gstinDetails = null;
		List<PayloadCnDnDetails> dash_productDetails=new ArrayList<PayloadCnDnDetails>();
		Map<String, Map<String, Double>> gstMap=new HashMap<String,Map<String,Double>>();
		List<CnDnAdditionalDetails> dash_invoiceCNDNdetails=new ArrayList<CnDnAdditionalDetails>();
		State customerStateCode = null;
		try {
			 LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
			 isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(id,loginMaster.getOrgUId());
			 user = userMasterService.getUserMasterById( loginMaster.getuId());
			 invoiceDetails	 = generateInvoiceService.getInvoiceDetailsById(id);
			 OrgInvoiceNumber=invoiceDetails.getInvoiceNumber();
			 gstMap = GSTNUtil.convertListToMapForCnDn(invoiceDetails.getCnDnList(), iterationNo);
			 invoiceDetails = modifyInvoiceDetailsAsPerCnDn(invoiceDetails,iterationNo,cId);
			 gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(invoiceDetails.getGstnStateIdInString(),loginMaster.getPrimaryUserUId());
			 dash_productDetails=dashBoardService.getProductDetailsForDash(cndnNumber);
			 dash_invoiceCNDNdetails=dashBoardService.getCNDNdetails(cId);
			 customerStateCode = stateService.getStateByStateName(invoiceDetails.getCustomerDetails().getCustState());	
		     }
		 catch (Exception e) {
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
		      model.addAttribute("OrgInvoiceNumber", OrgInvoiceNumber);
		      model.addAttribute("dash_productDetails", dash_productDetails);
		      model.addAttribute("dash_invoiceCNDNdetails", dash_invoiceCNDNdetails);
		      model.addAttribute("invoiceDetails", invoiceDetails);
		      model.addAttribute("gstinDetails", gstinDetails);
		      model.addAttribute("customerStateCode", customerStateCode.getStateCode());
		      model.addAttribute("userMaster", user);
		      model.addAttribute("gstMap",gstMap);
		      model.addAttribute("amtInWords", AmountInWordsModified.convertNumberToWords(""+invoiceDetails.getInvoiceValueAfterRoundOff()));
		   logger.info("Exit");
		return PageRedirectConstants.VIEW_CNDN_DASHBOARD_PAGE;
	}
	
	private InvoiceDetails modifyInvoiceDetailsAsPerCnDn(InvoiceDetails invoiceDetails, Integer iterationNo,Integer cndnInvoiceId) {
		String invoiceNumber = null;
		double invoiceValue = 0d;
		double invoiceValueAfterRoundOff = 0d;
		double totalTax = 0d;
		double amountAfterDiscount = 0d;
		String cnDnType = null;
		String footerNote = null;
		for(CnDnAdditionalDetails cndnAdditionalDetails : invoiceDetails.getCnDnAdditionalList()){
			if((cndnAdditionalDetails.getIterationNo().equals(iterationNo)) && (cndnAdditionalDetails.getId().equals(cndnInvoiceId))){
				invoiceNumber = cndnAdditionalDetails.getInvoiceNumber();
				invoiceValue = cndnAdditionalDetails.getInvoiceValue();
				invoiceValueAfterRoundOff = cndnAdditionalDetails.getInvoiceValueAfterRoundOff();
				totalTax = cndnAdditionalDetails.getTotalTax();
				amountAfterDiscount = cndnAdditionalDetails.getAmountAfterDiscount();
				cnDnType = cndnAdditionalDetails.getCnDnType();
				footerNote = cndnAdditionalDetails.getFooter();
			  }
		    }
		
		//create cndnList
		List<InvoiceCnDnDetails> cnDnDetailsList = new ArrayList<InvoiceCnDnDetails>();
		for(InvoiceCnDnDetails a : invoiceDetails.getCnDnList()){
			if(a.getIterationNo().equals(iterationNo)){
				a.setAmountAfterDiscount(a.getPreviousAmount());
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
	}*/
	
/*	@RequestMapping(value = "/getInvoiceEwayPreviewDetails",method=RequestMethod.POST)
	public String getInvoiceEwayBillPreview(@RequestParam String ewaybillNo, @RequestParam Integer invoiceId ,HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		List<EWayBill> invoiceEWayBill=new ArrayList<EWayBill>();
		InvoiceDetails invoiceDetails = null;
		List<EWayBill> ewaybillList = null;
		GSTINDetails gstinDetails = null;
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		String response = GSTNConstants.NOT_ALLOWED_ACCESS;
		UserMaster user = null;
		try {
			invoiceEWayBill=dashBoardService.getInvoiceEwayBillWITransactionDetails(ewaybillNo);
			boolean isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(invoiceId, loginMaster.getOrgUId());
			if (isInvoiceAllowed) {
				user = userMasterService.getUserMasterById(loginMaster.getuId());
				if (!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER))
				{
					user = userMasterService.getUserMasterById(user.getReferenceId());
				}
				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(invoiceId);
				ewaybillList = eWayBillService.getEwayBillsByInvoiceId(invoiceId);
				gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(invoiceDetails.getGstnStateIdInString(),loginMaster.getPrimaryUserUId());
				response = GSTNConstants.ALLOWED_ACCESS;
			} 
		else {
				invoiceDetails = new InvoiceDetails();
			}
		}
		 catch (Exception e) {
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
		        model.addAttribute("invoiceEWayBill", invoiceEWayBill);
		        model.addAttribute("ewaybillList", ewaybillList);
		   		model.addAttribute("userMaster", user);
		   		model.addAttribute("invoiceDetails", invoiceDetails);
		   		model.addAttribute("gstinDetails", gstinDetails);
		   		model.addAttribute(GSTNConstants.RESPONSE, response);
		      logger.info("Exit");
		return PageRedirectConstants.VIEW_INVOICE_EWAYBILL_DASHBOARD_PREVIEW_PAGE;
	}*/
	
	@PostMapping(value = "/gotoDashboard")
	public String gotoDashboard(@RequestParam("onlyMonth") String onlyMonth ,@RequestParam("onlyYear") String onlyYear,Model model,HttpServletRequest request) {
		logger.info("Entry");
		       model.addAttribute("dashboard", new Dashboard());
		       model.addAttribute("onlyMonth", onlyMonth);
		       model.addAttribute("onlyYear", onlyYear);
		       model.addAttribute("traverseFrom", "gotoDashboard");
		logger.info("Exit");
		   return PageRedirectConstants.DASHBOARD_PAGE;
	}
	
	@GetMapping(value = "/getMonthList")
	public @ResponseBody List<Month> getMonthList()
	{
		return GSTNUtil.getMonths();
	}
	
	@GetMapping(value = "/getYearList")
	public @ResponseBody String getYearsList()
	{
		int Currentyear = Calendar.getInstance().get(Calendar.YEAR);
		List<String>years = new ArrayList<String>();
		years= GSTNUtil.getDropdownYears(Currentyear,dropdownyears);
		return new Gson().toJson(years);
	}
	
	
	/*@RequestMapping(value = {"/getcndndetails"},method=RequestMethod.POST)
	public @ResponseBody String getCnDnById(@RequestParam Integer id, HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.DASHBOARD_LIST_CNDN_PAGE;
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
		return new Gson().toJson(model);
	}
	*/
	@RequestMapping(value = { "/getEWayBillsInDashboard" }, method = RequestMethod.POST)
	public String getEWayBillsInDashboard(@RequestParam Integer id, @RequestParam Integer ewaybillid,HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.VIEW_INVOICE_EWAYBILL_DASHBOARD_PREVIEW_PAGE;
		InvoiceDetails invoiceDetails = null;
		List<EWayBill> ewaybillList = null;
		GSTINDetails gstinDetails = null;

		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		String response = GSTNConstants.NOT_ALLOWED_ACCESS;
		UserMaster user = null;
		boolean isInvoiceAllowed = false;
		try {
			isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(id, loginMaster.getOrgUId());
			if (isInvoiceAllowed) {
				user = userMasterService.getUserMasterById(loginMaster.getuId());
				if (!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)) {
					user = userMasterService.getUserMasterById(user.getReferenceId());
				}

				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(id);
				ewaybillList = eWayBillService.getEwayBillsByInvoiceId(id);
				gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(invoiceDetails.getGstnStateIdInString(),
						loginMaster.getPrimaryUserUId());
				response = GSTNConstants.ALLOWED_ACCESS;
			} else {
				invoiceDetails = new InvoiceDetails();
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:", e);
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
		model.addAttribute("loggedInThrough", loginMaster.getLoggedInThrough());
		model.addAttribute("isInvoiceAllowed", isInvoiceAllowed);
		model.addAttribute("ewaybillList", ewaybillList);
		model.addAttribute("userMaster", user);
		model.addAttribute("invoiceDetails", invoiceDetails);
		model.addAttribute("gstinDetails", gstinDetails);
		model.addAttribute("ewaybillid",ewaybillid);

		logger.info("Exit");
		return PageRedirectConstants.VIEW_INVOICE_EWAYBILL_DASHBOARD_PREVIEW_PAGE;
	}
	

}
