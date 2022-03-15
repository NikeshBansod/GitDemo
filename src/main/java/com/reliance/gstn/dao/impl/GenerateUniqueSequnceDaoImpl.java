package com.reliance.gstn.dao.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.dao.GenerateUniqueSequnceDao;
import com.reliance.gstn.util.GSTNUtil;

@Repository
public class GenerateUniqueSequnceDaoImpl implements GenerateUniqueSequnceDao{
	
	private static final Logger logger = Logger.getLogger(GenerateUniqueSequnceDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private HttpSession sessionSeq;
	
	@Value("${get_maxunique_docsequence}")
	private String getDocSequenceByOrgid;
	

	@Value("${get_gst_max_unique_docsequence}")
	private String getMaxUniqueDocSequence;
	
	@Value("${get_initial_unique_docsequence}")
	private String getInitialUniqueDocSequence;
	
	@Value("${get_uniqueSequenece_doc}")
	private String getUniqueDocSequence;

	@Override
	public String getLatestDocumentNumber(String pattern, Integer orgUId) {

		logger.info("Entry");
		String latestdocNo = null;
		Session session = sessionFactory.getCurrentSession();
		String count = "01";
		
		Query query = session.createSQLQuery(getDocSequenceByOrgid);
		query.setString("pattern", pattern+"%");
		query.setInteger("orgUId", orgUId);
		
		@SuppressWarnings("unchecked")
		List<String> viewPoDetailsList = query.list();
		
		
		if (!viewPoDetailsList.isEmpty()) {
			
				latestdocNo = GSTNUtil.getMaxOfDocumentNumber(viewPoDetailsList); 
				count = GSTNUtil.incrementDocCount(latestdocNo);
				latestdocNo=pattern+count;
		
		}
		else{
			latestdocNo=pattern+"01";
		}
		
		
		
		logger.info("Exit");
		return latestdocNo+"";
	
	}
	
	
	@Override
	public synchronized List<String> getDocSequence(String pattern, Integer orgUId,Integer size)
			throws Exception {
		logger.info("Entry");
		List<String> viewSequenceList1 = null;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(getMaxUniqueDocSequence);
		query.setString("pattern", pattern+"%");
		query.setInteger("orgUId", orgUId);
		query.setInteger("size", size);
		
		List<String> viewSequenceList = query.list();
		
		if (viewSequenceList != null && !viewSequenceList.isEmpty()){
			sessionSeq.setAttribute("id", viewSequenceList.get(0));
			return viewSequenceList;
		}
			
		else {
			//viewSequenceList1=getInitialDocSequence(size);
			//viewSequenceList.addAll(viewSequenceList1);
			sessionSeq.setAttribute("id",01);
			
			System.out.println("viewSequenceList:"+viewSequenceList);
			logger.info("product not found for this store");
		}
		logger.info("Exit");
		return viewSequenceList;
	}
	
	
	@Override
	public synchronized List<String> getInitialDocSequence(Integer size)
			throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(getInitialUniqueDocSequence);
		query.setInteger("size", size);
		List<String> viewSequenceList = query.list();
		return viewSequenceList;
	}

	
	@Override
	public synchronized Integer getDocSequenceId(String pattern, Integer orgUId,Integer size){
		logger.info("Entry");
		Integer initialId=0;
		Integer uniqueSequence=0;
		try{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(getMaxUniqueDocSequence);
		query.setString("pattern", pattern+"%");
		query.setInteger("orgUId", orgUId);
		query.setInteger("size", size);
		
		uniqueSequence = (Integer) query.uniqueResult();
		
		if (uniqueSequence != null && uniqueSequence!=0){
			return uniqueSequence;
		}
		else{
			return initialId;
		}
		}
		catch(Exception e){
			logger.error(e);
		}
		logger.info("Exit");
		return uniqueSequence;
	}
	
	
	public String getDocSequenceSession(Integer Id) throws Exception{
		 Session session = sessionFactory.getCurrentSession();
			Query query = session.createSQLQuery(getUniqueDocSequence);
			query.setInteger("id", Id);
			String viewSequencenumber = (String) query.uniqueResult();
			if (viewSequencenumber != null && !viewSequencenumber.isEmpty()){
				return viewSequencenumber;
			}
		return viewSequencenumber;
	}
}
