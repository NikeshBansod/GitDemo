package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

public class EwayBillCalculateTax implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fromGstin;
	private String toGstin;
	private double sgstTotalAmount = 0;
	private double cgstTotalAmount = 0;
	private double igstTotalAmount = 0;
	private double totaltaxAmount = 0;
	private double totalAmount = 0;
	private double totalcessAmount = 0;
	private double totalCessadvolAmount=0;
	private double totalCessnonadvolAmount=0;
	private double otherAmount=0;

	private Integer fromStateCode;

	private Integer toStateCode;

	List<EwayBillItemList> itemList = new ArrayList<>();

	public String getFromGstin() {
		return fromGstin;
	}

	public void setFromGstin(String fromGstin) {
		this.fromGstin = fromGstin;
	}

	public String getToGstin() {
		return toGstin;
	}

	public void setToGstin(String toGstin) {
		this.toGstin = toGstin;
	}

	public List<EwayBillItemList> getItemList() {
		return itemList;
	}

	public void setItemList(List<EwayBillItemList> itemList) {
		this.itemList = itemList;
	}

	public double getSgstTotalAmount() {
		return sgstTotalAmount;
	}

	public void setSgstTotalAmount(double sgstTotalAmount) {
		this.sgstTotalAmount = sgstTotalAmount;
	}

	public double getCgstTotalAmount() {
		return cgstTotalAmount;
	}

	public void setCgstTotalAmount(double cgstTotalAmount) {
		this.cgstTotalAmount = cgstTotalAmount;
	}

	public double getIgstTotalAmount() {
		return igstTotalAmount;
	}

	public void setIgstTotalAmount(double igstTotalAmount) {
		this.igstTotalAmount = igstTotalAmount;
	}

	public double getTotaltaxAmount() {
		return totaltaxAmount;
	}

	public void setTotaltaxAmount(double totaltaxAmount) {
		this.totaltaxAmount = totaltaxAmount;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getTotalcessAmount() {
		return totalcessAmount;
	}

	public void setTotalcessAmount(double totalcessAmount) {
		this.totalcessAmount = totalcessAmount;
	}

	public Integer getFromStateCode() {
		return fromStateCode;
	}

	public void setFromStateCode(Integer fromStateCode) {
		this.fromStateCode = fromStateCode;
	}

	public Integer getToStateCode() {
		return toStateCode;
	}

	public void setToStateCode(Integer toStateCode) {
		this.toStateCode = toStateCode;
	}

	public double getTotalCessadvolAmount() {
		return totalCessadvolAmount;
	}

	public void setTotalCessadvolAmount(double totalCessadvolAmount) {
		this.totalCessadvolAmount = totalCessadvolAmount;
	}

	public double getTotalCessnonadvolAmount() {
		return totalCessnonadvolAmount;
	}

	public void setTotalCessnonadvolAmount(double totalCessnonadvolAmount) {
		this.totalCessnonadvolAmount = totalCessnonadvolAmount;
	}

	public double getOtherAmount() {
		return otherAmount;
	}

	public void setOtherAmount(double otherAmount) {
		this.otherAmount = otherAmount;
	}
	
	
	
	

}
