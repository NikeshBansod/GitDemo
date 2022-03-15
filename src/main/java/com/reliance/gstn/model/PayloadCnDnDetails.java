/**
 * 
 */
package com.reliance.gstn.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Nikesh.Bansod
 *
 */
@Entity
@Table(name = "gst_invoice_cn_dn_details")
public class PayloadCnDnDetails implements Serializable {

	private static final long serialVersionUID = -1905448786249417357L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_cn_dn_pk_id")
	private Integer id;
	
	@Column(name = "cn_dn_type")
	private String cnDnType;
	
	@Column(name = "created_on", updatable=false)
	private java.sql.Timestamp createdOn;

	@Column(name = "created_by", updatable=false)
	private String createdBy;
	
	@Column(name = "service_id")
	private Integer serviceId;
	
	@Column(name = "iteration_no")
	private Integer iterationNo;
	
	@Column(name = "reason")
	private String reason;
	
	@Column(name = "regime")
	private String regime;
	
	@Column(name = "rate")
	private double rate;

	@Column(name = "quantity")
	private double quantity;

	@Column(name = "taxable_value")
	private double taxableValue;
	
	@Column(name = "value_after_tax")
	private double valueAfterTax;
	
	/*These fields are common fields that are also present in InvoiceServiceDetails - Start */
	
	@Column(name = "uom")
	private String unitOfMeasurement;
	
	@Column(name = "service_amt")
	private double amount;
	
	@Column(name = "calculation_on")
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
	private String serviceIdInString;
	
	@Column(name = "gstn_state_id")
	private Integer gstnStateId;

	@Column(name = "delivery_state_id")
	private Integer deliveryStateId;

	@Column(name = "billing_for")
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
	private String hsnSacCode;
	
	@Column(name = "additional_amount")
	private double additionalAmount;
	
	@Column(name = "hsn_sac_description")
	private String hsnSacDescription;
	
	@Transient
	private double amountAfterDiscount;
	
	@Column(name = "ref_org_uId")
	private Integer orgUId;
	
	
	
	@Column(name = "upload_to_jiogst")
	private String uploadToJiogst ="false";
	
	
	@Column(name = "save_to_gstn")
	private String saveToGstn ="false";
	

	@Column(name = "submit_to_gstn")
	private String submitToGstn ="false";
	
	@Column(name = "file_to_gstn")
	private String fileToGstn ="false";
	
	
	/*These fields are common fields that are also present in InvoiceServiceDetails - End */


	@Column(name = "cndn_number")
	private String cndnNumber;
	
	@OneToOne(cascade = CascadeType.ALL,mappedBy="")
	@JoinTable(name="gst_invoice_cn_dn_mapping",joinColumns = @JoinColumn(name = "invoice_cn_dn_pk_id",nullable = true),
    inverseJoinColumns = @JoinColumn(name = "invoice_id",nullable = true))
	private InvoiceDetails invoiceDetails;
	
	@Column(name = "delete_status")
	private String deleteYn = "N";
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCnDnType() {
		return cnDnType;
	}

	public void setCnDnType(String cnDnType) {
		this.cnDnType = cnDnType;
	}

	public java.sql.Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(java.sql.Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getIterationNo() {
		return iterationNo;
	}

	public void setIterationNo(Integer iterationNo) {
		this.iterationNo = iterationNo;
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

	public double getTaxableValue() {
		return taxableValue;
	}

	public void setTaxableValue(double taxableValue) {
		this.taxableValue = taxableValue;
	}

	public double getValueAfterTax() {
		return valueAfterTax;
	}

	public void setValueAfterTax(double valueAfterTax) {
		this.valueAfterTax = valueAfterTax;
	}

	/*These fields are common fields that are also present in InvoiceServiceDetails - Start */
	
	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
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

	public Integer getDeliveryStateId() {
		return deliveryStateId;
	}

	public void setDeliveryStateId(Integer deliveryStateId) {
		this.deliveryStateId = deliveryStateId;
	}

	public String getBillingFor() {
		return billingFor;
	}

	public void setBillingFor(String billingFor) {
		this.billingFor = billingFor;
	}

	public double getOfferAmount() {
		return offerAmount;
	}

	public void setOfferAmount(double offerAmount) {
		this.offerAmount = offerAmount;
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

	/*These fields are common fields that are also present in InvoiceServiceDetails - End */
	
	public double getAmountAfterDiscount() {
		return amountAfterDiscount;
	}

	public void setAmountAfterDiscount(double amountAfterDiscount) {
		this.amountAfterDiscount = amountAfterDiscount;
	}

	public Integer getOrgUId() {
		return orgUId;
	}

	public void setOrgUId(Integer orgUId) {
		this.orgUId = orgUId;
	}

	public String getCndnNumber() {
		return cndnNumber;
	}

	public void setCndnNumber(String cndnNumber) {
		this.cndnNumber = cndnNumber;
	}

	public InvoiceDetails getInvoiceDetails() {
		return invoiceDetails;
	}

	public void setInvoiceDetails(InvoiceDetails invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}

	public String getDeleteYn() {
		return deleteYn;
	}

	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}
	
	
	public String getUploadToJiogst() {
		return uploadToJiogst;
	}

	public void setUploadToJiogst(String uploadToJiogst) {
		this.uploadToJiogst = uploadToJiogst;
	}

	public String getSaveToGstn() {
		return saveToGstn;
	}

	public void setSaveToGstn(String saveToGstn) {
		this.saveToGstn = saveToGstn;
	}

	public String getSubmitToGstn() {
		return submitToGstn;
	}

	public void setSubmitToGstn(String submitToGstn) {
		this.submitToGstn = submitToGstn;
	}

	public String getFileToGstn() {
		return fileToGstn;
	}

	public void setFileToGstn(String fileToGstn) {
		this.fileToGstn = fileToGstn;
	}

	
	
}
