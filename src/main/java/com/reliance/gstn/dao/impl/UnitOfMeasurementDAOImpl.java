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

import com.reliance.gstn.dao.UnitOfMeasurementDAO;
import com.reliance.gstn.model.UnitOfMeasurement;
//import com.reliance.gstn.util.LoggerUtil;

/**
 * @author Pradeep.Gangapuram
 *
 */
@Repository
public class UnitOfMeasurementDAOImpl implements UnitOfMeasurementDAO {
	
	private static final Logger logger = Logger.getLogger(UnitOfMeasurementDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${unit_of_measurement_list_query}")
	private String unitOfMeasurementListQuery;
	
	@Override
	public List<UnitOfMeasurement> getUnitOfMeasurement() throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(unitOfMeasurementListQuery);
		@SuppressWarnings("unchecked")
		List<UnitOfMeasurement> unitOfMeasurementList =(List<UnitOfMeasurement>)query.list();
		
		logger.info("Exit");
		return unitOfMeasurementList;
	}
}
