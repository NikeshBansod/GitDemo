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

import com.reliance.gstn.admin.model.AdvolCessRateDetails;
import com.reliance.gstn.admin.model.NonAdvolCessRateDetails;
import com.reliance.gstn.dao.AdvolAndNonAdvolCessServiceDAO;
import com.reliance.gstn.dao.UnitOfMeasurementDAO;
import com.reliance.gstn.model.UnitOfMeasurement;
//import com.reliance.gstn.util.LoggerUtil;

/**
 * @author Rupali.J
 *
 */
@Repository
public class AdvolAndNonAdvolCessDAOImpl implements AdvolAndNonAdvolCessServiceDAO {
	
	private static final Logger logger = Logger.getLogger(AdvolAndNonAdvolCessDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${advol_cess_list_query}")
	private String advolCessListQuery;
	
	@Value("${non_advol_cess_list_query}")
	private String nonAdvolCessListQuery;
	
	@Override
	public List<AdvolCessRateDetails> getAdvolCessRateDetailList() throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(advolCessListQuery);
		@SuppressWarnings("unchecked")
		List<AdvolCessRateDetails> unitOfMeasurementList =(List<AdvolCessRateDetails>)query.list();
		
		logger.info("Exit");
		return unitOfMeasurementList;
	}
	
	
	@Override
	public List<NonAdvolCessRateDetails> getNonAdvolCessRateDetailList() throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(nonAdvolCessListQuery);
		@SuppressWarnings("unchecked")
		List<NonAdvolCessRateDetails> unitOfMeasurementList =(List<NonAdvolCessRateDetails>)query.list();
		
		logger.info("Exit");
		return unitOfMeasurementList;
	}
}
