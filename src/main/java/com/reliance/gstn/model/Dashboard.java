package com.reliance.gstn.model;

import javax.persistence.Entity;
@Entity
public class Dashboard {
	private String startdate;
	private String years;
	private String wizardRequest;
	
	private long eWaybillCount;
	private long cndnCount;
	private long invoiceCount;
	private long invoiceEWaybillCount;
	private String onlyMonth;
	public String getOnlyMonth() {
		return onlyMonth;
	}
	public void setOnlyMonth(String onlyMonth) {
		this.onlyMonth = onlyMonth;
	}
	public String getOnlyYear() {
		return onlyYear;
	}
	public void setOnlyYear(String onlyYear) {
		this.onlyYear = onlyYear;
	}


	private String onlyYear;
	
	
	
	
	
	

	
	public long getInvoiceEWaybillCount() {
		return invoiceEWaybillCount;
	}
	public void setInvoiceEWaybillCount(long invoiceEWaybillCount) {
		this.invoiceEWaybillCount = invoiceEWaybillCount;
	}
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}


	private String enddate;



	public long geteWaybillCount() {
		return eWaybillCount;
	}
	public void seteWaybillCount(long eWaybillCount) {
		this.eWaybillCount = eWaybillCount;
	}
	public long getCndnCount() {
		return cndnCount;
	}
	public void setCndnCount(long cndnCount) {
		this.cndnCount = cndnCount;
	}
	public long getInvoiceCount() {
		return invoiceCount;
	}
	public void setInvoiceCount(long invoiceCount) {
		this.invoiceCount = invoiceCount;
	}
	public String getWizardRequest() {
		return wizardRequest;
	}
	public String setWizardRequest(String wizardRequest) {
		return this.wizardRequest = wizardRequest;
	}
	

}
