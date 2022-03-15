package com.reliance.gstn.controller;

import java.util.HashMap;
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
import com.reliance.gstn.service.StockStatusSummaryReportService;
import com.reliance.gstn.util.PageRedirectConstants;

@Controller
public class StockStatusSummaryReportController {
	
	@Autowired
	StockStatusSummaryReportService stockstatussummaryreportservice;
	private static final Logger logger = Logger.getLogger(StockStatusSummaryReportController.class);
	
	
	@RequestMapping(value = {"/unmoveditems"}, method = RequestMethod.GET)
	public String getUnmovedItemsPage(Model model, HttpServletRequest httpRequest){
		return PageRedirectConstants.UNMOVED_ITEMS;
	}

	@RequestMapping(value = {"/stocksummaryreport"}, method = RequestMethod.GET)
	public String getStockSummaryReportPage(Model model, HttpServletRequest httpRequest){
		return PageRedirectConstants.STOCK_SUMMARY_REPORT_PAGE;
	}
	
	
	@RequestMapping(value = "/getgoodsbystoreidanddate" , method = RequestMethod.POST)
	public @ResponseBody String showGoodsByStoreIdAndDate(@RequestParam("gstinNo") Integer gstinNo,@RequestParam("storeId") Integer storeId,@RequestParam("fDate") String fDate,@RequestParam("tDate") String tDate, HttpServletRequest httpRequest){
		logger.info("Entry");
		Map<String,Object> responseMap = new HashMap<>();
		try {
			responseMap=stockstatussummaryreportservice.getGoodsByStoreIdAndDate(gstinNo,storeId, fDate, tDate);
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(responseMap);		
	}
	
	@RequestMapping(value = "/getunmovedgoodsbystoreidandtodate" , method = RequestMethod.POST)
	public @ResponseBody String showUnmovedGoodsByStoreIdAndDate(@RequestParam("gstin") Integer gstin,@RequestParam("storeId") Integer storeId,@RequestParam("fDate") String fDate,@RequestParam("tDate") String tDate, HttpServletRequest httpRequest){
		logger.info("Entry");
		Map<String,Object> responseMap = new HashMap<>();
		try {
			responseMap=stockstatussummaryreportservice.getUnmovedGoodsByStoreIdAndToDate(gstin,storeId, fDate, tDate);
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(responseMap);		
	}
	
	
	@RequestMapping(value = {"/negetivestocks"}, method = RequestMethod.GET)
	public String getNegetiveStockPage(Model model, HttpServletRequest httpRequest){
		return PageRedirectConstants.NEGETIVE_STOCKS;
	}
	
	@RequestMapping(value = {"/balancecheckreport"}, method = RequestMethod.GET)
	public String getBalanceCheckPage(Model model, HttpServletRequest httpRequest){
		return PageRedirectConstants.BALANCE_CHECK_REPORT_PAGE;
	}
	
	@RequestMapping(value = "/getgoodsbystoreidandtodate" , method = RequestMethod.POST)
	public @ResponseBody String showGoodsByStoreIdAndToDate(@RequestParam("gstinNo") Integer gstinNo,@RequestParam("storeId") Integer storeId,@RequestParam("tDate") String tDate, HttpServletRequest httpRequest){
		logger.info("Entry");
		Map<String,Object> responseMap = new HashMap<>();
		try {
			responseMap=stockstatussummaryreportservice.getGoodsByStoreIdAndToDate(gstinNo,storeId, tDate);
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(responseMap);		
	}

	@RequestMapping(value = "/getstockdetailedreport" , method = RequestMethod.POST)
	public @ResponseBody String getStockDetailedReport(@RequestParam("storeId") Integer storeId,@RequestParam("productId") Integer productId ,@RequestParam("fDate") String fDate,@RequestParam("tDate") String tDate, HttpServletRequest httpRequest){
		logger.info("Entry");
		Map<String,Object> responseMap = new HashMap<>();
		try {
			responseMap=stockstatussummaryreportservice.getStockDetailedReport(storeId,productId,fDate,tDate);
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(responseMap);		
	}
	
	@RequestMapping(value = {"/getstockdetailedreportpage"}, method = RequestMethod.POST)
	public String getStockDetailedReportPage(Model model, @RequestParam("product_id") Integer product_id,@RequestParam("gstin") Integer gstin,@RequestParam("location") Integer location,@RequestParam("fromDate") String fromDate,@RequestParam("toDate") String toDate,@RequestParam("currentOpeningStock") Integer currentOpeningStock , HttpServletRequest httpRequest){
		model.addAttribute("storeId", location);
		model.addAttribute("gstin", gstin);
		model.addAttribute("productId", product_id);
		model.addAttribute("fDate", fromDate);
		model.addAttribute("tDate", toDate);
		model.addAttribute("currentOpeningStock", currentOpeningStock);
		return PageRedirectConstants.STOCK_DETAIL_REPORT_PAGE;
	}
	@RequestMapping(value = {"/backtostocksummaryreport"}, method = RequestMethod.POST)
	public String getBackToStockSummaryReportPage(Model model,@RequestParam("gstin") Integer gstin,@RequestParam("location") Integer location,@RequestParam("fromDate") String fromDate,@RequestParam("toDate") String toDate,HttpServletRequest httpRequest){
		model.addAttribute("storeId", location);
		model.addAttribute("gstin", gstin);
		model.addAttribute("fDate", fromDate);
		model.addAttribute("tDate", toDate);
		model.addAttribute("traversFrom", "backData");
		return PageRedirectConstants.STOCK_SUMMARY_REPORT_PAGE;
	}
	
	
}
