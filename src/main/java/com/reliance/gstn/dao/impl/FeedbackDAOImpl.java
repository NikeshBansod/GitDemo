package com.reliance.gstn.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.dao.FeedbackDAO;
import com.reliance.gstn.model.FeedbackDetails;
import com.reliance.gstn.model.MasterDescDetails;
@Repository
public class FeedbackDAOImpl implements FeedbackDAO {

	private static final Logger logger = Logger.getLogger(FeedbackDAOImpl.class);
	

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${feedback_Details_List_Query}")
	private String feedbackDetailsListQuery;
	
	@Value("${feedback_Details_Query}")
	private String feedbackDetailsQuery;
	
	@Value("${master_Desc_Query}")
	private String masterDescQuery;
	
	@Value("${all_master_Desc_Query}")
	private String allmasterDescQuery;
		
	@Override
	public List<FeedbackDetails> listFeedbackDetails(Integer uId) {
		logger.info("Entry");
		
		List<FeedbackDetails> feedbackDetails = new ArrayList<FeedbackDetails>();  
		Session session = sessionFactory.getCurrentSession();
		try{
		Query query = session.createQuery(feedbackDetailsListQuery);
		query.setInteger("uId", uId);
		
		@SuppressWarnings("unchecked")
		List<FeedbackDetails>list =(List<FeedbackDetails>)query.list();
		feedbackDetails.addAll(list);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
	    logger.info("Exit");
	    
		return feedbackDetails;
	}

	@Override
	public List<com.reliance.gstn.model.FeedbackDetails> FeedbackDetails(
			Integer id, Integer uId) {
logger.info("Entry");
		
		List<FeedbackDetails> feedbackDetails = new ArrayList<FeedbackDetails>();
		Session session = sessionFactory.getCurrentSession();
		try{
		Query query = session.createQuery(feedbackDetailsQuery);
		query.setInteger("uId", uId);
		query.setInteger("id", id);
		
		@SuppressWarnings("unchecked")
		List<FeedbackDetails>list =(List<FeedbackDetails>)query.list();
		feedbackDetails.addAll(list);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
	    logger.info("Exit");
	    
		return feedbackDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MasterDescDetails> getMasterDesc(Integer masterDescDetails) {
        logger.info("Entry");
        
		Session session = sessionFactory.getCurrentSession();
		List<MasterDescDetails>DescDetails=new ArrayList<MasterDescDetails>();
		try{
		Query query = session.createQuery(masterDescQuery);
		query.setInteger("masterDescDetails", masterDescDetails);
		List<MasterDescDetails>list=(List<MasterDescDetails>)query.list();
		DescDetails.addAll(list);
		
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
	    logger.info("Exit");
	    
		return DescDetails;
	}

	@Override
	public List<MasterDescDetails> getAllMasterDesc() {
		// TODO Auto-generated method stub
		 logger.info("Entry");
		
		Session session = sessionFactory.getCurrentSession();
		List<MasterDescDetails>masterDesc=new ArrayList<MasterDescDetails>();
		try{
		Query query = session.createQuery(allmasterDescQuery);
		
		List<MasterDescDetails>list=(List<MasterDescDetails>)query.list();
		masterDesc.addAll(list);
		
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
	    logger.info("Exit");
	    
		return masterDesc;
	}
}


