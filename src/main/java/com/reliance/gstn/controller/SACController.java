/**
 * 
 */
package com.reliance.gstn.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.model.SACCode;
import com.reliance.gstn.service.SACService;

/**
 * @author Nikesh.Bansod
 *
 */

@Controller
public class SACController {
	
	private static final Logger logger = Logger.getLogger(SACController.class);

	@Autowired
	SACService sacService;
	
	@RequestMapping(value = "/getSacCodeList", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody List<String> getTechList(@RequestParam("term") String query) {
		List<String> sacCodeList = getSACCodeList(query);

		return sacCodeList;
	}

	private List<String> getSACCodeList(String query) {
		logger.info("Entry");
		String sacCode = null;
		String sacDesc = null;
	    query = query.toLowerCase();
	    List<String> matched = new ArrayList<String>();
	    List<Object[]> distinctSacCodeList = sacService.getSACCodeList(query);
	       for(int i=0; i < distinctSacCodeList.size(); i++) {
	        	Object[] obj = distinctSacCodeList.get(i);
	        	sacCode = (String)obj[0];
	        	sacDesc = (String)obj[1];
	        	 matched.add("["+sacCode+"] - "+sacDesc);
	           
	       }
	     logger.info("Exit");   
	     return matched;
	}
	
	@RequestMapping(value="/getSacCodeIdByDescription/{description}",method=RequestMethod.GET)
	public @ResponseBody String getSacCodeIdByDescription(@PathVariable("description") String sacCodeDescription, Model model) {
		logger.info("Entry");
		String sacCode = sacService.getSacCodeBySacDescription(sacCodeDescription);
		logger.info("Exit");
		return new Gson().toJson(sacCode);
		
	}
	
	@RequestMapping(value="/getIGSTValueBySacCode",method=RequestMethod.POST)
	public @ResponseBody String getIGSTValueBySacCode(@RequestParam("sacCode") String sacCode, @RequestParam("sacDescription") String sacDescription, HttpServletRequest httpRequest) {
		logger.info("Entry");
		SACCode sacCodeDetails = null;
		try {
		
			sacCodeDetails = sacService.getIGSTValueBySacCode(sacCode,sacDescription);
		}catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(sacCodeDetails);
		
	}
	
 

	
}
