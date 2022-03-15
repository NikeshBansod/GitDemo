package com.reliance.gstn.controller;

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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.reliance.gstn.model.CustomerDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.service.CustomerService;
import com.reliance.gstn.service.GenericService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

@Controller
public class WizardCustomerController {

	private static final Logger logger = Logger.getLogger(WizardCustomerController.class);

	@Autowired
	public CustomerService customerService;

	@Autowired
	private GenericService genericService;
	

	@Value("${INVALID_MAPPING_EXCEPTION}")
	private String InvalidMappingException;

	@Value(value = "${get_prim_id_list_query}")
	private String getPrimIdsListQuery;

	@Value("${CUSTOMER_DETAILS_UPDATION_SUCESS}")
	private String customerDetailsUpdationSucessful;

	@Value("${GSTIN_DETAILS_INVALID_UPDATE}")
	private String updateInvalidGSTINDetails;

	@Value("${CUSTOMER_DETAILS_UPDATION_FAILURE}")
	private String customerDetailsUpdationFailed;

	@Value("${DUPLICATE_CUSTOMER_DETAILS_UPDATION_FAILURE}")
	private String dupliateCustomerDetailsUpdationfailure;

	@Value("${CUSTOMER_DETAILS_DELETION_SUCESS}")
	private String customerDetailsDeletionSucessful;

	@Value("${CUSTOMER_DETAILS_DELETION_FAILURE}")
	private String customerDetailsDeletionFailed;

	@Value("${CUSTOMER_DETAILS_SUCESS}")
	private String customerDetailsSuccessful;

	@Value("${GSTIN_DETAILS_INVALID}")
	private String invalidGSTINDetails;

	@Value("${CUSTOMER_DETAILS_FAILURE}")
	private String customerDetailsFailure;

	@Value("${DUPLICATE_CUSTOMER_DETAILS_FAILURE}")
	private String dupalicateCustomerDetail;

	@Value("${CUSTOMER_MAPPED_WITH_INVOICE}")
	private String customerMappedWithInvoice;
	
	@ModelAttribute("customerDetails")
	public CustomerDetails construct(){
		return new CustomerDetails();
	}
	

	@RequestMapping(value = "/wizardCustomerDetails", method = RequestMethod.GET)
	public String wizardManageCustomerDetails(Model model) {
		return PageRedirectConstants.WIZARD_MANAGE_CUSTOMER_DETAILS_LIST_PAGE;
	}


	@RequestMapping(value="/wizardAddCustomerDetails",method=RequestMethod.POST)
	public String wizardAddCustomerDetailsPost(@Valid @ModelAttribute("customerDetails") CustomerDetails customerDetails, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = null;
		Map<String,Object>responseMap = new HashMap<String,Object>();
		String pageRedirect = PageRedirectConstants.WIZARD_MANAGE_CUSTOMER_DETAILS_LIST_PAGE;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		if (!result.hasErrors()){
			
			try {
				Map<Object, Object> mapValues = new HashMap<Object, Object>(); 
				mapValues.put("loginMaster", loginMaster);
				responseMap = customerService.addCustomerDetails(customerDetails,mapValues);
				if(responseMap.get(GSTNConstants.RESPONSE).equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, customerDetailsSuccessful);
				//	customerDetails = new CustomerDetails();
					pageRedirect = PageRedirectConstants.WIZARD_MANAGE_CUSTOMER_DETAILS_LIST_PAGE;
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
	
	@RequestMapping(value = "/wizardEditCustomerDetails", method=RequestMethod.POST)
	public String wizardEditCustomerDetails(@ModelAttribute CustomerDetails customerDetails, Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String pageRedirect =PageRedirectConstants.WIZARD_MANAGE_CUSTOMER_DETAILS_EDIT_PAGE;
		CustomerDetails customerDetailsObj = new CustomerDetails();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer custId =customerDetails.getId();
			boolean isMappingValid = false;
			//String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(),getPrimIdsListQuery,custId,"CustomerDetails","refOrgId");
			
			if(isMappingValid){
				customerDetailsObj = customerService.getCustomerDetailsById(customerDetails.getId());
			}else{
				logger.info("INVALID ACCESS"+loginMaster.getUserId());
				model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
				pageRedirect = PageRedirectConstants.WIZARD_MANAGE_CUSTOMER_DETAILS_LIST_PAGE;
			}			
			model.addAttribute("customerDetailsObj", customerDetailsObj);
			model.addAttribute("editActionPerformed", true);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}		
		logger.info("Exit");
		return pageRedirect;
	}
	

	@RequestMapping(value="/wizardUpdateCustomerDetails",method=RequestMethod.POST)
	public String wizardUpdateCustomerDetails(@Valid @ModelAttribute CustomerDetails customerDetails ,BindingResult result,  HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.WIZARD_MANAGE_CUSTOMER_DETAILS_LIST_PAGE;
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
					pageRedirect = PageRedirectConstants.WIZARD_MANAGE_CUSTOMER_DETAILS_EDIT_PAGE;
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
	

	@RequestMapping(value = "/wizardDeleteCustomerDetails", method=RequestMethod.POST)
	public String wizardDeleteCustomerDetails(@RequestParam Integer id, Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = "";
		String pageRedirect = PageRedirectConstants.WIZARD_MANAGE_CUSTOMER_DETAILS_LIST_PAGE;
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
				pageRedirect = PageRedirectConstants.WIZARD_MANAGE_CUSTOMER_DETAILS_LIST_PAGE;
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

}
