package com.reliance.gstn.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.admin.dao.AdminDashboardDAO;
import com.reliance.gstn.admin.model.DashboardAdmin;
import com.reliance.gstn.admin.service.AdminDashboardService;



@Service
public class AdminDashboardServiceImpl implements AdminDashboardService
{
	
@Autowired
private AdminDashboardDAO adminDashboardDAO;



	@Override
	@Transactional
	public long getMobileInvoiceCount(DashboardAdmin dashboardadmin)
			throws Exception {
		return adminDashboardDAO.getMobileInvoiceCount(dashboardadmin);
		
	}

	@Override
	@Transactional
	public long getDesktopInvoiceCount(DashboardAdmin dashboardadmin) throws Exception {
		return  adminDashboardDAO.getDesktopInvoiceCount(dashboardadmin);
		
	}

	@Override
	@Transactional
	public long getGeneratedInvoiceCount(DashboardAdmin dashboardadmin) throws Exception {
		return  adminDashboardDAO.getGeneratedInvoiceCount(dashboardadmin);
		
	}

	@Override
	@Transactional
	public long getGenericEwaybillCount(DashboardAdmin dashboardadmin)
			throws Exception {
		// TODO Auto-generated method stub
		return  adminDashboardDAO.getGenericEwaybillCount(dashboardadmin);
	}

	@Override
	@Transactional
	public long getInvoiceEwaybillCount(DashboardAdmin dashboardadmin)
			throws Exception {
		// TODO Auto-generated method stub
		return  adminDashboardDAO.getInvoiceEwaybillCount(dashboardadmin);
	}

	@Override
	@Transactional
	public long getMobileGenericEwaybillCount(DashboardAdmin dashboardadmin)
			throws Exception {
		// TODO Auto-generated method stub
		return  adminDashboardDAO.getMobileGenericEwaybillCount(dashboardadmin);
	}

	@Override
	@Transactional
	public long getDesktopGenericEwaybillCount(DashboardAdmin dashboardadmin)
			throws Exception {
		// TODO Auto-generated method stub
		return  adminDashboardDAO.getDesktopGenericEwaybillCount(dashboardadmin);
	}

	@Override
	@Transactional
	public long getMobileInvoiceEwaybillCount(DashboardAdmin dashboardadmin)
			throws Exception {
		// TODO Auto-generated method stub
		return  adminDashboardDAO.getMobileInvoiceEwaybillCount(dashboardadmin);
	}

	@Override
	@Transactional
	public long getDesktopInvoiceEwaybillCount(DashboardAdmin dashboardadmin)
			throws Exception {
		// TODO Auto-generated method stub
		return  adminDashboardDAO.getDesktopInvoiceEwaybillCount(dashboardadmin);
	}

	@Override
	@Transactional
	public long getWizardGenericEwaybillCount(DashboardAdmin dashboardadmin)
			throws Exception {
		// TODO Auto-generated method stub
		return  adminDashboardDAO.getWizardGenericEwaybillCount(dashboardadmin);
	}

}
