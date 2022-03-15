package com.reliance.gstn.dao;

import java.util.List;

import com.reliance.gstn.model.CnDnAdditionalDetails;
import com.reliance.gstn.model.Dashboard;
import com.reliance.gstn.model.EWayBill;
import com.reliance.gstn.model.EwayBillWIItem;
import com.reliance.gstn.model.PayloadCnDnDetails;

public interface DashboardDAO {
	
	
	long getInvoiceCount(Dashboard dashboard, Integer uID, String appCode) throws Exception;

	long getCnDnNote(Dashboard dashboard, Integer uID) throws Exception;

	long getGeneratedInvoiceCount(Dashboard dashboard, Integer uID) throws Exception;

	
	List<Object[]> getGeneratedInvoiceList(Dashboard dashboard,
			Integer uID,String getInvType, String appCode);

	List<PayloadCnDnDetails> getProductDetailsForDash(String cndnNumber);

	List<CnDnAdditionalDetails> getCNDNdetails(Integer cId);

	long getInvoiceEWaybillCount(Dashboard dashboard, Integer orgUId);

	
List<EwayBillWIItem> getewayBillWIItemDetails(int ewayBillWITransId);

	List<EWayBill> getInvoiceEwayBillWITransactionDetails(String ewaybillNo);

	

}
