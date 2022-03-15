package com.reliance.gstn.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.dao.UploadAspDao;
import com.reliance.gstn.model.AspLoginBean;
import com.reliance.gstn.model.AspUserDetails;
import com.reliance.gstn.model.EwayBillWIItem;
import com.reliance.gstn.model.Gstr1OtpResponseEntity;
import com.reliance.gstn.model.GstrUploadDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.OrganizationMaster;
import com.reliance.gstn.model.PayloadCnDnDetails;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.WebserviceCallUtil;

@SuppressWarnings("unchecked")
@Repository
public class UploadAspDaoImpl implements UploadAspDao {

	private static final Logger logger = Logger.getLogger(GSTINDetailsDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${asp_user_details_by_userid}")
	private String aspUserDetailsByUserId;
	
	
	@Value("${${env}.gstr1.login.url}")
	private String gstr1LoginUrl;
	
	@Value("${invoice_details_by_gstin}")
	private String invoiceDetailsByGstin;
	
	@Value("${org_name_by_id}")
	private String orgNameById;
	
	@Value("${get_gstr_details}")
	private String getGstrDetails;
	
	@Value("${update_upload_setting}")
	private String uploadSettingQuery;
	
	@Value("${get_cndn_invoice_details}")
	private String cndnInvoiceQuery;
	
	@Value("${get_cndn_invoice_details_by_invoice}")
	private String cndnInvoiceByInvoice;
	
	@Value("${get_upload_history_by_gstin_user}")
	private String getUpalodHistoryDetails;
	
	@Value("${get_upload_type_by_gstin_no}")
	private String getUploadTypeByGstinNo;
	
	@Value("${get_last_uploaded_data_by_gstin}")
	private String getLastUploadedGstin;
	

	@Value("${gstr1_otp_response_query}")
	private String gstr1otpresponsequery;
	
	@Value("${get_user_details_for_otp}")
	private String getuserdetails;
	
	@Value("${set_user_details_for_session}")
	private String setuserdetails;
	
	@Value("${invoice_details_by_gstin_for_upload}")
	private String invoiceDetailsByGstinforupload;
	
	
	@Value("${cndn_details_by_gstin_for_upload}")
	private String CndnDetailsListByGstinforupload;
	
	
	@Value("${set_invoice_status}")
	private String setinvoicestatus;
	@Override
	public String getGstUser(AspUserDetails aspUserDetails) throws Exception{
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		Session session = sessionFactory.getCurrentSession();
		try {
			boolean res = aspUserLoginValidate(aspUserDetails);
			if(res == true){
				
				if(aspUserDetails.isUserExist()){
					aspUserDetails.setCreatedBy(aspUserDetails.getCreatedBy());
					aspUserDetails.setCreatedOn(aspUserDetails.getCreatedOn());
					aspUserDetails.setUpdatedBy(aspUserDetails.getReferenceId().toString());
					aspUserDetails.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
					session.update(aspUserDetails);
				} else {
					aspUserDetails.setCreatedBy(aspUserDetails.getReferenceId().toString());
					aspUserDetails.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
					session.saveOrUpdate(aspUserDetails);	
				}
			 
			response = GSTNConstants.SUCCESS;
			} else {
				if(aspUserDetails.isUserExist()){
					response = GSTNConstants.LOGIN_USER;
				}
			}
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return response;
	}
	
	@Override
	public List<AspUserDetails> getAspUserListByUid(Integer uid) {
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(aspUserDetailsByUserId);
		query.setInteger("referenceId", uid);
		
		List<AspUserDetails> aspUserDetails =(List<AspUserDetails>)query.list();
				
		logger.info("Exit");
		return aspUserDetails;
		
	}
	
		
	@Override
	public List<InvoiceDetails> getInvoiceDetailsByGstin(String gstinId,String financialPeriod)
			throws Exception {
		logger.info("Entry");
		List<InvoiceDetails> invoiceDetailsList = new ArrayList<InvoiceDetails>();
		try {
			 
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(invoiceDetailsByGstin);
			query.setString("gstinId", gstinId);
			query.setString("invoiceDate", financialPeriod);
			invoiceDetailsList = (List<InvoiceDetails>)query.list();
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
	
		logger.info("Exit");
		return invoiceDetailsList;
	}

	@Override
	public String getOrgNameById(Integer orgUId) throws Exception {
		
		logger.info("Entry");
		String orgName="";
		List<OrganizationMaster> orgDetails = new ArrayList<OrganizationMaster>();
		try {
			 
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(orgNameById);
			query.setInteger("orgUId", orgUId);
			orgDetails = (List<OrganizationMaster>)query.list();
			orgName = orgDetails.get(0).getOrgName();
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
	
		logger.info("Exit");
		
		return orgName;
	}

	
	public boolean aspUserLoginValidate(AspUserDetails aspUserDetails) {
		logger.info("Entry");
		
		boolean response= false;
		try {
		String userId=aspUserDetails.getUserId();
		String password=aspUserDetails.getPassword();
		String panNo = aspUserDetails.getPanNo();
		String userType="1";
		String requestType="MobileRequest";
		
		AspLoginBean aspLoginBean = new AspLoginBean();
		aspLoginBean.setUsername(userId);
		aspLoginBean.setPassword(password);
		aspLoginBean.setPancard(panNo);
		aspLoginBean.setUserType(userType);
		aspLoginBean.setRequestType(requestType);
		aspLoginBean.setServiceURL(gstr1LoginUrl);
		
		response= WebserviceCallUtil.validateAspLoginDetails(aspLoginBean);
		} catch (Exception e) {
			logger.error("Error in:",e);
			System.out.println(e);
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public String addGstrUploadDetails(Map<Object, Object> mapValues) {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		String uploadType = "";
				
		List<GstrUploadDetails> gstrDetails = new ArrayList<GstrUploadDetails>();
		try {
			Session session = sessionFactory.getCurrentSession();
			
			
			LoginMaster loginMaster = (LoginMaster)mapValues.get("loginMaster");
			String gstinId = (String)mapValues.get("gstinId");
			String financialPeriod = (String)mapValues.get("financialPeriod");
			String transactionId = (String)mapValues.get("transactionId");
			String status = (String)mapValues.get("status");
			String ackNo = (String)mapValues.get("ackNo");
			String fpMonth = financialPeriod.substring(0, 2);
			String fpYear = financialPeriod.substring(2, 6);
			GstrUploadDetails gstrUploadDetails = new GstrUploadDetails();
			
			Query uploadQuery = session.createQuery(getUploadTypeByGstinNo);
			uploadQuery.setString("gstinNo",gstinId);

			uploadType = (String)uploadQuery.list().get(0);
			
			Query query = session.createQuery(getGstrDetails);
			query.setString("uid", loginMaster.getuId().toString());
			query.setString("gstin", gstinId);
			query.setString("fpPeriod", financialPeriod);
			query.setString("actionTaken","UploadToJioGST");

			gstrDetails = (List<GstrUploadDetails>)query.list();
			
			if(status!=null && ackNo!=null ){
			if(!gstrDetails.isEmpty()){
				gstrUploadDetails = gstrDetails.get(0);
				gstrUploadDetails.setAckNo(ackNo);
				gstrUploadDetails.setTransactionId(transactionId);
				gstrUploadDetails.setStatus(status);
				gstrUploadDetails.setUploadDate(new java.sql.Timestamp(new Date().getTime()));
				gstrUploadDetails.setUploadType(uploadType);
				session.saveOrUpdate(gstrUploadDetails);
				response = GSTNConstants.SUCCESS;
			} else {
				gstrUploadDetails.setUserId(loginMaster.getuId().toString());
				gstrUploadDetails.setGstin(gstinId);
				gstrUploadDetails.setFpYear(fpYear);
				gstrUploadDetails.setFpPeriod(financialPeriod);
				gstrUploadDetails.setActionTaken("UploadToJioGST");
				gstrUploadDetails.setFpMonth(fpMonth);
				gstrUploadDetails.setAckNo(ackNo);
				gstrUploadDetails.setTransactionId(transactionId);
				gstrUploadDetails.setStatus(status);
				gstrUploadDetails.setUploadDate(new java.sql.Timestamp(new Date().getTime()));
				gstrUploadDetails.setGstrType("GSTR1");
				gstrUploadDetails.setUploadType(uploadType);
				session.save(gstrUploadDetails);
				response = GSTNConstants.SUCCESS;
			}
			}
			
		} catch(ConstraintViolationException  e){
			logger.error("Error in:",e);
			throw e;
		}catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public String updateUploadSetting(LoginMaster loginMaster, String gstinId,
			String uploadType) throws Exception {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		
		try {
			if(uploadType != null){
				
			} else {
				uploadType = "Manual";
			}
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(uploadSettingQuery);
			query.setString("uploadType",uploadType);
			query.setString("gstinNo", gstinId);
			query.executeUpdate();
			
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public List<PayloadCnDnDetails> getCndnDetails(String gstin,String fp) throws Exception {
		logger.info("Entry");
		List<PayloadCnDnDetails> cndnInvoiceDetails = new ArrayList<PayloadCnDnDetails>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(cndnInvoiceQuery);
			query.setString("createdOn",fp);
			query.setString("gstin",gstin);
			cndnInvoiceDetails = query.list();
			
		} catch (Exception e) {
			logger.info("Exception in :");
			throw e;
		}
		logger.info("Exit");
		return cndnInvoiceDetails;
	}
	
	@Override
	public List<PayloadCnDnDetails> getCndnDetailsByInvoiceId(Integer id) throws Exception {
		logger.info("Entry");
		List<PayloadCnDnDetails> cndnInvoiceDetails = new ArrayList<PayloadCnDnDetails>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(cndnInvoiceByInvoice);
			query.setInteger("id",id);
			cndnInvoiceDetails = query.list();
			
		} catch (Exception e) {
			logger.info("Exception in :");
			throw e;
		}
		logger.info("Exit");
		return cndnInvoiceDetails;
	}
	
	@Override
	public List<GstrUploadDetails> getUploadHistory(String id, String gstinId)throws Exception{
		List<GstrUploadDetails> gstrUploadDetails = new ArrayList<GstrUploadDetails>();

		try{
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(getUpalodHistoryDetails);
			query.setString("gstin",gstinId);
			gstrUploadDetails = (List<GstrUploadDetails>)query.list();
			
		} catch(Exception e){
			logger.info("Exception in :"+e);
		}
		return gstrUploadDetails; 
	}
	
	
	@Override
	public GstrUploadDetails getLastUploadedGstinData(String gstinId,String financialPeriod)throws Exception{
		GstrUploadDetails gstrUploadDetails = new GstrUploadDetails();
		String uploadType = "";
		try{
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(getLastUploadedGstin);
			query.setString("gstin",gstinId);
			query.setString("financialPeriod",financialPeriod);
			gstrUploadDetails = (GstrUploadDetails)query.list().get(0);
			
			Query query2 = session.createQuery(getUploadTypeByGstinNo);
			query2.setString("gstinNo",gstinId);
			uploadType = (String)query2.list().get(0);
				
			gstrUploadDetails.setUploadType(uploadType);
			
		} catch(Exception e){
			logger.info("Exception in :"+e);
		}
		return gstrUploadDetails; 
	}

	@Override
	public String addGstrAuthenticationDetails(String gstin, String fp,
			Map<String,String> authTokenResponse,String uId, Map<String, Object> responseMap) {
		String response = GSTNConstants.FAILURE;
		
		// TODO Auto-generated method stub
       try{
    	   List<Gstr1OtpResponseEntity> gstr1OtpResponseEntityList=new ArrayList<Gstr1OtpResponseEntity>();
			Gstr1OtpResponseEntity gstr1OtpResponseEntity=new Gstr1OtpResponseEntity();
			Session session = sessionFactory.getCurrentSession();
			authTokenResponse=(Map<String, String>) responseMap.get("authTokenResponse");
			Query query=session.createQuery(gstr1otpresponsequery);
			query.setString("gstin", gstin);
			gstr1OtpResponseEntityList = ( List<Gstr1OtpResponseEntity>)query.list();
			if(gstr1OtpResponseEntityList.isEmpty()){
				gstr1OtpResponseEntity.setUserid(uId);
				gstr1OtpResponseEntity.setGstin(gstin);
				gstr1OtpResponseEntity.setFp(fp);
				gstr1OtpResponseEntity.setAppkey((String) authTokenResponse.get("app_key"));
				gstr1OtpResponseEntity.setSek((String) authTokenResponse.get("sek"));
				gstr1OtpResponseEntity.setAuthtoken((String) authTokenResponse.get("auth_token"));
				gstr1OtpResponseEntity.setRef_exp((String) authTokenResponse.get("ref_exp"));
				gstr1OtpResponseEntity.setSes_exp((String) authTokenResponse.get("ses_exp"));
				gstr1OtpResponseEntity.setAction((String) authTokenResponse.get("action"));
				gstr1OtpResponseEntity.setSes_gen_time((String) authTokenResponse.get("ses_gen_time"));
				gstr1OtpResponseEntity.setTk_gen_time((String) authTokenResponse.get("tk_gen_time"));
				gstr1OtpResponseEntity.setUsername((String) authTokenResponse.get("username"));
				session.save(gstr1OtpResponseEntity);
				response=GSTNConstants.SUCCESS;
			}else{
				gstr1OtpResponseEntity=gstr1OtpResponseEntityList.get(0);
				gstr1OtpResponseEntity.setFp(fp);
				gstr1OtpResponseEntity.setAppkey((String) authTokenResponse.get("app_key"));
				gstr1OtpResponseEntity.setSek((String) authTokenResponse.get("sek"));
				gstr1OtpResponseEntity.setAuthtoken((String) authTokenResponse.get("auth_token"));
				gstr1OtpResponseEntity.setRef_exp((String) authTokenResponse.get("ref_exp"));
				gstr1OtpResponseEntity.setSes_exp((String) authTokenResponse.get("ses_exp"));
				gstr1OtpResponseEntity.setAction((String) authTokenResponse.get("action"));
				gstr1OtpResponseEntity.setSes_gen_time((String) authTokenResponse.get("ses_gen_time"));
				gstr1OtpResponseEntity.setTk_gen_time((String) authTokenResponse.get("tk_gen_time"));
				gstr1OtpResponseEntity.setUsername((String) authTokenResponse.get("username"));
				session.update(gstr1OtpResponseEntity);
				response=GSTNConstants.SUCCESS;
			}
			
		} catch(Exception e){
			logger.info("Exception in :"+e);
		}
		return response; 
	}

	@Override
	public List<Gstr1OtpResponseEntity> getGstr1OtpResponse(String gstin) {
		List<Gstr1OtpResponseEntity> gstr1OtpResponseEntity=new ArrayList<Gstr1OtpResponseEntity>();
		try{
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(gstr1otpresponsequery);
			query.setString("gstin", gstin);
			
			@SuppressWarnings("unchecked")
			List<Gstr1OtpResponseEntity> list = (List<Gstr1OtpResponseEntity>) query.list();
			gstr1OtpResponseEntity.addAll(list);
		} catch(Exception e){
			logger.info("Exception in :"+e);
		}
		return gstr1OtpResponseEntity; 
	}
	
	
@Override
	
	public String addDataTogstnUploadDetailsDatabase(LoginMaster loginMaster,
			String gstin, String fpMonth, String fpYear,String fp, String txtId, String referenceId,String type) throws Exception {
		// TODO Auto-generated method stub
		String uploadType = "";
		String response="";
		Session session = sessionFactory.getCurrentSession();
		 GstrUploadDetails gstrUploadDetails = new GstrUploadDetails();
		try{
			Query query = session.createQuery(getGstrDetails);
			query.setString("uid", loginMaster.getuId().toString());
			query.setString("gstin", gstin);
			query.setString("fpPeriod", fp);
			query.setString("actionTaken","SaveToGSTN");
			List<GstrUploadDetails> gstrDetails = new ArrayList<GstrUploadDetails>();
			gstrDetails = (List<GstrUploadDetails>)query.list();
			
			if(!gstrDetails.isEmpty()){
				gstrUploadDetails = gstrDetails.get(0);
				gstrUploadDetails.setAckNo(referenceId);
				gstrUploadDetails.setTransactionId(txtId);
				gstrUploadDetails.setStatus("Success");
				gstrUploadDetails.setUploadDate(new java.sql.Timestamp(new Date().getTime()));
				gstrUploadDetails.setUploadType(uploadType);
				session.saveOrUpdate(gstrUploadDetails);
			}else{
			/*GstrUploadDetails gstrUploadDetails=new GstrUploadDetails();*/
				gstrUploadDetails.setAckNo(referenceId);
				gstrUploadDetails.setActionTaken(type);
				gstrUploadDetails.setFpMonth(fpMonth);
				gstrUploadDetails.setFpPeriod(fp);
				gstrUploadDetails.setFpYear(fpYear);
				gstrUploadDetails.setGstin(gstin);
				gstrUploadDetails.setGstrType("GSTR1");
				gstrUploadDetails.setStatus("Success");
				gstrUploadDetails.setUserId(loginMaster.getuId().toString());
				gstrUploadDetails.setUploadType("Manual");
				gstrUploadDetails.setUploadDate(new java.sql.Timestamp(new Date().getTime()));
				gstrUploadDetails.setTransactionId(txtId);
				session.save(gstrUploadDetails);
				response=GSTNConstants.SUCCESS;
			}
		}
		catch(Exception e){
			logger.info("Exception in :"+e);
		}
		return response;
	}

@Override
public String addGstrAuthenticationDetailsFilingTime(String gstin, String fp,
		String uId, Map<String, String> responseMap) throws Exception {
	String response = GSTNConstants.FAILURE;
	String auth_token="";
	String app_key="";
	String sek="";
	String ref_exp= "";
	String ses_exp="";
	String action="";
	String ses_gen_time="";
	String tk_gen_time="";
	String username="";
	// TODO Auto-generated method stub
   try{
	   List<Gstr1OtpResponseEntity> gstr1OtpResponseEntityList=new ArrayList<Gstr1OtpResponseEntity>();
		Gstr1OtpResponseEntity gstr1OtpResponseEntity=new Gstr1OtpResponseEntity();
		Session session = sessionFactory.getCurrentSession();
		
		Query query=session.createQuery(gstr1otpresponsequery);
		query.setString("gstin", gstin);
		gstr1OtpResponseEntityList = ( List<Gstr1OtpResponseEntity>)query.list();
		
		app_key = (String) responseMap.get("app_key");
		sek = (String) responseMap.get("sek");
		auth_token = (String) responseMap.get("auth_token");
		ref_exp = (String) responseMap.get("ref_exp");
		ses_exp = (String) responseMap.get("ses_exp");
		action = (String) responseMap.get("action");
		ses_gen_time = (String) responseMap.get("ses_gen_time");
		tk_gen_time = (String) responseMap.get("tk_gen_time");
		username = (String) responseMap.get("username");
		
		if(gstr1OtpResponseEntityList.isEmpty())
		{
			gstr1OtpResponseEntity.setUserid(uId);
			gstr1OtpResponseEntity.setGstin(gstin);
			gstr1OtpResponseEntity.setFp(fp);
			gstr1OtpResponseEntity.setAppkey(app_key);
			gstr1OtpResponseEntity.setSek(sek);
			gstr1OtpResponseEntity.setAuthtoken(auth_token);
			gstr1OtpResponseEntity.setRef_exp(ref_exp);
			gstr1OtpResponseEntity.setSes_exp(ses_exp);
			gstr1OtpResponseEntity.setAction(action);
			gstr1OtpResponseEntity.setSes_gen_time(ses_gen_time);
			gstr1OtpResponseEntity.setTk_gen_time(tk_gen_time);
			gstr1OtpResponseEntity.setUsername(username);
			session.save(gstr1OtpResponseEntity);
			response=GSTNConstants.SUCCESS;
		}else{
			gstr1OtpResponseEntity=gstr1OtpResponseEntityList.get(0);
			gstr1OtpResponseEntity.setFp(fp);
			gstr1OtpResponseEntity.setAppkey(app_key);
			gstr1OtpResponseEntity.setSek(sek);
			gstr1OtpResponseEntity.setAuthtoken(auth_token);
			gstr1OtpResponseEntity.setRef_exp(ref_exp);
			gstr1OtpResponseEntity.setSes_exp(ses_exp);
			gstr1OtpResponseEntity.setAction(action);
			gstr1OtpResponseEntity.setSes_gen_time(ses_gen_time);
			gstr1OtpResponseEntity.setTk_gen_time(tk_gen_time);
			gstr1OtpResponseEntity.setUsername(username);
			session.update(gstr1OtpResponseEntity);
			response=GSTNConstants.SUCCESS;
		}
		
	} catch(Exception e){
		logger.info("Exception in :"+e);
	}
	return response; 
}

@Override
public String setUploadToJiogstStatus(String gstinId, String financialPeriod,
		String response, String uploadType,List<InvoiceDetails> invoiceList /*List<Integer> invoiceid*/) throws Exception {
	// TODO Auto-generated method stub
	Session session = sessionFactory.getCurrentSession();
	//InvoiceDetails invoicedetails=new InvoiceDetails();
	/*Transaction tx = session.beginTransaction();*/
	try{
	int count=0;
	for(int i=0;i<invoiceList.size();i++)
	{
	/*Query query = session.createQuery(setinvoicestatus);
	query.setString("gstinId", gstinId);
	query.setString("invoiceDate", financialPeriod);
	query.setInteger("id",  invoiceList.get(i).getId());*/
	
		InvoiceDetails invoicedetails =invoiceList.get(i);
		
		if(uploadType=="UploadToJiogst")
		{
		invoicedetails.setUploadToJiogst("true");
		}
		else if (uploadType=="SaveToGSTN")
		{
			invoicedetails.setSaveToGstn("true");
		}
		else if (uploadType=="SubmitToGSTN")
		{
			invoicedetails.setSubmitToGstn("true");
		}
		else if (uploadType=="fileToGstn")
		{
			invoicedetails.setFileToGstn("true");
		}
		
		session.update(invoicedetails);
		if( ++count % 100 == 0)
		{
			session.flush();
		      session.clear();
			
		}
		
		
	}
	 if(++count % 100 != 0)
	{
		session.flush();
	      session.clear();
		
	}
	
		response=GSTNConstants.SUCCESS;
	}
	
	catch(Exception e){
		logger.info("Exception in :"+e);
	}
	
	/*tx.commit();*/
	
	return response;
	
}

@Override
public List<InvoiceDetails> getInvoiceDetailsByGstinForCheck(String gstinId,
		String financialPeriod) throws Exception {
	logger.info("Entry");
	List<InvoiceDetails> invoiceDetailsList = new ArrayList<InvoiceDetails>();
	try {
		 
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(invoiceDetailsByGstinforupload);
		query.setString("gstinId", gstinId);
		query.setString("invoiceDate", financialPeriod);
		invoiceDetailsList = (List<InvoiceDetails>)query.list();
	} catch (Exception e) {
		logger.error("Error in:",e);
		throw e;
	}

	logger.info("Exit");
	return invoiceDetailsList;
}

@Override
public List<PayloadCnDnDetails> getCndnDetailsByGstinForCheck(String gstinId,
		String financialPeriod) throws Exception {
	// TODO Auto-generated method stub
	logger.info("Entry");
	List<PayloadCnDnDetails> CndnDetailsList = new ArrayList<PayloadCnDnDetails>();
	try
	{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(CndnDetailsListByGstinforupload);
		query.setString("gstin", gstinId);
		query.setString("createdOn", financialPeriod);
		CndnDetailsList = (List<PayloadCnDnDetails>)query.list();
			
	}
	catch (Exception e) {
		logger.error("Error in:",e);
		throw e;
	}
	logger.info("Exit");
	return CndnDetailsList;
			
			
}

@Override
public String setUploadToJiogstStatusCndn(String gstin, String fp,
		String response, String uploadType,List<PayloadCnDnDetails> cndnList /*List<Integer> invoiceid*/) throws Exception {
	// TODO Auto-generated method stub
	Session session = sessionFactory.getCurrentSession();
	//InvoiceDetails invoicedetails=new InvoiceDetails();
	/*Transaction tx = session.beginTransaction();*/
	try{
	int count=0;
	for(int i=0;i<cndnList.size();i++)
	{
	/*Query query = session.createQuery(setinvoicestatus);
	query.setString("gstinId", gstinId);
	query.setString("invoiceDate", financialPeriod);
	query.setInteger("id",  invoiceList.get(i).getId());*/
	
		PayloadCnDnDetails cndndetails =cndnList.get(i);
		
		if(uploadType=="UploadToJiogst")
		{
			cndndetails.setUploadToJiogst("true");
		}
		else if (uploadType=="SaveToGSTN")
		{
			cndndetails.setSaveToGstn("true");
		}
		else if (uploadType=="SubmitToGSTN")
		{
			cndndetails.setSubmitToGstn("true");
		}
		else if (uploadType=="fileToGstn")
		{
			cndndetails.setFileToGstn("true");
		}
		
		session.update(cndndetails);
		if( ++count % 100 == 0)
		{
			session.flush();
		      session.clear();
			
		}
		
		
	}
	 if(++count % 100 != 0)
	{
		session.flush();
	      session.clear();
		
	}
	
		response=GSTNConstants.SUCCESS;
	}
	
	catch(Exception e){
		logger.info("Exception in :"+e);
	}
	
	/*tx.commit();*/
	
	return response;
	
}


	
}
