package com.reliance.gstn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.reliance.gstn.admin.exception.DuplicateRecordExistException;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.UserGSTINMapping;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.GSTINUserMappingService;
import com.reliance.gstn.service.GenericService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

@Controller
public class WizardGSTINUserMappingController {

	private static final Logger logger = Logger.getLogger(WizardGSTINUserMappingController.class);

	@Autowired
	private GSTINUserMappingService gstinUserMappingService;

	@Autowired
	private GenericService genericService;
	
	@Value("${GSTIN_USER_MAPPING_ADD_SUCCESS}")
	private String gstinUserMappingAdditionSuccessful;

	@Value("${GSTIN_USER_MAPPING_ADD_FAILURE}")
	private String gstinUserMappingAdditionFailure;

	@Value("${GSTIN_USER_MAPPING_DUPLICATE}")
	private String gstinUserMappingDuplicacyFailure;

	@Value("${INVALID_MAPPING_EXCEPTION}")
	private String InvalidMappingException;

	@Value(value = "${get_prim_id_list_query}")
	private String getPrimIdsListQuery;

	@Value("${GSTIN_USER_MAPPING_UPDATE_SUCCESS}")
	private String gstinUserMappingUpdationSuccessful;

	@Value("${GSTIN_USER_MAPPING_UPDATE_FAILURE}")
	private String gstinUserMappingUpdationFailure;

	@Value("${GSTIN_USER_MAPPING_DELETE_SUCCESS}")
	private String gstinUserMappingDeleteSuccessful;

	@Value("${GSTIN_USER_MAPPING_DELETE_FAILURE}")
	private String gstinUserMappingDeleteFailure;
	
	
	@ModelAttribute("userGstinMapping")
	public UserGSTINMapping construct(){
		return new UserGSTINMapping();
	}
	
	@RequestMapping(value = "/wizardGetUserGstinMap", method = RequestMethod.GET)
	public String wizardGetUserGstinMappingListPage(Model model,HttpServletRequest httpRequest) {		
		return PageRedirectConstants.WIZARD_GET_USER_GSTIN_MAP_LIST_PAGE;
	}
	
	@RequestMapping(value="/wizardAddUserGstinMapping",method=RequestMethod.POST)
	public String wizardAddUserGstinMapping(@Valid @ModelAttribute("gstinUserMapping") UserGSTINMapping userGSTINMapping, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = "";
		String pageRedirect = PageRedirectConstants.WIZARD_GET_USER_GSTIN_MAP_LIST_PAGE;
		if (!result.hasErrors()){
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			try {
				Map<Object, Object> mapValues = new HashMap<Object, Object>(); 
				mapValues.put("loginMaster", loginMaster);
				response = gstinUserMappingService.addUserGSTINMapping(userGSTINMapping, mapValues);
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, gstinUserMappingAdditionSuccessful);
					userGSTINMapping = new UserGSTINMapping();
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, gstinUserMappingAdditionFailure);
				}
			} catch(DuplicateRecordExistException e){
				logger.error("Error in:",e);
				model.addAttribute(GSTNConstants.RESPONSE, gstinUserMappingDuplicacyFailure);
			} catch (Exception e) {
				logger.error("Error in:",e);
				model.addAttribute(GSTNConstants.RESPONSE, gstinUserMappingAdditionFailure);
			}			
		}
		model.addAttribute("userGSTINMapping", userGSTINMapping);
		logger.info("Exit");
		return  pageRedirect;
	}
	
	@RequestMapping(value = "/wizardEditUserGSTINMapping", method = RequestMethod.POST)
	public String wizardEditUserGSTINMapping(@ModelAttribute("userGstinMapping") UserGSTINMapping userGstinMapping, Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		UserGSTINMapping gstinMapping = new UserGSTINMapping();
			String pageRedirect = PageRedirectConstants.WIZARD_USER_GSTIN_MAP_EDIT_PAGE;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			boolean isMappingValid = false;
		//	String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(),getPrimIdsListQuery,userGstinMapping.getId(),"UserGSTINMapping","refOrgId");
				if(isMappingValid){
					gstinMapping = gstinUserMappingService.getUserGSTINMappingById(userGstinMapping.getId());
				}else{
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					pageRedirect = PageRedirectConstants.WIZARD_GET_USER_GSTIN_MAP_LIST_PAGE;
					model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
				}
				List<UserMaster> userMasterList = gstinMapping.getGstinUserSet();
				List<Integer> idList = new ArrayList<Integer>();
				for(UserMaster userMaster : userMasterList){
					idList.add(userMaster.getId());					
				}
				gstinMapping.setGstinUserIds(idList);
			model.addAttribute("userGstinMappingObj", gstinMapping);
		
			model.addAttribute("editActionPerformed", true);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}		
		logger.info("Exit");
		return pageRedirect;
	}
	
	
	@RequestMapping(value="/wizardUpdateUserGstinMapping",method=RequestMethod.POST)
	public String wizardUpdateUserGstinMapping(@Valid @ModelAttribute UserGSTINMapping userGstinMapping ,BindingResult result,  HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.WIZARD_GET_USER_GSTIN_MAP_LIST_PAGE;
		String response = "";		
		if (!result.hasErrors()){
			try {
				LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
				Map<Object, Object> mapValues = new HashMap<Object, Object>(); 
				mapValues.put("loginMaster", loginMaster);
				
				Integer gstinUserId =userGstinMapping.getId();
				boolean isMappingValid = false;
				//String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
				isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(), getPrimIdsListQuery,gstinUserId,"UserGSTINMapping","refOrgId");
						if(isMappingValid){
							isMappingValid = genericService.checkUserMappingValidation(userGstinMapping.getGstinId().toString(), getPrimIdsListQuery,gstinUserId,"UserGSTINMapping","gstinId");
							if(isMappingValid){
							response = gstinUserMappingService.updateUserGstinMapping(userGstinMapping, mapValues);
							} else {
								response = GSTNConstants.ACCESSVIOLATION;
							}
						}else{
							response = GSTNConstants.ACCESSVIOLATION;							
						}
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, gstinUserMappingUpdationSuccessful);
				}else if(response.equals(GSTNConstants.FAILURE)){
					model.addAttribute(GSTNConstants.RESPONSE, gstinUserMappingUpdationFailure);
				}else if(response.equals(GSTNConstants.ACCESSVIOLATION)){
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					pageRedirect = PageRedirectConstants.WIZARD_USER_GSTIN_MAP_EDIT_PAGE;
					model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
				}
			} catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE, gstinUserMappingUpdationFailure);
				logger.error("Error in:",e);
			}
		}else{
			logger.error("Error occured"+result.getAllErrors());
		}
		model.addAttribute("userGstinMapping", userGstinMapping);
		logger.info("Exit");
		return  pageRedirect;
	}
	
	@RequestMapping(value = "/wizardDeleteUserGSTINMapping", method = RequestMethod.POST)
	public String wizardDeleteUserGSTINMapping(@ModelAttribute("userGstinMapping") UserGSTINMapping userGstinMapping,  BindingResult result,Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String response = "";
		String pageRedirect = PageRedirectConstants.WIZARD_GET_USER_GSTIN_MAP_LIST_PAGE;	
		if (!result.hasErrors()){
			try {
				LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
				Integer gstinUserId =userGstinMapping.getId();
				boolean isMappingValid = false;
				//String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
				isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(), getPrimIdsListQuery,gstinUserId,"UserGSTINMapping","refOrgId");
						if(isMappingValid){
							response = gstinUserMappingService.removeUserGstinMapping(userGstinMapping);
						}else{
							response = GSTNConstants.ACCESSVIOLATION;
						}
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, gstinUserMappingDeleteSuccessful);
				}else if(response.equals(GSTNConstants.FAILURE)){
					model.addAttribute(GSTNConstants.RESPONSE, gstinUserMappingDeleteFailure);	
				}else if(response.equals(GSTNConstants.ACCESSVIOLATION)){
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					pageRedirect = PageRedirectConstants.WIZARD_GET_USER_GSTIN_MAP_LIST_PAGE;
					model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);	
				}
			} catch (Exception e) {
				logger.error("Error in:",e);
			}
		}else{
			logger.error("Error occured"+result.getAllErrors());
		}
		logger.info("Exit");
		return pageRedirect;
	}
	
	
}
