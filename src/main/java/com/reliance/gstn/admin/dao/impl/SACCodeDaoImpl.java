package com.reliance.gstn.admin.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.admin.dao.SACCodeDao;
import com.reliance.gstn.model.SACCode;
import com.reliance.gstn.util.GSTNConstants;

@Repository
public class SACCodeDaoImpl implements SACCodeDao {

	private static final Logger logger = Logger.getLogger(SACCodeDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${sac_details_list_limit_100_query}")
	private String SACDetailsListLimit1000Query;

	@Override
	public List<SACCode> getSACCodeList() {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(SACDetailsListLimit1000Query);
		query.setFirstResult(0).setMaxResults(10);
		
		@SuppressWarnings("unchecked")
		List<SACCode> SACDetailsList = query.list();
		logger.info("Exit");
		return SACDetailsList;
	}

	@Override
	public String addSACCode(SACCode sacCode) {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.persist(sacCode);
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public SACCode getSACDetailsById(Integer id) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();		
		SACCode sac = (SACCode) session.get(SACCode.class, id);
		logger.info("Exit");
		return sac;
	}

	@Override
	public String updateSACDetails(SACCode sacCode) throws Exception{
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
		
			session.saveOrUpdate(sacCode);
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		return response;
	}
	

	@Override
	public String removeSACDetails(Integer id) {
		String response = GSTNConstants.FAILURE;
		logger.info("Entry");
		try {
			Session session = sessionFactory.getCurrentSession();	
			SACCode p = (SACCode) session.get(SACCode.class, id);
			if(null != p){
				session.delete(p);
			}
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}

}
