package com.reliance.gstn.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.dao.wizardFeedbackDao;
import com.reliance.gstn.model.FeedbackDetails;
import com.reliance.gstn.service.wizardFeedbackDetailService;
import com.reliance.gstn.util.GSTNConstants;

@Service
public class wizardFeedbackDetailServiceImpl implements wizardFeedbackDetailService {

	private static final Logger logger = Logger.getLogger(FeedbackDetailServiceImpl.class);
	
	
	
	@Autowired
	wizardFeedbackDao feedbackDao;
	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	@Override
	@Transactional
	public String addFeedback(FeedbackDetails feedbackDetails){
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		String response = GSTNConstants.FAILURE;
		try {
		//	Session session = sessionFactory.openSession();
			session.save(feedbackDetails);
			response = GSTNConstants.SUCCESS;
		} catch(ConstraintViolationException  e){
			logger.error("Error in:",e);
			throw e;
		}catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
		}


	@Override
	@Transactional
	public List<FeedbackDetails> listFeedbackDetails(Integer uId)throws Exception {
		// TODO Auto-generated method stub
		return feedbackDao.listFeedbackDetails(uId);
	}


	@Override
	@Transactional
	public List<com.reliance.gstn.model.FeedbackDetails> FeedbackDetails(
			Integer id, Integer uId) throws Exception {
		// TODO Auto-generated method stub
		return feedbackDao.FeedbackDetails(id,uId);
	}


	
	}
		
	
	



