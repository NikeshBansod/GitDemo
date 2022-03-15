package com.reliance.gstn.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.ManageServiceCatalogue;
import com.reliance.gstn.service.GenericService;
import com.reliance.gstn.service.ManageServiceCatalogueService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

@Controller
public class WizardManageServiceCatalogueController {

	private static final Logger logger = Logger.getLogger(WizardManageServiceCatalogueController.class);
	
	@Autowired
	public ManageServiceCatalogueService manageServiceCatalogueService;

	@Autowired
	private GenericService genericService;
	
	@Value("${SERVICE_CATALOGUE_SUCESS}")
	private String serviceCatalogueSuccessful;

	@Value("${SERVICE_CATALOGUE_FAILURE}")
	private String serviceCatalogueFailure;

	@Value("${DUPLICATE_SERVICE_CATALOGUE_FAILURE}")
	private String dupalicateServiceCatalogueFailure;

	@Value(value = "${get_prim_id_list_query}")
	private String getPrimIdsListQuery;

	@Value("${INVALID_MAPPING_EXCEPTION}")
	private String InvalidMappingException;

	@Value("${SERVICE_CATALOGUE_UPDATION_SUCESS}")
	private String serviceCatalogueUpdationSuccessful;

	@Value("${SERVICE_CATALOGUE_UPDATION_FAILURE}")
	private String serviceCatalogueUpdationFailure;

	@Value("${DUPLICATE_SERVICE_CATALOGUE_UPDATION_FAILURE}")
	private String dupalicateServiceCatalogueUpdationFailure;

	@Value("${SERVICE_CATALOGUE_DELETION_SUCESS}")
	private String serviceCatalogueDeletionSuccessful;

	@Value("${SERVICE_CATALOGUE_DELETION_FAILURE}")
	private String serviceCatalogueDeletionFailure;
	
	
	@ModelAttribute("manageServiceCatalogue")
	public ManageServiceCatalogue construct(){
		return new ManageServiceCatalogue();
	}
	
	@RequestMapping(value = "/wizardManageServiceCatalogue", method = RequestMethod.GET)
	public String wizardManageServiceCatalogueViewPage(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {		
		return PageRedirectConstants.WIZARD_MANAGE_MY_SERVICE_CATALOGUE_LIST_PAGE;
	}
	
	@RequestMapping(value="/wizardAddManageServiceCatalogue",method=RequestMethod.POST)
	public String wizardPostManageMyServiceCataloguePage(@Valid @ModelAttribute("manageServiceCatalogue") ManageServiceCatalogue manageServiceCatalogue, BindingResult result,Model model,HttpServletRequest httpRequest,  HttpSession httpSession) {
		logger.info("Entry");	
		String response = null;
		Map<String,Object> mapResponse = new HashMap<String,Object>();
		String pageRedirect = PageRedirectConstants.WIZARD_MANAGE_MY_SERVICE_CATALOGUE_LIST_PAGE;
		try {
		if (!result.hasErrors()){
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer orgUId =loginMaster.getOrgUId();
			
			manageServiceCatalogue.setStatus("1");
			manageServiceCatalogue.setCreatedBy(loginMaster.getuId().toString());
			manageServiceCatalogue.setReferenceId(loginMaster.getuId());
			manageServiceCatalogue.setRefOrgId(orgUId);
			/*if(manageServiceCatalogue.getOtherUOM()!=""){
				manageServiceCatalogue.setUnitOfMeasurement(manageServiceCatalogue.getTempUom());
			}*/
			mapResponse = manageServiceCatalogueService.addManageServiceCatalogue(manageServiceCatalogue);
				if(mapResponse.get(GSTNConstants.RESPONSE).equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, serviceCatalogueSuccessful);
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, serviceCatalogueFailure);
				}
		}
		} catch(ConstraintViolationException e){
			model.addAttribute(GSTNConstants.RESPONSE, dupalicateServiceCatalogueFailure);
		}catch (Exception e) {
			model.addAttribute(GSTNConstants.RESPONSE, serviceCatalogueFailure);
		}
		model.addAttribute("manageServiceCatalogue", new ManageServiceCatalogue());
		logger.info("Exit");
		return  pageRedirect;
	}
	
	@RequestMapping(value = "/wizardEditManageServiceCatalogue")
	public String manageServiceCatalogueEditPage(@ModelAttribute ManageServiceCatalogue manageServiceCatalogue, Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.WIZARD_MANAGE_SERVICE_CATALOGUE_EDIT_PAGE;
		ManageServiceCatalogue manageServiceCatalogueObj = new ManageServiceCatalogue();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer serviceId =manageServiceCatalogue.getId();
			boolean isMappingValid = false;
		//	String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(), getPrimIdsListQuery,serviceId,"ManageServiceCatalogue","refOrgId");
				if(isMappingValid){
					manageServiceCatalogueObj = manageServiceCatalogueService.getManageServiceCatalogueById(manageServiceCatalogue.getId());
				}else{
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
					pageRedirect = PageRedirectConstants.WIZARD_MANAGE_MY_SERVICE_CATALOGUE_LIST_PAGE;
				}
			model.addAttribute("manageServiceCatalogueObj", manageServiceCatalogueObj);
			model.addAttribute("editActionPerformed", true);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value="/wizardUpdateManageServiceCatalogue",method=RequestMethod.POST)
	public String updateManageServiceCatalogue(@Valid @ModelAttribute ManageServiceCatalogue manageServiceCatalogue ,BindingResult result,  HttpServletRequest httpRequest,HttpSession httpSession, Model model) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.WIZARD_MANAGE_SERVICE_CATALOGUE_EDIT_PAGE;
		String response = null;
		
		if (!result.hasErrors()){
			try {
				LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
				Integer orgUId =loginMaster.getOrgUId();
				manageServiceCatalogue.setReferenceId(loginMaster.getuId());
				manageServiceCatalogue.setUpdatedBy(loginMaster.getuId().toString());
				manageServiceCatalogue.setRefOrgId(orgUId);
				Integer serviceId =manageServiceCatalogue.getId();
				/*if(manageServiceCatalogue.getOtherUOM()!=""){
					manageServiceCatalogue.setUnitOfMeasurement(manageServiceCatalogue.getTempUom());
				}*/
				boolean isMappingValid = false;
			//	String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
				isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(), getPrimIdsListQuery,serviceId,"ManageServiceCatalogue","refOrgId");
					if(isMappingValid){
						response = manageServiceCatalogueService.updateManageServiceCatalogue(manageServiceCatalogue);
					}else{
						response = GSTNConstants.ACCESSVIOLATION;
					}
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, serviceCatalogueUpdationSuccessful);
					model.addAttribute("manageServiceCatalogue", new ManageServiceCatalogue());
					pageRedirect = PageRedirectConstants.WIZARD_MANAGE_MY_SERVICE_CATALOGUE_LIST_PAGE;
				}else if(response.equals(GSTNConstants.FAILURE)){
					model.addAttribute(GSTNConstants.RESPONSE, serviceCatalogueUpdationFailure);
					model.addAttribute("manageServiceCatalogue", manageServiceCatalogue);
				}else if(response.equals(GSTNConstants.ACCESSVIOLATION)){
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
					pageRedirect = PageRedirectConstants.WIZARD_MANAGE_SERVICE_CATALOGUE_EDIT_PAGE;
					model.addAttribute("manageServiceCatalogue", manageServiceCatalogue);
				}
			} catch(DataIntegrityViolationException e){
				model.addAttribute(GSTNConstants.RESPONSE,dupalicateServiceCatalogueUpdationFailure);
				model.addAttribute("manageServiceCatalogue", manageServiceCatalogue);
			}	catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE, serviceCatalogueUpdationFailure);
				model.addAttribute("manageServiceCatalogue", manageServiceCatalogue);
			}
		}else{
			logger.error("Error occured"+result.getAllErrors());
		}
		
		logger.info("Exit");
		return  pageRedirect;
	}
	
	@RequestMapping(value="/wizardDeleteManageServiceCatalogue", method = RequestMethod.POST)
	public String deleteManageServiceCatalogue(@ModelAttribute("manageServiceCatalogue") ManageServiceCatalogue manageServiceCatalogue, Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		
		String response = null;
		String pageRedirect = PageRedirectConstants.WIZARD_MANAGE_MY_SERVICE_CATALOGUE_LIST_PAGE;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		try {
			Integer serviceId =manageServiceCatalogue.getId();
			boolean isMappingValid = false;
			//String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(), getPrimIdsListQuery,serviceId,"ManageServiceCatalogue","refOrgId");
			manageServiceCatalogue.setUpdatedBy(loginMaster.getuId().toString());
			manageServiceCatalogue.setStatus("0");
				if(isMappingValid){
					response = manageServiceCatalogueService.removeManageServiceCatalogue(manageServiceCatalogue);
				}else{
					response = GSTNConstants.ACCESSVIOLATION;
				}
			if(response.equals(GSTNConstants.SUCCESS)){
				model.addAttribute(GSTNConstants.RESPONSE, serviceCatalogueDeletionSuccessful);
			}else if(response.equals(GSTNConstants.FAILURE)){
				model.addAttribute(GSTNConstants.RESPONSE, serviceCatalogueDeletionFailure);
			}else if(response.equals(GSTNConstants.ACCESSVIOLATION)){
				logger.info("INVALID ACCESS"+loginMaster.getUserId());
				pageRedirect = PageRedirectConstants.WIZARD_MANAGE_MY_SERVICE_CATALOGUE_LIST_PAGE;
				model.addAttribute(GSTNConstants.RESPONSE,InvalidMappingException);
			}
		} catch (Exception e) {
			model.addAttribute(GSTNConstants.RESPONSE, serviceCatalogueDeletionFailure);
		}
		
		logger.info("Exit");
		return pageRedirect;
	}
	
}
