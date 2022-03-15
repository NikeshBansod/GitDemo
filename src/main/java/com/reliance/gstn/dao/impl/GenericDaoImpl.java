package com.reliance.gstn.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.dao.GenericDao;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.util.GSTNUtil;

@Repository
public class GenericDaoImpl implements GenericDao {

	private static final Logger logger = Logger.getLogger(CustomerDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${check_gstin_query}")
	private String checkGstinQuery;
	
	@Override
	public boolean checkUserMappingValidation(String idsValuesToFetch,String getIdListQuery, Integer primId,String targetTable,String refColumn) throws Exception {
		logger.info("Entry");
		boolean isMappingValid = false;
		try {
			
			Session session = sessionFactory.getCurrentSession();
			List<Integer> ali = GSTNUtil.getListFromString(idsValuesToFetch);
			String placeHoder = GSTNUtil.getPlaceHoderString(ali);
			String queryString =  getIdListQuery;
			queryString=queryString.replace("targetTable", targetTable);
			queryString=queryString.replace("refColumn", refColumn);
			queryString=queryString.replace("refIdColumn", placeHoder);
			Query query = session.createQuery(queryString);
			
			for (int i = 0; i < ali.size(); i++) {
				query.setInteger(i, ali.get(i));
			}
			
		@SuppressWarnings("unchecked")
		List<UserMaster> list = (List<UserMaster>)query.list();
		
		if (!list.isEmpty()) {
			
				if(list.contains(primId)){
					isMappingValid = true;
				}else{
					isMappingValid = false;
				}
			
	    }
			} catch (Exception e) {
				logger.error("Error in:",e);
			}
		logger.info("Exit");
		
		return isMappingValid;
	}

	@Override
	public boolean validateGstin(String gstinId, Integer uId) throws Exception {
		logger.info("Entry");
		boolean isMappingValid = false;
		try {
			
			Session session = sessionFactory.getCurrentSession();
		
			Query query = session.createQuery(checkGstinQuery);
			query.setString("gstinNo", gstinId);
			query.setInteger("uId", uId);
			
			String gstin=null;
			if(!query.list().isEmpty()){
				
				gstin = (String) query.list().get(0);
			}
			
			if(gstin!=null && gstin.equals(gstin)){
				isMappingValid=true;
			}
			
			} catch (Exception e) {
				logger.error("Error in:",e);
			}
		logger.info("Exit");
		
		return isMappingValid;
	}

}
