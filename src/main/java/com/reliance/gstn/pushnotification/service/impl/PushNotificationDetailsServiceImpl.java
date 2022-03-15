package com.reliance.gstn.pushnotification.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.reliance.gstn.model.PushNotificationProfile;
import com.reliance.gstn.pushnotification.service.PushNotificationDetailsService;
import com.reliance.gstn.util.WebserviceCallUtil;

/**
 * @author aman1.bansal
 *
 */
@Service
public class PushNotificationDetailsServiceImpl implements PushNotificationDetailsService {

	private static final Logger logger = Logger.getLogger(PushNotificationDetailsServiceImpl.class);

	@Value("${client_id}")
	private String clientid;

	@Value("${secret_key}")
	private String secretkey;

	@Value("${app_code}")
	private String appcode;

	@Value("${ip_usr}")
	private String ipaddress;
	
	@Value("${${env}.saveImeiAndContactNumberOnLogin.url}")
	private String saveImeiAndContactNumberOnLogin;
	
	@Value("${${env}.getUserProfileDetails.url}")
	private String getUserProfileDetails;

	@SuppressWarnings("unchecked")
	private Map<String, Object> webServiceCall(String apiUrl, String body) {
		logger.info("Entry:");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, String> headersMap = createApiHeader();
		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");

		String response = WebserviceCallUtil.callWebservice(apiUrl, headersMap, body, extraParams);
		logger.info("Payload Response :: " + response);

		Gson gson = new Gson();
		responseMap = (Map<String, Object>) gson.fromJson(response.toString(), responseMap.getClass());

		logger.info("Exit");
		return responseMap;
	}

	public Map<String, String> createApiHeader() {
		Map<String, String> headersMap = new HashMap<>();
		headersMap.put("client-id", clientid);
		headersMap.put("secret-key", secretkey);
		headersMap.put("Content-Type", "application/json");
		headersMap.put("app-code", appcode);
		headersMap.put("ip-address", ipaddress);
		return headersMap;
	}

	@Override
	public String pushAddorUpdateDetialsMicroService(String contactNo, String iMeiNo) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		logger.info("Entry:");
		String response = "";
		map.put("contactno", contactNo);
		map.put("imeino", iMeiNo);
		String body = new Gson().toJson(map);
		logger.info("Body : " + body);

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = webServiceCall(saveImeiAndContactNumberOnLogin, body);
		if (responseMap == null) {
			response = "failure";
		} else {
			response = responseMap.get("status").toString();
		}
		logger.info("Exit");
		return response;

	}

	@SuppressWarnings("unchecked")
	@Override
	public PushNotificationProfile fetchUserProfile(String imeiNo,String dataSend) {
		logger.info("Entry:");
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("imeino", imeiNo);
		map.put("datasend",dataSend);
		String body = new Gson().toJson(map);
		logger.info("Body : " + body);
		Map<String, Object> profile=new HashMap<>();
		profile= webServiceCall(getUserProfileDetails, body);
		PushNotificationProfile pushNotificationProfile = new PushNotificationProfile();
		if(profile.get("status").equals("failure") || profile.get("CUSTOMER_TYPE").equals("") || profile.get("EWAY_BILL").equals("")) {
			pushNotificationProfile.setCustomerType("NA");
			pushNotificationProfile.setEwayBill("N");
			pushNotificationProfile.setState(fetchStatesFromResp((List<String>)profile.get("STATE")));
			pushNotificationProfile.setVerticalType("NA");
		}else {
			pushNotificationProfile.setCustomerType((String)profile.get("CUSTOMER_TYPE"));
			pushNotificationProfile.setEwayBill((String)profile.get("EWAY_BILL"));
			pushNotificationProfile.setState(fetchStatesFromResp((List<String>)profile.get("STATE")));
			pushNotificationProfile.setVerticalType((String)profile.get("VERTICAL_TYPE"));
		}
		logger.info("Exit");
		return pushNotificationProfile;
	}
	
	public Set<String> fetchStatesFromResp(List<String> statesList) {
		Set<String> states = new HashSet<String>();
		if(statesList != null && statesList.size() > 0) {
			for(String ss : statesList) {
				states.add(ss);
			}
		}
		
		return states;
		
	}

}
