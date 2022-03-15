package com.reliance.gstn.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.model.FeedbackDetails;
import com.reliance.gstn.model.InventoryHistoryDetails;
import com.reliance.gstn.model.InventoryProductSave;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.OpeningStockJSObjectSave;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.GenerateUniqueSequnceService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.service.InventoryService;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author @kshay Mohite
 *
 */

@Controller
public class InventoryController {

	private static final Logger logger = Logger
			.getLogger(InventoryController.class);

	@Autowired
	private UserMasterService userMasterService;

	@Autowired
	InventoryService inventoryService;

	@Autowired
	GenerateUniqueSequnceService generateUniqueSequnceService;

	@Value("${STORE_CANNOT_BE_BLANK}")
	private String storeCannotBeBlank;

	@RequestMapping(value = "/getopeningstock", method = RequestMethod.GET)
	public String getOpeningSTockDetails(Model model) {
		return PageRedirectConstants.OPENING_CHARGE_DETAILS_PAGE;
	}

	@RequestMapping(value = "/increaseinventoryhistory", method = RequestMethod.GET)
	public String addIncreaseInventoryHistory(Model model) {
		return PageRedirectConstants.MANAGE_INCREASE_INVENTORY_HISTORY_PAGE;
	}
	
	
	@RequestMapping(value = "/decreaseinventoryhistory", method = RequestMethod.GET)
	public String addDecreaseInventoryHistory(Model model) {
		return PageRedirectConstants.MANAGE_DECREASE_INVENTORY_HISTORY_PAGE;
	}

	@RequestMapping(value = { "/increaseinventory", "/decreaseinventory" }, method = RequestMethod.GET)
	public String getIncreaseDecreaseInventoryPage(Model model,
			HttpServletRequest httpRequest) {
		logger.info("Entry");
		String uri = GSTNUtil.getLastURIPart(httpRequest.getRequestURI());
		if (uri.equals("/increaseinventory")) {
			model.addAttribute(GSTNConstants.INVENTORY_TYPE_PAGE,
					GSTNConstants.INCREASE);
			model.addAttribute(GSTNConstants.INVENTORY_TYPE,
					GSTNConstants.INVENTORY_TYPE_INCREASE);
		} else {
			model.addAttribute(GSTNConstants.INVENTORY_TYPE_PAGE,
					GSTNConstants.DECREASE);
			model.addAttribute(GSTNConstants.INVENTORY_TYPE,
					GSTNConstants.INVENTORY_TYPE_DECREASE);
		}
		logger.info("Exit");
		return PageRedirectConstants.INCREASE_DECREASE_INVENTORY_PAGE;
	}

	/* Check if  user is primary or secondary */
	
	@RequestMapping(value = "/getgstinforloggedinuser", method = RequestMethod.POST)
	public @ResponseBody String getGstinForloggedInUser(
			HttpServletRequest httpRequest) {
		logger.info("Entry");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			LoginMaster loginMaster = (LoginMaster) LoginUtil
					.getLoginUser(httpRequest);
			UserMaster userMaster = userMasterService
					.getUserMasterById(loginMaster.getuId());
			if (loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)) { 
				responseMap = inventoryService.getGSTINListwithms(loginMaster
						.getuId());
			} else {
				responseMap = inventoryService
						.getGstinDetailsMappedForSecondaryUserByUid(loginMaster
								.getuId());
				userMaster = userMasterService.getUserMasterById(userMaster
						.getReferenceId());
			}
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(responseMap);
	}

	@RequestMapping(value = "/showinventoryproductbygstinnlocationid", method = RequestMethod.POST)
	public @ResponseBody String showInventoryProductByGstinNLocationId(
			@RequestParam("locationId") Integer locationId,
			HttpServletRequest httpRequest) {
		logger.info("Entry");
		Map<String, Object> responseMap = new HashMap<String, Object>();
         boolean checkauthenticateStoreId = checkForStoreId(locationId , httpRequest);
		
		if(checkauthenticateStoreId){
		try {
			responseMap = inventoryService
					.getInventoryProductByGstinIdNLocationIdMicroService(locationId);
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		}else{
			responseMap.put("access", GSTNConstants.ACCESSVIOLATION);
		}
		logger.info("Exit");
		return new Gson().toJson(responseMap);
	}

	@RequestMapping(value = "/saveinventorydetails", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveInventoryDetails(
			@Valid @RequestBody InventoryProductSave inventoryProductSave,
			HttpServletRequest httpRequest) {
		logger.info("Entry");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			LoginMaster loginMaster = (LoginMaster) LoginUtil
					.getLoginUser(httpRequest);
			if (inventoryProductSave != null) {
				responseMap = inventoryService.saveInventoryDetails(
						inventoryProductSave, loginMaster.getuId());
			}
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return responseMap;
	}

	@RequestMapping(value = "/movementbetweenstores", method = RequestMethod.GET)
	public String movementBetweenStores(Model model) {
		return PageRedirectConstants.MOVEMENT_BETWEEN_STORES;
	}

	@RequestMapping(value = "/saveinventorydetailsbetweenstores", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveInventoryDetailsBetweenStores(
			@Valid @RequestBody InventoryProductSave inventoryProductSave,
			HttpServletRequest httpRequest) {
		logger.info("Entry");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		boolean checkauthenticateStoreId = checkForStoreId(inventoryProductSave.getProductList().get(0).getFromStoreId() , httpRequest);
		boolean checkauthenticateStoreId1 = checkForStoreId(inventoryProductSave.getProductList().get(0).getToStoreId() , httpRequest);
		
		if(checkauthenticateStoreId && checkauthenticateStoreId1){
		try {
			HttpSession session = httpRequest.getSession();
			LoginMaster loginMaster = (LoginMaster) LoginUtil
					.getLoginUser(httpRequest);
			DateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
			String pattern = f.format(new java.sql.Timestamp(new Date()
					.getTime()));
			Object object = session.getAttribute("uniqueSequenceid");
			Integer id = 0;
			if (object == null) {
				id = generateUniqueSequnceService.getDocSequenceId(pattern,
						loginMaster.getOrgUId(), 1);
				session.setAttribute("uniqueSequenceid", id);
			} else
				id = (Integer) object;
			if (inventoryProductSave != null) {
				inventoryProductSave.setUniqueSequenceid(id);
				responseMap = inventoryService.saveProductNInventoryDetailsStores(inventoryProductSave, loginMaster.getOrgUId());
				session.setAttribute("uniqueSequenceid",responseMap.get("uniqueSequenceid"));
				responseMap.remove("uniqueSequenceid");	
			}
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		}else{
			responseMap.put("access", GSTNConstants.ACCESSVIOLATION);
		}
		logger.info("Exit");
		return responseMap;
	}

	@RequestMapping(value = "/fetchopeningstockwrtstoreid", method = RequestMethod.POST)
	public @ResponseBody String fetchOpeningStockwrtStoreId(
			@RequestParam("locationId") Integer locationId, Model model,
			HttpServletRequest httpRequest) {
		logger.info("Entry");
		Map<String, Object> responseMap = new HashMap<String, Object>();
        boolean checkauthenticateStoreId = checkForStoreId(locationId , httpRequest);
		
		if(checkauthenticateStoreId){
		try {
			LoginMaster loginMaster = (LoginMaster) LoginUtil
					.getLoginUser(httpRequest);
			responseMap = inventoryService.getOpeningStockProductList(
					loginMaster.getuId(), locationId);

			logger.info("My Result::" + responseMap);
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		}else{
			responseMap.put("access", GSTNConstants.ACCESSVIOLATION);
		}
		return new Gson().toJson(responseMap);
	}

	@RequestMapping(value = "/saveopeningstockproductlist", method = RequestMethod.POST)
	public @ResponseBody String saveOpeningStockProduct(
			@RequestBody OpeningStockJSObjectSave openingStockJSObjectSave,
			Model model, HttpServletRequest httpRequest) {

		logger.info("Entry");
		LoginMaster loginMaster = (LoginMaster) LoginUtil
				.getLoginUser(httpRequest);
		Map<String, Object> responseMap = new HashMap<String, Object>();
         boolean checkauthenticateStoreId = checkForStoreId(openingStockJSObjectSave.getProductList().get(0).getStoreId() , httpRequest);
		
		if(checkauthenticateStoreId){
		try {

			responseMap = inventoryService.saveOpeningstockProductList(
					openingStockJSObjectSave, loginMaster.getuId());

			logger.info("My Result::" + responseMap);
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		}else{
			responseMap.put("access", GSTNConstants.ACCESSVIOLATION);
		}
		return new Gson().toJson(responseMap);
	}

	@RequestMapping(value = "/getstockstatusdetailedreportpage", method = RequestMethod.GET)
	public String getStockStatusDetailedReportPage(Model model) {
		return PageRedirectConstants.STOCK_STATUS_DETAILED_REPORT_PAGE;
	}

	@RequestMapping(value = "/getproductnamelistbystoreid", method = RequestMethod.POST)
	public @ResponseBody String getProductsByStoreId(
			@RequestParam("locationId") Integer locationId,
			HttpServletRequest httpRequest) {
		logger.info("Entry");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			if (locationId != 0 || locationId != null) {
				responseMap = inventoryService
						.getProductNameListByStoreId(locationId);
			} else {
				responseMap = GSTNUtil.showErrMsg(
						GSTNConstants.STATUS_CODE_406, storeCannotBeBlank,
						GSTNConstants.FAILURE);
			}
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(responseMap);
	}

	@RequestMapping(value = "/generatestockstatusdetailedreport", method = RequestMethod.POST)
	public @ResponseBody String generateStockStatusDetailedReport(
			@RequestParam("locationId") Integer locationId,
			@RequestParam("productId") Integer productId,
			HttpServletRequest httpRequest) {
		logger.info("Entry");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			responseMap = inventoryService.generateStockStatusDetailedReport(
					locationId, productId);
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(responseMap);
	}

	@RequestMapping(value = "/getinventoryreasonsforincrease", method = RequestMethod.GET)
	public @ResponseBody String getInventoryReasonsIncrease() {
		logger.info("Entry");
		Map<String, String> responseMap = new HashMap<>();
		try {
			responseMap = inventoryService.getReasonsForIncrease();
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(responseMap);

	}

	@RequestMapping(value = "/getinventoryreasonsfordecrease", method = RequestMethod.GET)
	public @ResponseBody String getInventoryReasonsdecrease() {
		logger.info("Entry");
		Map<String, String> responseMap = new HashMap<>();
		try {
			responseMap = inventoryService.getReasonsForDecrease();
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(responseMap);

	}
	
	public Integer getSessionUniqueSequence(LoginMaster loginMaster,HttpServletRequest httpRequest){
		logger.info("Entry");
		HttpSession session = httpRequest.getSession();
		DateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
		String pattern = f.format(new java.sql.Timestamp(new Date().getTime()));
		Object object = session.getAttribute("uniqueSequenceid");
		Integer id = 0;
		if (object == null) {
			id = generateUniqueSequnceService.getDocSequenceId(pattern,loginMaster.getOrgUId(), 1);
			session.setAttribute("uniqueSequenceid", id);
		}else{
			id = (Integer) object;
		}
		logger.info("Exit");
		return id;
	}

	@RequestMapping(value = "/saveinventorydetailsfromInventorty", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveinventorydetailsfromInventorty(
			@Valid @RequestBody InventoryProductSave inventoryProductSave,
			HttpServletRequest httpRequest) {
		logger.info("Entry");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		boolean checkauthenticateStoreId = checkForStoreId(inventoryProductSave.getProductList().get(0).getStoreId() , httpRequest);
		if(checkauthenticateStoreId){
		try {
			HttpSession session = httpRequest.getSession();
			LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
			DateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
			String pattern = f.format(new java.sql.Timestamp(new Date().getTime()));
			Object object = session.getAttribute("uniqueSequenceid");
			Integer id = 0;
			if (object == null) {
				id = generateUniqueSequnceService.getDocSequenceId(pattern,loginMaster.getOrgUId(), 1);
				session.setAttribute("uniqueSequenceid", id);
			} else
				id = (Integer) object;
			if (inventoryProductSave != null) {
				inventoryProductSave.setUniqueSequenceid(id);
				responseMap = inventoryService.saveInventoryDetailsFromIncreDecreInventry(inventoryProductSave, loginMaster.getuId());
				session.setAttribute("uniqueSequenceid",responseMap.get("uniqueSequenceid"));
				responseMap.remove("uniqueSequenceid");	
			}
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		}else{
			responseMap.put("access", GSTNConstants.ACCESSVIOLATION);
		}
		logger.info("Exit");
		return responseMap;
	}
	
	@RequestMapping(value = "/getgoodsbystoreidandcurrentdate" , method = RequestMethod.POST)
	public @ResponseBody String showGoodsByStoreIdAndCurrentDate(@RequestParam("gstinNo") Integer gstinNo,@RequestParam("storeId") Integer storeId,@RequestParam("tDate") String tDate, HttpServletRequest httpRequest){
		logger.info("Entry");
		Map<String,Object> responseMap = new HashMap<>();
		
		boolean checkauthenticateStoreId = checkForStoreId(storeId , httpRequest);
		if(checkauthenticateStoreId){
		try {
			responseMap=inventoryService.getGoodsByStoreIdAndcurrentDate(gstinNo,storeId, tDate);
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		}else{
			responseMap.put("access", GSTNConstants.ACCESSVIOLATION);
		}
		logger.info("Exit");
		return new Gson().toJson(responseMap);		
	}
	
public boolean checkForStoreId (Integer locationId ,HttpServletRequest httpRequest){
		
		boolean check = true;
		
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		
		try {
			check = userMasterService.getOrgIdCheckStatusWithStoreId(locationId , loginMaster.getOrgUId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return check;
		
		
	}
	
@RequestMapping(value="/getinventoryhistorydetails",method=RequestMethod.POST)
public @ResponseBody String getInventoryHistoryDetails(Model model,HttpServletRequest httpRequest) {
	logger.info("Entry");
	
	 List<Object[]> responseList = new ArrayList<Object[]>();
	
	try {
		responseList = inventoryService.getInventoryHistory();
		
	} catch (Exception e) {
		logger.error("Error in:",e);
	}
	logger.info("Exit");
	return new Gson().toJson(responseList);		
}


}