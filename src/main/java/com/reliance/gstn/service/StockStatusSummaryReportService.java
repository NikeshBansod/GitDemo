package com.reliance.gstn.service;
import java.util.Map;

public interface StockStatusSummaryReportService {
	
	Map<String, Object> getGoodsByStoreIdAndDate(Integer gstinNo,Integer store_id ,String fDate,String tDate);

	Map<String, Object> getGoodsByStoreIdAndToDate(Integer gstinNo,Integer storeId, String fDate);
	
	Map<String, Object> getUnmovedGoodsByStoreIdAndToDate(Integer gstinNo,Integer store_id ,String fDate,String tDate);

	Map<String, Object> getStockDetailedReport(Integer store_id,Integer product_id,String fdate, String tdate);
	

}
