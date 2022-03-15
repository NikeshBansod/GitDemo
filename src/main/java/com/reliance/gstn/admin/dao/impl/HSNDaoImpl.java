package com.reliance.gstn.admin.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.admin.dao.HSNDao;
import com.reliance.gstn.admin.model.HSNDetails;
import com.reliance.gstn.util.GSTNConstants;


@Repository
public class HSNDaoImpl implements HSNDao {

	private static final Logger logger = Logger.getLogger(HSNDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${hsn_desc_list_from_hsncode}")
	private String getHsnDesc;
	
	@Value("${hsn_details_list_query}")
	private String HSNDetailsListQuery;
	
	@Value("${hsn_details_list_limit_100_query}")
	private String HSNDetailsListLimit1000Query;
	
	@Value("${igst_by_hsnCode}")
	private String IGSTByHsnCode;
	
	@Value("${hsn_code_list_by_hsndesc}")
	private String hsnDetailsByHsnDesc;
	
	@Value("${hsn_by_code_and_state}")
	private String hsnDetailsByCodeAndStateId;
	
	@Override
	public String addHSNDetails(HSNDetails HSNDetails) throws Exception{
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.persist(HSNDetails);
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
	public List<HSNDetails> listHSNDetails(Integer uId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(HSNDetailsListQuery);
		
		@SuppressWarnings("unchecked")
		List<HSNDetails> HSNDetailsList = query.list();
		return HSNDetailsList;
	}

	
	@Override
	public HSNDetails getHSNDetailsById(Integer id) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();		
		HSNDetails msc = (HSNDetails) session.get(HSNDetails.class, id);
		logger.info("Exit");
		return msc;
	}

	@Override
	public String updateHSNDetails(HSNDetails HSNDetails) throws Exception{
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
		
			session.saveOrUpdate(HSNDetails);
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		return response;
	}

	@Override
	public String removeHSNDetails(Integer id) throws Exception{
		String response = GSTNConstants.FAILURE;
		logger.info("Entry");
		try {
			Session session = sessionFactory.getCurrentSession();	
			HSNDetails p = (HSNDetails) session.get(HSNDetails.class, id);
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

	@Override
	public List<Object[]> getHSNCodeList(String parameter) {
		logger.info("Entry");
		
		
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(getHsnDesc);
		query.setString("hsnCode", '%'+parameter+'%');
		query.setString("hsnDesc", '%'+parameter+'%');
		//query.setString("parameter", parameter+"%");
		
		@SuppressWarnings("unchecked")
		List<Object[]> hsnCodeList = query.list();
		
		logger.info("Exit");
		return hsnCodeList;
	}

	@Override
	public String getHsnCodeByHsnDescription(String hsnCodeDescription) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hsnDetailsByHsnDesc);
		query.setString("hsnCodeDescription", hsnCodeDescription);
		String hsnCode = null;
		
		@SuppressWarnings("unchecked")
		List<String> sacCodeList = query.list();
		
		if ((sacCodeList.size() > 0)) {
			hsnCode = sacCodeList.get(0);
		}
		
		logger.info("Exit");
		return hsnCode;
	}

	@Override
	public HSNDetails getHSNCodeData(String hsnCode, Integer stateId) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hsnDetailsByCodeAndStateId);
		query.setString("hsnCode", hsnCode);
		query.setInteger("stateId", stateId);
		
		HSNDetails hsn = null;
		@SuppressWarnings("unchecked")
		List<HSNDetails> hsnCodeList = query.list();
		if ((hsnCodeList.size() > 0)) {
			hsn = hsnCodeList.get(0);
		}
		
		logger.info("Exit");
		return hsn;
	}

	@Override
	public HSNDetails getIGSTValueByHsnCode(String hsnCode, String hsnDescription) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(IGSTByHsnCode);
		query.setString("hsnCode", hsnCode);
		query.setString("hsnDesc", hsnDescription);
		
		
		HSNDetails hsnDetails = null;
		@SuppressWarnings("unchecked")
		List<HSNDetails> hsnCodeList = (List<HSNDetails>)query.list();
		if (!hsnCodeList.isEmpty()) {
			hsnDetails = (HSNDetails)hsnCodeList.get(0);
		}
		
		logger.info("Exit");
		return hsnDetails;
	}

	@Override
	public List<HSNDetails> getHSNCodeList() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(HSNDetailsListLimit1000Query);
		query.setFirstResult(0).setMaxResults(10);
		
		@SuppressWarnings("unchecked")
		List<HSNDetails> HSNDetailsList = query.list();
		return HSNDetailsList;
	}
}
