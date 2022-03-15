/**
 * 
 */
package com.reliance.gstn.admin.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.admin.model.AdminFeedbackDetails;
import com.reliance.gstn.admin.model.AdminUserMaster;
import com.reliance.gstn.admin.model.DashboardAdmin;
import com.reliance.gstn.admin.service.AdminDashboardService;
import com.reliance.gstn.admin.service.AdminUserMasterService;
import com.reliance.gstn.model.LoginAttempt;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.Month;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.EncryptionUtil;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;
import com.reliance.gstn.util.TimeUtil;

/**
 * @author Nikesh.Bansod
 *
 */
@Controller
public class AdminLoginController {
	
	private static final Logger logger = Logger.getLogger(AdminLoginController.class);
	
	@Autowired
	private UserMasterService userMasterService;
	
	@Autowired
	private AdminUserMasterService adminUserMasterService;
	
	@Autowired
	private AdminDashboardService adminDashboardService;
	
	@Value("${max_no_of_login_attempt}")
	private Integer maxLoginAttempts;
	
	@Value("${otp_idle_duration_min}")
	private String loginIdleTimeMin;
	
	@Value("${otp_idle_duration_hour}")
	private String loginIdleTimeHr;
	
	
	@Value("${dropdownyears}")
	private int dropdownyears;
	
	@Value("${wizard.aes.encryption.key}")
	private String aesEncryptionkey;
	
	@RequestMapping(value = {"/idt/idtlogin"}, method = RequestMethod.GET)
	public String adminLoginPage(Model model) {
		
		return PageRedirectConstants.IDT_LOGIN_PAGE;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value="/idt/idtLogin", method = RequestMethod.POST)
	public String login(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.IDT_LOGIN_PAGE;
		String status = "Fail";
		Integer noOfAttempts =1;
		String userId = httpRequest.getParameter("uNameRd");
		String password = httpRequest.getParameter("nPassTowd");
		
		try{
			AdminUserMaster user  = new AdminUserMaster();
			if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(password)){
				List<LoginAttempt> loginAttemptList = userMasterService.getLoginAttemptDetails(userId);
				
				if(loginAttemptList.isEmpty()){
					LoginAttempt loginAttempt = new LoginAttempt();
					
					user = adminUserMasterService.getUserDetails(userId,password);
					
					if(user==null){
						//credentials does not match
						String responseAdd = userMasterService.addLoginAttemptRecord(userId,loginAttempt,noOfAttempts);
						pageRedirect = PageRedirectConstants.IDT_LOGIN_PAGE;
						model.addAttribute(GSTNConstants.LOGIN_ERROR, GSTNConstants.LOGIN_INVALID_CREDENTIALS);
					}else{
						LoginUtil.setAdminLoginUser(httpRequest,user);
						pageRedirect = PageRedirectConstants.REDIRECT_IDT_HOME_PAGE;
						status = "Successful";
					}
				}else if(!loginAttemptList.isEmpty()){
					
					for (LoginAttempt loginAttemptObj : loginAttemptList) {
							if(loginAttemptObj.getNoOfAttempts()< maxLoginAttempts){
								
								user = adminUserMasterService.getUserDetails(userId,password);
								
								if(user==null){
									//credentials does not match
									noOfAttempts = loginAttemptObj.getNoOfAttempts()+1;
									String responseAdd = userMasterService.addLoginAttemptRecord(userId,loginAttemptObj,noOfAttempts);
									pageRedirect = PageRedirectConstants.IDT_LOGIN_PAGE;
									model.addAttribute(GSTNConstants.LOGIN_ERROR, GSTNConstants.LOGIN_INVALID_CREDENTIALS);
								}else{
									//Credentials matches with block time constraint: access is provided by removing prev records 
									String responseRemove = userMasterService.removeLoginAttemptRecord(userId);
									LoginUtil.setAdminLoginUser(httpRequest,user);
									pageRedirect = PageRedirectConstants.REDIRECT_IDT_HOME_PAGE;
									status = "Successful";
								}
							}else if(loginAttemptObj.getNoOfAttempts()==maxLoginAttempts){
								long diffMinutes = TimeUtil.getTimeDiffFromCurrentTimeInLong(loginAttemptObj.getLoginAttemptTime().getTime(), "minutes");
								Integer blockTimeinMin = Integer.parseInt(loginIdleTimeMin);
								if(diffMinutes >= blockTimeinMin){
											user = adminUserMasterService.getUserDetails(userId,password);
											
												if(user==null){
													//credentials does not match
													user = new AdminUserMaster();
													noOfAttempts=1;
													String responseAdd = userMasterService.addLoginAttemptRecord(userId,loginAttemptObj,noOfAttempts);
													pageRedirect = PageRedirectConstants.IDT_LOGIN_PAGE;
													model.addAttribute(GSTNConstants.LOGIN_ERROR, GSTNConstants.LOGIN_INVALID_CREDENTIALS);
												}else{
													//Credentials matches with block time constraint: access is provided by removing prev records 
													String responseRemove = userMasterService.removeLoginAttemptRecord(userId);
													LoginUtil.setAdminLoginUser(httpRequest,user);
													pageRedirect = PageRedirectConstants.REDIRECT_IDT_HOME_PAGE;
													status = "Successful";
												}
											
										}else{
											//block the user
											model.addAttribute(GSTNConstants.LOGIN_ERROR, "3 Login Attempts Failed !!! you are blocked for "+(blockTimeinMin - diffMinutes)+" minutes");
											
										}
							}
					}
				}
			}else{
				//return back to loginPage
				model.addAttribute(GSTNConstants.LOGIN_ERROR, GSTNConstants.LOGIN_INVALID_CREDENTIALS);
			}
			
			
		}catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.debug("User : "+userId+", IP Address : "+GSTNUtil.getClientIpAddress(httpRequest)+", Login Status : "+status);
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value = "/idt/idthome", method = RequestMethod.GET)
	public String homePage(Model model) {
		
		return PageRedirectConstants.IDT_HOME_PAGE;
	}
	
	@RequestMapping("/idt/idtlogout")
	public String logout(HttpServletRequest httpRequest ) {
		
		HttpSession session = httpRequest.getSession(false);
		
		if (httpRequest.isRequestedSessionIdValid() && session != null) {
			session.removeAttribute(GSTNConstants.ADMIN_LOGIN_USER);
			session.invalidate();
			session=null;
		}
	   return "redirect:/idt/idtlogin";
	}
	
		
	@RequestMapping(value = {"/idt/listFeedbackDetails"}, method = RequestMethod.GET)
	public String listFeedbackDetails(Model model) {
		
		return PageRedirectConstants.LIST_FEEDBACK_DETAILS_LIST_PAGE;
	}
	
	
	@RequestMapping(value="/listFeedbackDetailsForm", method=RequestMethod.POST)
	public @ResponseBody String getFeedbackDetails(@RequestParam("masterDesc") Integer masterDesc, HttpServletRequest request ){
		logger.info("Entry");	
		List<AdminFeedbackDetails> lst = new ArrayList<AdminFeedbackDetails>();
		
		try{
			lst = adminUserMasterService.getUploadHistory(masterDesc);
		} catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(lst);
	}	
	

	@RequestMapping(value = "/idt/iDashboard",method = RequestMethod.GET)
	public String dashboaredPage(Model model,HttpServletRequest request) {
		logger.info("Entry");
		System.out.println("admin");
		model.addAttribute("dashboard", new DashboardAdmin());
	     logger.info("Exit");
		return PageRedirectConstants.ADMIN_DASHBOARD_PAGE;
	
}
	@GetMapping(value = "/idt/igetMonthList")
	public @ResponseBody List<Month> getMonthList()
	{
		return GSTNUtil.getMonths();
	}
	
	@GetMapping(value = "/idt/igetYearList")
	public @ResponseBody String getYearsList()
	{
		int Currentyear = Calendar.getInstance().get(Calendar.YEAR);
		List<String>years = new ArrayList<String>();
		years= GSTNUtil.getDropdownYears(Currentyear,dropdownyears);
		return new Gson().toJson(years);
	}
	
	
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/idt/igetDashboardDataAjax", method = RequestMethod.POST)
	public @ResponseBody String dashboaredPageData(@RequestBody DashboardAdmin dashboardadmin, HttpServletRequest httpRequest,Model model){
		logger.info("Entry"); 
		
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);		
		long mobileinvoiceCount = 0;
		long desktopinvoiceCount=0;
		long totalinvoiceCount=0;
		long genricEWaybillCount=0;
		long mobilegenricEWaybillCount=0;
		long desktopgenricEWaybillCount=0;
		long invoiceEWaybillCount=0;
		long mobileinvoiceEWaybillCount=0;
		long desktopinvoiceEWaybillCount=0;
		long wizardapplicationgenricEWaybillCount=0;
		
		String month=dashboardadmin.getStartdate();
		try {

			if (dashboardadmin.getStartdate().equalsIgnoreCase("All")) {
				dashboardadmin.setStartdate(GSTNConstants.DASHBOARD_STARTMONTH);
				dashboardadmin.setEnddate(GSTNConstants.DASHBOARD_ENDMONTH);
			}
			else {
				dashboardadmin.setEnddate(dashboardadmin.getStartdate());
			}
			    String year=dashboardadmin.getYears();
				String startdate = GSTNUtil.getMonthLimitDate(dashboardadmin.getStartdate(),dashboardadmin.getYears(), "dd-MM-yyyy", false);
				String enddate = GSTNUtil.getMonthLimitDate(dashboardadmin.getEnddate(),dashboardadmin.getYears(), "dd-MM-yyyy", true);
				dashboardadmin.setStartdate(startdate);
				dashboardadmin.setEnddate(enddate);
				System.out.println(startdate);
				System.out.println(enddate);
				System.out.println(year);
				EncryptionUtil ecr = new EncryptionUtil(aesEncryptionkey);
				mobileinvoiceCount = adminDashboardService.getMobileInvoiceCount(dashboardadmin);// for mobile invoice count,ecr.decrypt(appCode)
				desktopinvoiceCount = adminDashboardService.getDesktopInvoiceCount(dashboardadmin);// for desktop invoice count
				totalinvoiceCount = adminDashboardService.getGeneratedInvoiceCount(dashboardadmin);// for total invoice count 
				genricEWaybillCount= adminDashboardService.getGenericEwaybillCount(dashboardadmin);//for generic eway bill
				invoiceEWaybillCount= adminDashboardService.getInvoiceEwaybillCount(dashboardadmin);//for invoice ewaybill
				 mobilegenricEWaybillCount= adminDashboardService.getMobileGenericEwaybillCount(dashboardadmin);
				 desktopgenricEWaybillCount= adminDashboardService.getDesktopGenericEwaybillCount(dashboardadmin);
				 
				 mobileinvoiceEWaybillCount= adminDashboardService.getMobileInvoiceEwaybillCount(dashboardadmin);
				 desktopinvoiceEWaybillCount= adminDashboardService.getDesktopInvoiceEwaybillCount(dashboardadmin);
				 wizardapplicationgenricEWaybillCount= adminDashboardService.getWizardGenericEwaybillCount(dashboardadmin);
				
				dashboardadmin.setMobileinvoiceCount(mobileinvoiceCount);
				dashboardadmin.setDesktopinvoiceCount(desktopinvoiceCount);
				dashboardadmin.setTotalinvoiceCount(totalinvoiceCount); 
				dashboardadmin.setGenericEwaybillCount(genricEWaybillCount);
				dashboardadmin.setInvoiceEwaybillCount(invoiceEWaybillCount);
				dashboardadmin.setMobileGenericEwaybillCount(mobilegenricEWaybillCount);
				dashboardadmin.setDesktopGenericEwaybillCount(desktopgenricEWaybillCount);
				
				dashboardadmin.setMobileInvoiceEwaybillCount(mobileinvoiceEWaybillCount);
				dashboardadmin.setDesktopInvoiceEwaybillCount(desktopinvoiceEWaybillCount);
				dashboardadmin.setWizardGenericEwaybillCount(wizardapplicationgenricEWaybillCount);
		      }
		catch (Exception e) {
			logger.error("Error in:", e);
		      }		
		    logger.info("Exit");
		    String output=new Gson().toJson(dashboardadmin);
		    System.out.println(output);
		    logger.info(dashboardadmin);
		 return new Gson().toJson(dashboardadmin);
		 
		 
	   }
}