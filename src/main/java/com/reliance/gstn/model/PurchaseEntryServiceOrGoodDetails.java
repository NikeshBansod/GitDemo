package com.reliance.gstn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.SafeHtml;
/**
 * @author @kshay Mohite
 *
 */
@Entity
@Table(name = "gst_purchase_entry_servicegood_details")
public class PurchaseEntryServiceOrGoodDetails implements Serializable {

	private static final long serialVersionUID = -1407372028247992307L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "service_good_id")
	private Integer serviceGoodId;

	@Column(name = "service_id")
	private Integer serviceId;

	@Column(name = "uom")
	@SafeHtml(message = "HTML elements not allowed")
	private String unitOfMeasurement;

	@Column(name = "service_rate")
	private double rate;

	@Column(name = "service_qty")
	@Digits(integer = 15, fraction = 3)
	private double quantity;

	@Column(name = "service_amt")
	private double amount;
	
	@Column(name = "calculation_on")
	@SafeHtml(message = "HTML elements not allowed")
	private String calculationBasedOn;
	
	@Column(name = "tax_amount")
	private double taxAmount;

	@Column(name = "status")
	private String status;

	@Column(name = "sgst_amount")
	private double sgstAmount;

	@Column(name = "ugst_amount")
	private double ugstAmount;

	@Column(name = "cgst_amount")
	private double cgstAmount;

	@Column(name = "igst_amount")
	private double igstAmount;

	@Column(name = "previous_amount")
	private double previousAmount;
	
	@Column(name = "serviceId_inString")
	@SafeHtml(message = "HTML elements not allowed")
	private String serviceIdInString;

	@Column(name = "gstn_state_id")
	private Integer gstnStateId;

	@Column(name = "supplier_state_id")		//delievery_state_id
	private Integer supplierStateId;

	@Column(name = "billing_for")
	@SafeHtml(message = "HTML elements not allowed")
	private String billingFor;

	@Column(name = "offer_amount")
	private double offerAmount;
	
	@Column(name = "cess")
	private double cess;
	
	@Column(name = "category_type")
	private String categoryType;
	
	@Column(name = "sgst_percent")
	private double sgstPercentage;
	
	@Column(name = "ugst_percent")
	private double ugstPercentage;
	
	@Column(name = "cgst_percent")
	private double cgstPercentage;
	
	@Column(name = "igst_percent")
	private double igstPercentage;
	
	@Column(name = "hsn_sac_code")
	@SafeHtml(message = "HTML elements not allowed")
	private String hsnSacCode;
	
	@Column(name = "additional_amount")
	private double additionalAmount;
	
	@Column(name = "hsn_sac_description")
	private String hsnSacDescription;
	
	@Transient
	private double valueAfterTax;
	
	@Transient
	private double taxableValue;

	@Transient
	private double amountAfterDiscount;

	@Transient
	private double amountAfterCalculation;

	@Transient
	private String isValid;
	
	@Column(name = "place_of_supply_id")
	private Integer placeOfSupplyId;
	
	@Transient
	private String addInInventory ="N";
	
	
	public Integer getServiceGoodId() {
		return serviceGoodId;
	}

	public void setServiceGoodId(Integer serviceGoodId) {
		this.serviceGoodId = serviceGoodId;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
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

	public String getCalculationBasedOn() {
		return calculationBasedOn;
	}

	public void setCalculationBasedOn(String calculationBasedOn) {
		this.calculationBasedOn = calculationBasedOn;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getSgstAmount() {
		return sgstAmount;
	}

	public void setSgstAmount(double sgstAmount) {
		this.sgstAmount = sgstAmount;
	}

	public double getUgstAmount() {
		return ugstAmount;
	}

	public void setUgstAmount(double ugstAmount) {
		this.ugstAmount = ugstAmount;
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

	public double getPreviousAmount() {
		return previousAmount;
	}

	public void setPreviousAmount(double previousAmount) {
		this.previousAmount = previousAmount;
	}

	public String getServiceIdInString() {
		return serviceIdInString;
	}

	public void setServiceIdInString(String serviceIdInString) {
		this.serviceIdInString = serviceIdInString;
	}

	public Integer getGstnStateId() {
		return gstnStateId;
	}

	public void setGstnStateId(Integer gstnStateId) {
		this.gstnStateId = gstnStateId;
	}

	public String getBillingFor() {
		return billingFor;
	}

	public void setBillingFor(String billingFor) {
		this.billingFor = billingFor;
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

	public double getOfferAmount() {
		return offerAmount;
	}

	public void setOfferAmount(double offerAmount) {
		this.offerAmount = offerAmount;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public double getCess() {
		return cess;
	}

	public void setCess(double cess) {
		this.cess = cess;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public double getSgstPercentage() {
		return sgstPercentage;
	}

	public void setSgstPercentage(double sgstPercentage) {
		this.sgstPercentage = sgstPercentage;
	}

	public double getUgstPercentage() {
		return ugstPercentage;
	}

	public void setUgstPercentage(double ugstPercentage) {
		this.ugstPercentage = ugstPercentage;
	}

	public double getCgstPercentage() {
		return cgstPercentage;
	}

	public void setCgstPercentage(double cgstPercentage) {
		this.cgstPercentage = cgstPercentage;
	}

	public double getIgstPercentage() {
		return igstPercentage;
	}

	public void setIgstPercentage(double igstPercentage) {
		this.igstPercentage = igstPercentage;
	}

	public String getHsnSacCode() {
		return hsnSacCode;
	}

	public void setHsnSacCode(String hsnSacCode) {
		this.hsnSacCode = hsnSacCode;
	}

	public double getAdditionalAmount() {
		return additionalAmount;
	}

	public void setAdditionalAmount(double additionalAmount) {
		this.additionalAmount = additionalAmount;
	}

	public String getHsnSacDescription() {
		return hsnSacDescription;
	}

	public void setHsnSacDescription(String hsnSacDescription) {
		this.hsnSacDescription = hsnSacDescription;
	}

	public double getValueAfterTax() {
		return valueAfterTax;
	}

	public void setValueAfterTax(double valueAfterTax) {
		this.valueAfterTax = valueAfterTax;
	}

	public double getTaxableValue() {
		return taxableValue;
	}

	public void setTaxableValue(double taxableValue) {
		this.taxableValue = taxableValue;
	}

	public Integer getSupplierStateId() {
		return supplierStateId;
	}

	public void setSupplierStateId(Integer supplierStateId) {
		this.supplierStateId = supplierStateId;
	}

	public Integer getPlaceOfSupplyId() {
		return placeOfSupplyId;
	}

	public void setPlaceOfSupplyId(Integer placeOfSupplyId) {
		this.placeOfSupplyId = placeOfSupplyId;
	}

	public String getAddInInventory() {
		return addInInventory;
	}

	public void setAddInInventory(String addInInventory) {
		this.addInInventory = addInInventory;
	}

	
}
