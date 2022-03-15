/**
 * 
 */
package com.reliance.gstn.service.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.admin.exception.EwayBillApiException;
import com.reliance.gstn.admin.model.HSNDetails;
import com.reliance.gstn.admin.service.HSNService;
import com.reliance.gstn.dao.EWayBillDao;
import com.reliance.gstn.model.EWayBill;
import com.reliance.gstn.model.EwayBillRateMaster;
import com.reliance.gstn.model.EwayBillWIItem;
import com.reliance.gstn.model.EwayBillWITransaction;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.InvoiceServiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.NicUserDetails;
import com.reliance.gstn.model.State;
import com.reliance.gstn.model.UnitOfMeasurement;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.EWayBillService;
import com.reliance.gstn.service.GSTINDetailsService;
import com.reliance.gstn.service.GenerateInvoiceService;
import com.reliance.gstn.service.StateService;
import com.reliance.gstn.service.UnitOfMeasurementService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.AESEncryptionV31;
import com.reliance.gstn.util.AspApiConstants;
import com.reliance.gstn.util.EWBWIUtil;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.NICUtil;
import com.reliance.gstn.util.PdfGenUtil;
import com.reliance.gstn.util.PropertyReader;
import com.reliance.gstn.util.SendEmailWithPdf;
import com.reliance.gstn.util.UOMUtil;
import com.reliance.gstn.util.WebserviceCallUtil;

/**
 * @author Nikesh.Bansod
 *
 */
@Service
public class EWayBillServiceImpl implements EWayBillService {
	private static final Logger logger = Logger.getLogger(EWayBillServiceImpl.class);
	@Autowired
	private EWayBillDao eWayBillDao;

	@Autowired
	EWayBillDao ewayBillDao;

	@Value("${${env}.ewbs_secretkey}")
	private String secretkey;

	@Value("${${env}.ewbsclient_id}")
	private String ewbsclientId;

	@Value("${${env}.auth.token.url}")
	private String authTokenUrl;

	@Value("${${env}.ewaybill.key.path}")
	private String ewaybillKeyPath;

	@Value("${${env}.ewaybillsystem.url}")
	private String ewaybillsystem;

	@Value("${${env}.ewaybill.onboarding.url}")
	private String onboardingUrl;

	@Value("${${env}.EMAIL_FROM}")
	private String emailFrom;

	@Value("${${env}.EMAIL_SMTP_HOSTNAME}")
	private String emailSmtpHostName;

	@Value("${app_code}")
	private String appCode;

	@Autowired
	GenerateInvoiceService generateInvoiceService;

	@Autowired
	UserMasterService userMasterService;

	@Autowired
	private GSTINDetailsService gstinDetailsService;

	@Autowired
	public UnitOfMeasurementService unitOfMeasurementService;

	@Autowired
	public HSNService HSNService;

	@Autowired
	public StateService stateService;

	@Override
	public void addEwayBill(EWayBill e) throws Exception {
		logger.info("addEwayBill ::start ");
		eWayBillDao.addEwayBill(e);
		logger.info("addEwayBill ::end ");
	}

	private void addEwayBill(EwayBillWITransaction e) throws Exception {
		logger.info("addEwayBill ::start ");
		eWayBillDao.addEwayBill(e);
		logger.info("addEwayBill ::end ");
	}

	private void save(NicUserDetails nicUserDetails) throws EwayBillApiException {
		logger.info("save ::start ");
		ewayBillDao.save(nicUserDetails);
		logger.info("save ::end ");
	}

	@Override
	@Transactional
	public List<EWayBill> getEwayBillsByInvoiceId(int id) throws Exception {
		return eWayBillDao.getEwayBillsByInvoiceId(id);
	}

	@Override
	@Transactional
	public boolean validateEWayBillAgainstOrgId(Integer invoiceId, Integer orgUId) throws Exception {
		// TODO Auto-generated method stub
		return eWayBillDao.validateEWayBillAgainstOrgId(invoiceId, orgUId);
	}

	@Override
	@Transactional
	public EWayBill getEWayBillDetailsById(Integer id) throws Exception {
		return eWayBillDao.getEWayBillDetailsById(id);
	}

	@Override
	@Transactional
	public Map<String, String> generateEwaybill(EWayBill eWayBill, LoginMaster loginMaster)
			throws EwayBillApiException {
		logger.info("generateEwaybill ::star ");
		Map<String, String> response = new LinkedHashMap<>();
		try {
			InvoiceDetails invoiceDetails = getInvoiceDetailsById(eWayBill, loginMaster);
			if (invoiceDetails != null) {
				UserMaster user = userMasterService.getUserMasterById(loginMaster.getuId());
				if (!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)) {
					user = userMasterService.getUserMasterById(user.getReferenceId());
				}
				GSTINDetails gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(
						invoiceDetails.getGstnStateIdInString(), loginMaster.getPrimaryUserUId());
				if (gstinDetails != null) {
					eWayBill.setRef_org_uId(loginMaster.getOrgUId());
					eWayBill.setGstin(invoiceDetails.getGstnStateIdInString());
					eWayBill.setAuthUserId("" + loginMaster.getOrgUId());
					NicUserDetails nicUserDetails = eWayBillDao.getWizardNICUserDetails(eWayBill);
					Map<String, String> generateResponse = null;
					if (nicUserDetails != null) {
						nicUserDetails = getNICUserDetails(nicUserDetails, eWayBill);// invoiceDetails.getGstnStateIdInString()
						generateResponse = generateAuthToken(nicUserDetails);
					} else {
						nicUserDetails = new NicUserDetails();
						nicUserDetails = getNICUserDetails(nicUserDetails, eWayBill);
						generateResponse = userEWayBillOnBoarding(loginMaster, eWayBill, nicUserDetails);
						validateGenerateAuthToken(generateResponse, eWayBill, nicUserDetails, true);
					}
					if (generateResponse != null) {
						Object object = validateNICUser(generateResponse);
						if (object instanceof Boolean) {
							String appkey = generateResponse.get(AspApiConstants.APP_KEY);
							String authtoken = generateResponse.get(AspApiConstants.AUTH_TOKEN);
							String sek = generateResponse.get(AspApiConstants.SEK);
							byte[] SessionKeyInBytes = AESEncryptionV31.decrypt(sek, appkey.getBytes());
							Map<String, Object> reqmap = buildEwayBillRequest(invoiceDetails, eWayBill, user,
									gstinDetails, loginMaster, nicUserDetails);
							String reqString1 = NICUtil.getRequestString(reqmap);
							String request = new String(
									AESEncryptionV31.encryptEK(reqString1.getBytes(), SessionKeyInBytes));
							nicUserDetails.setData(request);
							nicUserDetails.setAuthToken(authtoken);
							Map<String, String> headersMap = NICUtil.getAuthTokenHeaderMap(nicUserDetails);
							Map<String, String> reqMap = NICUtil.getEwayBillRequest(nicUserDetails);
							String reqString = NICUtil.getRequestString(reqMap);
							Map<String, String> extraParams = new HashMap<String, String>();
							extraParams.put("methodName", "POST");
							logger.info("generateEwaybill :: reqString1 ::" + reqString1);
							String jsonResponseString = WebserviceCallUtil.callWebservice(ewaybillsystem, headersMap,
									reqString, extraParams);
							Map<String, String> responseMap = NICUtil.getResponseStringToObject(jsonResponseString);
							if (responseMap.get(AspApiConstants.STATUS).equals("0")) {
								byte[] decoded = DatatypeConverter
										.parseBase64Binary(responseMap.get(AspApiConstants.ERROR));
								Map<String, String> errorCode = NICUtil
										.getResponseStringToObject(new String(decoded, "UTF-8"));
								return getExcObjDesc(getErrorMsg(errorCode.get("errorCodes")));

							} else {
								Map responseMap2 = NICUtil.getResponseStringToObject(new String(AESEncryptionV31
										.decrypt(responseMap.get(AspApiConstants.JSON_DATA), SessionKeyInBytes)));
								response.put(AspApiConstants.STATUS_CD, AspApiConstants.SU_CD);
								response.put(AspApiConstants.STATUS, AspApiConstants.SUCCESS);
								response.put(AspApiConstants.EWAYBILL_DATE,
										responseMap2.get(AspApiConstants.EWAYBILL_DATE) + "");
								response.put(AspApiConstants.EWAYBILL_NO, formatDecimal(responseMap2) + "");
								response.put(AspApiConstants.EWAYBILL_VALIDUPTO,
										responseMap2.get(AspApiConstants.EWAYBILL_VALIDUPTO) + "");
								eWayBill.setEwaybill_date(responseMap2.get(AspApiConstants.EWAYBILL_DATE) + "");
								eWayBill.setEwaybillNo(formatDecimal(responseMap2) + "");
								eWayBill.setEwaybill_valid_upto(
										responseMap2.get(AspApiConstants.EWAYBILL_VALIDUPTO) + "");
								eWayBill.setEwaybillStatus(AspApiConstants.GENEWAYBILL);
								logger.info("generateEwaybill :: SUCCESS ::" + response);
								logger.info("generateEwaybill :: add ::" + eWayBill);
								eWayBill.setTxn(headersMap.get(AspApiConstants.TXN));

								eWayBill.setGeneratedThrough(loginMaster.getLoggedInThrough()); // added
																								// on
																								// 20/09/2018
																								// to
																								// track
								addEwayBill(eWayBill);
								return response;
							}

						} else {
							logger.info("generateEwaybill :: generateAuthToken ::" + object);
							return (Map) object;
						}
					} else {
						return getExcObjDesc(" Generate Auth Token Error: NIC UserId " + eWayBill.getNic_id());
					}
				} else {
					return getExcObjDesc(" GSTIN is invalid : " + eWayBill.getGstin());

				}
			} else {
				return getExcObjDesc(" Invoice is invalid : " + eWayBill.getInvoiceId());
			}

		} catch (Exception e) {
			logger.error("generateEwaybill", e);
			throw new EwayBillApiException(e.getMessage());
		}

	}

	public Object validateNICUser(Map<String, String> response) throws EwayBillApiException {
		Map<String, String> responseMap = new HashMap<>();
		try {
			if (response != null && response.get(AspApiConstants.ERROR_DESC) == null) {
				return true;
			} else {
				responseMap.put(AspApiConstants.STATUS_CD, AspApiConstants.ER_CD);
				responseMap.put(AspApiConstants.STATUS, AspApiConstants.FAILURE);
				if (response != null)
					responseMap.put(AspApiConstants.ERROR_DESC, response.get(AspApiConstants.ERROR_DESC));
				else
					responseMap.put(AspApiConstants.ERROR_DESC, " NIC AuthToken Error ");
				if (response.get(AspApiConstants.ERROR_CODE) == null) {
					responseMap.put(AspApiConstants.ERROR_CODE, (response.get(AspApiConstants.ERROR_DESC)));
					responseMap.put(AspApiConstants.ERROR_DESC,
							PropertyReader.getProperty(response.get(AspApiConstants.ERROR_DESC)));
				} else
					responseMap.put(AspApiConstants.ERROR_CODE,
							PropertyReader.getProperty(response.get(AspApiConstants.ERROR_CODE)));

			}

		} catch (Exception e) {
			throw new EwayBillApiException(e.getMessage());
		}
		return responseMap;
	}

	private NicUserDetails getNICUserDetails(NicUserDetails nicUserDetails, EWayBill eWayBill) throws Exception {
		nicUserDetails.setUserId(eWayBill.getNic_id());
		logger.info("eWayBillOnBoarding ::NicPwd " + eWayBill.getNicPwd());
		logger.info("eWayBillOnBoarding ::ewaybillKeyPath " + ewaybillKeyPath);
		nicUserDetails.setPassword(AESEncryptionV31.encrypt(eWayBill.getNicPwd().getBytes(), ewaybillKeyPath));
		return nicUserDetails;
	}

	private Map<String, String> generateAuthToken(NicUserDetails nicUserDetails) {
		Map<String, String> responseMap = null;
		try {
			logger.info("generateAuthToken start :::::");
			String requestJson = NICUtil.getAuthTokenRequestString(nicUserDetails);
			logger.info("generateAuthToken RequestJson :::::" + requestJson);
			Map<String, String> headersMap = NICUtil.getAuthTokenHeaderMap(nicUserDetails);
			logger.info("generateAuthToken Headers :::::" + headersMap);
			Map<String, String> extraParams = new HashMap<String, String>();
			extraParams.put("methodName", "POST");
			String jsonResponseString = WebserviceCallUtil.callWebservice(authTokenUrl, headersMap, requestJson,
					extraParams);
			logger.info("generateAuthToken Response :::::" + jsonResponseString);
			responseMap = NICUtil.getResponseStringToObject(jsonResponseString);
		} catch (com.google.gson.JsonSyntaxException e) {
			logger.error("generateAuthToken error :::::", e);
			throw new EwayBillApiException(" ASP API Invalid Response ");
		} catch (Exception e) {
			logger.error("generateAuthToken error :::::", e);
			throw new EwayBillApiException(e.getMessage());
		}
		return responseMap;

	}

	private EWayBill setEWayBillDetails(EWayBill eWayBill) {
		eWayBill.setEwayBillClientId(ewbsclientId);
		eWayBill.setEwayBillSecretKey(secretkey);
		return eWayBill;
	}

	private NicUserDetails getNicUserDetails(Map<String, String> response) {
		NicUserDetails nicUserDetails = new NicUserDetails();
		nicUserDetails.setGstin(response.get(AspApiConstants.GSTIN));
		nicUserDetails.setPassword(response.get(AspApiConstants.PWD));
		nicUserDetails.setUserId(response.get(AspApiConstants.USER_ID));
		return nicUserDetails;
	}

	private InvoiceDetails getInvoiceDetailsById(EWayBill eWayBill, LoginMaster loginMaster) {
		InvoiceDetails invoiceDetails = null;
		try {
			boolean isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(eWayBill.getInvoiceId(),
					loginMaster.getOrgUId());
			if (isInvoiceAllowed) {
				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(eWayBill.getInvoiceId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new EwayBillApiException(e.getMessage());
		}
		return invoiceDetails;
	}

	public Map<String, Object> buildEwayBillRequest(InvoiceDetails invoiceDetails, EWayBill eWayBill, UserMaster user,
			GSTINDetails gstinDetails, LoginMaster loginMaster, NicUserDetails nicUserDetails) throws Exception {
		Map<String, Object> request = new LinkedHashMap<>();
		try {
			request.put(AspApiConstants.SUPPLY_TYPE, "O");
			request.put(AspApiConstants.SUB_SUPPLY_TYPE, "1");
			request.put(AspApiConstants.SUB_SUPPLY_TYPE_DESC, "Supply");
			if (invoiceDetails.getDocumentType().equals("billOfSupply")) {
				request.put(AspApiConstants.DOC_TYPE, "BIL");
			} else if (invoiceDetails.getDocumentType().equals("invoice")) {
				request.put(AspApiConstants.DOC_TYPE, "INV");// invoiceDetails.getDocumentType()
			} else {
				request.put(AspApiConstants.DOC_TYPE, "OTH");
			}

			request.put(AspApiConstants.DOC_NO, invoiceDetails.getInvoiceNumber());
			request.put(AspApiConstants.DOC_DATE, getStringDate(invoiceDetails.getInvoiceDate()));
			request.put(AspApiConstants.FROM_GSTIN, invoiceDetails.getGstnStateIdInString());// invoiceDetails.getGstnStateIdInString()
			request.put(AspApiConstants.FROM_TRNAME, user.getOrganizationMaster().getOrgName());
			request.put(AspApiConstants.FROM_ADD, gstinDetails.getGstinAddressMapping().getAddress());
			request.put(AspApiConstants.FROM_ADD2, "");
			request.put(AspApiConstants.FROM_PLACE, gstinDetails.getGstinAddressMapping().getCity());
			request.put(AspApiConstants.FROM_PINCODE, gstinDetails.getGstinAddressMapping().getPinCode());
			request.put(AspApiConstants.ACTFROM_STATECODE, invoiceDetails.getGstnStateId());
			request.put(AspApiConstants.FROM_STATECODE, invoiceDetails.getGstnStateId());
			State state = null;
			if (invoiceDetails.getCustomerDetails().getCustGstId().equals("")) {
				request.put(AspApiConstants.TO_GSTIN, "URP");
				state = stateService.getStateByStateName(invoiceDetails.getCustomerDetails().getCustState());
			} else
				request.put(AspApiConstants.TO_GSTIN, invoiceDetails.getCustomerDetails().getCustGstId());

			if (invoiceDetails.getBillToShipIsChecked().equals("Yes")) {
				request.put(AspApiConstants.TO_TRNAME, invoiceDetails.getCustomerDetails().getCustName());
				request.put(AspApiConstants.TO_ADD, invoiceDetails.getCustomerDetails().getCustAddress1());
				request.put(AspApiConstants.TO_ADD2, "");
				request.put(AspApiConstants.TO_PLACE, invoiceDetails.getCustomerDetails().getCustCity());
				request.put(AspApiConstants.TO_PINCODE, invoiceDetails.getCustomerDetails().getPinCode());
				if (invoiceDetails.getCustomerDetails().getCustGstId().equals("")) {
					request.put(AspApiConstants.ACTTO_STATECODE, state.getStateId());
					request.put(AspApiConstants.TO_STATECODE, state.getStateId());
				} else {
					request.put(AspApiConstants.ACTTO_STATECODE,
							invoiceDetails.getCustomerDetails().getCustGstinState());
					request.put(AspApiConstants.TO_STATECODE, invoiceDetails.getCustomerDetails().getCustGstinState());
				}

			} else {
				request.put(AspApiConstants.TO_TRNAME, invoiceDetails.getShipToCustomerName());
				request.put(AspApiConstants.TO_ADD, invoiceDetails.getShipToAddress());
				request.put(AspApiConstants.TO_ADD2, "");
				request.put(AspApiConstants.TO_PLACE, invoiceDetails.getShipToCity());
				request.put(AspApiConstants.TO_PINCODE, invoiceDetails.getShipToPincode());

				request.put(AspApiConstants.ACTTO_STATECODE, invoiceDetails.getShipToStateCodeId());
				if (invoiceDetails.getCustomerDetails().getCustGstId().equals(""))
					if (state != null)
						request.put(AspApiConstants.TO_STATECODE, state.getStateId());
					else
						request.put(AspApiConstants.TO_STATECODE,
								invoiceDetails.getCustomerDetails().getCustGstinState());
			}

			request.put(AspApiConstants.TRANSACTIONTYPE, 1);
			request.put(AspApiConstants.DISPATCHFROM_GSTIN, "");
			request.put(AspApiConstants.DISPATCHFROM_TRADENAME, "");
			request.put(AspApiConstants.SHIPTOGSTIN, "");
			request.put(AspApiConstants.SHIPTO_TRADENAME, "");
			request.put(AspApiConstants.OTHERVALUE, 0);

			double cgstAmount = 0;
			double sgstAmount = 0;
			double igstAmount = 0;
			double cess = 0;
			double totalTaxAmt = 0;
			List<Map<String, Object>> itemList = new ArrayList<>();
			for (InvoiceServiceDetails invoiceServiceDetails : invoiceDetails.getServiceList()) {
				Map<String, Object> serviceRequestMap = new LinkedHashMap<>();
				serviceRequestMap.put(AspApiConstants.PRODUCT_NAME, invoiceServiceDetails.getServiceIdInString());
				serviceRequestMap.put(AspApiConstants.PRODUCT_DESC, invoiceServiceDetails.getServiceIdInString());
				serviceRequestMap.put(AspApiConstants.HSN_CODE,
						Integer.parseInt(invoiceServiceDetails.getHsnSacCode()));
				serviceRequestMap.put(AspApiConstants.QUANTITY, invoiceServiceDetails.getQuantity());
				serviceRequestMap.put(AspApiConstants.QTY_UNIT,
						getUnitOfMeasurement(invoiceServiceDetails.getUnitOfMeasurement()));
				cgstAmount = cgstAmount + invoiceServiceDetails.getCgstAmount();
				sgstAmount = sgstAmount + invoiceServiceDetails.getSgstAmount();
				igstAmount = igstAmount + invoiceServiceDetails.getIgstAmount();
				cess = cess + invoiceServiceDetails.getCess();
				serviceRequestMap.put(AspApiConstants.CGST_RATE, invoiceServiceDetails.getCgstPercentage());
				serviceRequestMap.put(AspApiConstants.SGST_RATE, invoiceServiceDetails.getSgstPercentage());
				serviceRequestMap.put(AspApiConstants.IGST_RATE, invoiceServiceDetails.getIgstPercentage());
				serviceRequestMap.put(AspApiConstants.CESS_RATE, 0);
				// serviceRequestMap.put(AspApiConstants.CESS_ADVOL, 0);
				serviceRequestMap.put(AspApiConstants.CESS_NONADVOL, 0);
				serviceRequestMap.put(AspApiConstants.TAXABLE_AMOUNT, (invoiceServiceDetails.getPreviousAmount()
						- invoiceServiceDetails.getOfferAmount() + invoiceServiceDetails.getAdditionalAmount()));
				totalTaxAmt = totalTaxAmt + (invoiceServiceDetails.getPreviousAmount()
						- invoiceServiceDetails.getOfferAmount() + invoiceServiceDetails.getAdditionalAmount());
				itemList.add(serviceRequestMap);
			}
			request.put(AspApiConstants.CGST_VALUE, cgstAmount);
			request.put(AspApiConstants.SGST_VALUE, sgstAmount);
			request.put(AspApiConstants.IGST_VALUE, igstAmount);
			request.put(AspApiConstants.CESS_VALUE, cess);
			request.put(AspApiConstants.TOTAL_VALUE, totalTaxAmt);
			request.put(AspApiConstants.TOTAL_INVVALUE, invoiceDetails.getInvoiceValueAfterRoundOff());
			request.put(AspApiConstants.CESS_NONADVOLVALUE, 0);
			request.putAll(getEwayBillRequest(eWayBill));
			if (eWayBill.getVehicleType().equals("overDimensionalCargo"))
				request.put(AspApiConstants.VEHICLE_TYPE, "O");
			else
				request.put(AspApiConstants.VEHICLE_TYPE, "R");
			request.put(AspApiConstants.ITEM_LIST, itemList);
			request.remove(AspApiConstants.INVOICE_ID);
			eWayBill.setGstin(invoiceDetails.getGstnStateIdInString());
			eWayBill.setRef_org_uId(loginMaster.getOrgUId());
			eWayBill.setSupplyType("Outward");
			eWayBill.setSubSupplyType("Supply");
			if (invoiceDetails.getDocumentType().equals("billOfSupply"))
				eWayBill.setDocType("Bill of Supply");
			else if (invoiceDetails.getDocumentType().equals("invoice"))
				eWayBill.setDocType("Tax Invoice");
			else
				eWayBill.setDocType("Others");
			eWayBill.setModeOfTransportDesc(getMode(eWayBill.getModeOfTransport()));
			eWayBill.setDocDate(new Timestamp(NICUtil.getDate(eWayBill.getDocDateInString()).getTime()));
		} catch (Exception e) {
			throw new EwayBillApiException(e.getMessage());
		}
		return request;
	}

	public String getErrorMsg(String errorCode) {
		StringBuffer bf = new StringBuffer();
		try {
			if (errorCode != null) {
				String[] errorCodeA = errorCode.split(",");
				if (errorCodeA != null)
					for (String code : errorCodeA) {
						if (code != null)
							bf.append(PropertyReader.getProperty(code) + " ");
					}

			}
		} catch (Exception e) {
			bf.append(PropertyReader.getProperty("365"));
		}
		return bf.toString();
	}

	public String getStringDate(Date date1) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(date1);
		return date;
	}

	public String getStringDate2(String date) {
		try {
			Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
			return getStringDate(date1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	private Map<String, String> getEwayBillRequest(EWayBill eWayBill) {
		Map<String, String> request = new LinkedHashMap<>();
		request.put(AspApiConstants.TRANSPORTER_ID, eWayBill.getTransporterId());
		request.put(AspApiConstants.TRANSPORTER_NAME, "Rail");
		request.put(AspApiConstants.TRANS_DOC_NO, eWayBill.getDocNo());
		request.put(AspApiConstants.TRANS_MODE, eWayBill.getModeOfTransport() + "");
		Double d = eWayBill.getKmsToBeTravelled();
		request.put(AspApiConstants.TRANSDISTANCE, d.intValue() + "");
		request.put(AspApiConstants.TRANSDOCDATE, NICUtil.getStringDate(eWayBill.getDocDateInString()));
		request.put(AspApiConstants.VEHICLE_NO, eWayBill.getVehicleNo());
		request.put(AspApiConstants.INVOICE_ID, eWayBill.getInvoiceId() + "");
		return request;
	}

	private String getUnitOfMeasurement(String key) {
		try {
			if (!UOMUtil.isMapLoaded()) {
				List<UnitOfMeasurement> unitOfMeasurementList = (List<UnitOfMeasurement>) unitOfMeasurementService
						.getUnitOfMeasurement();
				UOMUtil.setUOMMap(unitOfMeasurementList);
			}
			return UOMUtil.getUomValue(key);
		} catch (Exception e) {

		}
		return null;

	}

	private String getMode(Integer mode) {
		String modeDesc = null;
		if (mode == 1)
			modeDesc = "Road";
		else if (mode == 2)
			modeDesc = "Rail";
		else if (mode == 3)
			modeDesc = "Air";
		else if (mode == 4)
			modeDesc = "Ship";
		return modeDesc;
	}

	public static String formatDecimal(Map responseMap2) {
		Object ewaybillNo = responseMap2.get(AspApiConstants.EWAYBILL_NO);
		DecimalFormat df = new DecimalFormat("###.##");
		String val = df.format(ewaybillNo);
		return val;
	}

	@Override
	@Transactional
	public String downloadEWayBill(String dataDirectory, EWayBill eWayBill, LoginMaster loginMaster)
			throws EwayBillApiException {
		String fileName = null;
		try {
			InvoiceDetails invoiceDetails = getInvoiceDetailsById(eWayBill, loginMaster);
			if (invoiceDetails != null) {
				UserMaster user = userMasterService.getUserMasterById(loginMaster.getuId());
				if (!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)) {
					user = userMasterService.getUserMasterById(user.getReferenceId());
				}
				GSTINDetails gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(
						invoiceDetails.getGstnStateIdInString(), loginMaster.getPrimaryUserUId());
				if (gstinDetails != null) {
					eWayBill.setRef_org_uId(loginMaster.getOrgUId());
					eWayBill = eWayBillDao.getEwayDetails(eWayBill);
					if (eWayBill != null) {
						PdfGenUtil pdfGenUtil = new PdfGenUtil();
						fileName = pdfGenUtil.generatePdfFile(dataDirectory, invoiceDetails, eWayBill, gstinDetails);
						logger.info("downloadEWayBill ::fileName " + fileName);
					}
				} else {
					logger.error("downloadEWayBill ::GSTINDetails Error ");
				}
			} else {
				logger.error("downloadEWayBill ::InvoiceDetails Error ");
			}
		} catch (Exception e) {
			logger.error("downloadEWayBill error :: " + e);
			throw new EwayBillApiException(e.getMessage());
		}
		return fileName;
	}

	@Override
	@Transactional
	public Map<String, String> eWayBillOnBoarding(EWayBill eWayBill, LoginMaster loginMaster)
			throws EwayBillApiException {
		logger.info("eWayBillOnBoarding ::start ");
		NicUserDetails nicUserDetails = null;
		try {
			InvoiceDetails invoiceDetails = getInvoiceDetailsById(eWayBill, loginMaster);
			Map<String, String> generateResponse = null;
			boolean onbording = false;
			if (invoiceDetails != null) {
				eWayBill.setRef_org_uId(loginMaster.getOrgUId());
				eWayBill.setGstin(invoiceDetails.getGstnStateIdInString());
				eWayBill.setAuthUserId("" + loginMaster.getOrgUId());
				nicUserDetails = eWayBillDao.getWizardNICUserDetails(eWayBill);
				if (nicUserDetails != null) {
					nicUserDetails = getNICUserDetails(nicUserDetails, eWayBill);
					generateResponse = generateAuthToken(nicUserDetails);
				} else {
					nicUserDetails = new NicUserDetails();
					nicUserDetails = getNICUserDetails(nicUserDetails, eWayBill);
					generateResponse = userEWayBillOnBoarding(loginMaster, eWayBill, nicUserDetails);
					onbording = true;
				}
				return validateGenerateAuthToken(generateResponse, eWayBill, nicUserDetails, onbording);
			} else {
				return getExcObjDesc(" Invoice is invalid : " + eWayBill.getInvoiceId());
			}

		} catch (Exception e) {
			logger.error("Error in eWayBillOnBoarding  ", e);
			throw new EwayBillApiException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public Map<String, String> eWayBillOnBoarding(Map<String, Object> responseMap,
			Map<String, String> customerbonboarding) throws EwayBillApiException {
		logger.info("eWayBillOnBoarding ::start ");
		try {
			boolean onbording = false;
			EWayBill eWayBill = new EWayBill();
			eWayBill.setGstin(customerbonboarding.get(AspApiConstants.GSTIN));
			eWayBill.setNic_id(customerbonboarding.get(AspApiConstants.NIC_USER_ID));
			eWayBill.setNicPwd(customerbonboarding.get(AspApiConstants.PWD));
			eWayBill.setAuthUserId(customerbonboarding.get(AspApiConstants.USERID));

			logger.info("eWayBillOnBoarding ::GSTIN " + customerbonboarding.get(AspApiConstants.GSTIN));
			logger.info("eWayBillOnBoarding ::NIC_USER_ID " + customerbonboarding.get(AspApiConstants.NIC_USER_ID));
			logger.info("eWayBillOnBoarding ::PWD " + customerbonboarding.get(AspApiConstants.PWD));
			logger.info("eWayBillOnBoarding ::USERID " + customerbonboarding.get(AspApiConstants.USERID));
			NicUserDetails nicUserDetails = eWayBillDao.getWizardNICUserDetails(eWayBill);
			Map<String, String> generateResponse = null;
			if (nicUserDetails != null) {
				logger.info("eWayBillOnBoarding ::USERID " + nicUserDetails.getUserId());
				nicUserDetails = getNICUserDetails(nicUserDetails, eWayBill);
				generateResponse = generateAuthToken(nicUserDetails);
			} else {
				nicUserDetails = new NicUserDetails();
				nicUserDetails.setAuthUserId(customerbonboarding.get(AspApiConstants.USERID));
				nicUserDetails.setAppCode(customerbonboarding.get(AspApiConstants.APP_CODE));
				nicUserDetails = getNICUserDetails(nicUserDetails, eWayBill);
				generateResponse = userEWayBillOnBoarding(responseMap, customerbonboarding, eWayBill, nicUserDetails);
				onbording = true;
			}
			return validateGenerateAuthToken(generateResponse, eWayBill, nicUserDetails, onbording);
		} catch (Exception e) {
			logger.error("Error in eWayBillOnBoarding  ", e);
			throw new EwayBillApiException(e.getMessage());
		}

	}

	public static Map<String, Object> callWebservice(String webServiceUrl, Map<String, String> headersMap,
			String inputData) {
		logger.info("callWebservice :: URL " + webServiceUrl);
		logger.info("callWebservice :: Headers " + headersMap);
		logger.info("callWebservice :: Request " + inputData);
		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		String jsonResponseString = WebserviceCallUtil.callWebservice(webServiceUrl, headersMap, inputData,
				extraParams);
		Map<String, Object> responseMap = NICUtil.getResponseStringToObject(jsonResponseString);
		logger.info("callWebservice :: jsonResponseString  " + jsonResponseString);
		return responseMap;

	}

	@Override
	@Transactional
	public NicUserDetails getNicDetailsFromGstinAndOrgId(String gstnStateIdInString, Integer orgUId) throws Exception {
		return eWayBillDao.getNicDetailsFromGstinAndOrgId(gstnStateIdInString, orgUId);
	}

	@Override
	@Transactional
	public Map<String, String> cancelEWayBill(EWayBill eWayBill, LoginMaster loginMaster) throws EwayBillApiException {
		logger.info("cancelEWayBill ::start ");
		NicUserDetails nicUserDetails = null;
		Map<String, String> response = new LinkedHashMap<>();
		try {
			eWayBill.setRef_org_uId(loginMaster.getOrgUId());
			eWayBill.setAuthUserId("" + loginMaster.getOrgUId());
			EWayBill eWayBill2 = eWayBillDao.getEwayDetails(eWayBill);
			Map<String, String> generateResponse = null;
			if (eWayBill2 != null) {
				if (eWayBill2.getEwaybillStatus().equals(AspApiConstants.CAN_EWAYBILL)) {
					return getExcObjDesc(getErrorMsg("312") + eWayBill.getEwaybillNo());
				} else {
					nicUserDetails = eWayBillDao.getWizardNICUserDetails(eWayBill);
					if (nicUserDetails != null) {
						nicUserDetails = getNICUserDetails(nicUserDetails, eWayBill);
						generateResponse = generateAuthToken(nicUserDetails);
					} else {
						nicUserDetails = new NicUserDetails();
						nicUserDetails = getNICUserDetails(nicUserDetails, eWayBill);
						generateResponse = userEWayBillOnBoarding(loginMaster, eWayBill, nicUserDetails);
						validateGenerateAuthToken(generateResponse, eWayBill, nicUserDetails, true);
					}
					if (generateResponse != null) {
						Object object = validateNICUser(generateResponse);
						if (object instanceof Boolean) {
							String appkey = generateResponse.get(AspApiConstants.APP_KEY);
							String authtoken = generateResponse.get(AspApiConstants.AUTH_TOKEN);
							String sek = generateResponse.get(AspApiConstants.SEK);
							byte[] SessionKeyInBytes = AESEncryptionV31.decrypt(sek, appkey.getBytes());
							Map<String, Object> cancelEWayBillRequest = NICUtil.getCancelEWayBillRequest(eWayBill);
							String reqString = NICUtil.getRequestString(cancelEWayBillRequest);
							String request = new String(
									AESEncryptionV31.encryptEK(reqString.getBytes(), SessionKeyInBytes));
							nicUserDetails.setData(request);
							nicUserDetails.setAuthToken(authtoken);
							Map<String, String> req = NICUtil.getCancelEwayBillRequest(nicUserDetails);
							String reqString1 = NICUtil.getRequestString(req);
							Map<String, String> headersMap = NICUtil.getAuthTokenHeaderMap(nicUserDetails);
							Map<String, String> extraParams = new HashMap<String, String>();
							extraParams.put("methodName", "POST");
							String jsonResponseString = WebserviceCallUtil.callWebservice(ewaybillsystem, headersMap,
									reqString1, extraParams);
							Map<String, String> responseMap = NICUtil.getResponseStringToObject(jsonResponseString);
							if (responseMap.get(AspApiConstants.STATUS).equals("0")) {
								byte[] decoded = DatatypeConverter
										.parseBase64Binary(responseMap.get(AspApiConstants.ERROR));
								Map<String, String> errorCode = NICUtil
										.getResponseStringToObject(new String(decoded, "UTF-8"));
								logger.info("cancelEWayBill :: " + AspApiConstants.FAILURE + " :" + response);
								return getExcObjDesc(
										getErrorMsg(errorCode.get("errorCodes")) + " : " + eWayBill.getEwaybillNo());

							} else {
								Map responseMap3 = NICUtil.getResponseStringToObject(new String(AESEncryptionV31
										.decrypt(responseMap.get(AspApiConstants.JSON_DATA), SessionKeyInBytes)));
								response.put(AspApiConstants.STATUS_CD, AspApiConstants.SU_CD);
								response.put(AspApiConstants.STATUS, AspApiConstants.SUCCESS);
								response.put(AspApiConstants.EWAYBILL_NO,
										responseMap3.get(AspApiConstants.EWAYBILL_NO) + "");
								response.put(AspApiConstants.EWAYBILL_CANCEL_DATE,
										responseMap3.get(AspApiConstants.EWAYBILL_CANCEL_DATE) + "");

								eWayBill2.setCanceldate(responseMap3.get(AspApiConstants.EWAYBILL_CANCEL_DATE) + "");
								eWayBill2.setEwaybillStatus(AspApiConstants.CAN_EWAYBILL);
								eWayBill2.setCancelRmrk(eWayBill.getCancelRmrk());
								addEwayBill(eWayBill2);
								logger.info("cancelEWayBill :: " + AspApiConstants.SUCCESS + " :" + response);
								return response;
							}

						} else {
							return (Map) object;
						}
					} else {
						return getExcObjDesc(" Generate Auth Token Error: NIC UserId " + eWayBill.getNic_id());
					}

				}

			} else {
				return getExcObjDesc(PropertyReader.getProperty("301") + " : " + eWayBill.getEwaybillNo());
			}

		} catch (Exception e) {
			logger.error("generateEwaybill error :: " + e);
			throw new EwayBillApiException(e.getMessage());
		}

	}

	public Map<String, String> getExcObjDesc(String message) {
		Map<String, String> exObject = new LinkedHashMap<>();
		exObject.put(AspApiConstants.STATUS_CD, AspApiConstants.ER_CD);
		exObject.put(AspApiConstants.STATUS, AspApiConstants.FAILURE);
		exObject.put(AspApiConstants.ERROR_DESC, message);
		exObject.put(AspApiConstants.ERROR_CODE, AspApiConstants.EWAYBILL_ERROR);
		logger.info("generateEwaybill error :: " + exObject);
		return exObject;
	}

	private Map<String, String> userEWayBillOnBoarding(LoginMaster loginMaster, EWayBill eWayBill,
			NicUserDetails nicUserDetails) throws Exception {
		UserMaster user = userMasterService.getUserMasterById(loginMaster.getuId());
		Map<String, String> headersMap = NICUtil.getEWayBillOnBoardingHeaderMap(setEWayBillDetails(eWayBill));
		Map<String, Object> reqMap = NICUtil.getEWayBillOnBoardingRequest(user, eWayBill);
		String reqString = NICUtil.getRequestString(reqMap);
		Map<String, Object> responseMap = callWebservice(onboardingUrl, headersMap, reqString);
		Object objectList = responseMap.get(AspApiConstants.CUSTOMERS);
		if (objectList != null && objectList instanceof List) {
			Map<String, String> response = (Map) ((List) objectList).get(0);
			nicUserDetails.setEwayBillClientId(response.get(AspApiConstants.EWB_CLIENT_ID));
			nicUserDetails.setEwayBillSecretKey(response.get(AspApiConstants.EWB_SEC_KEY));
			nicUserDetails.setGstin(eWayBill.getGstin());
			nicUserDetails.setRef_org_uId(loginMaster.getOrgUId());
			nicUserDetails = getNICUserDetails(nicUserDetails, eWayBill);
			Map<String, String> generateResponse = generateAuthToken(nicUserDetails);
			return generateResponse;
		} else {
			return null;
		}
	}

	private Map<String, String> userEWayBillOnBoarding(Map<String, Object> res, Map<String, String> customerbonboarding,
			EWayBill eWayBill, NicUserDetails nicUserDetails) throws Exception {
		logger.info("userEWayBillOnBoarding :: ::: Start");
		Map<String, String> headersMap = NICUtil.getEWayBillOnBoardingHeaderMap(setEWayBillDetails(eWayBill));
		Map<String, Object> reqMap = NICUtil.getEWayBillOnBoardingRequest(res, customerbonboarding);
		String reqString = NICUtil.getRequestString(reqMap);
		logger.info("userEWayBillOnBoarding :: ::: URL ::::" + onboardingUrl);
		logger.info("userEWayBillOnBoarding :: ::: headersMap ::::" + headersMap);
		logger.info("userEWayBillOnBoarding :: ::: reqString ::::" + reqString);
		Map<String, Object> responseMap = callWebservice(onboardingUrl, headersMap, reqString);
		logger.info("userEWayBillOnBoarding :: ::: responseMap ::::" + responseMap);
		Object objectList = responseMap.get(AspApiConstants.CUSTOMERS);
		if (objectList != null && objectList instanceof List) {
			Map<String, String> response = (Map) ((List) objectList).get(0);
			nicUserDetails.setEwayBillClientId(response.get(AspApiConstants.EWB_CLIENT_ID));
			nicUserDetails.setEwayBillSecretKey(response.get(AspApiConstants.EWB_SEC_KEY));
			nicUserDetails.setGstin(eWayBill.getGstin());
			nicUserDetails = getNICUserDetails(nicUserDetails, eWayBill);
			Map<String, String> generateResponse = generateAuthToken(nicUserDetails);
			logger.info("userEWayBillOnBoarding :: ::: end");
			return generateResponse;
		} else {
			return null;
		}
	}

	private Map<String, String> validateGenerateAuthToken(Map<String, String> generateResponse, EWayBill eWayBill,
			NicUserDetails nicUserDetails, boolean onbording) {
		Map<String, String> responseMap2 = new LinkedHashMap<>();
		if (generateResponse != null) {
			Object object = validateNICUser(generateResponse);
			if (object instanceof Boolean) {
				logger.info("eWayBillOnBoarding :: ::: " + object);
				// eWayBill.getIsCheckNicUserId() != null &&
				// eWayBill.getIsCheckNicUserId().equals("Yes") &&
				if (onbording) {
					nicUserDetails.setAuthUserId(eWayBill.getAuthUserId());
					nicUserDetails.setAppCode(appCode);
					save(nicUserDetails);
				}
				responseMap2.put(AspApiConstants.STATUS_CD, AspApiConstants.SU_CD);
				responseMap2.put(AspApiConstants.STATUS, AspApiConstants.SUCCESS);
				responseMap2.put(AspApiConstants.ERROR_DESC, "");
				responseMap2.put(AspApiConstants.ERROR_CODE, "");
				responseMap2.put(AspApiConstants.NIC_USER_ID, eWayBill.getNic_id());
				logger.info("eWayBillOnBoarding :: error::: " + responseMap2);
				return responseMap2;
			} else {
				logger.info("eWayBillOnBoarding :: error::: " + object);
				return (Map) object;
			}
		} else {
			return getExcObjDesc(" Generate Auth Token Error: NIC UserId " + eWayBill.getNic_id());
		}
	}

	@Override
	public Map<String, String> sendEwayBillPdfToCustomer(String dataDirectory, EWayBill eWayBill,
			LoginMaster loginMaster) throws EwayBillApiException {
		String fileName = null;
		Map<String, String> responseMap2 = new LinkedHashMap<>();
		boolean flag = false;
		try {
			InvoiceDetails invoiceDetails = getInvoiceDetailsById(eWayBill, loginMaster);
			if (invoiceDetails != null) {
				UserMaster user = userMasterService.getUserMasterById(loginMaster.getuId());
				if (!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)) {
					user = userMasterService.getUserMasterById(user.getReferenceId());
				}
				GSTINDetails gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(
						invoiceDetails.getGstnStateIdInString(), loginMaster.getPrimaryUserUId());
				if (gstinDetails != null) {
					eWayBill.setRef_org_uId(loginMaster.getOrgUId());
					eWayBill = eWayBillDao.getEwayDetails(eWayBill);
					if (eWayBill != null) {
						PdfGenUtil pdfGenUtil = new PdfGenUtil();
						fileName = pdfGenUtil.generatePdfFile(dataDirectory, invoiceDetails, eWayBill, gstinDetails);
						String response = sendEwayBillPdfToCustomer(emailFrom, invoiceDetails.getCustomerEmail(),
								user.getDefaultEmailId(), "sandeep.jalagam@ril.com",
								" Eway Bill Document" + " from " + user.getOrganizationMaster().getOrgName() + " to "
										+ invoiceDetails.getCustomerDetails().getCustName() + " - "
										+ invoiceDetails.getInvoiceNumber(),
								emailSmtpHostName, dataDirectory + fileName, fileName);

						if (response.equals(GSTNConstants.SUCCESS)) {
							responseMap2.put(AspApiConstants.STATUS_CD, AspApiConstants.SU_CD);
							responseMap2.put(AspApiConstants.STATUS, AspApiConstants.SUCCESS);
							responseMap2.put(AspApiConstants.ERROR_DESC, "");
							responseMap2.put(AspApiConstants.ERROR_CODE, "");
							responseMap2.put(AspApiConstants.NIC_USER_ID, eWayBill.getNic_id());
						} else {
							flag = true;
						}

						logger.info("downloadEWayBill ::fileName " + fileName);
					}
				} else {
					logger.error("downloadEWayBill ::GSTINDetails Error ");
					flag = true;
				}
			} else {
				logger.error("downloadEWayBill ::InvoiceDetails Error ");
				flag = true;
			}
			if (flag) {
				responseMap2.put(AspApiConstants.STATUS_CD, AspApiConstants.ER_CD);
				responseMap2.put(AspApiConstants.STATUS, AspApiConstants.FAILURE);
				responseMap2.put(AspApiConstants.ERROR_DESC, " Failed to send mail ");
				responseMap2.put(AspApiConstants.ERROR_CODE, "");
				responseMap2.put(AspApiConstants.NIC_USER_ID, eWayBill.getNic_id());
			}
		} catch (Exception e) {
			logger.error("downloadEWayBill error :: " + e);
			throw new EwayBillApiException(e.getMessage());
		}
		return responseMap2;
	}

	private String sendEwayBillPdfToCustomer(String from, String to, String cc, String bcc, String subject,
			String hostName, String filePath, String fileName) {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			SendEmailWithPdf.sendMail(from, to, cc, bcc, subject, hostName, filePath, fileName);
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Failed to send mail from :" + from + " to " + to + "having fileName : " + filePath, e);
		}
		logger.info("Exit");
		return response;
	}

	@Override
	@Transactional
	public Map<String, String> generateEwaybill(EwayBillWITransaction ewayBillWITransaction)
			throws EwayBillApiException {
		Map<String, String> response = new LinkedHashMap<>();
		logger.info("generateEwaybill start");
		try {
			EWayBill eWayBill = new EWayBill();
			if (ewayBillWITransaction.getSupplyType().equalsIgnoreCase("I"))
				eWayBill.setGstin(ewayBillWITransaction.getToGstin());
			else
				eWayBill.setGstin(ewayBillWITransaction.getFromGstin());
			/*
			 * else if (!ewayBillWITransaction.getFromGstin().equals("URP"))
			 * eWayBill.setGstin(ewayBillWITransaction.getFromGstin()); else
			 * eWayBill.setGstin(ewayBillWITransaction.getToGstin());
			 */eWayBill.setNic_id(ewayBillWITransaction.getNicId());
			eWayBill.setNicPwd(ewayBillWITransaction.getNicPassword());
			eWayBill.setAuthUserId(ewayBillWITransaction.getUserId());
			NicUserDetails nicUserDetails = eWayBillDao.getWizardNICUserDetails(eWayBill);
			Map<String, String> generateResponse = null;
			if (nicUserDetails != null) {
				nicUserDetails = getNICUserDetails(nicUserDetails, eWayBill);
				generateResponse = generateAuthToken(nicUserDetails);
				if (generateResponse != null) {
					Object object = validateNICUser(generateResponse);
					if (object instanceof Boolean) {
						String appkey = generateResponse.get(AspApiConstants.APP_KEY);
						String authtoken = generateResponse.get(AspApiConstants.AUTH_TOKEN);
						String sek = generateResponse.get(AspApiConstants.SEK);
						byte[] SessionKeyInBytes = AESEncryptionV31.decrypt(sek, appkey.getBytes());
						Map<String, Object> reqmap = buildEwayBillRequest(ewayBillWITransaction);
						String reqString1 = NICUtil.getRequestString(reqmap);
						String request = new String(
								AESEncryptionV31.encryptEK(reqString1.getBytes(), SessionKeyInBytes));
						nicUserDetails.setData(request);
						nicUserDetails.setAuthToken(authtoken);
						Map<String, String> headersMap = NICUtil.getAuthTokenHeaderMap(nicUserDetails);
						Map<String, String> reqMap = NICUtil.getEwayBillRequest(nicUserDetails);
						String reqString = NICUtil.getRequestString(reqMap);
						logger.info("reqString ::::" + reqString1);
						Map<String, String> extraParams = new HashMap<String, String>();
						extraParams.put("methodName", "POST");
						String jsonResponseString = WebserviceCallUtil.callWebservice(ewaybillsystem, headersMap,
								reqString, extraParams);
						Map<String, String> responseMap = NICUtil.getResponseStringToObject(jsonResponseString);
						if (responseMap.get(AspApiConstants.STATUS).equals("0")) {
							byte[] decoded = DatatypeConverter
									.parseBase64Binary(responseMap.get(AspApiConstants.ERROR));
							Map<String, String> errorCode = NICUtil
									.getResponseStringToObject(new String(decoded, "UTF-8"));
							return getExcObjDesc(getErrorMsg(errorCode.get("errorCodes")));

						} else {
							Map responseMap2 = NICUtil.getResponseStringToObject(new String(AESEncryptionV31
									.decrypt(responseMap.get(AspApiConstants.JSON_DATA), SessionKeyInBytes)));
							response.put(AspApiConstants.STATUS_CD, AspApiConstants.SU_CD);
							response.put(AspApiConstants.STATUS, AspApiConstants.SUCCESS);
							response.put(AspApiConstants.EWAYBILL_DATE,
									responseMap2.get(AspApiConstants.EWAYBILL_DATE) + "");
							response.put(AspApiConstants.EWAYBILL_NO, formatDecimal(responseMap2) + "");
							response.put(AspApiConstants.EWAYBILL_VALIDUPTO,
									responseMap2.get(AspApiConstants.EWAYBILL_VALIDUPTO) + "");

							ewayBillWITransaction
									.setEwaybill_date(responseMap2.get(AspApiConstants.EWAYBILL_DATE) + "");
							ewayBillWITransaction.setEwaybillNo(formatDecimal(responseMap2) + "");
							ewayBillWITransaction
									.setEwaybill_valid_upto(responseMap2.get(AspApiConstants.EWAYBILL_VALIDUPTO) + "");
							ewayBillWITransaction.setEwaybillStatus(AspApiConstants.GENEWAYBILL);
							logger.info("generateEwaybill :: SUCCESS ::" + response);
							logger.info("generateEwaybill :: add ::" + eWayBill);
							ewayBillWITransaction.setTxnId("" + headersMap.get(AspApiConstants.TXN));
							addEwayBill(ewayBillWITransaction);
							return response;
						}

					} else {
						logger.info("Generate Eway Bill :: Generate AuthToken ::" + object);
						return (Map) object;
					}

				} else {
					return getExcObjDesc("Eway Bill Error:Generate AuthToken");
				}
			} else {
				return getExcObjDesc("Eway Bill onboarding Error:please complete onboarding process");
			}

		} catch (Exception e) {
			logger.info("generateEwaybill error" + e);
			return getExcObjDesc("Eway Bill Error:Generate AuthToken");
		}

	}

	public Map<String, Object> buildEwayBillRequest(EwayBillWITransaction ewayBillWITransaction)
			throws EwayBillApiException {
		logger.info("buildEwayBillRequest start");
		Map<String, Object> request = new LinkedHashMap<>();
		Map<String, Map<String, String>> masterMap = EWBWIUtil.getLoadEwayBillWIMasterList();
		try {
			String supplyTypeDesc = masterMap.get("Transaction").get(ewayBillWITransaction.getSupplyType());
			String subSupplyTypeDesc = masterMap.get(supplyTypeDesc).get(ewayBillWITransaction.getSubSupplyType());
			String documentTypeDesc = masterMap.get("Document").get(ewayBillWITransaction.getDocumentType());
			String transModeDesc = masterMap.get("Transportation_Mode").get(ewayBillWITransaction.getTransMode());
			request.put(AspApiConstants.SUPPLY_TYPE, ewayBillWITransaction.getSupplyType());
			ewayBillWITransaction.setSupplyTypeDesc(supplyTypeDesc);
			request.put(AspApiConstants.SUB_SUPPLY_TYPE, (ewayBillWITransaction.getSubSupplyType()));
			ewayBillWITransaction.setSubSupplyDesc(subSupplyTypeDesc);
			request.put(AspApiConstants.SUB_SUPPLY_TYPE_DESC, subSupplyTypeDesc);
			request.put(AspApiConstants.DOC_TYPE, ewayBillWITransaction.getDocumentType());
			ewayBillWITransaction.setDocumentTypeDesc(documentTypeDesc);
			request.put(AspApiConstants.DOC_NO, ewayBillWITransaction.getDocumentNo());
			ewayBillWITransaction.setDocumentDate(NICUtil.getSQLDate(ewayBillWITransaction.getDocumentDateInString()));
			request.put(AspApiConstants.DOC_DATE, ewayBillWITransaction.getDocumentDateInString());
			request.put(AspApiConstants.FROM_GSTIN, ewayBillWITransaction.getFromGstin());//
			request.put(AspApiConstants.FROM_TRNAME, ewayBillWITransaction.getFromTraderName());
			request.put(AspApiConstants.FROM_ADD, ewayBillWITransaction.getFromAddress1());
			request.put(AspApiConstants.FROM_ADD2, ewayBillWITransaction.getFromAddress2());
			request.put(AspApiConstants.FROM_PLACE, ewayBillWITransaction.getFromPlace());
			request.put(AspApiConstants.FROM_PINCODE, ewayBillWITransaction.getFromPincode());
			request.put(AspApiConstants.ACTFROM_STATECODE, ewayBillWITransaction.getActFromStateCode());
			request.put(AspApiConstants.FROM_STATECODE, ewayBillWITransaction.getFromStateCode());
			request.put(AspApiConstants.TO_GSTIN, ewayBillWITransaction.getToGstin());
			request.put(AspApiConstants.TO_TRNAME, ewayBillWITransaction.getToTraderName());
			request.put(AspApiConstants.TO_ADD, ewayBillWITransaction.getToAddress1());
			request.put(AspApiConstants.TO_ADD2, "");
			request.put(AspApiConstants.ACTTO_STATECODE, ewayBillWITransaction.getActToStateCode());
			request.put(AspApiConstants.TO_STATECODE, ewayBillWITransaction.getToStateCode());
			request.put(AspApiConstants.TO_PLACE, ewayBillWITransaction.getToPlace());
			request.put(AspApiConstants.TO_PINCODE, ewayBillWITransaction.getToPincode());

			request.put(AspApiConstants.TRANSACTIONTYPE, 1);
			request.put(AspApiConstants.DISPATCHFROM_GSTIN, "");
			request.put(AspApiConstants.DISPATCHFROM_TRADENAME, "");
			request.put(AspApiConstants.SHIPTOGSTIN, "");
			request.put(AspApiConstants.SHIPTO_TRADENAME, "");
			request.put(AspApiConstants.OTHERVALUE, 0);

			List<Map<String, Object>> itemList = new ArrayList<>();
			String fromGstinStCode = null;
			if (!ewayBillWITransaction.getFromGstin().equals("URP")) {
				fromGstinStCode = ewayBillWITransaction.getFromGstin().substring(0, 2);
			} else {
				fromGstinStCode = String.valueOf(
						ewayBillWITransaction.getFromStateCode() < 10 ? "0" + ewayBillWITransaction.getFromStateCode()
								: ewayBillWITransaction.getFromStateCode());
			}
			String toGstinStCode = null;
			if (!ewayBillWITransaction.getToGstin().equals("URP")) {
				toGstinStCode = ewayBillWITransaction.getToGstin().substring(0, 2);
			} else {
				toGstinStCode = String.valueOf(
						ewayBillWITransaction.getToStateCode() < 10 ? "0" + ewayBillWITransaction.getToStateCode()
								: ewayBillWITransaction.getToStateCode());
			}
			List<EwayBillWIItem> itemList2 = new ArrayList<>();
			double sgstTotalAmount = 0;
			double cgstTotalAmount = 0;
			double igstTotalAmount = 0;
			double totaltaxAmount = 0;
			double totalcessAmount = 0;
			for (EwayBillWIItem ewayBillWIItem : ewayBillWITransaction.getEwayBillWIItem()) {
				Map<String, Object> serviceRequestMap = new LinkedHashMap<>();
				HSNDetails HSNDetails = HSNService.getHSNDetailsById(ewayBillWIItem.getHsnId());
				serviceRequestMap.put(AspApiConstants.PRODUCT_NAME, ewayBillWIItem.getProductName());
				serviceRequestMap.put(AspApiConstants.PRODUCT_DESC, ewayBillWIItem.getProductDesc());
				serviceRequestMap.put(AspApiConstants.HSN_CODE, Integer.parseInt(HSNDetails.getHsnCode()));
				serviceRequestMap.put(AspApiConstants.QUANTITY, ewayBillWIItem.getQuantity());
				serviceRequestMap.put(AspApiConstants.QTY_UNIT, ewayBillWIItem.getQuantityUnit());

				if (HSNDetails != null) {
					double cgstRate = HSNDetails.getCgst();
					double igstRate = HSNDetails.getIgst();
					double sgstRate = HSNDetails.getSgstUgst();
					double sgstAmount = 0;
					double cgstAmount = 0;
					double igstAmount = 0;
					if (fromGstinStCode.equals(toGstinStCode)) {
						serviceRequestMap.put(AspApiConstants.CGST_RATE, cgstRate);
						serviceRequestMap.put(AspApiConstants.SGST_RATE, sgstRate);
						serviceRequestMap.put(AspApiConstants.IGST_RATE, 0d);
						ewayBillWIItem.setCgstRate(cgstRate);
						ewayBillWIItem.setSgstRate(sgstRate);
						ewayBillWIItem.setIgstRate(0d);
						sgstAmount = ((sgstRate * ewayBillWIItem.getTaxableAmount()) / 100);
						cgstAmount = ((cgstRate * ewayBillWIItem.getTaxableAmount()) / 100);
						sgstTotalAmount = NICUtil.getRoundingMode(sgstTotalAmount)
								+ NICUtil.getRoundingMode(sgstAmount);
						cgstTotalAmount = NICUtil.getRoundingMode(cgstTotalAmount)
								+ NICUtil.getRoundingMode(cgstAmount);

					} else {
						serviceRequestMap.put(AspApiConstants.CGST_RATE, 0d);
						serviceRequestMap.put(AspApiConstants.SGST_RATE, 0d);
						serviceRequestMap.put(AspApiConstants.IGST_RATE, igstRate);
						ewayBillWIItem.setCgstRate(0d);
						ewayBillWIItem.setSgstRate(0d);
						ewayBillWIItem.setIgstRate(igstRate);
						igstAmount = (igstRate * ewayBillWIItem.getTaxableAmount()) / 100;
						igstTotalAmount = NICUtil.getRoundingMode(igstTotalAmount)
								+ NICUtil.getRoundingMode(igstAmount);
					}
					ewayBillWIItem.setCessRate(0d);
					ewayBillWIItem.setCessAdVol(0);
					totaltaxAmount = totaltaxAmount + NICUtil.getRoundingMode(ewayBillWIItem.getTaxableAmount());
					totalcessAmount = totalcessAmount + ewayBillWIItem.getCessValue();
					ewayBillWIItem.setHsnCode(HSNDetails.getHsnCode());
					serviceRequestMap.put(AspApiConstants.CESS_RATE, 0);
					serviceRequestMap.put(AspApiConstants.CESS_NONADVOL, 0);
					serviceRequestMap.put(AspApiConstants.TAXABLE_AMOUNT, ewayBillWIItem.getTaxableAmount());
					ewayBillWIItem.setCreatedOn(NICUtil.getSQLDate());
					ewayBillWIItem.setCreatedBy(ewayBillWITransaction.getUserId());
					ewayBillWIItem.setCgstValue(NICUtil.getRoundingMode(cgstAmount));
					ewayBillWIItem.setSgstValue(NICUtil.getRoundingMode(sgstAmount));
					ewayBillWIItem.setIgstValue(NICUtil.getRoundingMode(igstAmount));
				}
				ewayBillWIItem.setEwayBillWITransaction(ewayBillWITransaction);
				itemList2.add(ewayBillWIItem);
				itemList.add(serviceRequestMap);
			}

			ewayBillWITransaction.setEwayBillWIItem(itemList2);
			request.put(AspApiConstants.CGST_VALUE, cgstTotalAmount);
			request.put(AspApiConstants.SGST_VALUE, sgstTotalAmount);
			request.put(AspApiConstants.IGST_VALUE, igstTotalAmount);
			request.put(AspApiConstants.CESS_VALUE, totalcessAmount);
			request.put(AspApiConstants.TOTAL_VALUE, totaltaxAmount);
			request.put(AspApiConstants.TOTAL_INVVALUE,
					totaltaxAmount + sgstTotalAmount + cgstTotalAmount + igstTotalAmount + totalcessAmount);

			request.put(AspApiConstants.CESS_NONADVOLVALUE, 0);
			ewayBillWITransaction.setCgstValue(NICUtil.getRoundingMode(cgstTotalAmount));
			ewayBillWITransaction.setSgstValue(NICUtil.getRoundingMode(sgstTotalAmount));
			ewayBillWITransaction.setIgstValue(NICUtil.getRoundingMode(igstTotalAmount));
			ewayBillWITransaction.setTotalValue(NICUtil.getRoundingMode(totaltaxAmount));
			ewayBillWITransaction.setCessValue(totalcessAmount);
			ewayBillWITransaction.setTotInvValue(NICUtil.getRoundingMode(
					totaltaxAmount + sgstTotalAmount + cgstTotalAmount + igstTotalAmount + totalcessAmount));
			request.put(AspApiConstants.TRANSPORTER_ID, ewayBillWITransaction.getTransId());
			request.put(AspApiConstants.TRANSPORTER_NAME, ewayBillWITransaction.getTransName());
			request.put(AspApiConstants.TRANS_DOC_NO, ewayBillWITransaction.getTransDocNo());
			request.put(AspApiConstants.TRANS_MODE, ewayBillWITransaction.getTransMode());
			ewayBillWITransaction.setTransModeDesc(transModeDesc);
			Double distance = ewayBillWITransaction.getTransDistance();
			request.put(AspApiConstants.TRANSDISTANCE, distance.intValue() + "");
			ewayBillWITransaction.setTransDocDate(NICUtil.getSQLDate(ewayBillWITransaction.getTransDocDateInString()));
			request.put(AspApiConstants.TRANSDOCDATE, ewayBillWITransaction.getTransDocDateInString());
			request.put(AspApiConstants.VEHICLE_NO, ewayBillWITransaction.getVehicleNo());
			request.put(AspApiConstants.VEHICLE_TYPE, ewayBillWITransaction.getVehicleType());
			request.put(AspApiConstants.ITEM_LIST, itemList);

		} catch (Exception e) {
			logger.error("buildEwayBillRequest error" + e);
			throw new EwayBillApiException("Eway Bill Request Generation error");
		}
		logger.info("buildEwayBillRequest end");
		return request;
	}

	public Map<String, Object> buildEwayBillRequestV3(EwayBillWITransaction ewayBillWITransaction)
			throws EwayBillApiException {
		logger.info("buildEwayBillRequest start");
		Map<String, Object> request = new LinkedHashMap<>();
		Map<String, Map<String, String>> masterMap = EWBWIUtil.getLoadEwayBillWIMasterList();
		try {
			String supplyTypeDesc = masterMap.get("Transaction").get(ewayBillWITransaction.getSupplyType());
			String subSupplyTypeDesc = masterMap.get(supplyTypeDesc).get(ewayBillWITransaction.getSubSupplyType());
			String documentTypeDesc = masterMap.get("Document").get(ewayBillWITransaction.getDocumentType());
			String transModeDesc = masterMap.get("Transportation_Mode").get(ewayBillWITransaction.getTransMode());
			request.put(AspApiConstants.SUPPLY_TYPE, ewayBillWITransaction.getSupplyType());
			ewayBillWITransaction.setSupplyTypeDesc(supplyTypeDesc);
			request.put(AspApiConstants.SUB_SUPPLY_TYPE, (ewayBillWITransaction.getSubSupplyType()));
			ewayBillWITransaction.setSubSupplyDesc(subSupplyTypeDesc);
			request.put(AspApiConstants.SUB_SUPPLY_TYPE_DESC, subSupplyTypeDesc);
			request.put(AspApiConstants.DOC_TYPE, ewayBillWITransaction.getDocumentType());
			ewayBillWITransaction.setDocumentTypeDesc(documentTypeDesc);
			request.put(AspApiConstants.DOC_NO, ewayBillWITransaction.getDocumentNo());
			ewayBillWITransaction.setDocumentDate(NICUtil.getSQLDate(ewayBillWITransaction.getDocumentDateInString()));
			request.put(AspApiConstants.DOC_DATE, ewayBillWITransaction.getDocumentDateInString());
			request.put(AspApiConstants.FROM_GSTIN, ewayBillWITransaction.getFromGstin());
			request.put(AspApiConstants.FROM_TRNAME, ewayBillWITransaction.getFromTraderName());
			request.put(AspApiConstants.FROM_ADD, ewayBillWITransaction.getFromAddress1());
			request.put(AspApiConstants.FROM_ADD2, ewayBillWITransaction.getFromAddress2());
			request.put(AspApiConstants.FROM_PLACE, ewayBillWITransaction.getFromPlace());
			request.put(AspApiConstants.FROM_PINCODE, ewayBillWITransaction.getFromPincode());
			request.put(AspApiConstants.ACTFROM_STATECODE, ewayBillWITransaction.getActFromStateCode());
			request.put(AspApiConstants.FROM_STATECODE, ewayBillWITransaction.getFromStateCode());
			request.put(AspApiConstants.TO_GSTIN, ewayBillWITransaction.getToGstin());
			request.put(AspApiConstants.TO_TRNAME, ewayBillWITransaction.getToTraderName());
			request.put(AspApiConstants.TO_ADD, ewayBillWITransaction.getToAddress1());
			request.put(AspApiConstants.TO_ADD2, "");
			request.put(AspApiConstants.ACTTO_STATECODE, ewayBillWITransaction.getActToStateCode());
			request.put(AspApiConstants.TO_STATECODE, ewayBillWITransaction.getToStateCode());
			request.put(AspApiConstants.TO_PLACE, ewayBillWITransaction.getToPlace());
			request.put(AspApiConstants.TO_PINCODE, ewayBillWITransaction.getToPincode());
			request.put(AspApiConstants.TRANSACTIONTYPE, 1);
			request.put(AspApiConstants.DISPATCHFROM_GSTIN, "");
			request.put(AspApiConstants.DISPATCHFROM_TRADENAME, "");
			request.put(AspApiConstants.SHIPTOGSTIN, "");
			request.put(AspApiConstants.SHIPTO_TRADENAME, "");
			request.put(AspApiConstants.OTHERVALUE, ewayBillWITransaction.getOtherValue());

			List<Map<String, Object>> itemList = new ArrayList<>();
			String fromGstinStCode = null;
			if (!ewayBillWITransaction.getFromGstin().equals("URP")) {
				fromGstinStCode = ewayBillWITransaction.getFromGstin().substring(0, 2);
			} else {
				fromGstinStCode = String.valueOf(
						ewayBillWITransaction.getFromStateCode() < 10 ? "0" + ewayBillWITransaction.getFromStateCode()
								: ewayBillWITransaction.getFromStateCode());
			}
			String toGstinStCode = null;
			if (!ewayBillWITransaction.getToGstin().equals("URP")) {
				toGstinStCode = ewayBillWITransaction.getToGstin().substring(0, 2);
			} else {
				toGstinStCode = String.valueOf(
						ewayBillWITransaction.getToStateCode() < 10 ? "0" + ewayBillWITransaction.getToStateCode()
								: ewayBillWITransaction.getToStateCode());
			}
			List<EwayBillWIItem> itemList2 = new ArrayList<>();
			double sgstTotalAmount = 0;
			double cgstTotalAmount = 0;
			double igstTotalAmount = 0;
			double totaltaxAmount = 0;
			double totalcessadvolAmount = 0;
			double totalcessnonadvolAmount = 0;
			for (EwayBillWIItem ewayBillWIItem : ewayBillWITransaction.getEwayBillWIItem()) {
				Map<String, Object> serviceRequestMap = new LinkedHashMap<>();
				HSNDetails HSNDetails = HSNService.getHSNDetailsById(ewayBillWIItem.getHsnId());
				serviceRequestMap.put(AspApiConstants.PRODUCT_NAME, ewayBillWIItem.getProductName());
				serviceRequestMap.put(AspApiConstants.PRODUCT_DESC, ewayBillWIItem.getProductDesc());
				serviceRequestMap.put(AspApiConstants.HSN_CODE, Integer.parseInt(HSNDetails.getHsnCode()));
				serviceRequestMap.put(AspApiConstants.QUANTITY, ewayBillWIItem.getQuantity());
				serviceRequestMap.put(AspApiConstants.QTY_UNIT, ewayBillWIItem.getQuantityUnit());
				double cgstRate = ewayBillWIItem.getCgstsgstRate() > 0 ? ewayBillWIItem.getCgstsgstRate() / 2
						: ewayBillWIItem.getCgstsgstRate();
				double igstRate = ewayBillWIItem.getIgstRate();
				double sgstRate = ewayBillWIItem.getCgstsgstRate() > 0 ? ewayBillWIItem.getCgstsgstRate() / 2
						: ewayBillWIItem.getCgstsgstRate();
				double sgstAmount = 0;
				double cgstAmount = 0;
				double igstAmount = 0;
				if (fromGstinStCode.equals(toGstinStCode)) {
					serviceRequestMap.put(AspApiConstants.CGST_RATE, cgstRate);
					serviceRequestMap.put(AspApiConstants.SGST_RATE, sgstRate);
					serviceRequestMap.put(AspApiConstants.IGST_RATE, 0d);
					ewayBillWIItem.setCgstRate(cgstRate);
					ewayBillWIItem.setSgstRate(sgstRate);
					ewayBillWIItem.setIgstRate(0d);
					sgstAmount = ((sgstRate * ewayBillWIItem.getTaxableAmount()) / 100);
					cgstAmount = ((cgstRate * ewayBillWIItem.getTaxableAmount()) / 100);
					sgstTotalAmount = NICUtil.getRoundingMode(sgstTotalAmount) + NICUtil.getRoundingMode(sgstAmount);
					cgstTotalAmount = NICUtil.getRoundingMode(cgstTotalAmount) + NICUtil.getRoundingMode(cgstAmount);

				} else {
					serviceRequestMap.put(AspApiConstants.CGST_RATE, 0d);
					serviceRequestMap.put(AspApiConstants.SGST_RATE, 0d);
					serviceRequestMap.put(AspApiConstants.IGST_RATE, igstRate);
					ewayBillWIItem.setCgstRate(0d);
					ewayBillWIItem.setSgstRate(0d);
					ewayBillWIItem.setIgstRate(igstRate);
					igstAmount = (igstRate * ewayBillWIItem.getTaxableAmount()) / 100;
					igstTotalAmount = NICUtil.getRoundingMode(igstTotalAmount) + NICUtil.getRoundingMode(igstAmount);
				}

				if (ewayBillWITransaction.getTotalCessadvolValue() == 0) {
					double cessadvolAmountTemp = NICUtil.getRoundingMode(
							(ewayBillWIItem.getCessadvolrate() * ewayBillWIItem.getTaxableAmount()) / 100);
					ewayBillWIItem.setCessadvolAmount(cessadvolAmountTemp);
					totalcessadvolAmount = NICUtil.getRoundingMode(totalcessadvolAmount) + cessadvolAmountTemp;
					serviceRequestMap.put(AspApiConstants.CESS_RATE, ewayBillWIItem.getCessadvolrate());
					ewayBillWIItem.setCessadvolAmount(cessadvolAmountTemp);

				} else {
					serviceRequestMap.put(AspApiConstants.CESS_RATE, 0);
					ewayBillWIItem.setCessadvolAmount(0d);
				}
				if (ewayBillWITransaction.getTotalCessnonadvolValue() == 0) {
					double cessnonadvolAmountTemp = NICUtil.getRoundingMode(ewayBillWIItem.getCessnonadvolrate());
					ewayBillWIItem.setCessnonadvolAmount(cessnonadvolAmountTemp);
					totalcessnonadvolAmount = NICUtil.getRoundingMode(totalcessnonadvolAmount) + cessnonadvolAmountTemp;
					serviceRequestMap.put(AspApiConstants.CESS_NONADVOL, cessnonadvolAmountTemp);
					ewayBillWIItem.setCessnonadvolAmount(cessnonadvolAmountTemp);

				} else {
					serviceRequestMap.put(AspApiConstants.CESS_NONADVOL, 0);
					ewayBillWIItem.setCessnonadvolAmount(0d);
				}

				totaltaxAmount = totaltaxAmount + NICUtil.getRoundingMode(ewayBillWIItem.getTaxableAmount());

				ewayBillWIItem.setHsnCode(HSNDetails.getHsnCode());

				serviceRequestMap.put(AspApiConstants.TAXABLE_AMOUNT, ewayBillWIItem.getTaxableAmount());
				ewayBillWIItem.setCreatedOn(NICUtil.getSQLDate());
				ewayBillWIItem.setCreatedBy(ewayBillWITransaction.getUserId());
				ewayBillWIItem.setCgstValue(NICUtil.getRoundingMode(cgstAmount));
				ewayBillWIItem.setSgstValue(NICUtil.getRoundingMode(sgstAmount));
				ewayBillWIItem.setIgstValue(NICUtil.getRoundingMode(igstAmount));

				ewayBillWIItem.setEwayBillWITransaction(ewayBillWITransaction);
				itemList2.add(ewayBillWIItem);
				itemList.add(serviceRequestMap);
			}

			ewayBillWITransaction.setEwayBillWIItem(itemList2);
			request.put(AspApiConstants.CGST_VALUE, cgstTotalAmount);
			request.put(AspApiConstants.SGST_VALUE, sgstTotalAmount);
			request.put(AspApiConstants.IGST_VALUE, igstTotalAmount);
			request.put(AspApiConstants.TOTAL_VALUE, totaltaxAmount);

			ewayBillWITransaction.setCgstValue(NICUtil.getRoundingMode(cgstTotalAmount));
			ewayBillWITransaction.setSgstValue(NICUtil.getRoundingMode(sgstTotalAmount));
			ewayBillWITransaction.setIgstValue(NICUtil.getRoundingMode(igstTotalAmount));
			ewayBillWITransaction.setTotalValue(NICUtil.getRoundingMode(totaltaxAmount));
			double totalInvValue = 0;
			if (totalcessadvolAmount == 0 && totalcessnonadvolAmount == 0) {

				totalInvValue = NICUtil.getRoundingMode(totaltaxAmount + sgstTotalAmount + cgstTotalAmount
						+ igstTotalAmount + ewayBillWITransaction.getTotalCessadvolValue()
						+ ewayBillWITransaction.getTotalCessnonadvolValue() + ewayBillWITransaction.getOtherValue());
				ewayBillWITransaction.setTotInvValue(totalInvValue);
				request.put(AspApiConstants.TOTAL_INVVALUE, totalInvValue);

				request.put(AspApiConstants.CESS_VALUE, ewayBillWITransaction.getTotalCessadvolValue());
				request.put(AspApiConstants.CESS_NONADVOLVALUE, ewayBillWITransaction.getTotalCessnonadvolValue());

			} else if (totalcessadvolAmount > 0 && totalcessnonadvolAmount > 0) {
				totalInvValue = NICUtil.getRoundingMode(
						totaltaxAmount + sgstTotalAmount + cgstTotalAmount + igstTotalAmount + totalcessadvolAmount
								+ totalcessnonadvolAmount + ewayBillWITransaction.getOtherValue());
				ewayBillWITransaction.setTotInvValue(totalInvValue);
				request.put(AspApiConstants.TOTAL_INVVALUE, totalInvValue);
				ewayBillWITransaction.setTotalCessadvolValue(totalcessadvolAmount);
				ewayBillWITransaction.setTotalCessnonadvolValue(totalcessnonadvolAmount);

				request.put(AspApiConstants.CESS_VALUE, totalcessadvolAmount);
				request.put(AspApiConstants.CESS_NONADVOLVALUE, totalcessnonadvolAmount);

			} else if (totalcessadvolAmount == 0 && totalcessnonadvolAmount > 0) {
				totalInvValue = NICUtil.getRoundingMode(totaltaxAmount + sgstTotalAmount + cgstTotalAmount
						+ igstTotalAmount + ewayBillWITransaction.getTotalCessadvolValue() + totalcessnonadvolAmount
						+ ewayBillWITransaction.getOtherValue());
				ewayBillWITransaction.setTotInvValue(totalInvValue);
				request.put(AspApiConstants.TOTAL_INVVALUE, totalInvValue);
				ewayBillWITransaction.setTotalCessnonadvolValue(totalcessnonadvolAmount);

				request.put(AspApiConstants.CESS_VALUE, totalcessadvolAmount);
				request.put(AspApiConstants.CESS_NONADVOLVALUE, ewayBillWITransaction.getTotalCessnonadvolValue());
			} else if (totalcessadvolAmount > 0 && totalcessnonadvolAmount == 0) {
				totalInvValue = NICUtil.getRoundingMode(totaltaxAmount + sgstTotalAmount + cgstTotalAmount
						+ igstTotalAmount + totalcessadvolAmount + ewayBillWITransaction.getTotalCessnonadvolValue()
						+ ewayBillWITransaction.getOtherValue());
				ewayBillWITransaction.setTotInvValue(totalInvValue);
				request.put(AspApiConstants.TOTAL_INVVALUE, totalInvValue);
				ewayBillWITransaction.setTotalCessadvolValue(totalcessadvolAmount);

				request.put(AspApiConstants.CESS_VALUE, ewayBillWITransaction.getTotalCessadvolValue());
				request.put(AspApiConstants.CESS_NONADVOLVALUE, totalcessnonadvolAmount);

			}
			request.put(AspApiConstants.TRANSPORTER_ID, ewayBillWITransaction.getTransId());
			request.put(AspApiConstants.TRANSPORTER_NAME, ewayBillWITransaction.getTransName());
			request.put(AspApiConstants.TRANS_DOC_NO, ewayBillWITransaction.getTransDocNo());
			request.put(AspApiConstants.TRANS_MODE, ewayBillWITransaction.getTransMode());
			ewayBillWITransaction.setTransModeDesc(transModeDesc);
			Double distance = ewayBillWITransaction.getTransDistance();
			request.put(AspApiConstants.TRANSDISTANCE, distance.intValue() + "");
			ewayBillWITransaction.setTransDocDate(NICUtil.getSQLDate(ewayBillWITransaction.getTransDocDateInString()));
			request.put(AspApiConstants.TRANSDOCDATE, ewayBillWITransaction.getTransDocDateInString());
			request.put(AspApiConstants.VEHICLE_NO, ewayBillWITransaction.getVehicleNo());
			request.put(AspApiConstants.VEHICLE_TYPE, ewayBillWITransaction.getVehicleType());
			request.put(AspApiConstants.ITEM_LIST, itemList);

		} catch (Exception e) {
			logger.error("buildEwayBillRequest error" + e);
			throw new EwayBillApiException("Eway Bill Request Generation error");
		}
		logger.info("buildEwayBillRequest end");
		return request;
	}

	public Map<String, Object> buildEwayBillResponse(EwayBillWITransaction ewayBillWITransaction)
			throws EwayBillApiException {
		Map<String, Object> request = new LinkedHashMap<>();
		logger.info("buildEwayBillResponse start");
		try {
			request.put(AspApiConstants.SUPPLY_TYPE, ewayBillWITransaction.getSupplyType());
			request.put(AspApiConstants.SUPPLY_TYPE_DESC, ewayBillWITransaction.getSupplyTypeDesc());
			request.put(AspApiConstants.SUB_SUPPLY_TYPE, ewayBillWITransaction.getSubSupplyType());
			request.put(AspApiConstants.SUB_SUPPLY_TYPE_DESC, ewayBillWITransaction.getSubSupplyDesc());
			request.put(AspApiConstants.OTH_SUB_SUPPLY_TYPE_DESC, ewayBillWITransaction.getOthersSubType());
			request.put(AspApiConstants.DOC_TYPE, ewayBillWITransaction.getDocumentType());
			request.put(AspApiConstants.DOC_TYPE_DESC, ewayBillWITransaction.getDocumentTypeDesc());
			request.put(AspApiConstants.DOC_NO, ewayBillWITransaction.getDocumentNo());
			request.put(AspApiConstants.DOC_DATE, NICUtil.getSQLDate(ewayBillWITransaction.getDocumentDate()));
			request.put(AspApiConstants.FROM_GSTIN, ewayBillWITransaction.getFromGstin());//
			request.put(AspApiConstants.FROM_TRNAME, ewayBillWITransaction.getFromTraderName());
			request.put(AspApiConstants.FROM_ADD, ewayBillWITransaction.getFromAddress1());
			request.put(AspApiConstants.FROM_ADD2, ewayBillWITransaction.getFromAddress2());
			request.put(AspApiConstants.FROM_PLACE, ewayBillWITransaction.getFromPlace());
			request.put(AspApiConstants.FROM_PINCODE, ewayBillWITransaction.getFromPincode());
			request.put(AspApiConstants.ACTFROM_STATECODE, ewayBillWITransaction.getActFromStateCode());
			request.put(AspApiConstants.FROM_STATECODE, ewayBillWITransaction.getFromStateCode());
			request.put(AspApiConstants.TO_GSTIN, ewayBillWITransaction.getToGstin());
			request.put(AspApiConstants.TO_TRNAME, ewayBillWITransaction.getToTraderName());
			request.put(AspApiConstants.TO_ADD, ewayBillWITransaction.getToAddress1());
			request.put(AspApiConstants.TO_ADD2, "");
			request.put(AspApiConstants.TO_PINCODE, ewayBillWITransaction.getToPincode());
			request.put(AspApiConstants.ACTTO_STATECODE, ewayBillWITransaction.getActToStateCode());
			request.put(AspApiConstants.TO_STATECODE, ewayBillWITransaction.getToStateCode());
			request.put(AspApiConstants.TO_PLACE, ewayBillWITransaction.getToPlace());

			List<Map<String, Object>> itemList = new ArrayList<>();
			for (EwayBillWIItem ewayBillWIItem : ewayBillWITransaction.getEwayBillWIItem()) {
				Map<String, Object> serviceRequestMap = new LinkedHashMap<>();
				serviceRequestMap.put(AspApiConstants.PRODUCT_NAME, ewayBillWIItem.getProductName());
				serviceRequestMap.put(AspApiConstants.PRODUCT_DESC, ewayBillWIItem.getProductDesc());
				serviceRequestMap.put(AspApiConstants.HSN_CODE, ewayBillWIItem.getHsnCode());
				serviceRequestMap.put(AspApiConstants.QUANTITY, ewayBillWIItem.getQuantity());
				serviceRequestMap.put(AspApiConstants.QTY_UNIT, ewayBillWIItem.getQuantityUnit());

				serviceRequestMap.put(AspApiConstants.CGST_RATE, ewayBillWIItem.getCgstRate());
				serviceRequestMap.put(AspApiConstants.SGST_RATE, ewayBillWIItem.getSgstRate());
				serviceRequestMap.put(AspApiConstants.IGST_RATE, ewayBillWIItem.getIgstRate());
				serviceRequestMap.put(AspApiConstants.CESS_RATE, ewayBillWIItem.getCessRate());

				serviceRequestMap.put(AspApiConstants.CGST_VALUE, ewayBillWIItem.getCgstValue());
				serviceRequestMap.put(AspApiConstants.SGST_VALUE, ewayBillWIItem.getSgstValue());
				serviceRequestMap.put(AspApiConstants.IGST_VALUE, ewayBillWIItem.getIgstValue());
				serviceRequestMap.put(AspApiConstants.CESS_VALUE, ewayBillWIItem.getCessValue());

				serviceRequestMap.put(AspApiConstants.CESS_ADVOL_RATE, ewayBillWIItem.getCessadvolrate());
				serviceRequestMap.put(AspApiConstants.CESS_NONADVOL_RATE, ewayBillWIItem.getCessnonadvolrate());
				serviceRequestMap.put(AspApiConstants.CESS_ADVOL_AMOUNT, ewayBillWIItem.getCessadvolAmount());
				serviceRequestMap.put(AspApiConstants.CESS_NONADVOL_AMOUNT, ewayBillWIItem.getCessnonadvolAmount());

				serviceRequestMap.put(AspApiConstants.TAXABLE_AMOUNT, ewayBillWIItem.getTaxableAmount());
				itemList.add(serviceRequestMap);
			}
			ewayBillWITransaction.setEwayBillWIItemList(itemList);
			request.put(AspApiConstants.CGST_VALUE, ewayBillWITransaction.getCgstValue());
			request.put(AspApiConstants.SGST_VALUE, ewayBillWITransaction.getSgstValue());
			request.put(AspApiConstants.IGST_VALUE, ewayBillWITransaction.getIgstValue());
			request.put(AspApiConstants.CESS_VALUE, ewayBillWITransaction.getCessValue());
			request.put(AspApiConstants.TOTAL_VALUE, ewayBillWITransaction.getTotalValue());
			request.put(AspApiConstants.TOTAL_INVVALUE, ewayBillWITransaction.getTotInvValue());
			request.put(AspApiConstants.OTHERVALUE, ewayBillWITransaction.getOtherValue());
			request.put(AspApiConstants.CESS_ADVOL_AMOUNT, ewayBillWITransaction.getTotalCessadvolValue());
			request.put(AspApiConstants.CESS_NONADVOL_AMOUNT, ewayBillWITransaction.getTotalCessnonadvolValue());
			request.put(AspApiConstants.TRANSPORTER_ID, ewayBillWITransaction.getTransId());
			request.put(AspApiConstants.TRANSPORTER_NAME, ewayBillWITransaction.getTransName());
			request.put(AspApiConstants.TRANS_DOC_NO, ewayBillWITransaction.getTransDocNo());
			request.put(AspApiConstants.TRANS_MODE, ewayBillWITransaction.getTransMode());
			request.put(AspApiConstants.TRANS_MODE_DESC, ewayBillWITransaction.getTransModeDesc());
			Double distance = ewayBillWITransaction.getTransDistance();
			request.put(AspApiConstants.TRANSDISTANCE, distance.intValue() + "");
			request.put(AspApiConstants.TRANSDOCDATE, NICUtil.getSQLDate(ewayBillWITransaction.getTransDocDate()));
			request.put(AspApiConstants.VEHICLE_NO, ewayBillWITransaction.getVehicleNo());
			request.put(AspApiConstants.VEHICLE_TYPE, ewayBillWITransaction.getVehicleType());
			if (ewayBillWITransaction.getVehicleType().equals("R"))
				request.put(AspApiConstants.VEHICLE_TYPE_DESC, "Regular");
			else
				request.put(AspApiConstants.VEHICLE_TYPE_DESC, "Over Dimensional Cargo");
			request.put(AspApiConstants.EWAYBILL_NO, ewayBillWITransaction.getEwaybillNo());
			request.put(AspApiConstants.EWAYBILL_DATE, ewayBillWITransaction.getEwaybill_date());
			request.put("ewaybill_valid_upto", ewayBillWITransaction.getEwaybill_valid_upto());
			request.put("ewaybillStatus", ewayBillWITransaction.getEwaybillStatus());
			request.put(AspApiConstants.ITEM_LIST, itemList);

		} catch (Exception e) {
			logger.error("buildEwayBillResponse error :::::" + e);
			throw new EwayBillApiException(e.getMessage());
		}
		return request;
	}

	public void setData(Map<String, Object> request, EwayBillWITransaction ewayBillWITransaction) {
		Map<String, Object> fromGstinMap = (Map) ewayBillWITransaction.getHeadersMap()
				.get(ewayBillWITransaction.getFromGstin());
		Map<String, Object> fromGstinAddmap = (Map) ((Map) fromGstinMap.get("pradr")).get("addr");
		Integer fromstd = Integer.parseInt(ewayBillWITransaction.getFromGstin().substring(0, 2));
		request.put(AspApiConstants.FROM_TRNAME, fromGstinMap.get("tradeNam"));
		ewayBillWITransaction.setFromTraderName("" + fromGstinMap.get("tradeNam"));
		request.put(AspApiConstants.FROM_ADD, fromGstinAddmap.get("loc") + "" + fromGstinAddmap.get("st") + ""
				+ fromGstinAddmap.get("dst") + "" + fromGstinAddmap.get("stcd"));
		Object dst = fromGstinAddmap.get("dst");
		ewayBillWITransaction.setFromAddress1(fromGstinAddmap.get("flno") + " " + fromGstinAddmap.get("bnm") + " "
				+ fromGstinAddmap.get("st") + " " + fromGstinAddmap.get("loc") + " " + fromGstinAddmap.get("bno") + " "
				+ fromGstinAddmap.get("dst") + " " + fromGstinAddmap.get("stcd") + " " + fromGstinAddmap.get("city"));
		request.put(AspApiConstants.FROM_ADD2, "");
		if (dst != null && !dst.equals("")) {
			request.put(AspApiConstants.FROM_PLACE, fromGstinAddmap.get("dst"));
			ewayBillWITransaction.setFromPlace("" + fromGstinAddmap.get("dst"));
		} else {
			request.put(AspApiConstants.FROM_PLACE, fromGstinAddmap.get("loc"));
			ewayBillWITransaction.setFromPlace("" + fromGstinAddmap.get("loc"));
		}
		request.put(AspApiConstants.FROM_PINCODE, Long.parseLong((String) fromGstinAddmap.get("pncd")));
		ewayBillWITransaction.setFromPincode(Integer.parseInt((String) fromGstinAddmap.get("pncd")));
		request.put(AspApiConstants.ACTFROM_STATECODE, fromstd);
		request.put(AspApiConstants.FROM_STATECODE, fromstd);
		ewayBillWITransaction.setActFromStateCode(fromstd);
		ewayBillWITransaction.setFromStateCode(fromstd);

		Map<String, Object> toGstinMap = (Map) ewayBillWITransaction.getHeadersMap()
				.get(ewayBillWITransaction.getToGstin());

		Map<String, Object> toGstinAddmap = (Map) ((Map) toGstinMap.get("pradr")).get("addr");
		Integer tostd = Integer.parseInt(ewayBillWITransaction.getToGstin().substring(0, 2));
		request.put(AspApiConstants.TO_TRNAME, toGstinMap.get("tradeNam"));
		request.put(AspApiConstants.TO_ADD, toGstinAddmap.get("loc") + "" + toGstinAddmap.get("st") + ""
				+ toGstinAddmap.get("dst") + " " + toGstinAddmap.get("stcd"));
		request.put(AspApiConstants.TO_ADD2, "");
		Object dst2 = toGstinAddmap.get("dst");
		if (dst2 != null) {
			request.put(AspApiConstants.TO_PLACE, toGstinAddmap.get("dst"));
			ewayBillWITransaction.setToPlace("" + toGstinAddmap.get("dst"));
		} else {
			request.put(AspApiConstants.TO_PLACE, toGstinAddmap.get("loc"));
			ewayBillWITransaction.setToPlace("" + toGstinAddmap.get("loc"));
		}
		request.put(AspApiConstants.TO_PINCODE, Long.parseLong((String) toGstinAddmap.get("pncd")));
		request.put(AspApiConstants.ACTTO_STATECODE, tostd);
		request.put(AspApiConstants.TO_STATECODE, tostd);

		ewayBillWITransaction.setToTraderName("" + toGstinMap.get("tradeNam"));

		ewayBillWITransaction.setToAddress1(toGstinAddmap.get("flno") + " " + toGstinAddmap.get("bnm") + " "
				+ toGstinAddmap.get("st") + " " + toGstinAddmap.get("loc") + " " + toGstinAddmap.get("bno") + " "
				+ toGstinAddmap.get("dst") + " " + toGstinAddmap.get("stcd") + " " + toGstinAddmap.get("city"));
		ewayBillWITransaction.setToStateCode(tostd);
		ewayBillWITransaction.setActToStateCode(tostd);
		ewayBillWITransaction.setToPincode(Integer.parseInt((String) toGstinAddmap.get("pncd")));

		ewayBillWITransaction.setCreatedOn(NICUtil.getSQLDate());
		ewayBillWITransaction.setCreatedBy(ewayBillWITransaction.getUserId());
	}

	@Override
	@Transactional
	public Object getGeneratedEwayBillList(Map<String, String> request) throws EwayBillApiException {
		logger.info("getGeneratedEwayBillList service start.");
		return eWayBillDao.getGeneratedEwayBillList(request);
	}

	@Override
	@Transactional
	public Object getEwayBillByNumber(Map<String, String> request) throws EwayBillApiException {
		EwayBillWITransaction ewayBillWITransaction = (EwayBillWITransaction) eWayBillDao.getEwayBillByNumber(request);
		return buildEwayBillResponse(ewayBillWITransaction);
	}

	@Override
	@Transactional
	public Object cancelGeneratedEwayBill(Map<String, String> request) throws EwayBillApiException {
		EwayBillWITransaction ewayBillWITransaction = (EwayBillWITransaction) eWayBillDao.getEwayBillByNumber(request);
		if (ewayBillWITransaction != null) {
			if (ewayBillWITransaction.getEwaybillStatus().equals(AspApiConstants.CAN_EWAYBILL)) {
				return getExcObjDesc(getErrorMsg("312") + ewayBillWITransaction.getEwaybillNo());
			} else {
				try {
					EWayBill eWayBill = new EWayBill();
					if (ewayBillWITransaction.getSupplyType().equals("O"))
						eWayBill.setGstin(ewayBillWITransaction.getFromGstin());
					else
						eWayBill.setGstin(ewayBillWITransaction.getToGstin());
					eWayBill.setNic_id(request.get(AspApiConstants.NIC_USER_ID));
					eWayBill.setNicPwd(request.get(AspApiConstants.PWD));
					eWayBill.setAuthUserId(ewayBillWITransaction.getUserId());
					eWayBill.setCancelRmrk(request.get("cancelRmrk"));
					eWayBill.setEwaybillNo(request.get("ewaybillno"));
					NicUserDetails nicUserDetails = eWayBillDao.getWizardNICUserDetails(eWayBill);
					Map<String, String> generateResponse = null;
					if (nicUserDetails != null) {
						nicUserDetails = getNICUserDetails(nicUserDetails, eWayBill);
						generateResponse = generateAuthToken(nicUserDetails);
						// nicUserDetails.setGstin(eWayBill.getGstin());
						if (generateResponse != null) {
							Object object = validateNICUser(generateResponse);
							if (object instanceof Boolean) {
								String appkey = generateResponse.get(AspApiConstants.APP_KEY);
								String authtoken = generateResponse.get(AspApiConstants.AUTH_TOKEN);
								String sek = generateResponse.get(AspApiConstants.SEK);
								byte[] SessionKeyInBytes = AESEncryptionV31.decrypt(sek, appkey.getBytes());
								Map<String, Object> cancelEWayBillRequest = NICUtil.getCancelEWayBillRequest(eWayBill);
								String reqString = NICUtil.getRequestString(cancelEWayBillRequest);
								String request2 = new String(
										AESEncryptionV31.encryptEK(reqString.getBytes(), SessionKeyInBytes));
								nicUserDetails.setData(request2);
								nicUserDetails.setAuthToken(authtoken);
								Map<String, String> req = NICUtil.getCancelEwayBillRequest(nicUserDetails);
								String reqString1 = NICUtil.getRequestString(req);
								Map<String, String> headersMap = NICUtil.getAuthTokenHeaderMap(nicUserDetails);
								Map<String, String> extraParams = new HashMap<String, String>();
								extraParams.put("methodName", "POST");
								String jsonResponseString = WebserviceCallUtil.callWebservice(ewaybillsystem,
										headersMap, reqString1, extraParams);
								Map<String, String> responseMap = NICUtil.getResponseStringToObject(jsonResponseString);
								if (responseMap.get(AspApiConstants.STATUS).equals("0")) {
									byte[] decoded = DatatypeConverter
											.parseBase64Binary(responseMap.get(AspApiConstants.ERROR));
									Map<String, String> errorCode = NICUtil
											.getResponseStringToObject(new String(decoded, "UTF-8"));
									logger.info(
											"cancelEWayBill :: " + AspApiConstants.FAILURE + " :" + jsonResponseString);
									return getExcObjDesc(getErrorMsg(errorCode.get("errorCodes")) + " : "
											+ eWayBill.getEwaybillNo());

								} else {
									Map responseMap3 = NICUtil.getResponseStringToObject(new String(AESEncryptionV31
											.decrypt(responseMap.get(AspApiConstants.JSON_DATA), SessionKeyInBytes)));
									Map<String, Object> response = new LinkedHashMap<>();
									response.put(AspApiConstants.STATUS_CD, AspApiConstants.SU_CD);
									response.put(AspApiConstants.STATUS, AspApiConstants.SUCCESS);
									response.put(AspApiConstants.EWAYBILL_NO,
											responseMap3.get(AspApiConstants.EWAYBILL_NO) + "");
									response.put(AspApiConstants.EWAYBILL_CANCEL_DATE,
											responseMap3.get(AspApiConstants.EWAYBILL_CANCEL_DATE) + "");
									ewayBillWITransaction.setEwaybill_canceldate(
											responseMap3.get(AspApiConstants.EWAYBILL_CANCEL_DATE) + "");
									ewayBillWITransaction.setEwaybillStatus(AspApiConstants.CAN_EWAYBILL);
									ewayBillWITransaction.setCancelRemark(eWayBill.getCancelRmrk());
									addEwayBill(ewayBillWITransaction);
									logger.info("cancelEWayBill :: " + AspApiConstants.SUCCESS + " :" + response);
									return response;
								}

							} else {
								return (Map) object;
							}
						} else {
							return getExcObjDesc(" Generate Auth Token Error: NIC UserId " + eWayBill.getNic_id());
						}

					} else {
						return getExcObjDesc(" Generate Auth Token Error: NIC UserId " + eWayBill.getNic_id());
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				return getExcObjDesc("Invalid EwayBill Number");
			}
		} else {
			return getExcObjDesc("Invalid EwayBill Number");
		}
	}

	@Override
	@Transactional
	public String downloadGeneratedEWayBill(String dataDirectory, Map<String, String> request)
			throws EwayBillApiException {
		logger.error("downloadGeneratedEWayBill : start");
		EwayBillWITransaction ewayBillWITransaction = (EwayBillWITransaction) eWayBillDao.getEwayBillByNumber(request);
		PdfGenUtil pdfGenUtil = new PdfGenUtil();
		String fileName = pdfGenUtil.generatePdfFile(ewayBillWITransaction, dataDirectory);
		logger.error("downloadGeneratedEWayBill : fileName :::: " + fileName);
		logger.error("downloadGeneratedEWayBill : end");
		return fileName;

	}

	@Override
	@Transactional
	public Map<String, String> sendEwayBillPdfToCustomer(String dataDirectory, Map<String, String> request)
			throws EwayBillApiException {
		Map<String, String> responseMap2 = new LinkedHashMap<>();
		String fileName = downloadGeneratedEWayBill(dataDirectory, request);
		String response = sendEwayBillPdfToCustomer(emailFrom, request.get("email_id"), request.get("email_id"),
				"sandeep.jalagam@ril.com", " Eway Bill Pdf Document", emailSmtpHostName, dataDirectory + fileName,
				fileName);
		if (response.equals(GSTNConstants.SUCCESS)) {
			responseMap2.put(AspApiConstants.STATUS_CD, AspApiConstants.SU_CD);
			responseMap2.put(AspApiConstants.STATUS, AspApiConstants.SUCCESS);
			responseMap2.put(AspApiConstants.ERROR_DESC, "");
			responseMap2.put(AspApiConstants.ERROR_CODE, "");
			return responseMap2;
		} else {
			responseMap2.put(AspApiConstants.STATUS_CD, AspApiConstants.ER_CD);
			responseMap2.put(AspApiConstants.STATUS, AspApiConstants.FAILURE);
			responseMap2.put(AspApiConstants.ERROR_DESC, " Failed to send mail please try again later ");
			responseMap2.put(AspApiConstants.ERROR_CODE, "");
			return responseMap2;
		}

	}

	@Override
	@Transactional
	public Object getEwayBillCustomerOnboardedList(Map<String, String> request) throws EwayBillApiException {
		logger.info("getEwayBillCustomerOnboardedList start");
		return eWayBillDao.getEwayBillCustomerOnboardedList(request);
	}

	@Override
	@Transactional
	public List<EwayBillRateMaster> getEwayBillRateList() throws EwayBillApiException {
		return eWayBillDao.getEwayBillRateList();
	}

	@Override
	@Transactional
	public Map<String, String> generateEwaybillV3(EwayBillWITransaction ewayBillWITransaction)
			throws EwayBillApiException {
		Map<String, String> response = new LinkedHashMap<>();
		logger.info("generateEwaybill start");
		try {
			EWayBill eWayBill = new EWayBill();
			if (ewayBillWITransaction.getSupplyType().equalsIgnoreCase("I"))
				eWayBill.setGstin(ewayBillWITransaction.getToGstin());
			else
				eWayBill.setGstin(ewayBillWITransaction.getFromGstin());
			/*
			 * else if (!ewayBillWITransaction.getFromGstin().equals("URP"))
			 * eWayBill.setGstin(ewayBillWITransaction.getFromGstin()); else
			 * eWayBill.setGstin(ewayBillWITransaction.getToGstin());
			 */eWayBill.setNic_id(ewayBillWITransaction.getNicId());
			eWayBill.setNicPwd(ewayBillWITransaction.getNicPassword());
			eWayBill.setAuthUserId(ewayBillWITransaction.getUserId());
			NicUserDetails nicUserDetails = eWayBillDao.getWizardNICUserDetails(eWayBill);
			Map<String, String> generateResponse = null;
			if (nicUserDetails != null) {
				nicUserDetails = getNICUserDetails(nicUserDetails, eWayBill);
				generateResponse = generateAuthToken(nicUserDetails);
				if (generateResponse != null) {
					Object object = validateNICUser(generateResponse);
					if (object instanceof Boolean) {
						String appkey = generateResponse.get(AspApiConstants.APP_KEY);
						String authtoken = generateResponse.get(AspApiConstants.AUTH_TOKEN);
						String sek = generateResponse.get(AspApiConstants.SEK);
						byte[] SessionKeyInBytes = AESEncryptionV31.decrypt(sek, appkey.getBytes());
						Map<String, Object> reqmap = buildEwayBillRequestV3(ewayBillWITransaction);
						String reqString1 = NICUtil.getRequestString(reqmap);
						String request = new String(
								AESEncryptionV31.encryptEK(reqString1.getBytes(), SessionKeyInBytes));
						nicUserDetails.setData(request);
						nicUserDetails.setAuthToken(authtoken);
						Map<String, String> headersMap = NICUtil.getAuthTokenHeaderMap(nicUserDetails);
						Map<String, String> reqMap = NICUtil.getEwayBillRequest(nicUserDetails);
						String reqString = NICUtil.getRequestString(reqMap);
						logger.info("reqString ::::" + reqString1);
						Map<String, String> extraParams = new HashMap<String, String>();
						extraParams.put("methodName", "POST");
						String jsonResponseString = WebserviceCallUtil.callWebservice(ewaybillsystem, headersMap,
								reqString, extraParams);
						Map<String, String> responseMap = NICUtil.getResponseStringToObject(jsonResponseString);
						if (responseMap.get(AspApiConstants.STATUS).equals("0")) {
							byte[] decoded = DatatypeConverter
									.parseBase64Binary(responseMap.get(AspApiConstants.ERROR));
							Map<String, String> errorCode = NICUtil
									.getResponseStringToObject(new String(decoded, "UTF-8"));
							return getExcObjDesc(getErrorMsg(errorCode.get("errorCodes")));

						} else {
							Map responseMap2 = NICUtil.getResponseStringToObject(new String(AESEncryptionV31
									.decrypt(responseMap.get(AspApiConstants.JSON_DATA), SessionKeyInBytes)));
							response.put(AspApiConstants.STATUS_CD, AspApiConstants.SU_CD);
							response.put(AspApiConstants.STATUS, AspApiConstants.SUCCESS);
							response.put(AspApiConstants.EWAYBILL_DATE,
									responseMap2.get(AspApiConstants.EWAYBILL_DATE) + "");
							response.put(AspApiConstants.EWAYBILL_NO, formatDecimal(responseMap2) + "");
							response.put(AspApiConstants.EWAYBILL_VALIDUPTO,
									responseMap2.get(AspApiConstants.EWAYBILL_VALIDUPTO) + "");

							ewayBillWITransaction
									.setEwaybill_date(responseMap2.get(AspApiConstants.EWAYBILL_DATE) + "");
							ewayBillWITransaction.setEwaybillNo(formatDecimal(responseMap2) + "");
							ewayBillWITransaction
									.setEwaybill_valid_upto(responseMap2.get(AspApiConstants.EWAYBILL_VALIDUPTO) + "");
							ewayBillWITransaction.setEwaybillStatus(AspApiConstants.GENEWAYBILL);
							logger.info("generateEwaybill :: SUCCESS ::" + response);
							logger.info("generateEwaybill :: add ::" + eWayBill);
							ewayBillWITransaction.setTxnId("" + headersMap.get(AspApiConstants.TXN));
							addEwayBill(ewayBillWITransaction);
							return response;
						}

					} else {
						logger.info("Generate Eway Bill :: Generate AuthToken ::" + object);
						return (Map) object;
					}

				} else {
					return getExcObjDesc("Eway Bill Error:Generate AuthToken");
				}
			} else {
				return getExcObjDesc("Eway Bill onboarding Error:please complete onboarding process");
			}

		} catch (Exception e) {
			logger.info("generateEwaybill error" + e);
			return getExcObjDesc("Eway Bill Error:Generate AuthToken");
		}
	}

	@Override
	@Transactional
	public Object operation(Map<String, String> inputMap) throws EwayBillApiException {
		return eWayBillDao.operation(inputMap);
	}

	@Override
	@Transactional
	public List<Object[]> getEwayBillsAndCustomerByInvoiceId(Integer id,Integer orgid) throws EwayBillApiException {
		// TODO Auto-generated method stub
		return eWayBillDao.getEwayBillsAndCustomerByInvoiceId(id,orgid);
	}

}
