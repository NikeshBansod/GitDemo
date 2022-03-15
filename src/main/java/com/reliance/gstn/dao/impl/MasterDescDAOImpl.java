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

import com.reliance.gstn.dao.MasterDescDAO;
import com.reliance.gstn.dao.UnitOfMeasurementDAO;
import com.reliance.gstn.model.MasterDescDetails;
import com.reliance.gstn.model.UnitOfMeasurement;

/**
 * @author Rupali J
 *
 */
@Repository
public class MasterDescDAOImpl implements MasterDescDAO {
	
	private static final Logger logger = Logger.getLogger(MasterDescDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${master_desc_list_query}")
	private String masterDescListQuery;
	
	@Override
	public List<MasterDescDetails> getMasterDescList() throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(masterDescListQuery);
		@SuppressWarnings("unchecked")
		List<MasterDescDetails> masterDescList =(List<MasterDescDetails>)query.list();
		
		logger.info("Exit");
		return masterDescList;
	}
}
