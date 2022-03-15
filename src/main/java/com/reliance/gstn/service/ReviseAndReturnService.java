/**
 * 
 */
package com.reliance.gstn.service;

import java.util.List;

import com.reliance.gstn.model.InvoiceAdditionalChargeDetails;
import com.reliance.gstn.model.InvoiceAdditionalChargeDetailsHistory;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.InvoiceDetailsHistory;
import com.reliance.gstn.model.InvoiceServiceDetails;
import com.reliance.gstn.model.InvoiceServiceDetailsHistory;
import com.reliance.gstn.model.ReviseAndReturn;
import com.reliance.gstn.model.ReviseAndReturnHistory;

/**
 * @author Nikesh.Bansod
 *
 */
public interface ReviseAndReturnService{

	List<ReviseAndReturn> listRR();

	String updateRRHistory(ReviseAndReturnHistory rrHistory);

	List<Object[]> listRevisionAndReturnDetails(Integer orgUId);

	boolean validateInvoiceHistoryAgainstOrgId(Integer id, Integer orgUId);

	/*List<InvoiceDetailsHistory> getInvoiceDetailsHistoryById(Integer id, Integer iterationNo);*/

	List<InvoiceDetails> getInvoiceDetailsByIdAndIterationNumber(Integer id ,Integer iterationNo);

	List<InvoiceDetailsHistory> getInvoiceDetailsHistoryByIdByNativeQuery(Integer id,Integer iterationNo);

	List<InvoiceServiceDetailsHistory> getProductInfoDetailsHistoryByIdByNativeQuery(Integer id,Integer iterationNo);

	List<InvoiceAdditionalChargeDetailsHistory> getAdditionalChargesHistory(Integer id, Integer iterationNo);

	List<InvoiceDetails> getInvoiceDetailsHistoryByIdByNativeQueryForLatestRR(Integer invoiceid);
	
	List<InvoiceDetails> getOldInvoiceDetailsByIdByNativeQuery(Integer id,Integer iterationNo);
	
	List<InvoiceServiceDetails> getOldServiceProductDetailsByIdByNativeQuery(Integer id, Integer iterationNo);
	
	List<InvoiceAdditionalChargeDetails> getOldAdditionalCharges(Integer id, Integer iterationNo);

}
