/**
 * 
 */
package com.reliance.gstn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.filter.CaptchaUtil;
import com.reliance.gstn.model.FooterBean;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.OrganizationMaster;
import com.reliance.gstn.model.OtpBean;
import com.reliance.gstn.model.State;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.GenerateInvoiceService;
import com.reliance.gstn.service.StateService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.BindingErrorUtil;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Nikesh.Bansod
 *
 */
@Controller
public class UserMasterController {
	
	private static final Logger logger = Logger.getLogger(UserMasterController.class);
	
	@Autowired
	private UserMasterService userMasterService;
	

	@Value("${CONFIRM_INVOICE_SUCESS}")
	private String invoiceSuccessful;
	
	@Value("${CONFIRM_INVOICE_FAILURE}")
	private String invoiceFailure;
	
	@Value("${REGISTRATION_SUCESS}")
	private String registrationSuccessful;
	
	@Value("${REGISTRATION_FAILURE}")
	private String registrationFailure;
	
	@Value("${PROFILE_UPDATE_SUCESS}")
	private String profileUpdationSuccessful;
	
	@Value("${PROFILE_UPDATE_FAILURE}")
	private String profileUpdationFailure;
	
	@Value("${CHANGE_PASSWORD_SUCCESS}")
	private String changePasswordSuccessful;
	
	@Value("${CHANGE_PASSWORD_FAILURE}")
	private String changePasswordFailure;
	
	@Value("${CHANGE_PASSWORD_UPDATE_FAILURE}")
	private String changePasswordUpdateFailure;
	
	@Value("${SECONDARY_USER_SUCESS}")
	private String secondaryUserAddSuccessful;
	
	@Value("${SECONDARY_USER_UPDATE_SUCESS}")
	private String secondaryUserEditSuccessful;
	
	@Value("${SECONDARY_USER_FAILURE}")
	private String secondaryUserAddFailure;
	
	@Value("${SECONDARY_USER_INVALID_INPUTS}")
	private String secondaryUserInvalidInputs;
	
	@Value("${SECONDARY_USER_DELETION_SUCCESS}")
	private String secondaryUserDeletionSuccessful;
	
	@Value("${SECONDARY_USER_DELETION_FAILURE}")
	private String secondaryUserDeletionFailure;
	
	@Value("${CAPTCHA_MISMATCH}")
	private String captchaMismatch;
	
	@Value("${CONFIRM_INVOICE_UPDATE_SUCESS}")
	private String invoiceUpdationSuccessful;
	
	@Value("${CONFIRM_INVOICE_UPDATE_FAILURE}")
	private String invoiceUpdationFailure;
	
	@Value("${DUPLICATE_SECONDARY_USER_FAILURE}")
	private String duplicateAddSecUser;
	
	@Value("${DUPLICATE_ORG_NAME_FAILURE}")
	private String duplicateOrgName;
	
	@Value("${CHANGE_PASSWORD_PATTERN_FAILURE}")
	private String changePasswordPatternFailure;
	
	@Value("${DATA_MANIPULATION_EXCEPTION}")
	private String dataManipulationException;
	
	@Value("${REGISTRATION_OTP_INVALID}")
	private String regOtpInvalid;
	
	@Value("${INVALID_MAPPING_EXCEPTION}")
	private String InvalidMappingException;
	
	@Value("${ACCOUNT_DELETION_SUCCESS}")
	private String accountDeletionSuccess;
	
	@Value("${ACCOUNT_DELETION_FAILED}")
	private String accountDeletionFailure;
	
	@Value("${GST_USER_LOGIN_SUCESS}")
	private String gstUserLoginSuccessful;
	
	@Value("${${env}.PATH_FOR_LOGO_IMG}")
	private String pathForLogoImage;
	
	@Value("${${env}.PATH_FOR_INVOICE_PDF}")
	private String invoiceDirectoryPath;

	@Value("${USER_MAPPED_EXCEPTION}")
	private String InvalidUserMappingException;
	
	@Value("${OLD_PASSWORD_SAME_NEW_PASSWORD}")
	private String oldPasswordAndNewPasswordSame;
	
	@Autowired
	StateService stateService;
	
	@Autowired
	GenerateInvoiceService generateInvoiceService;
	
	@Value("${FOOTER_ADD_FAILURE}")
	private String footerAdditionFailure;
	
	@Value("${FOOTER_ADD_SUCESS}")
	private String footerAdditionSuccess;
	
	
	@ModelAttribute("userMaster")
	public UserMaster construct(){
		return new UserMaster();
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegistrationPage(Model model) {
		return PageRedirectConstants.REGISTRATION_PAGE;
	}
	
	@RequestMapping(value="/registration",method=RequestMethod.POST)
	public String postRegistration(@Valid @ModelAttribute("userMaster") UserMaster userMaster, BindingResult result,Model model, HttpServletRequest httpRequest , HttpSession httpSession) {
		logger.info("Entry");
		String response = null;
		String pageRedirect = PageRedirectConstants.REGISTRATION_PAGE;
		String otp = (String) httpRequest.getParameter("otp");
		OtpBean sessionOtp=(OtpBean)httpRequest.getSession().getAttribute("otpBean");
		try {
			if (!sessionOtp.getMobileNo().equalsIgnoreCase(userMaster.getContactNo()) || (!sessionOtp.getMobileNo().equalsIgnoreCase(userMaster.getUserId())) || (!otp.equalsIgnoreCase(sessionOtp.getOtpNo()))) {
				model.addAttribute(GSTNConstants.RESPONSE, regOtpInvalid);
			} else {
				if (!result.hasErrors()) {
					userMaster.setStatus("1");
					userMaster.setUserRole(GSTNConstants.PRIMARY_USER);
					userMaster.setInvoiceSequenceType("Auto");
					response = userMasterService.addUserMaster(userMaster);
					if (response.equals(GSTNConstants.SUCCESS)) {
						model.addAttribute(GSTNConstants.RESPONSE,registrationSuccessful);
						pageRedirect = PageRedirectConstants.LOGIN_PAGE;
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
		httpRequest.getSession().removeAttribute("otpBean");
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value = "/profileManagement", method = RequestMethod.GET)
	public String profileManagement(Model model) {
		return PageRedirectConstants.PROFILE_MANAGEMENT_PAGE;
	}
	

	@RequestMapping(value = "/editUser", method = RequestMethod.GET)
	public String editMyOrganizationPage(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			
			UserMaster userMaster = userMasterService.getUserMasterById(loginMaster.getuId());
			userMaster.getOrganizationMaster().setLogoImagePath("");
			model.addAttribute("userMasterObj", userMaster);
			
			List<State> stateList = stateService.listState();
			model.addAttribute("stateList", stateList);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return PageRedirectConstants.ORGANIZATION_MASTER_EDIT_PAGE;
	}
	
	/*@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	public String updateOrganizationPage(@Valid @ModelAttribute("userMaster") UserMaster userMaster, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.ORGANIZATION_MASTER_EDIT_PAGE;
		try {
			String response = null;
			if (!result.hasErrors()){
				if(userMaster.getOrganizationMaster().getState() == null){
					userMaster.getOrganizationMaster().setState("0");	
				}
				response = userMasterService.updateUserMaster(userMaster);
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, profileUpdationSuccessful);
					UserMaster user = userMasterService.getUserDetailsById(userMaster.getId());
					LoginUtil.setLoginUser(httpRequest, user,"UPDATE");
					pageRedirect = PageRedirectConstants.HOME_PAGE;
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, profileUpdationFailure);
				}
			}else{
				System.out.println("Error occured"+result.getAllErrors());
			}
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		httpRequest.getSession().removeAttribute("otpBean");
		return  pageRedirect;
		
	}*/
	
	
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	public String updateOrganizationPage(@Valid @ModelAttribute("userMaster") UserMaster userMaster, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.ORGANIZATION_MASTER_EDIT_PAGE;
		try {
			String response = null;
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			if (!result.hasErrors()){
				if(userMaster.getId().equals(loginMaster.getuId())){
					UserMaster dbUserMaster = userMasterService.getUserMasterById(userMaster.getId());
					userMaster.getOrganizationMaster().setPanNumber(dbUserMaster.getOrganizationMaster().getPanNumber());
					userMaster.getOrganizationMaster().setLogoImagePath(dbUserMaster.getOrganizationMaster().getLogoImagePath());
					if(userMaster.getOrganizationMaster().getState() == null){
						userMaster.getOrganizationMaster().setState("0");	
					}
					response = userMasterService.updateUserMaster(userMaster);
					if(response.equals(GSTNConstants.SUCCESS)){
						model.addAttribute(GSTNConstants.RESPONSE, profileUpdationSuccessful);
						UserMaster user = userMasterService.getUserDetailsById(userMaster.getId());
						LoginUtil.setLoginUser(httpRequest, user,"UPDATE");
						pageRedirect = PageRedirectConstants.HOME_PAGE;
					}else{
						model.addAttribute(GSTNConstants.RESPONSE, profileUpdationFailure);
					}
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, profileUpdationFailure);
				}
				
			}else{
				System.out.println("Error occured"+result.getAllErrors());
			}
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		httpRequest.getSession().removeAttribute("otpBean");
		return  pageRedirect;
		
	}
	
	@RequestMapping(value="/updateUserAjax",method=RequestMethod.POST)
	public @ResponseBody String updateOrganizationPageAjax(@Valid @ModelAttribute("userMaster") UserMaster userMaster, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");
		Map<String, String> mapResponse = new HashMap<String, String>();
		String response = GSTNConstants.FAILURE;
		String responseMessage = profileUpdationFailure;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		try {
			if(userMaster.getOrganizationMaster().getState() == null){
				userMaster.getOrganizationMaster().setState("0");	
			}
			if (!result.hasErrors()){
				if(userMaster.getId().equals(loginMaster.getuId())){
					UserMaster dbUserMaster = userMasterService.getUserMasterById(userMaster.getId());
					userMaster.getOrganizationMaster().setPanNumber(dbUserMaster.getOrganizationMaster().getPanNumber());
					userMaster.getOrganizationMaster().setLogoImagePath(dbUserMaster.getOrganizationMaster().getLogoImagePath());
					if(userMaster.getOrganizationMaster().getState() == null){
						userMaster.getOrganizationMaster().setState("0");	
					}
					response = userMasterService.updateUserMaster(userMaster);
					if(response.equals(GSTNConstants.SUCCESS)){
						responseMessage =  profileUpdationSuccessful;
						UserMaster user = userMasterService.getUserDetailsById(userMaster.getId());
						LoginUtil.setLoginUser(httpRequest, user,"UPDATE");
					}else{
						responseMessage = profileUpdationFailure;
					}
				}else{
					responseMessage = profileUpdationFailure;
				}
				
			}else{
				System.out.println("Error occured"+result.getAllErrors());
				responseMessage = profileUpdationFailure;
			}
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		httpRequest.getSession().removeAttribute("otpBean");
		mapResponse.put(GSTNConstants.RESPONSE, response);
		mapResponse.put(GSTNConstants.MESSAGE, responseMessage);
		logger.info("Exit");
		
		return new Gson().toJson(mapResponse);
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String changePasswordPage(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		model.addAttribute("loginMaster", loginMaster);
		return PageRedirectConstants.CHANGE_PASSWORD_PAGE;
	}
	
	@RequestMapping(value="/changePassword",method=RequestMethod.POST)
	public String postChangePassword(Model model,  HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");
		
		String pageRedirect = PageRedirectConstants.CHANGE_PASSWORD_PAGE;
		try {
			String oldPassword = httpRequest.getParameter("oldPassword");
			String newPassword = httpRequest.getParameter("newPassword");
			boolean isValidNewPwdPattern =GSTNUtil.isValidInputPattern(newPassword,"(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,25}");
			boolean isValidOldPwdPattern =GSTNUtil.isValidInputPattern(oldPassword,"(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,25}");
			String response = null;
			
			String captchaImgText = (String)httpRequest.getParameter("captchaImgText");
			String captcha = (String)httpSession.getAttribute("CAPTCHA");
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			if(captcha==null || (captcha!=null && !captcha.equals(captchaImgText))){
				model.addAttribute(GSTNConstants.RESPONSE, captchaMismatch);
			}else{
				 
				boolean validatePassword = userMasterService.validatePassword(loginMaster.getuId(),oldPassword);
		if(isValidNewPwdPattern && isValidOldPwdPattern){
			if(!oldPassword.equals(newPassword)){
				if(validatePassword){
					response = userMasterService.changePassword(loginMaster.getuId(),newPassword);
					if(response.equals(GSTNConstants.SUCCESS)){
						model.addAttribute(GSTNConstants.RESPONSE, changePasswordSuccessful);
						model.addAttribute(GSTNConstants.STATUS, response);
						//pageRedirect = "redirect:/login";//PageRedirectConstants.LOGIN_PAGE;
						httpSession.invalidate();
						
					}else{
						model.addAttribute(GSTNConstants.RESPONSE, changePasswordUpdateFailure);
					}	
					
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, changePasswordFailure);	
				}
			} else {
				pageRedirect = PageRedirectConstants.CHANGE_PASSWORD_PAGE;
				model.addAttribute(GSTNConstants.RESPONSE, oldPasswordAndNewPasswordSame);
			}
			}else{
				pageRedirect = PageRedirectConstants.CHANGE_PASSWORD_PAGE;
				model.addAttribute(GSTNConstants.RESPONSE, changePasswordPatternFailure);	
			}
			}
			CaptchaUtil.clearSessionCaptcha(httpRequest);
			model.addAttribute("loginMaster", loginMaster);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return pageRedirect;
	}
	
	
@RequestMapping(value = "/confirmPassword", method = RequestMethod.GET)
public String confirmPasswordPage(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
	LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
	model.addAttribute("loginMaster", loginMaster);
	return PageRedirectConstants.CONFIRM_PASSWORD_PAGE;
}

@RequestMapping(value="/confirmPassword",method=RequestMethod.POST)
public String postConfirmPassword(Model model,  HttpServletRequest httpRequest, HttpSession httpSession) {
	logger.info("Entry");
	
	String newPassword = httpRequest.getParameter("newPassword");
	String otp = httpRequest.getParameter("otpHidden");
	OtpBean sessionOtp=(OtpBean)httpRequest.getSession().getAttribute("otpBean");
	boolean isValidPwdPattern =GSTNUtil.isValidInputPattern(newPassword,"(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,25}");
	String response = null;
	String pageRedirect = PageRedirectConstants.LOGIN_PAGE;
	String userId=(String)httpRequest.getSession().getAttribute("userId");
	try {
		String captchaImgText = (String)httpRequest.getParameter("captchaImgText");
		String captcha = (String)httpSession.getAttribute("CAPTCHA");
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		if(captcha==null || (captcha!=null && !captcha.equals(captchaImgText))){
			model.addAttribute(GSTNConstants.RESPONSE, captchaMismatch);
			pageRedirect = PageRedirectConstants.CONFIRM_PASSWORD_PAGE;
		}else{
			if(isValidPwdPattern && (otp.equalsIgnoreCase(sessionOtp.getOtpNo()))){
				response = userMasterService.recoverPassword(userId,newPassword);
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, changePasswordSuccessful);
					httpRequest.getSession().removeAttribute("userId");
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, changePasswordUpdateFailure);
				}	
			}else{
				pageRedirect = PageRedirectConstants.CONFIRM_PASSWORD_PAGE;
				model.addAttribute(GSTNConstants.RESPONSE, dataManipulationException);
			}
				
		}
		
		CaptchaUtil.clearSessionCaptcha(httpRequest);
		model.addAttribute("loginMaster", loginMaster);
	} catch (Exception e) {
		logger.error("Error in:",e);
	}
	logger.info("Exit");	
	return pageRedirect;
}
	

	@RequestMapping(value = "/secondaryUserPage", method = RequestMethod.GET)
	public String getSecondaryUserListPage(Model model) {
	
		return PageRedirectConstants.GET_SECONDARY_USERS_LIST_PAGE;
	}
	
@RequestMapping(value = "/getSecondaryUser", method = RequestMethod.POST)
public String getSecondaryUserPage(@Valid @ModelAttribute("userMaster") UserMaster userMaster, BindingResult result,Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
	logger.info("Entry");	
	String response = null;
	String pageRedirect = PageRedirectConstants.GET_SECONDARY_USERS_LIST_PAGE;
	LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		if (!result.hasErrors()){
			try {
				userMaster.setUserRole(GSTNConstants.SECONDARY_USER);
				userMaster.setReferenceId(loginMaster.getuId());
				userMaster.setCreatedBy(loginMaster.getuId().toString());
				userMaster.setStatus("1");
				userMaster.setInvoiceSequenceType(loginMaster.getInvoiceSequenceType());
				response = userMasterService.addSecondaryUser(userMaster);
				if(response.equals(GSTNConstants.SUCCESS)){
					
					model.addAttribute(GSTNConstants.RESPONSE, secondaryUserAddSuccessful);
					userMaster = new UserMaster();
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, secondaryUserAddFailure);
				}
				
			} catch(ConstraintViolationException e){
				model.addAttribute(GSTNConstants.RESPONSE,duplicateAddSecUser);
			}catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE, secondaryUserAddFailure);
			}
			
		}else{
			model.addAttribute(GSTNConstants.RESPONSE, secondaryUserInvalidInputs);
		}
	model.addAttribute("userMaster", userMaster);
	
	logger.info("Exit");
	return  pageRedirect;
	
}
	
	@RequestMapping(value="/getSecondaryUserList",method=RequestMethod.POST)
	public @ResponseBody String getSecondaryUserList(Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		List<UserMaster> secondaryUserList = new ArrayList<UserMaster>();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			secondaryUserList = userMasterService.getSecondaryUsersList(loginMaster.getuId());
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(secondaryUserList);
	}
	
	@RequestMapping(value="/checkIfUserNameExists",method=RequestMethod.POST)
	public @ResponseBody String checkIfUserNameExists(@RequestParam("userName") String userName) {
		logger.info("Entry");	
		
		boolean userNameExists=false;
		try {
			userNameExists = userMasterService.checkIfUserNameExists(userName);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(userNameExists);
	}
	
	@RequestMapping(value="/checkIfUserMobileNoExists",method=RequestMethod.POST)
	public @ResponseBody String checkIfUserMobileNoExists(@RequestParam("mobileNo") String mobileNo, HttpServletRequest request) {
		logger.info("Entry");	
		
		boolean userMobileNoExists=false;
		try {
			userMobileNoExists = userMasterService.checkIfUserMobileNoExists(mobileNo);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(userMobileNoExists);
	}
	
	@RequestMapping(value="/checkIfpanIsRegistered",method=RequestMethod.POST)
	public @ResponseBody String checkIfpanIsRegistered(@RequestParam("panNo") String panNo, HttpServletRequest request) {
		logger.info("Entry");	
		boolean panIsRegistered = false;
		try {
			panIsRegistered = userMasterService.checkIfpanIsRegistered(panNo);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(panIsRegistered);
	}
	
	@RequestMapping(value="/checkIfGstinIsRegistered",method=RequestMethod.POST)
	public @ResponseBody String checkIfGstinIsRegistered(@RequestParam("gstin") String gstin, HttpServletRequest request) {
		logger.info("Entry");	
		boolean gstinIsRegistered=false;
		try {
			gstinIsRegistered = userMasterService.checkIfGstinIsRegistered(gstin);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(gstinIsRegistered);
	}
	
	
	@RequestMapping(value="/isGstinRegisteredWithOrg",method=RequestMethod.POST)
	public @ResponseBody String isGstinRegisteredWithOrg(@RequestParam("gstin") String gstin, HttpServletRequest request) {
		logger.info("Entry");	
		boolean gstinIsRegistered=false;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		try {
			gstinIsRegistered = userMasterService.checkIfGstinRegisteredWithOrg(gstin, loginMaster.getuId());
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(gstinIsRegistered);
	}
	

		
	@RequestMapping(value = "/editSecondaryUser", method = RequestMethod.POST)
	public String editSecondaryUser(@ModelAttribute("userMaster") UserMaster userMaster, Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.SECONDARY_USER_EDIT_PAGE;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer primId = loginMaster.getuId();
			Integer secId = userMaster.getId();
			boolean isMappingValid = userMasterService.checkSecUserMapping(primId,secId);
			if(isMappingValid){
				UserMaster user = userMasterService.getUserMasterById(userMaster.getId());
				model.addAttribute("userMasterObj", user);
			}else{
				model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
				pageRedirect=PageRedirectConstants.GET_SECONDARY_USERS_LIST_PAGE;
				logger.info("INVALID ACCESS"+loginMaster.getUserId());
			}
			model.addAttribute("editActionPerformed", true);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value="/updateSecondaryUser",method=RequestMethod.POST)
	public String updateSecondaryUser(@Valid @ModelAttribute("userMaster") UserMaster userMaster, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.SECONDARY_USER_EDIT_PAGE;
		String response = null;
		
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer primId = loginMaster.getuId();
			Integer secId = userMaster.getId();
			boolean isMappingValid = userMasterService.checkSecUserMapping(primId,secId);
			if (!result.hasErrors()){
				userMaster.setUpdatedBy(loginMaster.getuId().toString());
				userMaster.setStatus("1");
					if(isMappingValid){
						response = userMasterService.updateSecondaryUser(userMaster);
					}else{
						response =GSTNConstants.ACCESSVIOLATION;
					}
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, secondaryUserEditSuccessful);
					model.addAttribute("userMaster", new UserMaster());
					pageRedirect = PageRedirectConstants.GET_SECONDARY_USERS_LIST_PAGE;
				}else if(response.equals(GSTNConstants.FAILURE)){
					model.addAttribute(GSTNConstants.RESPONSE, profileUpdationFailure);
					model.addAttribute("userMasterObj", userMaster);
				}else if(response.equals(GSTNConstants.ACCESSVIOLATION)){
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					pageRedirect = PageRedirectConstants.SECONDARY_USER_EDIT_PAGE;
					model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
					model.addAttribute("userMasterObj", userMaster);
				}
			}else{
				model.addAttribute(GSTNConstants.RESPONSE, profileUpdationFailure);
				System.out.println("Error occured"+result.getAllErrors());
				model.addAttribute("userMasterObj", userMaster);
			}
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return  pageRedirect;
		
	}
	
	@RequestMapping(value = "/deleteSecondaryUser", method = RequestMethod.POST)
	public String deleteSecondaryUser(@ModelAttribute("userMaster") UserMaster userMaster,  BindingResult result,Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String response = "";
		String pageRedirect = "";
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		Map<Object, Object> mapValues = new HashMap<Object, Object>();
		mapValues.put("loginMaster", loginMaster);
		
		try {
			Integer primId = loginMaster.getuId();
			Integer secId = userMaster.getId();
			boolean isMappingValid = userMasterService.checkSecUserMapping(primId,secId);
			if (!result.hasErrors()){
				userMaster.setUpdatedBy(loginMaster.getuId().toString());
				
				userMaster.setStatus("0");
					if(isMappingValid){
						response = userMasterService.removeSecondaryUser(userMaster, mapValues);
					}else{
						response = GSTNConstants.ACCESSVIOLATION;
					}
				
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, secondaryUserDeletionSuccessful);//MessageFormat.format(secondaryUserDeletionSuccessful,userMaster.getUserId())
					pageRedirect=PageRedirectConstants.GET_SECONDARY_USERS_LIST_PAGE;
				}else if(response.equals(GSTNConstants.FAILURE)){
					model.addAttribute(GSTNConstants.RESPONSE, secondaryUserDeletionFailure);	
				}else if(response.equals(GSTNConstants.ACCESSVIOLATION)){
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
					pageRedirect=PageRedirectConstants.GET_SECONDARY_USERS_LIST_PAGE;
				}else if(response.equals(GSTNConstants.ALREADYMAPPED)){
					model.addAttribute(GSTNConstants.RESPONSE, InvalidUserMappingException);
					pageRedirect=PageRedirectConstants.GET_SECONDARY_USERS_LIST_PAGE;
				}
			}else{
				logger.error("Error occured"+result.getAllErrors());
			}
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value = "/confirmInvoice", method = RequestMethod.GET)
	public String getConfirmInvoicePage(Model model, HttpServletRequest httpRequest) {
		
		logger.info("Entry");
	try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			UserMaster user = userMasterService.getUserMasterById(loginMaster.getuId());
			model.addAttribute("userMasterObj", user);
		} catch (Exception e) {
		logger.error("Error in:",e);
	  }
		logger.info("Entry");
		return PageRedirectConstants.CONFIRM_INVOICE_PAGE;
	}
	
	@RequestMapping(value="/updateDefaultEmailId",method=RequestMethod.POST)
	public String updateInvoice(Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");	
		
		String pageRedirect = PageRedirectConstants.CONFIRM_INVOICE_PAGE;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			
			String response = null;
			String  defaultEmailId = httpRequest.getParameter("defaultMailId");
			UserMaster user = userMasterService.getUserMasterById(loginMaster.getuId());
			model.addAttribute("userMasterObj", user);
			response = userMasterService.updateInvoice(loginMaster.getuId(), defaultEmailId);
			if(response.equals(GSTNConstants.SUCCESS)){
				model.addAttribute(GSTNConstants.RESPONSE, invoiceUpdationSuccessful);
				pageRedirect = PageRedirectConstants.HOME_PAGE;
			}else{
				model.addAttribute(GSTNConstants.RESPONSE, invoiceUpdationFailure);	
			}
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return  pageRedirect;
		
	}

	@RequestMapping(value="/updateTermsConditions",method=RequestMethod.POST)
	public @ResponseBody String updateTermsConditions(HttpServletRequest request) {
		logger.info("Entry");	
		
		boolean updateTermsConditions=false;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		UserMaster userDetails = new UserMaster();
		try {
			updateTermsConditions = userMasterService.updateTermsConditions(loginMaster.getOrgUId());
			userDetails = userMasterService.getUserDetailsById(loginMaster.getuId());
			LoginUtil.setLoginUser(request, userDetails,"UPDATE");
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(updateTermsConditions);
	}
	
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public String deleteUser() {
		return PageRedirectConstants.DELETE_USER_ACCOUNT_PAGE;
	}
	
	
	@RequestMapping(value="/deleteUserAccount",method=RequestMethod.POST)
	public String deleteUserAccount(HttpServletRequest request,Model model) {
		logger.info("Entry");	
		String pageRedirect = "";
		boolean isAccountDeleted=false;
		boolean islogoDeleted = false;
		String imageDirectory = null;
		String pdfDirectory = null;
		
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		String reasonOfDeletion = request.getParameter("reasonOfDeletion");
		String userFeedback = request.getParameter("userFeedback");
		try {
			imageDirectory = pathForLogoImage +loginMaster.getOrgUId();
			isAccountDeleted = userMasterService.deleteUserAccount(loginMaster.getOrgUId(),loginMaster.getUserId(),reasonOfDeletion,userFeedback);
			if(isAccountDeleted){
				if(imageDirectory!= null){
					islogoDeleted = GSTNUtil.deleteLogo(imageDirectory);
				}
				GSTNUtil.deletePdf(invoiceDirectoryPath,loginMaster.getOrgUId()+"");
				model.addAttribute(GSTNConstants.RESPONSE, accountDeletionSuccess);
				LoginUtil.destroyUserSession(request);
			}else{
				model.addAttribute(GSTNConstants.RESPONSE, accountDeletionFailure);
			}
			pageRedirect = PageRedirectConstants.DELETE_USER_ACCOUNT_PAGE;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return pageRedirect;
	}
	
	@ModelAttribute("footerBean")
	public FooterBean constructFooterBean(){
		return new FooterBean();
	}
	
	@RequestMapping(value = "/getFooter", method = RequestMethod.GET)
	public String getFooter(Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		logger.info("Exit");
		return PageRedirectConstants.FOOTER_PAGE;
	}
	
	@RequestMapping(value="/addFooter",method=RequestMethod.POST)
	public String addFooter(@Valid @ModelAttribute("footerBean") FooterBean footerBean, BindingResult result,Model model,HttpServletRequest httpRequest,HttpSession httpSession) {
		logger.info("Entry");
		String response = null;
		String footer = footerBean.getFooter();//httpRequest.getParameter("footerDesc");
		try {
			if (!result.hasErrors()){
			
				LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
				response = userMasterService.addFooter(loginMaster.getOrgUId(),footer,loginMaster.getuId());
				
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, footerAdditionSuccess);
					loginMaster.setFooter(footer);
					httpSession.setAttribute(GSTNConstants.LOGIN_USER, loginMaster);
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, footerAdditionFailure);
				}
			}else{
				BindingErrorUtil.getErrorResult(result, logger);
				model.addAttribute(GSTNConstants.RESPONSE,footerAdditionFailure);
			}
		}catch (Exception e) {
			logger.error("Error in:",e);
			model.addAttribute(GSTNConstants.RESPONSE, footerAdditionFailure);
		}
		logger.info("Exit");
		return  PageRedirectConstants.FOOTER_PAGE;
	}
	
	
	@RequestMapping(value = "/confirmPasswordLoginUnified", method = RequestMethod.GET)
	public String confirmPasswordPageLoginUnified(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		model.addAttribute("loginMaster", loginMaster);
		return PageRedirectConstants.CONFIRM_PASSWORD_PAGE_LOGIN_UNIFIED;
	}
	
	@RequestMapping(value="/confirmPasswordLoginUnified",method=RequestMethod.POST)
	public String confirmPasswordLoginUnified(Model model,  HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");
		
		String newPassword = httpRequest.getParameter("newPassword");
		String otp = httpRequest.getParameter("otpHidden");
		OtpBean sessionOtp=(OtpBean)httpRequest.getSession().getAttribute("otpBean");
		boolean isValidPwdPattern =GSTNUtil.isValidInputPattern(newPassword,"(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,25}");
		String response = null;
		String pageRedirect = PageRedirectConstants.LOGIN_PAGE;
		String userId=(String)httpRequest.getSession().getAttribute("userId");
		try {
			/*String captchaImgText = (String)httpRequest.getParameter("captchaImgText");
			String captcha = (String)httpSession.getAttribute("CAPTCHA");*/
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			/*if(captcha==null || (captcha!=null && !captcha.equals(captchaImgText))){
				model.addAttribute(GSTNConstants.RESPONSE, captchaMismatch);
				pageRedirect = PageRedirectConstants.CONFIRM_PASSWORD_PAGE;
			}*/
				
					response = userMasterService.recoverPassword(userId,newPassword);
					if(response.equals(GSTNConstants.SUCCESS)){
						model.addAttribute(GSTNConstants.RESPONSE, changePasswordSuccessful);
						httpRequest.getSession().removeAttribute("userId");
					}else{
						model.addAttribute(GSTNConstants.RESPONSE, changePasswordUpdateFailure);
					}	
				
			model.addAttribute("loginMaster", loginMaster);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");	
		return pageRedirect;
	}
	
	@RequestMapping(value = "/getInvSeqSettings", method = RequestMethod.GET)
	public String getInvSeqSettingsPage(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		return PageRedirectConstants.INVOICE_SEQUENCE_SETTINGS_PAGE;
	}
	
	@RequestMapping(value="/setInvSeqSettings",method=RequestMethod.POST)
	public @ResponseBody String setInvSeqSettings(@RequestParam("mode") String invoiceSequenceType, HttpServletRequest httpRequest,HttpSession httpSession) {
		logger.info("Entry");	
		Map<String,String> mapResponse = new HashMap<String,String>();
		String status = GSTNConstants.FAILURE;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			status = userMasterService.setInvoiceSequenceType(invoiceSequenceType,loginMaster.getuId());
			
			loginMaster.setInvoiceSequenceType(invoiceSequenceType);
			httpSession.setAttribute(GSTNConstants.LOGIN_USER, loginMaster);
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		mapResponse.put(GSTNConstants.STATUS, status);
		logger.info("Exit");
		return new Gson().toJson(mapResponse);
	}
}
