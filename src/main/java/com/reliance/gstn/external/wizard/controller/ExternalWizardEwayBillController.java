package com.reliance.gstn.external.wizard.controller;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;

import com.google.gson.Gson;
import com.reliance.gstn.admin.exception.EwayBillApiException;
import com.reliance.gstn.admin.model.HSNDetails;
import com.reliance.gstn.admin.service.HSNService;
import com.reliance.gstn.external.wizard.service.ExternalWizardEwayBillService;
import com.reliance.gstn.model.EwayBillCalculateTax;
import com.reliance.gstn.model.EwayBillItemList;
import com.reliance.gstn.model.EwayBillRateMaster;
import com.reliance.gstn.model.EwayBillWIAuth;
import com.reliance.gstn.model.EwayBillWIMaster;
import com.reliance.gstn.model.EwayBillWIMasterRes;
import com.reliance.gstn.model.EwayBillWITransaction;
import com.reliance.gstn.model.ExternalWizardEwayBill;
import com.reliance.gstn.model.PinCode;
import com.reliance.gstn.model.State;
import com.reliance.gstn.model.UnitOfMeasurement;
import com.reliance.gstn.service.EWayBillService;
import com.reliance.gstn.service.GstinValidationService;
import com.reliance.gstn.service.PinCodeService;
import com.reliance.gstn.service.SACService;
import com.reliance.gstn.service.StateService;
import com.reliance.gstn.service.UnitOfMeasurementService;
import com.reliance.gstn.util.AspApiConstants;
import com.reliance.gstn.util.BaseRequestValidation;
import com.reliance.gstn.util.EWBWIUtil;
import com.reliance.gstn.util.EncryptionUtil;
import com.reliance.gstn.util.NICUtil;

@Controller
@RequestMapping(value = "/ewaybill")
public class ExternalWizardEwayBillController {

	private static final Logger logger = Logger.getLogger(ExternalWizardEwayBillController.class);

	@Autowired
	ExternalWizardEwayBillService externalWizardEwayBillService;

	@Autowired
	public UnitOfMeasurementService unitOfMeasurementService;

	@Autowired
	public StateService stateService;

	@Autowired
	public SACService sacService;

	@Autowired
	public HSNService HSNService;
	@Autowired
	public PinCodeService pinCodeService;
	@Autowired
	public GstinValidationService gstinValidationService;

	@Autowired
	private EWayBillService eWayBillService;

	@Value("${${env}.PATH_FOR_INVOICE_PDF}")
	private String downloadEWayBillPdfPath;

	@Value("${wizard.aes.encryption.key}")
	private String aesEncryptioney;

	private static List<Map> stateMapList = new ArrayList<>();
	private static List<Map> unitOfMeasurementMapList = new ArrayList<>();

	@RequestMapping(value = "/getewbwimaster", method = RequestMethod.POST)
	@ResponseBody
	public Object getMasters(@RequestBody EwayBillWIMaster EwayBillWIMaster, @RequestHeader HttpHeaders headers)
			throws Exception {
		logger.info("Entry getMasters :::");
		List<EwayBillWIMasterRes> ewayBillMasterL = new ArrayList<>();
		if (EWBWIUtil.isValidRequest(headers)) {
			if (EwayBillWIMaster.getMasterType().equals("ewayBillRate"))
				return EWBWIUtil.getLoadEwayBillRateMasterList();
			else
				ewayBillMasterL = externalWizardEwayBillService.getMasters(EwayBillWIMaster);
		} else {
			EwayBillWIMasterRes ewayBillWIMasterRes = new EwayBillWIMasterRes();
			ewayBillWIMasterRes.setError(true);
			Map<String, String> errorMap = new HashMap<String, String>();
			errorMap.put("404", "Unauthorized Access");
			ewayBillWIMasterRes.setErrorMsg(errorMap);
			ewayBillMasterL.add(ewayBillWIMasterRes);
		}
		logger.info("Exit getMasters");
		return ewayBillMasterL;
	}

	@PostConstruct
	public void init() {
		try {
			if (!EWBWIUtil.isAuthInfoLoaded()) {
				List<EwayBillWIAuth> ewayBillWIAuthL = externalWizardEwayBillService.getAuthInfo();
				EWBWIUtil.loadAuthInfo(ewayBillWIAuthL);
			}
			if (!EWBWIUtil.isLoadEwayBillWIMasterList()) {
				List<EwayBillWIMaster> ewayBillWIMasterList = externalWizardEwayBillService.getEwayBillWIMasterList();
				EWBWIUtil.loadEwayBillWIMasterList(ewayBillWIMasterList);
			}

			if (!EWBWIUtil.isLoadEwayBillRateMasterList()) {
				List<EwayBillRateMaster> ewayBillWIMasterList = (List<EwayBillRateMaster>) eWayBillService
						.getEwayBillRateList();
				EWBWIUtil.loadEwayBillRateMasterList(ewayBillWIMasterList);
			}

		} catch (Exception e) {
			logger.info("Error in eway bill auth initiliozation  ", e);
		}
	}

	@RequestMapping(value = "/getewbstatecodemaster", method = RequestMethod.POST)
	@ResponseBody
	public Object getewbstatecodemaster(@RequestHeader HttpHeaders headers) {
		logger.info("getewbstatecodemaster Entry");
		if (EWBWIUtil.isValidRequest(headers)) {
			try {
				if (stateMapList.isEmpty()) {
					List<State> stateList = stateService.listState();
					for (State state : stateList) {
						Map<String, Object> map = new LinkedHashMap<>();
						map.put("stateName", state.getStateName());
						map.put("stateCode", state.getStateCode());
						map.put("stateId", state.getStateId());
						stateMapList.add(map);
					}
				}
			} catch (Exception e) {
				logger.error("getewbstatecodemaster Error in:", e);
			}
			logger.info("getewbstatecodemaster Exit");
			return stateMapList;
		} else {
			return NICUtil.getExcObjDesc();
		}

	}

	@RequestMapping(value = "/getewbunitOfMeasurementmaster", method = RequestMethod.POST)
	public @ResponseBody Object getewbunitOfMeasurementmaster(@RequestHeader HttpHeaders headers) {
		logger.info("getewbunitOfMeasurementmaster Entry");
		if (EWBWIUtil.isValidRequest(headers)) {
			try {
				if (unitOfMeasurementMapList.isEmpty()) {
					List<UnitOfMeasurement> unitOfMeasurementList = unitOfMeasurementService.getUnitOfMeasurement();
					for (UnitOfMeasurement unitOfMeasurement : unitOfMeasurementList) {
						Map<String, String> map = new LinkedHashMap<>();
						map.put("quantityCode", unitOfMeasurement.getQuantityCode());
						map.put("quantityDes", unitOfMeasurement.getQuantityDescription());
						unitOfMeasurementMapList.add(map);
					}
				}
			} catch (Exception e) {
				logger.error("getewbunitOfMeasurementmaster Error in:", e);
			}
			logger.info("getewbunitOfMeasurementmaster Exit");
			return unitOfMeasurementMapList;
		} else {
			return NICUtil.getExcObjDesc();
		}

	}

	@RequestMapping(value = "/getewbHSNCodeList", method = RequestMethod.POST)
	public @ResponseBody Object getHSNDetailsCodeList(@RequestHeader HttpHeaders headers,
			@RequestBody ExternalWizardEwayBill externalWizardEwayBill) {
		logger.info("getewbHSNCodeList Entry");
		if (EWBWIUtil.isValidRequest(headers)
				&& BaseRequestValidation.getBaseResponseObject(BaseRequestValidation.HSNCODE, externalWizardEwayBill)) {
			return getHSNCodeList(externalWizardEwayBill.getKey());
		} else {
			return NICUtil.getExcObjDesc();
		}

	}

	private Object getHSNCodeList(String query) {
		query = query.toLowerCase();
		List<Map> matched = new ArrayList<Map>();
		List<Object[]> distinctHSNCodeList = HSNService.getHSNCodeList(query);
		if (distinctHSNCodeList != null && !distinctHSNCodeList.isEmpty()) {
			for (int i = 0; i < distinctHSNCodeList.size(); i++) {
				Object[] obj = distinctHSNCodeList.get(i);
				Map<String, Object> map = new LinkedHashMap<>();
				map.put("hsnCode", obj[0]);
				map.put("hsnDesc", obj[1]);
				map.put("hsnId", obj[2]);
				matched.add(map);
			}
			return matched;
		} else {
			return getExcObjDesc("Invalid HSN QUERY");
		}
	}

	@RequestMapping(value = "/getewbSacCodeList", method = RequestMethod.POST)
	public @ResponseBody Object getTechList(@RequestHeader HttpHeaders headers,
			@RequestBody ExternalWizardEwayBill externalWizardEwayBill) {
		logger.info("getewbSacCodeList Entry");
		if (EWBWIUtil.isValidRequest(headers)
				&& BaseRequestValidation.getBaseResponseObject(BaseRequestValidation.HSNCODE, externalWizardEwayBill)) {
			return getSACCodeList(externalWizardEwayBill.getKey());
		} else {
			return NICUtil.getExcObjDesc();
		}
	}

	private Object getSACCodeList(String query) {
		logger.info("Entry");
		List<Map> matched = new ArrayList<Map>();
		query = query.toLowerCase();
		List<Object[]> distinctSacCodeList = sacService.getSACCodeList(query);
		if (distinctSacCodeList != null && !distinctSacCodeList.isEmpty()) {
			for (int i = 0; i < distinctSacCodeList.size(); i++) {
				Object[] obj = distinctSacCodeList.get(i);
				Map<String, String> map = new LinkedHashMap<>();
				map.put("sacCode", (String) obj[0]);
				map.put("sacDesc", (String) obj[1]);
				matched.add(map);
			}
			return matched;
		} else {
			return NICUtil.getExcObjDesc("Invalid SACCode QUERY");
		}

	}

	@RequestMapping(value = "/getewbPinCodeList", method = RequestMethod.POST)
	public @ResponseBody Object getPinCode(@RequestHeader HttpHeaders headers,
			@RequestBody ExternalWizardEwayBill externalWizardEwayBill) {
		logger.info("getewbPinCodeList Entry");
		if (EWBWIUtil.isValidRequest(headers)
				&& BaseRequestValidation.getBaseResponseObject(BaseRequestValidation.PINCODE, externalWizardEwayBill)) {
			Map<String, Object> sacCodeList = getPinCodeList(externalWizardEwayBill.getKey());
			logger.info("getewbPinCodeList " + sacCodeList);
			return sacCodeList;
		} else {
			return NICUtil.getExcObjDesc();
		}
	}

	private Map<String, Object> getPinCodeList(String query) {
		query = query.toLowerCase();
		List<PinCode> distinctPinCodeList = pinCodeService.getPinCodeListByPincode(query);
		for (PinCode PinCode : distinctPinCodeList) {
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("pinCode", PinCode.getPinCode());
			map.put("district", PinCode.getDistrict());
			State state = null;
			try {
				state = stateService.getStateById(PinCode.getStateId());
				if (state != null) {
					map.put("stateCode", state.getStateCode());
					map.put("stateName", state.getStateName());
					map.put("stateId", state.getStateId());
				}
			} catch (Exception e) {

			}

			return map;
		}

		return NICUtil.getExcObjDesc("Invalid PinCode");
	}

	@RequestMapping(value = "/getewbcustomerbonboarding", method = RequestMethod.POST)
	public @ResponseBody Object getewbCustomerbOnBoarding(@RequestHeader HttpHeaders headers,
			@RequestBody Map<String, String> customerbonboarding) {
		logger.info("getewbCustomerbOnBoarding Entry");
		Object object = null;
		if (EWBWIUtil.isValidRequest(headers) && BaseRequestValidation
				.getBaseResponseObject(BaseRequestValidation.EWAYBILL_CUSTOMERBONBOARDING, customerbonboarding)) {
			String response = gstinValidationService.isValidGstin(customerbonboarding.get(AspApiConstants.GSTIN));
			if (response != null && !response.trim().equals("")) {
				Map<String, Object> responseMap = (Map) NICUtil.getResponseStringToObject(response, Map.class);
				String isError = (String) responseMap.get("err_cd");
				String isActive = (String) responseMap.get("sts");
				if (isError != null && !isError.isEmpty()) {
					object = getExcObjDesc("INVALID GSTIN");
					logger.info("getewbCustomerbOnBoarding object ::::" + object);
				} else if (!isActive.equalsIgnoreCase("Active")) {
					object = getExcObjDesc("INVALID GSTIN");
					logger.info("getewbCustomerbOnBoarding object ::::" + object);
				} else {
					customerbonboarding.put(AspApiConstants.APP_CODE, headers.get(AspApiConstants.APPCODE).get(0));
					object = eWayBillService.eWayBillOnBoarding(responseMap, customerbonboarding);
					logger.info("getewbCustomerbOnBoarding object ::::" + object);
				}
			} else {
				logger.info("getewbCustomerbOnBoarding exit");
				object = NICUtil.getExcObjDesc();
				logger.info("getewbCustomerbOnBoarding object ::::" + object);
			}

		} else {
			object = NICUtil.getExcObjDesc();
			logger.info("getewbCustomerbOnBoarding object ::::" + object);
		}
		logger.info("getewbCustomerbOnBoarding exit");
		return object;

	}

	@RequestMapping(value = "/getEwayBillCustomerOnboardedList", method = RequestMethod.POST)
	public @ResponseBody Object getEwayBillCustomerOnboardedList(@RequestHeader HttpHeaders headers,
			@RequestBody Map<String, String> customerbonboarding) {
		logger.info("getEwayBillCustomerOnboardedList start");
		if (EWBWIUtil.isValidRequest(headers)
				&& BaseRequestValidation.getBaseResponseObject(BaseRequestValidation.GSTIN_DETAILS, customerbonboarding)
				&& BaseRequestValidation.getBaseResponseObject(BaseRequestValidation.GENERATED_EWAYBILLLIST,
						customerbonboarding)) {
			String appCode = null;
			try {
				EncryptionUtil ecr = new EncryptionUtil(aesEncryptioney);
				appCode = ecr.encrypt(headers.get(AspApiConstants.APPCODE).get(0));
			} catch (Exception e) {

			}
			customerbonboarding.put(AspApiConstants.APP_CODE, appCode);
			return eWayBillService.getEwayBillCustomerOnboardedList(customerbonboarding);

		} else {
			return NICUtil.getExcObjDesc();
		}
	}

	@RequestMapping(value = "/getCustomerGstinDetails", method = RequestMethod.POST)
	public @ResponseBody Object getCustomerGstinDetails(@RequestHeader HttpHeaders headers,
			@RequestBody Map<String, String> request) {
		Object object = null;
		if (EWBWIUtil.isValidRequest(headers)
				&& BaseRequestValidation.getBaseResponseObject(BaseRequestValidation.GSTIN_DETAILS, request)) {
			logger.info("getCustomerGstinDetails start");
			String response = gstinValidationService.isValidGstin(request.get(AspApiConstants.GSTIN));
			if (response != null && !response.trim().equals("")) {
				Map<String, Object> responseMap = (Map) NICUtil.getResponseStringToObject(response, Map.class);
				String isError = (String) responseMap.get("err_cd");
				String isActive = (String) responseMap.get("sts");
				if (isError != null && !isError.isEmpty()) {
					object = getExcObjDesc("INVALID GSTIN");
				} else if (!isActive.equalsIgnoreCase("Active")) {
					object = getExcObjDesc("INVALID GSTIN");
				} else {
					Map<String, Object> retmap = new LinkedHashMap<>();
					retmap.put(AspApiConstants.GSTIN, request.get(AspApiConstants.GSTIN));
					retmap.put("company_name", responseMap.get("lgnm"));
					retmap.put("trader_name", responseMap.get("tradeNam"));
					retmap.put("trader_address", ((Map) responseMap.get("pradr")).get("addr"));
					object = retmap;
				}
			} else {
				object = NICUtil.getExcObjDesc();
			}

		} else {
			object = NICUtil.getExcObjDesc();
		}
		logger.info("getCustomerGstinDetails object ::::" + object);
		logger.info("getCustomerGstinDetails end");
		return object;
	}

	@RequestMapping(value = "/ewayBillcalculateTaxAmount", method = RequestMethod.POST)
	public @ResponseBody Object ewayBillcalculateTaxAmount(@RequestHeader HttpHeaders headers,
			@RequestBody EwayBillCalculateTax ewayBillCalculateTax) {
		logger.info("ewayBillcalculateTaxAmount start");
		Object object = null;
		if (EWBWIUtil.isValidRequest(headers) && BaseRequestValidation
				.getBaseResponseObject(BaseRequestValidation.EWAYBILL_CALCULATETAXAMOUNT, ewayBillCalculateTax)) {
			String fromGstin = ewayBillCalculateTax.getFromGstin();
			String toGstin = ewayBillCalculateTax.getToGstin();
			String fromGstinStCode = null;
			String toGstinStCode = null;
			if (!fromGstin.equals("URP")) {
				if (!isValidGstin(ewayBillCalculateTax.getFromGstin()))
					object = getExcObjDesc("INVALID FROM GSTIN " + ewayBillCalculateTax.getFromGstin());
				else
					fromGstinStCode = fromGstin.substring(0, 2);
			} else {
				if (BaseRequestValidation.isValidRequest(ewayBillCalculateTax.getFromStateCode(),
						AspApiConstants.FROM_STATECODE))
					fromGstinStCode = String.valueOf(
							ewayBillCalculateTax.getFromStateCode() < 10 ? "0" + ewayBillCalculateTax.getFromStateCode()
									: ewayBillCalculateTax.getFromStateCode());

			}

			if (!toGstin.equals("URP")) {
				if (!isValidGstin(ewayBillCalculateTax.getToGstin()))
					object = getExcObjDesc("INVALID TO GSTIN " + ewayBillCalculateTax.getToGstin());
				else
					toGstinStCode = toGstin.substring(0, 2);
			} else {
				if (BaseRequestValidation.isValidRequest(ewayBillCalculateTax.getToStateCode(),
						AspApiConstants.TO_STATECODE))
					toGstinStCode = String.valueOf(
							ewayBillCalculateTax.getToStateCode() < 10 ? "0" + ewayBillCalculateTax.getToStateCode()
									: ewayBillCalculateTax.getToStateCode());
			}
			double sgstTotalAmount = 0;
			double cgstTotalAmount = 0;
			double igstTotalAmount = 0;
			double totaltaxAmount = 0;
			double totalcessAmount = 0;
			List<EwayBillItemList> list = new ArrayList<>();
			for (EwayBillItemList ewayBillItemList : ewayBillCalculateTax.getItemList()) {
				HSNDetails HSNDetails = HSNService.getHSNDetailsById(ewayBillItemList.getHsnId());
				if (HSNDetails != null) {
					double cgstRate = HSNDetails.getCgst();
					double igstRate = HSNDetails.getIgst();
					double sgstRate = HSNDetails.getSgstUgst();
					if (fromGstinStCode.equals(toGstinStCode)) {
						ewayBillItemList.setCgstAmount(
								NICUtil.getRoundingMode((cgstRate * ewayBillItemList.getTaxableAmount()) / 100));
						ewayBillItemList.setSgstAmount(
								NICUtil.getRoundingMode((sgstRate * ewayBillItemList.getTaxableAmount()) / 100));
						ewayBillItemList.setIgstAmount(0.0d);
						ewayBillItemList.setCgstRate(cgstRate);
						ewayBillItemList.setSgstRate(sgstRate);
						ewayBillItemList.setIgstRate(0d);

					} else {
						ewayBillItemList.setCgstAmount(0.0d);
						ewayBillItemList.setSgstAmount(0.0d);
						ewayBillItemList.setIgstAmount(
								NICUtil.getRoundingMode((igstRate * ewayBillItemList.getTaxableAmount()) / 100));
						ewayBillItemList.setCgstRate(0.0d);
						ewayBillItemList.setSgstRate(0.0d);
						ewayBillItemList.setIgstRate(igstRate);
					}
					sgstTotalAmount = NICUtil.getRoundingMode(sgstTotalAmount) + ewayBillItemList.getSgstAmount();
					cgstTotalAmount = NICUtil.getRoundingMode(cgstTotalAmount) + ewayBillItemList.getCgstAmount();
					igstTotalAmount = NICUtil.getRoundingMode(igstTotalAmount) + ewayBillItemList.getIgstAmount();
					totaltaxAmount = NICUtil.getRoundingMode(totaltaxAmount)
							+ NICUtil.getRoundingMode(ewayBillItemList.getTaxableAmount());
					totalcessAmount = NICUtil.getRoundingMode(totalcessAmount)
							+ NICUtil.getRoundingMode(ewayBillItemList.getCessAmount());
					list.add(ewayBillItemList);
				} else {
					object = getExcObjDesc("INVALID HSN ID " + ewayBillItemList.getHsnId());
				}

			}
			ewayBillCalculateTax.setItemList(list);
			ewayBillCalculateTax.setSgstTotalAmount(sgstTotalAmount);
			ewayBillCalculateTax.setCgstTotalAmount(cgstTotalAmount);
			ewayBillCalculateTax.setIgstTotalAmount(igstTotalAmount);
			ewayBillCalculateTax.setTotaltaxAmount(NICUtil.getRoundingMode(
					totaltaxAmount + sgstTotalAmount + cgstTotalAmount + igstTotalAmount + totalcessAmount));
			ewayBillCalculateTax.setTotalAmount(totaltaxAmount);
			ewayBillCalculateTax.setTotalcessAmount(totalcessAmount);
			object = ewayBillCalculateTax;
		} else {
			object = NICUtil.getExcObjDesc();
		}
		logger.info("ewayBillcalculateTaxAmount end");
		return object;
	}

	@RequestMapping(value = "/ewayBillcalculateTaxAmountV3", method = RequestMethod.POST)
	public @ResponseBody Object ewayBillcalculateTaxAmountV3(@RequestHeader HttpHeaders headers,
			@RequestBody EwayBillCalculateTax ewayBillCalculateTax) {
		logger.info("ewayBillcalculateTaxAmount start");
		Object object = null;
		if (EWBWIUtil.isValidRequest(headers) && BaseRequestValidation
				.getBaseResponseObject(BaseRequestValidation.EWAYBILL_CALCULATETAXAMOUNTV2, ewayBillCalculateTax)) {
			String fromGstin = ewayBillCalculateTax.getFromGstin();
			String toGstin = ewayBillCalculateTax.getToGstin();
			String fromGstinStCode = null;
			String toGstinStCode = null;
			if (!fromGstin.equals("URP")) {
				if (!isValidGstin(ewayBillCalculateTax.getFromGstin()))
					object = getExcObjDesc("INVALID FROM GSTIN " + ewayBillCalculateTax.getFromGstin());
				else
					fromGstinStCode = fromGstin.substring(0, 2);
			} else {
				if (BaseRequestValidation.isValidRequest(ewayBillCalculateTax.getFromStateCode(),
						AspApiConstants.FROM_STATECODE))
					fromGstinStCode = String.valueOf(
							ewayBillCalculateTax.getFromStateCode() < 10 ? "0" + ewayBillCalculateTax.getFromStateCode()
									: ewayBillCalculateTax.getFromStateCode());

			}

			if (!toGstin.equals("URP")) {
				if (!isValidGstin(ewayBillCalculateTax.getToGstin()))
					object = getExcObjDesc("INVALID TO GSTIN " + ewayBillCalculateTax.getToGstin());
				else
					toGstinStCode = toGstin.substring(0, 2);
			} else {
				if (BaseRequestValidation.isValidRequest(ewayBillCalculateTax.getToStateCode(),
						AspApiConstants.TO_STATECODE))
					toGstinStCode = String.valueOf(
							ewayBillCalculateTax.getToStateCode() < 10 ? "0" + ewayBillCalculateTax.getToStateCode()
									: ewayBillCalculateTax.getToStateCode());
			}
			double sgstTotalAmount = 0;
			double cgstTotalAmount = 0;
			double igstTotalAmount = 0;
			double totaltaxAmount = 0;
			double cessadvolAmount = 0;
			double cessnonadvolAmount = 0;
			List<EwayBillItemList> list = new ArrayList<>();
			for (EwayBillItemList ewayBillItemList : ewayBillCalculateTax.getItemList()) {

				double cgstRate = ewayBillItemList.getCgstsgstRate() > 0 ? ewayBillItemList.getCgstsgstRate() / 2
						: ewayBillItemList.getCgstsgstRate();
				double igstRate = ewayBillItemList.getIgstRate();
				double sgstRate = ewayBillItemList.getCgstsgstRate() > 0 ? ewayBillItemList.getCgstsgstRate() / 2
						: ewayBillItemList.getCgstsgstRate();
				if (fromGstinStCode.equals(toGstinStCode)) {
					ewayBillItemList.setCgstAmount(
							NICUtil.getRoundingMode((cgstRate * ewayBillItemList.getTaxableAmount()) / 100));
					ewayBillItemList.setSgstAmount(
							NICUtil.getRoundingMode((sgstRate * ewayBillItemList.getTaxableAmount()) / 100));
					ewayBillItemList.setIgstAmount(0.0d);
					ewayBillItemList.setCgstRate(cgstRate);
					ewayBillItemList.setSgstRate(sgstRate);
					ewayBillItemList.setIgstRate(0d);

				} else {
					ewayBillItemList.setCgstAmount(0.0d);
					ewayBillItemList.setSgstAmount(0.0d);
					ewayBillItemList.setIgstAmount(
							NICUtil.getRoundingMode((igstRate * ewayBillItemList.getTaxableAmount()) / 100));
					ewayBillItemList.setCgstRate(0.0d);
					ewayBillItemList.setSgstRate(0.0d);
					ewayBillItemList.setIgstRate(igstRate);
				}
				if (ewayBillCalculateTax.getTotalCessadvolAmount() == 0) {
					double cessadvolAmountTemp = NICUtil.getRoundingMode(
							(ewayBillItemList.getCessadvolrate() * ewayBillItemList.getTaxableAmount()) / 100);
					ewayBillItemList.setCessadvolAmount(cessadvolAmountTemp);
					cessadvolAmount = NICUtil.getRoundingMode(cessadvolAmount) + cessadvolAmountTemp;

				}
				if (ewayBillCalculateTax.getTotalCessadvolAmount() == 0) {
					double cessnonadvolAmountTemp = NICUtil.getRoundingMode(ewayBillItemList.getCessnonadvolrate());
					ewayBillItemList.setCessnonadvolAmount(cessnonadvolAmountTemp);
					cessnonadvolAmount = NICUtil.getRoundingMode(cessnonadvolAmount) + cessnonadvolAmountTemp;
				}
				sgstTotalAmount = NICUtil.getRoundingMode(sgstTotalAmount) + ewayBillItemList.getSgstAmount();
				cgstTotalAmount = NICUtil.getRoundingMode(cgstTotalAmount) + ewayBillItemList.getCgstAmount();
				igstTotalAmount = NICUtil.getRoundingMode(igstTotalAmount) + ewayBillItemList.getIgstAmount();
				totaltaxAmount = NICUtil.getRoundingMode(totaltaxAmount)
						+ NICUtil.getRoundingMode(ewayBillItemList.getTaxableAmount());

				list.add(ewayBillItemList);

			}
			ewayBillCalculateTax.setItemList(list);
			ewayBillCalculateTax.setSgstTotalAmount(sgstTotalAmount);
			ewayBillCalculateTax.setCgstTotalAmount(cgstTotalAmount);
			ewayBillCalculateTax.setIgstTotalAmount(igstTotalAmount);
			if (cessadvolAmount == 0 && cessnonadvolAmount == 0) {
				ewayBillCalculateTax.setTotaltaxAmount(NICUtil.getRoundingMode(totaltaxAmount + sgstTotalAmount
						+ cgstTotalAmount + igstTotalAmount + ewayBillCalculateTax.getTotalCessadvolAmount()
						+ ewayBillCalculateTax.getTotalCessnonadvolAmount() + ewayBillCalculateTax.getOtherAmount()));
			} else if (cessadvolAmount > 0 && cessnonadvolAmount > 0) {
				ewayBillCalculateTax.setTotaltaxAmount(
						NICUtil.getRoundingMode(totaltaxAmount + sgstTotalAmount + cgstTotalAmount + igstTotalAmount
								+ cessadvolAmount + cessnonadvolAmount + ewayBillCalculateTax.getOtherAmount()));
				ewayBillCalculateTax.setTotalCessadvolAmount(cessadvolAmount);
				ewayBillCalculateTax.setTotalCessnonadvolAmount(cessnonadvolAmount);
			} else if (cessadvolAmount == 0 && cessnonadvolAmount > 0) {
				ewayBillCalculateTax.setTotaltaxAmount(NICUtil.getRoundingMode(totaltaxAmount + sgstTotalAmount
						+ cgstTotalAmount + igstTotalAmount + ewayBillCalculateTax.getTotalCessadvolAmount()
						+ cessnonadvolAmount + ewayBillCalculateTax.getOtherAmount()));
				ewayBillCalculateTax.setTotalCessnonadvolAmount(cessnonadvolAmount);
			} else if (cessadvolAmount > 0 && cessnonadvolAmount == 0) {
				ewayBillCalculateTax.setTotaltaxAmount(NICUtil.getRoundingMode(totaltaxAmount + sgstTotalAmount
						+ cgstTotalAmount + igstTotalAmount + cessadvolAmount
						+ ewayBillCalculateTax.getTotalCessnonadvolAmount() + ewayBillCalculateTax.getOtherAmount()));
				ewayBillCalculateTax.setTotalCessadvolAmount(cessadvolAmount);
			}
			ewayBillCalculateTax.setTotalAmount(totaltaxAmount);

			object = ewayBillCalculateTax;
		} else {
			object = NICUtil.getExcObjDesc();
		}
		logger.info("ewayBillcalculateTaxAmount end");
		return object;
	}

	@SuppressWarnings("unchecked")
	private Boolean isValidGstin(String gstin) {
		Boolean flag = false;
		String response = gstinValidationService.isValidGstin(gstin);
		Gson gson = new Gson();
		Map<String, Object> responseMap = (Map<String, Object>) gson.fromJson(response.toString(), Map.class);
		String isError = (String) responseMap.get("err_cd");
		String isActive = (String) responseMap.get("sts");
		if (isError != null && !isError.isEmpty()) {
		} else if (!isActive.equalsIgnoreCase("Active")) {
		} else {
			flag = true;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	private Boolean isValidGstin(String gstin, Map<String, Object> map) {
		Boolean flag = false;
		String response = gstinValidationService.isValidGstin(gstin);
		Gson gson = new Gson();
		Map<String, Object> responseMap = (Map<String, Object>) gson.fromJson(response.toString(), Map.class);
		String isError = (String) responseMap.get("err_cd");
		String isActive = (String) responseMap.get("sts");
		if (isError != null && !isError.isEmpty()) {
		} else if (!isActive.equalsIgnoreCase("Active")) {
		} else {
			map.put(gstin, responseMap);
			flag = true;
		}
		return flag;
	}

	@RequestMapping(value = "/wizardGenerateEwayBill", method = RequestMethod.POST)
	public @ResponseBody Object wizardGenerateEwayBill(@RequestHeader HttpHeaders headers,
			@RequestBody EwayBillWITransaction ewayBillWITransaction) {
		logger.info("wizardGenerateEwayBill start");
		Object object = null;
		if (EWBWIUtil.isValidRequest(headers) && BaseRequestValidation
				.getBaseResponseObject(BaseRequestValidation.GENERATE_EWAYBILL, ewayBillWITransaction)) {
			Map<String, Object> headersMap = new LinkedHashMap<>();
			String fromGstin = ewayBillWITransaction.getFromGstin();
			String toGstin = ewayBillWITransaction.getToGstin();
			if (!fromGstin.equals("URP")) {
				if (!isValidGstin(fromGstin, headersMap))
					object = getExcObjDesc("INVALID FROM GSTIN " + fromGstin);
			} else if (BaseRequestValidation.isValidRequest(ewayBillWITransaction.getFromStateCode(),
					AspApiConstants.FROM_STATECODE))
				logger.info("wizardGenerateEwayBill gFromStateCode :::: " + ewayBillWITransaction.getFromStateCode());
			if (!toGstin.equals("URP")) {
				if (!isValidGstin(toGstin, headersMap))
					object = getExcObjDesc("INVALID TO GSTIN " + toGstin);
			} else if (BaseRequestValidation.isValidRequest(ewayBillWITransaction.getToStateCode(),
					AspApiConstants.TO_STATECODE))
				logger.info("wizardGenerateEwayBill FromStateCode :::: " + ewayBillWITransaction.getFromStateCode());

			if (fromGstin.equals("URP") && toGstin.equals("URP"))
				object = getExcObjDesc("INVALID TO GSTIN " + toGstin);
			ewayBillWITransaction.setAppName(headers.get("app_code").get(0));
			ewayBillWITransaction.setHeadersMap(headersMap);
			object = eWayBillService.generateEwaybill(ewayBillWITransaction);
		} else

		{
			object = NICUtil.getExcObjDesc();
		}
		logger.info("wizardGenerateEwayBill end");
		return object;
	}

	@RequestMapping(value = "/wizardGenerateEwayBillV3", method = RequestMethod.POST)
	public @ResponseBody Object wizardGenerateEwayBillV3(@RequestHeader HttpHeaders headers,
			@RequestBody EwayBillWITransaction ewayBillWITransaction) {
		logger.info("wizardGenerateEwayBill start");
		Object object = null;
		if (EWBWIUtil.isValidRequest(headers) && BaseRequestValidation
				.getBaseResponseObject(BaseRequestValidation.GENERATE_EWAYBILL, ewayBillWITransaction)) {
			Map<String, Object> headersMap = new LinkedHashMap<>();
			String fromGstin = ewayBillWITransaction.getFromGstin();
			String toGstin = ewayBillWITransaction.getToGstin();
			if (!fromGstin.equals("URP")) {
				if (!isValidGstin(fromGstin, headersMap))
					object = getExcObjDesc("INVALID FROM GSTIN " + fromGstin);
			} else if (BaseRequestValidation.isValidRequest(ewayBillWITransaction.getFromStateCode(),
					AspApiConstants.FROM_STATECODE))
				logger.info("wizardGenerateEwayBill gFromStateCode :::: " + ewayBillWITransaction.getFromStateCode());
			if (!toGstin.equals("URP")) {
				if (!isValidGstin(toGstin, headersMap))
					object = getExcObjDesc("INVALID TO GSTIN " + toGstin);
			} else if (BaseRequestValidation.isValidRequest(ewayBillWITransaction.getToStateCode(),
					AspApiConstants.TO_STATECODE))
				logger.info("wizardGenerateEwayBill FromStateCode :::: " + ewayBillWITransaction.getFromStateCode());

			if (fromGstin.equals("URP") && toGstin.equals("URP"))
				object = getExcObjDesc("INVALID TO GSTIN " + toGstin);
			ewayBillWITransaction.setAppName(headers.get("app_code").get(0));
			ewayBillWITransaction.setHeadersMap(headersMap);
			object = eWayBillService.generateEwaybillV3(ewayBillWITransaction);
		} else

		{
			object = NICUtil.getExcObjDesc();
		}
		logger.info("wizardGenerateEwayBill end");
		return object;
	}

	@RequestMapping(value = "/getGeneratedEwayBillList", method = RequestMethod.POST)
	public @ResponseBody Object getGeneratedEwayBillList(@RequestHeader HttpHeaders headers,
			@RequestBody Map<String, String> request) {
		Object object = null;
		logger.info("getGeneratedEwayBillList start.");
		if (EWBWIUtil.isValidRequest(headers)
				&& BaseRequestValidation.getBaseResponseObject(BaseRequestValidation.GENERATED_EWAYBILLLIST, request)) {
			request.put(AspApiConstants.APP_CODE, headers.get(AspApiConstants.APPCODE).get(0));
			object = eWayBillService.getGeneratedEwayBillList(request);
		} else {
			object = NICUtil.getExcObjDesc();
		}
		logger.info("getGeneratedEwayBillList end.");
		return object;
	}

	@RequestMapping(value = "/getEwayBillByNumber", method = RequestMethod.POST)
	public @ResponseBody Object getEwayBillByNumber(@RequestHeader HttpHeaders headers,
			@RequestBody Map<String, String> request) {
		Object object = null;
		logger.info("getEwayBillByNumber start.");
		if (EWBWIUtil.isValidRequest(headers)
				&& BaseRequestValidation.getBaseResponseObject(BaseRequestValidation.EWAYBILL_BYNUMBER, request)) {
			request.put(AspApiConstants.APP_CODE, headers.get(AspApiConstants.APPCODE).get(0));
			object = eWayBillService.getEwayBillByNumber(request);
		} else {
			object = NICUtil.getExcObjDesc();
		}
		logger.info("getEwayBillByNumber object." + object);
		logger.info("getEwayBillByNumber end.");
		return object;
	}

	@RequestMapping(value = "/cancelGeneratedEwayBill", method = RequestMethod.POST)
	public @ResponseBody Object cancelGeneratedEwayBill(@RequestHeader HttpHeaders headers,
			@RequestBody Map<String, String> request) {
		logger.info("cancelGeneratedEwayBill start.");
		Object object = null;
		if (EWBWIUtil.isValidRequest(headers)
				&& BaseRequestValidation.getBaseResponseObject(BaseRequestValidation.CANCEL_EWAYBILL, request)) {
			request.put(AspApiConstants.APP_CODE, headers.get(AspApiConstants.APPCODE).get(0));
			object = eWayBillService.cancelGeneratedEwayBill(request);
		} else {
			object = NICUtil.getExcObjDesc();
		}
		return object;
	}

	@RequestMapping(value = "/downloadGeneratedEWayBill", method = { RequestMethod.POST })
	public ResponseEntity<Object> downloadGeneratedEWayBill(@RequestHeader HttpHeaders headers2,
			@RequestBody Map<String, String> request, HttpServletResponse response) {
		ResponseEntity<Object> response2 = null;
		logger.info("downloadEWayBill Entry");
		if (EWBWIUtil.isValidRequestEk(headers2)
				&& BaseRequestValidation.getBaseResponseObject(BaseRequestValidation.EWAYBILL_BYNUMBER, request)) {
			byte[] contents = null;
			try {
				EncryptionUtil ecr = new EncryptionUtil(aesEncryptioney);
				String userId = request.get(AspApiConstants.USERID);
				String ewaybillno = request.get("ewaybillno");
				request.put(AspApiConstants.USERID, ecr.decrypt(userId));
				request.put("ewaybillno", ecr.decrypt(ewaybillno));
				request.put(AspApiConstants.APP_CODE, headers2.get(AspApiConstants.APPCODE).get(0));
				File dir = new File(downloadEWayBillPdfPath);
				if (!dir.exists()) {
					dir.mkdir();
				}
				dir = new File(downloadEWayBillPdfPath);
				if (dir.exists()) {
					String fileName = eWayBillService.downloadGeneratedEWayBill(downloadEWayBillPdfPath + "/", request);
					if (fileName != null) {
						Path file = Paths.get(downloadEWayBillPdfPath, fileName);
						if (Files.exists(file)) {
							contents = Files.readAllBytes(file);
							logger.info("downloadEWayBill Exit");
							HttpHeaders headers = new HttpHeaders();
							headers.setContentType(MediaType.parseMediaType("application/pdf"));
							String filename = "EwayBill.pdf";
							headers.setContentDispositionFormData(filename, filename);
							headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
							response2 = new ResponseEntity<Object>(contents, headers, HttpStatus.OK);
							String deletepath = downloadEWayBillPdfPath + File.separator + fileName;
							dir = new File(deletepath);
							dir.delete();
						}
					}

				} else {
					return new ResponseEntity<Object>(NICUtil.getExcObjDesc(AspApiConstants.INVALID_REQ), null,
							HttpStatus.BAD_REQUEST);
				}

			} catch (Exception e) {
				return new ResponseEntity<Object>(NICUtil.getExcObjDesc(AspApiConstants.INVALID_REQ), null,
						HttpStatus.BAD_REQUEST);
			}

		} else {
			return new ResponseEntity<Object>(NICUtil.getExcObjDesc(AspApiConstants.INVALID_REQ), null,
					HttpStatus.BAD_REQUEST);
		}

		return response2;

	}

	@RequestMapping(value = "/generatedEWayBillDownload", method = { RequestMethod.POST })
	public void downloadGeneratedEWayBill(@ModelAttribute("eWayBill") ExternalWizardEwayBill eWayBill,
			HttpServletResponse response) {
		logger.info("downloadEWayBill Entry");
		Map<String, String> request = new LinkedHashMap<>();
		request.put("userId", eWayBill.getUserId());
		request.put("ewaybillno", eWayBill.getEwaybillno());
		if (EWBWIUtil.isValidRequest(eWayBill)
				&& BaseRequestValidation.getBaseResponseObject(BaseRequestValidation.EWAYBILL_BYNUMBER, request)) {
			logger.info("downloadEWayBillPdfPath " + downloadEWayBillPdfPath);
			request.put("APP_CODE", eWayBill.getApp_code());
			File dir = new File(downloadEWayBillPdfPath);
			if (!dir.exists()) {
				dir.mkdir();
			}
			dir = new File(downloadEWayBillPdfPath);
			if (dir.exists()) {
				String fileName = eWayBillService.downloadGeneratedEWayBill(downloadEWayBillPdfPath + "/", request);
				if (fileName != null) {
					Path file = Paths.get(downloadEWayBillPdfPath, fileName);
					if (Files.exists(file)) {
						response.setContentType("application/pdf");
						response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
						try {
							Files.copy(file, response.getOutputStream());
							response.getOutputStream().flush();
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
				String deletepath = downloadEWayBillPdfPath + File.separator + fileName;
				dir = new File(deletepath);
				dir.delete();

			}
		}
		logger.info("downloadEWayBill Exit");
	}

	@RequestMapping(value = "/sendEwayBillPdfToMail", method = { RequestMethod.POST })
	public @ResponseBody Map sendEwayBillPdf(@RequestHeader HttpHeaders headers2,
			@RequestBody Map<String, String> request, HttpServletResponse response) {
		logger.info("downloadEWayBill Entry");
		if (EWBWIUtil.isValidRequest(headers2)
				&& BaseRequestValidation.getBaseResponseObject(BaseRequestValidation.EWAYBILL_BYNUMBER, request)) {
			request.put(AspApiConstants.APP_CODE, headers2.get(AspApiConstants.APPCODE).get(0));
			File dir = new File(downloadEWayBillPdfPath);
			if (!dir.exists()) {
				dir.mkdir();
			}
			dir = new File(downloadEWayBillPdfPath);
			if (dir.exists()) {
				return eWayBillService.sendEwayBillPdfToCustomer(downloadEWayBillPdfPath + File.separator, request);
			} else {
				Map<String, String> responseMap2 = new LinkedHashMap<>();
				responseMap2.put(AspApiConstants.STATUS_CD, AspApiConstants.ER_CD);
				responseMap2.put(AspApiConstants.STATUS, AspApiConstants.FAILURE);
				responseMap2.put(AspApiConstants.ERROR_DESC, " Failed to send mail please try again later ");
				responseMap2.put(AspApiConstants.ERROR_CODE, AspApiConstants.EWAYBILL_CONNECTION_ERROR);
				return responseMap2;
			}
		} else
			return NICUtil.getExcObjDesc();

	}

	@RequestMapping(value = "/getOperation", method = { RequestMethod.POST })
	public @ResponseBody Object getOperation(@RequestHeader HttpHeaders headers,
			@RequestBody Map<String, String> request, HttpServletResponse response) {
		logger.info("downloadEWayBill Entry");
		if (EWBWIUtil.isValidRequest(headers)) {
			if (request.get("type").equals("S") || request.get("type").equals("U") || request.get("type").equals("D"))
				return eWayBillService.operation(request);
			else
				return "Auth Exception";
		}
		return "Auth Exception";
	}

	@ExceptionHandler({ EwayBillApiException.class, SocketTimeoutException.class, ResourceAccessException.class,
			Exception.class })
	public ResponseEntity<Map<String, Object>> exceptionHandler(HttpServletRequest request, Exception ex) {
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> exObject;
		if (ex instanceof EwayBillApiException) {
			entity = new ResponseEntity<>(getExcObjDesc(((EwayBillApiException) ex).getMessage()), HttpStatus.OK);
		} else if (ex instanceof SocketTimeoutException || ex instanceof ResourceAccessException
				|| ex instanceof Exception) {
			exObject = new HashMap<>();
			exObject.put(AspApiConstants.STATUS_CD, AspApiConstants.ER_CD);
			exObject.put(AspApiConstants.STATUS, AspApiConstants.FAILURE);
			exObject.put(AspApiConstants.ERROR_CODE, AspApiConstants.EWAYBILL_CONNECTION_ERROR);
			exObject.put(AspApiConstants.ERROR_DESC, AspApiConstants.INVALID_REQ);
			exObject.put(AspApiConstants.ERROR_GRP, AspApiConstants.EWAYBILL_CONNECTION_ERROR);
			entity = new ResponseEntity<>(exObject, HttpStatus.BAD_REQUEST);
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
			exObject.put(AspApiConstants.ERROR_DESC, " please try again later ");
		exObject.put(AspApiConstants.ERROR_CODE, AspApiConstants.EWAYBILL_ERROR);
		exObject.put(AspApiConstants.ERROR_GRP, AspApiConstants.EWAYBILL_CONNECTION_ERROR);
		return exObject;
	}

}
