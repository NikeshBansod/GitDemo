package com.reliance.gstn.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.PoDetails;
import com.reliance.gstn.service.PoDetailsService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
//import com.reliance.gstn.util.LoggerUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

@Controller
public class PoDetailsController {

private static final Logger logger = Logger.getLogger(PoDetailsController.class);
	
	@Autowired
	public PoDetailsService poDetailsService;
	
	@ModelAttribute("poDetails")
	public PoDetails construct(){
		return new PoDetails();
	}
	
	@InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     dateFormat.setLenient(false);
 //    webDataBinder.registerCustomEditor(Date.class, "poValidToDate", new CustomDateEditor(dateFormat, true));
     webDataBinder.registerCustomEditor(Date.class,  new CustomDateEditor(dateFormat, true));
     }
	
	@Value("${PO_DETAILS_SUCESS}")
	private String poDetailsSuccessful;
	
	@Value("${PO_DETAILS_FAILURE}")
	private String poDetailsFailure;
	
	@Value("${PO_DETAILS_UPDATION_SUCESS}")
	private String PODetailsUpdationSucessful;
	
	@Value("${PO_DETAILS_UPDATION_FAILURE}")
	private String poDetailsUpdationFailed;
	
	@Value("${PO_DETAILS_DELETION_SUCESS}")
	private String poDetailsDeletionSucessful;
	
	@Value("${PO_DETAILS_DELETION_FAILURE}")
	private String poDetailsDeletionFailed;
	
	@Value("${PO_DETAILS_DUPLICACY_FAILURE}")
	private String dupalicatePoDetail;
	
	@RequestMapping(value = "/poDetails", method = RequestMethod.GET)
	public String managePoDetails(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			List<PoDetails> managePoDetailsList = poDetailsService.viewPoDetailsList(loginMaster.getuId());
			model.addAttribute("managePoDetailsList", managePoDetailsList);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return PageRedirectConstants.MANAGE_PO_DETAILS_LIST_PAGE;
	}
	
	
	@RequestMapping(value = "/addPoDetails", method = RequestMethod.GET)
	public String addPoDetails(Model model) {
		return PageRedirectConstants.MANAGE_PO_DETAILS_LIST_PAGE;
	}
	
	@RequestMapping(value="/addPoDetails",method=RequestMethod.POST)
	public String addCustomerDetailsPost(@Valid @ModelAttribute("poDetails") PoDetails poDetails, BindingResult result,Model model,  HttpSession httpSession,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = "";
		String pageRedirect = PageRedirectConstants.MANAGE_PO_DETAILS_LIST_PAGE;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		Integer orgUId =loginMaster.getOrgUId();
		if (!result.hasErrors()){
			
			try {
				poDetails.setCreatedBy(loginMaster.getUserId());
				//customized invalid Date Exception 
				/*if(poDetails.getPoValidFromDateTemp().compareTo(poDetails.getPoValidToDateTemp()) > 0){
					throw new InvalidDateException();
				}*/
				poDetails.setPoValidFromDate(GSTNUtil.convertStringToTimestamp(poDetails.getPoValidFromDateTemp()));
				poDetails.setPoValidToDate(GSTNUtil.convertStringToTimestamp(poDetails.getPoValidToDateTemp()));
				
				response = poDetailsService.addPoDetails(poDetails,loginMaster.getuId(),orgUId);
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, poDetailsSuccessful);
					pageRedirect = PageRedirectConstants.MANAGE_PO_DETAILS_LIST_PAGE;
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, poDetailsFailure);
				}
			}catch(ConstraintViolationException e){
				model.addAttribute(GSTNConstants.RESPONSE, dupalicatePoDetail);
				logger.error("Error in:",e);
			} catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE, poDetailsFailure);
				logger.error("Error in:",e);
			}
			
		}
		poDetails = new PoDetails();
		model.addAttribute("poDetails", poDetails);
		logger.info("Exit");
		return  pageRedirect;
	}
	
	
	@RequestMapping(value = "/editPODetails",method=RequestMethod.POST)
	public String editCustomerDetails(@ModelAttribute PoDetails poDetails, Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");	
		
		PoDetails poDetailsObj = null;
		try {
			poDetailsObj = poDetailsService.getPoDetailsById(poDetails.getId());
			poDetailsObj.setPoValidFromDateTemp(GSTNUtil.convertTimestampToString(poDetailsObj.getPoValidFromDate()));
			poDetailsObj.setPoValidToDateTemp(GSTNUtil.convertTimestampToString(poDetailsObj.getPoValidToDate()));
			model.addAttribute("poDetailsObj", poDetailsObj);
			model.addAttribute("editActionPerformed", true);
		} catch (Exception e) {
			logger.error("Error in:",e);
			e.printStackTrace();
		}
		
		logger.info("Exit");
		return PageRedirectConstants.MANAGE_PO_DETAILS_EDIT_PAGE;
	}
	
	
	@RequestMapping(value="/updatePoDetails",method=RequestMethod.POST)
	public String updateCustomerDetails(@Valid @ModelAttribute PoDetails poDetails ,BindingResult result,  HttpServletRequest httpRequest,HttpSession httpSession, Model model) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.MANAGE_PO_DETAILS_LIST_PAGE;
		String response = null;
		
		if (!result.hasErrors()){
			try {
				LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
				
				poDetails.setRefUserId(loginMaster.getuId());
				poDetails.setUpdatedBy(loginMaster.getuId().toString());
				poDetails.setPoValidFromDate(GSTNUtil.convertStringToTimestamp(poDetails.getPoValidFromDateTemp()));
				poDetails.setPoValidToDate(GSTNUtil.convertStringToTimestamp(poDetails.getPoValidToDateTemp()));
				response = poDetailsService.updatePoDetails(poDetails);
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, PODetailsUpdationSucessful);
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, poDetailsUpdationFailed);
					pageRedirect = PageRedirectConstants.MANAGE_PO_DETAILS_EDIT_PAGE;
				}
			} catch (Exception e) {
				logger.error("Error in:",e);
				model.addAttribute(GSTNConstants.RESPONSE, poDetailsUpdationFailed);
				pageRedirect = PageRedirectConstants.MANAGE_PO_DETAILS_EDIT_PAGE;
			}
		}else{
			logger.error("Error occured"+result.getAllErrors());
		}
		
		logger.info("Exit");
		return  pageRedirect;
	}
	
	@RequestMapping("/deletePoDetails")
	public String deletePoDetails(@RequestParam Integer id, Model model) {
		logger.info("Entry");	
		String response = "";
		try {
			response = poDetailsService.deletePoDetails(id);
			if(response.equals(GSTNConstants.SUCCESS)){
				model.addAttribute(GSTNConstants.RESPONSE, poDetailsDeletionSucessful);
			}else{
				model.addAttribute(GSTNConstants.RESPONSE, poDetailsDeletionFailed);
			}
		} catch (Exception e) {
			model.addAttribute(GSTNConstants.RESPONSE, poDetailsDeletionFailed);
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return PageRedirectConstants.MANAGE_PO_DETAILS_LIST_PAGE;
	}
	
	@RequestMapping(value="/getPoDetailsByPoCustomerName",method=RequestMethod.POST)
	public @ResponseBody String getPoDetailsByPoCustomerName(@RequestParam("custId") String poCustomerName, Model model) {
		logger.info("Entry");
		List<PoDetails> poDetailsList = new ArrayList<PoDetails>();
		try {
			poDetailsList = poDetailsService.getPoDetailsByPoCustomerName(poCustomerName);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(poDetailsList);
		
	}
	
	
	@RequestMapping(value = "/poDetailsPage", method = RequestMethod.GET)
	public String getPODetailsPage(Model model) {
	
		return PageRedirectConstants.MANAGE_PO_DETAILS_LIST_PAGE;
	}
	
	@RequestMapping(value="/getPODetailsList",method=RequestMethod.POST)
	public @ResponseBody String getPODetailsList(Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");
		List<PoDetails> poDetailsList = new ArrayList<PoDetails>();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			poDetailsList = poDetailsService.viewPoDetailsList(loginMaster.getuId());
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(poDetailsList);
	}
	
	@RequestMapping(value="/checkIfPoNoExists",method=RequestMethod.POST)
	public @ResponseBody String checkIfPoNoExists(@RequestParam("poNo") String poNo, HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		boolean isPoNOexists=false;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer orgUId =loginMaster.getOrgUId();
			isPoNOexists = poDetailsService.checkIfPoNoExists(poNo,orgUId);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return new Gson().toJson(isPoNOexists);
		
	}
}
