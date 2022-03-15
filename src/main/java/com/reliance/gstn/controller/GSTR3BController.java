/**
 * 
 */
package com.reliance.gstn.controller;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;

import com.google.gson.Gson;
import com.reliance.gstn.admin.exception.GSTR3BApiException;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.GSTR3BModel;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.service.GSTINDetailsService;
import com.reliance.gstn.service.GSTR3BService;
import com.reliance.gstn.util.AspApiConstants;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Sandeep.Jalagam
 *
 */
@Controller
public class GSTR3BController {
	private static final Logger logger = Logger.getLogger(GSTR3BController.class);

	@Value("${client_id}")
	private String gstr3bMobileClientId;

	@Value("${secret_key}")
	private String gstr3bMobileSecretKey;

	@Value("${app_code}")
	private String gstr3bMobileAppCode;

	@Value("${desktop.ewaybill.client_id}")
	private String gstr3bDesktopEwaybillClientId;

	@Value("${desktop.ewaybill.secret_key}")
	private String gstr3bDesktopEwaybillSecretKey;

	@Value("${desktop.ewaybill.app_code}")
	private String gstr3bDesktopEwaybillAppCode;
	
	@Autowired
	public GSTR3BService gstr3BbService;

	@Autowired
	public GSTINDetailsService gstnDetailsService;

	@RequestMapping(value = { "/getGstr3bJiogstData" }, method = RequestMethod.POST)
	public @ResponseBody Object getGstr3bJiogstData(@RequestBody GSTR3BModel gstr3b, @RequestHeader HttpHeaders headers,
			HttpServletRequest httpRequest) throws Exception {
		return gstr3BbService.getGstr3bJiogstData(gstr3b);
	}

	@RequestMapping(value = { "/getGstr3bJiogstL2" }, method = RequestMethod.POST)
	public @ResponseBody Object getGstr3bJiogstL2(@RequestBody GSTR3BModel gstr3b, @RequestHeader HttpHeaders headers,
			HttpServletRequest httpRequest) throws Exception {
		return gstr3BbService.getGstr3bJiogstL2(gstr3b);
	}

	@RequestMapping(value = { "/gstr3bSaveToJiogst" }, method = RequestMethod.POST)
	public @ResponseBody Object gstr3bSaveToJiogst(@RequestBody String gstr3b, @RequestHeader HttpHeaders headers,
			HttpServletRequest httpRequest) throws Exception {
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		Gson gson = new Gson();
		GSTR3BModel gstr3bModel = gson.fromJson(gstr3b, GSTR3BModel.class);
		gstr3bModel.setFp(gstr3bModel.getRet_period());
		gstr3bModel.setUserId(loginMaster.getUserId());
		gstr3bModel.setOrgid("" + loginMaster.getOrgUId());
		return gstr3BbService.gstr3bSaveToJiogst(gstr3b, gstr3bModel);
	}

	@RequestMapping(value = { "/getGstr3bJiogstRetStatus" }, method = RequestMethod.POST)
	public @ResponseBody Object getGstr3bJiogstRetStatus(@RequestBody GSTR3BModel gstr3bModel,
			@RequestHeader HttpHeaders headers, HttpServletRequest httpRequest) throws Exception {
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		gstr3bModel.setUserId(loginMaster.getUserId());
		gstr3bModel.setOrgid("" + loginMaster.getOrgUId());
		return gstr3BbService.getGstr3bJiogstRetStatus(gstr3bModel);
	}

	@RequestMapping(value = { "/getGstr3bJiogstList" }, method = RequestMethod.POST)
	public @ResponseBody Object getGstr3bJiogstList(@RequestBody GSTR3BModel gstr3bModel,
			@RequestHeader HttpHeaders headers, HttpServletRequest httpRequest) throws Exception {
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		gstr3bModel.setUserId(loginMaster.getUserId());
		gstr3bModel.setOrgid("" + loginMaster.getOrgUId());
		return gstr3BbService.getGstr3bJiogstList(gstr3bModel);
	}

	@RequestMapping(value = { "/getGstr3bGstnSave" }, method = RequestMethod.POST)
	public @ResponseBody Object getGstr3bGstnSave(@RequestBody String gstr3b, @RequestHeader HttpHeaders headers,
			HttpServletRequest httpRequest) throws Exception {
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		GSTR3BModel gstr3bModel = new GSTR3BModel();
		gstr3bModel.setUserId(loginMaster.getUserId());
		gstr3bModel.setOrgid("" + loginMaster.getOrgUId());
		gstr3bModel.setUsername(headers.get("username").get(0));
		gstr3bModel.setFp(headers.get("fp").get(0));
		gstr3bModel.setGstin(headers.get("gstin").get(0));
		if (headers.get("otp") != null)
			gstr3bModel.setOtp(headers.get("otp").get(0));
		return gstr3BbService.getGstr3bGstnSave(gstr3bModel, gstr3b);
	}

	@RequestMapping(value = { "/getGstr3bGstnl2" }, method = RequestMethod.POST)
	public @ResponseBody Object getGstr3bGstnl2(@RequestBody GSTR3BModel gstr3bModel,
			@RequestHeader HttpHeaders headers, HttpServletRequest httpRequest) throws Exception {
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		gstr3bModel.setUserId("9867779928");
		gstr3bModel.setOrgid("" + "9867779928");
		gstr3bModel.setUsername(headers.get("username").get(0));
		gstr3bModel.setFp(headers.get("fp").get(0));
		gstr3bModel.setGstin(headers.get("gstin").get(0));
		return gstr3BbService.getGstr3bGstnl2(gstr3bModel);
	}

	@ExceptionHandler({ GSTR3BApiException.class, SocketTimeoutException.class, ResourceAccessException.class,
			Exception.class })
	public ResponseEntity<Map<String, Object>> exceptionHandler(HttpServletRequest request, Exception ex) {
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> exObject;
		if (ex instanceof GSTR3BApiException) {
			entity = new ResponseEntity<>(getExcObjDesc(((GSTR3BApiException) ex).getMessage()), HttpStatus.OK);
		} else if (ex instanceof SocketTimeoutException || ex instanceof ResourceAccessException
				|| ex instanceof Exception) {
			exObject = new HashMap<>();
			exObject.put(AspApiConstants.ERROR_CODE, AspApiConstants.GSTR3B_CONNECTION_ERROR);
			exObject.put(AspApiConstants.ERROR_DESC, " Connection Error: Unable to connect to destination ");
			exObject.put(AspApiConstants.ERROR_GRP, AspApiConstants.GSTR3B_CONNECTION_ERROR);
			entity = new ResponseEntity<>(exObject, HttpStatus.GATEWAY_TIMEOUT);
		}
		logger.info(" Exception " + entity);
		return entity;
	}

	public Map<String, Object> getExcObjDesc(String message) {
		Map<String, Object> exObject = new LinkedHashMap<>();
		exObject.put(AspApiConstants.STATUS_CD, AspApiConstants.ER_CD);
		exObject.put(AspApiConstants.STATUS, AspApiConstants.FAILURE);
		if (message != null)
			exObject.put(AspApiConstants.ERROR_DESC, message);
		else
			exObject.put(AspApiConstants.ERROR_DESC, " Connection Error: Unable to connect to destination ");
		exObject.put(AspApiConstants.ERROR_CODE, AspApiConstants.GSTR3B_ERROR);
		return exObject;
	}

	public boolean validateGstr3bRequest(String gstinNo, HttpServletRequest httpRequest) throws GSTR3BApiException {
		boolean flag = false;
		try {
			LoginMaster loginMaster = LoginUtil.getLoginUser(httpRequest);
			GSTINDetails GSTINDetails = gstnDetailsService.getGstinDetailsFromGstinNo(gstinNo,
					loginMaster.getPrimaryUserUId());
			if (GSTINDetails != null)
				flag = true;

		} catch (Exception e) {
			throw new GSTR3BApiException("Invalid User");
		}
		return flag;
	}

	@RequestMapping(value = "/preparegstr3b", method = RequestMethod.POST)
	public String getPrepareGstr3B(@RequestParam String gstinId, @RequestParam String financialPeriod, HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");	
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		String appCode = null;
		if(loginMaster.getLoggedInThrough().equalsIgnoreCase("MOBILE")){
			appCode = gstr3bMobileAppCode;
		}else{
			appCode = gstr3bDesktopEwaybillAppCode;
		}
		model.addAttribute("userId", loginMaster.getOrgUId());
		model.addAttribute("appCode", appCode);
		model.addAttribute("gstinId", gstinId);
		model.addAttribute("financialPeriod", financialPeriod);
		model.addAttribute("loggedInFrom", loginMaster.getLoggedInThrough());
		logger.info("Exit");
		return PageRedirectConstants.PREPARE_GSTR3B_PAGE;
	}
	
	@RequestMapping(value = "/returntopreviousmenuasp", method = RequestMethod.POST)
	public String returnToPreviousMenuasp(@RequestParam String gstinId, @RequestParam String financialPeriod,@RequestParam String gstrType, 
			HttpServletRequest httpRequest, Model model){
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.UPLOAD_JIO_GST_PAGE;
		try{			   
		    model.addAttribute("L0_gstin", gstinId);
	        model.addAttribute("L0_fp", financialPeriod);
	        model.addAttribute("GSTR_type", gstrType);
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");		
		return  pageRedirect;
	}
	
	@RequestMapping(value = "/draftgstr3bform", method = RequestMethod.POST)
	public String getDraftGstr3B(@RequestParam String gstinId, @RequestParam String financialPeriod, HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");	
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		String appCode = null;
		if(loginMaster.getLoggedInThrough().equalsIgnoreCase("MOBILE")){
			appCode = gstr3bMobileAppCode;
		}else{
			appCode = gstr3bDesktopEwaybillAppCode;
		}
		model.addAttribute("userId", loginMaster.getOrgUId());
		model.addAttribute("appCode", appCode);
		model.addAttribute("gstinId", gstinId);
		model.addAttribute("financialPeriod", financialPeriod);
		model.addAttribute("loggedInFrom", loginMaster.getLoggedInThrough());
		model.addAttribute("username", "Reliance2.MH.TP.1");
		logger.info("Exit");
		return PageRedirectConstants.DRAFT_GSTR3B_PAGE;
	}
	
}
