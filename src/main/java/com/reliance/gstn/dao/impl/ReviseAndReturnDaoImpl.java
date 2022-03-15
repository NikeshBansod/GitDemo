/**
 * 
 */
package com.reliance.gstn.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.dao.ReviseAndReturnDao;
import com.reliance.gstn.model.InvoiceAdditionalChargeDetails;
import com.reliance.gstn.model.InvoiceAdditionalChargeDetailsHistory;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.InvoiceDetailsHistory;
import com.reliance.gstn.model.InvoiceServiceDetails;
import com.reliance.gstn.model.InvoiceServiceDetailsHistory;
import com.reliance.gstn.model.ReviseAndReturn;
import com.reliance.gstn.model.ReviseAndReturnHistory;
import com.reliance.gstn.util.GSTNConstants;

/**
 * @author Nikesh.Bansod
 *
 */
@Repository
public class ReviseAndReturnDaoImpl implements ReviseAndReturnDao {
	
	private static final Logger logger = Logger.getLogger(ReviseAndReturnDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${rr_list_query}")
	private String rrListQuery;
	
	@Value("${revised_and_return_details_List_Query}")
	private String revisedAndReturnDetailsListQuery;
	
	@Value("${validate_Invoice_History_By_OrgId_Query}")
	private String validateInvoiceHistoryByOrgId;
	
	@Value("${get_InvoiceDetails_By_Id_And_IterationNo}")
	private String getInvoiceDetailsByIdAndIterationNo;
	
	
	@Value("${get_InvoiceDetails_History_By_Id_And_IterationNo}")
	private String getInvoiceDetailsHistoryByIdAndIterationNoquery;
	
	@Value("${get_ProductDetails_History_By_Id_And_IterationNo}")
	private String  getProductDetailsHistoryByIdAndIterationNo;
	
	
	@Value("${get_addChargesDetails_History_By_IdAndIterationNo}")
	private String  getaddChargesDetailsHistoryByIdAndIterationNo;
	
	@Value("${get_InvoiceDetails_By_Id_for_RRHistor}")
	private String  getInvoiceDetailsByIdforRRHistor;
	
	
	
	@Override
	public List<ReviseAndReturn> listRR() {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(rrListQuery);

		@SuppressWarnings("unchecked")
		List<ReviseAndReturn> rrList = (List<ReviseAndReturn>) query.list();

		logger.info("Exit");
		return rrList;
	}

	@Override
	public String updateRRHistory(ReviseAndReturnHistory rrHistory) {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(rrHistory);
			response = GSTNConstants.SUCCESS;
		} catch(ConstraintViolationException e){
			logger.error("Error in:",e);
			throw e;
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> revisionAndReturnDetail(Integer orgUId) {
       logger.info("Entry");
		
		List<Object[]> revisionAndReturnDetail = null;  
		Session session = sessionFactory.getCurrentSession();
		try{
		Query query = session.createSQLQuery(revisedAndReturnDetailsListQuery);
		query.setInteger("orgId", orgUId);
		
		revisionAndReturnDetail =(List<Object[]>)query.list();
		
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
	    logger.info("Exit");
	    
		return revisionAndReturnDetail;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean validateInvoiceHistoryAgainstOrgId(Integer invoiceId,
			Integer orgUId) {
		logger.info("Entry");
		boolean isInvoiceAllowed = false;
		List<InvoiceDetailsHistory> invoiceDetailsHistoryList = new ArrayList<InvoiceDetailsHistory>();
		try {
			 
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(validateInvoiceHistoryByOrgId);
			query.setInteger("orgUId", orgUId);
			query.setInteger("id", invoiceId);
			invoiceDetailsHistoryList = (List<InvoiceDetailsHistory>)query.list();
			if (!invoiceDetailsHistoryList.isEmpty()){
				isInvoiceAllowed = true;
			}
			
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
	
		logger.info("Exit");
		return isInvoiceAllowed;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceDetailsHistory> getInvoiceDetailsHistoryById(Integer id,Integer iterationNo) {
		
		logger.info("Entry");
		List<InvoiceDetailsHistory> invoiceDetailsHistory = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		
		try {
			Query query = session.createSQLQuery(getInvoiceDetailsHistoryByIdAndIterationNoquery).addEntity(InvoiceDetailsHistory.class);
			query.setInteger("id", id);
			query.setInteger("iterationNo", iterationNo);
			invoiceDetailsHistory = query.list();
			
			
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		
		return invoiceDetailsHistory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceDetails> getInvoiceDetailsByIdAndIterationNo(Integer id, Integer iterationNo) {
		logger.info("Entry");
		List<InvoiceDetails> invoiceDetails = new ArrayList<>(); 
		Session session = sessionFactory.getCurrentSession();
		
		try {
			Query query = session.createQuery(getInvoiceDetailsByIdAndIterationNo);
			query.setInteger("id", id);
			query.setInteger("iterationNo", iterationNo);
			List<InvoiceDetails> list = (List<InvoiceDetails>)query.list();
			invoiceDetails.addAll(list);
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return invoiceDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceServiceDetailsHistory> getProductDetailsHistoryById(Integer id,Integer iterationNo) {
		logger.info("Entry");
		List<InvoiceServiceDetailsHistory> productDetailsHistory = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		
		try {
			Query query = (Query) session.createSQLQuery(getProductDetailsHistoryByIdAndIterationNo).addEntity(InvoiceServiceDetailsHistory.class);
			query.setInteger("id", id);
			query.setInteger("iterationNo", iterationNo);
			productDetailsHistory = query.list();
			
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		
		return productDetailsHistory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceAdditionalChargeDetailsHistory> getAdditionalChargesHistory(Integer id, Integer iterationNo) {
		logger.info("Entry");
		List<InvoiceAdditionalChargeDetailsHistory> addChargesDetailsHistory = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		
		try {
			Query query = (Query) session.createSQLQuery(getaddChargesDetailsHistoryByIdAndIterationNo).addEntity(InvoiceAdditionalChargeDetailsHistory.class);
			query.setInteger("id", id);
			query.setInteger("iterationNo", iterationNo);
			addChargesDetailsHistory = query.list();
			
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		
		return addChargesDetailsHistory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceDetails> getInvoiceDetailsHistoryByIdByNativeQueryForLatestRR(Integer invoiceid) {
		logger.info("Entry");
		List<InvoiceDetails> invoiceDetails = new ArrayList<>(); 
		Session session = sessionFactory.getCurrentSession();
		
		try {
			Query query = session.createQuery(getInvoiceDetailsByIdforRRHistor);
			query.setInteger("id", invoiceid);
			List<InvoiceDetails> list = (List<InvoiceDetails>)query.list();
			invoiceDetails.addAll(list);
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return invoiceDetails;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceDetails> getOldInvoiceDetailsByIdByNativeQuery(Integer id,Integer iterationNo) {
		logger.info("Entry");
		List<InvoiceDetails> invoiceDetailsHistory = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		
		try {
			Query query = session.createSQLQuery(getInvoiceDetailsHistoryByIdAndIterationNoquery).addEntity(InvoiceDetails.class);
			query.setInteger("id", id);
			query.setInteger("iterationNo", iterationNo);
			invoiceDetailsHistory =query.list();
			
			
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		
		return invoiceDetailsHistory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceServiceDetails> getOldServiceProductDetailsByIdByNativeQuery(Integer id,Integer iterationNo) {
		logger.info("Entry");
		List<InvoiceServiceDetails> productDetailsHistory = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		
		try {
			Query query = (Query) session.createSQLQuery(getProductDetailsHistoryByIdAndIterationNo).addEntity(InvoiceServiceDetails.class);
			query.setInteger("id", id);
			query.setInteger("iterationNo", iterationNo);
			productDetailsHistory = query.list();
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return productDetailsHistory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceAdditionalChargeDetails> getOldAdditionalCharges(Integer id, Integer iterationNo) {
		logger.info("Entry");
		List<InvoiceAdditionalChargeDetails> addChargesDetailsHistory = new ArrayList<>();
		Session session = sessionFactory.getCurrentSession();
		
		try {
			Query query = (Query) session.createSQLQuery(getaddChargesDetailsHistoryByIdAndIterationNo).addEntity(InvoiceAdditionalChargeDetails.class);
			query.setInteger("id", id);
			query.setInteger("iterationNo", iterationNo);
			addChargesDetailsHistory = query.list();
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return addChargesDetailsHistory;
	}
}

	


