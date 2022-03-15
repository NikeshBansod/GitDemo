/**
 * 
 */
package com.reliance.gstn.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.admin.dao.NotificationDao;
import com.reliance.gstn.admin.model.Notifications;
import com.reliance.gstn.admin.service.NotificationService;

/**
 * @author Nikesh.Bansod
 *
 */
@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	private NotificationDao notificationDao;

	@Override
	@Transactional
	public String addNotification(Notifications notifications) throws Exception{
		// TODO Auto-generated method stub
		return notificationDao.addNotification(notifications);
	}

	@Override
	@Transactional
	public List<Notifications> getNotificationsList() throws Exception {
		// TODO Auto-generated method stub
		return notificationDao.getNotificationsList();
	}

	@Override
	@Transactional
	public Notifications getNotificationById(Integer id) throws Exception{
		// TODO Auto-generated method stub
		return  notificationDao.getNotificationById( id);
	}

	@Override
	@Transactional
	public String editNotification(String notification, String userId, Integer id) {
		// TODO Auto-generated method stub
		return notificationDao.editNotification( notification, userId, id);
	}

	@Override
	@Transactional
	public String deleteNotification(Integer id,String userId) {
		// TODO Auto-generated method stub
		return notificationDao.deleteNotification(id,userId);
	}

}
