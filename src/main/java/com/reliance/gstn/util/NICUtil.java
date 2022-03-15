package com.reliance.gstn.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.reliance.gstn.admin.exception.EwayBillApiException;
import com.reliance.gstn.model.EWayBill;
import com.reliance.gstn.model.NicUserDetails;
import com.reliance.gstn.model.UserMaster;

public class NICUtil {

	public static String getRequestString(Object object) {
		String json = null;
		Gson gson = new Gson();
		json = gson.toJson(object);
		return json;
	}

	public static Map getResponseStringToObject(String jsonInString) {
		Gson gson = new Gson();
		Map map = gson.fromJson(jsonInString, Map.class);
		return map;
	}

	public static Object getResponseStringToObject(String jsonInString, Class<?> classz) {
		Gson gson = new Gson();
		Object map = gson.fromJson(jsonInString, classz);
		return map;
	}

	public static String getAuthTokenRequestString(NicUserDetails object) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put(AspApiConstants.JSON_ACTION, AspApiConstants.AUTH);
		map.put(AspApiConstants.USER_ID, object.getUserId());
		map.put(AspApiConstants.PWD, object.getPassword());
		return getRequestString(map);
	}

	public static String getReAuthTokenRequestString(NicUserDetails object) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put(AspApiConstants.JSON_ACTION, AspApiConstants.RE_AUTH);
		map.put(AspApiConstants.USER_ID, object.getUserId());
		map.put(AspApiConstants.PWD, object.getPassword());
		return getRequestString(map);
	}

	public static Map<String, String> getAuthTokenHeaderMap(NicUserDetails object) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put(AspApiConstants.CONTENT_TYPE, AspApiConstants.APPLICATION_JSON);
		map.put(AspApiConstants.EWB_USER_ID, object.getUserId());
		map.put(AspApiConstants.EWB_SEC_KEY, object.getEwayBillSecretKey());
		map.put(AspApiConstants.EWB_CLIENT_ID, object.getEwayBillClientId());
		map.put(AspApiConstants.API_VER, AspApiConstants.API_VER_1_03);
		map.put(AspApiConstants.TXN, getTxn());
		if (object.getGstin() != null)
			map.put(AspApiConstants.GSTIN, object.getGstin());
		if (object.getAuthToken() != null) {
			map.put(AspApiConstants.AUTH_TOKEN, object.getAuthToken());
			map.put(AspApiConstants.USER_IP, "127.0.0.1");
		}
		return map;

	}

	public static Map<String, String> getEWayBillOnBoardingHeaderMap(EWayBill eWayBill) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put(AspApiConstants.CONTENT_TYPE, AspApiConstants.APPLICATION_JSON);
		map.put(AspApiConstants.EWB_SEC_KEY, eWayBill.getEwayBillSecretKey());
		map.put(AspApiConstants.EWB_CLIENT_ID, eWayBill.getEwayBillClientId());
		return map;

	}

	public static Map<String, String> getEwayBillRequest(NicUserDetails object) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put(AspApiConstants.JSON_ACTION, AspApiConstants.GENEWAYBILL);
		map.put(AspApiConstants.JSON_DATA, object.getData());
		return map;

	}

	public static Map<String, String> getCancelEwayBillRequest(NicUserDetails object) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put(AspApiConstants.JSON_ACTION, AspApiConstants.CAN_EWAYBILL);
		map.put(AspApiConstants.JSON_DATA, object.getData());
		return map;

	}

	public static Map<String, Object> getEWayBillOnBoardingRequest(UserMaster user, EWayBill eWayBill) {
		List<Object> customersList = new ArrayList<>();
		Map<String, Object> customers = new LinkedHashMap<>();
		customers.put(AspApiConstants.COMPANY_NAME, user.getOrganizationMaster().getOrgName());
		customers.put(AspApiConstants.CUSTOMER_NAME, user.getOrganizationMaster().getOrgName());
		customers.put(AspApiConstants.CUSTOMER_NUM, user.getContactNo());
		customers.put(AspApiConstants.CUSTOMER_EMAILID, user.getDefaultEmailId());
		List<Object> userList = new ArrayList<>();
		userList.add(eWayBill.getNic_id());
		customers.put(AspApiConstants.EWAYBILL_USERNAME, userList);
		customersList.add(customers);
		Map<String, Object> map = new LinkedHashMap<>();
		map.put(AspApiConstants.CUSTOMERS, customersList);
		return map;

	}

	public static Map<String, Object> getEWayBillOnBoardingRequest(Map<String, Object> responseMap,
			Map<String, String> customerbonboarding) {
		List<Object> customersList = new ArrayList<>();
		Map<String, Object> customers = new LinkedHashMap<>();
		customers.put(AspApiConstants.COMPANY_NAME, "" + responseMap.get("lgnm"));
		customers.put(AspApiConstants.CUSTOMER_NAME, "" + responseMap.get("tradeNam"));
		customers.put(AspApiConstants.CUSTOMER_NUM, customerbonboarding.get("mobile_number"));
		customers.put(AspApiConstants.CUSTOMER_EMAILID, customerbonboarding.get("email_id"));
		List<Object> userList = new ArrayList<>();
		userList.add(customerbonboarding.get(AspApiConstants.NIC_USER_ID));
		customers.put(AspApiConstants.EWAYBILL_USERNAME, userList);
		customersList.add(customers);
		Map<String, Object> map = new LinkedHashMap<>();
		map.put(AspApiConstants.CUSTOMERS, customersList);
		return map;

	}

	public static Map<String, Object> getCancelEWayBillRequest(EWayBill eWayBill) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put(AspApiConstants.EWB_NO, eWayBill.getEwaybillNo());
		map.put(AspApiConstants.CANCEL_RSN_CODE, 2);
		map.put(AspApiConstants.CANCEL_RMK, eWayBill.getCancelRmrk());
		return map;

	}

	public static String getStringDate(Date date1) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(date1);
		return date;
	}

	public static String getStringDate(String date) {
		try {
			Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
			return getStringDate(date1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	public static java.sql.Timestamp getSQLDate(String date) {
		try {
			Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
			return new java.sql.Timestamp(date1.getTime());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	public static java.sql.Timestamp getSQLDate() {

		try {
			Date date = new Date();
			return new java.sql.Timestamp(date.getTime());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	public static String getSQLDate(java.sql.Timestamp Timestamp) {
		String formattedDate = null;
		try {
			Date date = new Date();
			date.setTime(Timestamp.getTime());
			formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
		} catch (Exception e) {
			// TODO: handle exception
			formattedDate = " ";
		}
		return formattedDate;

	}

	public static Date getDate(String date) {
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return date1;

	}

	private static String getTxn() {
		String saltStr = null;
		try {
			String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			StringBuilder salt = new StringBuilder();
			Random rnd = new Random();
			while (salt.length() < 32) {
				int index = (int) (rnd.nextFloat() * SALTCHARS.length());
				salt.append(SALTCHARS.charAt(index));
			}
			saltStr = salt.toString();
		} catch (Exception e) {

		}
		return saltStr;

	}

	public static Map<String, Object> getExcObjDesc(String message) {
		Map<String, Object> exObject = new LinkedHashMap<>();
		exObject.put(AspApiConstants.STATUS_CD, AspApiConstants.ER_CD);
		exObject.put(AspApiConstants.STATUS, AspApiConstants.FAILURE);
		if (message != null)
			exObject.put(AspApiConstants.ERROR_DESC, message);
		else
			exObject.put(AspApiConstants.ERROR_DESC, " please try again later");
		exObject.put(AspApiConstants.ERROR_CODE, AspApiConstants.EWAYBILL_ERROR);
		return exObject;
	}

	public static void getExceptionMsg(String message) throws EwayBillApiException{
		throw new EwayBillApiException(message);
	}

	public static Map<String, Object> getExcObjDesc() {
		Map<String, Object> exObject = new LinkedHashMap<>();
		exObject.put(AspApiConstants.STATUS_CD, AspApiConstants.ER_CD);
		exObject.put(AspApiConstants.STATUS, AspApiConstants.FAILURE);
		exObject.put(AspApiConstants.ERROR_DESC, "Unauthorized Access ");
		exObject.put(AspApiConstants.ERROR_CODE, AspApiConstants.EWAYBILL_ERROR);
		return exObject;
	}

	public static double getRoundingMode(double input) {
		DecimalFormat df2 = new DecimalFormat(".##");
		return Double.parseDouble(df2.format(input));
	}

}
