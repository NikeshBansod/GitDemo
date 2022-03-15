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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.dao.PurchaseEntryDAO;
import com.reliance.gstn.model.PurchaseEntryDetails;
import com.reliance.gstn.util.GSTNConstants;
/**
 * @author @kshay Mohite
 *
 */
@Repository
public class PurchaseEntryDAOImpl implements PurchaseEntryDAO {

	private static final Logger logger = Logger.getLogger(PurchaseEntryDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${get_max_invoice_number_seq}")
	private String getMaxInvoiceNumberSequence;

	@Value("${purchase_entry_details_by_org_uid}")
	private String purchaseEntryDetailsByOrgUId;

	@Value("${validate_purchase_entry_invoice_by_orgId}")
	private String validatePurchaseEntryInvoiceByOrgId;
	
	@Value("${update_purchase_entry_delete_status}")
	private String updatePurchaseEntryDeleteStatus;
	
	@Override
	public String getLatestInvoiceNumber(String pattern, Integer orgUId) {
		logger.info("Entry");
		String latestInvoiceNo = null;
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createSQLQuery(getMaxInvoiceNumberSequence);
		query.setString("pattern", pattern+"%");
		query.setInteger("orgUId", orgUId);
		
		@SuppressWarnings("unchecked")
		List<Object> list = query.list();
		
		if (!list.isEmpty()) {		
				Object obj = list.get(0);
				latestInvoiceNo = (String) obj;		
		}
		
		logger.info("Exit");
		return latestInvoiceNo;
	}

	@Override
	public Map<String, String> addGeneratePurchaseEntryInvoice(PurchaseEntryDetails purchaseEntryDetails) throws Exception {
		logger.info("Entry");
		Map<String,String> responseMap = new HashMap<String,String>();
		try {
			Session session = sessionFactory.getCurrentSession();
			purchaseEntryDetails.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			session.saveOrUpdate(purchaseEntryDetails);
			
			purchaseEntryDetails = (PurchaseEntryDetails) session.get(PurchaseEntryDetails.class, purchaseEntryDetails.getPurchaseEntryDetailsId());
			responseMap.put(GSTNConstants.RESPONSE, GSTNConstants.SUCCESS);
			responseMap.put("InvoiceNumber", purchaseEntryDetails.getPurchaseEntryGeneratedInvoiceNumber());
			responseMap.put("purchaseEntryDetailsId", ""+purchaseEntryDetails.getPurchaseEntryDetailsId());
			
		} catch (Exception e) {
			responseMap.put(GSTNConstants.RESPONSE, GSTNConstants.FAILURE);
			logger.error("Error in:",e);
			throw e;
		}		
		logger.info("Exit");
		return responseMap;
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getPurchaseEntriesDetailByOrgUId(Integer orgUId) {
		logger.info("Entry");
		List<Object[]> purchaseEntriesDetailList = new ArrayList<Object[]>();
		try {
			 
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(purchaseEntryDetailsByOrgUId);
			query.setInteger("orgUId", orgUId);
			purchaseEntriesDetailList = (List<Object[]>)query.list();
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}	
		logger.info("Exit");
		return purchaseEntriesDetailList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean validateInvoiceAgainstOrgId(Integer purchaseEntryDetailsId, Integer orgUId) throws Exception{
		logger.info("Entry");
		boolean isInvoiceAllowed = false;
		List<PurchaseEntryDetails> invoiceDetailsList = new ArrayList<PurchaseEntryDetails>();
		try {			 
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(validatePurchaseEntryInvoiceByOrgId);
			query.setInteger("orgUId", orgUId);
			query.setInteger("purchaseEntryDetailsId", purchaseEntryDetailsId);
			invoiceDetailsList = (List<PurchaseEntryDetails>)query.list();
			if (!invoiceDetailsList.isEmpty()){
				isInvoiceAllowed = true;
			}			
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}	
		logger.info("Exit");
		return isInvoiceAllowed;
	}

	@Override
	public PurchaseEntryDetails getPurchaseEntryInvoiceDetailsById(Integer id) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		PurchaseEntryDetails purchaseEntryDetails = null;
		try {
			purchaseEntryDetails = (PurchaseEntryDetails) session.get(PurchaseEntryDetails.class, id);
			purchaseEntryDetails.getAddChargesList();
			purchaseEntryDetails.getServiceList();		   
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return purchaseEntryDetails;
	}

	@Override
	public String deletePurchaseEntryInvoice(int purchaseEntryDetailsId) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		String response = "error";
		try {			
			Query query = session.createQuery(updatePurchaseEntryDeleteStatus);
			query.setInteger("id", purchaseEntryDetailsId);
			query.executeUpdate();
			
			response = GSTNConstants.SUCCESS; 
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}

}
