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
import com.reliance.gstn.model.UnitOfMeasurement;
import com.reliance.gstn.service.UnitOfMeasurementService;

/**
 * @author Dibyendu.Mukherjee
 *
 */
@Controller
public class UnitOfMeasurementController {
	
	private static final Logger logger = Logger.getLogger(UnitOfMeasurementController.class);
	
	@Autowired
	public UnitOfMeasurementService unitOfMeasurementService;
	
	/*  @RequestMapping(value="/getUnitOfMeasurement",method=RequestMethod.POST)
	  public @ResponseBody String getUnitOfMeasurement(Model model,HttpServletRequest httpRequest) {
		  logger.info("Entry");
		  List<UnitOfMeasurement> unitOfMeasurementList = new ArrayList<UnitOfMeasurement>();
		  try {
				  unitOfMeasurementList =  unitOfMeasurementService.getUnitOfMeasurement();
		  } catch (Exception e) {
			  logger.error("Error in:",e);
		  }
		  logger.info("Exit");
		  return new Gson().toJson(unitOfMeasurementList);
	  }*/
	
	@RequestMapping(value="/getUnitOfMeasurement",method=RequestMethod.POST)
	  public @ResponseBody String getUnitOfMeasurement(Model model,HttpServletRequest httpRequest) {
		  logger.info("Entry");
		  List<UnitOfMeasurement> unitOfMeasurementList = new ArrayList<UnitOfMeasurement>();
		  try {
				  unitOfMeasurementList =  unitOfMeasurementService.getUnitOfMeasurement();
		  } catch (Exception e) {
			  logger.error("Error in:",e);
		  }
		  logger.info("Exit");
		  return new Gson().toJson(unitOfMeasurementList);
	  }
	
	

}
