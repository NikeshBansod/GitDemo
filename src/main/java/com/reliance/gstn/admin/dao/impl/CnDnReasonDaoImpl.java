/**
 * 
 */
package com.reliance.gstn.admin.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.admin.dao.CnDnReasonDao;
import com.reliance.gstn.admin.model.CnDnReason;

/**
 * @author Nikesh.Bansod
 *
 */
@Repository
public class CnDnReasonDaoImpl implements CnDnReasonDao {
	
	private static final Logger logger = Logger.getLogger(CnDnReasonDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${cndn_reason_list_query}")
	private String cnDnReasonListQuery;
	
	@Override
	public List<CnDnReason> listCnDnReason() {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(cnDnReasonListQuery);
		
		@SuppressWarnings("unchecked")
		List<CnDnReason> cndnReasonList = query.list();
		logger.info("Exit");
		return cndnReasonList;
	}


}
