/**
 * 
 */
package com.reliance.gstn.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.dao.RegistrationDAO;
import com.reliance.gstn.model.NatureOfBusiness;
import com.reliance.gstn.model.Registration;
import com.reliance.gstn.util.GSTNConstants;
//import com.reliance.gstn.util.LoggerUtil;

/**
 * @author Nikesh.Bansod
 *
 */
@Repository
public class RegistrationDAOImpl implements RegistrationDAO {
	
	private static final Logger logger = Logger.getLogger(RegistrationDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${login_query}")
	private String loginQuery;
	
	@Value("${add_registration_query}")
	private String addRegistrationQuery;
	
	@Value("${validate_password_query}")
	private String validatePasswordQuery;
	
	@Value("${nature_of_business_list}")
	private String getNatureOfBusinessList;


	@Override
	public String addRegistration(Registration registration) {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createSQLQuery(addRegistrationQuery);
			query.setString("spName", registration.getSpName());
			query.setString("cpName", registration.getCpName());
			query.setString("contactNo", registration.getContactNo());
			query.setString("primaryEmail", registration.getPrimaryEmail());
			query.setString("password", registration.getPassword());
			query.setLong("gstnRegNumber", registration.getGstnRegNumber());
			query.setString("panNumber", registration.getPanNumber());
			query.setParameter("createdOn", new java.sql.Timestamp(new Date().getTime()));
			query.executeUpdate();
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return response;
	}


	@Override
	public Registration getRegistrationById(Integer uId) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();		
		Registration r = (Registration) session.load(Registration.class, uId);
		logger.info("Person loaded successfully, Registration details = "+r);
		logger.info("Exit");
		return r;
	}


	@Override
	public String updateRegistration(Registration registration) {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			registration.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			registration.setUpdatedBy(registration.getCpName());
			session.saveOrUpdate(registration);
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return response;
	}


	@Override
	public boolean validatePassword(Integer uId, String oldPassword) {
		logger.info("Entry");
		boolean validatePassword = false;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createSQLQuery(validatePasswordQuery);
			query.setInteger("uId", uId);
			query.setString("password", oldPassword);
			validatePassword = true;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return validatePassword;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<NatureOfBusiness> getNatureOfBusinessList() throws Exception {
		logger.info("Entry");
		List<NatureOfBusiness> list = new ArrayList<NatureOfBusiness>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(getNatureOfBusinessList);
			list = (List<NatureOfBusiness>)query.list();
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return list;
	}

}
