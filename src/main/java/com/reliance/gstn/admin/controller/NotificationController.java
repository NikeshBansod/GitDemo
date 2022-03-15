/**
 * 
 */
package com.reliance.gstn.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.admin.model.AdminLoginMaster;
import com.reliance.gstn.admin.model.Notifications;
import com.reliance.gstn.admin.service.NotificationService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Nikesh.Bansod
 *
 */
@SuppressWarnings("unused")
@Controller
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	
	@Value("${NOTIFICATION_ADD_SUCESS}")
	private String notificationAddSuccessful;
	
	@Value("${NOTIFICATION_ADD_FAILURE}")
	private String notificationAddFailure;
	
	@Value("${NOTIFICATION_EDIT_SUCESS}")
	private String notificationEditSuccessful;
	
	@Value("${NOTIFICATION_EDIT_FAILURE}")
	private String notificationEditFailure;
	
	@Value("${NOTIFICATION_DELETE_SUCESS}")
	private String notificationDeletionSuccessful;
	
	@Value("${NOTIFICATION_DELETE_FAILURE}")
	private String notificationDeletionFailed;
	
	private static final Logger logger = Logger.getLogger(NotificationController.class);
	
	@RequestMapping(value = {"/adminnotifications","/idt/inotifications"}, method = RequestMethod.GET)
	public String listAdminNotifications(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		
		return PageRedirectConstants.ADMIN_NOTIFICATIONS_LIST_PAGE;
	}
	
	@RequestMapping(value = {"/adminaddnotifications","/idt/iAddnotifications"}, method = RequestMethod.GET)
	public String addNotification(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		
		return PageRedirectConstants.NOTIFICATIONS_ADD_PAGE;
	}
	
	@RequestMapping(value = {"/adminsubmitnotifications","/idt/iSubmitnotifications"}, method = RequestMethod.POST)
	public String addNotificationPost(Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		String notification = httpRequest.getParameter("notifyBody");
		AdminLoginMaster adminLoginMaster = (AdminLoginMaster)LoginUtil.getAdminLoginUser(httpRequest);
		Notifications notifications = new Notifications();
		try{
			notifications.setCreatedBy(adminLoginMaster.getUserId());
			notifications.setNotification(notification);
			notifications.setUpdatedBy(adminLoginMaster.getUserId());
			response = notificationService.addNotification(notifications);
			model.addAttribute(GSTNConstants.RESPONSE, notificationAddSuccessful);
		}catch(Exception e){
			logger.error("Error in "+e);
			model.addAttribute(GSTNConstants.RESPONSE, notificationAddFailure);
		}
		//return PageRedirectConstants.NOTIFICATIONS_LIST_PAGE;
		logger.info("Exit");
		return PageRedirectConstants.ADMIN_NOTIFICATIONS_LIST_PAGE;
	}
	
	@RequestMapping(value={"/getNotificationList","/getAdminNotificationList","/idt/getINotificationList"}, method = RequestMethod.POST)
	public @ResponseBody String getNotificationsList(HttpServletRequest httpRequest){
		logger.info("Entry");	
		
		String json="";
		try {
			
			List<Notifications> lst = notificationService.getNotificationsList();
			json = new Gson().toJson(lst);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return json;
	}
	
	
	
	@RequestMapping(value = "/notifications", method = RequestMethod.GET)
	public String listNotifications(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		
		return PageRedirectConstants.NOTIFICATIONS_LIST_PAGE;
	}
	
	
	@RequestMapping(value = "/idt/editNotification", method = RequestMethod.POST)
	public String editCustomerDetails(@RequestParam("id") Integer id, Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");	
		System.out.println("editNotification id : "+id);
		Notifications notification = null;
		try {
			notification = notificationService.getNotificationById(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:",e);
		}
		model.addAttribute("notificationObj", notification);
		logger.info("Exit");
		return PageRedirectConstants.ADMIN_NOTIFICATIONS_EDIT_PAGE;
	}
	
	
	@RequestMapping(value = {"/admineditnotifications","/idt/iEditnotifications"}, method = RequestMethod.POST)
	public String updateNotificationsPost(Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		AdminLoginMaster adminLoginMaster = (AdminLoginMaster)LoginUtil.getAdminLoginUser(httpRequest);
		try{
			String notification = httpRequest.getParameter("notifyBody");
			Integer id = Integer.parseInt(httpRequest.getParameter("notificationNo"));
			
			response = notificationService.editNotification(notification,adminLoginMaster.getUserId(),id);
			model.addAttribute(GSTNConstants.RESPONSE, notificationEditSuccessful);
		}catch(Exception e){
			logger.error("Error in "+e);
			model.addAttribute(GSTNConstants.RESPONSE, notificationEditFailure);
		}
		//return PageRedirectConstants.NOTIFICATIONS_LIST_PAGE;
		logger.info("Exit");
		return PageRedirectConstants.ADMIN_NOTIFICATIONS_LIST_PAGE;
	}
	
	@RequestMapping(value={"/admindeletenotification","/idt/iDeletenotification"},method = RequestMethod.POST)
	public String deleteNotification(@RequestParam Integer id, Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = "";
		String pageRedirect = PageRedirectConstants.ADMIN_NOTIFICATIONS_LIST_PAGE;
		try {
			AdminLoginMaster adminLoginMaster = (AdminLoginMaster)LoginUtil.getAdminLoginUser(httpRequest);
			response = notificationService.deleteNotification(id,adminLoginMaster.getUserId());
			model.addAttribute(GSTNConstants.RESPONSE, notificationDeletionSuccessful);
		
		} catch (Exception e) {
			model.addAttribute(GSTNConstants.RESPONSE, notificationDeletionFailed);
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return pageRedirect;
	}

}
