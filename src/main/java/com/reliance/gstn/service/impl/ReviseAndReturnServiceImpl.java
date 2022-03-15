/**
 * 
 */
package com.reliance.gstn.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliance.gstn.dao.ReviseAndReturnDao;
import com.reliance.gstn.model.InvoiceAdditionalChargeDetails;
import com.reliance.gstn.model.InvoiceAdditionalChargeDetailsHistory;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.InvoiceDetailsHistory;
import com.reliance.gstn.model.InvoiceServiceDetails;
import com.reliance.gstn.model.InvoiceServiceDetailsHistory;
import com.reliance.gstn.model.ReviseAndReturn;
import com.reliance.gstn.model.ReviseAndReturnHistory;
import com.reliance.gstn.service.ReviseAndReturnService;

/**
 * @author Nikesh.Bansod
 *
 */
@Service
public class ReviseAndReturnServiceImpl implements ReviseAndReturnService {

	@Autowired
	ReviseAndReturnDao reviseAndReturnDao; 
	
	
	@Override
	@Transactional
	public List<ReviseAndReturn> listRR() {
		return reviseAndReturnDao.listRR();
	}


	@Override
	@Transactional
	public String updateRRHistory(ReviseAndReturnHistory rrHistory) {
		return reviseAndReturnDao.updateRRHistory( rrHistory);
	}


	@Override
	@Transactional
	public List<Object[]> listRevisionAndReturnDetails(Integer orgUId) {
		return reviseAndReturnDao.revisionAndReturnDetail(orgUId);
	}


	@Override
	@Transactional
	public boolean validateInvoiceHistoryAgainstOrgId(Integer invoiceId, Integer orgUId) {
		return reviseAndReturnDao.validateInvoiceHistoryAgainstOrgId( invoiceId,  orgUId);
	}


/*	@Override
	@Transactional
	public List<InvoiceDetailsHistory> getInvoiceDetailsHistoryById(Integer id,Integer iterationNo) {
		// TODO Auto-generated method stub
		return reviseAndReturnDao.getInvoiceDetailsHistoryById(id,iterationNo);
	}*/


	@Override
	@Transactional
	public List<InvoiceDetails> getInvoiceDetailsByIdAndIterationNumber(Integer id,Integer iterationNo) {
		return reviseAndReturnDao.getInvoiceDetailsByIdAndIterationNo(id,iterationNo);
	}


	@Override
	@Transactional
	public List<InvoiceDetailsHistory> getInvoiceDetailsHistoryByIdByNativeQuery(Integer id,Integer iterationNo) {
		return reviseAndReturnDao.getInvoiceDetailsHistoryById(id,iterationNo);
	}
	
	@Override
	@Transactional
	public List<InvoiceDetails> getOldInvoiceDetailsByIdByNativeQuery(Integer id,Integer iterationNo){
		return reviseAndReturnDao.getOldInvoiceDetailsByIdByNativeQuery(id,iterationNo);
	}


	@Override
	@Transactional
	public List<InvoiceServiceDetailsHistory> getProductInfoDetailsHistoryByIdByNativeQuery(Integer id, Integer iterationNo) {
		return reviseAndReturnDao.getProductDetailsHistoryById(id,iterationNo);
	}


	@Override
	@Transactional
	public List<InvoiceAdditionalChargeDetailsHistory> getAdditionalChargesHistory(Integer id, Integer iterationNo) {
		return reviseAndReturnDao.getAdditionalChargesHistory(id,iterationNo);
	}


	@Override
	@Transactional
	public List<InvoiceDetails> getInvoiceDetailsHistoryByIdByNativeQueryForLatestRR(Integer invoiceid) {
		return reviseAndReturnDao.getInvoiceDetailsHistoryByIdByNativeQueryForLatestRR(invoiceid);
	}

	@Override
	@Transactional
	public List<InvoiceServiceDetails> getOldServiceProductDetailsByIdByNativeQuery(Integer id, Integer iterationNo) {
		return reviseAndReturnDao.getOldServiceProductDetailsByIdByNativeQuery(id,iterationNo);
	}


	@Override
	@Transactional
	public List<InvoiceAdditionalChargeDetails> getOldAdditionalCharges(Integer id, Integer iterationNo) {
		return reviseAndReturnDao.getOldAdditionalCharges( id,  iterationNo);
	}
	


}
