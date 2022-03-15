package com.reliance.gstn.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.model.OtpBean;
import com.reliance.gstn.service.OtpService;
import com.reliance.gstn.service.UserMasterService;

@Controller
public class OtpController {

	private static final Logger logger = Logger.getLogger(OtpController.class);
	
	@Autowired
	private OtpService otpService;
	
	@Autowired
	private UserMasterService userMasterService;
	
	@Value("${no_of_otp}")
	private String otpNos;
	
	@RequestMapping(value="/isRegisteredUser",method=RequestMethod.POST)
	public @ResponseBody String isRegisteredUser(@RequestParam("userId") String userId,HttpServletRequest request) {
		logger.info("Entry");	
		
		boolean isUserReg=false;
		
		try {
			String mobileNo = userMasterService.getRegisteredUserMobileNo(userId);
			if(mobileNo!=null){
				isUserReg = true;
			}
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(isUserReg);
	}
	
	
	@RequestMapping(value="/sendOtpToRegisteredUser",method=RequestMethod.POST)
	public @ResponseBody String sendOtpToRegisteredUser(@RequestParam("userId") String userId,HttpServletRequest request) {
		logger.info("Entry");	
		Map<Boolean, String> otpData = new HashMap<Boolean, String>();
		
		try {
			String mobileNo = userMasterService.getRegisteredUserMobileNo(userId);
			if(mobileNo!=null){
				otpData = otpService.sendOtpToRegisteredUser(userId,mobileNo,request);
			}
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		
		return new Gson().toJson(otpData);
	}
	
	
	@RequestMapping(value="/verifyPwdRecoveryOtp",method=RequestMethod.POST)
	public @ResponseBody String verifyPwdRecoveryOtp(@RequestParam("otp") String otp, @RequestParam("userMobileNo") String contactNo, HttpServletRequest request) {
		logger.info("Entry");	
		Integer VerificationOtpCount = 0;
		boolean otpValidated=false;
		
		try {
			OtpBean sessionOtp=(OtpBean)request.getSession().getAttribute("otpBean");
			logger.info("sessionOtp : "+sessionOtp + "- OTP : "+otp);
			VerificationOtpCount = otpService.countVerificationOtp(contactNo,request);
			Integer otpNumbar = Integer.parseInt(otpNos);
			if(VerificationOtpCount < otpNumbar){
			if(sessionOtp.getOtpNo().equals(otp)){
				otpService.removeOtpRecord(contactNo,request);
				otpValidated=true;
				}else {
					logger.info("OTP Verification failed for "+contactNo+" while password reset");
				}
			}
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(otpValidated);
	}
	
	
	@RequestMapping(value="/sendOtpOnRegistration",method=RequestMethod.POST)
	public @ResponseBody String sendOtpOnRegistration(@RequestParam("userMobileNo") String userMobileNo,HttpServletRequest request) {
		logger.info("Entry");	
		Map<Boolean, String> otpData = new HashMap<Boolean, String>();
		
		try {
			otpData = otpService.sendOtpOnRegistration(userMobileNo,request);
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(otpData);
	}
	
	
	@RequestMapping(value="/verifyRegistrationOtp",method=RequestMethod.POST)
	public @ResponseBody String verifyRegistrationOtp(@RequestParam("otp") String otp, @RequestParam("userMobileNo") String contactNo, HttpServletRequest request) {
		logger.info("Entry");	
		
		boolean otpValidated=false;
		Integer VerificationOtpCount = 0;
		try {
			OtpBean sessionOtp=(OtpBean)request.getSession().getAttribute("otpBean");
			logger.info("sessionOtp : "+sessionOtp + "- OTP : "+otp);
			VerificationOtpCount = otpService.countVerificationOtp(contactNo,request);
			Integer otpNumbar = Integer.parseInt(otpNos);
			if(VerificationOtpCount < otpNumbar){
			if(sessionOtp.getOtpNo().equals(otp)){
				otpService.removeOtpRecord(contactNo,request);
				otpValidated=true;
				//request.getSession().removeAttribute("otpBean");
			} else {
				logger.info("OTP Verification failed for "+contactNo+" while Registration");
			}
			}
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(otpValidated);
	}
}
