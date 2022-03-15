package com.reliance.gstn.admin.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.admin.dao.AdminDashboardDAO;
import com.reliance.gstn.admin.model.DashboardAdmin;


@Repository
public class AdminDashboardDaoImpl implements AdminDashboardDAO {
	private static final Logger logger = Logger
			.getLogger(AdminDashboardDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${admin_total_invoice_query}")
	private String invoicequery;
	
	@Value("${admin_mobile_invoice_query}")
	private String mobileinvoicequery;
	
	@Value("${admin_desktop_invoice_query}")
	private String desktopinvoicequery;
	
	@Value("${admin_generic_eway_bill_count}")
	private String genericeWaybillcount;
	
	@Value("${admin_invoice_eway_bill_count}")
	private String invoiceWaybillcount;
	
	@Value("${admin_mobile_generic_eway_bill_count}")
	private String mobileGenericewaybillcount;
	
	@Value("${admin_desktop_generic_eway_bill_count}")
	private String desktopGenericewaybillcount;
	
	@Value("${admin_mobile_invoice_eway_bill_count}")
	private String mobileInvoiceewaybillcount;
	
	@Value("${admin_desktop_invoice_eway_bill_count}")
	private String desktopInvoiceewaybillcount;
	
	
	@Value("${admin_wizard_generic_eway_bill_count}")
	private String wizardGenericewaybillcount;
	
	@Override
	public long getMobileInvoiceCount(DashboardAdmin dashboardadmin)
			throws Exception {
		// TODO Auto-generated method stub

		logger.info("Entry");
		long mobileinvoiceCount;
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(mobileinvoicequery);
			query.setString("startdate", dashboardadmin.getStartdate());
			query.setString("enddate", dashboardadmin.getEnddate());
			
			mobileinvoiceCount = (long) query.uniqueResult();
			logger.info("my mobile invoice count ::" + mobileinvoiceCount);
			System.out.println(mobileinvoiceCount);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return mobileinvoiceCount;
		
	}

	@Override
	public long getDesktopInvoiceCount(DashboardAdmin dashboardadmin)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");
		long desktopinvoiceCount;
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(desktopinvoicequery);
			query.setString("startdate", dashboardadmin.getStartdate());
			query.setString("enddate", dashboardadmin.getEnddate());
			
			desktopinvoiceCount = (long) query.uniqueResult();
			logger.info("my desktop invoice count ::" + desktopinvoiceCount);
			System.out.println(desktopinvoiceCount);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return desktopinvoiceCount;
		
	}

	@Override
	public long getGeneratedInvoiceCount(DashboardAdmin dashboardadmin)
			throws Exception {
		// TODO Auto-generated method stub
		
		
		logger.info("Entry");
		long invoiceCount;
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(invoicequery);
			query.setString("startdate", dashboardadmin.getStartdate());
			query.setString("enddate", dashboardadmin.getEnddate());
			
			invoiceCount = (long) query.uniqueResult();
			logger.info("my final invoice count ::" + invoiceCount);
			System.out.println(invoiceCount);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return invoiceCount;
		
	}

	@Override
	public long getGenericEwaybillCount(DashboardAdmin dashboardadmin)
			throws Exception {
		// TODO Auto-generated method stub
		
			logger.info("Entry");
			Session session = sessionFactory.getCurrentSession();
			long genericewaybillcount;
			try {
				Query query = session.createQuery(genericeWaybillcount);
				query.setString("startdate", dashboardadmin.getStartdate());
				query.setString("enddate", dashboardadmin.getEnddate());
				
				genericewaybillcount = (long) query.uniqueResult();
				logger.info("my final eway bill count ::" + genericewaybillcount);
				System.out.println(genericewaybillcount);
			} catch (Exception e) {
				logger.error("Error in:", e);
				throw e;
			}
			logger.info("Exit");
			return genericewaybillcount;

		
	}

	@Override
	public long getInvoiceEwaybillCount(DashboardAdmin dashboardadmin)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		long invoiceWaybillCount;
		try {
			Query query = session.createQuery(invoiceWaybillcount);
			query.setString("startdate", dashboardadmin.getStartdate());
			query.setString("enddate", dashboardadmin.getEnddate());
			
			invoiceWaybillCount = (long) query.uniqueResult();
			logger.info("my final eway bill count ::" + invoiceWaybillCount);
			System.out.println(invoiceWaybillCount);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return invoiceWaybillCount;
	}

	@Override
	public long getMobileGenericEwaybillCount(DashboardAdmin dashboardadmin)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		long mobilegenericewaybillcount;
		try {
			Query query = session.createQuery(mobileGenericewaybillcount);
			query.setString("startdate", dashboardadmin.getStartdate());
			query.setString("enddate", dashboardadmin.getEnddate());
			
			mobilegenericewaybillcount = (long) query.uniqueResult();
			logger.info("my mobile generic eway bill count ::" + mobilegenericewaybillcount);
			System.out.println(mobilegenericewaybillcount);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return mobilegenericewaybillcount;
	}

	@Override
	public long getDesktopGenericEwaybillCount(DashboardAdmin dashboardadmin)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		long desktopgenericewaybillcount;
		try {
			Query query = session.createQuery(desktopGenericewaybillcount);
			query.setString("startdate", dashboardadmin.getStartdate());
			query.setString("enddate", dashboardadmin.getEnddate());
			
			desktopgenericewaybillcount = (long) query.uniqueResult();
			logger.info("my desktop generic eway bill count ::" + desktopgenericewaybillcount);
			System.out.println(desktopgenericewaybillcount);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return desktopgenericewaybillcount;
	}

	@Override
	public long getMobileInvoiceEwaybillCount(DashboardAdmin dashboardadmin)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		long mobileinvoiceewaybillcount;
		try {
			Query query = session.createQuery(mobileInvoiceewaybillcount);
			query.setString("startdate", dashboardadmin.getStartdate());
			query.setString("enddate", dashboardadmin.getEnddate());
			
			mobileinvoiceewaybillcount = (long) query.uniqueResult();
			logger.info("my mobile invoice eway bill count ::" + mobileinvoiceewaybillcount);
			System.out.println(mobileinvoiceewaybillcount);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return mobileinvoiceewaybillcount;
	}

	@Override
	public long getDesktopInvoiceEwaybillCount(DashboardAdmin dashboardadmin)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		long desktopinvoiceewaybillcount;
		try {
			Query query = session.createQuery(desktopInvoiceewaybillcount);
			query.setString("startdate", dashboardadmin.getStartdate());
			query.setString("enddate", dashboardadmin.getEnddate());
			
			desktopinvoiceewaybillcount = (long) query.uniqueResult();
			logger.info("my desktop invoice eway bill count ::" + desktopinvoiceewaybillcount);
			System.out.println(desktopinvoiceewaybillcount);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return desktopinvoiceewaybillcount;
	}

	@Override
	public long getWizardGenericEwaybillCount(DashboardAdmin dashboardadmin)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		long wizardgenericewaybillcount;
		try {
			Query query = session.createQuery(wizardGenericewaybillcount);
			query.setString("startdate", dashboardadmin.getStartdate());
			query.setString("enddate", dashboardadmin.getEnddate());
			
			wizardgenericewaybillcount = (long) query.uniqueResult();
			logger.info("my wizard generic  eway bill count ::" + wizardgenericewaybillcount);
			System.out.println(wizardgenericewaybillcount);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return wizardgenericewaybillcount;
	}
	

}
