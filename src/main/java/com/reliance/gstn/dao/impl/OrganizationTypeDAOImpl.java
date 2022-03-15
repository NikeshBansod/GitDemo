/**
 * 
 */
package com.reliance.gstn.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.dao.OrganizationTypeDAO;
import com.reliance.gstn.model.OrganizationType;
//import com.reliance.gstn.util.LoggerUtil;

/**
 * @author Nikesh.Bansod
 *
 */
@Repository
public class OrganizationTypeDAOImpl implements OrganizationTypeDAO {
	
	private static final Logger logger = Logger.getLogger(StateDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${organization_query}")
	private String orgTypeQuery;

	@Override
	public List<OrganizationType> listOrganizationType() {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(orgTypeQuery);
		@SuppressWarnings("unchecked")
		List<OrganizationType> organizationTypeList = (List<OrganizationType>)query.list();
		
		logger.info("Exit");
		return organizationTypeList;
	}

}
