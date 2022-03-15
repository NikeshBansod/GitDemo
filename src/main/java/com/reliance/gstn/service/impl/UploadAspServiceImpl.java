package com.reliance.gstn.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.util.PropertyReader;
import com.google.gson.Gson;
import com.reliance.gstn.admin.exception.GSTR3BApiException;
import com.reliance.gstn.dao.GSTR3BDao;
import com.reliance.gstn.dao.UploadAspDao;
import com.reliance.gstn.model.AspUserDetails;
import com.reliance.gstn.model.B2CSBean;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.GSTR3BModel;
import com.reliance.gstn.model.Gstr1OtpResponseEntity;
import com.reliance.gstn.model.GstrUploadDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.InvoiceServiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.PayloadCnDnDetails;
import com.reliance.gstn.service.GSTINDetailsService;
import com.reliance.gstn.service.UploadAspService;
import com.reliance.gstn.util.AspApiConstants;
import com.reliance.gstn.util.EmailUtilForGSTN;
import com.reliance.gstn.util.EncryptionUtil;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.TimeUtil;
import com.reliance.gstn.util.UOMUtil;
import com.reliance.gstn.util.UploadJIOGSTConstant;
import com.reliance.gstn.util.WebserviceCallUtil;

@Service
public class UploadAspServiceImpl implements UploadAspService {

	private static final Logger logger = Logger.getLogger(UploadAspServiceImpl.class);

	@Autowired
	private UploadAspDao uploadAspDao;

	@Autowired
	GSTINDetailsService gstnDetailsService;

	@Value("${${env}.gstr1.url}")
	private String gstr1Url;

	@Value("${${env}.gstr1.otp.url}")
	private String gstr1otpUrl;

	@Value("${${env}.asp.client.secret.key}")
	private String aspClientSecretKey;

	@Value("${${env}.aspclient.id}")
	private String aspClientId;

	@Value("${source.device.string}")
	private String sourceDeviceString;

	@Value("${source.device}")
	private String sourceDevice;

	@Value("${${env}.app.code}")
	private String appCode;

	@Value("${device.string}")
	private String deviceString;

	@Value("${B2CL.min.amt}")
	private String b2clMinAmt;

	@Value("${gstr.client.user}")
	private String gstrClientUserId;

	@Value("${gstr.client.pwd}")
	private String gstrClientPwd;

	@Value("${${env}.gstr3b.jiogst.url}")
	private String gstr3bJiogst;

	@Value("${${env}.gstr3b.gstn.url}")
	private String gstr3bGstn;

	@Autowired
	private GSTR3BDao gstr3bDao;

	@Value("${Accept-Language}")
	private String AcceptLanguage;

	@Value("${Content-Type}")
	private String ContentType;

	@Value("${location}")
	private String location;
	
	@Value("${smtp.to}")
	private String smtpto;
	
	@Value("${smtp.from}")
	private String smtpfrom;
	
	@Value("${smtp.subject}")
	private String smtpsubject;
	
	
	@Value("${currentThreadSleepTime}")
	private long currentThreadSleepTime;
	
	

	/*
	 * @Value("${${env}.gstr1.l0.url}") private String gstr1L0Url;
	 * 
	 * @Value("${${env}.gstr1.l2.url}") private String gstr1L2Url;
	 * 
	 * @Value("${${env}.gstr1.otp.url}") private String gstr1OTPUrl;
	 */

	@Override
	@Transactional
	public String getGstUser(AspUserDetails aspUserDetails) throws Exception {
		// TODO Auto-generated method stub
		return uploadAspDao.getGstUser(aspUserDetails);
	}

	@Override
	@Transactional
	public List<AspUserDetails> getAspUserListByUid(Integer uid) {
		// TODO Auto-generated method stub
		return uploadAspDao.getAspUserListByUid(uid);
	}

	@Override
	@Transactional
	public List<InvoiceDetails> getInvoiceDetailsByGstin(String gstinId, String financialPeriod) throws Exception {
		// TODO Auto-generated method stub
		return uploadAspDao.getInvoiceDetailsByGstin(gstinId, financialPeriod);
	}

	@Override
	@Transactional
	public String getOrgNameById(Integer orgUId) throws Exception {
		// TODO Auto-generated method stub
		return uploadAspDao.getOrgNameById(orgUId);
	}
	
	@SuppressWarnings({ "unused" })
	@Override
	@Transactional
	public String uploadInvoiceForGSTR1NEW(LoginMaster loginMaster, String gstinId, String financialPeriod)
			throws Exception {
		String response = "";
		boolean isUploaded=false;
		
		
		//fetch Invoice List
		List<InvoiceDetails> invoiceList = getInvoiceDetailsByGstin(gstinId, financialPeriod);
		
		//Fetch Cndn List
		List<PayloadCnDnDetails> cndnInvoiceList = getCndnDetails(gstinId, financialPeriod);
		String orgName = getOrgNameById(loginMaster.getOrgUId());
		Map<Object, Object> mapValues = new HashMap<Object, Object>();
		
		//for GSTIN details
		GSTINDetails gstinDetails = gstnDetailsService.getGstinDetailsFromGstinNo(gstinId,loginMaster.getPrimaryUserUId());
		
		//For header of the API
		Map<String, String> headersMap = getHeaderMap(AspApiConstants.GSTR1_SERVICE_TYPE, gstinId, financialPeriod,gstinDetails.getGstnUserId());
		logger.info("GSTR1 Header : " + headersMap);

		//Set http method Type
		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		
		
		Map<String, Object> statusResultMap=new HashMap<>();
		
		
		
		if(invoiceList.isEmpty() && cndnInvoiceList.isEmpty()){
			response=UploadJIOGSTConstant.EMPTYLIST;
		}else{
		
		
		if (!invoiceList.isEmpty()) {
					//upload invoice data
					Map<String, Object> map = getRootMapforGstr1(gstinId, financialPeriod, orgName);
					map = createSubSection(map, invoiceList, UploadJIOGSTConstant.ACTION, cndnInvoiceList);
					String body = new Gson().toJson(map);
					logger.info("GSTR1 Body : " + body);
					
					response=getUploadToJioGSTApiResponseNew( loginMaster,gstinId, headersMap, body, extraParams,cndnInvoiceList);//call method for API response
					
						if( ! response.equalsIgnoreCase(GSTNConstants.SUCCESS)){
							mapValues.put("status", "fail");
						}else{
							isUploaded = true;
						}
				}else{
					isUploaded = true;
				}
		
			if ( isUploaded && !cndnInvoiceList.isEmpty()){
		    	 isUploaded=false;
						//upload cndn data
						response = uploadCdnrNew(cndnInvoiceList, gstinId, financialPeriod, orgName, headersMap,extraParams, loginMaster,UploadJIOGSTConstant.ACTION);
								if (response.equalsIgnoreCase(AspApiConstants.GSTR1_STATUS_SERVICE_RESPONSE)) {
									 isUploaded=true;
								}else{
									mapValues.put("status", "fail");
									response = GSTNConstants.ERROR;
								}
								
		     }
								///bro
								
								if(isUploaded){
									mapValues.put("status", "success");  //if both invoice and CNDN upload successfully then status="SUCCESS" 
									response = GSTNConstants.SUCCESS;   //and update invoice status is true.
									
									
								
									List<InvoiceDetails> invoiceListforcheck =getInvoiceDetailsByGstinForCheck(gstinId, financialPeriod);
									uploadAspDao.setUploadToJiogstStatus(gstinId, financialPeriod,response,UploadJIOGSTConstant.UPLOADTYPE, invoiceListforcheck);
									List<PayloadCnDnDetails> cndnList= getCndnDetailsByGstinForCheck(gstinId, financialPeriod);
									uploadAspDao.setUploadToJiogstStatusCndn(gstinId, financialPeriod,response, UploadJIOGSTConstant.UPLOADTYPE, cndnList);
									
								}
		}
		    return response;
	}

	@SuppressWarnings({ "unchecked", "static-access", "unused" })
	private String getUploadToJioGSTApiResponseNew(LoginMaster loginMaster,String gstinId,Map<String, String> headersMap, String body,Map<String, String> extraParams, List<PayloadCnDnDetails> cndnInvoiceList) throws Exception {
		String response=UploadJIOGSTConstant.ERROR;
		 String err_cd=null;
		 String msg="";
		 Map<Object, Object> mapValues = new HashMap<Object, Object>();
		response = WebserviceCallUtil.callWebservice(gstr1Url, headersMap, body, extraParams);//First API call for ack no
		msg=response;
		logger.info("Payload Response :: " + response);
		Gson gson = new Gson();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = (Map<String, Object>) gson.fromJson(response.toString(), responseMap.getClass());
		Map<String, Object> statusResultMap=new HashMap<>();
		if(responseMap != null && responseMap.containsKey("status_cd")){
		  response =responseMap.get("status_cd").toString();
				if (response.equalsIgnoreCase("1")) {
					Thread.currentThread().sleep(currentThreadSleepTime);
					Map<String, String> statusMap = new HashMap<String, String>();
					statusMap.put("gstin", gstinId);
					statusMap.put("ackNo", (String) responseMap.get("ackNo"));
					statusMap.put("level", AspApiConstants.GSTR1_SERVICE_RESTATUS);
					body = new Gson().toJson(statusMap);
					logger.info("Status Body : " + body);
					headersMap.put("gstrclientServiceType", AspApiConstants.GSTR1_SERVICE_STATUS);
					response = WebserviceCallUtil.callWebservice(gstr1Url, headersMap, body, extraParams); //second API call for final response
					msg=response;
					
					
					logger.info("Status Response :: " + response);
 					Map<String, Object> statusResponseMap = new HashMap<String, Object>();
					gson = new Gson();
					statusResponseMap = (Map<String, Object>) gson.fromJson(response.toString(),statusResponseMap.getClass());
					if(statusResponseMap != null){
					statusResultMap = (Map<String, Object>) statusResponseMap.get("data");
					response = (String) statusResultMap.get("status");
					}
					    if(response.equalsIgnoreCase(AspApiConstants.GSTR1_STATUS_SERVICE_RESPONSE)){
					    	response=GSTNConstants.SUCCESS;
					    	mapValues.put("status", "success");
					    	mapValues.put("loginMaster", loginMaster);
							mapValues.put("gstinId", gstinId);
							mapValues.put("financialPeriod", headersMap.get("fp"));
							mapValues.put("transactionId", headersMap.get("txn"));
							mapValues.put("ackNo", statusResultMap.get("ackNo"));
							mapValues.put("status", statusResultMap.get("status"));
							if(response.equalsIgnoreCase(AspApiConstants.GSTR1_STATUS_SERVICE_RESPONSE) && cndnInvoiceList.isEmpty()){
							String dbresponse=addGstrUploadDetails(mapValues);//store in database
							}
					    }else{
					    	response=UploadJIOGSTConstant.ERROR;
					    }
				}else {
					err_cd = responseMap.get("err_cd").toString();
					if(err_cd.equalsIgnoreCase("011800")){ //if data is already submitted to GSTN for particular FP then 
						response=UploadJIOGSTConstant.ALREADYSUBMITTED;//its gives message with this error code(011800).
					}else{
					response = UploadJIOGSTConstant.ERROR;
					}
				 }
		}
		if(response.equalsIgnoreCase(UploadJIOGSTConstant.ERROR)){
			Date todayDate=new Date();
			String subject=smtpsubject+" "+todayDate+""+gstinId+""+headersMap.get("fp");
			EmailUtilForGSTN.sendEmail(smtpto, smtpfrom, subject,msg);
		}
		return response;
	}

	
	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	@Transactional
	public String uploadInvoiceForGSTR1(LoginMaster loginMaster, String gstinId, String financialPeriod)
			throws Exception {

		List<InvoiceDetails> invoiceList = getInvoiceDetailsByGstin(gstinId, financialPeriod);
		List<PayloadCnDnDetails> cndnInvoiceList = getCndnDetails(gstinId, financialPeriod);
		String orgName = getOrgNameById(loginMaster.getOrgUId());
		String response = "";
		String action = "R";
		//String timeofupload = "";
		Date UploadDate;
		String err_cd="";
		String UploadType = "UploadToJiogst";
		Map<Object, Object> mapValues = new HashMap<Object, Object>();
		GSTINDetails gstinDetails = gstnDetailsService.getGstinDetailsFromGstinNo(gstinId,
				loginMaster.getPrimaryUserUId());
		String userName = gstinDetails.getGstnUserId();
		Map<String, String> headersMap = createApiHeader(AspApiConstants.GSTR1_SERVICE_TYPE, gstinId, financialPeriod,
				userName);
		logger.info("GSTR1 Header : " + headersMap);

		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		if (!invoiceList.isEmpty() || !cndnInvoiceList.isEmpty()) {

			Map<String, Object> map = getRootMapforGstr1(gstinId, financialPeriod, orgName);

			map = createSubSection(map, invoiceList, action, cndnInvoiceList);

			String body = new Gson().toJson(map);
			logger.info("GSTR1 Body : " + body);

			response = WebserviceCallUtil.callWebservice(gstr1Url, headersMap, body, extraParams);
			logger.info("Payload Response :: " + response);
			Gson gson = new Gson();
			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap = (Map<String, Object>) gson.fromJson(response.toString(), responseMap.getClass());
			if(responseMap != null){
			  response =responseMap.get("status_cd").toString();
			  if(! response.equalsIgnoreCase("1")){
			  err_cd =responseMap.get("err_cd").toString();
			  }
			}
			if (response.equalsIgnoreCase("1")) {
				Thread.currentThread().sleep(currentThreadSleepTime);
				Map<String, String> statusMap = new HashMap<String, String>();
				statusMap.put("gstin", gstinId);
				statusMap.put("ackNo", (String) responseMap.get("ackNo"));
				statusMap.put("level", AspApiConstants.GSTR1_SERVICE_RESTATUS);
				body = new Gson().toJson(statusMap);
				logger.info("Status Body : " + body);
				headersMap.put("gstrclientServiceType", AspApiConstants.GSTR1_SERVICE_STATUS);
				response = WebserviceCallUtil.callWebservice(gstr1Url, headersMap, body, extraParams);
				logger.info("Status Response :: " + response);
				Map<String, Object> statusResponseMap = new HashMap<String, Object>();
				statusResponseMap = (Map<String, Object>) gson.fromJson(response.toString(),
						statusResponseMap.getClass());
				Map<String, Object> statusResultMap = (Map<String, Object>) statusResponseMap.get("data");
				response = (String) statusResultMap.get("status");

				mapValues.put("loginMaster", loginMaster);
				mapValues.put("gstinId", gstinId);
				mapValues.put("financialPeriod", financialPeriod);
				mapValues.put("transactionId", headersMap.get("txn"));
				mapValues.put("ackNo", statusResultMap.get("ackNo"));
				mapValues.put("status", statusResultMap.get("status"));

				if (response.equalsIgnoreCase(AspApiConstants.GSTR1_STATUS_SERVICE_RESPONSE)) {
					if (!cndnInvoiceList.isEmpty()) {
						response = uploadCdnr(cndnInvoiceList, gstinId, financialPeriod, orgName, headersMap,
								extraParams, loginMaster, action);
						if (response.equalsIgnoreCase(AspApiConstants.GSTR1_STATUS_SERVICE_RESPONSE)) {

							mapValues.put("status", "success");
						} else {
							mapValues.put("status", "fail");
						}
					}
					// mapValues.put("status", "success");
					
					
					List<InvoiceDetails> invoiceListforcheck =getInvoiceDetailsByGstinForCheck(gstinId, financialPeriod);
					uploadAspDao.setUploadToJiogstStatus(gstinId, financialPeriod, /* UploadDate */response,
							UploadType/* ,invoiceid */, invoiceListforcheck);

				} else {
					mapValues.put("status", "fail");
				}

				response = addGstrUploadDetails(mapValues);

			} else {
				if(err_cd.equalsIgnoreCase("011800")){
					response="alreadysubmitted";
				}else{
				response = "error";
				}
			}

		}
		// for removal of CDNR from upload asp
		else if (!cndnInvoiceList.isEmpty()) {
			response = uploadCdnr(cndnInvoiceList, gstinId, financialPeriod, orgName, headersMap, extraParams,
					loginMaster, action);
			if (response.equalsIgnoreCase(AspApiConstants.GSTR1_STATUS_SERVICE_RESPONSE)) {

				response = GSTNConstants.SUCCESS;
			}
		} else {
			response = "emptyList";
		}

		return response;
	}

	private String uploadCdnr(List<PayloadCnDnDetails> cndnInvoiceList, String gstinId, String financialPeriod,
			String orgName, Map<String, String> headersMap, Map<String, String> extraParams, LoginMaster loginMaster,
			String action) throws Exception {

		Map<String, Object> cdnrRootMap = new HashMap<String, Object>();
		String response = AspApiConstants.GSTR1_STATUS_SERVICE_RESPONSE;
		List<PayloadCnDnDetails> cndnInvoicesList = new ArrayList<PayloadCnDnDetails>(); // for removal of CDNR from
																							// upload asp
		Map<Object, Object> mapValues = new HashMap<Object, Object>();
		mapValues.put("loginMaster", loginMaster);
		mapValues.put("gstinId", gstinId);
		mapValues.put("financialPeriod", financialPeriod);
		mapValues.put("transactionId", headersMap.get("txn"));

		cdnrRootMap = getRootMapforGstr1(gstinId, financialPeriod, orgName);

		cdnrRootMap = createCdnrSubSection(cdnrRootMap, cndnInvoiceList, action);
		if(!cdnrRootMap.isEmpty()){
		String body = new Gson().toJson(cdnrRootMap);
		logger.info("CDNR GSTR1 Body : " + body);
		headersMap.put("gstrclientServiceType", AspApiConstants.GSTR1_SERVICE_TYPE);
		response = WebserviceCallUtil.callWebservice(gstr1Url, headersMap, body, extraParams);
		logger.info("CDNR Payload Response :: " + response);
		Gson gson = new Gson();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = (Map<String, Object>) gson.fromJson(response.toString(), responseMap.getClass());
		response = (String) responseMap.get("status_cd");
		if (response.equalsIgnoreCase("1")) {
			Thread.currentThread().sleep(currentThreadSleepTime);
			Map<String, String> statusMap = new HashMap<String, String>();
			statusMap.put("gstin", gstinId);
			statusMap.put("ackNo", (String) responseMap.get("ackNo"));
			statusMap.put("level", AspApiConstants.GSTR1_SERVICE_RESTATUS);
			body = new Gson().toJson(statusMap);
			logger.info("CDNR Status Body : " + body);
			headersMap.put("gstrclientServiceType", AspApiConstants.GSTR1_SERVICE_STATUS);
			response = WebserviceCallUtil.callWebservice(gstr1Url, headersMap, body, extraParams);
			logger.info("CDNR Status Response :: " + response);
			Map<String, Object> statusResponseMap = new HashMap<String, Object>();
			statusResponseMap = (Map<String, Object>) gson.fromJson(response.toString(), statusResponseMap.getClass());

			Map<String, Object> statusResultMap = (Map<String, Object>) statusResponseMap.get("data");
			response = (String) statusResultMap.get("status");

			mapValues.put("ackNo", statusResultMap.get("ackNo"));
			} 
		
			if (response.equalsIgnoreCase(AspApiConstants.GSTR1_STATUS_SERVICE_RESPONSE)) {
				mapValues.put("status", "success");
			} else {
				mapValues.put("status", "fail");
			}
		} else if (response.equalsIgnoreCase(AspApiConstants.GSTR1_STATUS_SERVICE_RESPONSE)) {
			mapValues.put("status", "success");
		} else { 
			response = "error";
			mapValues.put("status", "fail");
		}

		// }
		response = addGstrUploadDetails(mapValues);
		return response;
	}
	
	
	private String uploadCdnrNew(List<PayloadCnDnDetails> cndnInvoiceList, String gstinId, String financialPeriod,
			String orgName, Map<String, String> headersMap, Map<String, String> extraParams, LoginMaster loginMaster,
			String action) throws Exception {
		        
		        Map<String, Object> cdnrRootMap = new HashMap<String, Object>();
				String response = AspApiConstants.GSTR1_STATUS_SERVICE_RESPONSE;
				
				Map<Object, Object> mapValues = new HashMap<Object, Object>();
				mapValues.put("loginMaster", loginMaster);
				mapValues.put("gstinId", gstinId);
				mapValues.put("financialPeriod", financialPeriod);
				mapValues.put("transactionId", headersMap.get("txn"));

				cdnrRootMap = getRootMapforGstr1(gstinId, financialPeriod, orgName); //root map
				logger.info("CDNR  Body : " + cdnrRootMap);
				cdnrRootMap = createCdnrSubSection(cdnrRootMap, cndnInvoiceList, action);
				
				logger.info("CDNR GSTR1 Body : " + cdnrRootMap);
				
			     response=getCdnrUploadToJioGstNew(gstinId,cdnrRootMap,headersMap,extraParams,mapValues);
				return response;
	
	}
	
	
	@SuppressWarnings({ "unchecked", "static-access", "unused" })
	public String getCdnrUploadToJioGstNew(String gstinId , Map<String, Object>cdnrRootMap,Map<String, String>headersMap,Map<String, String>extraParams,Map<Object, Object>mapValues) throws Exception{
		
		String response=AspApiConstants.GSTR1_STATUS_SERVICE_RESPONSE;
		
		if(!cdnrRootMap.isEmpty()){
			String body = new Gson().toJson(cdnrRootMap);
			logger.info("CDNR GSTR1 Body : " + body);
			
			headersMap.put("gstrclientServiceType", AspApiConstants.GSTR1_SERVICE_TYPE);
			
			response = WebserviceCallUtil.callWebservice(gstr1Url, headersMap, body, extraParams);//call first API
			logger.info("CDNR Payload Response :: " + response);
			
			Gson gson = new Gson();
			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap = (Map<String, Object>) gson.fromJson(response.toString(), responseMap.getClass());
			response = (String) responseMap.get("status_cd");
			
			if (response.equalsIgnoreCase("1")) {
				
				Thread.currentThread().sleep(currentThreadSleepTime);
				
				Map<String, String> statusMap = new HashMap<String, String>();
				
				statusMap.put("gstin", gstinId);
				statusMap.put("ackNo", (String) responseMap.get("ackNo"));
				statusMap.put("level", AspApiConstants.GSTR1_SERVICE_RESTATUS);
				
				body = new Gson().toJson(statusMap);
				logger.info("CDNR Status Body : " + body);
				headersMap.put("gstrclientServiceType", AspApiConstants.GSTR1_SERVICE_STATUS);
				
				response = WebserviceCallUtil.callWebservice(gstr1Url, headersMap, body, extraParams); //call second API
				logger.info("CDNR Status Response :: " + response);
				Map<String, Object> statusResponseMap = new HashMap<String, Object>();
				statusResponseMap = (Map<String, Object>) gson.fromJson(response.toString(), statusResponseMap.getClass());

				Map<String, Object> statusResultMap = (Map<String, Object>) statusResponseMap.get("data");
				response = (String) statusResultMap.get("status");
				mapValues.put("ackNo", statusResultMap.get("ackNo"));
			} 
			if (response.equalsIgnoreCase(AspApiConstants.GSTR1_STATUS_SERVICE_RESPONSE)) {
				mapValues.put("status", "success");
			}else{
				mapValues.put("status", "fail");
				response=UploadJIOGSTConstant.ERROR;
			}
				String dbresponse = addGstrUploadDetails(mapValues);
		}
		return response;
	}

	public Map<String, String> createApiHeader(String serviceType, String gstinId, String financialPeriod,
			String userName) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		String username = gstrClientUserId;
		String pwd = gstrClientPwd;
		String randomNum = GSTNUtil.genRandomNumber(10000000, 99999999);
		username = randomNum + username;
		pwd = randomNum + pwd;
		EncryptionUtil eu = new EncryptionUtil();
		String encUsername = eu.encrypt(username);
		String encPwd = eu.encrypt(pwd);
		String invTxnString = GSTNUtil.genUniqueInvoiceTxn();

		Map<String, String> headersMap = new HashMap<String, String>();
		
		if (AspApiConstants.GSTR1_SERVICE_TYPE.equalsIgnoreCase(serviceType) 
				||AspApiConstants.GSTR1_L0_SERVICE_TYPE.equalsIgnoreCase(serviceType)
				||AspApiConstants.GSTR1_L2_SERVICE_TYPE.equalsIgnoreCase(serviceType) 
				||AspApiConstants.GSTR1_OTP_SERVICE_TYPE.equalsIgnoreCase(serviceType)
				||AspApiConstants.GSTR1_SAVETOGSTN_SERVICE_TYPE.equalsIgnoreCase(serviceType)
				||AspApiConstants.GSTR3B_JIOGST_AUTO.equalsIgnoreCase(serviceType)
				||AspApiConstants.GSTR3B_JIOGST_L2.equalsIgnoreCase(serviceType)
				||AspApiConstants.GSTR3B_JIOGST_SAVE.equalsIgnoreCase(serviceType)
				||AspApiConstants.GSTR3B_GSTN_L2.equalsIgnoreCase(serviceType)
				||AspApiConstants.GSTR3B_GSTN_SAVE.equalsIgnoreCase(serviceType)
				||AspApiConstants.GSTR1_SAVETOGSTN_WITHOTP_SERVICE_TYPE.equalsIgnoreCase(serviceType)
				||AspApiConstants.GSTR1_SABMITTOGSTN_SERVICE_TYPE.equalsIgnoreCase(serviceType)
				||AspApiConstants.GSTR1_FINAL_SABMITTOGSTN_SERVICE_TYPE.equalsIgnoreCase(serviceType)
				||AspApiConstants.GSTR1_SABMITTOGSTN_L0_OTP_SERVICE_TYPE.equalsIgnoreCase(serviceType)
				||AspApiConstants.GSTR1_L0_OTP_SERVICE_TYPE.equalsIgnoreCase(serviceType)
				||AspApiConstants.GET_SSESSION_FOR_FILING.equalsIgnoreCase(serviceType)
				||AspApiConstants.GSTR3B_JIOGST_RETSTATUS.equalsIgnoreCase(serviceType)
				||AspApiConstants.GSTR1_SERVICE_GSTNSAVERESTATUS.equalsIgnoreCase(serviceType)
				||AspApiConstants.GSTR1_SERVICE_GSTNSUBMITRESTATUS.equalsIgnoreCase(serviceType)) {

			String USER_AGENT = "Mozilla/5.0";

			headersMap.put("User-Agent", USER_AGENT);
			headersMap.put("Accept-Language", "en-US,en;q=0.5");
			headersMap.put("Content-Type", "application/json");
			headersMap.put("asp-clientsecretkey", aspClientSecretKey);
			headersMap.put("aspclient-id", aspClientId);
			headersMap.put("username", userName);
			headersMap.put("source-devicestring", sourceDeviceString);
			if (serviceType == AspApiConstants.GSTR1_SABMITTOGSTN_L0_OTP_SERVICE_TYPE
					|| serviceType == AspApiConstants.GSTR1_SABMITTOGSTN_SERVICE_TYPE
					|| serviceType == AspApiConstants.GSTR1_FINAL_SABMITTOGSTN_SERVICE_TYPE
					|| serviceType == AspApiConstants.GSTR1_L0_OTP_SERVICE_TYPE
					|| serviceType == AspApiConstants.GSTR3B_GSTN_L2
					|| serviceType == AspApiConstants.GSTR3B_GSTN_SUBMIT
					|| serviceType == AspApiConstants.GSTR3B_GSTN_SAVE
					|| serviceType == AspApiConstants.GSTR1_SERVICE_GSTNSAVERESTATUS) {
				headersMap.put("source-device", "Laptop");
			} else {
				headersMap.put("source-device", sourceDevice);
			}
			if(serviceType == AspApiConstants.GSTR1_SERVICE_GSTNSAVERESTATUS
					|| serviceType == AspApiConstants.GSTR1_SERVICE_GSTNSUBMITRESTATUS ){
				headersMap.put("device-string", "Device1");
			}else{
			headersMap.put("device-string", deviceString);
			}
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

			if (serviceType == AspApiConstants.GSTR1_SABMITTOGSTN_L0_OTP_SERVICE_TYPE
					|| serviceType == AspApiConstants.GSTR1_FINAL_SABMITTOGSTN_SERVICE_TYPE) {
				headersMap.put("asp-devicestring", "Laptop");
			}
		}

		return headersMap;
	}
	
	
	public  Map<String, String> getHeaderMap(String serviceType, String gstinId, String financialPeriod,
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
		headersMap.put("Accept-Language", AcceptLanguage);
		headersMap.put("Content-Type", ContentType);
		headersMap.put("asp-clientsecretkey", aspClientSecretKey);
		headersMap.put("aspclient-id", aspClientId);
		headersMap.put("username", userName);
		headersMap.put("source-devicestring", sourceDeviceString);
		headersMap.put("source-device", getSourceDevice(serviceType));
		headersMap.put("device-string", getDeviceString(serviceType));
		headersMap.put("gstin", gstinId);
		headersMap.put("fp", financialPeriod);
		headersMap.put("location", location);
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
	
	private String getSourceDevice(String serviceType){
		if (serviceType == AspApiConstants.GSTR1_SABMITTOGSTN_L0_OTP_SERVICE_TYPE
				|| serviceType == AspApiConstants.GSTR1_SABMITTOGSTN_SERVICE_TYPE
				|| serviceType == AspApiConstants.GSTR1_FINAL_SABMITTOGSTN_SERVICE_TYPE
				|| serviceType == AspApiConstants.GSTR1_L0_OTP_SERVICE_TYPE
				|| serviceType == AspApiConstants.GSTR3B_GSTN_L2
				|| serviceType == AspApiConstants.GSTR3B_GSTN_SUBMIT
				|| serviceType == AspApiConstants.GSTR3B_GSTN_SAVE
				|| serviceType == AspApiConstants.GSTR1_SERVICE_GSTNSAVERESTATUS) {
			sourceDevice=deviceString;
		} 
		
		return sourceDevice;
		
	}
	
	
	private  String getDeviceString(String serviceType){
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

	public Map<String, Object> getRootMapforGstr1(String gstinId, String financialPeriod, String orgName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gstin", gstinId);
		map.put("sname", orgName);
		map.put("fp", financialPeriod);
		return map;
	}


	private Map<String, Object> createCdnrSubSection(Map<String, Object> map, List<PayloadCnDnDetails> cndnInvoiceList,
			String action) throws Exception {
		List<Map<String, Object>> cdnrList = new ArrayList<Map<String, Object>>();

		Map<String, PayloadCnDnDetails> distinctCNDNDetails = new HashMap<String, PayloadCnDnDetails>();

		for (PayloadCnDnDetails cnDnDetails : cndnInvoiceList) {
			distinctCNDNDetails.put(cnDnDetails.getCndnNumber(), cnDnDetails);
		}

		cdnrList.addAll(getCdnrSection(cndnInvoiceList, action, distinctCNDNDetails));

		if (!cdnrList.isEmpty()) {
			map.put("cdnr", cdnrList);
		} else {
			map.clear();
		}
		
		return map;

	}

	@Override
	@Transactional
	public List<PayloadCnDnDetails> getCndnDetails(String gstin, String fp) throws Exception {
		return uploadAspDao.getCndnDetails(gstin, fp);
	}

	public Map<String, Object> createSubSection(Map<String, Object> map, List<InvoiceDetails> invoiceList,
			String action, List<PayloadCnDnDetails> cndnList) {

		List<Map<String, Object>> b2bList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> b2clList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> b2csList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> docIssueList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> hsnSummaryList = new ArrayList<Map<String, Object>>();

		Map<String, Map<String, Object>> hsnData = new HashMap<String, Map<String, Object>>();

		Map<String, Map<String, Object>> b2csData = new HashMap<String, Map<String, Object>>();

		for (InvoiceDetails invoiceDetails : invoiceList) {

			if (!invoiceDetails.getCustomerDetails().getCustGstId().equals("")) {
				Map<Double, Map<String, Object>> b2bObjMap = getB2bSection(invoiceDetails, action);
				Iterator<Double> itr = b2bObjMap.keySet().iterator();
				while (itr.hasNext()) {
					b2bList.add(b2bObjMap.get(itr.next()));
				}
			} else if (invoiceDetails.getInvoiceValueAfterRoundOff() > Double.valueOf(b2clMinAmt)
					&& isInvoiceIGSTCategory(invoiceDetails)) {
				Map<Double, Map<String, Object>> b2clObj = getB2clSection(invoiceDetails, action);
				Iterator<Double> itr = b2clObj.keySet().iterator();
				while (itr.hasNext()) {
					b2clList.add(b2clObj.get(itr.next()));
				}
			} 

			if (invoiceDetails.getInvoiceFor().equalsIgnoreCase("Product")) {

				hsnData = collectHsnData(invoiceDetails, hsnData, action);
			}

		}

		Map<String, B2CSBean> posMap = getB2CSInvCNDNData(invoiceList, cndnList, b2csData, action);

		Map<String, Map<String, Object>> b2csObj = getB2csSection(posMap, action);

		Iterator<String> itr = b2csObj.keySet().iterator();
		while (itr.hasNext()) {
			b2csList.add(b2csObj.get(itr.next()));
		}

		// CNDN removal from hsn Section
		for (PayloadCnDnDetails cnDnDetails : cndnList) {

			hsnData = collectCDNHsnData(cnDnDetails, hsnData, action);
		}
		for(Map.Entry<String, Map<String, Object>> hsnMapTemp:hsnData.entrySet()){
			hsnMapTemp.getValue().put("val",GSTNUtil.getConversionfromobjectToBigdecimal(hsnMapTemp.getValue().get("val")));
			hsnMapTemp.getValue().put("txval",GSTNUtil.getConversionfromobjectToBigdecimal(hsnMapTemp.getValue().get("txval")));
			hsnMapTemp.getValue().put("iamt",GSTNUtil.getConversionfromobjectToBigdecimal(hsnMapTemp.getValue().get("iamt")));
		}

		// CNDN removal from doc issue Section

		Map<String, PayloadCnDnDetails> distinctCNDNDetails = new HashMap<String, PayloadCnDnDetails>();

		for (PayloadCnDnDetails cnDnDetails : cndnList) {
			distinctCNDNDetails.put(cnDnDetails.getCndnNumber(), cnDnDetails);
		}

		Map<String, Object> docIssuedObj = getDocSection(invoiceList, distinctCNDNDetails, action);
		// Map<String,Object> docIssuedObj= getDocSection(invoiceList, action);
		docIssueList.add(docIssuedObj);

		hsnSummaryList = getHsnSection(hsnData, action);

		if (!b2bList.isEmpty()) {
			map.put("b2b", b2bList);
		}
		if (!b2clList.isEmpty()) {
			map.put("b2cl", b2clList);
		}
		if (!b2csList.isEmpty()) {
			map.put("b2cs", b2csList);
		}
		if (!hsnSummaryList.isEmpty()) {
			map.put("hsn", hsnSummaryList);
		}
		map.put("doc_issue", docIssueList);

		return map;
	}

	private boolean isInvoiceIGSTCategory(InvoiceDetails invoiceDetails) {
		boolean isIgst = false;
		List<InvoiceServiceDetails> invServicedetailsList = invoiceDetails.getServiceList();
		if (invServicedetailsList.get(0).getCategoryType()
				.equalsIgnoreCase(AspApiConstants.INV_SERVICE_IGST_CATEGORY)) {
			isIgst = true;
		}

		return isIgst;
	}

	private Map<String, Map<String, Object>> collectHsnData(InvoiceDetails invoiceDetails,
			Map<String, Map<String, Object>> hsnData, String action) {
		
		// boolean isNewService=true;
		for (InvoiceServiceDetails invServiceDetails : invoiceDetails.getServiceList()) {
			double totalValue = invServiceDetails.getAmount() + invServiceDetails.getCess()
					+ invServiceDetails.getIgstAmount() + invServiceDetails.getCgstAmount()
					+ invServiceDetails.getSgstAmount();
			double taxableValue = invServiceDetails.getAmount();
			String hsnCode = invServiceDetails.getHsnSacCode();
			if (hsnData.containsKey(hsnCode)) {
				Map<String, Object> hsnInnerData = hsnData.get(hsnCode);
				if (invServiceDetails.getCalculationBasedOn().equalsIgnoreCase("Lumpsum")
						&& invServiceDetails.getQuantity() == 0) {
					invServiceDetails.setQuantity(1);
				}
				hsnInnerData.put("qty", (double) hsnInnerData.get("qty") + invServiceDetails.getQuantity());

				hsnInnerData.put("iamt", (double) hsnInnerData.get("iamt") + invServiceDetails.getIgstAmount());
				hsnInnerData.put("camt", (double) hsnInnerData.get("camt") + invServiceDetails.getCgstAmount());
				hsnInnerData.put("samt", (double) hsnInnerData.get("samt") + invServiceDetails.getSgstAmount());
				hsnInnerData.put("csamt", (double) hsnInnerData.get("csamt") + invServiceDetails.getCess());
                hsnInnerData.put("val", (double) hsnInnerData.get("val") + totalValue);
                hsnInnerData.put("txval", (double) hsnInnerData.get("txval") + taxableValue);
				/*// if(isNewService){
				System.out.println(hsnInnerData.get("val").getClass());
				hsnInnerData.put("val",new BigDecimal( df.format(Double.parseDouble(hsnInnerData.get("val")!=null?(String)hsnInnerData.get("val"):"0")+ totalValue)));
				hsnInnerData.put("txval", new BigDecimal(df.format(Double.parseDouble(hsnInnerData.get("txval")!=null?(String)hsnInnerData.get("val"):"0")+ taxableValue)));
				
				hsnInnerData.put("val",getTaxvalInGSTR1Payload(hsnInnerData.get("val"),totalValue));
				hsnInnerData.put("txval",getTaxvalInGSTR1Payload(hsnInnerData.get("txval"),taxableValue));*/
				
				/*hsnInnerData.put("txval", (double) hsnInnerData.get("txval") + taxableValue);*/
				// isNewService=false;
				// }
				hsnData.put(hsnCode, hsnInnerData);

			} else {
				Map<String, Object> hsnInnerData = new HashMap<String, Object>();
				hsnInnerData.put("hsn_sc", invServiceDetails.getHsnSacCode());
				if (invServiceDetails.getHsnSacDescription() != null
						&& invServiceDetails.getHsnSacDescription().length() > 30) {
					hsnInnerData.put("desc", invServiceDetails.getHsnSacDescription().substring(0, 30));
				} else {
					hsnInnerData.put("desc", invServiceDetails.getHsnSacDescription());
				}
				String uom = invServiceDetails.getUnitOfMeasurement();
				if (uom.startsWith("OTHERS")) {
					uom = "OTHERS";
				}
				hsnInnerData.put("uqc", UOMUtil.getUomValue(uom));
				if (invServiceDetails.getCalculationBasedOn().equalsIgnoreCase("Lumpsum")
						&& invServiceDetails.getQuantity() == 0) {
					invServiceDetails.setQuantity(1);
				}
				hsnInnerData.put("qty", invServiceDetails.getQuantity());
				hsnInnerData.put("txval",taxableValue);
				hsnInnerData.put("iamt", invServiceDetails.getIgstAmount());
				hsnInnerData.put("camt", invServiceDetails.getCgstAmount());
				hsnInnerData.put("samt", invServiceDetails.getSgstAmount());
				hsnInnerData.put("csamt", invServiceDetails.getCess());
				hsnInnerData.put("val",totalValue);
				// isNewService=false;
				hsnInnerData.put("stid", AspApiConstants.GSTR1_B2CS_STORE_ID);
				hsnInnerData.put("action", action);
				hsnData.put(hsnCode, hsnInnerData);
			}
		}

		return hsnData;
	}

	private Map<String, Map<String, Object>> collectCDNHsnData(PayloadCnDnDetails cndnDetails,
			Map<String, Map<String, Object>> hsnData, String action) {
		DecimalFormat df = new DecimalFormat("#.##");
		// boolean isNewService=true;
		// for (InvoiceServiceDetails invServiceDetails :
		// invoiceDetails.getServiceList()) {
		if (cndnDetails.getCnDnType().equalsIgnoreCase("debitNote")) {

			double totalValue = cndnDetails.getAmount() + cndnDetails.getCess() + cndnDetails.getIgstAmount()
					+ cndnDetails.getCgstAmount() + cndnDetails.getSgstAmount();
			double taxableValue = cndnDetails.getAmount();
			String hsnCode = cndnDetails.getHsnSacCode();
			if (hsnData.containsKey(hsnCode)) {
				Map<String, Object> hsnInnerData = hsnData.get(hsnCode);
				if (cndnDetails.getCalculationBasedOn().equalsIgnoreCase("Lumpsum") && cndnDetails.getQuantity() == 0) {
					cndnDetails.setQuantity(1);
				}
				hsnInnerData.put("qty", (double) hsnInnerData.get("qty") + cndnDetails.getQuantity());

				hsnInnerData.put("iamt", (double) hsnInnerData.get("iamt") + cndnDetails.getIgstAmount());
				hsnInnerData.put("camt", (double) hsnInnerData.get("camt") + cndnDetails.getCgstAmount());
				hsnInnerData.put("samt", (double) hsnInnerData.get("samt") + cndnDetails.getSgstAmount());
				hsnInnerData.put("csamt", (double) hsnInnerData.get("csamt") + cndnDetails.getCess());
				hsnInnerData.put("val", (double) hsnInnerData.get("val") + totalValue);
				/*hsnInnerData.put("val", new BigDecimal(df.format(Double.parseDouble(hsnInnerData.get("val")!=null?(String)hsnInnerData.get("val"):"0")+ totalValue)));*/
				hsnInnerData.put("txval", (double) hsnInnerData.get("txval") + taxableValue);
				/*hsnInnerData.put("txval", new BigDecimal(df.format(Double.parseDouble(hsnInnerData.get("txval")!=null?(String)hsnInnerData.get("txval"):"0")+ taxableValue)));*/
				hsnData.put(hsnCode, hsnInnerData);

			} else {
				Map<String, Object> hsnInnerData = new HashMap<String, Object>();
				hsnInnerData.put("hsn_sc", cndnDetails.getHsnSacCode());
				if (cndnDetails.getHsnSacDescription() != null && cndnDetails.getHsnSacDescription().length() > 30) {
					hsnInnerData.put("desc", cndnDetails.getHsnSacDescription().substring(0, 30));
				} else {
					hsnInnerData.put("desc", cndnDetails.getHsnSacDescription());
				}
				String uom = cndnDetails.getUnitOfMeasurement();
				if (uom.startsWith("OTHERS")) {
					uom = "OTHERS";
				}
				hsnInnerData.put("uqc", UOMUtil.getUomValue(uom));
				if (cndnDetails.getCalculationBasedOn().equalsIgnoreCase("Lumpsum") && cndnDetails.getQuantity() == 0) {
					cndnDetails.setQuantity(1);
				}
				hsnInnerData.put("qty", cndnDetails.getQuantity());
				hsnInnerData.put("txval",taxableValue);
				hsnInnerData.put("iamt", cndnDetails.getIgstAmount());
				hsnInnerData.put("camt", cndnDetails.getCgstAmount());
				hsnInnerData.put("samt", cndnDetails.getSgstAmount());
				hsnInnerData.put("csamt", cndnDetails.getCess());
				hsnInnerData.put("val",totalValue);
				hsnInnerData.put("stid", AspApiConstants.GSTR1_B2CS_STORE_ID);
				hsnInnerData.put("action", action);
				hsnData.put(hsnCode, hsnInnerData);
			}

		} else {

			double totalValue = cndnDetails.getAmount() + cndnDetails.getCess() + cndnDetails.getIgstAmount()
					+ cndnDetails.getCgstAmount() + cndnDetails.getSgstAmount();
			double taxableValue = cndnDetails.getAmount();
			String hsnCode = cndnDetails.getHsnSacCode();
			if (hsnData.containsKey(hsnCode)) {
				Map<String, Object> hsnInnerData = hsnData.get(hsnCode);
				if (cndnDetails.getCalculationBasedOn().equalsIgnoreCase("Lumpsum") && cndnDetails.getQuantity() == 0) {
					cndnDetails.setQuantity(-1);
				}
				hsnInnerData.put("qty", (double) hsnInnerData.get("qty") - cndnDetails.getQuantity());

				hsnInnerData.put("iamt", (double) hsnInnerData.get("iamt") - cndnDetails.getIgstAmount());
				hsnInnerData.put("camt", (double) hsnInnerData.get("camt") - cndnDetails.getCgstAmount());
				hsnInnerData.put("samt", (double) hsnInnerData.get("samt") - cndnDetails.getSgstAmount());
				hsnInnerData.put("csamt", (double) hsnInnerData.get("csamt") - cndnDetails.getCess());
				hsnInnerData.put("val", (double) hsnInnerData.get("val") - totalValue);
				//hsnInnerData.put("val", new BigDecimal(df.format(Double.parseDouble(hsnInnerData.get("val")!=null?(String)hsnInnerData.get("val"):"0")+ totalValue)));
				hsnInnerData.put("txval", (double) hsnInnerData.get("txval") - taxableValue);
				//hsnInnerData.put("txval", new BigDecimal(df.format(Double.parseDouble(hsnInnerData.get("txval")!=null?(String)hsnInnerData.get("txval"):"0")+ totalValue)));
				hsnData.put(hsnCode, hsnInnerData);

			} else {
				Map<String, Object> hsnInnerData = new HashMap<String, Object>();
				hsnInnerData.put("hsn_sc", cndnDetails.getHsnSacCode());
				if (cndnDetails.getHsnSacDescription() != null && cndnDetails.getHsnSacDescription().length() > 30) {
					hsnInnerData.put("desc", cndnDetails.getHsnSacDescription().substring(0, 30));
				} else {
					hsnInnerData.put("desc", cndnDetails.getHsnSacDescription());
				}
				String uom = cndnDetails.getUnitOfMeasurement();
				if (uom.startsWith("OTHERS")) {
					uom = "OTHERS";
				}
				hsnInnerData.put("uqc", UOMUtil.getUomValue(uom));
				if (cndnDetails.getCalculationBasedOn().equalsIgnoreCase("Lumpsum") && cndnDetails.getQuantity() == 0) {
					cndnDetails.setQuantity(1);
				}
				hsnInnerData.put("qty", -cndnDetails.getQuantity());
				hsnInnerData.put("txval",-taxableValue);
				hsnInnerData.put("iamt", -cndnDetails.getIgstAmount());
				hsnInnerData.put("camt", -cndnDetails.getCgstAmount());
				hsnInnerData.put("samt", -cndnDetails.getSgstAmount());
				hsnInnerData.put("csamt", -cndnDetails.getCess());
				hsnInnerData.put("val",-totalValue);
				hsnInnerData.put("stid", AspApiConstants.GSTR1_B2CS_STORE_ID);
				hsnInnerData.put("action", action);
				hsnData.put(hsnCode, hsnInnerData);
			}
		}

		/*for(Map.Entry<String, Map<String, Object>>hsnMapTemp:hsnData.entrySet()){
			hsnMapTemp.getValue().put("val",GSTNUtil.getConversionfromobjectToBigdecimal(hsnMapTemp.getValue().get("val")));
			hsnMapTemp.getValue().put("txval",GSTNUtil.getConversionfromobjectToBigdecimal(hsnMapTemp.getValue().get("txval")));
			hsnMapTemp.getValue().put("iamt",GSTNUtil.getConversionfromobjectToBigdecimal(hsnMapTemp.getValue().get("iamt")));
		}
*/
		return hsnData;
	}

	private List<Map<String, Object>> getHsnSection(Map<String, Map<String, Object>> hsnData, String action) {

		List<Map<String, Object>> hsnSummaryList = new ArrayList<Map<String, Object>>();

		Iterator<String> outerIterator = hsnData.keySet().iterator();
		while (outerIterator.hasNext()) {
			String key = outerIterator.next();
			Map<String, Object> innerMap = hsnData.get(key);
			Iterator<String> innerIterator = innerMap.keySet().iterator();
			Map<String, Object> hsnSectionObj = new HashMap<String, Object>();
			while (innerIterator.hasNext()) {
				String innerKey = innerIterator.next();
				hsnSectionObj.put(innerKey, innerMap.get(innerKey));
			}
			Map<String, Object> custom = new HashMap<String, Object>();

			custom.put("ds", AspApiConstants.GSTR1_CUSTOM_DS);
			custom.put("bgrp", AspApiConstants.GSTR1_CUSTOM_BGRP);
			custom.put("bloc", AspApiConstants.GSTR1_CUSTOM_BLOC);
			custom.put("bid", AspApiConstants.GSTR1_CUSTOM_BID);
			custom.put("action", action);
			hsnSectionObj.put("custom", custom);
			hsnSummaryList.add(hsnSectionObj);
		}

		return hsnSummaryList;
	}

	/**
	 * @param invoiceCndnDetails
	 * @param type
	 *            This method will add cdnr section in the gstr1 paylod
	 * @return
	 */
	private List<Map<String, Object>> getCdnrSection(List<PayloadCnDnDetails> invoiceCndnDetailList, String action,
			Map<String, PayloadCnDnDetails> distinctCNDNDetails) {
		List<Map<String, Object>> cdnrList = new ArrayList<Map<String, Object>>();
		Double cndnValue = 0.0;
		Map<String, Double> cndnValMap = new HashMap<String, Double>();
		List<String> cndnList = new ArrayList<String>();
		cndnList.addAll(distinctCNDNDetails.keySet());

		for (String cndnNumber : cndnList) {
			for (PayloadCnDnDetails cndnDetails : invoiceCndnDetailList) {
				if (cndnNumber.equals(cndnDetails.getCndnNumber())) {
					cndnValue = cndnValue + cndnDetails.getValueAfterTax();
				}
			}
			cndnValMap.put(cndnNumber, cndnValue);
			cndnValue = 0.0;
		}

		for (PayloadCnDnDetails invoiceCndnDetails : invoiceCndnDetailList) {
			InvoiceDetails invoiceDetails = invoiceCndnDetails.getInvoiceDetails();

			if (!invoiceDetails.getCustomerDetails().getCustGstId().equals("")) {
				cdnrList.addAll(
						getCdnrSectionDetails(invoiceCndnDetails, AspApiConstants.GSTR1_B2B, action, cndnValMap));
			} else if (invoiceDetails.getInvoiceValueAfterRoundOff() > Double.valueOf(b2clMinAmt) && isInvoiceIGSTCategory(invoiceDetails)) {

				cdnrList.addAll(
						getCdnrSectionDetails(invoiceCndnDetails, AspApiConstants.GSTR1_B2CL, action, cndnValMap));
			}

		}
		return cdnrList;
	}

	private List<Map<String, Object>> getCdnrSectionDetails(PayloadCnDnDetails invoiceCndnDetails, String type,
			String action, Map<String, Double> cndnValMap) {
		List<Map<String, Object>> cdnrList = new ArrayList<Map<String, Object>>();
		InvoiceDetails invoiceDetails = invoiceCndnDetails.getInvoiceDetails();

		String preGst = "N";
		/*
		 * if(invoiceCndnDetails.getRegime().equalsIgnoreCase("preGst")){ preGst = "Y";
		 * } else { preGst = "N"; }
		 */

		Map<String, Object> cdnrMap = new HashMap<String, Object>();

		cdnrMap.put("p_gst", preGst);
		cdnrMap.put("ctin", invoiceDetails.getCustomerDetails().getCustGstId());
		// cdnrMap.put("rsn",CndnReasonUtil.getCndnReasonValue(invoiceCndnDetails.getReason())
		// );
		cdnrMap.put("typ", type);
		cdnrMap.put("inum", invoiceDetails.getInvoiceNumber());
		cdnrMap.put("idt", GSTNUtil.formatDate(invoiceDetails.getInvoiceDate(), "dd-MM-yyyy"));
		String noteType = "";
		if (invoiceCndnDetails.getCnDnType().equalsIgnoreCase("creditNote")) {
			noteType = "C";
		} else if (invoiceCndnDetails.getCnDnType().equalsIgnoreCase("debitNote")) {
			noteType = "D";
		}
		cdnrMap.put("ntty", noteType);
		cdnrMap.put("nt_num", invoiceCndnDetails.getCndnNumber());
		cdnrMap.put("nt_dt", GSTNUtil.formatDate(invoiceCndnDetails.getCreatedOn(), "dd-MM-yyyy"));
		cdnrMap.put("pos", String.format("%02d", invoiceDetails.getDeliveryPlace()));
		cdnrMap.put("rt", GSTNUtil.cdnrAggTaxRate(invoiceCndnDetails));
		cdnrMap.put("txval", invoiceCndnDetails.getTaxableValue());
		cdnrMap.put("irt", invoiceCndnDetails.getIgstPercentage());
		cdnrMap.put("iamt", invoiceCndnDetails.getIgstAmount());
		cdnrMap.put("crt", invoiceCndnDetails.getCgstPercentage());
		cdnrMap.put("val", cndnValMap.get(invoiceCndnDetails.getCndnNumber()));
		cdnrMap.put("camt", invoiceCndnDetails.getCgstAmount());
		cdnrMap.put("srt", invoiceCndnDetails.getSgstPercentage());
		cdnrMap.put("samt", invoiceCndnDetails.getSgstAmount());
		cdnrMap.put("csamt", invoiceCndnDetails.getCess());
		
		if(invoiceDetails.getDeleteYn().equalsIgnoreCase("N")){
		   cdnrMap.put("action", action);
		}else{
			cdnrMap.put("action", "D");
		}

		Map<String, Object> custom = new HashMap<String, Object>();
		custom.put("ds", AspApiConstants.GSTR1_CUSTOM_DS);
		custom.put("bgrp", AspApiConstants.GSTR1_CUSTOM_BGRP);
		custom.put("bloc", AspApiConstants.GSTR1_CUSTOM_BLOC);
		custom.put("bid", AspApiConstants.GSTR1_CUSTOM_BID);
		cdnrMap.put("custom", custom);

		cdnrList.add(cdnrMap);

		return cdnrList;
	}

	private Map<String, B2CSBean> getB2CSInvCNDNData(List<InvoiceDetails> invoiceList,
			List<PayloadCnDnDetails> cndnList, Map<String, Map<String, Object>> b2csData, String action) {

		List<B2CSBean> b2csBeanList = new ArrayList<B2CSBean>();
		b2csBeanList = collectB2CSPOSData(invoiceList, cndnList, action);
		Map<String, B2CSBean> posMap = new HashMap<String, B2CSBean>();

		for (B2CSBean b2csBean : b2csBeanList) {
			String key = b2csBean.getPos().toString() + "-" + b2csBean.getRt() + "-" + b2csBean.getEtin();
			if (posMap.containsKey(key)) {
				B2CSBean b2csBeanMap = posMap.get(key);
				b2csBeanMap.setTxval((Double) b2csBeanMap.getTxval() + (Double) b2csBean.getTxval());
				b2csBeanMap.setSamt((Double) b2csBeanMap.getSamt() + (Double) b2csBean.getSamt());
				b2csBeanMap.setCamt((Double) b2csBeanMap.getCamt() + (Double) b2csBean.getCamt());
				b2csBeanMap.setIamt((Double) b2csBeanMap.getIamt() + (Double) b2csBean.getIamt());
				b2csBeanMap.setCsamt((Double) b2csBeanMap.getCsamt() + (Double) b2csBean.getCsamt());
				posMap.put(key, b2csBeanMap);
			} else {
				posMap.put(key, b2csBean);
			}
		}

		return posMap;
	}

	private Map<Double, Map<String, Object>> getB2bSection(InvoiceDetails invoiceDetails, String action) {
		Map<Double, Map<String, Object>> b2bMap = new HashMap<Double, Map<String, Object>>();
		
		List<Double> list=new ArrayList<Double>();
		for (InvoiceServiceDetails invServiceDetails : invoiceDetails.getServiceList()) {
			double totalTaxRate = GSTNUtil.aggTaxRate(invServiceDetails);
			Map<String, Object> b2bObj = new HashMap<String, Object>();
			if (b2bMap.containsKey(totalTaxRate)) {
				b2bObj = b2bMap.get(totalTaxRate);
				b2bObj.put("iamt", (double) b2bObj.get("iamt") + invServiceDetails.getIgstAmount());
				b2bObj.put("camt", (double) b2bObj.get("camt") + invServiceDetails.getCgstAmount());
				b2bObj.put("samt", (double) b2bObj.get("samt") + invServiceDetails.getSgstAmount());
				b2bObj.put("csamt", (double) b2bObj.get("csamt") + invServiceDetails.getCess());
                b2bObj.put("txval", (double) b2bObj.get("txval") + invServiceDetails.getAmount());
				/*b2bObj.put("txval",getTaxvalInGSTR1Payload(b2bObj.get("txval"),invServiceDetails.getAmount()));*/
				
				/*b2bObj.put("txval", df.format((double) b2bObj.get("txval") + invServiceDetails.getAmount()));*/
				//b2bObj.put("txval",new BigDecimal(df.format(Double.parseDouble(b2bObj.get("txval")!=null? String.valueOf(b2bObj.get("txval")):"0")+(invServiceDetails.getAmount()))));
				b2bMap.put(totalTaxRate, b2bObj);
				list.add(totalTaxRate);
			} else {
				b2bMap.put(totalTaxRate, b2bObj);
				list.add(totalTaxRate);
				b2bObj.put("ctin", invoiceDetails.getCustomerDetails().getCustGstId());
				b2bObj.put("inv_typ", "R");
				b2bObj.put("etin", invoiceDetails.getEcommerceGstin());
				b2bObj.put("inum", invoiceDetails.getInvoiceNumber());
				b2bObj.put("idt", GSTNUtil.formatDate(invoiceDetails.getInvoiceDate(), "dd-MM-yyyy"));
				b2bObj.put("pos", String.format("%02d", invoiceDetails.getDeliveryPlace()));
				if ("YES".equalsIgnoreCase(invoiceDetails.getReverseCharge())) {
					b2bObj.put("rchrg", "Y");
				} else if ("NO".equalsIgnoreCase(invoiceDetails.getReverseCharge())) {
					b2bObj.put("rchrg", "N");
				}
                if(invoiceDetails.getDeleteYn().equalsIgnoreCase("N")){//check whether invoice is deleted or not .
				   b2bObj.put("action", action);
                }else{
                	 b2bObj.put("action","D");//if invoice is deleted then it should be pass with action="D".
                }
				b2bObj.put("val", invoiceDetails.getInvoiceValueAfterRoundOff());
				b2bObj.put("txval",invServiceDetails.getAmount());
				b2bObj.put("irt", invServiceDetails.getIgstPercentage());
				b2bObj.put("iamt", invServiceDetails.getIgstAmount());
				b2bObj.put("crt", invServiceDetails.getCgstPercentage());
				b2bObj.put("camt", invServiceDetails.getCgstAmount());
				b2bObj.put("srt", invServiceDetails.getSgstPercentage());
				b2bObj.put("samt", invServiceDetails.getSgstAmount());
				b2bObj.put("csamt", invServiceDetails.getCess());
				b2bObj.put("rt", GSTNUtil.aggTaxRate(invServiceDetails));
				Map<String, Object> custom = new HashMap<String, Object>();

				custom.put("ds", AspApiConstants.GSTR1_CUSTOM_DS);
				custom.put("bgrp", AspApiConstants.GSTR1_CUSTOM_BGRP);
				custom.put("bloc", AspApiConstants.GSTR1_CUSTOM_BLOC);
				custom.put("bid", AspApiConstants.GSTR1_CUSTOM_BID);
				b2bObj.put("custom", custom);
			}

		}
		
		for(Map.Entry<Double, Map<String, Object>> b2bMapTemp:b2bMap.entrySet()){
			b2bMapTemp.getValue().put("val",GSTNUtil.getConversionfromobjectToBigdecimal(b2bMapTemp.getValue().get("val")));
		    b2bMapTemp.getValue().put("txval",GSTNUtil.getConversionfromobjectToBigdecimal(b2bMapTemp.getValue().get("txval")));
		    b2bMapTemp.getValue().put("iamt",GSTNUtil.getConversionfromobjectToBigdecimal(b2bMapTemp.getValue().get("iamt")));
		}
		return b2bMap;
	}

	/*
	 * private Map<Double,Map<String,Object>> getB2csSection(InvoiceDetails
	 * invoiceDetails, String action){ Map<Double,Map<String,Object>> b2csMap=new
	 * HashMap<Double,Map<String,Object>>(); for (InvoiceServiceDetails
	 * invServiceDetails : invoiceDetails.getServiceList()) {
	 * 
	 * // Map<String,Double> noteTaxValues =
	 * GSTNUtil.getNoteTxValSum(invoiceDetails);
	 * 
	 * Map<String,Double> noteTaxValues = GSTNUtil.getNoteTxValSum(invoiceDetails,
	 * invServiceDetails);
	 * 
	 * double totalTaxRate=GSTNUtil.aggTaxRate(invServiceDetails);
	 * Map<String,Object> b2csObj = new HashMap<String,Object>();
	 * if(b2csMap.containsKey(totalTaxRate)){ b2csObj =b2csMap.get(totalTaxRate);
	 * b2csObj.put("iamt",
	 * (double)b2csObj.get("iamt")+invServiceDetails.getIgstAmount());
	 * b2csObj.put("camt",
	 * (double)b2csObj.get("camt")+invServiceDetails.getCgstAmount());
	 * b2csObj.put("samt",
	 * (double)b2csObj.get("samt")+invServiceDetails.getSgstAmount());
	 * b2csObj.put("csamt",
	 * (double)b2csObj.get("csamt")+invServiceDetails.getCess());
	 * b2csObj.put("txval",
	 * (double)b2csObj.get("txval")+(invServiceDetails.getAmount()-(noteTaxValues.
	 * get("creditNoteTxvalSum"))+(noteTaxValues.get("debitNoteTxvalSum"))));
	 * b2csMap.put(totalTaxRate, b2csObj); }else{ b2csMap.put(totalTaxRate,
	 * b2csObj); b2csObj.put("pos",String.format("%02d",
	 * invoiceDetails.getDeliveryPlace())); b2csObj.put("etin",
	 * invoiceDetails.getEcommerceGstin());
	 * b2csObj.put("txval",invServiceDetails.getAmount()-(noteTaxValues.get(
	 * "creditNoteTxvalSum"))+(noteTaxValues.get("debitNoteTxvalSum")));
	 * b2csObj.put("rt",GSTNUtil.aggTaxRate(invServiceDetails)); b2csObj.put("irt",
	 * invServiceDetails.getIgstPercentage()); b2csObj.put("iamt",
	 * invServiceDetails.getIgstAmount()-(noteTaxValues.get("crediNoteIgstSum"))+(
	 * noteTaxValues.get("debitNoteIgstSum"))); b2csObj.put("crt",
	 * invServiceDetails.getCgstPercentage()); b2csObj.put("camt",
	 * invServiceDetails.getCgstAmount()-(noteTaxValues.get("crediNoteCgstSum"))+(
	 * noteTaxValues.get("debitNoteCgstSum"))); b2csObj.put("srt",
	 * invServiceDetails.getSgstPercentage()); b2csObj.put("samt",
	 * invServiceDetails.getSgstAmount()-(noteTaxValues.get("crediNoteSgstSum"))+(
	 * noteTaxValues.get("debitNoteSgstSum"))); b2csObj.put("csamt",
	 * invServiceDetails.getCess()); b2csObj.put("stid",
	 * AspApiConstants.GSTR1_B2CS_STORE_ID); b2csObj.put("action", action);
	 * 
	 * Map<String,Object> custom = new HashMap<String,Object>();
	 * 
	 * custom.put("ds", AspApiConstants.GSTR1_CUSTOM_DS); custom.put("bgrp",
	 * AspApiConstants.GSTR1_CUSTOM_BGRP); custom.put("bloc",
	 * AspApiConstants.GSTR1_CUSTOM_BLOC); custom.put("bid",
	 * AspApiConstants.GSTR1_CUSTOM_BID); b2csObj.put("custom", custom); }
	 * 
	 * } return b2csMap; }
	 */

	private List<B2CSBean> collectB2CSPOSData(List<InvoiceDetails> invoiceDetails, List<PayloadCnDnDetails> cndnList,
			String action) {

		List<B2CSBean> b2csbeanList = new ArrayList<B2CSBean>();
		for (InvoiceDetails invDetail : invoiceDetails) {
			if (invDetail.getInvCategory().equalsIgnoreCase("B2CS")) {
				for (InvoiceServiceDetails invServiceDetails : invDetail.getServiceList()) {

					B2CSBean b2csbean = setBeanData(invServiceDetails, invDetail, action);
					b2csbeanList.add(b2csbean);

				}
			}
		}

		// CNDN removal from upload to ASP
		for (PayloadCnDnDetails cnDnDetails : cndnList) {

			if (cnDnDetails.getInvoiceDetails().getInvCategory().equalsIgnoreCase("B2CS")) {

				B2CSBean b2csbean = B2CSCNDNdata(cnDnDetails, action);
				b2csbeanList.add(b2csbean);
			}
		}

		return b2csbeanList;
	}

	private B2CSBean setBeanData(InvoiceServiceDetails invServiceDetails, InvoiceDetails invDetail, String action) {
		B2CSBean b2csbean = new B2CSBean();

				b2csbean.setPos(String.format("%02d", invDetail.getDeliveryPlace()));
				b2csbean.setEtin(invDetail.getEcommerceGstin());
				b2csbean.setAction(action);
				b2csbean.setStid(AspApiConstants.GSTR1_B2CS_STORE_ID);
		if(invDetail.getDeleteYn().equalsIgnoreCase("N")){
				b2csbean.setTxval(invServiceDetails.getAmount());
				b2csbean.setRt(GSTNUtil.aggTaxRate(invServiceDetails));
				b2csbean.setIrt(invServiceDetails.getIgstPercentage());
				b2csbean.setIamt(invServiceDetails.getIgstAmount());
				b2csbean.setCrt(invServiceDetails.getCgstPercentage());
				b2csbean.setCamt(invServiceDetails.getCgstAmount());
				b2csbean.setSrt(invServiceDetails.getSgstPercentage());
				b2csbean.setSamt(invServiceDetails.getSgstAmount());
				b2csbean.setCsamt(invServiceDetails.getCess());
		}/*else{
				b2csbean.setTxval(0.00);
				b2csbean.setRt(0.0);
				b2csbean.setIrt(0.0);
				b2csbean.setIamt(0.0);
				b2csbean.setCrt(0.0);
				b2csbean.setCamt(0.0);
				b2csbean.setSrt(0.0);
				b2csbean.setSamt(0.0);
				b2csbean.setCsamt(0.0); 
		}*/
		return b2csbean;

	}

	private B2CSBean B2CSCNDNdata(PayloadCnDnDetails cndnDetails, String action) {

		B2CSBean b2csbean = new B2CSBean();

		b2csbean.setPos(String.format("%02d", cndnDetails.getInvoiceDetails().getDeliveryPlace()));
		b2csbean.setEtin(cndnDetails.getInvoiceDetails().getEcommerceGstin());
		b2csbean.setStid(AspApiConstants.GSTR1_B2CS_STORE_ID);
		b2csbean.setAction(action);
		
		if(cndnDetails.getDeleteYn().equalsIgnoreCase("N")){
				b2csbean.setRt(GSTNUtil.aggCNDNTaxRate(cndnDetails));
				b2csbean.setIrt(cndnDetails.getIgstPercentage());
				b2csbean.setCrt(cndnDetails.getCgstPercentage());
				b2csbean.setSrt(cndnDetails.getSgstPercentage());
		
			if (cndnDetails.getCnDnType().equalsIgnoreCase("creditNote")) {
				b2csbean.setTxval(-cndnDetails.getAmount());
				b2csbean.setIamt(-cndnDetails.getIgstAmount());
				b2csbean.setCamt(-cndnDetails.getCgstAmount());
				b2csbean.setSamt(-cndnDetails.getSgstAmount());
				b2csbean.setCsamt(-cndnDetails.getCess());
			} else {
				b2csbean.setTxval(cndnDetails.getAmount());
				b2csbean.setIamt(cndnDetails.getIgstAmount());
				b2csbean.setCamt(cndnDetails.getCgstAmount());
				b2csbean.setSamt(cndnDetails.getSgstAmount());
				b2csbean.setCsamt(cndnDetails.getCess());
            }
		}/*else{
				b2csbean.setRt(0.0);
				b2csbean.setIrt(0.0);
				b2csbean.setCrt(0.0);
				b2csbean.setSrt(0.0);
				b2csbean.setTxval(0.0);
				b2csbean.setIamt(0.0);
				b2csbean.setCamt(0.0);
				b2csbean.setSamt(0.0);
				b2csbean.setCsamt(0.0);
		}*/
		return b2csbean;
	}

	private Map<String, Map<String, Object>> getB2csSection(Map<String, B2CSBean> posMap, String action) {
		Map<String, Map<String, Object>> b2csMap = new HashMap<String, Map<String, Object>>();
		DecimalFormat df = new DecimalFormat("#.##");
		for (Map.Entry<String, B2CSBean> entry : posMap.entrySet()) {
			String key = entry.getKey();
			B2CSBean b2csbean = entry.getValue();

			Map<String, Object> b2csObj = new HashMap<String, Object>();

			b2csMap.put(key, b2csObj);
			b2csObj.put("pos", b2csbean.getPos());
			b2csObj.put("etin", b2csbean.getEtin());
			b2csObj.put("txval",b2csbean.getTxval());
			b2csObj.put("rt", b2csbean.getRt());
			b2csObj.put("irt", b2csbean.getIrt());
			b2csObj.put("iamt", b2csbean.getIamt());
			b2csObj.put("crt", b2csbean.getCrt());
			b2csObj.put("camt", b2csbean.getCamt());
			b2csObj.put("srt", b2csbean.getSrt());
			b2csObj.put("samt", b2csbean.getSamt());
			b2csObj.put("csamt", b2csbean.getCsamt());
			b2csObj.put("stid", AspApiConstants.GSTR1_B2CS_STORE_ID);
			b2csObj.put("action", action);

			Map<String, Object> custom = new HashMap<String, Object>();

			custom.put("ds", AspApiConstants.GSTR1_CUSTOM_DS);
			custom.put("bgrp", AspApiConstants.GSTR1_CUSTOM_BGRP);
			custom.put("bloc", AspApiConstants.GSTR1_CUSTOM_BLOC);
			custom.put("bid", AspApiConstants.GSTR1_CUSTOM_BID);
			b2csObj.put("custom", custom);

		}for(Map.Entry<String, Map<String, Object>> b2csMapTemp:b2csMap.entrySet()){
			b2csMapTemp.getValue().put("txval",GSTNUtil.getConversionfromobjectToBigdecimal(b2csMapTemp.getValue().get("txval")));
		}


		return b2csMap;
	}

	private Map<Double, Map<String, Object>> getB2clSection(InvoiceDetails invoiceDetails, String action) {
		Map<Double, Map<String, Object>> b2clMap = new HashMap<Double, Map<String, Object>>();
		DecimalFormat df = new DecimalFormat("#.##");
		for (InvoiceServiceDetails invServiceDetails : invoiceDetails.getServiceList()) {
			double totalTaxRate = GSTNUtil.aggTaxRate(invServiceDetails);
			Map<String, Object> b2clObj = new HashMap<String, Object>();
			if (b2clMap.containsKey(totalTaxRate)) {
				b2clObj = b2clMap.get(totalTaxRate);
				b2clObj.put("iamt", (double) b2clObj.get("iamt") + invServiceDetails.getIgstAmount());
				b2clObj.put("csamt", (double) b2clObj.get("csamt") + invServiceDetails.getCess());
				b2clObj.put("txval", (double) b2clObj.get("txval") + invServiceDetails.getAmount());
				/*b2clObj.put("txval", new BigDecimal(df.format(Double.parseDouble(b2clObj.get("txval")!=null?(String)b2clObj.get("txval"):"0")+ invServiceDetails.getAmount())));*/
				//b2clObj.put("txval",getTaxvalInGSTR1Payload(b2clObj.get("txval"),invServiceDetails.getAmount()));
				
				b2clMap.put(totalTaxRate, b2clObj);
			} else {
				b2clMap.put(totalTaxRate, b2clObj);
				b2clObj.put("pos", String.format("%02d", invoiceDetails.getDeliveryPlace()));
				b2clObj.put("etin", invoiceDetails.getEcommerceGstin());
				b2clObj.put("inum", invoiceDetails.getInvoiceNumber());
				b2clObj.put("idt", GSTNUtil.formatDate(invoiceDetails.getInvoiceDate(), "dd-MM-yyyy"));
				b2clObj.put("val",invoiceDetails.getInvoiceValueAfterRoundOff());
				b2clObj.put("txval", invServiceDetails.getAmount());
				b2clObj.put("rt", GSTNUtil.aggTaxRate(invServiceDetails));
				b2clObj.put("irt", invServiceDetails.getIgstPercentage());
				b2clObj.put("iamt", invServiceDetails.getIgstAmount());
				/*
				 * b2clObj.put("crt",invServiceDetails.getCgstPercentage());
				 * b2clObj.put("camt",invServiceDetails.getCgstAmount());
				 * b2clObj.put("srt",invServiceDetails.getSgstPercentage()); b2clObj.put("samt",
				 * invServiceDetails.getSgstAmount());
				 */
				b2clObj.put("csamt", invServiceDetails.getCess());
				
				if(invoiceDetails.getDeleteYn().equalsIgnoreCase("N")){
				  b2clObj.put("action", action);
				}else{
					b2clObj.put("action", "D");
				}

				Map<String, Object> custom = new HashMap<String, Object>();

				custom.put("ds", AspApiConstants.GSTR1_CUSTOM_DS);
				custom.put("bgrp", AspApiConstants.GSTR1_CUSTOM_BGRP);
				custom.put("bloc", AspApiConstants.GSTR1_CUSTOM_BLOC);
				custom.put("bid", AspApiConstants.GSTR1_CUSTOM_BID);
				b2clObj.put("custom", custom);
			}

		}
		for(Map.Entry<Double, Map<String, Object>> b2clMapTemp:b2clMap.entrySet()){
			b2clMapTemp.getValue().put("val",GSTNUtil.getConversionfromobjectToBigdecimal(b2clMapTemp.getValue().get("val")));
			b2clMapTemp.getValue().put("txval",GSTNUtil.getConversionfromobjectToBigdecimal(b2clMapTemp.getValue().get("txval")));
			b2clMapTemp.getValue().put("iamt",GSTNUtil.getConversionfromobjectToBigdecimal(b2clMapTemp.getValue().get("iamt")));
		}

		return b2clMap;
	}

	private Map<String, Object> getDocSection(List<InvoiceDetails> invoiceDetailsList,
			Map<String, PayloadCnDnDetails> cndnList, String action) {

		Map<String, Object> docIssueObj = new HashMap<String, Object>();

		int totalDocCount = invoiceDetailsList.size() + cndnList.size();

		docIssueObj.put("doc", "Invoices for outward supply");
		docIssueObj.put("from", "1001");
		docIssueObj.put("to", "1500");
		docIssueObj.put("totnum", totalDocCount);
		docIssueObj.put("cancel", 0);
		docIssueObj.put("net_issue", totalDocCount);
		docIssueObj.put("stid", AspApiConstants.GSTR1_B2CS_STORE_ID);
		docIssueObj.put("action", action);

		Map<String, Object> custom = new HashMap<String, Object>();

		custom.put("ds", AspApiConstants.GSTR1_CUSTOM_DS);
		custom.put("bgrp", AspApiConstants.GSTR1_CUSTOM_BGRP);
		custom.put("bloc", AspApiConstants.GSTR1_CUSTOM_BLOC);
		custom.put("bid", AspApiConstants.GSTR1_CUSTOM_BID);
		docIssueObj.put("custom", custom);

		return docIssueObj;
	}

	private Map<String, Object> getDocSection(List<InvoiceDetails> invoiceDetailsList, String action) {

		Map<String, Object> docIssueObj = new HashMap<String, Object>();

		int totalDocCount = invoiceDetailsList.size();

		docIssueObj.put("doc", "Invoices for outward supply");
		docIssueObj.put("from", "1001");
		docIssueObj.put("to", "1500");
		docIssueObj.put("totnum", totalDocCount);
		docIssueObj.put("cancel", 0);
		docIssueObj.put("net_issue", totalDocCount);
		docIssueObj.put("stid", AspApiConstants.GSTR1_B2CS_STORE_ID);
		docIssueObj.put("action", action);

		Map<String, Object> custom = new HashMap<String, Object>();

		custom.put("ds", AspApiConstants.GSTR1_CUSTOM_DS);
		custom.put("bgrp", AspApiConstants.GSTR1_CUSTOM_BGRP);
		custom.put("bloc", AspApiConstants.GSTR1_CUSTOM_BLOC);
		custom.put("bid", AspApiConstants.GSTR1_CUSTOM_BID);
		docIssueObj.put("custom", custom);

		return docIssueObj;
	}

	@Override
	public List<String> getUploadFP() throws Exception {
		// String currentMonth =
		return null;
	}

	@Transactional
	public String addGstrUploadDetails(Map<Object, Object> mapValues) {
		// TODO Auto-generated method stub
		return uploadAspDao.addGstrUploadDetails(mapValues);
	}

	@Override
	@Transactional
	public String updateUploadSetting(LoginMaster loginMaster, String gstinId, String uploadType) throws Exception {
		// TODO Auto-generated method stub
		return uploadAspDao.updateUploadSetting(loginMaster, gstinId, uploadType);
	}

	@Override
	@Transactional
	public List<PayloadCnDnDetails> getCndnDetailsByInvoiceId(Integer id) throws Exception {
		return uploadAspDao.getCndnDetailsByInvoiceId(id);
	}

	@Override
	@Transactional
	public List<GstrUploadDetails> getUploadHistory(String id, String gstinId) throws Exception {
		return uploadAspDao.getUploadHistory(id, gstinId);
	}

	@Override
	@Transactional
	public GstrUploadDetails getLastUploadedGstinData(String gstinId, String financialPeriod) throws Exception {
		return uploadAspDao.getLastUploadedGstinData(gstinId, financialPeriod);
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	@Transactional
	public String getResponseForDeleteInvoice(InvoiceDetails invoiceDetail, String orgName,
			Map<Object, Object> mapValues, List<PayloadCnDnDetails> cndnInvoiceList, LoginMaster loginMaster)
			throws Exception {
		String financialPeriod = TimeUtil.getmonthAndYear(invoiceDetail.getInvoiceDate());
		List<InvoiceDetails> invoiceDetailList = new ArrayList<InvoiceDetails>();
		invoiceDetailList.add(invoiceDetail);
		String gstinId = invoiceDetail.getGstnStateIdInString();
		String response = "";
		
		if(invoiceDetail.getSubmitToGstn().equalsIgnoreCase("true")){
			response=UploadJIOGSTConstant.ERROR;
		}else{
			response=AspApiConstants.GSTR1_STATUS_SERVICE_RESPONSE;
		}
		/*GSTINDetails gstinDetails = gstnDetailsService.getGstinDetailsFromGstinNo(gstinId,
				loginMaster.getPrimaryUserUId());
		String userName = gstinDetails.getGstnUserId();
		Map<String, String> headersMap = createApiHeader(AspApiConstants.GSTR1_SERVICE_TYPE, gstinId, financialPeriod,
				userName);
		logger.info("GSTR1 Header : " + headersMap);

		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");

		Map<String, Object> map = getRootMapforGstr1(gstinId, financialPeriod, orgName);

		map = createSubSection(map, invoiceDetailList, action, cndnInvoiceList);

		String body = new Gson().toJson(map);
		logger.info("GSTR1 Body : " + body);

		response = WebserviceCallUtil.callWebservice(gstr1Url, headersMap, body, extraParams);
		logger.info("Payload Response :: " + response);
		Gson gson = new Gson();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = (Map<String, Object>) gson.fromJson(response.toString(), responseMap.getClass());
		response = (String) responseMap.get("status_cd");
		if (response.equalsIgnoreCase("1")) {
			Thread.currentThread().sleep(2000);
			Map<String, String> statusMap = new HashMap<String, String>();
			statusMap.put("gstin", gstinId);
			statusMap.put("ackNo", (String) responseMap.get("ackNo"));
			statusMap.put("level", AspApiConstants.GSTR1_SERVICE_RESTATUS);
			body = new Gson().toJson(statusMap);
			logger.info("Status Body : " + body);
			headersMap.put("gstrclientServiceType", AspApiConstants.GSTR1_SERVICE_STATUS);
			response = WebserviceCallUtil.callWebservice(gstr1Url, headersMap, body, extraParams);
			logger.info("Status Response :: " + response);
			Map<String, Object> statusResponseMap = new HashMap<String, Object>();
			statusResponseMap = (Map<String, Object>) gson.fromJson(response.toString(), statusResponseMap.getClass());
			Map<String, Object> statusResultMap = (Map<String, Object>) statusResponseMap.get("data");
			response = (String) statusResultMap.get("status");

			mapValues.put("loginMaster", loginMaster);
			mapValues.put("gstinId", gstinId);
			mapValues.put("financialPeriod", financialPeriod);
			mapValues.put("transactionId", headersMap.get("txn"));
			mapValues.put("ackNo", statusResultMap.get("ackNo"));

			if (response.equalsIgnoreCase(AspApiConstants.GSTR1_STATUS_SERVICE_RESPONSE)) {

				if (response.equalsIgnoreCase(AspApiConstants.GSTR1_STATUS_SERVICE_RESPONSE)) {

					mapValues.put("status", "success");
				}
				mapValues.put("status", "success");

			} else {
				mapValues.put("status", "fail");
			}

			response = addGstrUploadDetails(mapValues);

		} else {
			response = "error";
		}
		if (!cndnInvoiceList.isEmpty()) {
			response = uploadCdnr(cndnInvoiceList, gstinId, financialPeriod, orgName, headersMap, extraParams,
					loginMaster, action);
			if (response.equalsIgnoreCase(AspApiConstants.GSTR1_STATUS_SERVICE_RESPONSE)) {

				response = GSTNConstants.SUCCESS;
			}
		}*/
		return response;
	}

	@Override
	public String getGstr1LoResponse(LoginMaster loginMaster, String gstinId, String financialPeriod) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");
		Map<Object, Object> map = new HashMap<Object, Object>();
		GSTINDetails gstinDetails = gstnDetailsService.getGstinDetailsFromGstinNo(gstinId,
				loginMaster.getPrimaryUserUId());
		String userName = gstinDetails.getGstnUserId();
		Map<String, String> headersMap = createApiHeader(AspApiConstants.GSTR1_L0_SERVICE_TYPE, gstinId,
				financialPeriod, userName);
		logger.info("GSTR1 Header : " + headersMap);

		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");

		map.put("gstin", gstinId);
		map.put("fp", financialPeriod);
		String body = new Gson().toJson(map);
		logger.info("GSTR1 Body : " + body);

		String response = WebserviceCallUtil.callWebservice(gstr1Url, headersMap, body, extraParams);
		logger.info("Payload Response :: " + response);

		logger.info("Exit");
		return response;
	}

	@Override
	public String getGstr1L2Response(LoginMaster loginMaster, String gstin, String fp, String section, String recOffset,
			String noOfRecords) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");
		Map<Object, Object> map = new HashMap<Object, Object>();
		GSTINDetails gstinDetails = gstnDetailsService.getGstinDetailsFromGstinNo(gstin,
				loginMaster.getPrimaryUserUId());
		String userName = gstinDetails.getGstnUserId();
		Map<String, String> headersMap = createApiHeader(AspApiConstants.GSTR1_L2_SERVICE_TYPE, gstin, fp, userName);// ,section,recOffset,noOfRecords
		logger.info("GSTR1 Header : " + headersMap);

		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");

		map.put("section", section);
		map.put("gstin", gstin);
		map.put("fp", fp);
		map.put("recOffset", recOffset);
		map.put("noOfRecords", noOfRecords);

		String body = new Gson().toJson(map);
		logger.info("GSTR1 Body : " + body);

		String response = WebserviceCallUtil.callWebservice(gstr1Url, headersMap, body, extraParams);
		logger.info("Payload Response :: " + response);

		logger.info("Exit");
		return response;

	}

	@Override
	public String getGstr1OTPResponse(LoginMaster loginMaster, String gstin, String fp, String statusofaction)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");
		Map<Object, Object> map = new HashMap<Object, Object>();
		GSTINDetails gstinDetails = gstnDetailsService.getGstinDetailsFromGstinNo(gstin,
				loginMaster.getPrimaryUserUId());
		String userName = gstinDetails.getGstnUserId();
		Map<String, String> headersMap = createApiHeader(AspApiConstants.GSTR1_OTP_SERVICE_TYPE, gstin, fp, userName);
		logger.info("GSTR1 Header : " + headersMap);

		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");

		if (statusofaction == "expired" || statusofaction == "firsttimeloginuser") {
			map.put("action", "");
		} else {
			map.put("action", "reauth");
		}
		map.put("otp", "");
		String body = new Gson().toJson(map);
		logger.info("GSTR1 Body : " + body);

		String response = WebserviceCallUtil.callWebservice(gstr1otpUrl, headersMap, body, extraParams);
		logger.info("Payload Response :: " + response);

		logger.info("Exit");
		return response;

	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override

	public Map<String,String> responsesavetogstn(LoginMaster loginMaster, String gstin, String otp, String fp, String gt,
			String cur_gt, String sname) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");
		/*
		 * String gstinId="29AWRPJ5446K0Z0"; String financialPeriod="022018";
		 */
		Map<String,String> returnMap=new HashMap<>();
		String gstinId = gstin;
		String financialPeriod = fp;
		String UploadType = "SaveToGSTN";
		String status_cd = "";
		String type = "SaveToGSTN";
		Map<String, String> saveGstnResponse = new HashMap<String, String>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		String fpMonth = fp.substring(0, 2);
		String fpYear = fp.substring(2, 6);

		GSTINDetails gstinDetails = gstnDetailsService.getGstinDetailsFromGstinNo(gstin,
				loginMaster.getPrimaryUserUId());
		String userName = gstinDetails.getGstnUserId();

		Map<String, String> headersMap = createApiHeader(AspApiConstants.GSTR1_SAVETOGSTN_WITHOTP_SERVICE_TYPE, gstin,
				fp, userName);
		logger.info("GSTR1 Header : " + headersMap);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		map.put("action", "otp");
		map.put("otp", otp);
		map.put("fp", fp);
		map.put("gstin", gstin);
		map.put("gt", gstinDetails.getGrossTurnover());
		map.put("cur_gt", gstinDetails.getCurrentTurnover());
		map.put("sname", sname);
		String body = new Gson().toJson(map);
		logger.info("GSTR1 Body : " + body);

		String response = WebserviceCallUtil.callWebservice(gstr1otpUrl, headersMap, body, extraParams);
		logger.info("Payload Response :: " + response);
		Gson gson = new Gson();
		responseMap = (Map<String, Object>) gson.fromJson(response.toString(), responseMap.getClass());
		String gstnUploadDetailsDatabaseResponse = "";
		String errordesc=(String) responseMap.get("error_desc");
		if(errordesc == null){
		saveGstnResponse = (Map<String, String>) responseMap.get("saveGstnResponse");
		}else{
			returnMap.put("response", response);
		}
		if (!saveGstnResponse.isEmpty()) {
			status_cd = (String) saveGstnResponse.get("status_cd");
			if (status_cd.equalsIgnoreCase("1")) {
				returnMap.put("response", response);
				returnMap.put("txnid",  (String) headersMap.get("txn"));
				returnMap.put("ref_id", (String) saveGstnResponse.get("ref_id"));
			}else{
				returnMap.put("response", response);
			}
		}
		logger.info("Exit");
		return returnMap;
	}

	@Override
	@Transactional
	public String addGstrAuthenticationDetails(String gstin, String fp, Map<String, String> authTokenResponse,
			String uId, Map<String, Object> responseMap) throws Exception {
		// TODO Auto-generated method stub
		return uploadAspDao.addGstrAuthenticationDetails(gstin, fp, authTokenResponse, uId, responseMap);
	}

	@Override
	@Transactional
	public List<Gstr1OtpResponseEntity> getGstr1OtpResponse(String gstin) throws Exception {
		return uploadAspDao.getGstr1OtpResponse(gstin);
	}

	public String getGstr3bService(GSTR3BModel gstr3bModel, String body) throws Exception {
		logger.info("getGstr3bJiogstAutoData start");
		Map<String, String> headersMap = createApiHeader(gstr3bModel.getServiceType(), gstr3bModel.getGstin(),
				gstr3bModel.getFp(), gstr3bModel.getUsername());
		if (gstr3bModel.getServiceType().equals(AspApiConstants.GSTR3B_JIOGST_RETSTATUS))
			headersMap.put("ackNo", gstr3bModel.getAckNo());
		if (gstr3bModel.getOtp() != null)
			headersMap.put("otp", gstr3bModel.getOtp());
		logger.info("getGstr3bJiogstAutoData Header : " + headersMap);
		Map<String, String> extraParams = new HashMap<>();
		extraParams.put("methodName", "POST");
		if (body == null)
			body = new Gson().toJson(new HashMap<Object, Object>());
		logger.info("getGstr3bJiogstAutoData Body : " + body);
		String url = null;
		if (gstr3bModel.getService().equals(AspApiConstants.JIOGST))
			url = gstr3bJiogst;
		else
			url = gstr3bGstn;
		logger.info("getGstr3bJiogstAutoData url :: " + url);
		logger.info("getGstr3bJiogstAutoData body :: " + body);
		String response = null;
		try {
			response = WebserviceCallUtil.callWebservice(url, headersMap, body, extraParams);
		} catch (Exception e) {
			logger.error("getGstr3bJiogstAutoData  getGstr3bService", e);
		}

		logger.info("getGstr3bJiogstAutoData Payload Response :: " + response);
		logger.info("getGstr3bJiogstAutoData Exit");
		if (response != null && (response.contains("status_cd") || response.contains("http_status_cd"))) {
			Gson gson = new Gson();
			Map<String, Object> map = gson.fromJson(response, Map.class);
			if (gstr3bModel.getServiceType().equals(AspApiConstants.GSTR3B_JIOGST_SAVE)) {
				if (map.containsKey("ackNo")) {
					Map<String, String> responseMap = gson.fromJson(response, Map.class);
					gstr3bModel.setAckNo(responseMap.get("ackNo"));
					gstr3bModel.setTransactionId(headersMap.get("txn"));
					gstr3bModel.setStatus("PROCESSING");
				} else {
					gstr3bModel.setTransactionId(headersMap.get("txn"));
					gstr3bModel.setStatus("FAIL");
				}
				gstr3bDao.saveToJiogst(gstr3bModel);
				return response;
			} else if (gstr3bModel.getServiceType().equals(AspApiConstants.GSTR3B_JIOGST_RETSTATUS)) {
				Map dataMap = (Map) map.get("data");
				String status = (String) dataMap.get("status");
				String ackNo = (String) dataMap.get("ackNo");
				gstr3bDao.updateByAckNo(ackNo, status);
				return response;
			}

		} else {
			if (gstr3bModel.getServiceType().equals(AspApiConstants.GSTR3B_JIOGST_SAVE)) {
				gstr3bModel.setTransactionId(headersMap.get("txn"));
				gstr3bModel.setStatus("UPLOAD_FAIL");
				gstr3bDao.saveToJiogst(gstr3bModel);
			}
			throw new GSTR3BApiException(" Connection Error: Unable to connect to destination ");
		}
		return response;

	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	@Transactional
	public Map<String,String> responsesavetogstnwithoutOtp(LoginMaster loginMaster, String gstin, String fp, String gt,
			String cur_gt, String sname, String app_key, String sek, String auth_token) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");
		/*
		 * String gstinId="29AWRPJ5446K0Z0"; String financialPeriod="022018";
		 */
		Map<String,String> returnMap=new HashMap<>();
		String gstinId = gstin;
		String financialPeriod = fp;
		String UploadType = "SaveToGSTN";
		String fpMonth = fp.substring(0, 2);
		String fpYear = fp.substring(2, 6);
		String type = "SaveToGSTN";
		Map<Object, Object> map = new HashMap<Object, Object>();
		GSTINDetails gstinDetails = gstnDetailsService.getGstinDetailsFromGstinNo(gstin,
				loginMaster.getPrimaryUserUId());
		String userName = gstinDetails.getGstnUserId();
		Map<String, String> headersMap = createApiHeader(AspApiConstants.GSTR1_SAVETOGSTN_SERVICE_TYPE, gstin, fp,
				userName);
		logger.info("GSTR1 Header : " + headersMap);

		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		if (app_key != null && sek != null && auth_token != null) {
			map.put("app_key", app_key);
			map.put("sek", sek);
			map.put("auth_token", auth_token);
			map.put("action", "");
			map.put("otp", "");
			map.put("fp", fp);
			map.put("gstin", gstin);
			map.put("gt", gt);
			map.put("cur_gt", cur_gt);
			map.put("sname", sname);
		}

		String body = new Gson().toJson(map);
		logger.info("GSTR1 Body : " + body);

		String response = WebserviceCallUtil.callWebservice(gstr1otpUrl, headersMap, body, extraParams);
		Gson gson = new Gson();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = (Map<String, Object>) gson.fromJson(response.toString(), responseMap.getClass());
		String status_cd = "";
		String gstnUploadDetailsDatabaseResponse = "";
		Map<String, String> saveGstnResponse = new HashMap<String, String>();
		saveGstnResponse = (Map<String, String>) responseMap.get("saveGstnResponse");
		status_cd = (String) saveGstnResponse.get("status_cd");

		if (status_cd.equalsIgnoreCase("1")) {
			returnMap.put("response", response);
			returnMap.put("txnid",  (String) headersMap.get("txn"));
			returnMap.put("ref_id", (String) saveGstnResponse.get("ref_id"));
			
			
			/*gstnUploadDetailsDatabaseResponse = uploadAspDao.addDataTogstnUploadDetailsDatabase(loginMaster, gstin,
					fpMonth, fpYear, fp,, (String) saveGstnResponse.get("ref_id"), type);*/
			/*List<InvoiceDetails> invoiceList = getInvoiceDetailsByGstin(gstinId, financialPeriod);
			uploadAspDao.setUploadToJiogstStatus(gstinId, financialPeriod,  UploadDate response,
					UploadType ,invoiceid , invoiceList);*/
		}else{
			returnMap.put("response", response);
		}
		logger.info("Payload Response :: " + response);

		logger.info("Exit");
		return returnMap;
	}

	@Override
	public String getGstnL0Response(LoginMaster loginMaster, String gstin, String otp, String fp, String offset,
			String limit, String app_key, String sek, String auth_token) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");

		Map<Object, Object> map = new HashMap<Object, Object>();
		GSTINDetails gstinDetails = gstnDetailsService.getGstinDetailsFromGstinNo(gstin,
				loginMaster.getPrimaryUserUId());
		String userName = gstinDetails.getGstnUserId();
		Map<String, String> headersMap = createApiHeader(AspApiConstants.GSTR1_SABMITTOGSTN_SERVICE_TYPE, gstin, fp,
				userName);
		logger.info("GSTR1 Header : " + headersMap);

		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");

		map.put("fp", fp);
		map.put("gstin", gstin);
		map.put("limit", limit);
		map.put("offset", offset);
		map.put("app_key", app_key);
		map.put("sek", sek);
		map.put("auth_token", auth_token);

		String body = new Gson().toJson(map);
		logger.info("GSTR1 Body : " + body);

		String response = WebserviceCallUtil.callWebservice(gstr1otpUrl, headersMap, body, extraParams);
		logger.info("Payload Response :: " + response);

		logger.info("Exit");
		return response;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	@Transactional
	public Map<String,String> responsesubmittogstnwithoutOtp(LoginMaster loginMaster, String gstin, String fp, String sname,
			String app_key, String sek, String auth_token) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");
		/*
		 * String gstinId="29AWRPJ5446K0Z0"; String financialPeriod="022018";
		 */
		Map<String,String> returnMap=new HashMap<>();
		String err_cd="";
		String fpMonth = fp.substring(0, 2);
		String fpYear = fp.substring(2, 6);
		String type = "SubmitToGSTN";
		Map<Object, Object> map = new HashMap<Object, Object>();
		GSTINDetails gstinDetails = gstnDetailsService.getGstinDetailsFromGstinNo(gstin,
				loginMaster.getPrimaryUserUId());
		String userName = gstinDetails.getGstnUserId();
		Map<String, String> headersMap = createApiHeader(AspApiConstants.GSTR1_FINAL_SABMITTOGSTN_SERVICE_TYPE, gstin,
				fp, userName);
		logger.info("GSTR1 Header : " + headersMap);

		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");

		map.put("fp", fp);
		map.put("gstin", gstin);
		map.put("app_key", app_key);
		map.put("sek", sek);
		map.put("auth_token", auth_token);

		String body = new Gson().toJson(map);
		logger.info("GSTR1 Body : " + body);

		String response = WebserviceCallUtil.callWebservice(gstr1otpUrl, headersMap, body, extraParams);
		logger.info("Payload Response :: " + response);
		Gson gson = new Gson();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = (Map<String, Object>) gson.fromJson(response.toString(), responseMap.getClass());
		String status_cd = "";
		String gstnUploadDetailsDatabaseResponse = "";
		Map<String, String> saveGstnResponse = new HashMap<String, String>();
		saveGstnResponse = (Map<String, String>) responseMap.get("saveGstnResponse");
		 err_cd = (String) saveGstnResponse.get("err_cd");
		if (err_cd == null) {
			status_cd = (String) saveGstnResponse.get("status_cd");
			if (status_cd.equalsIgnoreCase("1")) {
				returnMap.put("response", response);
				returnMap.put("txnid",  (String) headersMap.get("txn"));
				returnMap.put("ref_id", (String) saveGstnResponse.get("ref_id"));
				
				
			}
		}
			else{
				returnMap.put("response", response);
			}
		

		logger.info("Exit");
		return returnMap;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	@Transactional
	public Map<String, String> getSubmitToGstnL0OtpResponse(LoginMaster loginMaster, String gstin, String otp, String fp)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");
		/*
		 * String gstinId="29AWRPJ5446K0Z0"; String financialPeriod="022018";
		 */
        Map<String,String> returnMap=new HashMap<>();
		String type = "SubmitToGSTN";
		String fpMonth = fp.substring(0, 2);
		String fpYear = fp.substring(2, 6);
		Map<Object, Object> map = new HashMap<Object, Object>();
		GSTINDetails gstinDetails = gstnDetailsService.getGstinDetailsFromGstinNo(gstin,
				loginMaster.getPrimaryUserUId());
		String userName = gstinDetails.getGstnUserId();
		Map<String, String> headersMap = createApiHeader(AspApiConstants.GSTR1_SABMITTOGSTN_L0_OTP_SERVICE_TYPE, gstin,
				fp, userName);
		logger.info("GSTR1 Header : " + headersMap);

		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		map.put("fp", fp);
		map.put("gstin", gstin);
		map.put("otp", otp);
		map.put("action", "otp");

		String body = new Gson().toJson(map);
		logger.info("GSTR1 Body : " + body);

		String response = WebserviceCallUtil.callWebservice(gstr1otpUrl, headersMap, body, extraParams);
		Gson gson = new Gson();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = (Map<String, Object>) gson.fromJson(response.toString(), responseMap.getClass());
		String status_cd = "";
		String gstnUploadDetailsDatabaseResponse = "";
		Map<String, String> saveGstnResponse = new HashMap<String, String>();
		saveGstnResponse = (Map<String, String>) responseMap.get("saveGstnResponse");
		/* status_cd = (String) saveGstnResponse.get("status_cd"); */
		String err_msg = (String) responseMap.get("error_desc");
		if (err_msg == null) {
			err_msg = (String) saveGstnResponse.get("dev_msg");
			if (err_msg == null) {
				returnMap.put("response", response);
				returnMap.put("txnid",  (String) headersMap.get("txn"));
				returnMap.put("ref_id", (String) saveGstnResponse.get("ref_id"));
			
			}else{
				returnMap.put("response", response);
			}

		}
		logger.info("Payload Response :: " + response);

		logger.info("Exit");
		return returnMap;
	}

	@Override
	public String getGstnL0ResponsewithOTP(LoginMaster loginMaster, String gstin, String otp, String fp, String offset,
			String limit) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");

		Map<Object, Object> map = new HashMap<Object, Object>();
		GSTINDetails gstinDetails = gstnDetailsService.getGstinDetailsFromGstinNo(gstin,
				loginMaster.getPrimaryUserUId());
		String userName = gstinDetails.getGstnUserId();
		/*String userName="Reliance.MH.2";*/
		Map<String, String> headersMap = createApiHeader(AspApiConstants.GSTR1_L0_OTP_SERVICE_TYPE, gstin, fp,
				userName);
		logger.info("GSTR1 Header : " + headersMap);

		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		map.put("fp", fp);
		map.put("gstin", gstin);
		map.put("otp", otp);
		map.put("action", "otp");
		map.put("offset", offset);
		map.put("limit", limit);

		String body = new Gson().toJson(map);
		logger.info("GSTR1 Body : " + body);
		String response = WebserviceCallUtil.callWebservice(gstr1otpUrl, headersMap, body, extraParams);
		logger.info("Payload Response :: " + response);

		logger.info("Exit");
		return response;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	@Transactional
	public String responseFileToGstn(LoginMaster loginMaster, String gstin, String otp, String fp) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");
		String UploadType = "fileToGSTN";
		String fpMonth = fp.substring(0, 2);
		String fpYear = fp.substring(2, 6);
		Map<Object, Object> map = new HashMap<Object, Object>();
		GSTINDetails gstinDetails = gstnDetailsService.getGstinDetailsFromGstinNo(gstin,
				loginMaster.getPrimaryUserUId());
		String userName = gstinDetails.getGstnUserId();
		Map<String, String> headersMap = createApiHeaderForFile(AspApiConstants.GSTR1_FILE_TOGSTN_TYPE, gstin, fp, otp,
				userName);
		logger.info("GSTR1 Header : " + headersMap);

		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		/*
		 * map.put("fp", fp); map.put("gstin", gstin); map.put("otp", otp);
		 * map.put("action", "otp");
		 */
		String body = new Gson().toJson(map);
		logger.info("GSTR1 Body : " + body);

		String response = WebserviceCallUtil.callWebservice(gstr1otpUrl, headersMap, body, extraParams);
		Gson gson = new Gson();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
	    String status_cd="";
		String gstnUploadDetailsDatabaseResponse="";
	    /*Map<String,String> saveGstnResponse=new HashMap<String,String>();
		saveGstnResponse=(Map<String, String>) responseMap.get("saveGstnResponse");*/
	    status_cd =responseMap.get("status_cd").toString();
	    
	    if(status_cd.equalsIgnoreCase("1")){
	    	 gstnUploadDetailsDatabaseResponse=uploadAspDao.addDataTogstnUploadDetailsDatabase(loginMaster,gstin,fpMonth,fpYear,fp,(String)headersMap.get("txn"),(String)responseMap.get("ack_num"),UploadType);
	    	/* List<InvoiceDetails> invoiceList = getInvoiceDetailsByGstinForCheck(gstin, fp);
				uploadAspDao.setUploadToJiogstStatus(gstin, fp,  UploadDate response,
						UploadType ,invoiceid  invoiceList);*/
	    }
		logger.info("Payload Response :: " + response);

		logger.info("Exit");
		return response;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	@Transactional
	public String otpResponseForFileToGstn(LoginMaster loginMaster, String gstin, String fp) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");
		String otp = "";
		String gstinId = gstin;
		String type = "fileToGSTN";
		String financialPeriod = fp;
		String UploadType = "FileToGSTN";
		String fpMonth = fp.substring(0, 2);
		String fpYear = fp.substring(2, 6);
		Map<Object, Object> map = new HashMap<Object, Object>();
		GSTINDetails gstinDetails = gstnDetailsService.getGstinDetailsFromGstinNo(gstin,
				loginMaster.getPrimaryUserUId());
		String userName = gstinDetails.getGstnUserId();
		Map<String, String> headersMap = createApiHeaderForFile(AspApiConstants.GSTR1_FILE_OTP_TOGSTN_TYPE, gstin, fp,
				otp, userName);
		logger.info("GSTR1 Header : " + headersMap);

		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		/*
		 * map.put("fp", fp); map.put("gstin", gstin); map.put("otp", otp);
		 * map.put("action", "otp");
		 */
		String body = new Gson().toJson(map);
		logger.info("GSTR1 Body : " + body);

		String response = WebserviceCallUtil.callWebservice(gstr1otpUrl, headersMap, body, extraParams);
		Gson gson = new Gson();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
	    String status_cd="";
		String gstnUploadDetailsDatabaseResponse="";
	    /*Map<String,String> saveGstnResponse=new HashMap<String,String>();
		saveGstnResponse=(Map<String, String>) responseMap.get("saveGstnResponse");*/
	    status_cd = responseMap.get("status_cd").toString();
	    
	    if(status_cd.equalsIgnoreCase("1")){
	    	 gstnUploadDetailsDatabaseResponse=uploadAspDao.addDataTogstnUploadDetailsDatabase(loginMaster,gstin,fpMonth,fpYear,fp,(String)headersMap.get("txn"),(String)responseMap.get("ack_num"),type);
	    	 List<InvoiceDetails> invoiceList = getInvoiceDetailsByGstinForCheck(gstinId, financialPeriod);
				uploadAspDao.setUploadToJiogstStatus(gstinId, financialPeriod,  /*UploadDate*/ response,
						UploadType ,/*invoiceid */ invoiceList);
	    }
		logger.info("Payload Response :: " + response);

		logger.info("Exit");
		return response;
	}

	@Transactional
	private Map<String, String> createApiHeaderForFile(String serviceType, String gstinId, String financialPeriod,
			String otp, String userName) throws Exception {
		String sek = "";
		String auth_token = "";
		String username = gstrClientUserId;
		String pwd = gstrClientPwd;
		String randomNum = GSTNUtil.genRandomNumber(10000000, 99999999);
		username = randomNum + username;
		pwd = randomNum + pwd;
		EncryptionUtil eu = new EncryptionUtil();
		String encUsername = eu.encrypt(username);
		String encPwd = eu.encrypt(pwd);
		String invTxnString = GSTNUtil.genUniqueInvoiceTxn();
		String appkey="";
		String panNo="";
		
		List<Gstr1OtpResponseEntity> responseEntityMap = new ArrayList<>();
		responseEntityMap=uploadAspDao.getGstr1OtpResponse(gstinId);
		if(responseEntityMap != null){
		    sek=responseEntityMap.get(0).getSek();
	        auth_token=responseEntityMap.get(0).getAuthtoken();
	        appkey=responseEntityMap.get(0).getAppkey();
	        panNo=gstinId.substring(2,12);
		}
		/*
		 * List<OrganizationMaster>organizationMaster=new ArrayList<>();
		 * organizationMaster=uploadAspDao.getOrganizationMaster(gstinId);
		 */

		Map<String, String> headersMap = new HashMap<String, String>();
		if (AspApiConstants.GSTR1_FILE_OTP_TOGSTN_TYPE.equalsIgnoreCase(serviceType)
				|| AspApiConstants.GSTR1_FILE_TOGSTN_TYPE.equalsIgnoreCase(serviceType)) {

			String USER_AGENT = "Mozilla/5.0";
			headersMap.put("Content-Type", "application/json");
			headersMap.put("accept", "application/json");
			headersMap.put("app-code", "Wizard");
			headersMap.put("asp-clientsecretkey", aspClientSecretKey);
			headersMap.put("asp-devicestring", "Laptop");
			headersMap.put("auth-token", auth_token);
			headersMap.put("aspclient-id", aspClientId);
			/* headersMap.put("cache-control","no-cache,no-cache"); */
			headersMap.put("device-string", "Laptop");
			headersMap.put("formtype", "GSTR1");
			headersMap.put("gstin", gstinId);
			headersMap.put("gstrclientUserName", encUsername);
			headersMap.put("gstrclientPassword", encPwd);
			headersMap.put("gstrclientServiceType", serviceType);
			headersMap.put("ip-usr", GSTNUtil.getCurrentMachineIpAddr());
			headersMap.put("location", "Maharshtra");
			headersMap.put("pan",panNo);
			headersMap.put("source-devicestring", sourceDeviceString);
			headersMap.put("source-device", "Laptop");
			headersMap.put("state-cd", gstinId.substring(0, 2));
			headersMap.put("txn", invTxnString);
			headersMap.put("username", userName);
			
		if(serviceType==AspApiConstants.GSTR1_FILE_TOGSTN_TYPE){
				headersMap.put("appkey", appkey);
				headersMap.put("ret_period", financialPeriod);
				headersMap.put("otp",otp);
				headersMap.put("sek",sek);
				headersMap.put("fp", financialPeriod);
			}
		}

		return headersMap;
	}

	@Override
	public String getValideSession(LoginMaster loginMaster, String gstin, String otp, String fp) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");

		Map<Object, Object> map = new HashMap<Object, Object>();
		GSTINDetails gstinDetails = gstnDetailsService.getGstinDetailsFromGstinNo(gstin,
				loginMaster.getPrimaryUserUId());
		String userName = gstinDetails.getGstnUserId();
		Map<String, String> headersMap = createApiHeader(AspApiConstants.GET_SSESSION_FOR_FILING, gstin, fp, userName);
		logger.info("GSTR1 Header : " + headersMap);

		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");

		map.put("otp", otp);
		map.put("action", "otp");

		String body = new Gson().toJson(map);
		logger.info("GSTR1 Body : " + body);

		String response = WebserviceCallUtil.callWebservice(gstr1otpUrl, headersMap, body, extraParams);
		logger.info("Payload Response :: " + response);

		logger.info("Exit");
		return response;
	}

	@Override
	@Transactional
	public String addGstrAuthenticationDetailsFilingTime(String gstin, String fp, String uId,
			Map<String, String> responseMap) throws Exception {
		// TODO Auto-generated method stub
		return uploadAspDao.addGstrAuthenticationDetailsFilingTime(gstin, fp, uId, responseMap);
	}

	@Override
	public String getGstnSaveStatus(String serviceType ,LoginMaster loginMaster, String ref_id, String app_key, String sek,
			String auth_token, String gstin, String fp) throws Exception {
		logger.info("Entry");
		Map<Object, Object> map = new HashMap<Object, Object>();
		GSTINDetails gstinDetails = gstnDetailsService.getGstinDetailsFromGstinNo(gstin,
				loginMaster.getPrimaryUserUId());
		String userName = gstinDetails.getGstnUserId();
		Map<String, String> headersMap = createApiHeader(serviceType, gstin, fp, userName);
		logger.info("GSTR1 Header : " + headersMap);

		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");

		map.put("ref_id", ref_id);
		map.put("app_key", app_key);
		map.put("sek", sek);
		map.put("auth_token",auth_token);
		map.put("gstin", gstin);
		map.put("fp", fp);

		String body = new Gson().toJson(map);
		logger.info("GSTR1 Body : " + body);

		String response = WebserviceCallUtil.callWebservice(gstr1otpUrl, headersMap, body, extraParams);
		logger.info("Payload Response :: " + response);

		logger.info("Exit");
		return response;
	}
	
	@Override
	@Transactional
	public void setUploadToJiogstStatus(String gstinId, String financialPeriod,
			String response, String uploadType, List<InvoiceDetails> invoiceList)
			throws Exception {
		uploadAspDao.setUploadToJiogstStatus(gstinId, financialPeriod, response, uploadType, invoiceList);
		
	}

	@Override
	@Transactional
	public List<InvoiceDetails> getInvoiceDetailsByGstinForCheck(
			String gstinId, String financialPeriod) throws Exception {
		// TODO Auto-generated method stub
		return uploadAspDao.getInvoiceDetailsByGstinForCheck(gstinId, financialPeriod);
	}

	@Override
	@Transactional
	public String addDataTogstnUploadDetailsDatabase(LoginMaster loginMaster,
			String gstin, String fpMonth, String fpYear, String fp,
			String txnid, String refid, String uploadType) throws Exception {
		// TODO Auto-generated method stub
		return uploadAspDao.addDataTogstnUploadDetailsDatabase(loginMaster,gstin,fpMonth,fpYear,fp,txnid,refid,uploadType);
	}
	
	@Override
	@Transactional
	public List<PayloadCnDnDetails> getCndnDetailsByGstinForCheck(String gstinId,
			String financialPeriod) throws Exception {
		// TODO Auto-generated method stub
		return uploadAspDao.getCndnDetailsByGstinForCheck(gstinId, financialPeriod);
	}

	@Override
	@Transactional
	public void setUploadToJiogstStatusCndn(String gstinId, String financialPeriod,
			String response, String uploadType,
			List<PayloadCnDnDetails> cndnList) throws Exception
	{
		// TODO Auto-generated method stub
		 uploadAspDao.setUploadToJiogstStatusCndn(gstinId, financialPeriod, response, uploadType, cndnList);
	}

	public BigDecimal getTaxvalInGSTR1Payload(Object taxval,double val){
		BigDecimal value = null;
		DecimalFormat df = new DecimalFormat("#.##");
		BigDecimal a = (BigDecimal) (taxval!=null?(BigDecimal)taxval:0);
		value = new BigDecimal(df.format(a.doubleValue()+val));
		
		System.out.println("value ="+new BigDecimal(df.format(a.doubleValue()+val))); 
		
		return value;
	}

}
