/**
 * 
 */
package com.reliance.gstn.service;

import java.util.List;
import java.util.Map;

import com.reliance.gstn.admin.exception.EwayBillApiException;
import com.reliance.gstn.model.EWayBill;
import com.reliance.gstn.model.EwayBillRateMaster;
import com.reliance.gstn.model.EwayBillWITransaction;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.NicUserDetails;

/**
 * @author Nikesh.Bansod
 *
 */
public interface EWayBillService {

	void addEwayBill(EWayBill e) throws Exception;

	public Map<String, String> generateEwaybill(EWayBill eWayBill, LoginMaster loginMaster) throws EwayBillApiException;

	public String downloadEWayBill(String dataDirectory, EWayBill eWayBill, LoginMaster loginMaster)
			throws EwayBillApiException;

	public Map<String, String> eWayBillOnBoarding(EWayBill eWayBill, LoginMaster loginMaster)
			throws EwayBillApiException;

	public List<EWayBill> getEwayBillsByInvoiceId(int id) throws Exception;

	boolean validateEWayBillAgainstOrgId(Integer invoiceId, Integer orgUId) throws Exception;

	public EWayBill getEWayBillDetailsById(Integer id) throws Exception;

	public NicUserDetails getNicDetailsFromGstinAndOrgId(String gstnStateIdInString, Integer orgUId) throws Exception;

	public Map<String, String> cancelEWayBill(EWayBill eWayBill, LoginMaster loginMaster) throws EwayBillApiException;

	public Map<String, String> sendEwayBillPdfToCustomer(String dataDirectory, EWayBill eWayBill,
			LoginMaster loginMaster) throws EwayBillApiException;

	public Map<String, String> sendEwayBillPdfToCustomer(String dataDirectory, Map<String, String> request)
			throws EwayBillApiException;

	public Map<String, String> eWayBillOnBoarding(Map<String, Object> responseMap,
			Map<String, String> customerbonboarding) throws EwayBillApiException;

	public Map<String, String> generateEwaybill(EwayBillWITransaction ewayBillWITransaction)
			throws EwayBillApiException;

	public Object getGeneratedEwayBillList(Map<String, String> request) throws EwayBillApiException;

	public Object getEwayBillByNumber(Map<String, String> request) throws EwayBillApiException;

	public Object cancelGeneratedEwayBill(Map<String, String> request) throws EwayBillApiException;

	public String downloadGeneratedEWayBill(String dataDirectory, Map<String, String> request)
			throws EwayBillApiException;

	public Object getEwayBillCustomerOnboardedList(Map<String, String> request) throws EwayBillApiException;

	public List<EwayBillRateMaster> getEwayBillRateList() throws EwayBillApiException;

	public Map<String, String> generateEwaybillV3(EwayBillWITransaction ewayBillWITransaction)
			throws EwayBillApiException;

	public Object operation(Map<String, String> inputMap) throws EwayBillApiException;

	public List<Object[]> getEwayBillsAndCustomerByInvoiceId(Integer id, Integer orgid) throws EwayBillApiException;

}
