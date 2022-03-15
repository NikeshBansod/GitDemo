package com.reliance.gstn.model;

import java.io.Serializable;

public class EwayBillItemList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer hsnId;
	private Double taxableAmount;
	private Double quantity;
	private Double cessAmount;
	private Double cgstAmount;
	private Double sgstAmount;
	private Double igstAmount;
	private Integer itemNo;
	private Double cgstRate;
	private Double sgstRate;
	private Double igstRate;
	private Double cgstsgstRate;
	private Double cessadvolrate;
	private Double cessadvolAmount;
	private Double cessnonadvolrate;
	private Double cessnonadvolAmount;

	public Double getTaxableAmount() {
		return taxableAmount;
	}

	public void setTaxableAmount(Double taxableAmount) {
		this.taxableAmount = taxableAmount;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Integer getItemNo() {
		return itemNo;
	}

	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	public Integer getHsnId() {
		return hsnId;
	}

	public void setHsnId(Integer hsnId) {
		this.hsnId = hsnId;
	}

	public Double getCgstAmount() {
		return cgstAmount;
	}

	public void setCgstAmount(Double cgstAmount) {
		this.cgstAmount = cgstAmount;
	}

	public Double getSgstAmount() {
		return sgstAmount;
	}

	public void setSgstAmount(Double sgstAmount) {
		this.sgstAmount = sgstAmount;
	}

	public Double getIgstAmount() {
		return igstAmount;
	}

	public void setIgstAmount(Double igstAmount) {
		this.igstAmount = igstAmount;
	}

	public Double getCessAmount() {
		return cessAmount;
	}

	public void setCessAmount(Double cessAmount) {
		this.cessAmount = cessAmount;
	}

	public Double getCgstRate() {
		return cgstRate;
	}

	public void setCgstRate(Double cgstRate) {
		this.cgstRate = cgstRate;
	}

	public Double getSgstRate() {
		return sgstRate;
	}

	public void setSgstRate(Double sgstRate) {
		this.sgstRate = sgstRate;
	}

	public Double getIgstRate() {
		return igstRate;
	}

	public void setIgstRate(Double igstRate) {
		this.igstRate = igstRate;
	}

	public Double getCgstsgstRate() {
		return cgstsgstRate;
	}

	public void setCgstsgstRate(Double cgstsgstRate) {
		this.cgstsgstRate = cgstsgstRate;
	}

	public Double getCessadvolrate() {
		return cessadvolrate;
	}

	public void setCessadvolrate(Double cessadvolrate) {
		this.cessadvolrate = cessadvolrate;
	}

	public Double getCessnonadvolrate() {
		return cessnonadvolrate;
	}

	public void setCessnonadvolrate(Double cessnonadvolrate) {
		this.cessnonadvolrate = cessnonadvolrate;
	}

	public Double getCessadvolAmount() {
		return cessadvolAmount;
	}

	public void setCessadvolAmount(Double cessadvolAmount) {
		this.cessadvolAmount = cessadvolAmount;
	}

	public Double getCessnonadvolAmount() {
		return cessnonadvolAmount;
	}

	public void setCessnonadvolAmount(Double cessnonadvolAmount) {
		this.cessnonadvolAmount = cessnonadvolAmount;
	}

	

}
