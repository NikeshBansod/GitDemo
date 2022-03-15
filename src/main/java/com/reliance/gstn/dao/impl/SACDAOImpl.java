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

import com.reliance.gstn.dao.SACDAO;
import com.reliance.gstn.model.SACCode;
//import com.reliance.gstn.util.LoggerUtil;

/**
 * @author Nikesh.Bansod
 *
 */
@Repository
public class SACDAOImpl implements SACDAO {
	
	private static final Logger logger = Logger.getLogger(SACDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${sac_desc_list_from_saccode}")
	private String getSacDesc;
	
	@Value("${sac_code_list_by_sacdesc}")
	private String getSACCodebySACDesc;
	
	@Value("${sac_code_by_stateid_and_saccode}")
	private String SACCodeListByStateIdAndSacCode;
	
	@Value("${igst_by_saccode}")
	private String IGSTBySacCode;

	@Override
	public List<Object[]> getSACCodeList(String parameter) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(getSacDesc);
		query.setString("sacCode", '%'+parameter+'%');
		query.setString("sacDescription", '%'+parameter+'%');
		//query.setString("parameter", parameter+"%");
		
		@SuppressWarnings("unchecked")
		List<Object[]> sacCodeList = (List<Object[]>)query.list();
		
		logger.info("Exit");
		return sacCodeList;
	}

	@Override
	public String getSacCodeBySacDescription(String sacCodeDescription) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(getSACCodebySACDesc);
		query.setString("sacCodeDescription", sacCodeDescription);
		String sacCode = "";
		
		@SuppressWarnings("unchecked")
		List<String> sacCodeList = (List<String>)query.list();
		
		if (!sacCodeList.isEmpty()) {
			sacCode = sacCodeList.get(0);
		}
		
		logger.info("Exit");
		return sacCode;
	}

	@Override
	public SACCode getSACCodeData(String sacCode, Integer deliveryStateId) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(SACCodeListByStateIdAndSacCode);
		query.setString("sacCode", sacCode);
		query.setInteger("stateId", deliveryStateId);
		
		SACCode sac = null;
		@SuppressWarnings("unchecked")
		List<SACCode> sacCodeList = (List<SACCode>)query.list();
		if (!sacCodeList.isEmpty()) {
			sac = sacCodeList.get(0);
		}
		
		logger.info("Exit");
		return sac;
	}

	@Override
	public SACCode getIGSTValueBySacCode(String sacCode, String sacDescription) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(IGSTBySacCode);
		query.setString("sacCode", sacCode);
		query.setString("sacDescription", sacDescription+"%");
		
		
		SACCode sacCodeDetails = null;
		@SuppressWarnings("unchecked")
		List<SACCode> sacCodeList = (List<SACCode>)query.list();
		if (!sacCodeList.isEmpty()) {
			sacCodeDetails = (SACCode)sacCodeList.get(0);
		}
		
		logger.info("Exit");
		return sacCodeDetails;
	}

	@Override
	public SACCode getSACCodeById(Integer sacCodePkId) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();		
		SACCode msc = (SACCode) session.get(SACCode.class, sacCodePkId);
		logger.info("Exit");
		return msc;
	}

}
