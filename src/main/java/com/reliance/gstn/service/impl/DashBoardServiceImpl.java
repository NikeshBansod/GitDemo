/**
 * 
 */
package com.reliance.gstn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.dao.DashboardDAO;
import com.reliance.gstn.model.CnDnAdditionalDetails;
import com.reliance.gstn.model.Dashboard;
import com.reliance.gstn.model.EWayBill;
import com.reliance.gstn.model.PayloadCnDnDetails;
import com.reliance.gstn.service.DashBoardService;

/**
 * @author Sagar.Parab
 *
 */
@Service
public class DashBoardServiceImpl implements DashBoardService {
	
	@Autowired
	private DashboardDAO dashboardDAO;

	@Override
	@Transactional
	public List<Object[]> getGeneratedInvoiceList(Dashboard dashboard,
			Integer uID,String getInvType,String appCode) {
		// TODO Auto-generated method stub
		return dashboardDAO.getGeneratedInvoiceList(dashboard,uID, getInvType,appCode);
	}
	@Override
	@Transactional
	public long getInvoiceCount(Dashboard dashboard,Integer uID,String appCode) throws Exception {
	
		return dashboardDAO.getInvoiceCount(dashboard,uID,appCode);
		
	}

	@Override
	@Transactional
	public long getCnDnNote(Dashboard dashboard, Integer uID) throws Exception {
		return dashboardDAO.getCnDnNote(dashboard,uID);
		
	}

	@Override
	@Transactional
	public long getGeneratedInvoiceCount(Dashboard dashboard, Integer uID)
			throws Exception {
		return dashboardDAO.getGeneratedInvoiceCount(dashboard,uID);
		
	}
	@Override
	@Transactional
	public List<PayloadCnDnDetails> getProductDetailsForDash(String cndnNumber) {
		// TODO Auto-generated method stub
		return dashboardDAO.getProductDetailsForDash(cndnNumber);
	}
	@Override
	@Transactional
	public List<CnDnAdditionalDetails> getCNDNdetails(Integer cId) {
		// TODO Auto-generated method stub
		return dashboardDAO.getCNDNdetails(cId);
	}
	@Override
	@Transactional
	public long getInvoiceEWaybillCount(Dashboard dashboard, Integer orgUId) {
		// TODO Auto-generated method stub
		return dashboardDAO.getInvoiceEWaybillCount(dashboard,orgUId);
	}
	
	@Override
	@Transactional
	public List<EWayBill> getInvoiceEwayBillWITransactionDetails(
			String ewaybillNo) {
		// TODO Auto-generated method stub
		return dashboardDAO.getInvoiceEwayBillWITransactionDetails(ewaybillNo);
	}
	


}
