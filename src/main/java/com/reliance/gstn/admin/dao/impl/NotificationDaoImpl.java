/**
 * 
 */
package com.reliance.gstn.admin.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.admin.dao.NotificationDao;
import com.reliance.gstn.admin.model.Notifications;
import com.reliance.gstn.util.GSTNConstants;

/**
 * @author Nikesh.Bansod
 *
 */
@Repository
public class NotificationDaoImpl implements NotificationDao {
	
	private static final Logger logger = Logger.getLogger(NotificationDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value(value = "${notification_list_query}")
	private String notificationListQuery;

	@Override
	public String addNotification(Notifications notifications) throws Exception{
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.openSession();
			notifications.setStatus("1");
			notifications.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			notifications.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			session.save(notifications);
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
	public List<Notifications> getNotificationsList() throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(notificationListQuery);
		
		@SuppressWarnings("unchecked")
		List<Notifications> customerDetailsList = (List<Notifications>)query.list();
		logger.info("Exit");
		return customerDetailsList;
	}

	@Override
	public Notifications getNotificationById(Integer id) throws Exception{
		logger.info("Entry");
		Notifications notification = null;
		try{
			Session session = sessionFactory.getCurrentSession();	
			notification = (Notifications) session.get(Notifications.class, id);
		}catch(Exception e){
			logger.error(e);
			throw e;
		}
		logger.info("Exit");
		return notification;
	}

	@Override
	public String editNotification(String notificatn, String userId, Integer id) {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Notifications notification = (Notifications) session.get(Notifications.class, id);
			notification.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			notification.setNotification(notificatn);
			notification.setUpdatedBy(userId);
			session.saveOrUpdate(notification);
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
	public String deleteNotification(Integer id,String userId) {
		String response = GSTNConstants.FAILURE;
		logger.info("Entry");
		try {
			Session session = sessionFactory.getCurrentSession();	
			Notifications notification = (Notifications) session.get(Notifications.class, id);
			notification.setStatus("0");
			notification.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			notification.setUpdatedBy(userId);
			if(null != notification){
				session.saveOrUpdate(notification);
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
