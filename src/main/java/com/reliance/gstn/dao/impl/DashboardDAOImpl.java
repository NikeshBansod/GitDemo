package com.reliance.gstn.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.dao.DashboardDAO;
import com.reliance.gstn.model.CnDnAdditionalDetails;
import com.reliance.gstn.model.Dashboard;
import com.reliance.gstn.model.EWayBill;
import com.reliance.gstn.model.EwayBillWIItem;
import com.reliance.gstn.model.PayloadCnDnDetails;
import com.reliance.gstn.util.GSTNConstants;

@Repository
public class DashboardDAOImpl implements DashboardDAO {

	private static final Logger logger = Logger
			.getLogger(DashboardDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${invoice_list_equery}")
	private String invoicelistquery;

	@Value("${cndn_list_query}")
	private String cndnlistquery;

	@Value("${ewaybill_list_query}")
	private String ewaybilllistquery;

	@Value("${eway_bill_count}")
	private String ewaybillcount;

	@Value("${cndn_query}")
	private String cndnquery;

	@Value("${invoice_query}")
	private String invoicequery;

	@Value("${get_invoiceid_by_cndn}")
	private String getinvoiceidbycndn;

	@Value("${dash_product_details}")
	private String dashproductdetails;

	@Value("${dash_invoice_CNDN_details}")
	private String dashinvoiceCNDNdetails;

	@Value("${invoice_ewaybill_count}")
	private String invoiceewaybillcount;
	
	@Value("${invoice_ewaybill_list_query}")
	private String invoiceewaybilllistquery;
	
	
	@Value("${ewayBill_WI_Item_Details}")
	private String ewayBillWIItemDetails;
	
	
	@Value("${invoice_EWayBill_Details}")
	private String invoiceEWayBillDetails;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getGeneratedInvoiceList(Dashboard dashboard,
			Integer uID, String getInvType,String appCode) {

		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		List<Object[]> invoiceList = null;

		try {
			if (getInvType
					.equalsIgnoreCase(GSTNConstants.DASHBOARD_TYPE_INVOICED)) {
				Query query = session.createQuery(invoicelistquery);
				query.setString("startdate", dashboard.getStartdate());
				query.setString("enddate", dashboard.getEnddate());
				query.setInteger("uID", uID);
				invoiceList = (List<Object[]>) query.list();
			} else if (getInvType.equalsIgnoreCase(GSTNConstants.DASHBOARD_TYPE_CNDN)) {
				Query query = session.createSQLQuery(cndnlistquery);
				query.setString("startdate", dashboard.getStartdate());
				query.setString("enddate", dashboard.getEnddate());
				query.setInteger("uID", uID);
				invoiceList = (List<Object[]>) query.list();
			}
			else if (getInvType.equalsIgnoreCase(GSTNConstants.DASHBOARD_TYPE_EWAYBILL)) {
				Query query = session.createQuery(ewaybilllistquery);
				query.setString("startdate", dashboard.getStartdate());
				query.setString("enddate", dashboard.getEnddate());
				query.setInteger("uID", uID);
				/*query.setString("appCode", appCode);*/
				invoiceList = (List<Object[]>) query.list();
			}
			else if (getInvType.equalsIgnoreCase(GSTNConstants.DASHBOARD_TYPE_EWAYBILL_INVOICE)) {
				Query query = session.createQuery(invoiceewaybilllistquery);
				query.setString("startdate", dashboard.getStartdate());
				query.setString("enddate", dashboard.getEnddate());
				query.setInteger("uID", uID);
				invoiceList = (List<Object[]>) query.list();
			}

		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return invoiceList;
	}

	@Override
	public long getInvoiceCount(Dashboard dashboard, Integer uID,String appCode)
			throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		long eWaybillCount;
		try {
			Query query = session.createQuery(ewaybillcount);
			query.setString("startdate", dashboard.getStartdate());
            query.setString("enddate", dashboard.getEnddate());
			query.setInteger("userId", uID);
		/*	query.setString("appCode", appCode);*/
			eWaybillCount = (long) query.uniqueResult();
			logger.info("my final eway bill count ::" + eWaybillCount);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return eWaybillCount;

	}

	@Override
	public long getCnDnNote(Dashboard dashboard, Integer uID) throws Exception {

		long cndnCount;
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createSQLQuery(cndnquery).addScalar("count", LongType.INSTANCE);
			query.setString("startdate", dashboard.getStartdate());
			query.setString("enddate", dashboard.getEnddate());
			query.setInteger("uID", uID);
			cndnCount = (long) query.uniqueResult();
			logger.info("my final CnDn count ::" + cndnCount);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return cndnCount;
	}

	@Override
	public long getGeneratedInvoiceCount(Dashboard dashboard, Integer uID)
			throws Exception {

		logger.info("Entry");
		long invoiceCount;
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(invoicequery);
			query.setString("startdate", dashboard.getStartdate());
			query.setString("enddate", dashboard.getEnddate());
			query.setInteger("uID", uID);
			invoiceCount = (long) query.uniqueResult();
			logger.info("my final invoice count ::" + invoiceCount);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return invoiceCount;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayloadCnDnDetails> getProductDetailsForDash(String cndnNumber) {

		List<PayloadCnDnDetails> dash_productDetails = new ArrayList<PayloadCnDnDetails>();
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(dashproductdetails);
			query.setString("cndnNumber", cndnNumber);
			List<PayloadCnDnDetails> list = (List<PayloadCnDnDetails>) query.list();
			dash_productDetails.addAll(list);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return dash_productDetails;
	}

	@Override
	public List<CnDnAdditionalDetails> getCNDNdetails(Integer cId) {
		List<CnDnAdditionalDetails> dash_invoiceCNDNdetails = new ArrayList<CnDnAdditionalDetails>();
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(dashinvoiceCNDNdetails);
			query.setInteger("cId", cId);
			@SuppressWarnings("unchecked")
			List<CnDnAdditionalDetails> list = (List<CnDnAdditionalDetails>) query.list();
			dash_invoiceCNDNdetails.addAll(list);

		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return dash_invoiceCNDNdetails;
	}

	@Override
	public long getInvoiceEWaybillCount(Dashboard dashboard, Integer orgUId) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		long invoiceEWaybillCount;
		try {
			Query query = session.createQuery(invoiceewaybillcount);
			query.setString("startdate", dashboard.getStartdate());
			query.setString("enddate", dashboard.getEnddate());
			query.setInteger("orgUId", orgUId);
			invoiceEWaybillCount = (long) query.uniqueResult();
			logger.info("my final eway bill count ::" + invoiceEWaybillCount);

		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}

		logger.info("Exit");

		return invoiceEWaybillCount;
	}


	@Override
	public List<EwayBillWIItem> getewayBillWIItemDetails(int ewayBillWITransId) {
		List<EwayBillWIItem> ewayBillWIItem=new ArrayList<EwayBillWIItem>();
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		
		try {
			Query query = session.createQuery(ewayBillWIItemDetails);
			query.setInteger("ewayBillWITransId",ewayBillWITransId);
			@SuppressWarnings("unchecked")
			List<EwayBillWIItem> list = (List<EwayBillWIItem>) query.list();
			ewayBillWIItem.addAll(list);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}

		logger.info("Exit");
		return ewayBillWIItem;
	}

	@Override
	public List<EWayBill> getInvoiceEwayBillWITransactionDetails(
			String ewaybillNo) {
		List<EWayBill> InvoiceEWayBill=new ArrayList<EWayBill>();
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(invoiceEWayBillDetails);
			query.setString("ewaybillNo",ewaybillNo);
			@SuppressWarnings("unchecked")
			List<EWayBill> list = (List<EWayBill>) query.list();
			InvoiceEWayBill.addAll(list);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}

		logger.info("Exit");
		return InvoiceEWayBill;
	}

	

	

	

}
