/**
 * 
 */
package com.reliance.gstn.model;

import java.io.Serializable;

/**
 * @author Nikesh.Bansod
 *
 */
public class ServiceTax implements Serializable {
	
	private static final long serialVersionUID = -2216712645185596891L;

	private Integer gstnStateId;
	
	private Integer serviceId;
	
	private Integer deliveryStateId;
	
	private Integer offerId;
	
	private double rate;
	
	private double quantity;
	
	private double amount;
	
	private double taxAmount;
	
	private String calculationBasedOn;
	
	private String invoiceFor;
	
	private double amountAfterDiscount;
	
	private double amountAfterCalculation;
	
	private double sgstAmount;
	
	private double cgstAmount;
	
	private double igstAmount;
	
//	private double offerAmount;

	public Integer getGstnStateId() {
		return gstnStateId;
	}

	public void setGstnStateId(Integer gstnStateId) {
		this.gstnStateId = gstnStateId;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getDeliveryStateId() {
		return deliveryStateId;
	}

	public void setDeliveryStateId(Integer deliveryStateId) {
		this.deliveryStateId = deliveryStateId;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Integer getOfferId() {
		return offerId;
	}

	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}

	public String getCalculationBasedOn() {
		return calculationBasedOn;
	}

	public void setCalculationBasedOn(String calculationBasedOn) {
		this.calculationBasedOn = calculationBasedOn;
	}

	public String getInvoiceFor() {
		return invoiceFor;
	}

	public void setInvoiceFor(String invoiceFor) {
		this.invoiceFor = invoiceFor;
	}

	public double getAmountAfterDiscount() {
		return amountAfterDiscount;
	}

	public void setAmountAfterDiscount(double amountAfterDiscount) {
		this.amountAfterDiscount = amountAfterDiscount;
	}

	public double getAmountAfterCalculation() {
		return amountAfterCalculation;
	}

	public void setAmountAfterCalculation(double amountAfterCalculation) {
		this.amountAfterCalculation = amountAfterCalculation;
	}

	public double getSgstAmount() {
		return sgstAmount;
	}

	public void setSgstAmount(double sgstAmount) {
		this.sgstAmount = sgstAmount;
	}

	public double getCgstAmount() {
		return cgstAmount;
	}

	public void setCgstAmount(double cgstAmount) {
		this.cgstAmount = cgstAmount;
	}

	public double getIgstAmount() {
		return igstAmount;
	}

	public void setIgstAmount(double igstAmount) {
		this.igstAmount = igstAmount;
	}
	
	
	
	

}
