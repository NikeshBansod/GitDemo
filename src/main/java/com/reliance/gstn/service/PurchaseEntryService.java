package com.reliance.gstn.service;

import java.util.List;
import java.util.Map;

import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.PurchaseEntryDetails;
/**
 * @author @kshay Mohite
 *
 */
public interface PurchaseEntryService {

	String getLatestInvoiceNumber(String string, Integer orgUId);

	Map<String, String> addGeneratePurchaseEntryInvoice(PurchaseEntryDetails purchaseEntryDetails) throws Exception;

	List<Object[]> getPurchaseEntriesDetailByOrgUId(Integer orgUId);

	boolean validateInvoiceAgainstOrgId(Integer purchaseEntryDetailsId, Integer orgUId)  throws Exception;
	
	PurchaseEntryDetails getPurchaseEntryInvoiceDetailsById(Integer id) throws Exception;

	String deletePurchaseEntryInvoice(int purchaseEntryDetailsId) throws Exception;
}
