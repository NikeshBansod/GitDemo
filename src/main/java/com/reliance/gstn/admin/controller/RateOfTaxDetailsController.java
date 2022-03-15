/**
 * 
 */
package com.reliance.gstn.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.admin.model.AdditionalChargeRateOfTaxDetails;
import com.reliance.gstn.admin.model.ProductRateOfTaxDetails;
import com.reliance.gstn.admin.model.ServiceRateOfTaxDetails;
import com.reliance.gstn.admin.service.RateOfTaxDetailsService;

/**
 * @author Rupali J
 *
 */
@Controller
public class RateOfTaxDetailsController {
	
	private static final Logger logger = Logger.getLogger(RateOfTaxDetailsController.class);
	
	@Autowired
	public RateOfTaxDetailsService rateOfTaxDetailService;
	
	  @RequestMapping(value="/getProductRateOfTaxDetails",method=RequestMethod.POST)
	  public @ResponseBody String getProductRateOfTaxDetails(Model model,HttpServletRequest httpRequest) {
		  logger.info("Entry");
		  List<ProductRateOfTaxDetails> taxRateDetailsList = new ArrayList<ProductRateOfTaxDetails>();
		  try {
			  taxRateDetailsList =  rateOfTaxDetailService.getTaxRatesInPercentage();
		  } catch (Exception e) {
			  logger.error("Error in:",e);
		  }
		  logger.info("Exit");
		  return new Gson().toJson(taxRateDetailsList);
	  }
	  
	  @RequestMapping(value={"/getServiceRateOfTaxDetails","/idt/getiServiceRateOfTaxDetails"},method=RequestMethod.POST)
	  public @ResponseBody String getServiceRateOfTaxDetails(Model model,HttpServletRequest httpRequest) {
		  logger.info("Entry");
		  List<ServiceRateOfTaxDetails> serviceTaxRateDetailsList = new ArrayList<ServiceRateOfTaxDetails>();
		  try {
			  serviceTaxRateDetailsList =  rateOfTaxDetailService.getServiceTaxRatesInPercentage();
		  } catch (Exception e) {
			  logger.error("Error in:",e);
		  }
		  logger.info("Exit");
		  return new Gson().toJson(serviceTaxRateDetailsList);
	  }
	
	  
	  @RequestMapping(value="/getAdditionalChargeRateOfTaxDetails",method=RequestMethod.POST)
	  public @ResponseBody String getAdditionalChargeRateOfTaxDetails(Model model,HttpServletRequest httpRequest) {
		  logger.info("Entry");
		  List<AdditionalChargeRateOfTaxDetails> serviceTaxRateDetailsList = new ArrayList<AdditionalChargeRateOfTaxDetails>();
		  try {
			  serviceTaxRateDetailsList =  rateOfTaxDetailService.getAdditionalChargeTaxRatesInPercentage();
		  } catch (Exception e) {
			  logger.error("Error in:",e);
		  }
		  logger.info("Exit");
		  return new Gson().toJson(serviceTaxRateDetailsList);
	  }
	  
}
