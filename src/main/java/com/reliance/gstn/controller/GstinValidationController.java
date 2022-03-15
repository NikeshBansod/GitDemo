package com.reliance.gstn.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reliance.gstn.service.GstinValidationService;

/**
 * @author Vivek2.Dubey
 *
 */
@Controller
public class GstinValidationController {
	
	private static final Logger logger = Logger.getLogger(GstinValidationController.class);
	
	@Autowired
	GstinValidationService gstinValidationService;

	@RequestMapping(value="/isValidGstin",method=RequestMethod.GET)
	public @ResponseBody String isValidGstinNo(HttpServletRequest request){
		logger.info("Entry:");
		String result="";
		String gstinNo = request.getParameter("gstinNo");
		result = gstinValidationService.isValidGstin(gstinNo);
		logger.info("Exit:");
		return result;
	}
}
