/**
 * 
 */
package com.reliance.gstn.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.reliance.gstn.model.InvoiceCnDnDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.PoDetails;
import com.reliance.gstn.model.UserMaster;

/**
 * @author Nikesh.Bansod
 *
 */
public interface GenerateInvoiceService {

	Map<String,String> addGenerateInvoice(InvoiceDetails invoiceDetails) throws Exception;

	Map<String, String> compareInvoiceDate(Timestamp convertStringToTimestamp, String uIdToString);
	
	List<InvoiceDetails> getInvoiceDetailsByPoDetails(PoDetails poDetail);

	List<InvoiceDetails> getInvoiceDetailsByOrgUId(Integer orgUId) throws Exception;
	
	List<Object[]> getInvoiceDetailByOrgUId(Integer orgUId) throws Exception;

	InvoiceDetails getInvoiceDetailsById(Integer id)  throws Exception;

	String getLatestInvoiceNumber(String string, Integer orgUId);

	boolean validateInvoiceAgainstOrgId(Integer invoiceId, Integer orgUId) throws Exception;

	Map<String, String> addCNDN(InvoiceCnDnDetails invoiceCNDNDetails, Integer invoiceId);

	Map<String, String> addInvoiceCnDn(InvoiceDetails invoiceDetails, LoginMaster loginMaster, String invoiceNumber, String cnDnFooter) throws Exception;

	Map<String, String> verifyInvoiceCnDn(InvoiceDetails invoiceDetails) throws Exception;

	String getLatestCnDnInvoiceNumber(String string, Integer orgUId);

	String deleteInvoice(LoginMaster loginMaster, InvoiceDetails invoiceDetail)  throws Exception;

	String getTinyUrl(String invNum)throws Exception;

	String fetchInvoiceinXml(InvoiceDetails invoiceDetails, UserMaster user)throws Exception;

	List<Object[]> getDocumentListByDocType(Integer orgUId, String documentType) throws Exception;

	String getCnDnPkIdByCnDnNoAndIterationNo(String cndnNumber, Integer orgUId, String iterationNo);

	String saveRR(InvoiceDetails invoiceDetails);

	String removeServiceLineItems(String ids);

	String checkInvoiceNumberPresent(String invoiceNumber, Integer orgUId) throws Exception;

}
