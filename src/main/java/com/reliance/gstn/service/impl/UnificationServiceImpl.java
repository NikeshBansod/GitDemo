package com.reliance.gstn.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.reliance.gstn.dao.UnificationDao;
import com.reliance.gstn.model.OrganizationMasterUnification;
import com.reliance.gstn.model.UserMasterUnification;
import com.reliance.gstn.service.UnificationService;
import com.reliance.gstn.util.WebserviceCallUtil;

@Service
public class UnificationServiceImpl implements UnificationService{
	private static final Logger logger = Logger.getLogger(UnificationServiceImpl.class);
	
	@Autowired
	private UnificationDao unificationDao;
	
	@Value("${forgot_password_otp_validation}")
	private String forgotmpinurl;
	

	@Override
	public Map<String, Object> getOtp(String contactNo, String emailId, String panNumber,
			String userName, String password) {
		
		
		Map<String,Object> actionfrom = new HashMap<>();
		Map<String,Object> userDetailsrequest = new HashMap<>();
		Map<String,Object> orgDetailsrequest = new HashMap<>();
		Map<String,Object> responsemap = new HashMap<>();
		Map<String, Object> responsewebservice = new HashMap<String, Object>();
		String userNamenew = "G".concat(userName.substring(0,9));
		actionfrom.put("action", "genotp");
		

		orgDetailsrequest.put("pan", panNumber);
		orgDetailsrequest.put("orgTypeOthers", "");
		orgDetailsrequest.put("orgName", "");
		orgDetailsrequest.put("addressLine1", "");
		orgDetailsrequest.put("addressLine2", "");
		orgDetailsrequest.put("stdCode", "");
		orgDetailsrequest.put("emailId", "");
		
		userDetailsrequest.put("prefix", "");
		userDetailsrequest.put("firstName", "");
		userDetailsrequest.put("lastName", "");
		userDetailsrequest.put("gender", "");
		userDetailsrequest.put("department", "");
		userDetailsrequest.put("designation", "");
		userDetailsrequest.put("emailId", emailId);
		userDetailsrequest.put("mobileNumber", contactNo);
		userDetailsrequest.put("userName", userNamenew);
		userDetailsrequest.put("password", password);
		
		
		//responsemap.put("action", actionfrom);
		responsemap.put("action", "genotp");
		responsemap.put("orgDetails", orgDetailsrequest);
		responsemap.put("userDetails", userDetailsrequest);
		
		
		Map<String, String> headersMap = getUnificationHeader();
		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		String body = new Gson().toJson(responsemap);
		logger.info("unification otp generate bosy:"+body);
		String response = WebserviceCallUtil.callWebservice("http://10.147.111.149:8080/onboarding/v1.0/register", headersMap,body, extraParams);
		Gson gson = new Gson();
		responsewebservice = (Map<String, Object>) gson.fromJson(response.toString(),responsewebservice.getClass());

		return responsewebservice;
		
	}
	
	
	@Override
	public Map<String, Object> verifyOtp(String otp,String cachekey,String contactNo, String emailId, String panNumber,
			String userName, String password) {
		
		
		Map<String,Object> actionfrom = new HashMap<>();
		Map<String,Object> userDetailsrequest = new HashMap<>();
		Map<String,Object> orgDetailsrequest = new HashMap<>();
		Map<String,Object> responsemap = new HashMap<>();
		Map<String, Object> responsewebservice = new HashMap<String, Object>();
		String userNamenew = "G".concat(userName.substring(0,9));
		//actionfrom.put("action", "genotp");
		

		orgDetailsrequest.put("pan", panNumber);
		orgDetailsrequest.put("orgTypeOthers", "");
		orgDetailsrequest.put("orgName", "");
		orgDetailsrequest.put("addressLine1", "");
		orgDetailsrequest.put("addressLine2", "");
		orgDetailsrequest.put("stdCode", "");
		orgDetailsrequest.put("emailId", "");
		
		userDetailsrequest.put("prefix", "");
		userDetailsrequest.put("firstName", "");
		userDetailsrequest.put("lastName", "");
		userDetailsrequest.put("gender", "");
		userDetailsrequest.put("department", "");
		userDetailsrequest.put("designation", "");
		userDetailsrequest.put("emailId", emailId);
		userDetailsrequest.put("mobileNumber", contactNo);
		userDetailsrequest.put("userName", userNamenew);
		userDetailsrequest.put("password", password);
		
		
		//responsemap.put("action", actionfrom);
		responsemap.put("action", "register");
		responsemap.put("cachekey", cachekey);
		responsemap.put("otp", otp);
		responsemap.put("orgDetails", orgDetailsrequest);
		responsemap.put("userDetails", userDetailsrequest);
		
		
		Map<String, String> headersMap = getUnificationHeader();
		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		String body = new Gson().toJson(responsemap);
		logger.info("unification otp generate bosy:"+body);
		String response = WebserviceCallUtil.callWebservice("http://10.147.111.149:8080/onboarding/v1.0/register", headersMap,body, extraParams);
		Gson gson = new Gson();
		responsewebservice = (Map<String, Object>) gson.fromJson(response.toString(),responsewebservice.getClass());

		return responsewebservice;
		
	}
	
	@Override
	public UserMasterUnification login(String username, String password) {
		Map<String,Object> responsemap = new HashMap<>();
		Map<String, Object> responsewebservice = new HashMap<String, Object>();
		UserMasterUnification userDetails = new UserMasterUnification();
		OrganizationMasterUnification organizationDetails = new OrganizationMasterUnification();
		responsemap.put("userName", username);
		responsemap.put("password", password);
		
		Map<String, String> headersMap = getUnificationHeader();
		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		String body = new Gson().toJson(responsemap);
		logger.info("unification login reuqest body:"+body);
		String response = WebserviceCallUtil.callWebservice("http://10.147.111.149:8080/auth/v1.0/authenticateuser", headersMap,body, extraParams);
		Gson gson = new Gson();
		responsewebservice = (Map<String, Object>) gson.fromJson(response.toString(),responsewebservice.getClass());
		//return responsewebservice;
		Object msg=responsewebservice.get("msg");
		Object userdetails = responsewebservice.get("userDetails");
		Object orgdetails = responsewebservice.get("orgDetails");
		String statuscode=(String)((Map<String, Object>) msg).get("status_cd");
		String status=(String) ((Map<String, Object>) msg).get("status_msg");
		String firstName=(String)((Map<String, Object>) userdetails).get("firstName");
		String lastName=(String)((Map<String, Object>) userdetails).get("lastName");
		Double userid=(Double)((Map<String, Object>) userdetails).get("userId");
		Boolean isadmin=(Boolean)((Map<String, Object>) userdetails).get("isadmin");
		String userName=(String)((Map<String, Object>) userdetails).get("userName");
		Double companyId=(Double)((Map<String, Object>) orgdetails).get("companyId");
		String pan=(String)((Map<String, Object>) orgdetails).get("pan");
		Boolean registrationStatus=(Boolean)((Map<String, Object>) orgdetails).get("registrationStatus");
		String emailId=unificationDao.getEmailByContact(username);
		userDetails.setUserId(String.valueOf(userid));
		userDetails.setPassword(password);
		userDetails.setContactNo(username);
		userDetails.setUserName(username);
		userDetails.setFirstName(firstName);
		userDetails.setLastName(lastName);
		userDetails.setUserIdUnifi(userid);
		userDetails.setIsadmin(isadmin);
		userDetails.setUserRole("PRIMARY");
		userDetails.setDefaultEmailId(emailId);
		userDetails.setInvoiceSequenceType("Auto");
		organizationDetails.setPanNumber(pan);
		organizationDetails.setCompanyId(companyId);
		organizationDetails.setRegstatus(registrationStatus);
		organizationDetails.setState("maharashtra");
		userDetails.setOrganizationMasterUnification(organizationDetails);
		//userDetails = unificationDao.saveOrUpdateUserDetails(userDetails);
		return userDetails;
	}
	
	
	public Map <String , String > getUnificationHeader(){
	Map <String , String > unificationHeader = new HashMap<>();
    unificationHeader.put("Content-Type", "application/json");
    unificationHeader.put("channelId", "BILL_LITE");
    unificationHeader.put("sourceDevice", "DESKTOP");
    unificationHeader.put("clientTxnId", "LAPN24235325555");
    unificationHeader.put("clientIp", "137.232.3.35");
    unificationHeader.put("authToken", "X123");
   /* unificationHeader.put("cachekey", "111");
    unificationHeader.put("taxpayerCompanyId", "0");
    unificationHeader.put("taxPractitionerCompanyId", "123");
    unificationHeader.put("gskCompanyId", "3268");*/
    
    
    System.out.println(unificationHeader);
    
    return unificationHeader;
	}


	@Override
	public Map<String, Object> updateUser(UserMasterUnification userMasterUnification) {
		Map<String,Object> userDetailsrequest = new HashMap<>();
		Map<String,Object> orgDetailsrequest = new HashMap<>();
		Map<String,Object> responsemap = new HashMap<>();
		Map<String, Object> responsewebservice = new HashMap<String, Object>();
		
		orgDetailsrequest.put("pan", userMasterUnification.getOrganizationMasterUnification().getPanNumber());
		orgDetailsrequest.put("orgTypeOthers", userMasterUnification.getOrganizationMasterUnification().getOtherOrgType());
		orgDetailsrequest.put("orgTypeId", "");
		orgDetailsrequest.put("orgName", userMasterUnification.getOrganizationMasterUnification().getOrgName());
		orgDetailsrequest.put("addressLine1", userMasterUnification.getOrganizationMasterUnification().getAddress1());
		orgDetailsrequest.put("addressLine2", userMasterUnification.getOrganizationMasterUnification().getAddress2());
		orgDetailsrequest.put("stdCode", userMasterUnification.getOrganizationMasterUnification().getStdCode());
		orgDetailsrequest.put("pinCode", userMasterUnification.getOrganizationMasterUnification().getPinCode());
		orgDetailsrequest.put("landlineNo", userMasterUnification.getOrganizationMasterUnification().getLandlineNo());
		orgDetailsrequest.put("emailId", userMasterUnification.getOrganizationMasterUnification().getOrgEmailId());
		
		//orgDetailsrequest.put("orgType", userMasterUnification.getOrganizationMasterUnification().getOrgType());
		
		
		userDetailsrequest.put("prefix", userMasterUnification.getPrefix());
		userDetailsrequest.put("firstName", userMasterUnification.getFirstName());
		userDetailsrequest.put("lastName", userMasterUnification.getLastName());
		userDetailsrequest.put("gender", userMasterUnification.getGender());
		userDetailsrequest.put("department", userMasterUnification.getDepartment());
		userDetailsrequest.put("designation", userMasterUnification.getDesignation());
		userDetailsrequest.put("stdCode", userMasterUnification.getUserStdCode());
		userDetailsrequest.put("landlineNo", userMasterUnification.getUserLandlineNo());
		userDetailsrequest.put("emailId", userMasterUnification.getEmailId());
		userDetailsrequest.put("mobileNumber", userMasterUnification.getContactNo());
		userDetailsrequest.put("userName", userMasterUnification.getUserName());
		
		Double useridHeader=userMasterUnification.getUserIdUnifi();
		Double companyidHeader=userMasterUnification.getOrganizationMasterUnification().getCompanyId();
		
		logger.info("userid:"+useridHeader);
		logger.info("companyid:"+companyidHeader);
		
				responsemap.put("orgDetails", orgDetailsrequest);
				responsemap.put("userDetails", userDetailsrequest);
				
				Map<String, String> headersMap = getUnificationHeader();
				headersMap.put("userId",String.format("%.0f",useridHeader));
				headersMap.put("companyId",String.format("%.0f",companyidHeader));
				Map<String, String> extraParams = new HashMap<String, String>();
				extraParams.put("methodName", "POST");
				String body = new Gson().toJson(responsemap);
				logger.info("unification login reuqest body:"+body);
				String headers = new Gson().toJson(headersMap);
				logger.info("unification edit user reuqest body:"+headers);
				String response = WebserviceCallUtil.callWebservice("http://10.147.111.149:8080/onboarding/v1.0/completeregistration", headersMap,body, extraParams);
				Gson gson = new Gson();
				responsewebservice = (Map<String, Object>) gson.fromJson(response.toString(),responsewebservice.getClass());
		return responsewebservice;
	}




	@SuppressWarnings("unchecked")
	@Override
	public List<UserMasterUnification> updateprofile(Object userId, Object companyId,Object pannumber) {
		Map<String,Object> responsemap = new HashMap<>();
		Map<String,Object> responsemaporg = new HashMap<>();
		Map<String, Object> responsewebserviceuser = new HashMap<String, Object>();
		Map<String, Object> responsewebserviceorganization = new HashMap<String, Object>();
		UserMasterUnification userMasterUnification = new UserMasterUnification();
		UserMasterUnification userDetails = new UserMasterUnification();
		OrganizationMasterUnification organizationDetails = new OrganizationMasterUnification();
		OrganizationMasterUnification organizationDetailsheeder = new OrganizationMasterUnification();
		List<UserMasterUnification> userMasterUnificationList=new ArrayList<UserMasterUnification>();
		
		responsemap.put("action", "getbyuserid");
		responsemap.put("userSysId", String.format("%.0f",userId));
		Map<String, String> headersMap = getUnificationHeader();
		headersMap.put("userId",String.format("%.0f",userId));
		headersMap.put("companyId",String.format("%.0f",companyId));
		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		String body = new Gson().toJson(responsemap);
		logger.info("unification login reuqest body:"+body);
		String headers = new Gson().toJson(headersMap);
		logger.info("unification edit user reuqest body:"+headers);
		
		String response = WebserviceCallUtil.callWebservice("http://10.147.111.149:8080/profile/v1.0/userprofile", headersMap,body, extraParams);
		Gson gson = new Gson();
		responsewebserviceuser = (Map<String, Object>) gson.fromJson(response.toString(),responsewebserviceuser.getClass());
        
		
		organizationDetailsheeder.setPanNumber((String)pannumber);
		organizationDetailsheeder.setCompanyId(Double.valueOf(String.format("%.0f",companyId)));
		responsemaporg.put("action", "get");
		responsemaporg.put("orgDetails", organizationDetailsheeder);
		String bodyorg = new Gson().toJson(responsemaporg);
		logger.info("unification login reuqest body:"+bodyorg);
		String responseorg = WebserviceCallUtil.callWebservice("http://10.147.111.149:8080/onboarding/v1.0/companyprofile", headersMap,bodyorg, extraParams);
		Gson gsonorg = new Gson();
		responsewebserviceorganization = (Map<String, Object>) gsonorg.fromJson(responseorg.toString(),responsewebserviceorganization.getClass());
        
		Object msgorg=responsewebserviceuser.get("msg");
		Object orgdetails = responsewebserviceorganization.get("orgDetails");
		String statuscodeorg=(String)((Map<String, Object>) msgorg).get("status_cd");
		String statusorg=(String) ((Map<String, Object>) msgorg).get("status_msg");
		
		//String stdCode=(String)((Map<String, Object>) orgdetails).get("stdCode");
		String landlineNo=(String)((Map<String, Object>) orgdetails).get("landlineNo");
		Double companyIdorg=(Double)((Map<String, Object>) orgdetails).get("companyId");
		String orgName=(String)((Map<String, Object>) orgdetails).get("orgName");
		String registration_status=(String)((Map<String, Object>) orgdetails).get("registration_status");
		String pinCode=(String)((Map<String, Object>) orgdetails).get("pinCode");
		String addressLine1=(String)((Map<String, Object>) orgdetails).get("addressLine1");
		String addressLine2=(String)((Map<String, Object>) orgdetails).get("addressLine2");
		String emailIdorg=(String)((Map<String, Object>) orgdetails).get("emailId");
		String pan=(String)((Map<String, Object>) orgdetails).get("pan");
		String orgTypeOthers=(String)((Map<String, Object>) orgdetails).get("orgTypeOthers");
		organizationDetails.setPanNumber(pan);
		organizationDetails.setAddress1(addressLine1);
		organizationDetails.setAddress2(addressLine2);
		//organizationDetails.setStdCode(stdCode);
		organizationDetails.setOrgEmailId(emailIdorg);
		organizationDetails.setCompanyId(companyIdorg);
		organizationDetails.setLandlineNo(landlineNo);
		organizationDetails.setOrgName(orgName);
		organizationDetails.setOrgType(orgTypeOthers);
		organizationDetails.setOtherOrgType(orgTypeOthers);
		organizationDetails.setPinCode(Integer.parseInt(pinCode));
		//organizationDetails.setOrgContactNo(orgContactNo);
	
		
		Object msg=responsewebserviceuser.get("msg");
		List userdetails = (List) responsewebserviceuser.get("userlist");
		Integer userlength = userdetails.size();
		//Object orgdetails = responsewebservice.get("orgDetails");
		String statuscode=(String)((Map<String, Object>) msg).get("status_cd");
		String status=(String) ((Map<String, Object>) msg).get("status_msg");
		for(int i=0;i<userlength;i++){
			String firstName=(String)((Map<String, Object>) userdetails.get(i)).get("firstName");
			//String stdCodeuser=(String)((Map<String, Object>) userdetails.get(i)).get("stdCode");
			String lastName=(String)((Map<String, Object>) userdetails.get(i)).get("lastName");
			Double userid=(Double)((Map<String, Object>) userdetails.get(i)).get("userSysId");
			//Boolean isadmin=(Boolean)((Map<String, Object>) userdetails.get(i)).get("adminRole");
			String userName=(String)((Map<String, Object>) userdetails.get(i)).get("userName");
			String prefix=(String)((Map<String, Object>) userdetails.get(i)).get("prefix");
			String mobilenumber=(String)((Map<String, Object>) userdetails.get(i)).get("mobileNumber");
			String designation=(String)((Map<String, Object>) userdetails.get(i)).get("designation");
			String department=(String)((Map<String, Object>) userdetails.get(i)).get("department");
			String userlandlineno=(String)((Map<String, Object>) userdetails.get(i)).get("landLineNumber");
			String useremailId=(String)((Map<String, Object>) userdetails.get(i)).get("emailId");
			//Double companyIdVal=(Double)((Map<String, Object>) orgdetails).get("companyId");
			//String pan=(String)((Map<String, Object>) orgdetails).get("pan");
			//Boolean registrationStatus=(Boolean)((Map<String, Object>) orgdetails).get("registrationStatus");
			userDetails.setUserId(String.valueOf(userid));
			userDetails.setContactNo(mobilenumber);
			userDetails.setUserName(userName);
			userDetails.setFirstName(firstName);
			userDetails.setLastName(lastName);
			userDetails.setUserIdUnifi(userid);
			userDetails.setPrefix(prefix);
			userDetails.setUserLandlineNo(userlandlineno);
			//userDetails.setUserStdCode(stdCodeuser);
			//userDetails.setIsadmin(isadmin);
			userDetails.setDesignation(designation);
			userDetails.setDepartment(department);
			userDetails.setDefaultEmailId(useremailId);
			if(prefix.equalsIgnoreCase("Mr.")){
				userDetails.setGender("Male");
			}
			else{
				userDetails.setGender("Female");
			}
			//userDetails.setUserLandlineNo(userlandlineno);
			userDetails.setUserRole("PRIMARY");
			userDetails.setInvoiceSequenceType("Auto");
			organizationDetails.setState("maharashtra");
			userDetails.setOrganizationMasterUnification(organizationDetails);
		}
		userMasterUnificationList.add(userDetails);
		
		
		return userMasterUnificationList;
	}
	
	@Override
	public Map<String, Object> requestOtp(String userName) {
		// TODO Auto-generated method stub
		Map<String, String> headersMap = getUnificationHeader();
		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		Map<String, String> bodymap = new HashMap<String, String>();
		bodymap.put("userName", userName);
		bodymap.put("action", "forgotpassword");
		String body = new Gson().toJson(bodymap);
		String response = WebserviceCallUtil.callWebservice(forgotmpinurl, headersMap,body, extraParams);
		Map<String, Object> responsemap = new HashMap<>();
		Gson gson =new Gson();
		responsemap=(Map<String, Object>)gson.fromJson(response.toString(), responsemap.getClass());
		return responsemap;
	}


	@Override
    public Map<String, Object> verifyOtpSavePassword(String cacheKey, String otp, String password) {
           // TODO Auto-generated method stub
           Map<String, String> headersMap = getUnificationHeader();
           Map<String, String> extraParams = new HashMap<String, String>();
           extraParams.put("methodName", "POST");
           Map<String, String> bodymap = new HashMap<String, String>();
           bodymap.put("cachekey", cacheKey);
           bodymap.put("otp", otp);
           bodymap.put("newPassword", password);
           bodymap.put("action", "setpassword");
           String body = new Gson().toJson(bodymap);
           String response = WebserviceCallUtil.callWebservice(forgotmpinurl, headersMap,body, extraParams);
           Map<String, Object> responsemap = new HashMap<>();
           Gson gson =new Gson();
           responsemap=(Map<String, Object>)gson.fromJson(response.toString(), responsemap.getClass());
           return responsemap;
    }


	
	@Override
	@Transactional
	public String saveOrUpdateUserDetails(UserMasterUnification userMasterUni) {
		
		return unificationDao.saveOrUpdateUserDetails(userMasterUni);
	}


	@Override
	public Map<String, Object> updateMyProfile(UserMasterUnification userMasterUnification) {
		Map<String,Object> userDetailsrequest = new HashMap<>();
		Map<String,Object> orgDetailsrequest = new HashMap<>();
		Map<String,Object> responsemap = new HashMap<>();
		Map<String,Object> responsemapuser = new HashMap<>();
		Map<String, Object> responsewebserviceorg = new HashMap<String, Object>();
		Map<String, Object> responsewebserviceuser = new HashMap<String, Object>();
		Map<String, Object> userMasterUnificationresponse= new HashMap<String, Object>();
		
		orgDetailsrequest.put("companyId", userMasterUnification.getOrganizationMasterUnification().getCompanyId());
		orgDetailsrequest.put("pan", userMasterUnification.getOrganizationMasterUnification().getPanNumber());
		orgDetailsrequest.put("orgTypeOthers", userMasterUnification.getOrganizationMasterUnification().getOtherOrgType());
		orgDetailsrequest.put("orgTypeId", "");
		orgDetailsrequest.put("orgName", userMasterUnification.getOrganizationMasterUnification().getOrgName());
		orgDetailsrequest.put("addressLine1", userMasterUnification.getOrganizationMasterUnification().getAddress1());
		orgDetailsrequest.put("addressLine2", userMasterUnification.getOrganizationMasterUnification().getAddress2());
		orgDetailsrequest.put("stdCode", userMasterUnification.getOrganizationMasterUnification().getStdCode());
		orgDetailsrequest.put("pinCode", userMasterUnification.getOrganizationMasterUnification().getPinCode());
		orgDetailsrequest.put("landlineNo", userMasterUnification.getOrganizationMasterUnification().getLandlineNo());
		orgDetailsrequest.put("emailId", userMasterUnification.getOrganizationMasterUnification().getOrgEmailId());
		//orgDetailsrequest.put("orgType", userMasterUnification.getOrganizationMasterUnification().getOrgType());
		
		responsemap.put("action", "updateattribs");
		responsemap.put("entityType", "MC");
		responsemap.put("orgDetails", orgDetailsrequest);
		Double useridHeader=userMasterUnification.getUserIdUnifi();
		Double companyidHeader=userMasterUnification.getOrganizationMasterUnification().getCompanyId();
		
		Map<String, String> headersMap = getUnificationHeader();
		headersMap.put("userId",String.format("%.0f",useridHeader));
		headersMap.put("companyId",String.format("%.0f",companyidHeader));
		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		String body = new Gson().toJson(responsemap);
		logger.info("unification update org reuqest body:"+body);
		String headers = new Gson().toJson(headersMap);
		logger.info("unification update org reuqest body:"+headers);
		String response = WebserviceCallUtil.callWebservice("http://10.147.111.149:8080/onboarding/v1.0/companyprofile", headersMap,body, extraParams);
		Gson gson = new Gson();
		responsewebserviceorg = (Map<String, Object>) gson.fromJson(response.toString(),responsewebserviceorg.getClass());
		
		Object msgorg=responsewebserviceorg.get("msg");
		String statuscodeorg=(String)((Map<String, Object>) msgorg).get("status_cd");
		String statusorg=(String) ((Map<String, Object>) msgorg).get("status_msg");
		
		/*responsemapuser.put("action", "update");
		responsemapuser.put("userSysId", userMasterUnification.getUserIdUnifi());
		responsemapuser.put("prefix", userMasterUnification.getPrefix());
		responsemapuser.put("firstName", userMasterUnification.getFirstName());
		responsemapuser.put("lastName", userMasterUnification.getLastName());
		//responsemapuser.put("gender", userMasterUnification.getGender());
		responsemapuser.put("department", userMasterUnification.getDepartment());
		responsemapuser.put("designation", userMasterUnification.getDesignation());
		responsemapuser.put("stdCode", userMasterUnification.getUserStdCode());
		responsemapuser.put("landlineNo", userMasterUnification.getUserLandlineNo());
		responsemapuser.put("adminRole", "Y");
		responsemapuser.put("isActive", "Y");
		responsemapuser.put("emailId", "john.doe@dummy.com");
		responsemapuser.put("mobileNumbe", "6666677777");
		responsemapuser.put("userName", "johndoe");
	
		String bodyuser = new Gson().toJson(responsemapuser);
		logger.info("unification update org reuqest body:"+bodyuser);
		String responseuser = WebserviceCallUtil.callWebservice("http://10.147.111.149:8080/profile/v1.0/userprofile", headersMap,bodyuser, extraParams);
		Gson gsonuser = new Gson();
		responsewebserviceuser = (Map<String, Object>) gsonuser.fromJson(responseuser.toString(),responsewebserviceuser.getClass());
		*/
		
		/*Object msuser=responsewebserviceorg.get("msg");
		String statuscodeuser=(String)((Map<String, Object>) msuser).get("status_cd");
		String statususer=(String) ((Map<String, Object>) msuser).get("status_msg");
		*/
		//userMasterUnificationresponse.put("statuscodeuser",statuscodeuser);
		//userMasterUnificationresponse.put("statususer", statususer);
		userMasterUnificationresponse.put("statuscodeorg",statuscodeorg);
		userMasterUnificationresponse.put("statusorg", statusorg);
		return userMasterUnificationresponse;
	}


}
