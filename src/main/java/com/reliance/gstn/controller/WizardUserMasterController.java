package com.reliance.gstn.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.filter.CaptchaUtil;
import com.reliance.gstn.model.FooterBean;
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

@Controller
public class WizardUserMasterController {

	private static final Logger logger = Logger.getLogger(WizardUserMasterController.class);
	
	@Autowired
	private UserMasterService userMasterService;

	@Autowired
	StateService stateService;

	@Value("${PROFILE_UPDATE_SUCESS}")
	private String profileUpdationSuccessful;

	@Value("${PROFILE_UPDATE_FAILURE}")
	private String profileUpdationFailure;

	@Value("${SECONDARY_USER_SUCESS}")
	private String secondaryUserAddSuccessful;

	@Value("${SECONDARY_USER_FAILURE}")
	private String secondaryUserAddFailure;

	@Value("${DUPLICATE_SECONDARY_USER_FAILURE}")
	private String duplicateAddSecUser;

	@Value("${SECONDARY_USER_INVALID_INPUTS}")
	private String secondaryUserInvalidInputs;
	
	@Value("${SECONDARY_USER_DELETION_SUCCESS}")
	private String secondaryUserDeletionSuccessful;

	@Value("${SECONDARY_USER_DELETION_FAILURE}")
	private String secondaryUserDeletionFailure;

	@Value("${INVALID_MAPPING_EXCEPTION}")
	private String InvalidMappingException;

	@Value("${SECONDARY_USER_UPDATE_SUCESS}")
	private String secondaryUserEditSuccessful;
	
	@Value("${CAPTCHA_MISMATCH}")
	private String captchaMismatch;
	
	@Value("${CHANGE_PASSWORD_FAILURE}")
	private String changePasswordFailure;
	
	@Value("${CHANGE_PASSWORD_UPDATE_FAILURE}")
	private String changePasswordUpdateFailure;
	
	@Value("${CHANGE_PASSWORD_SUCCESS}")
	private String changePasswordSuccessful;
	
	@Value("${CHANGE_PASSWORD_PATTERN_FAILURE}")
	private String changePasswordPatternFailure;
	
	@Value("${DATA_MANIPULATION_EXCEPTION}")
	private String dataManipulationException;
	
	@Value("${${env}.PATH_FOR_LOGO_IMG}")
	private String pathForLogoImage;
	
	@Value("${ACCOUNT_DELETION_SUCCESS}")
	private String accountDeletionSuccess;
	
	@Value("${ACCOUNT_DELETION_FAILED}")
	private String accountDeletionFailure;
	
	@Value("${${env}.PATH_FOR_INVOICE_PDF}")
	private String invoiceDirectoryPath;

	@Value("${USER_MAPPED_EXCEPTION}")
	private String InvalidUserMappingException;

	@Value("${FOOTER_ADD_FAILURE}")
	private String footerAdditionFailure;
	
	@Value("${FOOTER_ADD_SUCESS}")
	private String footerAdditionSuccess;
	
	
	@ModelAttribute("userMaster")
	public UserMaster construct(){
		return new UserMaster();
	}
	

	@RequestMapping(value = "/wEditUser", method = RequestMethod.GET)
	public String editMyOrganizationPage(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);			
			UserMaster userMaster = userMasterService.getUserMasterById(loginMaster.getuId());
			model.addAttribute("userMasterObj", userMaster);			
			List<State> stateList = stateService.listState();
			model.addAttribute("stateList", stateList);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return PageRedirectConstants.WIZARD_ORGANIZATION_MASTER_EDIT_PAGE;
	}

	@RequestMapping(value="/wUpdateUser",method=RequestMethod.POST)
	public String updateOrganizationPage(@Valid @ModelAttribute("userMaster") UserMaster userMaster, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.WIZARD_ORGANIZATION_MASTER_EDIT_PAGE;
		String status = GSTNConstants.FAILURE;
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
					status = GSTNConstants.SUCCESS;
					//pageRedirect = PageRedirectConstants.WIZARD_HOME_PAGE;
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, profileUpdationFailure);
				}
			}else{
				System.out.println("Error occured"+result.getAllErrors());
			}			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		model.addAttribute(GSTNConstants.STATUS,status );
		logger.info("Exit");
		httpRequest.getSession().removeAttribute("otpBean");
		return  pageRedirect;		
	}

	@RequestMapping(value="/wupdateUserAjax",method=RequestMethod.POST)
	public @ResponseBody String wizardUpdateOrganizationPageAjax(@Valid @ModelAttribute("userMaster") UserMaster userMaster, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");
		Map<String, String> mapResponse = new HashMap<String, String>();
		String response = GSTNConstants.FAILURE;
		String responseMessage = profileUpdationFailure;
		try {			
			if (!result.hasErrors()){
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
	
	@RequestMapping(value = "/wizardSecondaryUserPage", method = RequestMethod.GET)
	public String getWizardSecondaryUserListPage(Model model) {	
		return PageRedirectConstants.WIZARD_GET_SECONDARY_USERS_LIST_PAGE;
	}
	
	@RequestMapping(value = "/getWizardSecondaryUser", method = RequestMethod.POST)
	public String getWizardSecondaryUserPage(@Valid @ModelAttribute("userMaster") UserMaster userMaster, BindingResult result,Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");	
		String response = null;
		String pageRedirect = PageRedirectConstants.WIZARD_GET_SECONDARY_USERS_LIST_PAGE;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			if (!result.hasErrors()){
				try {
					userMaster.setUserRole(GSTNConstants.SECONDARY_USER);
					userMaster.setReferenceId(loginMaster.getuId());
					userMaster.setCreatedBy(loginMaster.getuId().toString());
					userMaster.setStatus("1");
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

	@RequestMapping(value = "/wizardDeleteSecondaryUser", method = RequestMethod.POST)
	public String wizardDeleteSecondaryUser(@ModelAttribute("userMaster") UserMaster userMaster,  BindingResult result,Model model, HttpServletRequest httpRequest) {
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
					pageRedirect=PageRedirectConstants.WIZARD_GET_SECONDARY_USERS_LIST_PAGE;
				}else if(response.equals(GSTNConstants.FAILURE)){
					model.addAttribute(GSTNConstants.RESPONSE, secondaryUserDeletionFailure);	
				}else if(response.equals(GSTNConstants.ACCESSVIOLATION)){
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
					pageRedirect=PageRedirectConstants.WIZARD_GET_SECONDARY_USERS_LIST_PAGE;
				}else if(response.equals(GSTNConstants.ALREADYMAPPED)){
					model.addAttribute(GSTNConstants.RESPONSE, InvalidUserMappingException);
					pageRedirect=PageRedirectConstants.WIZARD_GET_SECONDARY_USERS_LIST_PAGE;
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
	
	@RequestMapping(value = "/wizardEditSecondaryUser", method = RequestMethod.POST)
	public String wizardEditSecondaryUser(@ModelAttribute("userMaster") UserMaster userMaster, Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.WIZARD_SECONDARY_USER_EDIT_PAGE;
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
				pageRedirect=PageRedirectConstants.WIZARD_GET_SECONDARY_USERS_LIST_PAGE;
				logger.info("INVALID ACCESS"+loginMaster.getUserId());
			}
			model.addAttribute("editActionPerformed", true);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value="/wizardUpdateSecondaryUser",method=RequestMethod.POST)
	public String wizardUpdateSecondaryUser(@Valid @ModelAttribute("userMaster") UserMaster userMaster, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.WIZARD_SECONDARY_USER_EDIT_PAGE;
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
					pageRedirect = PageRedirectConstants.WIZARD_GET_SECONDARY_USERS_LIST_PAGE;
				}else if(response.equals(GSTNConstants.FAILURE)){
					model.addAttribute(GSTNConstants.RESPONSE, profileUpdationFailure);
					model.addAttribute("userMasterObj", userMaster);
				}else if(response.equals(GSTNConstants.ACCESSVIOLATION)){
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					pageRedirect = PageRedirectConstants.WIZARD_SECONDARY_USER_EDIT_PAGE;
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
	
	

	@RequestMapping(value = "/wizardChangePassword", method = RequestMethod.GET)
	public String changePasswordPage(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		model.addAttribute("loginMaster", loginMaster);
		return PageRedirectConstants.WIZARD_CHANGE_PASSWORD_PAGE;
	}
	
	@RequestMapping(value="/wizardChangePassword",method=RequestMethod.POST)
	public String postChangePassword(Model model,  HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");
		
		String pageRedirect = PageRedirectConstants.WIZARD_CHANGE_PASSWORD_PAGE;
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
				if(validatePassword){
					response = userMasterService.changePassword(loginMaster.getuId(),newPassword);
					if(response.equals(GSTNConstants.SUCCESS)){
						model.addAttribute(GSTNConstants.RESPONSE, changePasswordSuccessful);
						//model.addAttribute(GSTNConstants.STATUS, response);
						pageRedirect = PageRedirectConstants.LOGIN_PAGE;
						httpSession.invalidate();
						
					}else{
						model.addAttribute(GSTNConstants.RESPONSE, changePasswordUpdateFailure);
					}	
					
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, changePasswordFailure);	
				}
			}else{
				pageRedirect = PageRedirectConstants.WIZARD_CHANGE_PASSWORD_PAGE;
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
	
	

	
	@RequestMapping(value = "/wizardConfirmPassword", method = RequestMethod.GET)
	public String confirmPasswordPage(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		model.addAttribute("loginMaster", loginMaster);
		return PageRedirectConstants.WIZARD_CONFIRM_PASSWORD_PAGE;
	}
	
	@RequestMapping(value="/wizardConfirmPassword",method=RequestMethod.POST)
	public String postConfirmPassword(Model model,  HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");
		
		String newPassword = httpRequest.getParameter("newPassword");
		String otp = httpRequest.getParameter("otpHidden");
		OtpBean sessionOtp=(OtpBean)httpRequest.getSession().getAttribute("otpBean");
		boolean isValidPwdPattern =GSTNUtil.isValidInputPattern(newPassword,"(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,25}");
		String response = null;
		String pageRedirect = PageRedirectConstants.WIZARD_LOGIN_PAGE;
		String userId=(String)httpRequest.getSession().getAttribute("userId");
		try {
			String captchaImgText = (String)httpRequest.getParameter("captchaImgText");
			String captcha = (String)httpSession.getAttribute("CAPTCHA");
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			if(captcha==null || (captcha!=null && !captcha.equals(captchaImgText))){
				model.addAttribute(GSTNConstants.RESPONSE, captchaMismatch);
				pageRedirect = PageRedirectConstants.WIZARD_CONFIRM_PASSWORD_PAGE;
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
					pageRedirect = PageRedirectConstants.WIZARD_CONFIRM_PASSWORD_PAGE;
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
	

	@RequestMapping(value = "/wizardDeleteUser", method = RequestMethod.GET)
	public String deleteUser() {
		return PageRedirectConstants.WIZARD_DELETE_USER_ACCOUNT_PAGE;
	}
	
	
	@RequestMapping(value="/wizardDeleteUserAccount",method=RequestMethod.POST)
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
				pageRedirect = PageRedirectConstants.WIZARD_LOGIN_PAGE;
			}else{
				model.addAttribute(GSTNConstants.RESPONSE, accountDeletionFailure);
			}
			pageRedirect = PageRedirectConstants.WIZARD_DELETE_USER_ACCOUNT_PAGE;
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
	
	@RequestMapping(value = "/wFooter", method = RequestMethod.GET)
	public String getFooter(Model model, HttpServletRequest httpRequest) {
		return PageRedirectConstants.WIZARD_FOOTER_PAGE;
	}
	
	@RequestMapping(value="/wAddFooter",method=RequestMethod.POST)
	public String addFooter(@Valid @ModelAttribute("footerBean") FooterBean footerBean, BindingResult result,Model model,HttpServletRequest httpRequest,HttpSession httpSession) {
		logger.info("Entry");
		String response = null;
		String footer = footerBean.getFooter();
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
		return  PageRedirectConstants.WIZARD_FOOTER_PAGE;
	}
	
}
