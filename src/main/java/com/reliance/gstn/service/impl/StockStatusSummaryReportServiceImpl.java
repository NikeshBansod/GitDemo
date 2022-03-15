package com.reliance.gstn.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.reliance.gstn.service.StockStatusSummaryReportService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.WebserviceCallUtil;

@Service
public class StockStatusSummaryReportServiceImpl implements StockStatusSummaryReportService {
	
	
private static final Logger logger = Logger.getLogger(StockStatusSummaryReportServiceImpl.class);
	
	@Value("${client_id}")
	private String clientid;

	@Value("${secret_key}")
	private String secretkey;

	@Value("${app_code}")
	private String appcode;

	@Value("${ip_usr}")
	private String ipaddress;
	
	@Value("${SERVICE_UNAVAILABLE}")
	private String serviceUnavailable;
	
	@Value("${${env}.getgoodsbystoreidanddate.url}")
	private String getGoodsByStoreIdAndDate;
	
	@Value("${${env}.getgoodsbystoreidandtodate.url}")
	private String getGoodsByStoreIdAndToDate;
	
	@Value("${${env}.getunmovedgoodsbystoreidandtodate.url}")
	private String getUnmovedGoodsByStoreIdAndToDate;
	
	@Value("${${env}.getStockDetailedReport.url}")
	private String getStockDetailedReport;
	
	
	@Override
	public Map<String, Object> getGoodsByStoreIdAndDate(Integer gstinNo,Integer store_id,String fdate, String tdate) {
		logger.info("Entry:");
		String fromDate= GSTNUtil.convertStringDateInddMMyyyyToFormatyyyyMMdd(fdate);
		String toDate= GSTNUtil.convertStringDateInddMMyyyyToFormatyyyyMMdd(tdate);
		
		fromDate = fromDate.concat(GSTNConstants.START_TIME_FORMAT);
		toDate = toDate.concat(GSTNConstants.END_TIME_FORMAT);
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("gstinNo", gstinNo);
		map.put("storeId", store_id);
		map.put("fdate", fromDate);
		map.put("tdate", toDate);
		String body = new Gson().toJson(map);
		//String body = gson.toJson(map); 
		logger.info("Body : " + body);
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap = webServiceCallReport(getGoodsByStoreIdAndDate,body);
		if (responseMap == null) {
			responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_503, serviceUnavailable, GSTNConstants.FAILURE);
		}
		logger.info("Exit");		
		return responseMap;
	}

	@Override
	public Map<String, Object> getGoodsByStoreIdAndToDate(Integer gstinNo,Integer storeId,String tDate) {
		logger.info("Entry:");
		
		String toDate= GSTNUtil.convertStringDateInddMMyyyyToFormatyyyyMMdd(tDate);
		toDate = toDate.concat(GSTNConstants.END_TIME_FORMAT);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("gstinNo", gstinNo);
		map.put("storeId", storeId);
		map.put("tdate", toDate);
		
		String body = new Gson().toJson(map);
		logger.info("Body : " + body);
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap = webServiceCallReport(getGoodsByStoreIdAndToDate,body);
		if (responseMap == null) {
			responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_503, serviceUnavailable, GSTNConstants.FAILURE);
		}
		logger.info("Exit");		
		return responseMap;
	}

	
	@SuppressWarnings("unchecked")
	private Map<String, Object> webServiceCallReport(String apiUrl, String body){
		logger.info("Entry:");
		Map<String, Object> responseMap = new HashMap<>();
		Map<String, String> headersMap = createApiHeader();		
		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		
		String response = WebserviceCallUtil.callWebservice(apiUrl, headersMap, body, extraParams);
		logger.info("Payload Response :: " + response);
		
		Gson gson=new Gson();		
		responseMap=(Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
		
		logger.info("Exit");		
		return responseMap;
	}

	private Map<String, String> createApiHeader() {		
		Map<String, String> headersMap = new HashMap<>();
		headersMap.put("client-id", clientid);
		headersMap.put("secret-key",secretkey);
		headersMap.put("Content-Type", "application/json");
		headersMap.put("app-code",appcode);
		headersMap.put("ip-address", ipaddress);		
		return headersMap;
	}

	@Override
	public Map<String, Object>getUnmovedGoodsByStoreIdAndToDate(Integer gstin,Integer store_id,String fdate, String tdate) {
		logger.info("Entry:");
		String fromDate= GSTNUtil.convertStringDateInddMMyyyyToFormatyyyyMMdd(fdate);
		String toDate= GSTNUtil.convertStringDateInddMMyyyyToFormatyyyyMMdd(tdate);
		
		fromDate = fromDate.concat(GSTNConstants.START_TIME_FORMAT);
		toDate = toDate.concat(GSTNConstants.END_TIME_FORMAT);
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("gstinNo", gstin);
		map.put("storeId", store_id);
		map.put("fdate", fromDate);
		map.put("tdate", toDate);
		
		String body = new Gson().toJson(map);
		logger.info("Body : " + body);
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap = webServiceCallReport(getUnmovedGoodsByStoreIdAndToDate,body);
		if (responseMap == null) {
			responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_503, serviceUnavailable, GSTNConstants.FAILURE);
		}
		logger.info("Exit");		
		return responseMap;
		
	}

	@Override
	public Map<String, Object> getStockDetailedReport(Integer store_id,Integer product_id,String fdate, String tdate) {
		logger.info("Entry:");
		String fromDate= GSTNUtil.convertStringDateInddMMyyyyToFormatyyyyMMdd(fdate);
		String toDate= GSTNUtil.convertStringDateInddMMyyyyToFormatyyyyMMdd(tdate);
		
		fromDate = fromDate.concat(GSTNConstants.START_TIME_FORMAT);
		toDate = toDate.concat(GSTNConstants.END_TIME_FORMAT);
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		//map.put("storeId", store_id);
		map.put("productId", product_id);
		map.put("fdate", fromDate);
		map.put("tdate", toDate);
		
		String body = new Gson().toJson(map);
		logger.info("Body : " + body);
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap = webServiceCallReport(getStockDetailedReport,body);
		if (responseMap == null) {
			responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_503, serviceUnavailable, GSTNConstants.FAILURE);
		}
		logger.info("Exit");		
		return responseMap;
		
	}

	
	
	
	
	
}
