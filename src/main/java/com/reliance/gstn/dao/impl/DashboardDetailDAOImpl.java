/**
 * 
 */
package com.reliance.gstn.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.dao.DashboardDetailDAO;
import com.reliance.gstn.dao.MasterDescDAO;
import com.reliance.gstn.dao.UnitOfMeasurementDAO;
import com.reliance.gstn.model.MasterDescDetails;
import com.reliance.gstn.model.UnitOfMeasurement;

/**
 * @author Rupali J
 *
 */
@Repository
public class DashboardDetailDAOImpl implements DashboardDetailDAO {
	
	private static final Logger logger = Logger.getLogger(DashboardDetailDAOImpl.class);
/*
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${get_total_invoice_count}")
	private String invoiceCountQuery;
	
	@Value("${get_total_invoice_amount}")
	private String invoiceAmtQuery;
	
	
	@Override
	public String getDashboardList() throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(invoiceCountQuery);
		@SuppressWarnings("unchecked")
		String masterDescList =(String)query.list().get(0);
		
		logger.info("Exit");
		return masterDescList;
	}
*/}
