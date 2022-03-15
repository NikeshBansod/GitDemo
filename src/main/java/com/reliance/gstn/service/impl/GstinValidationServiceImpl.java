package com.reliance.gstn.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.reliance.gstn.service.GstinValidationService;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.WebserviceCallUtil;

/**
 * @author Vivek2.Dubey
 *
 */
@Service
public class GstinValidationServiceImpl implements GstinValidationService{

	private static final Logger logger = Logger.getLogger(GstinValidationServiceImpl.class);
	@Value("${${env}.GSTIN_VALIDATION_URL}")
	//@Value("${prod.GSTIN_VALIDATION_URL}")
	private String gstinvalidationurl;
	
	/*@Value("http://aspapi.jiogst.com:8080/auth/v3.2/search?gstin={gstinNo}&action=TP")
	private String gstinvalidationurl;*/
	
	@Value("${${env}.asp.client.secret.key}")
//	@Value("${prod.asp.client.secret.key}")
	private String aspClientSecretKey;
	
	@Value("${${env}.aspclient.id}")
//	@Value("${prod.aspclient.id}")
	private String aspClientId;
	
	@Value("${source.device.string}")
	private String sourceDeviceString;
	
	@Value("${source.device}")
	private String sourceDevice;
	
	@Value("${${env}.app.code}")
	private String appCode;
	
	@Value("${device.string}")
	private String deviceString;
	
	@Override
	public String isValidGstin(String gstinNo) {
		// TODO Auto-generated method stub
		logger.info("Entry:");
		String webServiceUrl=updateUrlDynamicValues(gstinNo);
		logger.debug("url=="+webServiceUrl);
		Map<String,String>headersMap=getHeaderMap(gstinNo);
		String inputData="";
		Map<String,String>extraParams=getExtraParamMap();
		String response=WebserviceCallUtil.callWebservice(webServiceUrl, headersMap, inputData, extraParams);
		logger.info("Exit:");
		return response;
	}
	
	private String updateUrlDynamicValues(String gstinNo){
		String url=gstinvalidationurl.replace("{gstinNo}", gstinNo);
		return url;
	}
	
	private Map<String,String> getHeaderMap(String gstinId){
		Map<String,String>headersMap=new HashMap<String,String>();
		String invTxnString = GSTNUtil.genUniqueInvoiceTxn();
		String USER_AGENT = "Mozilla/5.0";
		
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Accept-Language", "en-US,en;q=0.5");
		headersMap.put("Content-Type", "application/json");
		headersMap.put("asp-clientsecretkey", aspClientSecretKey);
		headersMap.put("aspclient-id", aspClientId);
		headersMap.put("username", "Reliance.MH.2");
		headersMap.put("source-devicestring", sourceDeviceString);
		headersMap.put("device-string", deviceString);
		headersMap.put("gstin", gstinId);
		headersMap.put("source-device", sourceDevice);
		headersMap.put("location", "Mumbai");
		headersMap.put("app-code", appCode);
		headersMap.put("txn", invTxnString);
		headersMap.put("ip-usr", GSTNUtil.getCurrentMachineIpAddr());
		headersMap.put("state-cd", gstinId.substring(0, 2));
		return headersMap;
	}
	
	private Map<String,String> getExtraParamMap(){
		Map<String,String>extraParams=new HashMap<String,String>();
		extraParams.put("methodName", "GET");
		return extraParams;
	}

}
