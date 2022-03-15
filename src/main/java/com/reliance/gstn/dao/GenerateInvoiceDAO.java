/**
 * 
 */
package com.reliance.gstn.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.reliance.gstn.model.InvoiceCnDnDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.PayloadCnDnDetails;
import com.reliance.gstn.model.PoDetails;

/**
 * @author Nikesh.Bansod
 *
 */
public interface GenerateInvoiceDAO {

	Map<String,String> addGenerateInvoice(InvoiceDetails invoiceDetails) throws Exception;

	Map<String, String> compareInvoiceDate(Timestamp inputDate, String uIdToString);

	List<InvoiceDetails> getInvoiceDetailsByPoDetails(PoDetails poDetail);

	List<InvoiceDetails> getInvoiceDetailsByOrgUId(Integer orgUId) throws Exception;
	
	List<Object[]> getInvoiceDetailByOrgUId(Integer orgUId) throws Exception;

	InvoiceDetails getInvoiceDetailsById(Integer id)  throws Exception;

	String getLatestInvoiceNumber(String pattern, Integer orgUId);

	boolean validateInvoiceAgainstOrgId(Integer invoiceId, Integer orgUId) throws Exception;

	Map<String, String> addCNDN(InvoiceCnDnDetails invoiceCNDNDetails, Integer invoiceId);

	Integer getLatestIterationNo(Integer invoiceId);

	Map<String, String> addInvoiceCnDn(InvoiceDetails invoiceDetails, Integer iterationNo, String invoiceNumber) throws Exception;

	String getLatestCnDnInvoiceNumber(String pattern, Integer orgUId);

	String setDeleteFlagForInvoice(List<PayloadCnDnDetails> cndnInvoiceList, InvoiceDetails invoiceDetail) throws Exception;

	List<Object[]> getDocumentListByDocType(Integer orgUId, String documentType) throws Exception;

	String getCnDnPkIdByCnDnNoAndIterationNo(String cndnNumber, Integer orgUId, String iterationNo);

	String saveRR(InvoiceDetails invoiceDetails);

	String removeServiceLineItems(String removeItemsList);

	String checkInvoiceNumberPresent(String invoiceNumber, Integer orgUId) throws Exception;
}
