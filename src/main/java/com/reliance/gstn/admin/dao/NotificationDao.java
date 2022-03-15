/**
 * 
 */
package com.reliance.gstn.admin.dao;

import java.util.List;

import com.reliance.gstn.admin.model.Notifications;

/**
 * @author Nikesh.Bansod
 *
 */
public interface NotificationDao {

	String addNotification(Notifications notifications) throws Exception;

	List<Notifications> getNotificationsList() throws Exception;

	Notifications getNotificationById(Integer id) throws Exception;

	String editNotification(String notification, String userId, Integer id);

	String deleteNotification(Integer id, String userId);

}
