package com.reliance.gstn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.PurchaseEntryDetails;
import com.reliance.gstn.model.State;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.GSTINDetailsService;
import com.reliance.gstn.service.PurchaseEntryService;
import com.reliance.gstn.service.StateService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.AmountInWords;
import com.reliance.gstn.util.AmountInWordsModified;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;
/**
 * @author @kshay Mohite
 *
 */
@Controller
public class WizardPurchaseEntryInvoiceController {

	private static final Logger logger = Logger.getLogger(WizardPurchaseEntryInvoiceController.class);

	@Autowired
	private PurchaseEntryService purchaseEntryService;

	@Autowired
	private UserMasterService userMasterService;

	@Autowired
	StateService stateService;

	@Autowired
	private GSTINDetailsService gstinDetailsService;

	@Autowired
	private TaxCalculationController taxCalculation;
		
	
	@RequestMapping(value = "/wgetmypurchaseentrypage", method = RequestMethod.GET)
	public String wgetPurchaseEntryInvoiceGrid(Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		logger.info("Exit");
		return PageRedirectConstants.WIZARD_GET_MY_PURCHASE_ENTRIES_PAGE;
	}

	@RequestMapping(value = {"/wgetpurchaseentryinvoicedetail"},method=RequestMethod.POST)
	public String getInvoiceDetailsById(@RequestParam Integer id, HttpServletRequest httpRequest, Model model) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.WIZARD_VIEW_PURCHASE_ENTRY_INVOICE_PAGE;
		PurchaseEntryDetails purchaseEntryDetails = null;
		GSTINDetails gstinDetails = null;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
		UserMaster user = null;
		Map<String,Map<String,Double>> gstMap = new HashMap<String,Map<String,Double>>();
		String supplierCompleteAddress = null;
		State state = new State();
		String supplierStateCode = "";		
		try {
			boolean isInvoiceAllowed = purchaseEntryService.validateInvoiceAgainstOrgId(id,loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				user = userMasterService.getUserMasterById( loginMaster.getuId());
				if(!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
					user = userMasterService.getUserMasterById(user.getReferenceId());
				}
				/*if(user.getOrganizationMaster().getState() != null){
					state = stateService.getStateByStateName(user.getOrganizationMaster().getState());	
					if(state != null){
						user.getOrganizationMaster().setStateCode(state.getStateCode());
					}
				}*/
				
				purchaseEntryDetails = purchaseEntryService.getPurchaseEntryInvoiceDetailsById(id);
				if(purchaseEntryDetails.getSupplierAddress().isEmpty() || purchaseEntryDetails.getSupplierAddress() == null ){
					supplierCompleteAddress = purchaseEntryDetails.getSupplierState()+" [ "+purchaseEntryDetails.getSupplierStateCodeId()+" ]"
							+" , "+purchaseEntryDetails.getSupplierPincode();
				}else{
					supplierCompleteAddress = purchaseEntryDetails.getSupplierAddress()+" , "+purchaseEntryDetails.getSupplierState()
							+" [ "+purchaseEntryDetails.getSupplierStateCodeId()+" ]"+" , "+purchaseEntryDetails.getSupplierPincode();
				}				
				if(purchaseEntryDetails.getSupplierGstin() == null || purchaseEntryDetails.getSupplierGstin().isEmpty()){
					purchaseEntryDetails.setSupplierGstin("NA");
				}
				gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(purchaseEntryDetails.getGstnStateIdInString(),loginMaster.getPrimaryUserUId());
				state = stateService.getStateByStateName(gstinDetails.getGstinAddressMapping().getState());	
				if(state.getStateCode() != null){
					supplierStateCode = state.getStateCode();
				}
				gstMap = GSTNUtil.convertListToMapForPurchaseEntry(purchaseEntryDetails.getServiceList());
			}	
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		model.addAttribute("purchaseEntryDetails", purchaseEntryDetails);
		model.addAttribute("supplierCompleteAddress", supplierCompleteAddress);
		model.addAttribute("gstinDetails", gstinDetails);
		model.addAttribute("userMaster", user);
		model.addAttribute("gstMap", gstMap);
		model.addAttribute("amtInWords", AmountInWordsModified.convertNumberToWords(""+purchaseEntryDetails.getInvoiceValueAfterRoundOff()));
		model.addAttribute("supplierStateCode", supplierStateCode);
		logger.info("Exit");
		return pageRedirect;
	}

	@RequestMapping(value = "/wgeneratepurchaseentryinvoice", method = RequestMethod.GET)
	public String wgeneratePurchaseEntry(Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String pageRedirect = null;
		boolean isGstinPresent = false;
		State state = new State();
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
			UserMaster user = userMasterService.getUserMasterById( loginMaster.getuId());
			if(user.getOrganizationMaster().getState() != null){
				state = stateService.getStateByStateName(user.getOrganizationMaster().getState());	
				if(state != null){
					user.getOrganizationMaster().setStateCode(state.getStateCode());
				}
			}
			List<GSTINDetails> gstinList = new ArrayList<GSTINDetails>();
			//check if gstin is present - Start
			//if user is primary redirect to add gstin page
			//if user is secondary show message Ask your concerned primary member to map gstin to your account
			if(loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
				gstinList =  gstinDetailsService.listGstinDetails(loginMaster.getuId());
			}else{
				/*String gstinUserMapped = gstinUserMappingService.getGSTINUserMappingByReferenceUserId(loginMaster.getuId());
				if(gstinUserMapped != null){
					gstinList = gstinDetailsService.getGstinDetailsByUniqueIds(gstinUserMapped);
				}*/
				gstinList = gstinDetailsService.getGstinDetailsMappedForSecondaryUser(loginMaster.getuId());
				user = userMasterService.getUserMasterById(user.getReferenceId());
			}
			//check if gstin is present - End
			
			if(gstinList != null && gstinList.size() > 0){
				isGstinPresent = true;
			}			
			if(isGstinPresent && (loginMaster.getTermsConditionsFlag() != null)){
				if(loginMaster.getTermsConditionsFlag().toUpperCase().equals("Y")){
					pageRedirect = PageRedirectConstants.WIZARD_GENERATE_PURCHASE_ENTRY_INVOICE_PAGE;
				}else{
					pageRedirect = PageRedirectConstants.WIZARD_GENERATE_PURCHASE_ENTRY_INVOICE_EXCEPTION_PAGE;
				}				
			}else{
				pageRedirect = PageRedirectConstants.WIZARD_GENERATE_PURCHASE_ENTRY_INVOICE_EXCEPTION_PAGE;
			}			
			model.addAttribute("userDetails", user);
			model.addAttribute("isGstinPresent", isGstinPresent);
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return pageRedirect;
	}

	
}
