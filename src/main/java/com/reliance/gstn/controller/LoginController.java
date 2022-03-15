/**
 * 
 */
package com.reliance.gstn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.model.LoginAttempt;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.PushNotificationProfile;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.pushnotification.service.PushNotificationDetailsService;
import com.reliance.gstn.service.RegistrationService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
//import com.reliance.gstn.util.LoggerUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;
import com.reliance.gstn.util.TimeUtil;


/**
 * @author Nikesh.Bansod
 *
 */

@Controller
public class LoginController {
	  
	private static final Logger loginLogger = Logger.getLogger("loginfile");
	
	private static final Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	public RegistrationService registrationService;
	
	@Autowired
	private UserMasterService userMasterService;
	
	@Autowired
	private PushNotificationDetailsService pushNotificationDetailsService;
	
	@Value("${max_no_of_login_attempt}")
	private Integer maxLoginAttempts;

	@Value("${otp_idle_duration_min}")
	private String loginIdleTimeMin;
	
	@Value("${otp_idle_duration_hour}")
	private String loginIdleTimeHr;
	
	@Value("${${env}.notification.status}")
	private String notificationStatus;
	
	
	
	/*@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String decidingIndexPage(HttpServletRequest httpRequest, Model model) {
		return PageRedirectConstants.DETECTINGDEVICE;
	}*/
	
	
	@RequestMapping(value = {"/","/login"}, method = RequestMethod.GET)
	public String indexPage(HttpSession session,Model model ) {
		session.setAttribute("notificationStatus", notificationStatus);
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
	@RequestMapping(value="/loginP", method = RequestMethod.POST)
	public String login(Model model, @ModelAttribute("userMaster") UserMaster userMaster,HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.LOGIN_PAGE;
		String status = "Fail";
		Integer noOfAttempts =1;
		String mobHitsResponse = "";
		String response="";
		//no of attempt 
		//noofattemp<maxno  timediif
		try {
			String loggedInThroughStr = httpRequest.getParameter("loggedInThrough");
			String userId = userMaster.getUserId();
			UserMaster user  = new UserMaster();
				if(StringUtils.isNotBlank(userMaster.getUserId()) && StringUtils.isNotBlank(userMaster.getPassword())){
				List<LoginAttempt> loginAttemptList = userMasterService.getLoginAttemptDetails(userId);
				
				if(loginAttemptList.isEmpty()){
					LoginAttempt loginAttempt = new LoginAttempt();					
					user = userMasterService.getUserDetails(userMaster);
					
					if(user==null){
						//credentials does not match
						String responseAdd = userMasterService.addLoginAttemptRecord(userId,loginAttempt,noOfAttempts);
						pageRedirect = PageRedirectConstants.LOGIN_PAGE;
						model.addAttribute(GSTNConstants.LOGIN_ERROR, GSTNConstants.LOGIN_INVALID_CREDENTIALS);
					}else{
						if(loggedInThroughStr.equalsIgnoreCase("MOBILE")){
							user.setLoggedInDevice("MOBILE");
							user.setIMEINo(userMaster.getIMEINo());
							user.setDataSend(userMaster.getDataSend());
						}else{
							user.setLoggedInDevice("WIZARD");
						}
						LoginUtil.setLoginUser(httpRequest,user,"LOGIN");						
												
						mobHitsResponse = userMasterService.countLoginAttemptRecord(userId);
						if(mobHitsResponse.equals(GSTNConstants.SUCCESS)){
							logger.info("IMEI NO: "+userMaster.getIMEINo() +" Logged in :"+user.getLoggedInDevice()+" DATA SEND :"+userMaster.getDataSend());
							if(userMaster.getIMEINo() != "" && user.getLoggedInDevice().equals("MOBILE")){
								logger.info("Entry");
								try {
									response=pushNotificationDetailsService.pushAddorUpdateDetialsMicroService(userMaster.getUserId(), userMaster.getIMEINo());
								}catch(Exception e) {
									logger.debug("Push notification contactImei update fail" +response);
								}
								pageRedirect = PageRedirectConstants.REDIRECT_HOME_PAGE;
								status = "Successful";
								logger.info("Exit");
							} else {
								pageRedirect = PageRedirectConstants.REDIRECT_HOME_PAGE;
								status = "Successful";
							}
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
									pageRedirect = PageRedirectConstants.LOGIN_PAGE;
									model.addAttribute(GSTNConstants.LOGIN_ERROR, GSTNConstants.LOGIN_INVALID_CREDENTIALS);
								}else{
									String countResponse = userMasterService.removeLoginAttemptRecord(user.getUserId());
									//Credentials matches with block time constraint: access is provided by removing prev records 
									String responseRemove = userMasterService.removeLoginAttemptRecord(user.getUserId());
									if(loggedInThroughStr.equalsIgnoreCase("MOBILE")){
										user.setLoggedInDevice("MOBILE");
									}else{
										user.setLoggedInDevice("WIZARD");
									}
									LoginUtil.setLoginUser(httpRequest,user,"LOGIN");
									mobHitsResponse = userMasterService.countLoginAttemptRecord(userId);
									if(mobHitsResponse.equals(GSTNConstants.SUCCESS)){
										if(userMaster.getIMEINo() != "" && user.getLoggedInDevice().equals("MOBILE")){
											try {
												response=pushNotificationDetailsService.pushAddorUpdateDetialsMicroService(userMaster.getUserId(), userMaster.getIMEINo());
											}catch(Exception e) {
												logger.debug("Push notification contactImei update fail" +response);
											}
											pageRedirect = PageRedirectConstants.REDIRECT_HOME_PAGE;
											status = "Successful";
										} else {
											pageRedirect = PageRedirectConstants.REDIRECT_HOME_PAGE;
											status = "Successful";
										}
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
											pageRedirect = PageRedirectConstants.LOGIN_PAGE;
											model.addAttribute(GSTNConstants.LOGIN_ERROR, GSTNConstants.LOGIN_INVALID_CREDENTIALS);
										}else{
											//Credentials matches with block time constraint: access is provided by removing prev records 
											String responseRemove = userMasterService.removeLoginAttemptRecord(user.getUserId());
											if(loggedInThroughStr.equalsIgnoreCase("MOBILE")){
												user.setLoggedInDevice("MOBILE");
											}else{
												user.setLoggedInDevice("WIZARD");
											}
											LoginUtil.setLoginUser(httpRequest,user,"LOGIN");
											mobHitsResponse = userMasterService.countLoginAttemptRecord(userId);
											if(mobHitsResponse.equals(GSTNConstants.SUCCESS)){
												if(userMaster.getIMEINo() != "" && user.getLoggedInDevice().equals("MOBILE")){
													try {
														 response=pushNotificationDetailsService.pushAddorUpdateDetialsMicroService(userMaster.getUserId(), userMaster.getIMEINo());
													}catch(Exception e) {
														logger.debug("Push notification contactImei update fail" +response);
													}
													pageRedirect = PageRedirectConstants.REDIRECT_HOME_PAGE;
													status = "Successful";
												} else {
													pageRedirect = PageRedirectConstants.REDIRECT_HOME_PAGE;
													status = "Successful";	
												}
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
	
	@RequestMapping(value = "/notificationDetails", method = RequestMethod.GET)
	public String showNotifications(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		
		return PageRedirectConstants.NOTIFICATIONS_SHOW_PAGE;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession(false);
		if (httpRequest.isRequestedSessionIdValid() && session != null) {
			session.removeAttribute(GSTNConstants.LOGIN_USER);
			session.invalidate();			
			session=null;
		}
	   return "redirect:/login";
	}
	
	@RequestMapping("/logoutWithException")
	public void logoutWithException(HttpServletRequest httpRequest) throws Exception{
		
		HttpSession session = httpRequest.getSession(false);
		if (httpRequest.isRequestedSessionIdValid() && session != null) {
			session.removeAttribute(GSTNConstants.LOGIN_USER);
			session.invalidate();
			session=null;
		}
	  throw new Exception("Something Went Wrong");
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String homePage(Model model,HttpServletRequest httpRequest) {
		model.addAttribute("notificationStatus", notificationStatus);
		String redirect = null;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		if(loginMaster != null){
			model.addAttribute("loggedInThrough", loginMaster.getLoggedInThrough());
			if(loginMaster.getLoggedInThrough().equals("MOBILE")) {
				PushNotificationProfile profile=null;
				try {
					profile=pushNotificationDetailsService.fetchUserProfile(loginMaster.getImeiNo(),loginMaster.getDataSend());
					if(null!=profile) {
						model.addAttribute("pushNotificationProfile", profile);	
					}
					
				}catch(Exception e){
					logger.error("Error in:",e);
				}
			}
            /*if(loginMaster.getLoggedInByWizard() == true){
                  redirect =PageRedirectConstants.WIZARD_REDIRECT_HOME_PAGE;
            }else{*/
                  redirect = PageRedirectConstants.HOME_PAGE;
            /*}*/
			
		}else{
            redirect = "redirect:/logout";
		}
		return redirect;
	}
	
	@RequestMapping(value = "/logCustomError", method = RequestMethod.POST)
	public @ResponseBody String logCustomError(@RequestParam("status") String status, @RequestParam("responseText") String responseText,@RequestParam("error") String error, @RequestParam("methodName") String methodName,HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
		logger.info("Error occured for user[OrgId,uId,userName] { "+loginMaster.getOrgUId()+","+loginMaster.getuId()+","+loginMaster.getUserId()+" } from method url call : "+methodName+", Status Code : "+status +", Type : "+error+", Description : "+responseText);
		logger.info("Exit");
		//return PageRedirectConstants.CUSTOM_ERROR_PAGE;
		return new Gson().toJson(null);
	}
	
	@RequestMapping("/tError")
	public String tokenError(HttpServletRequest httpRequest) throws Exception{
		
		return PageRedirectConstants.INVALID_TOKEN_ERROR_PAGE;
	}
	
	@RequestMapping("/wtError")
	public String wizardTokenError(HttpServletRequest httpRequest) throws Exception{
		
		return PageRedirectConstants.WIZARD_INVALID_TOKEN_ERROR_PAGE;
	}

}
