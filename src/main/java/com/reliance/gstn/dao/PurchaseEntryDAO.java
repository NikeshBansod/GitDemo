package com.reliance.gstn.dao;

import java.util.List;
import java.util.Map;

import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.PurchaseEntryDetails;
/**
 * @author @kshay Mohite
 *
 */
public interface PurchaseEntryDAO {

	String getLatestInvoiceNumber(String pattern, Integer orgUId);

	Map<String, String> addGeneratePurchaseEntryInvoice(PurchaseEntryDetails purchaseEntryDetails) throws Exception;

	List<Object[]> getPurchaseEntriesDetailByOrgUId(Integer orgUId);

	boolean validateInvoiceAgainstOrgId(Integer purchaseEntryDetailsId, Integer orgUId) throws Exception;

	PurchaseEntryDetails getPurchaseEntryInvoiceDetailsById(Integer id) throws Exception;

	String deletePurchaseEntryInvoice(int purchaseEntryDetailsId) throws Exception;

}
