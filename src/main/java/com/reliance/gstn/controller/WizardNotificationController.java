package com.reliance.gstn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.reliance.gstn.util.PageRedirectConstants;

@Controller
public class WizardNotificationController {
	
	@RequestMapping(value = "/wizardnotification", method = RequestMethod.GET)
	public String getnotification() {
		return PageRedirectConstants.WIZARD_NOTIFICATION_PAGE;
	}
}
