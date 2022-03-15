package com.reliance.gstn.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.reliance.gstn.model.AspUserDetails;
import com.reliance.gstn.model.GSTR3BModel;
import com.reliance.gstn.model.Gstr1OtpResponseEntity;
import com.reliance.gstn.model.GstrUploadDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.PayloadCnDnDetails;

public interface UploadAspService {

	String getGstUser(AspUserDetails aspUserDetails) throws Exception;

	List<AspUserDetails> getAspUserListByUid(Integer uid);

	List<InvoiceDetails> getInvoiceDetailsByGstin(String gstinId, String financialPeriod) throws Exception;

	String getOrgNameById(Integer orgUId) throws Exception;

	String uploadInvoiceForGSTR1(LoginMaster loginMaster, String gstinId, String financialPeriod) throws Exception;

	List<String> getUploadFP() throws Exception;

	String addGstrUploadDetails(Map<Object, Object> mapValues);

	String updateUploadSetting(LoginMaster loginMaster, String gstinId, String uploadType) throws Exception;

	List<PayloadCnDnDetails> getCndnDetails(String gstinId, String financialPeriod) throws Exception;

	List<PayloadCnDnDetails> getCndnDetailsByInvoiceId(Integer id) throws Exception;

	List<GstrUploadDetails> getUploadHistory(String id, String gstinId) throws Exception;

	String getResponseForDeleteInvoice(InvoiceDetails invoiceDetail, String orgName, Map<Object, Object> mapValues,
			List<PayloadCnDnDetails> cndnInvoiceList, LoginMaster loginMaster) throws Exception;

	GstrUploadDetails getLastUploadedGstinData(String gstin, String financialPeriod) throws Exception;

	String getGstr1LoResponse(LoginMaster loginMaster, String gstinId, String financialPeriod) throws Exception;

	String getGstr1L2Response(LoginMaster loginMaster, String gstin, String fp, String section, String recOffset,
			String noOfRecords) throws Exception;

	String getGstr1OTPResponse(LoginMaster loginMaster, String gstin, String fp, String statusofaction) throws Exception;

	Map<String, String> responsesavetogstn(LoginMaster loginMaster, String gstin,
			 String otp, String fp,
			String gt, String cur_gt, String sname) throws Exception;

	

	String addGstrAuthenticationDetails(String gstin, String fp,
			Map<String,String> authTokenResponse, String uId, Map<String, Object> responseMap) throws Exception;

	List<Gstr1OtpResponseEntity> getGstr1OtpResponse(String gstin)throws Exception;
	
	public String getGstr3bService(GSTR3BModel gstr3bModel, String body) throws Exception;

	Map<String, String> responsesavetogstnwithoutOtp(LoginMaster loginMaster, String gstin,
			String fp, String gt, String cur_gt, String sname, String app_key,
			String sek, String auth_token) throws Exception ;

	String getGstnL0Response(LoginMaster loginMaster, String gstin,
			String otp, String fp, String offset, String limit, String app_key, String sek, String auth_token)throws Exception;

	Map<String, String> responsesubmittogstnwithoutOtp(LoginMaster loginMaster,
			String gstin, String fp, String sname, String app_key, String sek,
			String auth_token)throws Exception;

	Map<String, String> getSubmitToGstnL0OtpResponse(LoginMaster loginMaster, String gstin,
			String otp, String fp)throws Exception;

	String getGstnL0ResponsewithOTP(LoginMaster loginMaster, String gstin,
			String otp, String fp, String offset, String limit)throws Exception;

	String responseFileToGstn(LoginMaster loginMaster, String gstin,
			String otp, String fp) throws Exception;

	String otpResponseForFileToGstn(LoginMaster loginMaster, String gstin,
			 String fp)throws Exception;

	String getValideSession(LoginMaster loginMaster, String gstin, String otp,
			String fp)throws Exception;

	String addGstrAuthenticationDetailsFilingTime(String gstin, String fp,
			String string, Map<String, String> responseMap)throws Exception;

	String getGstnSaveStatus(String gstr1ServiceGstnsaverestatus, LoginMaster loginMaster, String ref_id, String app_key, String sek,
			String auth_token, String gstin, String fp) throws Exception;
	
	void setUploadToJiogstStatus(String gstinId, String financialPeriod,
			String response, String uploadType, List<InvoiceDetails> invoiceList)throws Exception;

	List<InvoiceDetails> getInvoiceDetailsByGstinForCheck(String gstinId,
			String financialPeriod)throws Exception;

	String uploadInvoiceForGSTR1NEW(LoginMaster loginMaster, String gstinId,
			String financialPeriod) throws Exception;
	
	List<PayloadCnDnDetails> getCndnDetailsByGstinForCheck(String gstin,
			String fp)throws Exception;

	void setUploadToJiogstStatusCndn(String gstin, String fp, String response,
			String uploadType, List<PayloadCnDnDetails> cndnList) throws Exception;

	String addDataTogstnUploadDetailsDatabase(LoginMaster loginMaster,
			String gstin, String fpMonth, String fpYear, String fp,
			String txnid, String refid, String uploadType) throws Exception;


}
