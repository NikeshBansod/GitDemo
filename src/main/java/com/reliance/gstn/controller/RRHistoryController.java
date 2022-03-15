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
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.InvoiceAdditionalChargeDetailsHistory;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.InvoiceDetailsHistory;
import com.reliance.gstn.model.InvoiceServiceDetails;
import com.reliance.gstn.model.InvoiceServiceDetailsHistory;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.ReviseAndReturnHistory;
import com.reliance.gstn.model.State;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.GSTINDetailsService;
import com.reliance.gstn.service.ReviseAndReturnService;
import com.reliance.gstn.service.StateService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.AmountInWordsModified;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNHistoryUtil;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

@Controller
public class RRHistoryController {
	
	private static final Logger logger = Logger.getLogger(RRHistoryController.class);
	
	
	
	@Autowired
	ReviseAndReturnService reviseAndReturnService;
	
	@Autowired
	private UserMasterService userMasterService;
	
	@Autowired
	private GSTINDetailsService gstinDetailsService;
		
	@Autowired
	private StateService stateService;
	
	@RequestMapping(value = "/showrevisedandreturndetails", method = RequestMethod.GET)
	 public String showRevisedAndReturnDetails(Model model,HttpServletRequest httpRequest) throws Exception {
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		UserMaster user = null;
		try{
		user = userMasterService.getUserMasterById( loginMaster.getuId());//usermaster added -for CNDN page preview from RR
		if(!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
			user = userMasterService.getUserMasterById(user.getReferenceId());
		}
		}catch(Exception e){
			logger.error("Error in:",e);
		}
   	model.addAttribute("userMaster", user);
	 		return PageRedirectConstants.SHOW_REVISE_RETURN_LIST;
	 }
	
	 @RequestMapping(value="/getrevisionandreturndetaillist",method=RequestMethod.POST)
		public @ResponseBody String getRevisionAndReturnDetailList(Model model,HttpServletRequest httpRequest) {
			logger.info("Entry");
			
			
			List<Object[]> revisionAndReturnList = new ArrayList<>();
			try {
				LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
				revisionAndReturnList = reviseAndReturnService.listRevisionAndReturnDetails(loginMaster.getOrgUId());
			} catch (Exception e) {
				logger.error("Error in:",e);
			}
			logger.info("Exit");
			return new Gson().toJson(revisionAndReturnList);
		}
	 
		@RequestMapping(value = {"/getinvoicedetailhistoryforrr"},method=RequestMethod.POST)
		public String getInvoiceDetailsHistoryById(@RequestParam Integer id,@RequestParam Integer iterationNo, HttpServletRequest httpRequest, Model model) {
			logger.info("Entry");
		
			String uri = GSTNUtil.getLastURIPart(httpRequest.getRequestURI()); 
			String pageRedirect = null;
			InvoiceDetailsHistory invoiceDetailsHistory = null;
			GSTINDetails gstinDetails = null;
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
			UserMaster user = null;
			InvoiceDetails invoiceDetails = null;
			State customerStateCode = null;
			Map<String,Map<String,Double>> gstMap = new HashMap<String,Map<String,Double>>();
			
			try {
				boolean isInvoiceAllowed = reviseAndReturnService.validateInvoiceHistoryAgainstOrgId(id,loginMaster.getOrgUId());
				if(isInvoiceAllowed){
					user = userMasterService.getUserMasterById( loginMaster.getuId());
					if(!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
						user = userMasterService.getUserMasterById(user.getReferenceId());
					}
					List<InvoiceDetails>list = reviseAndReturnService.getInvoiceDetailsByIdAndIterationNumber(id,iterationNo);
					if(list.isEmpty()){
						List<InvoiceDetailsHistory>invoiceDetaillistObject = reviseAndReturnService.getInvoiceDetailsHistoryByIdByNativeQuery(id,iterationNo);
						List<InvoiceServiceDetailsHistory>productInfoDetaillistObject = reviseAndReturnService.getProductInfoDetailsHistoryByIdByNativeQuery(id,iterationNo);
						List<InvoiceAdditionalChargeDetailsHistory> addchargesHistorylistObject = reviseAndReturnService.getAdditionalChargesHistory(id,iterationNo);
						
						invoiceDetailsHistory = invoiceDetaillistObject.get(0); //history table dont have primary key so we need to use native query
						invoiceDetailsHistory.setServiceList(productInfoDetaillistObject);
						invoiceDetailsHistory.setAddChargesList(addchargesHistorylistObject);
						
						gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(invoiceDetailsHistory.getGstnStateIdInString(),loginMaster.getPrimaryUserUId());
						customerStateCode = stateService.getStateByStateName(invoiceDetailsHistory.getCustomerDetails().getCustState());
						gstMap = GSTNHistoryUtil.convertListToMap(invoiceDetailsHistory.getServiceList());
						model.addAttribute("invoiceDetails", invoiceDetailsHistory);
						model.addAttribute("amtInWords", AmountInWordsModified.convertNumberToWords(""+invoiceDetailsHistory.getInvoiceValueAfterRoundOff()));
					}else{
						invoiceDetails=list.get(0);
						List<InvoiceServiceDetails>serviceListHistory=invoiceDetails.getServiceList();
						List<InvoiceServiceDetails>serviceListWithIteration = new ArrayList<>();
						
						for(InvoiceServiceDetails sList1 : serviceListHistory){
							if(sList1.getIterationNo() == iterationNo){
								serviceListWithIteration.add(sList1);
							}
						}
						invoiceDetails.setServiceList(serviceListWithIteration);
						model.addAttribute("invoiceDetails", invoiceDetails);
						model.addAttribute("amtInWords", AmountInWordsModified.convertNumberToWords(""+invoiceDetails.getInvoiceValueAfterRoundOff()));
						gstinDetails = gstinDetailsService.getGstinDetailsFromGstinNo(invoiceDetails.getGstnStateIdInString(),loginMaster.getPrimaryUserUId());
						customerStateCode = stateService.getStateByStateName(invoiceDetails.getCustomerDetails().getCustState());
						gstMap = GSTNUtil.convertListToMap(invoiceDetails.getServiceList());
						
					}
				}	
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error in:",e);
			}
			String conditionValue=httpRequest.getParameter("conditionValue");
			model.addAttribute("dash_conditionValue",conditionValue);

			model.addAttribute("gstinDetails", gstinDetails);
			model.addAttribute("userMaster", user);
			model.addAttribute("customerStateCode", customerStateCode.getStateCode());
			model.addAttribute("gstMap", gstMap);
			//String invoiceValue = new BigDecimal(invoiceDetails.getInvoiceValue()).setScale(2, RoundingMode.DOWN).toPlainString();
			
			model.addAttribute("iterationNo", iterationNo);
			model.addAttribute("invoiceid", id);
		
			model.addAttribute("loggedInThrough", loginMaster.getLoggedInThrough());
			if(uri.equals("/getinvoicedetailhistoryforrr")){
			
				pageRedirect = PageRedirectConstants.VIEW_INVOICE_PAGE;
			}
			logger.info("Exit");
			return pageRedirect;
		}
		
		@RequestMapping(value = "/getlatestrrdocumnetforinvoice" ,method =RequestMethod.POST )
		public @ResponseBody String getLatestRRDocumentForInvoice(@RequestParam Integer iterationNo,@RequestParam Integer invoiceid,HttpServletRequest httpRequest){
			logger.info("Entry");
			String latestIterationNo = null;
			try {
					List<InvoiceDetails>list = reviseAndReturnService.getInvoiceDetailsHistoryByIdByNativeQueryForLatestRR(invoiceid);
						 latestIterationNo=String.valueOf(list.get(0).getIterationNo());
				}
			catch(Exception e){
				e.printStackTrace();
				logger.error("Error in:",e);
			}
			logger.info("Exit");
			return new Gson().toJson(latestIterationNo);
			
			
			
		}
	 

}
