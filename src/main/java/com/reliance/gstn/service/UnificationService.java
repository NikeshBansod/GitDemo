package com.reliance.gstn.service;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.model.UserMasterUnification;


public interface UnificationService {
	

	Map<String, Object> getOtp(String contactNo, String emailId, String panNumber,String userName, String password);

	Map<String, Object> verifyOtp(String otp,String cachekey,String contactNo, String emailId, String panNumber,String userName, String password);

	UserMasterUnification login(String username, String password);
	
	Map<String, Object> requestOtp(String userName);

	Map<String, Object> verifyOtpSavePassword(String cacheKey, String otp, String password);

	Map<String, Object> updateUser(UserMasterUnification userMasterUnification);
	
	List<UserMasterUnification>  updateprofile(Object userId, Object companyId,Object pannumber);

	String saveOrUpdateUserDetails(UserMasterUnification userMasterUni);
	
	Map<String, Object> updateMyProfile(UserMasterUnification userMasterUnification);
	

}
