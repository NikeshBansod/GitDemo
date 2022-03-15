package com.reliance.gstn.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.reliance.gstn.model.AspUserDetails;
import com.reliance.gstn.model.Gstr1OtpResponseEntity;
import com.reliance.gstn.model.GstrUploadDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.PayloadCnDnDetails;

public interface UploadAspDao {
	
	String getGstUser(AspUserDetails aspUserDetails) throws Exception;
	
	List<AspUserDetails> getAspUserListByUid(Integer uid);

	List<InvoiceDetails> getInvoiceDetailsByGstin(String gstinId,String financialPeriod) throws Exception;

	String getOrgNameById(Integer orgUId) throws Exception;

	String addGstrUploadDetails(Map<Object, Object> mapValues);

	String updateUploadSetting(LoginMaster loginMaster, String gstinId,String uploadType) throws Exception;

	List<PayloadCnDnDetails> getCndnDetails(String gstin,String fp) throws Exception;
	
	List<PayloadCnDnDetails> getCndnDetailsByInvoiceId(Integer id) throws Exception;
	
	List<GstrUploadDetails> getUploadHistory(String id, String gstinId)throws Exception;
	
	GstrUploadDetails getLastUploadedGstinData(String gstin, String financialPeriod)throws Exception;

	
	String addGstrAuthenticationDetails(String gstin, String fp,
			Map<String,String> authTokenResponse,String uId, Map<String, Object> responseMap)throws Exception;

	List<Gstr1OtpResponseEntity> getGstr1OtpResponse(String gstin)throws Exception;


	String addDataTogstnUploadDetailsDatabase(LoginMaster loginMaster,
			String gstin, String fpMonth, String fpYear, String fp, String txtId, String referenceId, String type)throws Exception;

	String setUploadToJiogstStatus(String gstinId, String financialPeriod,
			String response, String uploadType, List<InvoiceDetails> invoiceList /*List<Integer> invoiceid*/)throws Exception;
	
	String addGstrAuthenticationDetailsFilingTime(String gstin, String fp,
			String uId, Map<String, String> responseMap)throws Exception;

	List<InvoiceDetails> getInvoiceDetailsByGstinForCheck(String gstinId,
			String financialPeriod)throws Exception;
	
	List<PayloadCnDnDetails> getCndnDetailsByGstinForCheck(String gstinId, String financialPeriod )throws Exception;

	String setUploadToJiogstStatusCndn(String gstinId, String financialPeriod,
			String response, String uploadType,
			List<PayloadCnDnDetails> cndnList) throws Exception;
}
