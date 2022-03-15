package com.reliance.gstn.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.State;
import com.reliance.gstn.service.GSTINDetailsService;
import com.reliance.gstn.service.GenericService;
import com.reliance.gstn.service.StateService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

@Controller
public class WizardGSTINDetailsController {

	private static final Logger logger = Logger.getLogger(WizardGSTINDetailsController.class);
	
	@Autowired
	private GSTINDetailsService gstinDetailsService;

	@Autowired
	StateService stateService;

	@Autowired
	private UserMasterService userMasterService;

	@Autowired
	private GenericService genericService;


	@Value("${GSTIN_DETAILS_SUCESS}")
	private String gstinDetailsSuccessful;
	
	@Value("${GSTIN_DETAILS_FAILURE}")
	private String gstinDetailsFailure;

	@Value("${GSTIN_DETAILS_INVALID}")
	private String invalidGSTINDetails;

	@Value("${GSTIN_NO_DETAILS_FAILURE}")
	private String gstinNoDetailsFailure;

	@Value(value = "${get_prim_id_list_query}")
	private String getPrimIdsListQuery;

	@Value("${INVALID_MAPPING_EXCEPTION}")
	private String InvalidMappingException;

	@Value("${GSTIN_DETAILS_UPDATION_SUCCESS}")
	private String gstinDetailsUpdationSuccessful;

	@Value("${GSTIN_DETAILS_INVALID_UPDATE}")
	private String updateInvalidGSTINDetails;

	@Value("${GSTIN_DETAILS_UPDATION_FAILURE}")
	private String gstinDetailsUpdationFailure;

	@Value("${GSTIN_DETAILS_DELETION_SUCCESS}")
	private String gstinDetailsDeletionSuccessful;
	
	@Value("${GSTIN_DETAILS_DELETION_FAILURE}")
	private String gstinDetailsDeletionFailure;
	
	

	@ModelAttribute("gstinDetails")
	public GSTINDetails construct(){
		return new GSTINDetails();
	}
	
	@RequestMapping(value = "/wizardListGstinDetails", method = RequestMethod.GET)
	public String wizardListGstinDetailsPage(Model model) {
		return PageRedirectConstants.WIZARD_List_GSTIN_DETAILS_PAGE;
	}
	
	
	@RequestMapping(value = "/wizardAddGstinDetails", method = RequestMethod.GET)
	public String wizardGstinDetailsAddPage(Model model) {
		return PageRedirectConstants.WIZARD_Add_GSTIN_DETAILS_PAGE;
	}
	
	@RequestMapping(value="/wizardSaveGstinDetails",method=RequestMethod.POST)
	public String wizardPostGstinDetailsPage(@Valid @ModelAttribute("gstinDetails") GSTINDetails gstinDetails, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = "";
		String pageRedirect = PageRedirectConstants.WIZARD_Add_GSTIN_DETAILS_PAGE;
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
					pageRedirect = PageRedirectConstants.WIZARD_List_GSTIN_DETAILS_PAGE;
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

	@RequestMapping(value = "/wizardEditGstinDetails",method=RequestMethod.POST)
	public String wizardManageOffersEditPage(@ModelAttribute GSTINDetails gstinDetails, Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String pageRedirect=PageRedirectConstants.WIZARD_GSTIN_DETAILS_EDIT_PAGE;
		GSTINDetails gstinDetailsObj = null;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer gstinId = gstinDetails.getId();
			boolean isMappingValid = false;
			String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			isMappingValid = genericService.checkUserMappingValidation(idsValuesToFetch,getPrimIdsListQuery,gstinId,"GSTINDetails","referenceId");			
				if(isMappingValid){
					gstinDetailsObj = gstinDetailsService.getGstinDetailsById(gstinDetails.getId());
				}else{
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
					pageRedirect = PageRedirectConstants.WIZARD_List_GSTIN_DETAILS_PAGE;
				}
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		model.addAttribute("gstinDetailsObj", gstinDetailsObj);
		model.addAttribute("editActionPerformed", true);
		logger.info("Exit");
		return pageRedirect;
	}

	@RequestMapping(value="/wizardUpdateGstinDetails",method=RequestMethod.POST)
	public String wizardUpdateGstinDetails(@Valid @ModelAttribute GSTINDetails gstinDetails ,BindingResult result,  HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.WIZARD_List_GSTIN_DETAILS_PAGE;
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
					pageRedirect=PageRedirectConstants.WIZARD_GSTIN_DETAILS_EDIT_PAGE;
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

	@RequestMapping(value = "/wizardDeleteGstinDetails", method = RequestMethod.POST)
	public String wizardDeleteProduct(@ModelAttribute("gstinDetails") GSTINDetails gstinDetails,  BindingResult result,Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String response = "";
		String pageRedirect = PageRedirectConstants.WIZARD_List_GSTIN_DETAILS_PAGE;
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
					pageRedirect = PageRedirectConstants.WIZARD_List_GSTIN_DETAILS_PAGE;
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
	
	
}
