/**
 * 
 */
package com.reliance.gstn.admin.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.admin.model.CnDnReason;
import com.reliance.gstn.admin.service.CnDnReasonService;

/**
 * @author Nikesh.Bansod
 *
 */
@Controller
public class CnDnReasonController {
	
	private static final Logger logger = Logger.getLogger(CnDnReasonController.class);
	
	@Autowired
	CnDnReasonService cnDnReasonService;
	
	@RequestMapping(value = "/getCNDNReasonList", method = RequestMethod.POST)
	public @ResponseBody String getCNDNReasonList() {
		System.out.println("here");
		logger.info("Entry");
		List<CnDnReason> cndnResonList = cnDnReasonService.listCnDnReason();
		logger.info("Exit");
		return new Gson().toJson(cndnResonList);
	}
}
