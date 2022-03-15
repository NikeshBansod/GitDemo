/**
 * 
 */
package com.reliance.gstn.controller;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;

import com.google.gson.Gson;
import com.reliance.gstn.admin.exception.EwayBillApiException;
import com.reliance.gstn.model.EWayBill;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.NicUserDetails;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.EWayBillService;
import com.reliance.gstn.service.GSTINDetailsService;
import com.reliance.gstn.service.GenerateInvoiceService;
import com.reliance.gstn.service.UnitOfMeasurementService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.AspApiConstants;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Nikesh.Bansod
 *
 */
@Controller
public class EWayBillController {
	private static final Logger logger = Logger.getLogger(EWayBillController.class);

	@Autowired
	private EWayBillService eWayBillService;

	@Autowired
	GenerateInvoiceService generateInvoiceService;

	@Autowired
	private UserMasterService userMasterService;

	@Autowired
	public UnitOfMeasurementService unitOfMeasurementService;

	@Autowired
	private GSTINDetailsService gstinDetailsService;

	@Value("${${env}.PATH_FOR_INVOICE_PDF}")
	private String downloadEWayBillPdfPath;

	@RequestMapping(value = { "/generateEWayBill" }, method = RequestMethod.POST)
	public String getInvoiceDetailsById(@RequestParam Integer id, HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.GET_EWAY_BILL_PAGE;

		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		InvoiceDetails invoiceDetails = null;
		NicUserDetails nicUserDetails = null;
		try {
			boolean isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(id, loginMaster.getOrgUId());
			if (isInvoiceAllowed) {
				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(id);
				nicUserDetails = eWayBillService.getNicDetailsFromGstinAndOrgId(invoiceDetails.getGstnStateIdInString(),
						loginMaster.getOrgUId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:", e);
		}

		model.addAttribute("invoiceId", id);
		model.addAttribute("gstn", invoiceDetails.getGstnStateIdInString());
		model.addAttribute("nicUserDetails", nicUserDetails);
		logger.info("Exit");
		return pageRedirect;
	}

	@RequestMapping(value = { "/testEWayBill" }, method = RequestMethod.GET)
	public @ResponseBody String test(HttpServletRequest httpRequest) {
		logger.info("Entry");
		String status = "Failure";
		logger.info("Exit");
		return status;
	}

	@RequestMapping(value = { "/getEWayBills" }, method = RequestMethod.POST)
	public String getCnDnById(@RequestParam Integer id, HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.PRE_EWAY_BILL_PAGE;
		InvoiceDetails invoiceDetails = null;
		List<EWayBill> ewaybillList = null;
		GSTINDetails gstinDetails = null;

		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		String response = GSTNConstants.NOT_ALLOWED_ACCESS;
		UserMaster user = null;
		boolean isInvoiceAllowed = false;
		try {
			isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(id, loginMaster.getOrgUId());
			if (isInvoiceAllowed) {
				user = userMasterService.getUserMasterById(loginMaster.getuId());
				if (!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)) {
					user = userMasterService.getUserMasterById(user.getReferenceId());
				}

				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(id);
				ewaybillList = eWayBillService.getEwayBillsByInvoiceId(id);
				gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(invoiceDetails.getGstnStateIdInString(),
						loginMaster.getPrimaryUserUId());
				response = GSTNConstants.ALLOWED_ACCESS;
			} else {
				invoiceDetails = new InvoiceDetails();
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:", e);
		}
		model.addAttribute("loggedInThrough", loginMaster.getLoggedInThrough());
		model.addAttribute("isInvoiceAllowed", isInvoiceAllowed);
		model.addAttribute("ewaybillList", ewaybillList);
		model.addAttribute("userMaster", user);
		model.addAttribute("invoiceDetails", invoiceDetails);
		model.addAttribute("gstinDetails", gstinDetails);
	//	model.addAttribute(GSTNConstants.RESPONSE, response);

		logger.info("Exit");
		return pageRedirect;
	}

	@RequestMapping(value = "/getEwayBillDetailsInPreview", method = RequestMethod.POST)
	public @ResponseBody String getEwayBillDetailsInPreview(@RequestParam("id") Integer eWayBillId,
			HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		EWayBill eWayBillDetail = null;
		boolean isInvoiceAllowed = false;
		try {
			LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
			isInvoiceAllowed = eWayBillService.validateEWayBillAgainstOrgId(eWayBillId, loginMaster.getOrgUId());
			if (isInvoiceAllowed) {
				eWayBillDetail = eWayBillService.getEWayBillDetailsById(eWayBillId);
				eWayBillDetail.setRenderData(GSTNConstants.ALLOWED_ACCESS);
			} else {
				eWayBillDetail.setRenderData(GSTNConstants.NOT_ALLOWED_ACCESS);
			}

		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(eWayBillDetail);
	}

	@RequestMapping(value = { "/cancelEWayBillPage" }, method = RequestMethod.POST)
	public String cancelEWayBillPage(@RequestParam("id") Integer id, @RequestParam("ewayBillId") Integer ewaybillNo,
			HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.GET_CANCEL_EWAY_BILL_PAGE;

		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		InvoiceDetails invoiceDetails = null;
		NicUserDetails nicUserDetails = null;
		EWayBill eWayBill = null;

		try {
			boolean isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(id, loginMaster.getOrgUId());
			if (isInvoiceAllowed) {
				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(id);
				eWayBill = eWayBillService.getEWayBillDetailsById(ewaybillNo);
				nicUserDetails = eWayBillService.getNicDetailsFromGstinAndOrgId(invoiceDetails.getGstnStateIdInString(),
						loginMaster.getOrgUId());

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:", e);
		}

		model.addAttribute("invoiceId", id);
		model.addAttribute("gstn", invoiceDetails.getGstnStateIdInString());
		model.addAttribute("nicUserDetails", nicUserDetails);
		model.addAttribute("eWayBill", eWayBill.getEwaybillNo());
		model.addAttribute("eWayBillId", eWayBill.getId());
		logger.info("Exit");
		return pageRedirect;
	}

	@RequestMapping(value = { "/addEwayBillPost" }, method = RequestMethod.POST)
	public @ResponseBody String addEWayBill(@RequestBody EWayBill eWayBill, HttpServletRequest httpRequest) {
		logger.info("addEWayBill Entry");
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		Map<String, String> nicUserDetails = eWayBillService.generateEwaybill(eWayBill, loginMaster);
		logger.info("addEWayBill response :" + nicUserDetails);
		logger.info("addEWayBill Exit ");
		return new Gson().toJson(nicUserDetails);
	}

	@RequestMapping(value = { "/eWayBillOnBoarding" }, method = RequestMethod.POST)
	public @ResponseBody String eWayBillOnBoarding(@RequestBody EWayBill eWayBill, HttpServletRequest httpRequest) {
		logger.info("eWayBillOnBoarding Entry");
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		Object nicUserDetails = eWayBillService.eWayBillOnBoarding(eWayBill, loginMaster);
		logger.info("eWayBillOnBoarding response :" + nicUserDetails);
		logger.info("eWayBillOnBoarding Exit");
		return new Gson().toJson(nicUserDetails);

	}

	@RequestMapping(value = "/sendEwayBillPdf", method = { RequestMethod.POST })
	public @ResponseBody Map sendEwayBillPdf(@RequestBody EWayBill eWayBill, HttpServletRequest request,
			HttpServletRequest httpRequest, HttpServletResponse response) {
		logger.info("downloadEWayBill Entry");
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		File dir = new File(downloadEWayBillPdfPath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		dir = new File(downloadEWayBillPdfPath);
		if (dir.exists()) {
			return eWayBillService.sendEwayBillPdfToCustomer(downloadEWayBillPdfPath + File.separator, eWayBill,
					loginMaster);
		} else {
			Map<String, String> responseMap2 = new LinkedHashMap<>();
			responseMap2.put(AspApiConstants.STATUS_CD, AspApiConstants.ER_CD);
			responseMap2.put(AspApiConstants.STATUS, AspApiConstants.FAILURE);
			responseMap2.put(AspApiConstants.ERROR_DESC, " Failed to send mail please try again later ");
			responseMap2.put(AspApiConstants.ERROR_CODE, "");
			responseMap2.put(AspApiConstants.NIC_USER_ID, eWayBill.getNic_id());
			return responseMap2;
		}
	}

	@RequestMapping(value = "/downloadEWayBill", method = { RequestMethod.POST })
	public void downloadEWayBill(@ModelAttribute("eWayBill") EWayBill eWayBill, HttpServletRequest request,
			HttpServletRequest httpRequest, HttpServletResponse response) {
		logger.info("downloadEWayBill Entry");
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		File dir = new File(downloadEWayBillPdfPath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		dir = new File(downloadEWayBillPdfPath);
		if (dir.exists()) {
			String fileName = eWayBillService.downloadEWayBill(downloadEWayBillPdfPath + "/", eWayBill, loginMaster);
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

		logger.info("downloadEWayBill Exit");
	}

	@RequestMapping(value = { "/cancelEWayBill" }, method = RequestMethod.POST)
	public @ResponseBody Object cancelEWayBill(@RequestBody EWayBill eWayBill, HttpServletRequest httpRequest) {
		logger.info("cancelEWayBill Entry");
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		Object nicUserDetails = eWayBillService.cancelEWayBill(eWayBill, loginMaster);
		logger.info("cancelEWayBill Exit");
		return nicUserDetails;
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
			exObject.put(AspApiConstants.ERROR_CODE, AspApiConstants.EWAYBILL_CONNECTION_ERROR);
			exObject.put(AspApiConstants.ERROR_DESC, " Connection Error: Unable to connect to destination ");
			exObject.put(AspApiConstants.ERROR_GRP, AspApiConstants.EWAYBILL_CONNECTION_ERROR);
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
		exObject.put(AspApiConstants.ERROR_CODE, AspApiConstants.EWAYBILL_ERROR);
		return exObject;
	}
	
	@RequestMapping(value = "/getEwayBillList", method = RequestMethod.POST)
	public @ResponseBody String getEwayBillList(@RequestParam("id") Integer id, HttpServletRequest httpRequest) {		
		logger.info("Entry");
		List<EWayBill> ewaybillList = null;
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		try {
			boolean isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(id, loginMaster.getOrgUId());
			if (isInvoiceAllowed) {
				ewaybillList = eWayBillService.getEwayBillsByInvoiceId(id);
			}
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(ewaybillList);
	}
	
	@RequestMapping(value = "/getEwayBillListAndCutomer", method = RequestMethod.POST)
	public @ResponseBody String getEwayBillListAndCutomer(@RequestParam("id") Integer id, HttpServletRequest httpRequest) {		
		logger.info("Entry");
		List<Object[]> ewaybillList = null;
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		try {
			boolean isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(id, loginMaster.getOrgUId());
			if (isInvoiceAllowed) {
				ewaybillList = eWayBillService.getEwayBillsAndCustomerByInvoiceId(id,loginMaster.getOrgUId());
			}
		} catch (Exception e) {
			logger.error("Error in:", e);
		}
		logger.info("Exit");
		return new Gson().toJson(ewaybillList);
	}
	

}
