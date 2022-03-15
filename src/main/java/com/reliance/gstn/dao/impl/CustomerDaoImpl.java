package com.reliance.gstn.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataIntegrityViolationException;

import com.google.gson.Gson;
import com.reliance.gstn.dao.CustomerDao;
import com.reliance.gstn.model.CustomerDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.GstinValidationService;
import com.reliance.gstn.util.GSTNConstants;
//import com.reliance.gstn.util.LoggerUtil;
import com.reliance.gstn.util.GSTNUtil;

/**
 * @author Vivek2.Dubey
 *
 */
@Repository
public class CustomerDaoImpl implements CustomerDao {

	private static final Logger logger = Logger.getLogger(CustomerDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Value(value = "${manage_customer_details_list_query}")
	private String manageCustomerDetailsListQuery;

	@Value(value = "${customer_details_list_by_refid}")
	private String customerDetailsListByRefId;

	@Value(value = "${customer_name_by_refid}")
	private String customerNameByRefId;

	@Value(value = "${customer_details_list_by_refid_and_custname}")
	private String customerDetailsByRefIdAndCustName;
	
	@Value(value = "${check_customer_org_duplicacy}")
	private String checkCustContactNoDuplicacy;
	
	@Value(value = "${get_customer_by_contactNo}")
	private String getCustomerDetailsByContactNo;
	
	@Value(value = "${get_customers_list_by_custName_and_contactNo_query}")
	private String getCustomersListByCustNameAndContactNoQuery;
	
	@Value(value = "${get_registered_customers_list_by_custName_and_contactNo_query}")
	private String getRegisteredCustomersListByCustNameAndContactNoQuery;
	
	@Value(value = "${get_customer_detail_by_custName_and_contactNo_query}")
	private String getCustomerDetailByCustNameAndContactNoQuery;
	
	@Value(value = "${get_Registered_SupplierList_BySupplierName_AndContactNo_Query}")
	private String getRegisteredSupplierListBySupplierNameAndContactNoQuery;
	
	@Autowired
	GstinValidationService gstinValidationService;		
	
	@Override
	public Map<String,Object> addCustomerDetails(CustomerDetails customerDetails,
			Map<?, ?> mapValues) throws ConstraintViolationException, Exception {
		logger.info("Entry");
		Map<String,Object> mapResponse = new HashMap<String,Object>();
		String response = GSTNConstants.FAILURE;
		String isError = "";
		String isActive = "";
		
		try {
			Session session = sessionFactory.openSession();
			LoginMaster loginMaster = (LoginMaster)mapValues.get("loginMaster");
			
			if(customerDetails.getCustGstId() != null && !customerDetails.getCustGstId().isEmpty()){
				response = gstinValidationService.isValidGstin(customerDetails.getCustGstId());
				Gson gson = new Gson();
				Map<String, Object> responseMap = new HashMap<String, Object>();
				responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
				isError = (String) responseMap.get("err_cd");
				isActive = (String) responseMap.get("sts");
				
				if(isError != null && !isError.isEmpty()){
					
					response = GSTNConstants.INVALID_GSTIN;
					
				}else if (!isActive.equalsIgnoreCase("Active")){
					
					response = GSTNConstants.INVALID_GSTIN;
					
				}  else {
					customerDetails.setCreatedBy(loginMaster.getuId().toString());
					customerDetails.setRefOrgId(loginMaster.getOrgUId());
					customerDetails.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
					customerDetails.setRefId(loginMaster.getuId());
					customerDetails.setStatus("1");
					session.save(customerDetails);
					mapResponse.put("customerDetails", customerDetails);
					response = GSTNConstants.SUCCESS;
				}
			} else {
			
				customerDetails.setCreatedBy(loginMaster.getuId().toString());
				customerDetails.setRefOrgId(loginMaster.getOrgUId());
				customerDetails.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
				customerDetails.setRefId(loginMaster.getuId());
				customerDetails.setStatus("1");
				session.save(customerDetails);
				mapResponse.put("customerDetails", customerDetails);
				response = GSTNConstants.SUCCESS;
			}
		} catch(ConstraintViolationException  e){
			logger.error("Error in:",e);
			throw e;
		}catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		
		mapResponse.put(GSTNConstants.RESPONSE, response);
		logger.info("Exit");
		return mapResponse;
	}

	@Override
	public List<CustomerDetails> viewCustomerDetailsList(Integer uId) throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(manageCustomerDetailsListQuery);
		query.setInteger("referenceId", uId);
		query.setString("status", "1");

		@SuppressWarnings("unchecked")
		List<CustomerDetails> viewCustomerDetailsList =(List<CustomerDetails>)query.list();
		logger.info("Exit");
		return viewCustomerDetailsList;
	}

	@Override
	public String deleteCustomerDetails(Integer id) throws ConstraintViolationException, Exception {
		String response = GSTNConstants.FAILURE;
		logger.info("Entry");
		try {
			Session session = sessionFactory.getCurrentSession();	
			Object persistCustomerDetail = session.get(CustomerDetails.class, id);
			if(persistCustomerDetail != null){
				session.delete(persistCustomerDetail);
				response = GSTNConstants.SUCCESS;
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
	public String updateCustomerDetails(CustomerDetails customerDetails, Map<?, ?> mapValues)
			throws ConstraintViolationException, Exception {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		String isError = "";
		String isActive = "";
		
		try {
			Session session = sessionFactory.getCurrentSession();
			LoginMaster loginMaster = (LoginMaster)mapValues.get("loginMaster");
			if(customerDetails.getCustGstId() != null && !customerDetails.getCustGstId().isEmpty()){
				response = gstinValidationService.isValidGstin(customerDetails.getCustGstId());
				Gson gson = new Gson();
				Map<String, Object> responseMap = new HashMap<String, Object>();
				responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
				isError = (String) responseMap.get("err_cd");
				isActive = (String) responseMap.get("sts");
				
				if(isError != null && !isError.isEmpty()) {
			//	if(isError != null || !isError.isEmpty()){
				 
					response = GSTNConstants.INVALID_GSTIN;
					
				} else if (!isActive.equalsIgnoreCase("Active")){
					
					response = GSTNConstants.INVALID_GSTIN;
					
				} else{
					customerDetails.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
					customerDetails.setRefId(loginMaster.getuId());
					customerDetails.setUpdatedBy(loginMaster.getuId().toString());
					customerDetails.setRefOrgId(loginMaster.getOrgUId());
					
					session.saveOrUpdate(customerDetails);
					response = GSTNConstants.SUCCESS;
					
				}
			} else {
			
			customerDetails.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			customerDetails.setRefId(loginMaster.getuId());
			customerDetails.setUpdatedBy(loginMaster.getuId().toString());
			customerDetails.setRefOrgId(loginMaster.getOrgUId());
			
			session.saveOrUpdate(customerDetails);
			response = GSTNConstants.SUCCESS;
			}
		} catch(DataIntegrityViolationException e){
			logger.error("Error in:",e);
			throw e;
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public CustomerDetails getCustomerDetailsById(Integer id) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();	
		CustomerDetails custDetails = (CustomerDetails) session.get(CustomerDetails.class, id);
		logger.info("Exit");
		return custDetails;
	}

	@Override
	public List<CustomerDetails> getCustomersList(Integer refOrgId) throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
	
		Query query = session.createQuery(customerDetailsListByRefId);
		query.setInteger("refOrgId", refOrgId);
		
		@SuppressWarnings("unchecked")
		List<CustomerDetails> viewCustomerDetailsList = (List<CustomerDetails>)query.list();
		
		logger.info("Exit");
		return viewCustomerDetailsList;
	}

	@Override
	public List<String> getCustomersListByCustName(String idsValuesToFetch) throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(customerNameByRefId);
		query.setString("referenceId", idsValuesToFetch);
		@SuppressWarnings("unchecked")
		List<String> customerDetailsList = (List<String>)query.list();
		logger.info("Exit");
		return customerDetailsList;
	}

	@Override
	public CustomerDetails getCustomerDetailsByCustName(String custName,String idsValuesToFetch) throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(customerDetailsByRefIdAndCustName);
		query.setString("custName", custName);
		query.setString("referenceId", idsValuesToFetch);
		CustomerDetails customerDetails = null;
		
		@SuppressWarnings("unchecked")
		List<CustomerDetails> customerDetailsList =(List<CustomerDetails>)query.list();
		
		if (!customerDetailsList.isEmpty()) {
			customerDetails = customerDetailsList.get(0);
		}
		logger.info("Exit");
		return customerDetails;
	}

	@Override
	public boolean checkIfCustomerExists(String customer, String custName, Integer orgUId) throws Exception{
		
		logger.info("Entry");
		boolean gstinRegistered = false;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(checkCustContactNoDuplicacy);
		query.setString("custContact", customer);
		query.setString("custName", custName);
		query.setInteger("orgRefId", orgUId);
		@SuppressWarnings("unchecked")
		List<CustomerDetails> list = (List<CustomerDetails>)query.list();
		
		if (!list.isEmpty()) {
			gstinRegistered = true;
		}
		logger.info("Exit");
		
		return gstinRegistered;
	}

	@Override
	public CustomerDetails checkIfCustomerContactNoExists(String contactNo, Integer orgUId) {
		logger.info("Entry");
		CustomerDetails customer = new CustomerDetails();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(getCustomerDetailsByContactNo);
		query.setString("custContact", contactNo);
		query.setInteger("orgRefId", orgUId);
		@SuppressWarnings("unchecked")
		List<CustomerDetails> list = (List<CustomerDetails>)query.list();
		
		if (!list.isEmpty()) {
			customer = (CustomerDetails)list.get(0);
			customer.setIsAvailable("Yes");
		}else{
			customer.setIsAvailable("No");
		}
		logger.info("Exit");
		
		return customer;
	}

	@Override
	public List<Object[]> getCustomersListByCustNameAndMobileNo(Integer orgUid, String inputData, String documentType) {
		logger.info("Entry");
		String queryString = getCustomersListByCustNameAndContactNoQuery;
		if(documentType.equals("rcInvoice")){
			queryString = getRegisteredCustomersListByCustNameAndContactNoQuery;
		}
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(queryString);
		query.setInteger("orgRefId", orgUid);
		query.setString("inputData", "%"+inputData+"%");
		
		@SuppressWarnings("unchecked")
		List<Object[]> customerList = (List<Object[]>)query.list();
		
		logger.info("Exit");
		return customerList;
	}

	@Override
	public CustomerDetails getCustomerDetailsByCustNameAndContactNo(String custName, String contactNo, Integer orgUid) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(getCustomerDetailByCustNameAndContactNoQuery);
		query.setString("contactNo", contactNo+"%");
		query.setString("custName", custName+"%");
		query.setInteger("refOrgId", orgUid);
		
		CustomerDetails customer = null;
		@SuppressWarnings("unchecked")
		List<CustomerDetails> list = (List<CustomerDetails>)query.list();
		
		if (!list.isEmpty()) {
			customer = (CustomerDetails)list.get(0);
		}
		
		logger.info("Exit");
		return customer;
	}

	@Override
	public boolean checkUserCustomerMapping(String idsValuesToFetch,Integer primId, Integer custId)
			throws Exception {
		logger.info("Entry");
		boolean isMappingValid = false;
		try {
			
			Session session = sessionFactory.getCurrentSession();
			List<Integer> ali = GSTNUtil.getListFromString(idsValuesToFetch);
			String placeHoder = GSTNUtil.getPlaceHoderString(ali);
			String queryString =  "";
			queryString=queryString.replace("refUserId", placeHoder);
			Query query = session.createQuery(queryString);
			
			for (int i = 0; i < ali.size(); i++) {
				query.setInteger(i, ali.get(i));
			}
			
		@SuppressWarnings("unchecked")
		List<UserMaster> list = (List<UserMaster>)query.list();
		
		if (!list.isEmpty()) {
			
				if(list.contains(custId)){
					isMappingValid = true;
				}else{
					isMappingValid = false;
				}
			
	    }
			} catch (Exception e) {
				logger.error("Error in:",e);
			}
		logger.info("Exit");
		
		return isMappingValid;
	}

	@Override
	public boolean checkSecUserCustomerMapping(Integer primId, Integer custId)
			throws Exception {
		logger.info("Entry");
		boolean isMappingValid = false;
		try {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("");
		query.setInteger("refId", primId);
		query.setInteger("custId", custId);
		
		@SuppressWarnings("unchecked")
		List<UserMaster> list = (List<UserMaster>)query.list();
		
		if (!list.isEmpty()) {
			isMappingValid = true;
		}
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		
		return isMappingValid;
	}

	@Override
	public List<Object[]> getSupplierListBySupplNameAndMobileNo(Integer orgUid,
			String inputData, String documentType) throws Exception {
		logger.info("Entry");
		String queryString = getRegisteredSupplierListBySupplierNameAndContactNoQuery;
		if(documentType.equals("rcInvoice")){
			queryString = getRegisteredCustomersListByCustNameAndContactNoQuery;
		}
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(queryString);
		query.setInteger("orgRefId", orgUid);
		query.setString("inputData", "%"+inputData+"%");
		
		@SuppressWarnings("unchecked")
		List<Object[]> customerList = (List<Object[]>)query.list();
		
		logger.info("Exit");
		return customerList;
	}
}
