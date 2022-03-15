package com.reliance.gstn.admin.model;

import javax.persistence.Entity;

@Entity
public class DashboardAdmin {
	
	private String startdate;
	private String years;
	private long totalinvoiceCount;
	private long mobileinvoiceCount;
	private long desktopinvoiceCount;
	private String onlyMonth;
	private String onlyYear;
	private String enddate;
	private long GenericEwaybillCount;
	private long InvoiceEwaybillCount;
	private long MobileGenericEwaybillCount;
	private long DesktopGenericEwaybillCount;
	private long MobileInvoiceEwaybillCount;
	private long DesktopInvoiceEwaybillCount;
	private long WizardGenericEwaybillCount;
	
	
	

	/*
	private long eWaybillCount;
	private long cndnCount;
	private long invoiceEWaybillCount;*/
	
	
	
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getOnlyYear() {
		return onlyYear;
	}
	public void setOnlyYear(String onlyYear) {
		this.onlyYear = onlyYear;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
	}
	
	public long getTotalinvoiceCount() {
		return totalinvoiceCount;
	}
	public void setTotalinvoiceCount(long totalinvoiceCount) {
		this.totalinvoiceCount = totalinvoiceCount;
	}
	public long getMobileinvoiceCount() {
		return mobileinvoiceCount;
	}
	public void setMobileinvoiceCount(long mobileinvoiceCount) {
		this.mobileinvoiceCount = mobileinvoiceCount;
	}
	public long getDesktopinvoiceCount() {
		return desktopinvoiceCount;
	}
	public void setDesktopinvoiceCount(long desktopinvoiceCount) {
		this.desktopinvoiceCount = desktopinvoiceCount;
	}
	public String getOnlyMonth() {
		return onlyMonth;
	}
	public void setOnlyMonth(String onlyMonth) {
		this.onlyMonth = onlyMonth;
	}
	public long getGenericEwaybillCount() {
		return GenericEwaybillCount;
	}
	public void setGenericEwaybillCount(long genricEWaybillCount) {
		GenericEwaybillCount = genricEWaybillCount;
	}
	public long getInvoiceEwaybillCount() {
		return InvoiceEwaybillCount;
	}
	public void setInvoiceEwaybillCount(long invoiceEwaybillCount) {
		InvoiceEwaybillCount = invoiceEwaybillCount;
	}
	public long getMobileGenericEwaybillCount() {
		return MobileGenericEwaybillCount;
	}
	public void setMobileGenericEwaybillCount(long mobileGenericEwaybillCount) {
		MobileGenericEwaybillCount = mobileGenericEwaybillCount;
	}
	public long getDesktopGenericEwaybillCount() {
		return DesktopGenericEwaybillCount;
	}
	public void setDesktopGenericEwaybillCount(long desktopGenericEwaybillCount) {
		DesktopGenericEwaybillCount = desktopGenericEwaybillCount;
	}
	public long getMobileInvoiceEwaybillCount() {
		return MobileInvoiceEwaybillCount;
	}
	public void setMobileInvoiceEwaybillCount(long mobileInvoiceEwaybillCount) {
		MobileInvoiceEwaybillCount = mobileInvoiceEwaybillCount;
	}
	public long getDesktopInvoiceEwaybillCount() {
		return DesktopInvoiceEwaybillCount;
	}
	public void setDesktopInvoiceEwaybillCount(long desktopInvoiceEwaybillCount) {
		DesktopInvoiceEwaybillCount = desktopInvoiceEwaybillCount;
	}
	
	public long getWizardGenericEwaybillCount() {
		return WizardGenericEwaybillCount;
	}
	public void setWizardGenericEwaybillCount(long wizardGenericEwaybillCount) {
		WizardGenericEwaybillCount = wizardGenericEwaybillCount;
	}
}
