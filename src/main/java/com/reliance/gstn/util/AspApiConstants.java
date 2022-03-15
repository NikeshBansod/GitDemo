package com.reliance.gstn.util;

public class AspApiConstants {

	public static final String GSTR1_SERVICE_TYPE = "GSTR1";

	public static final String GSTR1_L0_SERVICE_TYPE = "L0";

	public static final String GSTR1_L2_SERVICE_TYPE = "L2";

	public static final String GSTR1_OTP_SERVICE_TYPE = "OTPREQUEST";

	public static final String GSTR1_SAVETOGSTN_SERVICE_TYPE = "GSTNSAVE";
	
	
	public static final String GSTR1_SAVETOGSTN_WITHOTP_SERVICE_TYPE="GSTNSAVEOTP";

	public static final String GSTR1_SERVICE_STATUS = "GSTR1STATUS";
	
	public static final String GSTR1_SABMITTOGSTN_SERVICE_TYPE="GSTNL0";
	
	public static final String GSTR1_FINAL_SABMITTOGSTN_SERVICE_TYPE="GSTNSUBMIT";
	
	public static final String GSTR1_SABMITTOGSTN_L0_OTP_SERVICE_TYPE="GSTNSUBMITOTP";
	
	public static final String GSTR1_L0_OTP_SERVICE_TYPE="GSTNL0OTP";
	
	public static final String GSTR1_FILE_OTP_TOGSTN_TYPE="GSTNFILEOTP";
	
	public static final String GSTR1_FILE_TOGSTN_TYPE="GSTNFILE";
	
	public static final String GET_SSESSION_FOR_FILING="GETGSTNSESSION";

	public static final String GSTR1_SERVICE_RESTATUS = "RETSTATUS";
	
	public static final String GSTR1_SERVICE_GSTNSUBMITRESTATUS = "GSTNSUBMITRESTATUS";
	
	public static final String GSTR1_SERVICE_GSTNSAVERESTATUS = "GSTNSAVERESTATUS";

	public static final String GSTR1_CUSTOM_DS = "BILL-DS";

	public static final String GSTR1_CUSTOM_BGRP = "BILL-BGRP";

	public static final String GSTR1_CUSTOM_BLOC = "BILL-BLOC";

	public static final String GSTR1_CUSTOM_BID = "BILL-ID";

	public static final String GSTR1_B2CS_STORE_ID = "BILLSTORE";

	public static final String GSTR1_B2B = "B2B";

	public static final String GSTR1_B2CS = "B2CS";

	public static final String GSTR1_B2CL = "B2CL";

	public static final String GSTR1_STATUS_SERVICE_RESPONSE = "Success";

	public static final int GSTR1_UNIQUE_TXN_SIZE = 32;

	public static final String INV_SERVICE_IGST_CATEGORY = "CATEGORY_WITH_IGST";

	public static final String CREDIT_NOTE = "creditNote";

	public static final String DEBIT_NOTE = "debitNote";

	public static final String IGST = "IGST";

	public static final String CGST = "CGST";

	public static final String SGST = "SGST";

	public static final String JSON_ACTION = "action";
	public static final String JSON_DATA = "data";
	public static final String AUTH = "auth";
	public static final String RE_AUTH = "reauth";
	public static final String USER_ID = "username";
	public static final String PWD = "password";
	public static final String ORG_ID = "orgid";
	public static final String EWB_USER_ID = "ewb-user-id";
	public static final String EWB_SEC_KEY = "ewbs_secretkey";
	public static final String EWB_CLIENT_ID = "ewbsclient_id";
	public static final String GSTIN = "gstin";
	public static final String CONTENT_TYPE = "content-type";
	public static final String APPLICATION_JSON = "application/json";
	public static final String APPLICATION_XML = "application/xml";
	public static final String APP_KEY = "app_key";
	public static final String AUTH_TOKEN = "authtoken";
	public static final String SEK = "sek";
	public static final String GENEWAYBILL = "GENEWAYBILL";
	public static final String CAN_EWAYBILL = "CANEWB";
	public static final String SUPPLY_TYPE = "supplyType";
	public static final String SUPPLY_TYPE_DESC = "supplyTypeDesc";
	public static final String SUB_SUPPLY_TYPE = "subSupplyType";
	public static final String SUB_SUPPLY_TYPE_DESC = "subSupplyDesc";
	public static final String OTH_SUB_SUPPLY_TYPE_DESC = "othersSubType";
	public static final String DOC_TYPE = "docType";
	public static final String DOC_TYPE_DESC = "docTypeDesc";
	public static final String DOC_NO = "docNo";
	public static final String DOC_DATE = "docDate";
	public static final String FROM_GSTIN = "fromGstin";
	public static final String FROM_TRNAME = "fromTrdName";
	public static final String FROM_ADD = "fromAddr1";
	public static final String FROM_ADD2 = "fromAddr2";
	public static final String FROM_PLACE = "fromPlace";
	public static final String FROM_PINCODE = "fromPincode";
	public static final String FROM_STATECODE = "fromStateCode";
	public static final String ACTFROM_STATECODE = "actFromStateCode";
	public static final String TO_GSTIN = "toGstin";
	public static final String TO_TRNAME = "toTrdName";
	public static final String TO_ADD = "toAddr1";
	public static final String TO_ADD2 = "toAddr2";
	public static final String TO_PLACE = "toPlace";
	public static final String TO_PINCODE = "toPincode";
	public static final String ACTTO_STATECODE = "actToStateCode";
	public static final String TO_STATECODE = "toStateCode";
	public static final String TOTAL_VALUE = "totalValue";
	public static final String TOTAL_INVVALUE = "totInvValue";
	public static final String CGST_VALUE = "cgstValue";
	public static final String SGST_VALUE = "sgstValue";
	public static final String IGST_VALUE = "igstValue";
	public static final String CESS_VALUE = "cessValue";
	public static final String TRANSPORTER_ID = "transporterId";
	public static final String TRANSPORTER_NAME = "transporterName";
	public static final String TRANS_DOC_NO = "transDocNo";
	public static final String TRANS_MODE = "transMode";
	public static final String TRANS_MODE_DESC = "transModeDesc";
	public static final String TRANSDISTANCE = "transDistance";
	public static final String TRANSDOCDATE = "transDocDate";
	public static final String VEHICLE_NO = "vehicleNo";
	public static final String VEHICLE_TYPE = "vehicleType";
	public static final String VEHICLE_TYPE_DESC = "vehicleTypeDesc";
	public static final String ITEM_LIST = "itemList";
	public static final String PRODUCT_NAME = "productName";
	public static final String PRODUCT_DESC = "productDesc";
	public static final String HSN_CODE = "hsnCode";
	public static final String QUANTITY = "quantity";
	public static final String QTY_UNIT = "qtyUnit";
	public static final String CGST_RATE = "cgstRate";
	public static final String SGST_RATE = "sgstRate";
	public static final String IGST_RATE = "igstRate";
	public static final String CESS_RATE = "cessRate";
	public static final String CESS_ADVOL = "cessAdvol";
	public static final String CESS_NONADVOL ="cessNonAdvol";
	public static final String TAXABLE_AMOUNT = "taxableAmount";
	
	public static final String CESS_ADVOL_RATE= "cessadvolRate";
	public static final String CESS_NONADVOL_RATE= "cessnonadvolRate";
	public static final String CESS_ADVOL_AMOUNT= "cessadvolAmount";
	public static final String CESS_NONADVOL_AMOUNT= "cessnonadvolAmount";
	

	public static final String ERROR_DESC = "error_desc";
	public static final String ERROR_GRP = "error_grp";
	public static final String ERROR_CODE = "error_code";
	public static final String STATUS_CD = "status_cd";
	public static final String STATUS = "status";
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";
	public static final String ERROR = "error";
	public static final String SU_CD = "1";
	public static final String ER_CD = "0";
	public static final String EWAYBILL_CONNECTION_ERROR = "EWAYBILL_CONNECTION_ERROR";
	public static final String EWAYBILL_ERROR = "EWAYBILL001";
	public static final String INVOICE_ID = "invoiceId";
	public static final String APP_CONFIG_FILE_PATH = "com/reliance/gstn/resource/messages.properties";
	public static final String EWAYBILL_NO = "ewayBillNo";
	public static final String EWB_NO = "ewbNo";
	public static final String CANCEL_RSN_CODE = "cancelRsnCode";
	public static final String CANCEL_RMK = "cancelRmrk";
	public static final String EWAYBILL_DATE = "ewayBillDate";
	public static final String EWAYBILL_VALIDUPTO = "validUpto";
	public static final String EWAYBILL_CANCEL_DATE = "cancelDate";
	public static final String CUSTOMERS = "customers";
	public static final String COMPANY_NAME = "cname";
	public static final String CUSTOMER_NAME = "spoc";
	public static final String CUSTOMER_NUM = "cnum";
	public static final String CUSTOMER_EMAILID = "email";
	public static final String EWAYBILL_USERNAME = "ewb_username";
	public static final String PROVIDER = "provider";
	public static final String REMARKS = "remarks";
	public static final String NIC_USER_ID = "nic_id";
	public static final String NIC_PWD = "nic_pwd";
	public static final String REF_ORG_UID = "ref_org_uId";
	public static final String API_VER = "api-ver";
	public static final String TXN = "txn";
	public static final String API_VER_1_02 = "1.02";
	public static final String API_VER_1_03 = "1.03";
	public static final String USER_IP = "ip-usr";
	public static final String USERID = "userId";
	public static final String APP_CODE = "APP_CODE";
	public static final String AUTH_USER_ID = "AUTH_USER_ID";
	public static final String APPCODE = "app_code";
	public static final String INVALID_REQ = "invalid request format. cannot be processed";
	public static final String EMAIL_ID = "email_id";
	public static final String MOBILE_NO = "mobile_number";
	public static final String KEY = "key";
	public static final String HSNID = "HsnId";
	public static final String EWAYBILLNO = "ewaybillno";
	public static final String IP_USER = "ip_usr";
	public static final String SECRET_KEY = "secret_key";
	public static final String CLIENT_ID = "client_id";

	public static final String GSTR3B_JIOGST_AUTO = "JIOGST_AUTO";
	public static final String GSTR3B_JIOGST_L2 = "L2";
	public static final String GSTR3B_JIOGST_SAVE = "JIOGST_SAVE";
	public static final String GSTR3B_JIOGST_RETSTATUS = "JIOGST_RETSTATUS";
	public static final String JIOGST = "JIOGST";
	public static final String GSTN = "GSTN";
	public static final String GSTR3B_CONNECTION_ERROR = "GSTR3B_CONNECTION_ERROR";
	public static final String GSTR3B_ERROR = "GSTR3B001";
	public static final String USER_AGENT="User-Agent";
	public static final String USER_AGENT_VAL = "Mozilla/5.0";
	public static final String ASP_CLIENTSECRETKEY="asp-clientsecretkey";
	public static final String ASPCLIENT_ID="aspclient-id";
	public static final String USERNAME="username";
	public static final String SOURCE_DEVICESTRING="source-devicestring";
	public static final String DEVICE_STRING="device-string";
	public static final String RET_PERIOD="ret_period";
	
	public static final String GSTR3B_GSTN_SUBMIT = "GSTNSUBMIT";
	public static final String GSTR3B_GSTN_SAVE = "GSTNSAVE";
	public static final String GSTR3B_GSTN_L2 = "GSTNL2";
	
	
	public static final String GSTR1_FIRSTTIMELOGINUSER = "firsttimeloginuser";
	public static final String GSTR1_INVALIDSESSION = "invalidsession";
	public static final String GSTR1_EXPIRED = "expired";
	public static final String GSTR1_VALIDSESSION = "validsession";
	
	public static final String CESS_NONADVOLVALUE ="cessNonAdvolValue";
	
	
	public static final String TRANSACTIONTYPE ="transactionType";
	public static final String DISPATCHFROM_GSTIN ="dispatchFromGSTIN";
	public static final String DISPATCHFROM_TRADENAME ="dispatchFromTradeName";
	public static final String SHIPTOGSTIN ="shipToGSTIN";
	public static final String SHIPTO_TRADENAME ="shipToTradeName";
	public static final String OTHERVALUE ="otherValue";
	
	public static final String APP_CONFIG_FILE_PATH_FOR_GSTR1="com/reliance/gstn/resource/application.properties";
	

}
