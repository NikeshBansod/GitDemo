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

@Entity
@Table(name = "gst_invoice_service_details_history")
public class InvoiceServiceDetailsHistory implements Serializable {

	private static final long serialVersionUID = -2537695823230268973L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_service_pk_id")
	private Integer id;

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

	@Column(name = "delivery_state_id")
	private Integer deliveryStateId;

	@Column(name = "billing_for")
	@SafeHtml(message = "HTML elements not allowed")
	private String billingFor;

	@Transient
	private double amountAfterDiscount;

	@Transient
	private double amountAfterCalculation;

	@Column(name = "offer_amount")
	private double offerAmount;

	@Transient
	private String isValid;

	@Transient
	private String typeOfExport;

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
	private double cndnValuePreviouslyAdded;

	@Transient
	private double valueAfterTax;

	@Transient
	private double cnDnAppliedRate;

	@Transient
	private String cnDnType;

	@Transient
	private String reason;

	@Transient
	private String regime;

	@Transient
	private double taxableValue;

	@Column(name = "diff_percent")
	@SafeHtml(message = "HTML elements not allowed")
	private String diffPercent;

	@Column(name = "discount_type")
	@SafeHtml(message = "HTML elements not allowed")
	private String discountTypeInItem;

	@Column(name = "advol_cess")
	private double advolCess;

	@Column(name = "advol_cess_amt")
	private double advolCessAmount;

	@Column(name = "non_advol_cess")
	private double nonAdvolCess;

	@Column(name = "iteration_no")
	private int iterationNo;

	@Transient
	private String isNew;

	@Transient
	private String isTaxRateChange;

	@Transient
	private double newTaxRate;
	
	@Column(name = "is_description_checked")
	@SafeHtml(message = "HTML elements not allowed")
	private String isDescriptionChecked;
	
	@Column(name = "description")
	@SafeHtml(message = "HTML elements not allowed")
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getGstnStateId() {
		return gstnStateId;
	}

	public void setGstnStateId(Integer gstnStateId) {
		this.gstnStateId = gstnStateId;
	}

	public Integer getDeliveryStateId() {
		return deliveryStateId;
	}

	public void setDeliveryStateId(Integer deliveryStateId) {
		this.deliveryStateId = deliveryStateId;
	}

	public String getServiceIdInString() {
		return serviceIdInString;
	}

	public void setServiceIdInString(String serviceIdInString) {
		this.serviceIdInString = serviceIdInString;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public String getCalculationBasedOn() {
		return calculationBasedOn;
	}

	public void setCalculationBasedOn(String calculationBasedOn) {
		this.calculationBasedOn = calculationBasedOn;
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

	public double getOfferAmount() {
		return offerAmount;
	}

	public void setOfferAmount(double offerAmount) {
		this.offerAmount = offerAmount;
	}

	public double getPreviousAmount() {
		return previousAmount;
	}

	public void setPreviousAmount(double previousAmount) {
		this.previousAmount = previousAmount;
	}

	public double getUgstAmount() {
		return ugstAmount;
	}

	public void setUgstAmount(double ugstAmount) {
		this.ugstAmount = ugstAmount;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getTypeOfExport() {
		return typeOfExport;
	}

	public void setTypeOfExport(String typeOfExport) {
		this.typeOfExport = typeOfExport;
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

	public double getCndnValuePreviouslyAdded() {
		return cndnValuePreviouslyAdded;
	}

	public void setCndnValuePreviouslyAdded(double cndnValuePreviouslyAdded) {
		this.cndnValuePreviouslyAdded = cndnValuePreviouslyAdded;
	}

	public double getValueAfterTax() {
		return valueAfterTax;
	}

	public void setValueAfterTax(double valueAfterTax) {
		this.valueAfterTax = valueAfterTax;
	}

	public double getCnDnAppliedRate() {
		return cnDnAppliedRate;
	}

	public void setCnDnAppliedRate(double cnDnAppliedRate) {
		this.cnDnAppliedRate = cnDnAppliedRate;
	}

	public String getCnDnType() {
		return cnDnType;
	}

	public void setCnDnType(String cnDnType) {
		this.cnDnType = cnDnType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRegime() {
		return regime;
	}

	public void setRegime(String regime) {
		this.regime = regime;
	}

	public double getTaxableValue() {
		return taxableValue;
	}

	public void setTaxableValue(double taxableValue) {
		this.taxableValue = taxableValue;
	}

	public String getDiffPercent() {
		return diffPercent;
	}

	public void setDiffPercent(String diffPercent) {
		this.diffPercent = diffPercent;
	}

	public String getDiscountTypeInItem() {
		return discountTypeInItem;
	}

	public void setDiscountTypeInItem(String discountTypeInItem) {
		this.discountTypeInItem = discountTypeInItem;
	}

	public double getAdvolCess() {
		return advolCess;
	}

	public void setAdvolCess(double advolCess) {
		this.advolCess = advolCess;
	}

	public double getAdvolCessAmount() {
		return advolCessAmount;
	}

	public void setAdvolCessAmount(double advolCessAmount) {
		this.advolCessAmount = advolCessAmount;
	}

	public double getNonAdvolCess() {
		return nonAdvolCess;
	}

	public void setNonAdvolCess(double nonAdvolCess) {
		this.nonAdvolCess = nonAdvolCess;
	}

	public int getIterationNo() {
		return iterationNo;
	}

	public void setIterationNo(int iterationNo) {
		this.iterationNo = iterationNo;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getIsTaxRateChange() {
		return isTaxRateChange;
	}

	public void setIsTaxRateChange(String isTaxRateChange) {
		this.isTaxRateChange = isTaxRateChange;
	}

	public double getNewTaxRate() {
		return newTaxRate;
	}

	public void setNewTaxRate(double newTaxRate) {
		this.newTaxRate = newTaxRate;
	}

	public String getIsDescriptionChecked() {
		return isDescriptionChecked;
	}

	public void setIsDescriptionChecked(String isDescriptionChecked) {
		this.isDescriptionChecked = isDescriptionChecked;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
