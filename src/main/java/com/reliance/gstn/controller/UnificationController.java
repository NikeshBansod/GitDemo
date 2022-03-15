package com.reliance.gstn.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.model.LoginAttempt;
import com.reliance.gstn.model.OrganizationMaster;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.OrganizationMasterUnification;
import com.reliance.gstn.model.OtpBean;
import com.reliance.gstn.model.State;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.model.UserMasterUnification;
import com.reliance.gstn.service.UnificationService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;
import com.reliance.gstn.util.TimeUtil;


@Controller
public class UnificationController {
	
	private static final Logger logger = Logger.getLogger(UnificationController.class);
	

	@Autowired
	public UnificationService unificationService;
	
	
	@ModelAttribute("userMasterUnification")
	public UserMasterUnification construct(){
		return new UserMasterUnification();
	}
	
	@RequestMapping(value = "/registerunifi", method = RequestMethod.GET)
	public String getRegistrationPageUnification(Model model) {
		model.addAttribute("userMaster",new UserMasterUnification());
		return PageRedirectConstants.REGISTRATION_PAGE_UNIFICATION;
	}
	
	
	@RequestMapping(value="/registrationunification",method=RequestMethod.POST)
	public @ResponseBody String postRegistrationUnification(@RequestParam("contactNo") String contactNo, @RequestParam("emailId") String emailId, @RequestParam("pannumber") String pannumber, @RequestParam("password") String password, HttpServletRequest httpRequest , HttpSession httpSession) {
		logger.info("Entry");
		Map<String, Object> otpData = new HashMap<String, Object>();
		try {
			otpData  = unificationService.getOtp(contactNo,emailId,pannumber,contactNo,password);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(otpData);
	}
	
	@RequestMapping(value="/verifyRegistrationUnifiOtp",method=RequestMethod.POST)
	public @ResponseBody String verifyRegistrationOtp(@RequestParam("otp") String otp,@RequestParam("cachekey") String cachekey, @RequestParam("contactNo") String contactNo, @RequestParam("emailId") String emailId, @RequestParam("pannumber") String pannumber, @RequestParam("password") String password, HttpServletRequest request) {
		logger.info("Entry");	
		Map<String, Object> otpverification = new HashMap<String, Object>();
		String response = null;
		try {
			
			otpverification=unificationService.verifyOtp(otp, cachekey, contactNo, emailId, pannumber, contactNo, password);
			String status_code= (String)((Map<String, Object>)otpverification.get("msg")).get("status_cd"); 
			if(status_code.equalsIgnoreCase("1")){
                UserMasterUnification userMasterUni = new UserMasterUnification();
                OrganizationMasterUnification organizationMasterUnifi = new OrganizationMasterUnification();
                userMasterUni.setUserId(contactNo);
                userMasterUni.setPassword(password);
                userMasterUni.setDefaultEmailId(emailId);
                userMasterUni.setUserRole("PRIMARY");
                userMasterUni.setContactNo(contactNo);
                userMasterUni.setInvoiceSequenceType("auto");
                userMasterUni.setDepartment("auto");
                userMasterUni.setDesignation("auto");
                userMasterUni.setInvoiceSequenceType("auto");
                userMasterUni.setUserIdUnifi(Double.valueOf(otpverification.get("userId").toString()));
                organizationMasterUnifi.setCompanyId(Double.valueOf(otpverification.get("companyId").toString()));
                organizationMasterUnifi.setPanNumber(pannumber);
                organizationMasterUnifi.setState("0");
                userMasterUni.setOrganizationMasterUnification(organizationMasterUnifi);
                response = unificationService.saveOrUpdateUserDetails(userMasterUni);
          }

			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(otpverification);
	}
	
	@RequestMapping(value = {"/loginunifi"}, method = RequestMethod.GET)
	public String indexPage(HttpSession session,Model model ) {
		//session.setAttribute("notificationStatus", notificationStatus);
		return PageRedirectConstants.LOGIN_PAGE_UNIFI;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value="/loginunifiP", method = RequestMethod.POST)
	public  String login(@ModelAttribute("userMaster") UserMasterUnification userMaster,Model model,HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");
		Map<String, Object> loginresponse = new HashMap<String, Object>();
		HttpSession session = httpRequest.getSession();
		String redirect = null;
		UserMasterUnification userDetails = new UserMasterUnification();
		OrganizationMaster organizationDetails = new OrganizationMaster();
		try {
			userDetails=unificationService.login(userMaster.getUserId(),userMaster.getPassword());
			session.setAttribute("userId", userDetails.getUserIdUnifi());
			session.setAttribute("companyId", userDetails.getOrganizationMasterUnification().getCompanyId());
			session.setAttribute("panNumber", userDetails.getOrganizationMasterUnification().getPanNumber());
			if(userDetails.getOrganizationMasterUnification().isRegstatus()){
				redirect= PageRedirectConstants.HOME_PAGE_UNIICATION;
				model.addAttribute("userMaster", userDetails);
			}
			else{
				/*model.addAttribute("organizationMasterEditUnification", new UserMaster()); 
				redirect = "redirect:/edituserprofileunification";*/
				redirect= PageRedirectConstants.UNIFICATION_VIEW_PAGE;
				model.addAttribute("userMaster", userDetails);
			}
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return redirect;
	}

	
	@RequestMapping(value = "/edituserprofileunification", method = RequestMethod.GET)
	public String editMyOrganizationPage(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");
		Object userId=httpRequest.getSession().getAttribute("userId");
		Object companyId=httpRequest.getSession().getAttribute("companyId");
		Object pannumber=httpRequest.getSession().getAttribute("panNumber");
		List<UserMasterUnification> edituserprofileresponse = null;
		try {
			edituserprofileresponse=unificationService.updateprofile(userId, companyId,pannumber);
			//model.addAttribute("stateList", new State());
			model.addAttribute("userMasterObj", edituserprofileresponse.get(0));
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return PageRedirectConstants.UNIFIACTION_MYPROFILE_PAGE;
	}
	
	@RequestMapping(value = "/edituserprofileunificationsave", method = RequestMethod.POST)
	public String editMyOrganizationPageSave(@Valid @ModelAttribute("userMasterUnification") UserMasterUnification userMasterUnification,BindingResult bindingResult, HttpServletRequest httpRequest, Model model, HttpSession httpSession) {
		logger.info("Entry");
		String redirect = null;
		Map<String, Object> edituser = new HashMap<String, Object>();
		try {
			
			edituser=unificationService.updateUser(userMasterUnification);
			Object msg=edituser.get("msg");
			String statuscode=(String)((Map<String, Object>) msg).get("status_cd");
			String status=(String) ((Map<String, Object>) msg).get("status_msg");
			if(statuscode.equalsIgnoreCase("1")){
				model.addAttribute(GSTNConstants.RESPONSE, "User Details Updated Successfully.");
				redirect= PageRedirectConstants.HOME_PAGE;
			}
			else{
				model.addAttribute(GSTNConstants.RESPONSE, status);
				redirect= PageRedirectConstants.UNIFIACTION_MYPROFILE_PAGE;
			}
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return redirect;
	}
	
	
	@RequestMapping(value = "/updateuserprofileunificationsave", method = RequestMethod.POST)
	public String updateMyOrganizationPageSave(@Valid @ModelAttribute("userMasterUnification") UserMasterUnification userMasterUnification,BindingResult bindingResult, HttpServletRequest httpRequest, Model model, HttpSession httpSession) {
		logger.info("Entry");
		String redirect = null;
		Map<String, Object> edituser = new HashMap<String, Object>();
		try {
			
			edituser=unificationService.updateMyProfile(userMasterUnification);
			//String statuscodeuser=(String) edituser.get("statuscodeuser");
			//String statususer=(String) edituser.get("statususer");
			String statuscodeorg=(String) edituser.get("statuscodeorg");
			String statusorg=(String) edituser.get("statusorg");
			if(statuscodeorg.equalsIgnoreCase("1")){
				model.addAttribute(GSTNConstants.RESPONSE, statusorg);
				redirect= PageRedirectConstants.HOME_PAGE_UNIICATION;
			}
			else{
				model.addAttribute(GSTNConstants.RESPONSE, statusorg);
				redirect= PageRedirectConstants.UNIFIACTION_MYPROFILE_PAGE;
			}
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return redirect;
	}
	
	




	@RequestMapping(value="/loginUnifiedVerifySavePassword",method=RequestMethod.POST)
	public @ResponseBody String loginUnifiedVerifySavePassword(@RequestParam("cacheKey") String cacheKey,@RequestParam("otp") String otp,@RequestParam("password") String password, HttpServletRequest request) {
		logger.info("Entry");	
		
		Map<String, Object> verifyotpAndSavePassword = new HashMap<String, Object>();
		Map<String, Object> msg = new HashMap<String, Object>();
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			verifyotpAndSavePassword=unificationService.verifyOtpSavePassword(cacheKey,otp,password);
			//if((Map<String,Object>)verifyotpAndSavePassword.containsKey("msg")==true)
			msg=(Map<String,Object>)verifyotpAndSavePassword.get("msg");
			if(msg.get("status_cd").equals("1")==true)
			{
				response.put("status", true);
				response.put("status_msg", verifyotpAndSavePassword.get("status_msg"));
			}
			else 
			{
				response.put("status", false);
				response.put("status_msg", msg.get("status_msg"));
			}
			
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(response);
	}
















	@RequestMapping(value="/loginUnifiedForgotPassword",method=RequestMethod.POST)
	public @ResponseBody String loginUnifiedForgotPassword(@RequestParam("userName") String userName, HttpServletRequest request) {
		logger.info("Entry");	
		
		Map<String, Object> requestotp = new HashMap<String, Object>();
		Map<String, Object> msg = new HashMap<String, Object>();
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			requestotp=unificationService.requestOtp(userName);
			msg=(Map<String,Object>)requestotp.get("msg");
			if(msg.get("status_cd").equals("1")==true)
			{
				response.put("status", true);
				response.put("cachekey", requestotp.get("cachekey"));
			}
			else 
			{
				response.put("status", false);
				response.put("status_msg", msg.get("status_msg"));
			}
			
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(response);
	}
	
	@RequestMapping(value = "/homeUnifi", method = RequestMethod.GET)
	public String homePage(Model model,HttpServletRequest httpRequest) {
		
		String redirect = null;
		
          
         redirect = PageRedirectConstants.HOME_PAGE_UNIICATION;
		return redirect;
	}
	
}
