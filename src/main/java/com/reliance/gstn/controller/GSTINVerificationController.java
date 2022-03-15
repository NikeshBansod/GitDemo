package com.reliance.gstn.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reliance.gstn.model.ApiBean;
import com.reliance.gstn.util.GSTNUtil;
//import com.reliance.gstn.util.LoggerUtil;

@Controller
public class GSTINVerificationController {

	private static final Logger logger = Logger
			.getLogger(GSTINVerificationController.class);
	
	@Value("${GSTIN_VERIFY_API_SEND_OTP_URL}")
	private String gstinVerifyApiSendOtpUrl;
	
	@Value("${GSTIN_VERIFY_SECURITY_KEY}")
	private String gstinVerifySecurityKey;
	
	@Value("${GSTIN_VERIFY_API_VALIDATE_OTP_URL}")
	private String gstinVerifyApiValidateOtpUrl;

	@RequestMapping(value = "/verifyGstin", method = RequestMethod.GET)
	@ResponseBody
	public String verifyGstin( HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		String response="";
		try {
		String userName="Reliance.MH.1";
		String location=httpRequest.getParameter("location");
		String stateCode=httpRequest.getParameter("name");
		String sourceDevice=httpRequest.getParameter("deviceOS");
		String sourceDeviceString=httpRequest.getParameter("deviceType");
		String appCode="01";
		
		ApiBean api = new ApiBean();
		api.setGstinverifyUrl(gstinVerifyApiSendOtpUrl);
		api.setSecurityKey(gstinVerifySecurityKey);
		api.setUserName(userName);
		api.setLocation(location);
		api.setStateCode(stateCode);
		api.setSourceDevice(sourceDevice);
		api.setSourceDeviceString(sourceDeviceString);
		api.setAppCode(appCode);
		api.setUserIp(httpRequest.getRemoteAddr());
		
			response=GSTNUtil.verifyGstinService(api);
		} catch (Exception e) {
			logger.error("Error in:",e);
			System.out.println(e);
		}
		logger.info("Exit");
		return response;
	}

	@RequestMapping(value = "/gstnOtpValidate", method = RequestMethod.GET)
	@ResponseBody
	public String gstnOtpValidate( HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		String response="";
		try {
		String userName="Reliance.MH.1";
		String location=httpRequest.getParameter("location");
		String stateCode=httpRequest.getParameter("name");
		String sourceDevice=httpRequest.getParameter("deviceOS");
		String sourceDeviceString=httpRequest.getParameter("deviceType");
		String appCode="01";
		String gstinOtp = httpRequest.getParameter("gstinOtp");
		
		ApiBean api = new ApiBean();
		api.setGstinverifyUrl(gstinVerifyApiValidateOtpUrl);
		api.setSecurityKey(gstinVerifySecurityKey);
		api.setUserName(userName);
		api.setLocation(location);
		api.setStateCode(stateCode);
		api.setSourceDevice(sourceDevice);
		api.setSourceDeviceString(sourceDeviceString);
		api.setAppCode(appCode);
		api.setUserIp(httpRequest.getRemoteAddr());
		api.setGstinOtp(gstinOtp);
		
			response=GSTNUtil.validateGstinOtp(api);
		} catch (Exception e) {
			logger.error("Error in:",e);
			System.out.println(e);
		}
		logger.info("Exit");
		return response;
	}

}
