/**
 * 
 */
package com.reliance.gstn.admin.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.admin.model.HSNDetails;
import com.reliance.gstn.admin.service.HSNService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Rupali J
 *
 */
@Controller
public class HSNController {
	
	private static final Logger logger = Logger.getLogger(HSNController.class);
	
	@Autowired
	public HSNService HSNService;
	
	@Value("${HSN_DETAILS_SUCESS}")
	private String HSNDetailsSuccessful;
	
	@Value("${HSN_DETAILS_FAILURE}")
	private String HSNDetailsFailure;
	
	@Value("${HSN_UPDATION_SUCESS}")
	private String HSNUpdationSuccessful;
	
	@Value("${HSN_UPDATION_FAILURE}")
	private String HSNUpdationFailure;
	
	@Value("${HSN_DELETION_SUCESS}")
	private String HSNDeletionSuccessful;
	
	@Value("${HSN_DELETION_FAILURE}")
	private String HSNDeletionFailure;
	
	@ModelAttribute("HSNDetails")
	public HSNDetails construct(){
		return new HSNDetails();
	}
	
	@RequestMapping(value = "/idt/hsnDetails", method = RequestMethod.GET)
	public String HSNDetailsViewPage(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		return PageRedirectConstants.HSN_DETAILS_LIST_PAGE;
	}

	@RequestMapping(value = "/testHSN", method = RequestMethod.GET)
	public @ResponseBody String testHSN(Model model) {
		System.out.println("Here");
		HSNDetails hsnMaster = getDummyObject();
		String response = null;
		try {
			response = HSNService.addHSNDetails(hsnMaster);
		} catch (Exception e) {
			e.printStackTrace();
			response = "Error while adding HSN data";
		}
		return response;
		
	}

	private HSNDetails getDummyObject() {
		HSNDetails hsnDetail =  new HSNDetails();
		return hsnDetail;
		
	}
	
	@RequestMapping(value = "/idt/addHSNDetails", method = RequestMethod.GET)
	public String addHSNDetails(Model model) {
		return PageRedirectConstants.ADD_HSN_DETAILS_PAGE;
	}
	
	@RequestMapping(value="/idt/addHSNDetails",method=RequestMethod.POST)
	public String addHSNDetailsPost(@Valid @ModelAttribute("HSNDetails") HSNDetails HSNDetails, BindingResult result,Model model,  HttpSession httpSession,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = null;
		String pageRedirect = PageRedirectConstants.HSN_DETAILS_LIST_PAGE;
		if (!result.hasErrors()){
			try {
				HSNDetails.setSgstUgst((HSNDetails.getIgst()/2));
				HSNDetails.setCgst((HSNDetails.getIgst()/2));
				response = HSNService.addHSNDetails(HSNDetails);
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, HSNDetailsSuccessful);
					HSNDetails = new HSNDetails();
					
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, HSNDetailsFailure);   
				}
			} catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE, HSNDetailsFailure);
				logger.error("Error in:",e);
			}
			
		}
		model.addAttribute("HSNDetails", HSNDetails);
		logger.info("Exit");
		return  pageRedirect;
	}
	
	@RequestMapping(value = "/idt/editHSNDetails",method=RequestMethod.POST)
	public String HSNDetailsEditPage(@ModelAttribute HSNDetails HSNDetails, Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");	
		System.out.println("Here 2");
		HSNDetails HSNDetailsObj = HSNService.getHSNDetailsById(HSNDetails.getId());
		model.addAttribute("HSNDetailsObj", HSNDetailsObj);
		
		logger.info("Exit");
		return PageRedirectConstants.HSN_DETAILS_EDIT_PAGE;
	}
	
	@RequestMapping(value="/idt/updateHSNDetails",method=RequestMethod.POST)
	public String updateHSNDetails(@Valid @ModelAttribute HSNDetails HSNDetails ,BindingResult result,  HttpServletRequest httpRequest,HttpSession httpSession, Model model) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.HSN_DETAILS_EDIT_PAGE;
		String response = null;
		
		if (!result.hasErrors()){
			try {
				HSNDetails.setSgstUgst((HSNDetails.getIgst()/2));
				HSNDetails.setCgst((HSNDetails.getIgst()/2));
				response = HSNService.updateHSNDetails(HSNDetails);
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, HSNUpdationSuccessful);
					pageRedirect = PageRedirectConstants.HSN_DETAILS_LIST_PAGE;
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, HSNUpdationFailure);
				}
			} catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE, HSNUpdationFailure);
			}
		}else{
			System.out.println("Error occured"+result.getAllErrors());
		}
		
		logger.info("Exit");
		return  pageRedirect;
	}
	
	@RequestMapping(value="/idt/deleteHSNDetails",method=RequestMethod.POST)
	public String deleteHSNDetails(@RequestParam Integer id, Model model) {
		logger.info("Entry");	
		System.out.println("id : "+id);
		String response = null;
		try {
			response = HSNService.removeHSNDetails(id);
			if(response.equals(GSTNConstants.SUCCESS)){
				model.addAttribute(GSTNConstants.RESPONSE, HSNDeletionSuccessful);
			}else{
				model.addAttribute(GSTNConstants.RESPONSE, HSNDeletionFailure);
			}
		} catch (Exception e) {
			model.addAttribute(GSTNConstants.RESPONSE, HSNDeletionFailure);
		}
		
		logger.info("Exit");
		return PageRedirectConstants.HSN_DETAILS_LIST_PAGE;
	}
	
	@RequestMapping(value = "/getHSNCodeList", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody List<String> getHSNDetailsCodeList(@RequestParam("term") String query) {
		logger.info("Entry");
		List<String> sacCodeList = getHSNCodeList(query);
		logger.info("Entry");
		return sacCodeList;
	}

	
		private List<String> getHSNCodeList(String query) {
			 String hsnCode = null;
			 String hsnDesc = null;
		     query = query.toLowerCase();
		     List<String> matched = new ArrayList<String>();
		     List<Object[]> distinctHSNCodeList = HSNService.getHSNCodeList(query);
		        for(int i=0; i < distinctHSNCodeList.size(); i++) {
		         	Object[] obj = distinctHSNCodeList.get(i);
		         	hsnCode = (String)obj[0];
		         	hsnDesc = (String)obj[1];
		         	 matched.add("["+hsnCode+"] - "+hsnDesc);
		         	
		        }
		     return matched;
		} 

	
	
	

			/*private List<String> getHSNCodeList(String query) {
				     query = query.toLowerCase();
				     return HSNService.getHSNCodeListNew(query);
				}*/ 

	 			
	        
	    	 
	 
	
	@RequestMapping(value="/getHSNCodeByDescription/{description}",method=RequestMethod.GET)
	public @ResponseBody String getHSNCodeByDescription(@PathVariable("description") String hsnCodeDescription, Model model) {
		
		System.out.println("hsnCodeDescription:"+hsnCodeDescription);
		String hsnCode = HSNService.getHsnCodeByHsnDescription(hsnCodeDescription);
		
		return new Gson().toJson(hsnCode);
		
	}
	
	@RequestMapping(value="/getIGSTValueByHsnCode",method=RequestMethod.POST)
	public @ResponseBody String postIGSTValueByHsnCode(@RequestParam("hsnCode") String hsnCode, @RequestParam("hsnDescription") String hsnDescription, HttpServletRequest httpRequest) {
		logger.info("Entry");
		HSNDetails hsnDetails = null;
		try {
		
			hsnDetails = HSNService.getIGSTValueByHsnCode(hsnCode,hsnDescription);
		}catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(hsnDetails);
		
	}
	
	@RequestMapping(value = "/getCompleteHSNCodeList")
	public @ResponseBody String getMCompleteHSNCodeList() {
		List<HSNDetails> hsnCodeList = HSNService.getHSNCodeList();
		return new Gson().toJson(hsnCodeList);
	}
	

}
