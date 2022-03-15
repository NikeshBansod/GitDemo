package com.reliance.gstn.admin.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.admin.service.SACCodeService;
import com.reliance.gstn.model.SACCode;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.PageRedirectConstants;


@Controller
public class SACCodeController {
	
	private static final Logger logger = Logger.getLogger(SACCodeController.class);
	
	@Autowired
	SACCodeService SACCodeService;
	
	@Value("${SAC_CODE_ADD_FAILURE}")
	private String sacCodeAddFailure;
	
	@Value("${SAC_CODE_ADD_SUCCESS}")
	private String sacCodeAddSuccessful;
	
	@Value("${SAC_CODE_UPDATION_FAILURE}")
	private String sacUpdationFailure;
	
	@Value("${SAC_CODE_UPDATION_SUCCESS}")
	private String sacUpdationSuccessful;
	
	@Value("${SAC_CODE_DELETION_FAILURE}")
	private String SACDeletionFailure;
	
	@Value("${SAC_CODE_DELETION_SUCCESS}")
	private String SACDeletionSuccessful;
	
	@ModelAttribute("SACCode")
	public SACCode construct(){
		return new SACCode();
	}
	
	@RequestMapping(value = "/idt/sacCodeDetails", method = RequestMethod.GET)
	public String SACDetailsListPage(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		return PageRedirectConstants.SAC_DETAILS_LIST_PAGE;
	}
	
	@RequestMapping(value = {"/getCompleteSACCodeList","/idt/getiCompleteSACCodeList"})
	public @ResponseBody String getCompleteSACCodeList() {
		List<SACCode> sacCodeList = SACCodeService.getSACCodeList();
		return new Gson().toJson(sacCodeList);
	}
	
	@RequestMapping(value="/idt/addSACDetails",method=RequestMethod.POST)
	public String addSACDetailsPost(@Valid @ModelAttribute("SACCode") SACCode sacCode, BindingResult result,Model model,  HttpSession httpSession,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = null;
		String pageRedirect = PageRedirectConstants.SAC_DETAILS_LIST_PAGE;
		if (!result.hasErrors()){
			try {
				sacCode.setSgstOrUgst((sacCode.getIgst()/2));
				sacCode.setCgst((sacCode.getIgst()/2));
				response = SACCodeService.addSACCode(sacCode);
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, sacCodeAddSuccessful);
					sacCode = new SACCode();
					
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, sacCodeAddFailure);   
				}
			} catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE, sacCodeAddFailure);
				logger.error("Error in:",e);
			}
			
		}
		model.addAttribute("SACDetails", sacCode);
		logger.info("Exit");
		return  pageRedirect;
	}
	
	@RequestMapping(value = "/idt/editSACDetails",method=RequestMethod.POST)
	public String SACDetailsEditPage(@ModelAttribute SACCode sacCode, Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");	
		
		SACCode SACDetailsObj = SACCodeService.getSACDetailsById(sacCode.getId());
		model.addAttribute("SACDetailsObj", SACDetailsObj);
		
		logger.info("Exit");
		return PageRedirectConstants.SAC_DETAILS_EDIT_PAGE;
	}
	
	
	@RequestMapping(value="/idt/updateSacDetails",method=RequestMethod.POST)
	public String updateSACDetails(@Valid @ModelAttribute SACCode sacCode ,BindingResult result,  HttpServletRequest httpRequest,HttpSession httpSession, Model model) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.SAC_DETAILS_EDIT_PAGE;
		String response = null;
		
		if (!result.hasErrors()){
			try {
				//sacCode.setSgstUgst((sacCode.getIgst()/2));
				sacCode.setSgstOrUgst((sacCode.getIgst()/2));
				sacCode.setCgst((sacCode.getIgst()/2));
				response = SACCodeService.updateSACDetails(sacCode);
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, sacUpdationSuccessful);
					pageRedirect = PageRedirectConstants.SAC_DETAILS_LIST_PAGE;
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, sacUpdationFailure);
				}
			} catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE, sacUpdationFailure);
			}
		}else{
			System.out.println("Error occured"+result.getAllErrors());
		}
		
		logger.info("Exit");
		return  pageRedirect;
	}
	
	
	@RequestMapping(value="/idt/deleteSACDetails",method=RequestMethod.POST)
	public String deleteSACDetails(@RequestParam Integer id, Model model) {
		logger.info("Entry");	
		System.out.println("id : "+id);
		String response = null;
		try {
			response = SACCodeService.removeSACDetails(id);
			if(response.equals(GSTNConstants.SUCCESS)){
				model.addAttribute(GSTNConstants.RESPONSE, SACDeletionSuccessful);
			}else{
				model.addAttribute(GSTNConstants.RESPONSE, SACDeletionFailure);
			}
		} catch (Exception e) {
			model.addAttribute(GSTNConstants.RESPONSE, SACDeletionFailure);
		}
		
		logger.info("Exit");
		return PageRedirectConstants.SAC_DETAILS_LIST_PAGE;
	}


}
