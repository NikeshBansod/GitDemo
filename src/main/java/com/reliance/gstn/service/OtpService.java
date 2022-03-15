package com.reliance.gstn.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface OtpService {

	Map<Boolean, String> sendOtpToRegisteredUser(String userId, String mobileNo, HttpServletRequest request) throws Exception;

	Map<Boolean, String> sendOtpOnRegistration(String userMobileNo, HttpServletRequest request) throws Exception;

	void removeOtpRecord(String userMobileNo, HttpServletRequest request) throws Exception;
	
	Integer countVerificationOtp(String userMobileNo, HttpServletRequest request) throws Exception;
}
