/**
 * 
 */
package com.reliance.gstn.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.ManageServiceCatalogue;
import com.reliance.gstn.model.ServiceBean;
import com.reliance.gstn.model.ServiceFetch;
import com.reliance.gstn.model.StoresBean;
import com.reliance.gstn.service.GenericService;
import com.reliance.gstn.service.ManageServiceCatalogueService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
//import com.reliance.gstn.util.LoggerUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Nikesh.Bansod
 *
 */
@Controller
public class ManageServiceCatalogueController {
	
	private static final Logger logger = Logger.getLogger(ManageServiceCatalogueController.class);
	
	@Autowired
	public ManageServiceCatalogueService manageServiceCatalogueService;
	
	@Autowired
	private UserMasterService userMasterService;
	
	@Autowired
	private GenericService genericService;
	
	@Value("${SERVICE_CATALOGUE_SUCESS}")
	private String serviceCatalogueSuccessful;
	
	@Value("${SERVICE_CATALOGUE_FAILURE}")
	private String serviceCatalogueFailure;
	
	@Value("${DUPLICATE_SERVICE_CATALOGUE_FAILURE}")
	private String dupalicateServiceCatalogueFailure;
	
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
	
	@Value("${INVALID_MAPPING_EXCEPTION}")
	private String InvalidMappingException;
	
	@Value(value = "${get_prim_id_list_query}")
	private String getPrimIdsListQuery;
	
	@Autowired
	private GSTINDetailsController gstinDetailsController;
	
	@ModelAttribute("manageServiceCatalogue")
	public ManageServiceCatalogue construct(){
		return new ManageServiceCatalogue();
	}
	
	@RequestMapping(value = "/manageServiceCatalogue", method = RequestMethod.GET)
	public String manageServiceCatalogueViewPage(Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		
		return PageRedirectConstants.MANAGE_MY_SERVICE_CATALOGUE_LIST_PAGE;
	}
	
	@RequestMapping(value = "/addManageServiceCatalogue", method = RequestMethod.GET)
	public String manageMyServiceCatalogueAddPage(Model model) {
		return PageRedirectConstants.MANAGE_SERVICE_CATALOGUE_PAGE;
	}
	
	@RequestMapping(value="/addManageServiceCatalogue",method=RequestMethod.POST)
	public String postManageMyServiceCataloguePage(@Valid @ModelAttribute("manageServiceCatalogue") ManageServiceCatalogue manageServiceCatalogue, BindingResult result,Model model,HttpServletRequest httpRequest,  HttpSession httpSession) {
		logger.info("Entry");	
		String response = null;
		Map<String,Object> mapResponse = new HashMap<String,Object>();
		String pageRedirect = PageRedirectConstants.MANAGE_MY_SERVICE_CATALOGUE_LIST_PAGE;
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
	
	
	
	@RequestMapping(value = "/editManageServiceCatalogue")
	public String manageServiceCatalogueEditPage(@ModelAttribute ServiceFetch serviceFetch, Model model, HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.MANAGE_SERVICE_CATALOGUE_EDIT_PAGE;
		ServiceFetch serviceFetchObj = new ServiceFetch();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer serviceId =serviceFetch.getId();
			boolean isMappingValid = false;
		//	String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(), getPrimIdsListQuery,serviceId,"ManageServiceCatalogue","refOrgId");
				if(isMappingValid){
					//manageServiceCatalogueObj = manageServiceCatalogueService.getManageServiceCatalogueById(manageServiceCatalogue.getId());
					serviceFetchObj = manageServiceCatalogueService.getManageServiceCatalogueByIdFetch(serviceFetch.getId());
				}else{
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
					pageRedirect = PageRedirectConstants.MANAGE_MY_SERVICE_CATALOGUE_LIST_PAGE;
				}
		
			model.addAttribute("manageServiceCatalogueObj", serviceFetchObj);
		/*	model.addAttribute("stateName",  manageServiceCatalogue.getGstinLocationServiceFetch().getGstinLocation());
			model.addAttribute("gstinNo",  manageServiceCatalogue.getGstinLocationServiceFetch().getGstindetailsfetch().getGstinNo());*/
			model.addAttribute("editActionPerformed", true);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value="/updateManageServiceCatalogue",method=RequestMethod.POST)
	public String updateManageServiceCatalogue(@Valid @ModelAttribute ManageServiceCatalogue manageServiceCatalogue ,BindingResult result,  HttpServletRequest httpRequest,HttpSession httpSession, Model model) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.MANAGE_SERVICE_CATALOGUE_EDIT_PAGE;
		String response = null;
		
		if (!result.hasErrors()){
			try {
				LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
				Integer orgUId =loginMaster.getOrgUId();
				manageServiceCatalogue.setReferenceId(loginMaster.getuId());
				manageServiceCatalogue.setUpdatedBy(loginMaster.getuId().toString());
				manageServiceCatalogue.setRefOrgId(orgUId);
				manageServiceCatalogue.setUnitOfMeasurement("Na");
				manageServiceCatalogue.setAdvolCess((double) 0);
				manageServiceCatalogue.setNonAdvolCess((double) 0);
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
					pageRedirect = PageRedirectConstants.MANAGE_MY_SERVICE_CATALOGUE_LIST_PAGE;
				}else if(response.equals(GSTNConstants.FAILURE)){
					model.addAttribute(GSTNConstants.RESPONSE, serviceCatalogueUpdationFailure);
					model.addAttribute("manageServiceCatalogue", manageServiceCatalogue);
				}else if(response.equals(GSTNConstants.ACCESSVIOLATION)){
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
					pageRedirect = PageRedirectConstants.MANAGE_SERVICE_CATALOGUE_EDIT_PAGE;
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
	
	@RequestMapping(value="/deleteManageServiceCatalogue", method = RequestMethod.POST)
	public String deleteManageServiceCatalogue(@ModelAttribute("manageServiceCatalogue") ManageServiceCatalogue manageServiceCatalogue, Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		
		String response = null;
		String pageRedirect = PageRedirectConstants.MANAGE_MY_SERVICE_CATALOGUE_LIST_PAGE;
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
				pageRedirect = PageRedirectConstants.MANAGE_MY_SERVICE_CATALOGUE_LIST_PAGE;
				model.addAttribute(GSTNConstants.RESPONSE,InvalidMappingException);
			}
		} catch (Exception e) {
			model.addAttribute(GSTNConstants.RESPONSE, serviceCatalogueDeletionFailure);
		}
		
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value="/getServicesList",method=RequestMethod.POST)
	public @ResponseBody String getServicesList(Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		List<ManageServiceCatalogue> servicesList = new ArrayList<ManageServiceCatalogue>();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer orgUId =loginMaster.getOrgUId();
			String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			servicesList = manageServiceCatalogueService.getServicesList(idsValuesToFetch,orgUId);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(servicesList);
	}
	
	@RequestMapping(value="/getManageServiceById",method=RequestMethod.POST)
	public @ResponseBody String getManageServiceById(@RequestParam("serviceId") Integer id, HttpServletRequest httpRequest) {
		logger.info("Entry");
		ManageServiceCatalogue serviceDetail = new ManageServiceCatalogue();
		try {
			serviceDetail = manageServiceCatalogueService.getManageServiceCatalogueById(id);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(serviceDetail);
		
	}
	
	@RequestMapping(value="/checkIfServiceExists",method=RequestMethod.POST)
	public @ResponseBody String checkIfServiceExists(@RequestParam("service") String service, HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		boolean isProductexists = false;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer orgUId =loginMaster.getOrgUId();
			isProductexists = manageServiceCatalogueService.checkIfServiceExists(service,orgUId);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return new Gson().toJson(isProductexists);
		
	}
	
	
	@RequestMapping(value = "/getServiceNameList", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody List<String> getServiceNameList(@RequestParam("term") String query, @RequestParam("gNo") String gstnNo,@RequestParam("location") String locationUniqueSequence,@RequestParam("documentType") String defaultDocumentType, HttpServletRequest httpRequest) {
		String serviceTypeCall = null;
		Map<String,Object>mapResponse = new HashMap<String,Object>(); 
		List<String> serviceList = null;
		System.out.println("gstnNo : "+gstnNo+",locationUniqueSequence:"+locationUniqueSequence+",documentType :"+defaultDocumentType);
		if(defaultDocumentType.equals("invoice") || defaultDocumentType.equals("rcInvoice") || defaultDocumentType.equals("eComInvoice")){ 
			serviceTypeCall = "invoice";
		}else if(defaultDocumentType.equals("billOfSupply") || defaultDocumentType.equals("eComBillOfSupply")){
			serviceTypeCall = "billOfSupply";
		}
		
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		mapResponse = gstinDetailsController.checkIfGstinBelongsToUser(httpRequest,gstnNo,locationUniqueSequence);
		if(mapResponse.get(GSTNConstants.RESPONSE).equals(true)){
			GSTINDetails gstin = (GSTINDetails)mapResponse.get("GSTINDetails");
			Integer location = gstinDetailsController.fetchLocationFromUniqueSequence(gstin,locationUniqueSequence);
			serviceList = getServiceNameListAutoComplete(query,loginMaster.getOrgUId(),serviceTypeCall,location);
		}
		
		return serviceList;
	}

	private List<String> getServiceNameListAutoComplete(String query,Integer orgUId,String serviceTypeCall,Integer location) {
		 
	     query = query.toLowerCase();
	     List<String> matched = new ArrayList<String>();
	     matched = manageServiceCatalogueService.getServiceNameListByAutoComplete(query, orgUId,serviceTypeCall,location);
	        /*for(int i=0; i < serviceNameList.size(); i++) {
	         	Object[] obj = distinctHSNCodeList.get(i);
	         	hsnCode = (String)obj[0];
	         	hsnDesc = (String)obj[1];
	         	 matched.add("["+hsnCode+"] - "+hsnDesc);
	         	
	        }*/
	     return matched;
	}
	
	@RequestMapping(value="/getManageServiceByServiceName",method=RequestMethod.POST)
	public @ResponseBody String getManageServiceByServiceName(@RequestParam("serviceName") String serviceName, HttpServletRequest httpRequest) {
		logger.info("Entry");
		ManageServiceCatalogue serviceDetail = new ManageServiceCatalogue();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			serviceDetail = manageServiceCatalogueService.getManageServiceCatalogueByName(serviceName,loginMaster.getOrgUId());
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(serviceDetail);
		
	}
	
	@RequestMapping(value="/addServiceDynamically",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> addServiceDynamically(@Valid @RequestBody ManageServiceCatalogue manageServiceCatalogue, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = null;
		String status = GSTNConstants.FAILURE;
		Map<String,Object> mapResponse = new HashMap<String,Object>();
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		if (!result.hasErrors()){
			try {
				manageServiceCatalogue.setStatus("1");
				manageServiceCatalogue.setCreatedBy(loginMaster.getuId().toString());
				manageServiceCatalogue.setReferenceId(loginMaster.getuId());
				manageServiceCatalogue.setRefOrgId(loginMaster.getOrgUId());
				mapResponse = manageServiceCatalogueService.addManageServiceCatalogue(manageServiceCatalogue);
				if(mapResponse.get(GSTNConstants.RESPONSE).equals(GSTNConstants.SUCCESS)){
					response = serviceCatalogueSuccessful;
					status = GSTNConstants.SUCCESS;
				}else{
					response = serviceCatalogueFailure;
				}
				
			}catch(ConstraintViolationException e){
				response = dupalicateServiceCatalogueFailure;
			}catch (Exception e) {
				response = serviceCatalogueFailure;
			}
			
		}
		mapResponse.put(GSTNConstants.RESPONSE, response);
		mapResponse.put(GSTNConstants.STATUS, status);
		
		logger.info("Exit");
		return  mapResponse;
	}
	
	@RequestMapping(value={"/invoiceServiceList", "/billOfSupplyServiceList"},method=RequestMethod.POST)
	public @ResponseBody String getGenericServicesList(@RequestParam("gNo") String gstnNo,@RequestParam("location") String locationUniqueSequence,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");
		String uri = GSTNUtil.getLastURIPart(httpRequest.getRequestURI());
		List<ManageServiceCatalogue> servicesList = new ArrayList<ManageServiceCatalogue>();
		Map<String,Object>mapResponse = new HashMap<String,Object>(); 
		try {
			mapResponse = gstinDetailsController.checkIfGstinBelongsToUser(httpRequest,gstnNo,locationUniqueSequence);
			if(mapResponse.get(GSTNConstants.RESPONSE).equals(true)){
				GSTINDetails gstin = (GSTINDetails)mapResponse.get("GSTINDetails");
				Integer location = gstinDetailsController.fetchLocationFromUniqueSequence(gstin,locationUniqueSequence);
				LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
				Integer orgUId =loginMaster.getOrgUId();
				String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
				if(uri.equals("/invoiceServiceList")){
					servicesList = manageServiceCatalogueService.getServicesList(idsValuesToFetch,orgUId,"invoice",location);
				}else if(uri.equals("/billOfSupplyServiceList")){
					servicesList = manageServiceCatalogueService.getServicesList(idsValuesToFetch,orgUId,"billOfSupply",location);
				}/*else{
					servicesList = manageServiceCatalogueService.getServicesList(idsValuesToFetch,orgUId);
				}*/
				
			}
			
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(servicesList);
	}

	
	@RequestMapping(value="/serviceSaveAjax",method=RequestMethod.POST)
	public @ResponseBody String serviceSaveAjax(@Valid @RequestBody ServiceBean servicebean, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		List<ManageServiceCatalogue> serviceList= new ArrayList<>();
		String webSerResponse = null;
		String message = serviceCatalogueFailure;
		String status = GSTNConstants.FAILURE;
		Map<String,String> mapresponse = new HashMap<String,String>();
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		if (!result.hasErrors()){
			try{
				System.out.println("adding service ");
				serviceList = convertServiceBeanToManageService(servicebean,loginMaster);
				
				webSerResponse = manageServiceCatalogueService.addServiceThroughWebserviceCall(serviceList);
				if(webSerResponse.equals("true")){
					message = serviceCatalogueSuccessful;
					status = GSTNConstants.SUCCESS;
				}

				}catch (Exception e) {
					logger.error("Error in:",e);
				}
		}
		mapresponse.put("message", message);
		mapresponse.put("status", status);
		logger.info("Exit");
		return new Gson().toJson(mapresponse);
		
	}

	private List<ManageServiceCatalogue> convertServiceBeanToManageService(
			ServiceBean servicebean, LoginMaster loginMaster) {
		List<ManageServiceCatalogue> serviceList= new ArrayList<>();
		ManageServiceCatalogue service = null;
		for(StoresBean stores:servicebean.getStoresBean())
		{
			service = new ManageServiceCatalogue();
			
			service.setName(servicebean.getName());
			service.setSacCode(servicebean.getSacCode());
			service.setSacDescription(servicebean.getSacDescription());
			service.setUnitOfMeasurement(servicebean.getUnitOfMeasurement());
			service.setOtherUOM(servicebean.getOtherUOM());
			service.setServiceRate(servicebean.getServiceRate());
			service.setServiceIgst(servicebean.getServiceIgst());
			service.setReferenceId(loginMaster.getuId());
			service.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			service.setCreatedBy(loginMaster.getuId().toString());
			service.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			service.setUpdatedBy(loginMaster.getuId().toString());
			service.setStatus("1");
			service.setRefOrgId(loginMaster.getOrgUId());
			service.setSacCodePkId(servicebean.getSacCodePkId());
			service.setAdvolCess(servicebean.getAdvolCess());
			service.setNonAdvolCess(servicebean.getNonAdvolCess());
			service.setStoreId(stores.getStoreId());
			serviceList.add(service);
		}
		return serviceList;
	}
	
	@RequestMapping(value="/getManageServiceByServiceNameAndStoreId",method=RequestMethod.POST)
	public @ResponseBody String getManageServiceByServiceNameAndStoreId(@RequestParam("storeId") String storeId,@RequestParam("serviceName") String serviceName, HttpServletRequest httpRequest) {
		logger.info("Entry");
		ManageServiceCatalogue serviceDetail = new ManageServiceCatalogue();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			serviceDetail = manageServiceCatalogueService.getManageServiceCatalogueByNameAndStoreId(serviceName,loginMaster.getOrgUId(),Integer.parseInt(storeId));
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(serviceDetail);
		
	}
	
	
	@RequestMapping(value="/getServicesListJson",method=RequestMethod.POST)
	public @ResponseBody String getServicesListJson(Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		List<ServiceFetch> servicesList = new ArrayList<>();
		try {
				LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
				Integer orgUId =loginMaster.getOrgUId();
				String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
				List<ServiceFetch> servicesList2 = manageServiceCatalogueService.getServicesListJson(idsValuesToFetch,orgUId);
				
				if (servicesList2 != null && !servicesList2.isEmpty()) 
				{
					List<String> serv = new ArrayList<String>();
					 for (ServiceFetch service : servicesList2)
			        {
						 if (!serv.contains(service.getName()))
						 {
						 serv.add(service.getName());
							 service.setStoreName(service.getGstinLocationServiceFetch().getGstinLocation());
						 service.setGstinLocationServiceFetch(null);
						 servicesList.add(service);
						 }
					 }
				}
			 
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(servicesList);
	}
}
