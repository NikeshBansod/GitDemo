/**
 * 
 */
package com.reliance.gstn.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.reliance.gstn.model.FeedbackDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Rupali J
 *
 */

@Controller
public class DashboardDetailsController {
	
	private static final Logger logger = Logger.getLogger(DashboardDetailsController.class);
		
	/*
	@Value("${get_total_invoice_count}")
	private String feedbackDetailsSuccessful;
	
	@Value("${FEEDBACK_DETAILS_FAILURE}")
	private String feedbackDetailsFailure;
		
		
	@RequestMapping(value = "/addDashboardDetails", method = RequestMethod.GET)
	public String addFeedbackDetails(Model model) {
		return PageRedirectConstants.MANAGE_DASHBOARD_DETAILS_LIST_PAGE;
	}
	
	@RequestMapping(value = "/getDashboardDetails", method = RequestMethod.POST)
	public String getDashboardDetails(Model model,HttpServletRequest request) {
		
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
	String financialPeriod=request.getParameter("financialPeriod");
	
	String errorMessage = "Selected GSTIN is not valid. Please Update your GSTIN in GSTIN master and then try to upload the Data";
		return errorMessage;
	}*/
	
}
