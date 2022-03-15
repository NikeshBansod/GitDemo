/**
 * 
 */
package com.reliance.gstn.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.admin.dao.AdminUserMasterDao;
import com.reliance.gstn.admin.model.AdminFeedbackDetails;
import com.reliance.gstn.admin.model.AdminUserMaster;

/**
 * @author Nikesh.Bansod
 *
 */
@Repository
public class AdminUserMasterDaoImpl implements AdminUserMasterDao {
	
	private static final Logger logger = Logger.getLogger(AdminUserMasterDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${admin_login_query}")
	private String adminLoginQuery;
	
	@Value("${get_feedback_details}")
	private String getFeedbackDetails;

	@Override
	public AdminUserMaster getUserDetails(String userId, String password) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		AdminUserMaster user = null;
		Query query = session.createQuery(adminLoginQuery);
		query.setString("userId", userId);
		query.setString("password", password);
		
		@SuppressWarnings("unchecked")
		List<AdminUserMaster> list = (List<AdminUserMaster>)query.list();
		
		if (!list.isEmpty()) {
			user = list.get(0);
			
		}
		logger.info("Exit");
		return user;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AdminFeedbackDetails> getUploadHistory(Integer masterDesc){
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		List<Object[]> objDetailList = new ArrayList<Object[]>();
		List<AdminFeedbackDetails> feedbackDetailList = new ArrayList<AdminFeedbackDetails>();
		Query query = session.createQuery(getFeedbackDetails);
		query.setInteger("masterDesc", masterDesc);
		objDetailList = (List<Object[]>)query.list();
		for(Object[] feedbackObj : objDetailList){
			AdminFeedbackDetails feedbackDetails = new AdminFeedbackDetails();
			feedbackDetails.setUserId(feedbackObj[0].toString());
			feedbackDetails.setOrgniazationName(feedbackObj[1].toString());
			feedbackDetails.setPan(feedbackObj[2].toString());
			feedbackDetails.setFeedbackDesc(feedbackObj[3].toString());
			feedbackDetails.setCreatedDate(java.sql.Timestamp.valueOf(feedbackObj[4].toString()));
			feedbackDetailList.add(feedbackDetails);
		}
		
		
		return feedbackDetailList;
		 
	}

}
