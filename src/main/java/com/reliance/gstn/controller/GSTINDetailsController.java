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
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.GstinLocation;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.State;
import com.reliance.gstn.service.GSTINAddressMappingService;
import com.reliance.gstn.service.GSTINDetailsService;
import com.reliance.gstn.service.GSTINUserMappingService;
import com.reliance.gstn.service.GenericService;
import com.reliance.gstn.service.GstinValidationService;
import com.reliance.gstn.service.StateService;
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
public class GSTINDetailsController {
	
	private static final Logger logger = Logger.getLogger(GSTINDetailsController.class);
	
	@Autowired
	private GSTINDetailsService gstinDetailsService;
	
	@Autowired
	private GSTINUserMappingService gstinUserMappingService;
	
	@Autowired
	private GSTINAddressMappingService gstinAddressMappingService;
	
	@Autowired
	private GenericService genericService;

	@Autowired
	private GstinValidationService gstinValidationService;
	
	
	@Value("${GSTIN_DETAILS_SUCESS}")
	private String gstinDetailsSuccessful;
	
	@Value("${GSTIN_DETAILS_FAILURE}")
	private String gstinDetailsFailure;
	
	@Value("${GSTIN_NO_DETAILS_FAILURE}")
	private String gstinNoDetailsFailure;
	
	@Value("${GSTIN_DETAILS_UPDATION_FAILURE}")
	private String gstinDetailsUpdationFailure;
	
	@Value("${GSTIN_DETAILS_UPDATION_SUCCESS}")
	private String gstinDetailsUpdationSuccessful;
	
	@Value("${GSTIN_DETAILS_DELETION_SUCCESS}")
	private String gstinDetailsDeletionSuccessful;
	
	@Value("${GSTIN_DETAILS_DELETION_FAILURE}")
	private String gstinDetailsDeletionFailure;
	
	@Value("${INVALID_MAPPING_EXCEPTION}")
	private String InvalidMappingException;
	
	@Value(value = "${get_prim_id_list_query}")
	private String getPrimIdsListQuery;
	
	@Value("${GSTIN_DETAILS_INVALID}")
	private String invalidGSTINDetails;
	
	@Value("${GSTIN_DETAILS_INVALID_UPDATE}")
	private String updateInvalidGSTINDetails;
	
	@ModelAttribute("gstinDetails")
	public GSTINDetails construct(){
		return new GSTINDetails();
	}
	
	@Autowired
	private UserMasterService userMasterService;
	
	@Autowired
	StateService stateService;
	
	
	
	@RequestMapping(value = "/addGstinDetails", method = RequestMethod.GET)
	public String gstinDetailsAddPage(Model model) {
		logger.info("Entry");
		/*
		try {
			List<State> stateList = stateService.listState();
			model.addAttribute("stateList", stateList);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");*/
		return PageRedirectConstants.GSTIN_DETAILS_PAGE;
	}
	
	
	@RequestMapping(value="/addGstinDetails",method=RequestMethod.POST)
	public String postGstinDetailsPage(@Valid @ModelAttribute("gstinDetails") GSTINDetails gstinDetails, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = "";
		String pageRedirect = PageRedirectConstants.GSTIN_DETAILS_PAGE;
		List<State> stateList = new ArrayList<State>();
		if (!result.hasErrors()){
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			try {
				gstinDetails.setStatus("1");
				gstinDetails.setReferenceId(loginMaster.getuId());
				gstinDetails.setCreatedBy(loginMaster.getuId().toString());
				stateList = stateService.listState();
				response = gstinDetailsService.addGstinDetails(gstinDetails); 
				
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, gstinDetailsSuccessful);
					gstinDetails =  new GSTINDetails();
				}else if(response.equals(GSTNConstants.INVALID_GSTIN)){
					model.addAttribute(GSTNConstants.RESPONSE, invalidGSTINDetails);
				}else {
					model.addAttribute(GSTNConstants.RESPONSE, gstinDetailsFailure);
				}
			} catch (Exception e) {
				logger.error("Error in:",e);
				model.addAttribute(GSTNConstants.RESPONSE, gstinNoDetailsFailure);
			}
			
		}
		
		model.addAttribute("stateList", stateList);
		model.addAttribute("gstinDetails", gstinDetails);
		
		
		logger.info("Exit");
		return  pageRedirect;
	}
	

	@RequestMapping(value="/getGstinDetails",method=RequestMethod.POST)
	public @ResponseBody String getGstinDetails(Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		List<GSTINDetails> gstinDetailsList = new ArrayList<GSTINDetails>();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			gstinDetailsList = gstinDetailsService.listGstinDetails(loginMaster.getuId());
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(gstinDetailsList);
	}
	
	@RequestMapping(value = "/editGstinDetails",method=RequestMethod.POST)
	public String manageOffersEditPage(@ModelAttribute GSTINDetails gstinDetails, Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String pageRedirect=PageRedirectConstants.GSTIN_DETAILS_EDIT_PAGE;
		GSTINDetails gstinDetailsObj = null;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer gstinId = gstinDetails.getId();
			boolean isMappingValid = false;
			
			String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			isMappingValid = genericService.checkUserMappingValidation(idsValuesToFetch,getPrimIdsListQuery,gstinId,"GSTINDetails","referenceId");
			
				if(isMappingValid){
					//gstinDetailsObj = gstinDetailsService.getGstinDetailsById(gstinDetails.getId());
					 gstinDetailsObj = gstinDetailsService.getGstinDetailsByIdEdit(gstinDetails.getId());
				}else{
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
					pageRedirect = PageRedirectConstants.GSTIN_DETAILS_PAGE;
				}
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		model.addAttribute("gstinDetailsObj", gstinDetailsObj);
		model.addAttribute("editActionPerformed", true);
		
		
		
		logger.info("Exit");
		return pageRedirect;
	}
	
	
	@RequestMapping(value="/updateGstinDetails",method=RequestMethod.POST)
	public String updateGstinDetails(@Valid @ModelAttribute GSTINDetails gstinDetails ,BindingResult result,  HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.GSTIN_DETAILS_PAGE;
		String response = "";
		List<State> stateList = new ArrayList<State>();
		
		if (!result.hasErrors()){
			try {
				LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
				gstinDetails.setReferenceId(loginMaster.getuId());
				gstinDetails.setUpdatedBy(loginMaster.getuId().toString());
				gstinDetails.setStatus("1");
				Integer gstinId =gstinDetails.getId();
				boolean isMappingValid = false;
				String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
				isMappingValid = genericService.checkUserMappingValidation(idsValuesToFetch,getPrimIdsListQuery,gstinId,"GSTINDetails","referenceId");
					if(isMappingValid){
						response = gstinDetailsService.updateGstinDetails(gstinDetails);
					}else{
						response=GSTNConstants.ACCESSVIOLATION;
					}
				 stateList = stateService.listState();
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, gstinDetailsUpdationSuccessful);
				}else if(response.equals(GSTNConstants.INVALID_GSTIN)){
					model.addAttribute(GSTNConstants.RESPONSE, updateInvalidGSTINDetails);
				}else if(response.equals(GSTNConstants.FAILURE)){
					model.addAttribute(GSTNConstants.RESPONSE, gstinDetailsUpdationFailure);
				}else if(response.equals(GSTNConstants.ACCESSVIOLATION)){
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					pageRedirect=PageRedirectConstants.GSTIN_DETAILS_EDIT_PAGE;
					model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
				}
			} catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE, gstinDetailsUpdationFailure);
				logger.error("Error in:",e);
			}
		}else{
			logger.error("Error occured"+result.getAllErrors());
		}
		
		model.addAttribute("stateList", stateList);
		model.addAttribute("gstinDetailsObj", gstinDetails);
		
		logger.info("Exit");
		return  pageRedirect;
	}
	
	@RequestMapping(value = "/deleteGstinDetails", method = RequestMethod.POST)
	public String deleteProduct(@ModelAttribute("gstinDetails") GSTINDetails gstinDetails,  BindingResult result,Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String response = "";
		String pageRedirect = PageRedirectConstants.GSTIN_DETAILS_PAGE;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		if (!result.hasErrors()){
			gstinDetails.setUpdatedBy(loginMaster.getuId().toString());
			gstinDetails.setStatus("0");
			try {
				Integer gstinId =gstinDetails.getId();
				boolean isMappingValid = false;
				String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
				isMappingValid = genericService.checkUserMappingValidation(idsValuesToFetch,getPrimIdsListQuery,gstinId,"GSTINDetails","referenceId");
					if(isMappingValid){
						response = gstinDetailsService.removeGSTINDetails(gstinDetails);
					}else{
						response = GSTNConstants.ACCESSVIOLATION;
					}
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, gstinDetailsDeletionSuccessful);
				}else if(response.equals(GSTNConstants.FAILURE)){
					model.addAttribute(GSTNConstants.RESPONSE, gstinDetailsDeletionFailure);	
				}else if(response.equals(GSTNConstants.ACCESSVIOLATION)){
					pageRedirect = PageRedirectConstants.GSTIN_DETAILS_PAGE;
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
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
	
	@RequestMapping(value="/getGSTINListAsPerRole",method=RequestMethod.GET)
	public @ResponseBody String getGSTINUserMappingListAsPerRole(Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");
		List<GSTINDetails> gstinList = getGSTINUserMappingListAsPerRoleX(httpRequest);
		logger.info("Exit");
		return new Gson().toJson(gstinList);
	}
	
	public List<GSTINDetails> getGSTINUserMappingListAsPerRoleX(HttpServletRequest httpRequest){
		logger.info("Entry");
		List<GSTINDetails> gstinList = new ArrayList<GSTINDetails>();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			if(loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
				gstinList =  gstinDetailsService.listGstinDetails(loginMaster.getuId());
			}else{
				gstinList = getGSTINListForSecondaryUsers(loginMaster);
			}
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return gstinList;
	}


	private List<GSTINDetails> getGSTINListForSecondaryUsers(LoginMaster loginMaster) {
		logger.info("Entry");
		
		List<GSTINDetails> gstinList = new ArrayList<GSTINDetails>();
		try {
			/*String gstinUserMapped = gstinUserMappingService.getGSTINUserMappingByReferenceUserId(loginMaster.getuId());
			gstinList = gstinDetailsService.getGstinDetailsByUniqueIds(gstinUserMapped);*/
			gstinList = gstinDetailsService.getGstinDetailsMappedForSecondaryUser(loginMaster.getuId());
			for(GSTINDetails aa : gstinList){
				List<GstinLocation> gstinLocationSet = gstinDetailsService.getSecondaryMappedLocations(aa.getId(),loginMaster.getuId());
				aa.setGstinLocationSet(gstinLocationSet);
			}
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return gstinList;
	}
	
	@RequestMapping(value="/getGstinDetailsFromGstinNo",method=RequestMethod.POST)
	public @ResponseBody String getGstinDetailsFromGstinNo(@RequestParam("gstinNo") String gstinNo, HttpServletRequest httpRequest) {
		logger.info("Entry");
		//System.out.println("getGstinDetailsFromGstinNo : "+gstinNo);
		GSTINDetails gstinDetails = null;
		
		try {
			 
			 LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			 gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(gstinNo,loginMaster.getPrimaryUserUId());
			 if(loginMaster.getUserRole().equals(GSTNConstants.SECONDARY_USER)){
				 List<GstinLocation> gstinLocationSet = gstinDetailsService.getSecondaryMappedLocations(gstinDetails.getId(),loginMaster.getuId());
				 gstinDetails.setGstinLocationSet(gstinLocationSet);
			 }
			 if(gstinDetails.getGstnUserId() != null && gstinDetails.getGstnUserId() != ""){
				 gstinDetails.setGstnUId(true);
			 } else {
				 gstinDetails.setGstnUId(false);
			 }
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(gstinDetails);
		
	}

	@RequestMapping(value="/checkIfGstinIsValidWithRegisteredPAN",method=RequestMethod.POST)
	public @ResponseBody String checkIfGstinIsValidWithRegisteredPAN(@RequestParam("panNo") String panNo,HttpServletRequest request) {
		
		logger.info("Entry");	
		boolean gstinIsValidWithRegisteredPan = false;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
			Integer orgId =loginMaster.getOrgUId();
			gstinIsValidWithRegisteredPan = gstinDetailsService.checkIfGstinIsValidWithRegisteredPAN(orgId,panNo);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(gstinIsValidWithRegisteredPan);
	}
	
	
	
	@RequestMapping(value="/removeGstinLocationById",method=RequestMethod.POST)
	public @ResponseBody String removeGstinLocationById(@RequestParam("locId") Integer locId ,HttpServletRequest httpRequest) {
		logger.info("Entry");
		String response="";
		try {
			response=gstinDetailsService.removeGstinLocationById(locId);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(response);
	}
	
	
	@RequestMapping(value="/getGstinLocationDetails",method=RequestMethod.POST)
	public @ResponseBody String getGstinLocationDetails(@RequestParam("gstinId") Integer gstinId, Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		List<GstinLocation> gstinLocationDetailsList = new ArrayList<GstinLocation>();
		try {
			gstinLocationDetailsList = gstinDetailsService.listGstinLocationDetails(gstinId);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(gstinLocationDetailsList);
	}
	
	
	@RequestMapping(value="/getDataByGstinNumber",method=RequestMethod.POST)
	public @ResponseBody String getDataByGstinNumber(@RequestParam("gstinNo") String gstinNo,HttpServletRequest request) {
		
		logger.info("Entry");	
		String response = "";
		try {
			response = gstinValidationService.isValidGstin(gstinNo);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		//return new Gson().toJson(response);
		return response;
	}
	
	@RequestMapping(value="/saveGstinUserId", method=RequestMethod.POST)
	public @ResponseBody String saveGSTNUserId(@RequestParam("gstinId") String gstinId, @RequestParam("gstnUserId") String gstnUserId, @RequestParam("grossTurnover") String grossTurnover, @RequestParam("currentTurnover") String currentTurnover, Model model, HttpServletRequest request){
		
		logger.info("Entry");
		String response = "";
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		
		try{
			
			Map<String, Object> mapValues = new HashMap<String, Object>();
			mapValues.put("GstnUserId", gstnUserId);
			mapValues.put("GrossTurnover", grossTurnover);
			mapValues.put("CurrentTurnover", currentTurnover);
			mapValues.put("gstinId", gstinId);
			mapValues.put("loginMaster", loginMaster);
			
			response = gstinDetailsService.updateUserGstinDetails(mapValues);
			
		}catch(Exception e){
			
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(response);
		
	}
	
	
	/*@RequestMapping(value = "/getgstinforloggedinuser1" , method = RequestMethod.POST)
	public @ResponseBody String getGstinForloggedInUser(HttpServletRequest httpRequest){
		logger.info("Entry");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
			UserMaster userMaster = userMasterService.getUserMasterById(loginMaster.getuId());			
			if(loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){										Check if user is primary or secondary
				responseMap=gstinDetailsService.getGSTINListwithms(loginMaster.getuId());
			}else{
				responseMap = gstinDetailsService.getGstinDetailsMappedForSecondaryUserByUid(loginMaster.getuId());
				userMaster = userMasterService.getUserMasterById(userMaster.getReferenceId());
			}			
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(responseMap);		
	}*/
	
	
	public Map<String, Object> checkIfGstinBelongsToUser(HttpServletRequest httpRequest,String gstnNo, String location) {
		logger.info("Entry");
		Map<String,Object> mapResponse = new HashMap<String,Object>(); 
		List<GSTINDetails> gstinList = getGSTINUserMappingListAsPerRoleX(httpRequest);
		boolean isValid = false;
		String status = GSTNConstants.ACCESSVIOLATION;
		if(gstinList != null && gstinList.size() > 0){
			for(GSTINDetails gstin : gstinList){
				if(gstin.getGstinNo().equals(gstnNo)){
					if(gstin.getGstinLocationSet() != null ){
						for(GstinLocation gstnLoc : gstin.getGstinLocationSet()){
							if(gstnLoc.getUniqueSequence().equals(location)){
								isValid = true;
								status = GSTNConstants.ALLOWED_ACCESS;
								mapResponse.put("GSTINDetails", gstin);
								break;
							}
						}
					}
				}
			}
		}
		mapResponse.put(GSTNConstants.STATUS,status);
		mapResponse.put(GSTNConstants.RESPONSE, isValid);
		logger.info("Exit");
		return mapResponse;
	}


	public Integer fetchLocationFromUniqueSequence(GSTINDetails gstin, String locationUniqueSequence) {
		Integer location = null;
		for(GstinLocation loc : gstin.getGstinLocationSet()){
			if(loc.getUniqueSequence().equals(locationUniqueSequence)){
				location = loc.getId();
			}
		}
		return location;
	}
	
	@RequestMapping(value="/getGSTINForProductServices",method=RequestMethod.POST)
    public @ResponseBody String getGSTINForProductServices(Model model,HttpServletRequest httpRequest) {
          logger.info("Entry");
          List<GSTINDetails> gstinList = new ArrayList<GSTINDetails>();
          try {
                LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
                if(loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
                      gstinList =  gstinDetailsService.listGstinDetails(loginMaster.getuId());
                }else{
                      gstinList = gstinDetailsService.listGstinDetails(loginMaster.getPrimaryUserUId());
                }
          } catch (Exception e) {
                logger.error("Error in:",e);
          }
          logger.info("Exit");
          return new Gson().toJson(gstinList);
    }
	
	@RequestMapping(value="/getGstinDetailsAndCity",method=RequestMethod.POST)
	public @ResponseBody String getGstinDetailsAndCity(Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		List<GSTINDetails> gstinDetailsList = new ArrayList<GSTINDetails>();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			gstinDetailsList = gstinDetailsService.listGstinDetailsAndCity(loginMaster.getuId());
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(gstinDetailsList);
	}

}
