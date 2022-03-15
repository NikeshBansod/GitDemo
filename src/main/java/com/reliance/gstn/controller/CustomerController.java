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
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.model.CustomerDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.service.CustomerService;
import com.reliance.gstn.service.GenericService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.GSTNConstants;
//import com.reliance.gstn.util.LoggerUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Shubham.Upadhyay
 *
 */
@Controller
public class CustomerController {
	
	private static final Logger logger = Logger.getLogger(CustomerController.class);
	
	@Autowired
	public CustomerService customerService;
	
	@Autowired
	private UserMasterService userMasterService;
	
	@Autowired
	private GenericService genericService;
	
	@Value("${CUSTOMER_DETAILS_SUCESS}")
	private String customerDetailsSuccessful;
	
	@Value("${CUSTOMER_DETAILS_FAILURE}")
	private String customerDetailsFailure;
	
	@Value("${DUPLICATE_CUSTOMER_DETAILS_FAILURE}")
	private String dupalicateCustomerDetail;
	
	@Value("${CUSTOMER_DETAILS_UPDATION_SUCESS}")
	private String customerDetailsUpdationSucessful;
	
	@Value("${DUPLICATE_CUSTOMER_DETAILS_UPDATION_FAILURE}")
	private String dupliateCustomerDetailsUpdationfailure;
	
	@Value("${CUSTOMER_DETAILS_UPDATION_FAILURE}")
	private String customerDetailsUpdationFailed;
	
	@Value("${CUSTOMER_DETAILS_DELETION_SUCESS}")
	private String customerDetailsDeletionSucessful;
	
	@Value("${CUSTOMER_DETAILS_DELETION_FAILURE}")
	private String customerDetailsDeletionFailed;
	
	@Value("${CUSTOMER_MAPPED_WITH_INVOICE}")
	private String customerMappedWithInvoice;
	
	@Value("${INVALID_MAPPING_EXCEPTION}")
	private String InvalidMappingException;
	
	@Value(value = "${get_prim_id_list_query}")
	private String getPrimIdsListQuery;
	
	@Value("${GSTIN_DETAILS_INVALID}")
	private String invalidGSTINDetails;
	
	@Value("${GSTIN_DETAILS_INVALID_UPDATE}")
	private String updateInvalidGSTINDetails;
	
	@ModelAttribute("customerDetails")
	public CustomerDetails construct(){
		return new CustomerDetails();
	}
	
	
	@RequestMapping(value = "/customerDetails", method = RequestMethod.GET)
	public String manageCustomerDetails(Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			List<CustomerDetails> manageCustomerDetailsList = customerService.viewCustomerDetailsList(loginMaster.getuId());
			model.addAttribute("manageCustomerDetailsList", manageCustomerDetailsList);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return PageRedirectConstants.MANAGE_CUSTOMER_DETAILS_LIST_PAGE;
	}
	
	@RequestMapping(value = "/addCustomerDetails", method = RequestMethod.GET)
	public String addCustomerDetails(Model model) {
		return PageRedirectConstants.MANAGE_CUSTOMER_DETAILS_LIST_PAGE;
	}
	
	@RequestMapping(value="/addCustomerDetails",method=RequestMethod.POST)
	public String addCustomerDetailsPost(@Valid @ModelAttribute("customerDetails") CustomerDetails customerDetails, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = null;
		Map<String,Object>responseMap = new HashMap<String,Object>();
		String pageRedirect = PageRedirectConstants.MANAGE_CUSTOMER_DETAILS_LIST_PAGE;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		if (!result.hasErrors()){
			
			try {
				Map<Object, Object> mapValues = new HashMap<Object, Object>(); 
				mapValues.put("loginMaster", loginMaster);
				responseMap = customerService.addCustomerDetails(customerDetails,mapValues);
				if(responseMap.get(GSTNConstants.RESPONSE).equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, customerDetailsSuccessful);
				//	customerDetails = new CustomerDetails();
					pageRedirect = PageRedirectConstants.MANAGE_CUSTOMER_DETAILS_LIST_PAGE;
				} else if(responseMap.get(GSTNConstants.RESPONSE).equals(GSTNConstants.INVALID_GSTIN)) {
					model.addAttribute(GSTNConstants.RESPONSE, invalidGSTINDetails);
				} else {
					model.addAttribute(GSTNConstants.RESPONSE, customerDetailsFailure);
				}
			} catch(ConstraintViolationException e){
				model.addAttribute(GSTNConstants.RESPONSE, dupalicateCustomerDetail);
				logger.error("Error in:",e);
			} catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE, customerDetailsFailure);
				logger.error("Error in:",e);
			}
			
		}
		
		model.addAttribute("customerDetails", new CustomerDetails());
		logger.info("Exit");
		return  pageRedirect;
	}
	
	
	@RequestMapping(value = "/editCustomerDetails", method = RequestMethod.POST)
	public String editCustomerDetails(@ModelAttribute CustomerDetails customerDetails, Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String pageRedirect =PageRedirectConstants.MANAGE_CUSTOMER_DETAILS_EDIT_PAGE;
		CustomerDetails customerDetailsObj = new CustomerDetails();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer custId =customerDetails.getId();
			boolean isMappingValid = false;
		//	String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(),getPrimIdsListQuery,custId,"CustomerDetails","refOrgId");
			
			if(isMappingValid){
				customerDetailsObj = customerService.getCustomerDetailsById(customerDetails.getId());
			}else{
				logger.info("INVALID ACCESS"+loginMaster.getUserId());
				model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
				pageRedirect = PageRedirectConstants.MANAGE_CUSTOMER_DETAILS_LIST_PAGE;
			}
			
			model.addAttribute("customerDetailsObj", customerDetailsObj);
			model.addAttribute("editActionPerformed", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return pageRedirect;
	}
	
	
	@RequestMapping(value="/updateCustomerDetails",method=RequestMethod.POST)
	public String updateCustomerDetails(@Valid @ModelAttribute CustomerDetails customerDetails ,BindingResult result,  HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.MANAGE_CUSTOMER_DETAILS_LIST_PAGE;
		String response = null;
		
		if (!result.hasErrors()){
			try {
				LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
				Map<Object, Object> mapValues = new HashMap<Object, Object>(); 
				mapValues.put("loginMaster", loginMaster);
				Integer custId =customerDetails.getId();
				boolean isMappingValid = false;
			//	String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
				isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString() ,getPrimIdsListQuery,custId,"CustomerDetails","refOrgId");
					if(isMappingValid){
						response = customerService.updateCustomerDetails(customerDetails, mapValues);
					}else{
						response = GSTNConstants.ACCESSVIOLATION;
					}
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, customerDetailsUpdationSucessful);
					model.addAttribute("customerDetails", new CustomerDetails() );
				}else if(response.equals(GSTNConstants.INVALID_GSTIN)){
					model.addAttribute(GSTNConstants.RESPONSE, updateInvalidGSTINDetails);
				}else if(response.equals(GSTNConstants.FAILURE)){
					model.addAttribute(GSTNConstants.RESPONSE, customerDetailsUpdationFailed);
					model.addAttribute("customerDetails", customerDetails );
				}else if(response.equals(GSTNConstants.ACCESSVIOLATION)){
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
					pageRedirect = PageRedirectConstants.MANAGE_CUSTOMER_DETAILS_EDIT_PAGE;
					model.addAttribute("customerDetails", customerDetails );
				}
			} catch(DataIntegrityViolationException e){
					model.addAttribute(GSTNConstants.RESPONSE, dupliateCustomerDetailsUpdationfailure);
					model.addAttribute("customerDetails", customerDetails );
					logger.error("Error in:",e);
			}catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE, customerDetailsUpdationFailed);
				model.addAttribute("customerDetails", customerDetails );
				logger.error("Error in:",e);
			}
		}else{
			model.addAttribute("customerDetails", customerDetails );
			logger.error("Error occured"+result.getAllErrors());
		}
		
		logger.info("Exit");
		return  pageRedirect;
	}
	
	
	@RequestMapping(value="/deleteCustomerDetails", method = RequestMethod.POST)
	public String deleteCustomerDetails(@RequestParam Integer id, Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = "";
		String pageRedirect = PageRedirectConstants.MANAGE_CUSTOMER_DETAILS_LIST_PAGE;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer custId =id;
			boolean isMappingValid = false;
			//String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(), getPrimIdsListQuery,custId,"CustomerDetails","refOrgId");
			
				if(isMappingValid){
					response = customerService.deleteCustomerDetails(id);
				}else{
					response = GSTNConstants.ACCESSVIOLATION;
				}
			
			if(response.equals(GSTNConstants.SUCCESS)){
				model.addAttribute(GSTNConstants.RESPONSE, customerDetailsDeletionSucessful);
			}else if(response.equals(GSTNConstants.FAILURE)){
				model.addAttribute(GSTNConstants.RESPONSE, customerDetailsDeletionFailed);
			}else if(response.equals(GSTNConstants.ACCESSVIOLATION)){
				logger.info("INVALID ACCESS"+loginMaster.getUserId());
				pageRedirect = PageRedirectConstants.MANAGE_CUSTOMER_DETAILS_LIST_PAGE;
				model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
			}
		} catch(DataIntegrityViolationException e){
			model.addAttribute(GSTNConstants.RESPONSE, customerMappedWithInvoice);
			logger.error("Error in:",e);
		} catch (Exception e) {
			model.addAttribute(GSTNConstants.RESPONSE, customerDetailsDeletionFailed);
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return pageRedirect;
	}

	
	@RequestMapping("/getCustomersList")
	public @ResponseBody String getCustomersList(HttpServletRequest httpRequest){
		logger.info("Entry");	
		
		String json="";
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			List<CustomerDetails> lst = customerService.getCustomersList(loginMaster.getOrgUId());
			json = new Gson().toJson(lst);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return json;
	}
	
	@RequestMapping(value = "/customerDetailsList", method = RequestMethod.GET)
	public String getCustomerListPage(Model model) {
	
		return PageRedirectConstants.MANAGE_CUSTOMER_DETAILS_LIST_PAGE;
	}
	
	@RequestMapping(value="/getCustomerDetailsList",method=RequestMethod.POST)
	public @ResponseBody String getCustomerDetailsList(Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		
		List<CustomerDetails> customerDetailsList = new ArrayList<CustomerDetails>();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		//	String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			customerDetailsList = customerService.getCustomersList(loginMaster.getOrgUId());
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return new Gson().toJson(customerDetailsList);
	}
	
	@RequestMapping(value="/getCustomerDetailsForAutoCompleteList",method=RequestMethod.GET)
	public @ResponseBody String getCustomerDetailsForAutoCompleteList(@RequestParam("term") String query,@RequestParam("documentType") String defaultDocumentType, HttpServletRequest httpRequest) {
		logger.info("Entry");
		List<String> customerDetailsList = new ArrayList<String>();
		try {
			customerDetailsList = getCustomerListByCustName(query, httpRequest, defaultDocumentType);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		 logger.info("Exit");
		return new Gson().toJson(customerDetailsList);
	}


	private List<String> getCustomerListByCustName(String query, HttpServletRequest httpRequest, String documentType) throws Exception{
		logger.info("Entry");	
		 List<String> matched = new ArrayList<String>();
		 
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			//String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			Integer orgUid = (Integer)loginMaster.getOrgUId();
			String custName = null;
			String contactNo = null;
			query = query.toLowerCase();
			
			List<Object[]> customerDetailsList = customerService.getCustomersListByCustNameAndMobileNo(orgUid, query, documentType);
			    for(int i=0; i < customerDetailsList.size(); i++) {
			        Object[] obj = customerDetailsList.get(i);
			        contactNo = (String)obj[0];
		        	custName = (String)obj[1];
		            if(custName.toLowerCase().contains(query) || contactNo.toLowerCase().startsWith(query)) {
		                matched.add("["+contactNo+"] - "+custName);
		            }
			    }
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
	        logger.info("Exit");
	        
	     return matched;
	}
	
	@RequestMapping(value="/getCustomerDetailByCustName/{custName}",method=RequestMethod.GET)
	public @ResponseBody String getCustomerDetailByCustName(@PathVariable("custName") String custName, HttpServletRequest httpRequest) {
		logger.info("Entry");
		CustomerDetails customerDetails = new CustomerDetails();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			customerDetails = customerService.getCustomerDetailsByCustName(custName,idsValuesToFetch);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(customerDetails);
		
	}
	
	@RequestMapping(value="/checkIfCustomerExists",method=RequestMethod.POST)
	public @ResponseBody String checkIfCustomerExists(@RequestParam("custContact") String customer, @RequestParam("custName") String custName, HttpServletRequest httpRequest) {
		logger.info("Entry");
		boolean isProductexists=false;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer orgUId =loginMaster.getOrgUId();
			isProductexists = customerService.checkIfCustomerExists(customer,custName,orgUId);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(isProductexists);
		
	}
	
	@RequestMapping(value="/checkIfCustomerContactNoExists",method=RequestMethod.POST)
	public @ResponseBody String checkIfCustomerContactNoExists(@RequestParam("custContact") String contactNo, HttpServletRequest httpRequest) {
		logger.info("Entry");
		CustomerDetails customer = null;
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer orgUId = loginMaster.getOrgUId();
			customer = customerService.checkIfCustomerContactNoExists(contactNo,orgUId);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(customer);
		
	}
	
	@RequestMapping(value="/getCustomerDetailByCustNameAndContactNo",method=RequestMethod.POST)
	public @ResponseBody String getCustomerDetailByCustNameAndContactNoPost(@RequestParam("custName") String custName,@RequestParam("contactNo") String contactNo, HttpServletRequest httpRequest) {
		logger.info("Entry");
		CustomerDetails customerDetails = new CustomerDetails();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			customerDetails = customerService.getCustomerDetailsByCustNameAndContactNo(custName,contactNo,loginMaster.getOrgUId());
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(customerDetails);
		
	}
	
	@RequestMapping(value="/addCustomerDynamically",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> addCustomerDynamically(@Valid @RequestBody CustomerDetails customerDetails, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = null;
		String status = GSTNConstants.FAILURE;
		Map<String,Object> mapResponse = new HashMap<String,Object>();
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		if (!result.hasErrors()){
			try {
				Map<Object, Object> mapValues = new HashMap<Object, Object>(); 
				mapValues.put("loginMaster", loginMaster);
				mapResponse = customerService.addCustomerDetails(customerDetails,mapValues);
				if(mapResponse.get(GSTNConstants.RESPONSE).equals(GSTNConstants.SUCCESS)){
					response = customerDetailsSuccessful;
					status = GSTNConstants.SUCCESS;
				} else if(mapResponse.get(GSTNConstants.RESPONSE).equals(GSTNConstants.INVALID_GSTIN)) {
					response = invalidGSTINDetails;
				} else {
					response = customerDetailsFailure;
				}
			} catch(ConstraintViolationException e){
				response = dupalicateCustomerDetail;
				logger.error("Error in:",e);
			} catch (Exception e) {
				response = customerDetailsFailure;
				logger.error("Error in:",e);
			}
			
		}
		mapResponse.put(GSTNConstants.RESPONSE, response);
		mapResponse.put(GSTNConstants.STATUS, status);
		
		logger.info("Exit");
		return  mapResponse;
	}
	
	
	@RequestMapping(value="/getSupplierDetailsForAutoCompleteList",method=RequestMethod.GET)
	public @ResponseBody String getSupplierDetailsForAutoCompleteList(@RequestParam("term") String query,@RequestParam("documentType") String defaultDocumentType, HttpServletRequest httpRequest) {
		logger.info("Entry");
		List<String> customerDetailsList = new ArrayList<String>();
		try {
			customerDetailsList = getSupplierListByCustName(query, httpRequest, defaultDocumentType);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		 logger.info("Exit");
		return new Gson().toJson(customerDetailsList);
	}
	
	private List<String> getSupplierListByCustName(String query, HttpServletRequest httpRequest, String documentType) throws Exception{
		logger.info("Entry");	
		 List<String> matched = new ArrayList<String>();
		 
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			//String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			Integer orgUid = (Integer)loginMaster.getOrgUId();
			String custName = null;
			String contactNo = null;
			query = query.toLowerCase();
			
			List<Object[]> supplierDetailsList = customerService.getSupplierListBySupplierNameAndMobileNo(orgUid, query, documentType);
			    for(int i=0; i < supplierDetailsList.size(); i++) {
			        Object[] obj = supplierDetailsList.get(i);
			        contactNo = (String)obj[0];
		        	custName = (String)obj[1];
		            if(custName.toLowerCase().contains(query) || contactNo.toLowerCase().startsWith(query)) {
		                matched.add("["+contactNo+"] - "+custName);
		            }
			    }
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
	        logger.info("Exit");
	        
	     return matched;
	}
	
}
