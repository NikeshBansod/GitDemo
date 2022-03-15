package com.reliance.gstn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.reliance.gstn.model.LoginAttempt;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.OtpBean;
import com.reliance.gstn.model.State;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.StateService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.BindingErrorUtil;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;
import com.reliance.gstn.util.TimeUtil;

/**
 * @author @kshay Mohite
 *
 */

@Controller
public class WizardLoginController {

	private static final Logger loginLogger = Logger.getLogger("loginfile");
	
	private static final Logger logger = Logger.getLogger(WizardLoginController.class);

	@Autowired
	private UserMasterService userMasterService;

	@Value("${REGISTRATION_OTP_INVALID}")
	private String regOtpInvalid;

	@Value("${REGISTRATION_SUCESS}")
	private String registrationSuccessful;

	@Value("${REGISTRATION_FAILURE}")
	private String registrationFailure;

	@Value("${DUPLICATE_ORG_NAME_FAILURE}")
	private String duplicateOrgName;

	@Value("${max_no_of_login_attempt}")
	private Integer maxLoginAttempts;

	@Value("${otp_idle_duration_min}")
	private String loginIdleTimeMin;
	
	@Value("${otp_idle_duration_hour}")
	private String loginIdleTimeHr;
	

	@Autowired
	StateService stateService;

	@RequestMapping(value = {"/wLogin"}, method = RequestMethod.GET)
	public String indexPage(Model model) {
		return PageRedirectConstants.LOGIN_PAGE;
	}
	
	/**
	 * This method will return login user details if user does not exist in
	 * database then it will be redirected on login page with error message
	 * 
	 * @param models
	 * @param loginMaster
	 * @param httpRequest
	 * @param httpSession
	 * @return redirection Page
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="/wloginn", method = RequestMethod.POST)
	public String login(Model model, @ModelAttribute("userMaster") UserMaster userMaster, HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.WIZARD_LOGIN_PAGE;
		String status = "Fail";
		Integer noOfAttempts =1;
		String deskHitsResponse = "";
		try {			
			String userId = userMaster.getUserId();
			UserMaster user  = new UserMaster();
			if(StringUtils.isNotBlank(userMaster.getUserId()) && StringUtils.isNotBlank(userMaster.getPassword()))
			{
				List<LoginAttempt> loginAttemptList = userMasterService.getLoginAttemptDetails(userId);			
				if(loginAttemptList.isEmpty()){
					LoginAttempt loginAttempt = new LoginAttempt();				
					user = userMasterService.getUserDetails(userMaster);				
					if(user==null){
						//credentials does not match
						String responseAdd = userMasterService.addLoginAttemptRecord(userId,loginAttempt,noOfAttempts);
						pageRedirect = PageRedirectConstants.WIZARD_LOGIN_PAGE;
						model.addAttribute(GSTNConstants.LOGIN_ERROR, GSTNConstants.LOGIN_INVALID_CREDENTIALS);
					}else{
						//LoginUtil.setLoginUser(httpRequest,user);
						LoginUtil.setLoginUserFromWizard(httpRequest,user);
						deskHitsResponse = userMasterService.countDeskLoginAttemptRecord(userId);
						if(deskHitsResponse.equals(GSTNConstants.SUCCESS)){
						pageRedirect = PageRedirectConstants.WIZARD_REDIRECT_HOME_PAGE;
						status = "Successful";
						} 
					}
				}else if(!loginAttemptList.isEmpty()){				
					for (LoginAttempt loginAttemptObj : loginAttemptList) {
						if(loginAttemptObj.getNoOfAttempts()<maxLoginAttempts){							
							user = userMasterService.getUserDetails(userMaster);							
							if(user==null){
								//credentials does not match
								noOfAttempts = loginAttemptObj.getNoOfAttempts()+1;
								String responseAdd = userMasterService.addLoginAttemptRecord(userId,loginAttemptObj,noOfAttempts);
								pageRedirect = PageRedirectConstants.WIZARD_LOGIN_PAGE;
								model.addAttribute(GSTNConstants.LOGIN_ERROR, GSTNConstants.LOGIN_INVALID_CREDENTIALS);
							}else{
								//Credentials matches with block time constraint: access is provided by removing prev records 
								String responseRemove = userMasterService.removeLoginAttemptRecord(user.getUserId());
								//LoginUtil.setLoginUser(httpRequest,user);
								LoginUtil.setLoginUserFromWizard(httpRequest,user);
								deskHitsResponse = userMasterService.countDeskLoginAttemptRecord(userId);
								if(deskHitsResponse.equals(GSTNConstants.SUCCESS)){
								pageRedirect = PageRedirectConstants.WIZARD_REDIRECT_HOME_PAGE;
								status = "Successful";
								} 
							}
						}else if(loginAttemptObj.getNoOfAttempts()==maxLoginAttempts){
							long diffMinutes = TimeUtil.getTimeDiffFromCurrentTimeInLong(loginAttemptObj.getLoginAttemptTime().getTime(), "minutes");
							Integer blockTimeinMin = Integer.parseInt(loginIdleTimeMin);
							if(diffMinutes >= blockTimeinMin){
								user = userMasterService.getUserDetails(userMaster);									
								if(user==null){
									//credentials does not match
									user = new UserMaster();
									noOfAttempts=1;
									String responseAdd = userMasterService.addLoginAttemptRecord(userId,loginAttemptObj,noOfAttempts);
									pageRedirect = PageRedirectConstants.WIZARD_LOGIN_PAGE;
									model.addAttribute(GSTNConstants.LOGIN_ERROR, GSTNConstants.LOGIN_INVALID_CREDENTIALS);
								}else{
									//Credentials matches with block time constraint: access is provided by removing prev records 
									String responseRemove = userMasterService.removeLoginAttemptRecord(user.getUserId());
									//LoginUtil.setLoginUser(httpRequest,user);
									LoginUtil.setLoginUserFromWizard(httpRequest,user);
									deskHitsResponse = userMasterService.countDeskLoginAttemptRecord(userId);
									if(deskHitsResponse.equals(GSTNConstants.SUCCESS)){
									pageRedirect = PageRedirectConstants.WIZARD_REDIRECT_HOME_PAGE;
									status = "Successful";
									}
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
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		loginLogger.trace("User : "+userMaster.getUserId()+", IP Address : "+GSTNUtil.getClientIpAddress(httpRequest)+", Login Status : "+status);
		logger.debug("User : "+userMaster.getUserId()+", IP Address : "+GSTNUtil.getClientIpAddress(httpRequest)+", Login Status : "+status);
		logger.info("Exit");
		return pageRedirect;
	}
	

	@RequestMapping(value = "/wHome", method = RequestMethod.GET)
	public String homePage(Model model) {		
		return PageRedirectConstants.WIZARD_HOME_PAGE;
	}
	
	@RequestMapping("/wlogout")
	public String logout(HttpServletRequest httpRequest) {		
		HttpSession session = httpRequest.getSession(false);
		if (httpRequest.isRequestedSessionIdValid() && session != null) {
			session.removeAttribute(GSTNConstants.LOGIN_USER);
			session.invalidate();
			session=null;
		}
	   return "redirect:/login";
	}
	
	@RequestMapping("/wlogoutWithException")
	public void logoutWithException(HttpServletRequest httpRequest) throws Exception{		
		HttpSession session = httpRequest.getSession(false);
		if (httpRequest.isRequestedSessionIdValid() && session != null) {
			session.removeAttribute(GSTNConstants.LOGIN_USER);
			session.invalidate();
			session=null;
		}
	  throw new Exception("Something Went Wrong");
	}
	
	@RequestMapping(value = "/wizardRegistration", method = RequestMethod.GET)
	public String getWizardRegistrationPage(@ModelAttribute("userMaster") UserMaster userMaster, Model model) {
		try{			
			model.addAttribute("userMaster", userMaster);
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		return PageRedirectConstants.WIZARD_REGISTRATION_PAGE;
	}		

	@RequestMapping(value="/wizardRegisteringUser",method=RequestMethod.POST)
	public String postRegistration(@Valid @ModelAttribute("userMaster") UserMaster userMaster, BindingResult result,Model model, HttpServletRequest httpRequest , HttpSession httpSession) {
		logger.info("Entry");
		String response = null;
		String status = GSTNConstants.FAILURE;
		String pageRedirect = PageRedirectConstants.WIZARD_REGISTRATION_PAGE;
		String otp = (String) httpRequest.getParameter("otp");
		OtpBean sessionOtp=(OtpBean)httpRequest.getSession().getAttribute("otpBean");
		try {
			if (!otp.equalsIgnoreCase(sessionOtp.getOtpNo())) {
				model.addAttribute(GSTNConstants.RESPONSE, regOtpInvalid);
			} else {
				if (!result.hasErrors()) {
					userMaster.setStatus("1");
					userMaster.setUserRole(GSTNConstants.PRIMARY_USER);
					response = userMasterService.addUserMaster(userMaster);
					if (response.equals(GSTNConstants.SUCCESS)) {
						model.addAttribute(GSTNConstants.RESPONSE,registrationSuccessful);
						pageRedirect = PageRedirectConstants.WIZARD_LOGIN_PAGE;
						status = GSTNConstants.SUCCESS;
					} else {
						model.addAttribute(GSTNConstants.RESPONSE,registrationFailure);
					}
				} else {
					BindingErrorUtil.getErrorResult(result, logger);
					model.addAttribute(GSTNConstants.RESPONSE,registrationFailure);
				}
			}
		} catch (Exception e) {
			BindingErrorUtil.getErrorResult(result, logger);
			logger.error("Error in:",e);
			String errMsg = " ";
			if(e instanceof ConstraintViolationException){
				errMsg =duplicateOrgName;
			}else{
				errMsg =registrationFailure;
			}
			model.addAttribute(GSTNConstants.RESPONSE,errMsg );
		}
		model.addAttribute(GSTNConstants.STATUS,status );
		httpRequest.getSession().removeAttribute("otpBean");
		logger.info("Exit");
		return pageRedirect;
	}
	
	


}
