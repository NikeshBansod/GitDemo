package com.reliance.gstn.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.beans.factory.annotation.Value;

public class HeaderUtil {
	
	@Value("${${env}.asp.client.secret.key}")
	private static String aspClientSecretKey;

	@Value("${${env}.aspclient.id}")
	private static String aspClientId;

	@Value("${source.device.string}")
	private static String sourceDeviceString;

	@Value("${source.device}")
	private static String sourceDevice;

	@Value("${${env}.app.code}")
	private static String appCode;

	@Value("${device.string}")
	private static String deviceString;


	@Value("${gstr.client.user}")
	private static String gstrClientUserId;

	@Value("${gstr.client.pwd}")
	private static String gstrClientPwd;

	
	public static  Map<String, String> getHeaderMap(String serviceType, String gstinId, String financialPeriod,
			String userName) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		
		
		
		String username = gstrClientUserId;
		String pwd = gstrClientPwd;
		String randomNum = GSTNUtil.genRandomNumber(10000000, 99999999);
		username = randomNum + username;
		pwd = randomNum + pwd;
		EncryptionUtil eu = new EncryptionUtil();
		String encUsername = eu.encrypt(username);
		String encPwd = eu.encrypt(pwd);
		String invTxnString = GSTNUtil.genUniqueInvoiceTxn();
		
		
		String USER_AGENT = "Mozilla/5.0";
		
		
		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Accept-Language", "en-US,en;q=0.5");
		headersMap.put("Content-Type", "application/json");
		headersMap.put("asp-clientsecretkey", aspClientSecretKey);
		headersMap.put("aspclient-id", aspClientId);
		headersMap.put("username", userName);
		headersMap.put("source-devicestring", sourceDeviceString);
		headersMap.put("source-device", getSourceDevice(serviceType));
		headersMap.put("device-string", getDeviceString(serviceType));

			
			
			
			headersMap.put("gstin", gstinId);
			headersMap.put("fp", financialPeriod);

			headersMap.put("location", "Mumbai");
			headersMap.put("app-code", appCode);
			headersMap.put("txn", invTxnString);
			headersMap.put("ip-usr", GSTNUtil.getCurrentMachineIpAddr());
			headersMap.put("state-cd", gstinId.substring(0, 2));
			headersMap.put("gstrclientUserName", encUsername);
			headersMap.put("gstrclientPassword", encPwd);
			headersMap.put("gstrclientServiceType", serviceType);

			headersMap=getAspDeviceString(serviceType, headersMap);

		return headersMap;
		
		
	}
	
	private static String getSourceDevice(String serviceType){
		if (serviceType == AspApiConstants.GSTR1_SABMITTOGSTN_L0_OTP_SERVICE_TYPE
				|| serviceType == AspApiConstants.GSTR1_SABMITTOGSTN_SERVICE_TYPE
				|| serviceType == AspApiConstants.GSTR1_FINAL_SABMITTOGSTN_SERVICE_TYPE
				|| serviceType == AspApiConstants.GSTR1_L0_OTP_SERVICE_TYPE
				|| serviceType == AspApiConstants.GSTR3B_GSTN_L2
				|| serviceType == AspApiConstants.GSTR3B_GSTN_SUBMIT
				|| serviceType == AspApiConstants.GSTR3B_GSTN_SAVE
				|| serviceType == AspApiConstants.GSTR1_SERVICE_GSTNSAVERESTATUS) {
			sourceDevice="Laptop";
		} 
		
		return sourceDevice;
		
	}
	
	
	private static String getDeviceString(String serviceType){
		if(serviceType == AspApiConstants.GSTR1_SERVICE_GSTNSAVERESTATUS
				|| serviceType == AspApiConstants.GSTR1_SERVICE_GSTNSUBMITRESTATUS ){
			deviceString="Device1";
		}
		
		return deviceString;
	}
	
	private static Map<String, String> getAspDeviceString(String serviceType,Map<String, String> headersMap){
		
		if (serviceType == AspApiConstants.GSTR1_SABMITTOGSTN_L0_OTP_SERVICE_TYPE
				|| serviceType == AspApiConstants.GSTR1_FINAL_SABMITTOGSTN_SERVICE_TYPE) {
			headersMap.put("asp-devicestring", "Laptop");
		}
		
		return headersMap;
	}

}
