/**
 * 
 */
package com.reliance.gstn.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.ManageOffers;
import com.reliance.gstn.service.ManageOffersService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
//import com.reliance.gstn.util.LoggerUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Pradeep.Gangapuram
 *
 */
@Controller
public class ManageOffersController {
	
	private static final Logger logger = Logger.getLogger(ManageOffersController.class);
	
	@Autowired
	public ManageOffersService manageOffersService;
	
	@Value("${MANAGE_OFFER_SUCESS}")
	private String manageOfferSuccessful;
	
	@Value("${MANAGE_OFFER_FAILURE}")
	private String manageOfferFailure;
	
	@Value("${MANAGE_OFFER_UPDATION_SUCESS}")
	private String manageOfferUpdationSuccessful;
	
	@Value("${MANAGE_OFFER_UPDATION_FAILURE}")
	private String manageOfferUpdationFailure;
	
	@Value("${MANAGER_OFFER_DELETION_SUCESS}")
	private String manageOfferDeletionSuccessful;
	
	@Value("${MANAGE_OFFER_DELETION_FAILURE}")
	private String manageOfferDeletionFailure;
	
	@Autowired
	private UserMasterService userMasterService;
	
	
	@ModelAttribute("manageOffers")
	public ManageOffers construct(){
		return new ManageOffers();
	}
	
	@RequestMapping(value = "/manageOffers", method = RequestMethod.GET)
	public String manageOffersViewPage(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
	
		return PageRedirectConstants.MANAGE_OFFERS_LIST_PAGE;
	}
	
	
	@RequestMapping(value="/getmanageOffers",method=RequestMethod.POST)
	public @ResponseBody String getmanageOffers(Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		List<ManageOffers> ManageOffersList = new ArrayList<ManageOffers>();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer orgUId =loginMaster.getOrgUId();
			String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			ManageOffersList = manageOffersService.listManageOffers(idsValuesToFetch,orgUId);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return new Gson().toJson(ManageOffersList);
	}
	
	
	
	@RequestMapping(value = "/addManageOffers", method = RequestMethod.GET)
	public String manageOffersAddPage(Model model) {
		return PageRedirectConstants.MANAGE_OFFERS_PAGE;
	}
	
	
	@RequestMapping(value="/addManageOffers",method=RequestMethod.POST)
	public String postManageMyServiceCataloguePage(@Valid @ModelAttribute("manageOffers") ManageOffers manageOffers, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = null;
		String pageRedirect = PageRedirectConstants.MANAGE_OFFERS_PAGE;
		try {
		if (!result.hasErrors()){
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer orgUId =loginMaster.getOrgUId();
			
			manageOffers.setCreatedBy(loginMaster.getuId().toString());
			manageOffers.setReferenceId(loginMaster.getuId());
			manageOffers.setDiscountValidDate(GSTNUtil.convertStringToTimestamp(manageOffers.getDiscountValidDateInString()));
			manageOffers.setRefOrgId(orgUId);
			manageOffers.setStatus("1");
				
					response = manageOffersService.addManageOffers(manageOffers);
					if(response.equals(GSTNConstants.SUCCESS)){
						model.addAttribute(GSTNConstants.RESPONSE, manageOfferSuccessful);
					//	manageOffers = new ManageOffers();
					}else{
						model.addAttribute(GSTNConstants.RESPONSE, manageOfferFailure);
					}
		}
		} catch (Exception e) {
			model.addAttribute(GSTNConstants.RESPONSE, manageOfferFailure);
		}
		model.addAttribute("manageOffers", new ManageOffers());
		logger.info("Exit");
		return  pageRedirect;
	}
	
	
	@RequestMapping(value = "/editManageOffers")
	public String manageOffersEditPage(@ModelAttribute ManageOffers manageOffers, Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");	
		
		try {
			ManageOffers manageOffersObj = manageOffersService.getManageOffersById(manageOffers.getId());
			manageOffersObj.setDiscountValidDateInString(GSTNUtil.convertTimestampToString(manageOffersObj.getDiscountValidDate()));
			model.addAttribute("manageOffersObj", manageOffersObj);
			model.addAttribute("editActionPerformed", true);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return PageRedirectConstants.MANAGE_OFFERS_EDIT_PAGE;
	}
	
	
	@RequestMapping(value="/updateManageOffers",method=RequestMethod.POST)
	public String updateManageOffers(@Valid @ModelAttribute ManageOffers manageOffers ,BindingResult result,  HttpServletRequest httpRequest,HttpSession httpSession, Model model) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.MANAGE_OFFERS_PAGE;
		String response = null;
		
		if (!result.hasErrors()){
			try {
				LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
				Integer orgUId =loginMaster.getOrgUId();
				manageOffers.setReferenceId(loginMaster.getuId());
				manageOffers.setUpdatedBy(loginMaster.getuId().toString());
				manageOffers.setDiscountValidDate(GSTNUtil.convertStringToTimestamp(manageOffers.getDiscountValidDateInString()));
				manageOffers.setRefOrgId(orgUId);
				response = manageOffersService.updateManageOffers(manageOffers);
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, manageOfferUpdationSuccessful);
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, manageOfferUpdationFailure);
				}
			} catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE, manageOfferUpdationFailure);
			}
		}else{
			logger.error("Error occured"+result.getAllErrors());
		}
		
		logger.info("Exit");
		return  pageRedirect;
	}
	
	

	@RequestMapping(value = "/deleteManageOffers", method = RequestMethod.POST)
	public String deleteManageOffers(@ModelAttribute("manageOffers") ManageOffers manageOffers,  BindingResult result,Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");
		String response = null;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		if (!result.hasErrors()){
			manageOffers.setUpdatedBy(loginMaster.getuId().toString());
			manageOffers.setStatus("0");
			try {
				response = manageOffersService.removeManageOffer(manageOffers);
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, manageOfferDeletionSuccessful);
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, manageOfferDeletionFailure);	
				}
			} catch (Exception e) {
				logger.error("Error in:",e);
			}
		}else{
			logger.error("Error occured"+result.getAllErrors());
		}
		logger.info("Exit");
		return PageRedirectConstants.MANAGE_OFFERS_PAGE;
	}
	
	@RequestMapping(value="/getManageOfferById",method=RequestMethod.POST)
	public @ResponseBody String getManageOfferById(@RequestParam("serviceId") Integer id,@RequestParam("serviceType") String serviceType, HttpServletRequest httpRequest) {
		logger.info("Entry");
		logger.info("serviceId:"+id+",serviceType : "+serviceType);
		List<ManageOffers> offersList = new ArrayList<ManageOffers>();
		try {
			offersList = manageOffersService.getManageOffersByOfferType(id.toString(),serviceType);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return new Gson().toJson(offersList);
		
	}
	
	@RequestMapping(value="/checkIfOfferExists",method=RequestMethod.POST)
	public @ResponseBody String checkIfOfferExists(@RequestParam("offer") String offer, HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		boolean isProductexists = false;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer orgUId =loginMaster.getOrgUId();
			isProductexists = manageOffersService.checkIfOfferExists(offer,orgUId);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return new Gson().toJson(isProductexists);
		
	}
	
}
