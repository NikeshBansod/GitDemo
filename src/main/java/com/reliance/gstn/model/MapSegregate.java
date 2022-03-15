package com.reliance.gstn.model;

public class MapSegregate {
	
	private String taxRate;
	private double taxableAmount;
	private double cgstAmount;
	private double sgstAmount;
	private double igstAmount;
	private double taxRateTotaltax;
	private String isDiff;
	public String getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}
	public double getTaxableAmount() {
		return taxableAmount;
	}
	public void setTaxableAmount(double taxableAmount) {
		this.taxableAmount = taxableAmount;
	}
	public double getCgstAmount() {
		return cgstAmount;
	}
	public void setCgstAmount(double cgstAmount) {
		this.cgstAmount = cgstAmount;
	}
	public double getSgstAmount() {
		return sgstAmount;
	}
	public void setSgstAmount(double sgstAmount) {
		this.sgstAmount = sgstAmount;
	}
	public double getIgstAmount() {
		return igstAmount;
	}
	public void setIgstAmount(double igstAmount) {
		this.igstAmount = igstAmount;
	}
	public double getTaxRateTotaltax() {
		return taxRateTotaltax;
	}
	public void setTaxRateTotaltax(double taxRateTotaltax) {
		this.taxRateTotaltax = taxRateTotaltax;
	}
	public String getIsDiff() {
		return isDiff;
	}
	public void setIsDiff(String isDiff) {
		this.isDiff = isDiff;
	}
	
	

}
