/**
 * 
 */
package com.reliance.gstn.dao;

import java.util.List;
import java.util.Map;

import com.reliance.gstn.admin.exception.EwayBillApiException;
import com.reliance.gstn.model.EWayBill;
import com.reliance.gstn.model.EwayBillRateMaster;
import com.reliance.gstn.model.EwayBillWITransaction;
import com.reliance.gstn.model.NicUserDetails;

/**
 * @author Nikesh.Bansod
 *
 */
public interface EWayBillDao {

	void addEwayBill(EWayBill e) throws EwayBillApiException;

	void updateEwayBill(EWayBill e) throws EwayBillApiException;

	public NicUserDetails getNICUserDetails(EWayBill e) throws EwayBillApiException;

	public void save(NicUserDetails nicUserDetails) throws EwayBillApiException;

	public List<EWayBill> getEwayBillsByInvoiceId(int id) throws Exception;

	boolean validateEWayBillAgainstOrgId(Integer invoiceId, Integer orgUId) throws Exception;

	public EWayBill getEWayBillDetailsById(Integer id) throws Exception;

	public EWayBill getEwayDetails(EWayBill gstin) throws EwayBillApiException;

	public NicUserDetails getNicDetailsFromGstinAndOrgId(String gstnStateIdInString, Integer orgUId) throws Exception;

	void addEwayBill(EwayBillWITransaction e) throws EwayBillApiException;

	public NicUserDetails getWizardNICUserDetails(EWayBill eWayBill) throws EwayBillApiException;

	public Object getGeneratedEwayBillList(Map<String, String> request) throws EwayBillApiException;

	public Object getEwayBillByNumber(Map<String, String> request) throws EwayBillApiException;

	public Object getEwayBillCustomerOnboardedList(Map<String, String> request) throws EwayBillApiException;

	public List<EwayBillRateMaster> getEwayBillRateList() throws EwayBillApiException;

	public Object operation(Map<String, String> inputMap) throws EwayBillApiException;

	public List<Object[]> getEwayBillsAndCustomerByInvoiceId(Integer id, Integer orgid) throws EwayBillApiException;
}
