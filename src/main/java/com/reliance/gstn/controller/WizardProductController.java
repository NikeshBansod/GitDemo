package com.reliance.gstn.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.Product;
import com.reliance.gstn.service.GenericService;
import com.reliance.gstn.service.ProductService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

@Controller
public class WizardProductController {

	private static final Logger logger = Logger.getLogger(WizardProductController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private GenericService genericService;
	
	

	@Value("${PRODUCT_ADD_SUCESS}")
	private String productAdditionSuccessful;

	@Value("${PRODUCT_ADD_FAILURE}")
	private String productAdditionFailure;

	@Value("${DUPLICATE_PRODUCT_ADD_FAILURE}")
	private String duplicateProductFailure;

	@Value("${INVALID_MAPPING_EXCEPTION}")
	private String InvalidMappingException;

	@Value(value = "${get_prim_id_list_query}")
	private String getPrimIdsListQuery;

	@Value("${PRODUCT_UPDATION_SUCCESS}")
	private String productUpdationSuccessful;

	@Value("${PRODUCT_UPDATION_FAILURE}")
	private String productUpdationFailure;

	@Value("${DUPLICATE_PRODUCT_UPDATION_FAILURE}")
	private String dupliateProductUpdationFailure;

	@Value("${PRODUCT_DELETION_SUCCESS}")
	private String productDeletionSuccessful;

	@Value("${PRODUCT_DELETION_FAILURE}")
	private String productDeletionFailure;
	

	@ModelAttribute("product")
	public Product construct(){
		return new Product();
	}

	@RequestMapping(value = "/wizardGetProducts", method = RequestMethod.GET)
	public String wizardGetProductsListPage(Model model) {
		return PageRedirectConstants.WIZARD_GET_PRODUCTS_LIST_PAGE;
	}
	
	@RequestMapping(value="/wizardProductSave",method=RequestMethod.POST)
	public String wizardAddProductPage(@Valid @ModelAttribute("product") Product product, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		Map<String,Object> mapResponse = new HashMap<String,Object>();
		String response = null;
		String pageRedirect = PageRedirectConstants.WIZARD_GET_PRODUCTS_LIST_PAGE;			
			try {
				if (!result.hasErrors()){
				LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
				Integer orgUId = loginMaster.getOrgUId();
				
				product.setStatus("1");
				product.setCreatedBy(loginMaster.getuId().toString());
				product.setReferenceId(loginMaster.getuId());
				product.setRefOrgId(orgUId);
				/*if(product.getOtherUOM()!=""){
					product.setUnitOfMeasurement(product.getTempUom());
				}*/
				mapResponse = productService.addProduct(product);
				if(mapResponse.get(GSTNConstants.RESPONSE).equals(GSTNConstants.SUCCESS)){
					//product = new Product();
					model.addAttribute(GSTNConstants.RESPONSE, productAdditionSuccessful);					
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, productAdditionFailure);
				}
			}
			} catch(ConstraintViolationException e){
				logger.error("Error in:",e);
				model.addAttribute(GSTNConstants.RESPONSE, duplicateProductFailure);
			}catch (Exception e) {
				logger.error("Error in:",e);
				model.addAttribute(GSTNConstants.RESPONSE, productAdditionFailure);
			}
		model.addAttribute("product", new Product());
		logger.info("Exit");
		return  pageRedirect;
	}

	@RequestMapping(value = "/wizardEditProduct", method = RequestMethod.POST)
	public String wizardEditProduct(@ModelAttribute("product") Product product, Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String pageRedirect =PageRedirectConstants.WIZARD_PRODUCT_EDIT_PAGE;
		try {
			Product p = new Product();
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			Integer productId = product.getId();
			boolean isMappingValid = false;
		//	String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(), getPrimIdsListQuery,productId,"Product","refOrgId");
			
			if(isMappingValid){
				p = productService.getProductById(product.getId());
			}else{
				logger.info("INVALID ACCESS"+loginMaster.getUserId());
				model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
				pageRedirect = PageRedirectConstants.WIZARD_GET_PRODUCTS_LIST_PAGE;
			}			
			model.addAttribute("productObj", p);
			model.addAttribute("editActionPerformed", true);
		}catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return pageRedirect;
	}
	
	@RequestMapping(value="/wizardUpdateProduct",method=RequestMethod.POST)
	public String wizardUpdateProduct(@Valid @ModelAttribute Product product ,BindingResult result,  HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.WIZARD_PRODUCT_EDIT_PAGE;
		String response = null;		
		if (!result.hasErrors()){
			try {
				LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
				Integer productId =product.getId();
				boolean isMappingValid = false;
			//	String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
				isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(), getPrimIdsListQuery,productId,"Product","refOrgId");
				Integer orgUId =loginMaster.getOrgUId();
				product.setReferenceId(loginMaster.getuId());
				product.setUpdatedBy(loginMaster.getuId().toString());
				product.setRefOrgId(orgUId);
				/*if(product.getOtherUOM()!=""){
					product.setUnitOfMeasurement(product.getTempUom());
				}*/
					if(isMappingValid){
						response = productService.updateProduct(product);
					}else{
						response = GSTNConstants.ACCESSVIOLATION;
					}
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, productUpdationSuccessful);
					pageRedirect = PageRedirectConstants.WIZARD_GET_PRODUCTS_LIST_PAGE;
				}else if(response.equals(GSTNConstants.FAILURE)){
					model.addAttribute(GSTNConstants.RESPONSE, productUpdationFailure);
				}else if(response.equals(GSTNConstants.ACCESSVIOLATION)){
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
					pageRedirect = PageRedirectConstants.WIZARD_PRODUCT_EDIT_PAGE;
				}
			} catch(DataIntegrityViolationException e){
				model.addAttribute(GSTNConstants.RESPONSE, dupliateProductUpdationFailure);
				logger.error("Error in:",e);
			} catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE, productUpdationFailure);
				logger.error("Error in:",e);
			}
		}else{
			logger.error("Error occured"+result.getAllErrors());
		}		
		logger.info("Exit");
		return  pageRedirect;
	}

	@RequestMapping(value = "/WizardDeleteProduct", method = RequestMethod.POST)
	public String WizardDeleteProduct(@ModelAttribute("product") Product product,  BindingResult result,Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String response = null;
		String pageRedirect = PageRedirectConstants.WIZARD_GET_PRODUCTS_LIST_PAGE;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		if (!result.hasErrors()){
			product.setUpdatedBy(loginMaster.getuId().toString());
			product.setStatus("0");
			try {
				Integer productId =product.getId();
				boolean isMappingValid = false;
				//String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
				isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(), getPrimIdsListQuery,productId,"Product","refOrgId");
					if(isMappingValid){
						response = productService.removeProduct(product);
					}else{
						response =GSTNConstants.ACCESSVIOLATION;
					}
				if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, productDeletionSuccessful);
				}else if(response.equals(GSTNConstants.FAILURE)){
					model.addAttribute(GSTNConstants.RESPONSE, productDeletionFailure);	
				}
				else if(response.equals(GSTNConstants.ACCESSVIOLATION)){
					logger.info("INVALID ACCESS"+loginMaster.getUserId());
					model.addAttribute(GSTNConstants.RESPONSE, InvalidMappingException);
					pageRedirect = PageRedirectConstants.WIZARD_GET_PRODUCTS_LIST_PAGE;
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
