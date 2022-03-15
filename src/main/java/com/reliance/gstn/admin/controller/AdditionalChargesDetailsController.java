/**
 * 
 */
package com.reliance.gstn.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.admin.model.AdditionalChargeDetails;
import com.reliance.gstn.admin.service.AdditionalChargeDetailsService;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.Product;
import com.reliance.gstn.model.ProductBean;
import com.reliance.gstn.service.GenericService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Rupali J
 *
 */

@Controller
public class AdditionalChargesDetailsController {
	
	private static final Logger logger = Logger.getLogger(AdditionalChargesDetailsController.class);
	
	@Autowired
	public AdditionalChargeDetailsService additionalChargeDetailsService;
	
	@Autowired
	private GenericService genericService;
	
	@Value("${ADDITIONAL_CHARGE_DETAILS_SUCESS}")
	private String additionalChargeDetailsSuccessful;
	
	@Value("${ADDITIONAL_CHARGE_DETAILS_FAILURE}")
	private String additionalChargeDetailsFailure;
	
	@Value("${DUPLICATE_ADDITIONAL_CHARGE_DETAILS_FAILURE}")
	private String dupalicateAdditionalChargeDetail;
	
	@Value("${ADDITIONAL_CHARGE_DETAILS_UPDATION_SUCESS}")
	private String additionalChargeDetailsUpdationSucessful;
	
	@Value("${DUPLICATE_ADDITIONAL_CHARGE_DETAILS_UPDATION_FAILURE}")
	private String dupliateAdditionalChargeDetailsUpdationfailure;
	
	@Value("${ADDITIONAL_CHARGE_DETAILS_UPDATION_FAILURE}")
	private String additionalChargeDetailsUpdationFailed;
	
	@Value("${ADDITIONAL_CHARGE_DELETION_SUCESS}")
	private String additionalChargeDetailsDeletionSucessful;
	
	@Value("${ADDITIONAL_CHARGE_DETAILS_DELETION_FAILURE}")
	private String additionalChargeDetailsDeletionFailed;
	
	@Value(value = "${get_prim_id_list_query}")
	private String getPrimIdsListQuery;

	@Value("${INVALID_MAPPING_EXCEPTION}")
	private String InvalidMappingException;
	
	
	@ModelAttribute("additionalChargeDetails")
	public AdditionalChargeDetails construct(){
		return new AdditionalChargeDetails();
	}
	
	@RequestMapping(value = "/additionalChargeDetails", method = RequestMethod.GET)
	public String manageadditionalChargeDetails(Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			List<AdditionalChargeDetails> manageAdditionalChargeDetailsList = additionalChargeDetailsService.viewAdditionalChargeDetailsList(loginMaster.getuId());
			model.addAttribute("manageAdditionalChargeDetailsList", manageAdditionalChargeDetailsList);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return PageRedirectConstants.GET_ADD_CHARGE_LIST_PAGE;
	}
	
	@RequestMapping(value = "/additionalCharges", method = RequestMethod.GET)
	public String getAddChargesPage(Model model) {
		return PageRedirectConstants.GET_ADD_CHARGE_LIST_PAGE;
	}
	

	@RequestMapping(value="/getAddChargesDetailsList",method=RequestMethod.POST)
	public @ResponseBody String getAddChargesDetailsList(Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		
		List<AdditionalChargeDetails> additionalChargeDetailsList = new ArrayList<AdditionalChargeDetails>();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			additionalChargeDetailsList = additionalChargeDetailsService.getAddChargesList(loginMaster.getOrgUId());
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return new Gson().toJson(additionalChargeDetailsList);
	}
	
	
	@RequestMapping(value="/addAdditionalCharges",method=RequestMethod.POST)
	public String addAdditionalCharges(@Valid @ModelAttribute("additionalChargeDetails") AdditionalChargeDetails additionalChargeDetails, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = null;
		String pageRedirect = PageRedirectConstants.GET_ADD_CHARGE_LIST_PAGE;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		if (!result.hasErrors()){
			
			try {
				Map<Object, Object> mapValues = new HashMap<Object, Object>(); 
				mapValues.put("loginMaster", loginMaster);
				response = additionalChargeDetailsService.addChargesDetails(additionalChargeDetails,mapValues);
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, additionalChargeDetailsSuccessful);
					pageRedirect = PageRedirectConstants.GET_ADD_CHARGE_LIST_PAGE;
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, additionalChargeDetailsFailure);
				}
			} catch(ConstraintViolationException e){
				model.addAttribute(GSTNConstants.RESPONSE, dupalicateAdditionalChargeDetail);
				logger.error("Error in:",e);
			} catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE, additionalChargeDetailsFailure);
				logger.error("Error in:",e);
			}
			
		}
		
		model.addAttribute("additionalChargeDetails", new AdditionalChargeDetails());
		logger.info("Exit");
		return  pageRedirect;
	}
	
	@RequestMapping(value = "/editAdditionalChargeDetails")
	public String editAdditionalChargeDetails(@ModelAttribute AdditionalChargeDetails additionalChargeDetails, Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String pageRedirect =PageRedirectConstants.ADD_CHARGE_EDIT_PAGE;
		AdditionalChargeDetails addChargeDetailsObj = new AdditionalChargeDetails();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer chargeId =additionalChargeDetails.getId();
			boolean isMappingValid = false;
		//	String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(),getPrimIdsListQuery,chargeId,"AdditionalChargeDetails","refOrgId");
			
			if(isMappingValid){
				addChargeDetailsObj = additionalChargeDetailsService.getAdditionalChargeDetailsById(additionalChargeDetails.getId());
			}else{
				logger.info("INVALID ACCESS"+loginMaster.getUserId());
				model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
				pageRedirect = PageRedirectConstants.GET_ADD_CHARGE_LIST_PAGE;
			}
			
			
			
			model.addAttribute("addChargeDetailsObj", addChargeDetailsObj);
			model.addAttribute("editActionPerformed", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return pageRedirect;
	}
	
	
	@RequestMapping(value="/updateAdditionalChargeDetails",method=RequestMethod.POST)
	public String updateAdditionalChargeDetails(@Valid @ModelAttribute AdditionalChargeDetails additionalChargeDetails ,BindingResult result,  HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.GET_ADD_CHARGE_LIST_PAGE;
		String response = null;
		
		if (!result.hasErrors()){
			try {
				LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
				Map<Object, Object> mapValues = new HashMap<Object, Object>(); 
				mapValues.put("loginMaster", loginMaster);
				Integer chargeId =additionalChargeDetails.getId();
				boolean isMappingValid = false;
			//	String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
				isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(),getPrimIdsListQuery,chargeId,"AdditionalChargeDetails","refOrgId");
					if(isMappingValid){
						response = additionalChargeDetailsService.updateAdditionalChargeDetails(additionalChargeDetails, mapValues);
					}else{
						response = GSTNConstants.ACCESSVIOLATION;
					}
				
					
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, additionalChargeDetailsUpdationSucessful);
					model.addAttribute("additionalChargeDetails", new AdditionalChargeDetails() );
				}else if(response.equals(GSTNConstants.FAILURE)){
					model.addAttribute(GSTNConstants.RESPONSE, additionalChargeDetailsUpdationFailed);
					model.addAttribute("additionalChargeDetails", additionalChargeDetails );
				}
			} catch(DataIntegrityViolationException e){
				pageRedirect = PageRedirectConstants.ADD_CHARGE_EDIT_PAGE;
					model.addAttribute(GSTNConstants.RESPONSE, dupliateAdditionalChargeDetailsUpdationfailure);
					model.addAttribute("addChargeDetailsObj", additionalChargeDetails);
					model.addAttribute("editActionPerformed", true);
					logger.error("Error in:",e);
			}catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE, additionalChargeDetailsUpdationFailed);
				model.addAttribute("additionalChargeDetails", additionalChargeDetails );
				logger.error("Error in:",e);
			}
		} else {
			model.addAttribute("additionalChargeDetails", additionalChargeDetails );
			logger.error("Error occured"+result.getAllErrors());
		}
		
		logger.info("Exit");
		return  pageRedirect;
	}
	
	@RequestMapping("/deleteAddChargeDetails")
	public String deleteAddChargeDetails(@RequestParam Integer id, Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = "";
		String pageRedirect = PageRedirectConstants.GET_ADD_CHARGE_LIST_PAGE;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer chargeId =id;
			boolean isMappingValid = false;
		//	String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(), getPrimIdsListQuery,chargeId,"AdditionalChargeDetails","refOrgId");
			
				if(isMappingValid){
					response = additionalChargeDetailsService.deleteAdditionalChargeDetails(id);
				}else{
					response = GSTNConstants.ACCESSVIOLATION;
				}
			
			if(response.equals(GSTNConstants.SUCCESS)){
				model.addAttribute(GSTNConstants.RESPONSE, additionalChargeDetailsDeletionSucessful);
			}else if(response.equals(GSTNConstants.FAILURE)){
				model.addAttribute(GSTNConstants.RESPONSE, additionalChargeDetailsDeletionFailed);
			}else if(response.equals(GSTNConstants.ACCESSVIOLATION)){
				logger.info("INVALID ACCESS"+loginMaster.getUserId());
				pageRedirect = PageRedirectConstants.GET_ADD_CHARGE_LIST_PAGE;
				model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
			}
		} catch (Exception e) {
			model.addAttribute(GSTNConstants.RESPONSE, additionalChargeDetailsDeletionFailed);
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return pageRedirect;
	}
	
	
	
	@RequestMapping(value = "/getAdditionalChargesList", method = RequestMethod.POST, headers = "Accept=*/*")
	public @ResponseBody List<String> getAdditionalChargesList(@RequestParam("term") String query,HttpServletRequest httpRequest){
		logger.info("Entry");
		List<String> addChargesList = null;

		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		addChargesList = getAdditionalChargesListAutoComplete(query,loginMaster.getOrgUId());

		logger.info("Exit");
		return addChargesList;
	}

	private List<String> getAdditionalChargesListAutoComplete(String query,Integer orgUId) {
		query = query.toLowerCase();
		List<String> matched = new ArrayList<String>();
		try {
			matched = additionalChargeDetailsService.getAdditionalChargesListAutoComplete(query,orgUId);
		} catch (Exception e) {
			logger.error("Error Additional Charges AutoComplete"+e.getMessage());
			e.printStackTrace();
		}
		return matched;
	}
	
	@RequestMapping(value = "/getAdditionalChargeByChargeName", method = RequestMethod.POST)
	public @ResponseBody String getAdditionalChargeByChargeName(@RequestParam("chargeName") String chargeName,HttpServletRequest httpRequest) {
		logger.info("Entry");
		AdditionalChargeDetails addCharge = new AdditionalChargeDetails();
		try {
			LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
			addCharge = additionalChargeDetailsService.getAdditionalChargeByChargeName(chargeName,loginMaster.getOrgUId());
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(addCharge);

	}
	
	@RequestMapping(value = "/saveAdditionalChargeAjax", method = RequestMethod.POST)
	public @ResponseBody String saveAdditionalChargeAjax(@Valid @RequestBody AdditionalChargeDetails additionalChargeDetails, BindingResult result,Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String response = null;
		String message = additionalChargeDetailsFailure;
		String status = GSTNConstants.FAILURE;
		Map<String, String> mapresponse = new HashMap<String, String>();
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		if (!result.hasErrors()) {
			try {
				Map<Object, Object> mapValues = new HashMap<Object, Object>(); 
				mapValues.put("loginMaster", loginMaster);
				response = additionalChargeDetailsService.addChargesDetails(additionalChargeDetails,mapValues);
				if (response.equals(GSTNConstants.SUCCESS)) {
					message = additionalChargeDetailsSuccessful;
					status = GSTNConstants.SUCCESS;
				}else{
					message = additionalChargeDetailsFailure;
				}

			}catch(ConstraintViolationException e){
				model.addAttribute(GSTNConstants.RESPONSE, dupalicateAdditionalChargeDetail);
				logger.error("Error in:",e);
			}catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE, additionalChargeDetailsFailure);
				logger.error("Error in:",e);
			}
		}
		mapresponse.put("message", message);
		mapresponse.put("status", status);
		logger.info("Exit");
		return new Gson().toJson(mapresponse);

	}
	
	
}
