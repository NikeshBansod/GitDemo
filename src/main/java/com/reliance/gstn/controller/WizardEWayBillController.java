package com.reliance.gstn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.reliance.gstn.model.EWayBill;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.NicUserDetails;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.EWayBillService;
import com.reliance.gstn.service.GSTINDetailsService;
import com.reliance.gstn.service.GenerateInvoiceService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

@Controller
public class WizardEWayBillController {

	private static final Logger logger = Logger.getLogger(EWayBillController.class);

	@Autowired
	GenerateInvoiceService generateInvoiceService;

	@Autowired
	private EWayBillService eWayBillService;

	@Autowired
	private UserMasterService userMasterService;

	@Autowired
	private GSTINDetailsService gstinDetailsService;


	@RequestMapping(value = {"/wgenerateEWayBill" }, method = RequestMethod.POST)
	public String getInvoiceDetailsById(@RequestParam Integer id, HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.WIZARD_GET_EWAY_BILL_PAGE;		
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
			//e.printStackTrace();
			logger.error("Error in:", e);
		}
		model.addAttribute("invoiceId", id);
		model.addAttribute("gstn", invoiceDetails.getGstnStateIdInString());
		model.addAttribute("nicUserDetails", nicUserDetails);
		logger.info("Exit");
		return pageRedirect;
	}

	@RequestMapping(value = {"/wgetEWayBills"}, method = RequestMethod.POST)
	public String getCnDnById(@RequestParam Integer id, HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.WIZARD_PRE_EWAY_BILL_PAGE;
		InvoiceDetails invoiceDetails = null;
		List<EWayBill> ewaybillList = null;
		GSTINDetails gstinDetails = null;
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		String response = GSTNConstants.NOT_ALLOWED_ACCESS;
		UserMaster user = null;
		try {
			boolean isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(id, loginMaster.getOrgUId());
			if (isInvoiceAllowed) {
				user = userMasterService.getUserMasterById(loginMaster.getuId());
				if (!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)) {
					user = userMasterService.getUserMasterById(user.getReferenceId());
				}

				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(id);
				ewaybillList = eWayBillService.getEwayBillsByInvoiceId(id);
				gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(invoiceDetails.getGstnStateIdInString(),loginMaster.getPrimaryUserUId());
				response = GSTNConstants.ALLOWED_ACCESS;
			} else {
				invoiceDetails = new InvoiceDetails();
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("Error in:", e);
		}
		model.addAttribute("ewaybillList", ewaybillList);
		model.addAttribute("userMaster", user);
		model.addAttribute("invoiceDetails", invoiceDetails);
		model.addAttribute("gstinDetails", gstinDetails);
		model.addAttribute(GSTNConstants.RESPONSE, response);
		logger.info("Exit");
		return pageRedirect;
	}

	@RequestMapping(value = { "/wcancelEWayBillPage" }, method = RequestMethod.POST)
	public String cancelEWayBillPage(@RequestParam("id") Integer id, @RequestParam("ewayBillId") Integer ewaybillNo,
			HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.WIZARD_GET_CANCEL_EWAY_BILL_PAGE;
		LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
		InvoiceDetails invoiceDetails = null;
		NicUserDetails nicUserDetails = null;
		EWayBill eWayBill = null;
		try {
			boolean isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(id, loginMaster.getOrgUId());
			if (isInvoiceAllowed) {
				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(id);
				eWayBill = eWayBillService.getEWayBillDetailsById(ewaybillNo);
				nicUserDetails = eWayBillService.getNicDetailsFromGstinAndOrgId(invoiceDetails.getGstnStateIdInString(),loginMaster.getOrgUId());
			}
		} catch (Exception e) {
			//e.printStackTrace();
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

}
