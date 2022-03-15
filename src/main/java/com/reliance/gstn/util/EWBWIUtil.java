package com.reliance.gstn.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;

import com.reliance.gstn.admin.exception.EwayBillApiException;
import com.reliance.gstn.external.wizard.controller.ExternalWizardEwayBillController;
import com.reliance.gstn.model.EwayBillRateMaster;
import com.reliance.gstn.model.EwayBillWIAuth;
import com.reliance.gstn.model.EwayBillWIMaster;
import com.reliance.gstn.model.ExternalWizardEwayBill;

public class EWBWIUtil {

	private static final Logger logger = Logger.getLogger(ExternalWizardEwayBillController.class);
	private static Map<String, EwayBillWIAuth> ewayBillWIAuthMap = new HashMap<String, EwayBillWIAuth>();

	private static Map<String, Map<String, String>> ewayBillWIMasterListMap = new HashMap<String, Map<String, String>>();

	private static Map<String, List<Map<String, String>>> ewayBillRateMasterListMap = new HashMap<String, List<Map<String, String>>>();

	private static String aesEncryptioney = "AD5652A44F0A472799021E934C51A4B15054998E955161A4";

	private static EncryptionUtil ecr = null;
	static {
		try {
			ecr = new EncryptionUtil(aesEncryptioney);
		} catch (Exception e) {
			logger.error("static error :: " + e);
		}
	}

	public static boolean isAuthInfoLoaded() {
		return !ewayBillWIAuthMap.isEmpty();
	}

	public static boolean isLoadEwayBillWIMasterList() {
		return !ewayBillWIMasterListMap.isEmpty();
	}

	public static boolean isLoadEwayBillRateMasterList() {
		return !ewayBillRateMasterListMap.isEmpty();
	}

	public static void loadAuthInfo(List<EwayBillWIAuth> authInfoL) {
		for (EwayBillWIAuth ewayBillWIAuth : authInfoL) {
			ewayBillWIAuthMap.put(ewayBillWIAuth.getClientId(), ewayBillWIAuth);
		}

	}

	public static Map<String, Map<String, String>> getLoadEwayBillWIMasterList() {
		return ewayBillWIMasterListMap;
	}

	public static Map<String, List<Map<String, String>>> getLoadEwayBillRateMasterList() {
		return ewayBillRateMasterListMap;
	}

	public static void loadEwayBillWIMasterList(List<EwayBillWIMaster> ewayBillWIMasterList) {
		for (EwayBillWIMaster ewayBillWIMaster : ewayBillWIMasterList) {
			if (ewayBillWIMaster.getMasterSubType() != null
					&& ewayBillWIMasterListMap.containsKey(ewayBillWIMaster.getMasterSubType())) {
				ewayBillWIMasterListMap.get(ewayBillWIMaster.getMasterSubType()).put(ewayBillWIMaster.getMasterCode(),
						ewayBillWIMaster.getMasterType());
			} else if (ewayBillWIMaster.getMasterSubType() != null) {
				Map<String, String> map = new HashMap<>();
				map.put(ewayBillWIMaster.getMasterCode(), ewayBillWIMaster.getMasterType());
				ewayBillWIMasterListMap.put(ewayBillWIMaster.getMasterSubType(), map);
			}

		}

	}

	public static void loadEwayBillRateMasterList(List<EwayBillRateMaster> ewayBillRateMasterList) {
		for (EwayBillRateMaster ewayBillWIMaster : ewayBillRateMasterList) {
			Map<String, String> map = new LinkedHashMap<>();
			map.put("code", ewayBillWIMaster.getMasterCode());
			map.put("value", ewayBillWIMaster.getMasterDesc());
			
			if (ewayBillRateMasterListMap.containsKey(ewayBillWIMaster.getMasterType())) {
				ewayBillRateMasterListMap.get(ewayBillWIMaster.getMasterType()).add(map);
			} else {
				List<Map<String, String>> list = new ArrayList<>();
				list.add(map);
				ewayBillRateMasterListMap.put(ewayBillWIMaster.getMasterType(), list);
			}

		}
		
	}

	public static boolean isValidRequest(HttpHeaders headers) throws EwayBillApiException {
		boolean isValidRequest = false;

		if (isValidRequest(headers, "secret_key") && isValidRequest(headers, "client_id")
				&& isValidRequest(headers, "app_code") && isValidRequest(headers, "ip_usr")) {
			logger.info("isValidRequest Entry ");
			logger.info("isValidRequest client_id :::: " + headers.get("client_id").get(0));
			EwayBillWIAuth ewayBillWIAuth = ewayBillWIAuthMap.get(headers.get("client_id").get(0));
			List<String> ipList = Arrays.asList(ewayBillWIAuth.getIp().split(","));
			try {
				isValidRequest = ewayBillWIAuth != null
						&& ewayBillWIAuth.getSecretKey().equals(headers.get("secret_key").get(0))
						&& String.valueOf(ewayBillWIAuth.getAppCode())
								.equals(ecr.decrypt(headers.get("app_code").get(0)))
						&& ipList.contains(headers.get("ip_usr").get(0));
				headers.put("app_code", Arrays.asList(ecr.decrypt(headers.get("app_code").get(0))));
				logger.info("isValidRequest ip_usr :::: " + headers.get("ip_usr").get(0));
				logger.info("isValidRequest secret_key :::: " + headers.get("secret_key").get(0));
				logger.info("isValidRequest app_code :::: " + ecr.decrypt(headers.get("app_code").get(0)));
			} catch (Exception e) {
				logger.error("isValidRequest error :: " + e);
				throw new EwayBillApiException(e.getMessage());
			}

			if (isValidRequest)
				return isValidRequest;
			else
				throw new EwayBillApiException("Unauthorized Access");

		}
		return isValidRequest;
	}

	public static boolean isValidRequestEk(HttpHeaders headers) throws EwayBillApiException {
		boolean isValidRequest = false;
		if (isValidRequest(headers, "secret_key") && isValidRequest(headers, "client_id")
				&& isValidRequest(headers, "app_code") && isValidRequest(headers, "ip_usr")) {
			logger.info("isValidRequestEk Entry ");
			logger.info("isValidRequestEk client_id :::: " + headers.get("client_id").get(0));
			EwayBillWIAuth ewayBillWIAuth = ewayBillWIAuthMap.get(headers.get("client_id").get(0));
			try {
				isValidRequest = ewayBillWIAuth != null
						&& ewayBillWIAuth.getSecretKey().equals(headers.get("secret_key").get(0))
						&& String.valueOf(ewayBillWIAuth.getAppCode())
								.equals(ecr.decrypt(headers.get("app_code").get(0)));
				headers.put("app_code", Arrays.asList(ecr.decrypt(headers.get("app_code").get(0))));

				logger.info("isValidRequestEk secret_key :::: " + headers.get("secret_key").get(0));
				logger.info("isValidRequestEk app_code :::: " + ecr.decrypt(headers.get("app_code").get(0)));
			} catch (Exception e) {
				logger.error("isValidRequestEk error :: " + e);
				throw new EwayBillApiException(e.getMessage());
			}
			if (isValidRequest)
				return isValidRequest;
			else
				throw new EwayBillApiException("Unauthorized Access");

		}
		return isValidRequest;
	}

	public static boolean isValidRequest(ExternalWizardEwayBill headers) throws EwayBillApiException {
		boolean isValidRequest = false;
		if (isValidRequest(headers.getSecret_key(), "secret_key") && isValidRequest(headers.getClient_id(), "client_id")
				&& isValidRequest(headers.getApp_code(), "app_code") && isValidRequest(headers.getIp_usr(), "ip_usr")) {
			logger.info("isValidRequestEk Entry ");
			logger.info("isValidRequestEk client_id :::: " + headers.getClient_id());
			EwayBillWIAuth ewayBillWIAuth = ewayBillWIAuthMap.get(headers.getClient_id());
			List<String> ipList = Arrays.asList(ewayBillWIAuth.getIp().split(","));
			try {

				isValidRequest = ewayBillWIAuth != null && ewayBillWIAuth.getSecretKey().equals(headers.getSecret_key())
						&& String.valueOf(ewayBillWIAuth.getAppCode()).equals(ecr.decrypt(headers.getApp_code()))
						&& ipList.contains(headers.getIp_usr());
				headers.setApp_code(ecr.decrypt(headers.getApp_code()));
				logger.info("isValidRequestEk secret_key :::: " + headers.getSecret_key());
				logger.info("isValidRequestEk app_code :::: " + ecr.decrypt(headers.getApp_code()));
			} catch (Exception e) {
				logger.error("isValidRequest error :: " + e);
				throw new EwayBillApiException(e.getMessage());
			}
			if (isValidRequest)
				return isValidRequest;
			else
				throw new EwayBillApiException("Unauthorized Access");

		}
		return isValidRequest;
	}

	private static boolean isValidRequest(HttpHeaders headers, String key) {
		boolean flag = false;
		if (headers.get(key) != null) {
			String data = headers.get(key).get(0);
			if (data != null && !data.trim().equals(""))
				flag = true;
			else {
				throw new EwayBillApiException(key + ": should not be empty");
			}
		} else {
			throw new EwayBillApiException(key + ": should not be empty");
		}
		return flag;
	}

	private static boolean isValidRequest(String headers, String key) {
		boolean flag = false;
		if (headers != null && !headers.trim().equals(""))
			flag = true;
		else {
			throw new EwayBillApiException(key + ": should not be empty");
		}

		return flag;
	}

	public static Integer validUserRequestId(HttpHeaders headers) {
		EwayBillWIAuth ewayBillWIAuth = ewayBillWIAuthMap.get(headers.get("client_id").get(0));
		try {
			boolean isValidRequest = ewayBillWIAuth != null
					&& ewayBillWIAuth.getSecretKey().equals(headers.get("secret_key").get(0))
					&& String.valueOf(ewayBillWIAuth.getAppCode()).equals(ecr.decrypt(headers.get("app_code").get(0)));
			if (isValidRequest)
				return ewayBillWIAuth.getId();
			else
				return null;
		} catch (Exception e) {
			logger.error("validUserRequestId error :: " + e);
			throw new EwayBillApiException(e.getMessage());
		}

	}

}
