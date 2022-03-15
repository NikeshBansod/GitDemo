/**
 * 
 */
package com.reliance.gstn.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
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
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.NatureOfBusiness;
import com.reliance.gstn.model.Registration;
import com.reliance.gstn.service.RegistrationService;
import com.reliance.gstn.util.GSTNConstants;
//import com.reliance.gstn.util.LoggerUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Nikesh.Bansod
 *
 */
@Controller
public class RegistrationController {
	
	private static final Logger logger = Logger.getLogger(RegistrationController.class);
	
	@Autowired
	public RegistrationService registrationService;
	
	@Value("${REGISTRATION_SUCESS}")
	private String registrationSuccessful;
	
	@Value("${REGISTRATION_FAILURE}")
	private String registrationFailure;
	
	@Value("${MY_PROFILE_UPDATION_SUCESS}")
	private String myProfileUpdationSuccessful;
	
	@Value("${MY_PROFILE_UPDATION_FAILURE}")
	private String myProfileUpdationFailure;
	
	@Value("${MY_PROFILE_UPDATION_INVALID_INPUTS}")
	private String myProfileUpdationInvalidInputs;
	
	@Value("${CHANGE_PASSWORD_SUCCESS}")
	private String changePasswordSuccessful;
	
	@Value("${CHANGE_PASSWORD_FAILURE}")
	private String changePasswordFailure;
	
	@ModelAttribute("registration")
	public Registration construct(){
		return new Registration();
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registrationPage(Model model) {
		return PageRedirectConstants.REGISTRATION_PAGE;
	}
	
	
	@RequestMapping(value = "/manageMyProfile", method = RequestMethod.GET)
	public String manageMyProfileViewPage(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		Registration registration = registrationService.getRegistrationById(loginMaster.getuId());
		model.addAttribute("registration", registration);
		return PageRedirectConstants.MANAGE_MY_PROFILE_VIEW_PAGE;
	}
	
	
	@RequestMapping(value = "/editMyProfile", method = RequestMethod.GET)
	public String manageMyProfileEditPage(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		Registration registration = registrationService.getRegistrationById(loginMaster.getuId());
		model.addAttribute("registration", registration);
		return PageRedirectConstants.MANAGE_MY_PROFILE_EDIT_PAGE;
	}
	
	@RequestMapping(value="/editMyProfile",method=RequestMethod.POST)
	public String editRegistration(@Valid @ModelAttribute("registration") Registration registration, BindingResult result,Model model,  HttpSession httpSession) {
		
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.MANAGE_MY_PROFILE_EDIT_PAGE;
		String response = null;
		
		if (!result.hasErrors()){
			response = registrationService.updateRegistration(registration);
			if(response.equals(GSTNConstants.SUCCESS)){
				model.addAttribute(GSTNConstants.RESPONSE, myProfileUpdationSuccessful);
				pageRedirect = PageRedirectConstants.MANAGE_MY_PROFILE_VIEW_PAGE;
			}else{
				model.addAttribute(GSTNConstants.RESPONSE, myProfileUpdationFailure);
			}
		}else{
			System.out.println("Error occured"+result.getAllErrors());
		}
		
		logger.info("Exit");
		return  pageRedirect;
	}
	

	@RequestMapping(value="/getNatureOfBusinessList",method=RequestMethod.POST)
	public @ResponseBody String getNatureOfBusinessList(HttpServletRequest httpRequest) {
		logger.info("Entry");	
		List<NatureOfBusiness> list = new ArrayList<NatureOfBusiness>();
		try {
			list = registrationService.getNatureOfBusinessList();
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(list);
	}

}
