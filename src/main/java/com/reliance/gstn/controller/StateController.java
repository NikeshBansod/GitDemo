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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.model.State;
import com.reliance.gstn.service.StateService;
//import com.reliance.gstn.util.LoggerUtil;

/**
 * @author Nikesh.Bansod
 *
 */

@Controller
public class StateController {
	
	private static final Logger logger = Logger.getLogger(StateController.class);
	
	@Autowired
	StateService stateService;
	
	@ModelAttribute("stateList")
	public List<State> stateList(){
		logger.info("Entry");
		List<State> stateList = new ArrayList<State>();
		try {
			stateList = stateService.listState();
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return stateList;
	}
	
	@RequestMapping(value="/getStatesList",method=RequestMethod.POST)
	public @ResponseBody String getStatesList(Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");
		List<State> stateList = new ArrayList<State>();
		try {
			stateList = stateService.listState();
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(stateList);
	}
	
	@RequestMapping(value="/getStateCodeByStateName",method=RequestMethod.POST)
	public @ResponseBody String getStateCodeByStateName(@RequestParam("stateName") String stateName, HttpServletRequest httpRequest) {
		logger.info("Entry");
		State state=null;
		try {
			state = stateService.getStateByStateName(stateName);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(state);
		
	}
	
	@RequestMapping(value="/getStateByGstinNumber",method=RequestMethod.POST)
	public @ResponseBody String postPincodeById(@RequestParam("id") String gstinNumber, Model model) {
		logger.info("Entry");
		String stateId = firstTwoDigits(gstinNumber);
		List<State> stateList = new ArrayList<State>();
		try {
			stateList = stateService.getStateByStateId(Integer.valueOf(stateId));
		} catch (NumberFormatException e) {
			logger.error("Error in:",e);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(stateList);
	}
	
	public String firstTwoDigits(String str) {
	    return str.length() < 2 ? str : str.substring(0, 2);
	}

}
