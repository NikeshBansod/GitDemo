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
import com.reliance.gstn.model.OpeningStockBean;
import com.reliance.gstn.model.Product;
import com.reliance.gstn.model.ProductBean;
import com.reliance.gstn.model.ProductFetch;
import com.reliance.gstn.service.GenericService;
import com.reliance.gstn.service.ProductService;
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
public class ProductController {
	
	private static final Logger logger = Logger.getLogger(ProductController.class);
	
	@Autowired
	private UserMasterService userMasterService;
	
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
	
	@Value("${INVALID_MAPPING_EXCEPTION}")
	private String InvalidMappingException;
	
	@Value(value = "${get_prim_id_list_query}")
	private String getPrimIdsListQuery;
	
	@Autowired
	private GSTINDetailsController gstinDetailsController;
	
	@ModelAttribute("product")
	public Product construct(){
		return new Product();
	}
	
	@ModelAttribute("productfetch")
	public ProductFetch constructfetch(){
		return new ProductFetch();
	}
	
	@RequestMapping(value = "/getProducts", method = RequestMethod.GET)
	public String getProductsListPage(Model model) {
		return PageRedirectConstants.GET_PRODUCTS_LIST_PAGE;
	}

	@RequestMapping(value = "/getProductsList", method = RequestMethod.POST)
	public @ResponseBody String getProductsList(Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");
		List<Product> productsList = new ArrayList<Product>();
		try {
			LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
			Integer orgUId = loginMaster.getOrgUId();
			String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(), loginMaster.getUserRole());
			productsList = productService.getProductsList(idsValuesToFetch,orgUId);
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(productsList);
	}

	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public String addProductPage(@Valid @ModelAttribute("product") Product product,BindingResult result, Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		Map<String, Object> mapResponse = new HashMap<String, Object>();
		String response = null;
		String pageRedirect = PageRedirectConstants.GET_PRODUCTS_LIST_PAGE;

		try {
			if (!result.hasErrors()) {
				LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
				Integer orgUId = loginMaster.getOrgUId();
				product.setStatus("1");
				product.setCreatedBy(loginMaster.getuId().toString());
				product.setReferenceId(loginMaster.getuId());
				product.setRefOrgId(orgUId);
				/*
				 * if(product.getOtherUOM()!=""){
				 * product.setUnitOfMeasurement(product.getTempUom()); }
				 */
				mapResponse = productService.addProduct(product);
				if (mapResponse.get(GSTNConstants.RESPONSE).equals(GSTNConstants.SUCCESS)) {
					// product = new Product();
					model.addAttribute(GSTNConstants.RESPONSE,productAdditionSuccessful);
				} else {
					model.addAttribute(GSTNConstants.RESPONSE,productAdditionFailure);
				}
			}
		} catch (ConstraintViolationException e) {
			logger.error("Error in:", e);
			model.addAttribute(GSTNConstants.RESPONSE, duplicateProductFailure);
		} catch (Exception e) {
			logger.error("Error in:", e);
			model.addAttribute(GSTNConstants.RESPONSE, productAdditionFailure);
		}
		model.addAttribute("product", new Product());
		logger.info("Exit");
		return pageRedirect;
	}

	@RequestMapping(value = "/addProductDynamically", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addProductDynamically(@Valid @RequestBody Product product, BindingResult result,Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String response = null;
		String status = GSTNConstants.FAILURE;
		Map<String, Object> mapResponse = new HashMap<String, Object>();
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		if (!result.hasErrors()) {
			try {
				product.setStatus("1");
				product.setCreatedBy(loginMaster.getuId().toString());
				product.setReferenceId(loginMaster.getuId());
				product.setRefOrgId(loginMaster.getOrgUId());
				mapResponse = productService.addProduct(product);
				if (mapResponse.get(GSTNConstants.RESPONSE).equals(GSTNConstants.SUCCESS)) {
					response = productAdditionSuccessful;
					status = GSTNConstants.SUCCESS;
				} else {
					response = productAdditionFailure;
				}

			} catch (ConstraintViolationException e) {
				response = duplicateProductFailure;
			} catch (Exception e) {
				response = productAdditionFailure;
			}

		}
		mapResponse.put(GSTNConstants.RESPONSE, response);
		mapResponse.put(GSTNConstants.STATUS, status);

		logger.info("Exit");
		return mapResponse;
	}

	@RequestMapping(value = "/editProduct", method = RequestMethod.POST)
	public String editProduct(@ModelAttribute("product") Product product,Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.PRODUCT_EDIT_PAGE;
		try {
			ProductFetch p = new ProductFetch();
			LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
			Integer productId = product.getId();
			boolean isMappingValid = false;
			boolean storeExists = true;
			// String idsValuesToFetch =
			// userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
			isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(), getPrimIdsListQuery,productId, "Product", "refOrgId");

			if (isMappingValid) {
				// p = productService.getProductById(product.getId());
				p = productService.getProductByIdJson(product.getId());
				if (p.getStoreId() == null) {
					storeExists = false;
				}

			} else {
				logger.info("INVALID ACCESS" + loginMaster.getUserId());
				model.addAttribute(GSTNConstants.RESPONSE,InvalidMappingException);
				pageRedirect = PageRedirectConstants.GET_PRODUCTS_LIST_PAGE;
			}

			model.addAttribute("productObj", p);
			model.addAttribute("inventoryFlag", p.getInventoryUpdateFlag());
			model.addAttribute("storeExists", storeExists);
			model.addAttribute("editActionPerformed", true);
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return pageRedirect;
	}

	@RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
	public String updateProduct(@Valid @ModelAttribute Product product,BindingResult result, HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.PRODUCT_EDIT_PAGE;
		String response = null;

		if (!result.hasErrors()) {
			try {
				LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
				Integer productId = product.getId();
				boolean isMappingValid = false;
				// String idsValuesToFetch =
				// userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
				isMappingValid = genericService.checkUserMappingValidation(
				loginMaster.getOrgUId().toString(),
				getPrimIdsListQuery, productId, "Product", "refOrgId");
				Integer orgUId = loginMaster.getOrgUId();
				product.setReferenceId(loginMaster.getuId());
				product.setUpdatedBy(loginMaster.getuId().toString());
				product.setRefOrgId(orgUId);
				product.setCurrentStock(product.getOpeningStock());
				product.setCurrentStockValue(product.getOpeningStockValue());
				product.setAdvolCess((double) 0);
				product.setNonAdvolCess((double) 0);
				/*
				 * if(product.getOtherUOM()!=""){
				 * product.setUnitOfMeasurement(product.getTempUom()); }
				 */
				if (isMappingValid) {
					response = productService.updateProduct(product);
				} else {
					response = GSTNConstants.ACCESSVIOLATION;
				}
				if (response.equals(GSTNConstants.SUCCESS)) {
					model.addAttribute(GSTNConstants.RESPONSE,productUpdationSuccessful);
					pageRedirect = PageRedirectConstants.GET_PRODUCTS_LIST_PAGE;
				} else if (response.equals(GSTNConstants.FAILURE)) {
					model.addAttribute(GSTNConstants.RESPONSE,productUpdationFailure);
				} else if (response.equals(GSTNConstants.ACCESSVIOLATION)) {
					logger.info("INVALID ACCESS" + loginMaster.getUserId());
					model.addAttribute(GSTNConstants.RESPONSE,InvalidMappingException);
					pageRedirect = PageRedirectConstants.PRODUCT_EDIT_PAGE;
				}
			} catch (DataIntegrityViolationException e) {
				model.addAttribute(GSTNConstants.RESPONSE,dupliateProductUpdationFailure);
				logger.error("Error in:", e);
			} catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE,productUpdationFailure);
				logger.error("Error in:", e);
			}
		} else {
			logger.error("Error occured" + result.getAllErrors());
		}

		logger.info("Exit");
		return pageRedirect;
	}

	@RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
	public String deleteProduct(@ModelAttribute("product") Product product,BindingResult result, Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String response = null;
		String pageRedirect = PageRedirectConstants.GET_PRODUCTS_LIST_PAGE;
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		if (!result.hasErrors()) {
			product.setUpdatedBy(loginMaster.getuId().toString());
			product.setStatus("0");
			try {
				Integer productId = product.getId();
				boolean isMappingValid = false;
				// String idsValuesToFetch =
				// userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
				isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(),getPrimIdsListQuery, productId, "Product", "refOrgId");
				if (isMappingValid) {
					response = productService.removeProduct(product);
					System.out.println("catch");
				} else {
					response = GSTNConstants.ACCESSVIOLATION;
				}
				if (response.equals(GSTNConstants.SUCCESS)) {
					model.addAttribute(GSTNConstants.RESPONSE,productDeletionSuccessful);
				} else if (response.equals(GSTNConstants.FAILURE)) {
					model.addAttribute(GSTNConstants.RESPONSE,productDeletionFailure);
				} else if (response.equals(GSTNConstants.ACCESSVIOLATION)) {
					logger.info("INVALID ACCESS" + loginMaster.getUserId());
					model.addAttribute(GSTNConstants.RESPONSE,InvalidMappingException);
					pageRedirect = PageRedirectConstants.GET_PRODUCTS_LIST_PAGE;
				}
				else
				{
					model.addAttribute(GSTNConstants.RESPONSE,response);
				}
			} catch (Exception e) {
				logger.error("Error in:", e);
			}
		} else {
			logger.error("Error occured" + result.getAllErrors());
		}
		logger.info("Exit");
		return pageRedirect;
	}

	@RequestMapping(value = "/getProductById", method = RequestMethod.POST)
	public @ResponseBody String getProductById(@RequestParam("productId") Integer id,HttpServletRequest httpRequest) {
		logger.info("Entry");
		Product product = new Product();
		try {
			product = productService.getProductById(id);

		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(product);

	}

	@RequestMapping(value = "/checkIfProductExists", method = RequestMethod.POST)
	public @ResponseBody String checkIfProductExists(@RequestParam("product") String product,HttpServletRequest httpRequest) {
		logger.info("Entry");
		boolean isProductexists = false;
		try {
			LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
			Integer orgUId = loginMaster.getOrgUId();
			isProductexists = productService.checkIfProductExists(product,orgUId);
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(isProductexists);

	}

	@RequestMapping(value = "/getProductNameList", method = RequestMethod.POST, headers = "Accept=*/*")
	public @ResponseBody List<String> getServiceNameList(@RequestParam("term") String query,@RequestParam("gNo") String gstnNo,@RequestParam("location") String locationUniqueSequence,@RequestParam("documentType") String defaultDocumentType,HttpServletRequest httpRequest)
	{
		logger.info("Entry");
		String productTypeCall = null;
		Map<String, Object> mapResponse = new HashMap<String, Object>();
		List<String> productList = null;
		System.out.println("gstnNo : " + gstnNo + ",locationUniqueSequence:"+ locationUniqueSequence + ",documentType :"+ defaultDocumentType);
		if (defaultDocumentType.equals("invoice")|| defaultDocumentType.equals("rcInvoice")|| defaultDocumentType.equals("eComInvoice")) 
		{
			productTypeCall = "invoice";
		} else if (defaultDocumentType.equals("billOfSupply")|| defaultDocumentType.equals("eComBillOfSupply")) 
		{
			productTypeCall = "billOfSupply";
		}
		else{
			productTypeCall="purchaseEntryInvoiceAndBillOfSupply";
		}

		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		mapResponse = gstinDetailsController.checkIfGstinBelongsToUser(httpRequest, gstnNo, locationUniqueSequence);
		if (mapResponse.get(GSTNConstants.RESPONSE).equals(true)) {
			GSTINDetails gstin = (GSTINDetails) mapResponse.get("GSTINDetails");
			Integer location = gstinDetailsController.fetchLocationFromUniqueSequence(gstin,locationUniqueSequence);
			productList = getProductListAutoComplete(query,loginMaster.getOrgUId(), productTypeCall, location);
		}

		logger.info("Exit");
		return productList;
	}

	private List<String> getProductListAutoComplete(String query,Integer orgUId, String productTypeCall, Integer location) {

		query = query.toLowerCase();
		List<String> matched = new ArrayList<String>();
		matched = productService.getProductNameListByAutoComplete(query,orgUId, productTypeCall, location);
		return matched;
	}

	@RequestMapping(value = "/getProductByProductName", method = RequestMethod.POST)
	public @ResponseBody String getManageServiceProductByProductName(@RequestParam("productName") String productName,HttpServletRequest httpRequest) {
		logger.info("Entry");
		Product serviceDetail = new Product();
		try {
			LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
			serviceDetail = productService.getProductByName(productName,loginMaster.getOrgUId());
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(serviceDetail);

	}
	
	@RequestMapping(value={"/invoiceProductList","/billOfSupplyProductList","/purchaseEntryInvoiceAndBillOfSupplyProductList"},method=RequestMethod.POST)
	public @ResponseBody String getGenericProductsList(@RequestParam("gNo") String gstnNo,@RequestParam("location") String locationUniqueSequence,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");
		String uri = GSTNUtil.getLastURIPart(httpRequest.getRequestURI());
		List<Product> productsList = new ArrayList<Product>();
		Map<String, Object> mapResponse = new HashMap<String, Object>();

		try {
			mapResponse = gstinDetailsController.checkIfGstinBelongsToUser(httpRequest, gstnNo, locationUniqueSequence);
			if (mapResponse.get(GSTNConstants.RESPONSE).equals(true)) {
				GSTINDetails gstin = (GSTINDetails) mapResponse.get("GSTINDetails");
				Integer location = gstinDetailsController.fetchLocationFromUniqueSequence(gstin,locationUniqueSequence);
				LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
				Integer orgUId = loginMaster.getOrgUId();
				String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
				if(uri.equals("/invoiceProductList")){
					productsList = productService.getProductsList(idsValuesToFetch,orgUId,"invoice",location);
				}else if(uri.equals("/billOfSupplyProductList")){
					productsList = productService.getProductsList(idsValuesToFetch,orgUId,"billOfSupply",location);
				}else if(uri.equals("/purchaseEntryInvoiceAndBillOfSupplyProductList")){
					productsList = productService.getProductsList(idsValuesToFetch,orgUId,"purchaseEntryInvoiceAndBillOfSupply",location);
				}
			}

		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(productsList);
	}

	@RequestMapping(value = "/saveProductWithOpeningStockAjax", method = RequestMethod.POST)
	public @ResponseBody String saveProductWithOpeningStockAjax(@Valid @RequestBody ProductBean productbean, BindingResult result,Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		List<Product> productList = new ArrayList<>();
		 Map<String, Object>  webSerResponse = new HashMap<>();
		String message = productAdditionFailure;
		String status = GSTNConstants.FAILURE;
		Map<String, String> mapresponse = new HashMap<String, String>();
		LoginMaster loginMaster = (LoginMaster) LoginUtil
				.getLoginUser(httpRequest);
		if (!result.hasErrors()) {
			try {
				productList = convertProductBeanToProduct(productbean,loginMaster);
				webSerResponse = productService.addProductThroughWebserviceCall(productList);
				if (webSerResponse.get("error").equals(false)) {
					message = productAdditionSuccessful;
					status = GSTNConstants.SUCCESS;
				}
				if(webSerResponse.get("error").equals(true))
				{
					if(webSerResponse.get("errorcode").equals("119"))
					{
						message=(String) webSerResponse.get("message");
						
					}
				}

			} catch (Exception e) {
				logger.error("Error in:", e);
			}
		}
		mapresponse.put("message", message);
		mapresponse.put("status", status);
		logger.info("Exit");
		return new Gson().toJson(mapresponse);

	}

	private List<Product> convertProductBeanToProduct(ProductBean productbean,
			LoginMaster loginMaster) {
		List<Product> productList = new ArrayList<>();
		Product product = null;
		for (OpeningStockBean openingStock : productbean.getOpeningStockBean()) {
			product = new Product();

			product.setName(productbean.getName());
			product.setHsnCode(productbean.getHsnCode());
			product.setHsnDescription(productbean.getHsnDescription());
			product.setUnitOfMeasurement(productbean.getUnitOfMeasurement());
			product.setOtherUOM(productbean.getOtherUOM());
			product.setProductRate(productbean.getProductRate());
			product.setProductIgst(productbean.getProductIgst());
			product.setReferenceId(loginMaster.getuId());
			product.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			product.setCreatedBy(loginMaster.getuId().toString());
			product.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			product.setUpdatedBy(loginMaster.getuId().toString());
			product.setStatus("1");
			product.setRefOrgId(loginMaster.getOrgUId());
			product.setHsnCodePkId(productbean.getHsnCodePkId());
			product.setAdvolCess(productbean.getAdvolCess());
			product.setNonAdvolCess(productbean.getNonAdvolCess());
			product.setPurchaseRate(productbean.getPurchaseRate());
			product.setPurchaseUOM(productbean.getPurchaseUOM());
			product.setPurchaseOtherUOM(productbean.getPurchaseOtherUOM());
			product.setStoreId(openingStock.getStoreId());
			product.setOpeningStock(openingStock.getCurrentStock());
			product.setOpeningStockValue(openingStock.getOpeningStockValue());
			product.setCurrentStock(openingStock.getCurrentStock());
			product.setCurrentStockValue(openingStock.getCurrentStockValue());
			product.setInventoryUpdateFlag(openingStock.getInventoryUpdate());
			productList.add(product);
		}

		return productList;
	}

	@RequestMapping(value = "/getProductByProductNameAndStoreId", method = RequestMethod.POST)
	public @ResponseBody String getProductByProductNameAndStoreId(
			@RequestParam("storeId") String storeId,
			@RequestParam("productName") String productName,
			HttpServletRequest httpRequest) {
		logger.info("Entry");
		Product product = new Product();
		try {
			LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
			product = productService.getProductByNameAndStoreId(productName,loginMaster.getOrgUId(), Integer.parseInt(storeId));
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(product);

	}

	@RequestMapping(value = "/getProductsListjson", method = RequestMethod.POST)
	public @ResponseBody String getProductsListJson(Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");
		List<ProductFetch> productsList = new ArrayList<>();

		try {
			LoginMaster loginMaster = (LoginMaster) LoginUtil
					.getLoginUser(httpRequest);
			Integer orgUId = loginMaster.getOrgUId();

			String idsValuesToFetch = userMasterService.getDependentIds(
					loginMaster.getuId(), loginMaster.getUserRole());

			List<ProductFetch> productsList2 = productService
					.getProductsListJson(idsValuesToFetch, orgUId);

			if (productsList2 != null && !productsList2.isEmpty()) {
				List<String> prod = new ArrayList<String>();
				for (ProductFetch productFetch : productsList2) {
					if (!prod.contains(productFetch.getName())) {
						prod.add(productFetch.getName());
						productFetch.setStoreName(productFetch.getGstinLocationfetch()
								.getGstinLocation());
						productFetch.setGstinLocationfetch(null);
						productsList.add(productFetch);
					}
				}

			}
/*
			for (ProductFetch product : productsList) {
				if (null != product.getGstinLocationfetch()) {
					product.setStoreName(product.getGstinLocationfetch()
							.getGstinLocation());
				}
				product.setGstinLocationfetch(null);
			}
*/
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(productsList);
	}
	
	@RequestMapping(value = "/getProductForEditPage", method = RequestMethod.POST)
	public @ResponseBody String getProductForEditPage(Model model,@ModelAttribute("product") Product product,String name,Integer prod,HttpServletRequest httpRequest) {
		logger.info("Entry");
		List<ProductFetch> productsList = new ArrayList<>();

		try {
			ProductFetch p = new ProductFetch();
			LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
			product.setId(prod);
			boolean isMappingValid = false;
			boolean storeExists = true;
			isMappingValid = genericService.checkUserMappingValidation(loginMaster.getOrgUId().toString(), getPrimIdsListQuery,prod, "Product", "refOrgId");

			if (isMappingValid) {
				// p = productService.getProductById(product.getId());
				p = productService.getProductByIdJson(product.getId());
				Integer refOrgID=p.getRefOrgId();
				String Productname=name;
				productsList=productService.getProductByIdJsonEditPage(refOrgID,Productname);
			}
			
			for (ProductFetch products : productsList) {
				 
				  if(null!=products.getGstinLocationfetch()){
				  products.setStoreName(products.getGstinLocationfetch().getGstinLocation());
				  products.setGstinNumber(products.getGstinLocationfetch().getGstindetailsfetch().getGstinNo());
				  products.setExistingName(products.getName());
				  products.setGstinLocationfetch(null);
				  }
				  /*else{
				  products.setGstinLocationfetch(null);}*/
				  }
		/*	if (productsList2 != null && !productsList2.isEmpty()) {
				List<String> prod = new ArrayList<String>();
				for (ProductFetch productFetch : productsList2) {
					if (!prod.contains(productFetch.getName())) {
						prod.add(productFetch.getName());
						productFetch.setStoreName(productFetch.getGstinLocationfetch()
								.getGstinLocation());
						productFetch.setGstinLocationfetch(null);
						productsList.add(productFetch);
					}
				}

			}*/

		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(productsList);
	}
	
	@RequestMapping(value = "/getInventoryProductNameList", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody List<String> getInventoryProductNameList(@RequestParam("term") String query,@RequestParam("gNo") String gstnNo,@RequestParam("location") String location,@RequestParam("documentType") String documentType,HttpServletRequest httpRequest)
	{
		logger.info("Entry");
		String productTypeCall = documentType;
		List<String> productList = null;
		System.out.println("gstnNo : " + gstnNo + ",location:"+ location + ",documentType :"+ documentType);
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		int locationNew=Integer.parseInt(location);	
		productList = getProductListAutoComplete(query,loginMaster.getOrgUId(), productTypeCall, locationNew);
		

		logger.info("Exit");
		return productList;
	} 

	@RequestMapping(value={"/inventoryAddProductList"},method=RequestMethod.POST)
	public @ResponseBody String getInventoryGenericProductsList(@RequestParam("gNo") String gstnNo,@RequestParam("location") String locationUniqueSequence,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");
		String uri = GSTNUtil.getLastURIPart(httpRequest.getRequestURI());
		List<Product> productsList = new ArrayList<Product>();
		Map<String, Object> mapResponse = new HashMap<String, Object>();

		try {
			mapResponse = gstinDetailsController.checkIfGstinBelongsToUser(httpRequest, gstnNo, locationUniqueSequence);
			if (mapResponse.get(GSTNConstants.RESPONSE).equals(true)) {
				GSTINDetails gstin = (GSTINDetails) mapResponse.get("GSTINDetails");
				Integer location = gstinDetailsController.fetchLocationFromUniqueSequence(gstin,locationUniqueSequence);
				LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
				Integer orgUId = loginMaster.getOrgUId();
				String idsValuesToFetch = userMasterService.getDependentIds(loginMaster.getuId(),loginMaster.getUserRole());
				
					productsList = productService.getProductsList(idsValuesToFetch,orgUId,"invoice",location);
				
			}

		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(productsList);
	}
	
	
	
}
