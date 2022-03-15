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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.admin.model.AdvolCessRateDetails;
import com.reliance.gstn.admin.model.NonAdvolCessRateDetails;
import com.reliance.gstn.model.UnitOfMeasurement;
import com.reliance.gstn.service.AdvolAndNonAdvolCessService;
import com.reliance.gstn.service.UnitOfMeasurementService;

/**
 * @author Rupali.Jagdale
 *
 */
@Controller
public class CessListController {
	
	private static final Logger logger = Logger.getLogger(CessListController.class);
	
	@Autowired
	public AdvolAndNonAdvolCessService advolAndNonAdvolCessService;
	
	  @RequestMapping(value="/getAdvolCessRate",method=RequestMethod.POST)
	  public @ResponseBody String getAdvolCessRate(Model model,HttpServletRequest httpRequest) {
		  logger.info("Entry");
		  List<AdvolCessRateDetails> advolCessRateList = new ArrayList<AdvolCessRateDetails>();
		  try {
			  advolCessRateList =  advolAndNonAdvolCessService.getAdvolCessRateDetailList();
		  } catch (Exception e) {
			  logger.error("Error in:",e);
		  }
		  logger.info("Exit");
		  return new Gson().toJson(advolCessRateList);
	  }
	  
	  
	  @RequestMapping(value="/getNonAdvolCessRate",method=RequestMethod.POST)
	  public @ResponseBody String getNonAdvolCessRate(Model model,HttpServletRequest httpRequest) {
		  logger.info("Entry");
		  List<NonAdvolCessRateDetails> nonAdvolCessRateList = new ArrayList<NonAdvolCessRateDetails>();
		  try {
			  nonAdvolCessRateList =  advolAndNonAdvolCessService.getNonAdvolCessRateDetailList();
		  } catch (Exception e) {
			  logger.error("Error in:",e);
		  }
		  logger.info("Exit");
		  return new Gson().toJson(nonAdvolCessRateList);
	  }
	

}
