package com.reliance.gstn.admin.service;

import com.reliance.gstn.admin.model.DashboardAdmin;




public interface AdminDashboardService {
	
	
	long getMobileInvoiceCount(DashboardAdmin dashboardadmin) throws Exception;

	long getDesktopInvoiceCount(DashboardAdmin dashboardadmin) throws Exception;

    long getGeneratedInvoiceCount(DashboardAdmin dashboardadmin) throws Exception;

	long getGenericEwaybillCount(DashboardAdmin dashboardadmin)throws Exception;

	long getInvoiceEwaybillCount(DashboardAdmin dashboardadmin)throws Exception;

	long getMobileGenericEwaybillCount(DashboardAdmin dashboardadmin)throws Exception;

	long getDesktopGenericEwaybillCount(DashboardAdmin dashboardadmin)throws Exception;

	long getMobileInvoiceEwaybillCount(DashboardAdmin dashboardadmin)throws Exception;

	long getDesktopInvoiceEwaybillCount(DashboardAdmin dashboardadmin)throws Exception;

	long getWizardGenericEwaybillCount(DashboardAdmin dashboardadmin)throws Exception;

	

}
