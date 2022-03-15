/**
 * 
 */
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.admin.exception.DuplicateRecordExistException;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.GSTINUserMapping;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.UserGSTINMapping;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.GSTINDetailsService;
import com.reliance.gstn.service.GSTINUserMappingService;
import com.reliance.gstn.service.GenericService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.GSTNConstants;
//import com.reliance.gstn.util.LoggerUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Pradeep.Gangapuram
 *
 */
@Controller
public class GSTINUserMappingController {
	
	private static final Logger logger = Logger.getLogger(GSTINUserMappingController.class);
	
	@Autowired
	private GSTINUserMappingService gstinUserMappingService;
	
	@Autowired
	private UserMasterService userMasterService;
	
	@Autowired
	private GSTINDetailsService gstinDetailsService;
	
	@Autowired
	private GenericService genericService;
	
	@Value("${GSTIN_USER_MAPPING_ADD_SUCCESS}")
	private String gstinUserMappingAdditionSuccessful;
	
	@Value("${GSTIN_USER_MAPPING_ADD_FAILURE}")
	private String gstinUserMappingAdditionFailure;
	
	@Value("${GSTIN_USER_MAPPING_DUPLICATE}")
	private String gstinUserMappingDuplicacyFailure;
	
	@Value("${GSTIN_USER_MAPPING_UPDATE_SUCCESS}")
	private String gstinUserMappingUpdationSuccessful;
	
	@Value("${GSTIN_USER_MAPPING_UPDATE_FAILURE}")
	private String gstinUserMappingUpdationFailure;
	
	@Value("${GSTIN_USER_MAPPING_DELETE_SUCCESS}")
	private String gstinUserMappingDeleteSuccessful;
	
	@Value("${GSTIN_USER_MAPPING_DELETE_FAILURE}")
	private String gstinUserMappingDeleteFailure;
	
	@Value("${INVALID_MAPPING_EXCEPTION}")
	private String InvalidMappingException;
	
	@Value(value = "${get_prim_id_list_query}")
	private String getPrimIdsListQuery;
	
	/*@ModelAttribute("gstinUserMapping")
	public GSTINUserMapping construct(){
		return new GSTINUserMapping();
	}
	*/
	@ModelAttribute("userGstinMapping")
	public UserGSTINMapping construct(){
		return new UserGSTINMapping();
	}
	
	@RequestMapping(value = "/getGstinUserMap", method = RequestMethod.GET)
	public String getGstinUserMappingListPage(Model model) {
		return PageRedirectConstants.GET_GSTIN_USER_MAP_LIST_PAGE;
	}
	

	@RequestMapping(value="/addGstinUserMapping",method=RequestMethod.POST)
	public String addProductPage(@Valid @ModelAttribute("gstinUserMapping") GSTINUserMapping gstinUserMapping, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = "";
		String pageRedirect = PageRedirectConstants.GET_GSTIN_USER_MAP_LIST_PAGE;
		if (!result.hasErrors()){
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			gstinUserMapping.setStatus("1");
			gstinUserMapping.setCreatedBy(loginMaster.getuId().toString());
			gstinUserMapping.setReferenceId(loginMaster.getuId());
			try {
				response = gstinUserMappingService.addGSTINUserMapping(gstinUserMapping);
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, gstinUserMappingAdditionSuccessful);
					gstinUserMapping = new GSTINUserMapping();
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, gstinUserMappingAdditionFailure);
				}
			} catch (Exception e) {
				logger.error("Error in:",e);
				model.addAttribute(GSTNConstants.RESPONSE, gstinUserMappingAdditionFailure);
			}
			
		}
		model.addAttribute("gstinUserMapping", gstinUserMapping);
		logger.info("Exit");
		return  pageRedirect;
	}
	
	@RequestMapping(value="/getGSTINUserMappingList",method=RequestMethod.POST)
	public @ResponseBody String getGSTINUserMappingList(Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		
		List<GSTINUserMapping> gstinUserMappingList = new ArrayList<GSTINUserMapping>();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			gstinUserMappingList = gstinUserMappingService.getGSTINUserMappingList(idsValuesToFetch);
			List<UserMaster> secondaryUserList = userMasterService.getSecondaryUsersList(loginMaster.getuId());
			
			for(UserMaster user : secondaryUserList){
				for(GSTINUserMapping gstnId : gstinUserMappingList){
					List<GSTINDetails> gstinList = new ArrayList<GSTINDetails>();
					if(gstnId.getReferenceUserId().compareTo(user.getId())==0){
						gstinList = gstinDetailsService.getGstinDetailsByUniqueIds(gstnId.getGstinId());
						StringBuilder GstinNoList = new StringBuilder();
						if(!gstinList.isEmpty()){
						for(GSTINDetails gstinNo : gstinList ){
							GstinNoList.append(gstinNo.getGstinNo()+", ");
						}
						GstinNoList.deleteCharAt(GstinNoList.lastIndexOf(", "));
						}
						
						gstnId.setGstinNo(GstinNoList);
						gstnId.setUserName(user.getUserName());
						break;
					}
				}
				
			}
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(gstinUserMappingList);
	}
	
	@RequestMapping(value = "/editGSTINUserMapping", method = RequestMethod.POST)
	public String editSecondaryUser(@ModelAttribute("gstinUserMapping") GSTINUserMapping gstinUserMapping, Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
			GSTINUserMapping p = new GSTINUserMapping();
			String pageRedirect = PageRedirectConstants.GSTIN_USER_MAP_EDIT_PAGE;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer gstinUserId =gstinUserMapping.getId();
			boolean isMappingValid = false;
			String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			isMappingValid = genericService.checkUserMappingValidation(idsValuesToFetch,getPrimIdsListQuery,gstinUserId,"GSTINUserMapping","referenceUserId");
				if(isMappingValid){
					p = gstinUserMappingService.getGSTINUserMappingById(gstinUserMapping.getId());
				}else{
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					pageRedirect = PageRedirectConstants.GET_GSTIN_USER_MAP_LIST_PAGE;
					model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
				}
			model.addAttribute("gstinUserMappingObj", p);
		
			model.addAttribute("editActionPerformed", true);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return pageRedirect;
	}
	
	
	@RequestMapping(value="/updateGstinUserMapping",method=RequestMethod.POST)
	public String updateGstinUserMapping(@Valid @ModelAttribute GSTINUserMapping gstinUserMapping ,BindingResult result,  HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.GET_GSTIN_USER_MAP_LIST_PAGE;
		String response = "";
		
		if (!result.hasErrors()){
			try {
				LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
				gstinUserMapping.setReferenceId(loginMaster.getuId());
				gstinUserMapping.setUpdatedBy(loginMaster.getuId().toString());
				Integer gstinUserId =gstinUserMapping.getId();
				boolean isMappingValid = false;
				String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
				isMappingValid = genericService.checkUserMappingValidation(idsValuesToFetch,getPrimIdsListQuery,gstinUserId,"GSTINUserMapping","referenceUserId");
						if(isMappingValid){
							response = gstinUserMappingService.updateGstinUserMapping(gstinUserMapping);
						}else{
							response = GSTNConstants.ACCESSVIOLATION;
							
						}
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, gstinUserMappingUpdationSuccessful);
				}else if(response.equals(GSTNConstants.FAILURE)){
					model.addAttribute(GSTNConstants.RESPONSE, gstinUserMappingUpdationFailure);
				}else if(response.equals(GSTNConstants.ACCESSVIOLATION)){
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					pageRedirect = PageRedirectConstants.GSTIN_USER_MAP_EDIT_PAGE;
					model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
				}
			} catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE, gstinUserMappingUpdationFailure);
				logger.error("Error in:",e);
			}
		}else{
			logger.error("Error occured"+result.getAllErrors());
		}
		model.addAttribute("gstinUserMapping", gstinUserMapping);
		logger.info("Exit");
		return  pageRedirect;
	}
	
	@RequestMapping(value = "/deleteGSTINUserMapping", method = RequestMethod.POST)
	public String deleteProduct(@ModelAttribute("gstinUserMapping") GSTINUserMapping gstinUserMapping,  BindingResult result,Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String response = "";
		String pageRedirect = PageRedirectConstants.GET_GSTIN_USER_MAP_LIST_PAGE;
	
		if (!result.hasErrors()){
	/*		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);		
			gstinUserMapping.setUpdatedBy(loginMaster.getuId().toString());
			gstinUserMapping.setStatus("0"); */
			try {
				LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
				Integer gstinUserId =gstinUserMapping.getId();
				boolean isMappingValid = false;
				String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
				isMappingValid = genericService.checkUserMappingValidation(idsValuesToFetch,getPrimIdsListQuery,gstinUserId,"GSTINUserMapping","referenceUserId");
						if(isMappingValid){
							response = gstinUserMappingService.removeGstinUserMapping(gstinUserMapping);
						}else{
							response = GSTNConstants.ACCESSVIOLATION;
						}
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, gstinUserMappingDeleteSuccessful);
				}else if(response.equals(GSTNConstants.FAILURE)){
					model.addAttribute(GSTNConstants.RESPONSE, gstinUserMappingDeleteFailure);	
				}else if(response.equals(GSTNConstants.ACCESSVIOLATION)){
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					pageRedirect = PageRedirectConstants.GET_GSTIN_USER_MAP_LIST_PAGE;
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
	
	
	@RequestMapping(value="/checkIfGSTINUserExists",method=RequestMethod.POST)
	public @ResponseBody String checkIfGSTINUserExists(@RequestParam("referenceUserId") Integer referenceUserId, HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		boolean isGstinUserexists = false;
		try {
			
			isGstinUserexists = gstinUserMappingService.checkIfGSTINUserExists(referenceUserId);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return new Gson().toJson(isGstinUserexists);
		
	}
	
	@RequestMapping(value="/getGstinNameList",method=RequestMethod.POST)
	public @ResponseBody String getGstinNameList(HttpServletRequest httpRequest) {
		logger.info("Entry");
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		List<UserMaster> secondaryUserList = new ArrayList<UserMaster>();
		try {
			 secondaryUserList = userMasterService.getSecondaryUsersList(loginMaster.getuId());
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
	
		logger.info("Exit");
		return new Gson().toJson(secondaryUserList);
		
	}
	
	@RequestMapping(value="/getGstinNoList",method=RequestMethod.POST)
	public @ResponseBody String getGstinNoList(HttpServletRequest httpRequest) {
		logger.info("Entry");
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		List<GSTINDetails> gstinDetailsList = new ArrayList<GSTINDetails>();
		try {
			  gstinDetailsList = gstinDetailsService.listGstinDetails(loginMaster.getuId());
				
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
	
		logger.info("Exit");
		return new Gson().toJson(gstinDetailsList);
		
	}
	
	
	
	@RequestMapping(value = "/getUserGstinMap", method = RequestMethod.GET)
	public String getUserGstinMappingListPage(Model model,HttpServletRequest httpRequest) {
		
		return PageRedirectConstants.GET_USER_GSTIN_MAP_LIST_PAGE;
	}
	

	@RequestMapping(value="/addUserGstinMapping",method=RequestMethod.POST)
	public String addUserGstinMapping(@Valid @ModelAttribute("gstinUserMapping") UserGSTINMapping userGSTINMapping, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = "";
		String pageRedirect = PageRedirectConstants.GET_USER_GSTIN_MAP_LIST_PAGE;
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
	
	@RequestMapping(value="/getUserGSTINMappingList",method=RequestMethod.POST)
	public @ResponseBody String getUserGSTINMappingList(Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		GSTINDetails gstinDetailsObj = null;
		List<UserGSTINMapping> gstinUserMappingList = new ArrayList<UserGSTINMapping>();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			gstinUserMappingList = gstinUserMappingService.getUserGSTINMappingList(idsValuesToFetch);
			
			List<UserGSTINMapping> removeUserGSTINMapping = new ArrayList<UserGSTINMapping>();
				for(UserGSTINMapping gstnId : gstinUserMappingList){
					gstinDetailsObj = gstinDetailsService.getGstinDetailsById(gstnId.getGstinId());
					List<UserMaster> userList = gstnId.getGstinUserSet();
					StringBuilder UserIdList = new StringBuilder();
					for(UserMaster userMaster : userList ){
						userMaster.setPassword("");
						if(userMaster.getStatus().equalsIgnoreCase("1")){
						UserIdList.append(userMaster.getUserName()+", ");
						}
					}
					if(UserIdList.length() != 0){
						UserIdList.deleteCharAt(UserIdList.lastIndexOf(", "));
						gstnId.setUserName(UserIdList);
						gstnId.setGstinNo(gstinDetailsObj.getGstinNo());
					} else{
						//gstinUserMappingList.remove(gstnId);		//commented bcz it throws "ConcurrentModificationException" as it removes
																	//an object from list while iterating
						removeUserGSTINMapping.add(gstnId);
					}						
				}
				if(!removeUserGSTINMapping.isEmpty()){
					gstinUserMappingList.removeAll(removeUserGSTINMapping);
				}
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(gstinUserMappingList);
	}
	
	@RequestMapping(value = "/editUserGSTINMapping", method = RequestMethod.POST)
	public String editUserGSTINMapping(@ModelAttribute("userGstinMapping") UserGSTINMapping userGstinMapping, Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		UserGSTINMapping gstinMapping = new UserGSTINMapping();
			String pageRedirect = PageRedirectConstants.USER_GSTIN_MAP_EDIT_PAGE;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			boolean isMappingValid = false;
		//	String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(),getPrimIdsListQuery,userGstinMapping.getId(),"UserGSTINMapping","refOrgId");
				if(isMappingValid){
					gstinMapping = gstinUserMappingService.getUserGSTINMappingById(userGstinMapping.getId());
				}else{
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					pageRedirect = PageRedirectConstants.GET_USER_GSTIN_MAP_LIST_PAGE;
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
	
	
	@RequestMapping(value="/updateUserGstinMapping",method=RequestMethod.POST)
	public String updateUserGstinMapping(@Valid @ModelAttribute UserGSTINMapping userGstinMapping ,BindingResult result,  HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.GET_USER_GSTIN_MAP_LIST_PAGE;
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
					pageRedirect = PageRedirectConstants.USER_GSTIN_MAP_EDIT_PAGE;
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
	
	@RequestMapping(value = "/deleteUserGSTINMapping", method = RequestMethod.POST)
	public String deleteUserGSTINMapping(@ModelAttribute("userGstinMapping") UserGSTINMapping userGstinMapping,  BindingResult result,Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String response = "";
		String pageRedirect = PageRedirectConstants.GET_USER_GSTIN_MAP_LIST_PAGE;
	
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
					pageRedirect = PageRedirectConstants.GET_USER_GSTIN_MAP_LIST_PAGE;
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
	
	@RequestMapping(value="/getGstinList",method=RequestMethod.POST)
	public @ResponseBody String getGstinList(HttpServletRequest httpRequest) {
		logger.info("Entry");
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		List<GSTINDetails> gstinIdList = new ArrayList<GSTINDetails>();
		try {
			
			gstinIdList = gstinDetailsService.listGstinDetails(loginMaster.getuId());
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
	
		logger.info("Exit");
		return new Gson().toJson(gstinIdList);
		
	}
		
	
}
