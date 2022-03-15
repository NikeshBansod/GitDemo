package com.reliance.gstn.service;

import java.util.List;

import com.reliance.gstn.model.CnDnAdditionalDetails;
import com.reliance.gstn.model.Dashboard;
import com.reliance.gstn.model.EWayBill;
import com.reliance.gstn.model.PayloadCnDnDetails;

public interface DashBoardService {

	List<Object[]> getGeneratedInvoiceList(Dashboard dashboard, Integer getuId,
			String getInvType, String appCode);

	long getInvoiceCount(Dashboard dashboard, Integer uid, String appCode) throws Exception;

	long getCnDnNote(Dashboard dashboard, Integer getuId) throws Exception;

	long getGeneratedInvoiceCount(Dashboard dashboard, Integer getuId) throws Exception;

	List<PayloadCnDnDetails> getProductDetailsForDash(String cndnNumber);

	List<CnDnAdditionalDetails> getCNDNdetails(Integer cId);

	long getInvoiceEWaybillCount(Dashboard dashboard, Integer orgUId);


	List<EWayBill> getInvoiceEwayBillWITransactionDetails(String ewaybillNo);

	

	
}
