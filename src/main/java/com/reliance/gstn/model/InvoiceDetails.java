package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "gst_invoice_details")
public class InvoiceDetails implements Serializable{

	private static final long serialVersionUID = 7851496514069719090L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_id")
	private int id;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "invoice_for")
	private String invoiceFor;

	@OneToOne     
	@JoinColumn(name = "customer_id")
	CustomerDetails customerDetails;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "service_place")
	private String servicePlace;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "service_country")
	private String serviceCountry;

	//@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "delivery_place")
	private Integer deliveryPlace;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "delivery_country")
	private String deliveryCountry;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "po_id")
	private String poDetails;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "type_of_export")
	private String typeOfExport;

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="gst_invoice_service_mapping",joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "invoice_service_pk_id"))
	private List<InvoiceServiceDetails> serviceList = new ArrayList<InvoiceServiceDetails>();


	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "discount_type")
	private String discountType;

	//@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "discount_value")
	private double discountValue;

	@Column(name = "discount_amount")
	private double discountAmount;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "discount_remarks")
	private String discountRemarks;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "additional_charges_type")
	private String additionalChargesType;

	
	@Column(name = "additional_charges_value")
	private double additionalChargesValue;

	@Column(name = "additional_amount")
	private double additonalAmount;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "additional_charges_remark")
	private String additionalChargesRemark;

	@Column(name = "amount_after_discount")
	private double amountAfterDiscount;

	@Column(name = "total_tax")
	private double totalTax;

	@Column(name = "invoice_value")
	private double invoiceValue;
	
	@Column(name = "ref_user_id")
	private Integer referenceId;
	
	@Column(name = "created_on", updatable=false)
	private java.sql.Timestamp createdOn;

	@Column(name = "created_by", updatable=false)
	private String createdBy;

	@Column(name = "updated_on")
	private java.sql.Timestamp updatedOn;

	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "invoice_date")
	private java.sql.Timestamp invoiceDate;
	
	@Transient
	@SafeHtml(message = "HTML elements not allowed")
	private String invoiceDateInString;
	

	@Column(name = "gstn_state_id")
	private Integer gstnStateId;
	
	@Column(name = "gstn_id")
	@SafeHtml(message = "HTML elements not allowed")
	private String gstnStateIdInString;
	
	@Column(name = "invoice_number")
	private String invoiceNumber;
	
	

	@Column(name = "invoice_period_from_date")
	private java.sql.Timestamp invoicePeriodFromDate;
	
	@Transient
	@SafeHtml(message = "HTML elements not allowed")
	private String invoicePeriodFromDateInString;
	
	@Column(name = "invoice_period_to_date")
	private java.sql.Timestamp invoicePeriodToDate;
	
	@Transient
	@SafeHtml(message = "HTML elements not allowed")
	private String invoicePeriodToDateInString;
	
	@Column(name = "ref_org_uId")
	private Integer orgUId;
	
	@Column(name = "billToShip_IsChecked")
	@SafeHtml(message = "HTML elements not allowed")
	private String billToShipIsChecked;
	
	@Column(name = "shipTo_customer_name")
	@SafeHtml(message = "HTML elements not allowed")
	private String shipToCustomerName;
	
	@Column(name = "shipTo_customer_address")
	@SafeHtml(message = "HTML elements not allowed")
	private String shipToAddress;
	
	@Column(name = "shipTo_customer_city")
	@SafeHtml(message = "HTML elements not allowed")
	private String shipToCity;
	
	@Column(name = "shipTo_customer_pincode")
	@SafeHtml(message = "HTML elements not allowed")
	private String shipToPincode;
	
	@Column(name = "shipTo_customer_state")
	@SafeHtml(message = "HTML elements not allowed")
	private String shipToState;
	
	@Column(name = "shipTo_customer_state_code")
	@SafeHtml(message = "HTML elements not allowed")
	private String shipToStateCode;
	
	@Column(name = "shipTo_customer_state_code_id")
	@SafeHtml(message = "HTML elements not allowed")
	private String shipToStateCodeId;
	
	@Column(name = "location")
	@SafeHtml(message = "HTML elements not allowed")
	private String location;
	
	@Column(name = "customer_email")
	@SafeHtml(message = "HTML elements not allowed")
	private String customerEmail;
	
	@Column(name = "ecommerce")
	@SafeHtml(message = "HTML elements not allowed")
	private String ecommerce;
	
	@Column(name = "ecommerce_gstin")
	@SafeHtml(message = "HTML elements not allowed")
	private String ecommerceGstin;
	
	@Column(name = "reverse_charge")
	@SafeHtml(message = "HTML elements not allowed")
	private String reverseCharge;
	
	@Column(name = "invoice_value_after_round_off")
	private double invoiceValueAfterRoundOff;
	
	@Column(name = "delete_status")
	private String deleteYn = "N";
	
	@Column(name = "invoice_category")
	private String invCategory;
	
	@Column(name = "upload_to_jiogst")
	private String uploadToJiogst ="false";
	
	@Column(name = "save_to_gstn")
	private String saveToGstn ="false";
	

	@Column(name = "submit_to_gstn")
	private String submitToGstn ="false";
	
	@Column(name = "file_to_gstn")
	private String fileToGstn ="false";
	
	@UpdateTimestamp
	@Column(name = "upload_to_jiogst_TimeStamp")
	private java.sql.Timestamp UploadToJiogstTime;
	
	@UpdateTimestamp
	@Column(name = "save_to_gstn_TimeStamp")
	private java.sql.Timestamp saveToGstnTime;
	
	@UpdateTimestamp
	@Column(name = "submit_to_gstn_TimeStamp")
	private java.sql.Timestamp submitToGstnTime;
	
	@UpdateTimestamp
	@Column(name = "file_to_gstn_TimeStamp")
	private java.sql.Timestamp fileToGstnTime;
	
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="gst_invoice_add_chgs_mapping",joinColumns = @JoinColumn(name = "invoice_id",nullable = true),
            inverseJoinColumns = @JoinColumn(name = "invoice_additional_charge_pk_id",nullable = true))
	private List<InvoiceAdditionalChargeDetails> addChargesList = new ArrayList<InvoiceAdditionalChargeDetails>();
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="gst_invoice_cn_dn_mapping",joinColumns = @JoinColumn(name = "invoice_id",nullable = true),
            inverseJoinColumns = @JoinColumn(name = "invoice_cn_dn_pk_id",nullable = true))
	private List<InvoiceCnDnDetails> cnDnList = new ArrayList<InvoiceCnDnDetails>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="gst_invoice_cn_dn_additional_mapping",joinColumns = @JoinColumn(name = "invoice_id",nullable = true),
            inverseJoinColumns = @JoinColumn(name = "invoice_cn_dn_additional_pk_id",nullable = true))
	private List<CnDnAdditionalDetails> cnDnAdditionalList = new ArrayList<CnDnAdditionalDetails>();
	
	@Transient
	private String cnDnType;
	
	@Column(name = "footer_note")
	@SafeHtml(message = "HTML elements not allowed")
	private String footerNote;
	
	@Column(name = "documentType")
	@SafeHtml(message = "HTML elements not allowed")
	private String documentType;
	
	@Column(name = "generated_through")
	@SafeHtml(message = "HTML elements not allowed")
	private String generatedThrough;
	
	@Column(name = "is_customer_registered")
	@SafeHtml(message = "HTML elements not allowed")
	private String isCustomerRegistered;
	
	@Column(name="otherCharges")
	private double otherCharges;
	
	@Column(name = "mode_of_creation")
	@SafeHtml(message = "HTML elements not allowed")
	private String modeOfCreation;
	
	@Column(name = "iteration_no")
	private int iterationNo ;
	
	@Transient
	private String lastRRType;
	
	@Transient
	private String lastRR;
	
	@Column(name = "last_rr_invoice_number")
	@SafeHtml(message = "HTML elements not allowed")
	private String lastRRInvoiceNumber;
	
	@Column(name = "location_store_id")
	private Integer locationStoreId;
	
	@Column(name = "last_rr")
	@SafeHtml(message = "HTML elements not allowed")
	private String rrTypeForCreation;
	
	@Column(name = "last_rr_type")
	@SafeHtml(message = "HTML elements not allowed")
	private String createDocType;
	
	@Column(name = "placeOfSupply")
	@SafeHtml(message = "HTML elements not allowed")
	private String placeOfSupply;
	
	@Transient
	private String cndnIsAdditionalChargePresent;
	
	public String getInvoiceDateInString() {
		return invoiceDateInString;
	}

	public void setInvoiceDateInString(String invoiceDateInString) {
		this.invoiceDateInString = invoiceDateInString;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInvoiceFor() {
		return invoiceFor;
	}

	public void setInvoiceFor(String invoiceFor) {
		this.invoiceFor = invoiceFor;
	}

	public String getServicePlace() {
		return servicePlace;
	}

	public void setServicePlace(String servicePlace) {
		this.servicePlace = servicePlace;
	}

	public String getServiceCountry() {
		return serviceCountry;
	}

	public void setServiceCountry(String serviceCountry) {
		this.serviceCountry = serviceCountry;
	}

	public Integer getDeliveryPlace() {
		return deliveryPlace;
	}

	public void setDeliveryPlace(Integer deliveryPlace) {
		this.deliveryPlace = deliveryPlace;
	}

	public String getDeliveryCountry() {
		return deliveryCountry;
	}

	public void setDeliveryCountry(String deliveryCountry) {
		this.deliveryCountry = deliveryCountry;
	}

	public String getTypeOfExport() {
		return typeOfExport;
	}

	public void setTypeOfExport(String typeOfExport) {
		this.typeOfExport = typeOfExport;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public double getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}

	public String getAdditionalChargesType() {
		return additionalChargesType;
	}

	public void setAdditionalChargesType(String additionalChargesType) {
		this.additionalChargesType = additionalChargesType;
	}

	public double getAdditionalChargesValue() {
		return additionalChargesValue;
	}

	public void setAdditionalChargesValue(double additionalChargesValue) {
		this.additionalChargesValue = additionalChargesValue;
	}

	public double getAdditonalAmount() {
		return additonalAmount;
	}

	public void setAdditonalAmount(double additonalAmount) {
		this.additonalAmount = additonalAmount;
	}

	public String getAdditionalChargesRemark() {
		return additionalChargesRemark;
	}

	public void setAdditionalChargesRemark(String additionalChargesRemark) {
		this.additionalChargesRemark = additionalChargesRemark;
	}

	public double getAmountAfterDiscount() {
		return amountAfterDiscount;
	}

	public void setAmountAfterDiscount(double amountAfterDiscount) {
		this.amountAfterDiscount = amountAfterDiscount;
	}

	public double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	public double getInvoiceValue() {
		return invoiceValue;
	}

	public void setInvoiceValue(double invoiceValue) {
		this.invoiceValue = invoiceValue;
	}

	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	public String getPoDetails() {
		return poDetails;
	}

	public void setPoDetails(String poDetails) {
		this.poDetails = poDetails;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getDiscountRemarks() {
		return discountRemarks;
	}

	public void setDiscountRemarks(String discountRemarks) {
		this.discountRemarks = discountRemarks;
	}

	public List<InvoiceServiceDetails> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<InvoiceServiceDetails> serviceList) {
		this.serviceList = serviceList;
	}

	public Integer getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Integer referenceId) {
		this.referenceId = referenceId;
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

	public java.sql.Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(java.sql.Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public java.sql.Timestamp getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(java.sql.Timestamp invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Integer getGstnStateId() {
		return gstnStateId;
	}

	public void setGstnStateId(Integer gstnStateId) {
		this.gstnStateId = gstnStateId;
	}

	public String getGstnStateIdInString() {
		return gstnStateIdInString;
	}

	public void setGstnStateIdInString(String gstnStateIdInString) {
		this.gstnStateIdInString = gstnStateIdInString;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public java.sql.Timestamp getInvoicePeriodFromDate() {
		return invoicePeriodFromDate;
	}

	public void setInvoicePeriodFromDate(java.sql.Timestamp invoicePeriodFromDate) {
		this.invoicePeriodFromDate = invoicePeriodFromDate;
	}

	public String getInvoicePeriodFromDateInString() {
		return invoicePeriodFromDateInString;
	}

	public void setInvoicePeriodFromDateInString(String invoicePeriodFromDateInString) {
		this.invoicePeriodFromDateInString = invoicePeriodFromDateInString;
	}

	public java.sql.Timestamp getInvoicePeriodToDate() {
		return invoicePeriodToDate;
	}

	public void setInvoicePeriodToDate(java.sql.Timestamp invoicePeriodToDate) {
		this.invoicePeriodToDate = invoicePeriodToDate;
	}

	public String getInvoicePeriodToDateInString() {
		return invoicePeriodToDateInString;
	}

	public void setInvoicePeriodToDateInString(String invoicePeriodToDateInString) {
		this.invoicePeriodToDateInString = invoicePeriodToDateInString;
	}

	public Integer getOrgUId() {
		return orgUId;
	}

	public void setOrgUId(Integer orgUId) {
		this.orgUId = orgUId;
	}

	public String getBillToShipIsChecked() {
		return billToShipIsChecked;
	}

	public void setBillToShipIsChecked(String billToShipIsChecked) {
		this.billToShipIsChecked = billToShipIsChecked;
	}

	public String getShipToCustomerName() {
		return shipToCustomerName;
	}

	public void setShipToCustomerName(String shipToCustomerName) {
		this.shipToCustomerName = shipToCustomerName;
	}

	public String getShipToAddress() {
		return shipToAddress;
	}

	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}

	public String getShipToCity() {
		return shipToCity;
	}

	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}

	public String getShipToState() {
		return shipToState;
	}

	public void setShipToState(String shipToState) {
		this.shipToState = shipToState;
	}

	public String getShipToStateCode() {
		return shipToStateCode;
	}

	public void setShipToStateCode(String shipToStateCode) {
		this.shipToStateCode = shipToStateCode;
	}

	public String getShipToStateCodeId() {
		return shipToStateCodeId;
	}

	public void setShipToStateCodeId(String shipToStateCodeId) {
		this.shipToStateCodeId = shipToStateCodeId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getEcommerce() {
		return ecommerce;
	}

	public void setEcommerce(String ecommerce) {
		this.ecommerce = ecommerce;
	}

	public String getEcommerceGstin() {
		return ecommerceGstin;
	}

	public void setEcommerceGstin(String ecommerceGstin) {
		this.ecommerceGstin = ecommerceGstin;
	}

	public String getReverseCharge() {
		return reverseCharge;
	}

	public void setReverseCharge(String reverseCharge) {
		this.reverseCharge = reverseCharge;
	}

	public List<InvoiceAdditionalChargeDetails> getAddChargesList() {
		return addChargesList;
	}

	public void setAddChargesList(List<InvoiceAdditionalChargeDetails> addChargesList) {
		this.addChargesList = addChargesList;
	}

	public double getInvoiceValueAfterRoundOff() {
		return invoiceValueAfterRoundOff;
	}

	public void setInvoiceValueAfterRoundOff(double invoiceValueAfterRoundOff) {
		this.invoiceValueAfterRoundOff = invoiceValueAfterRoundOff;
	}

	public List<InvoiceCnDnDetails> getCnDnList() {
		return cnDnList;
	}

	public void setCnDnList(List<InvoiceCnDnDetails> cnDnList) {
		this.cnDnList = cnDnList;
	}

	public List<CnDnAdditionalDetails> getCnDnAdditionalList() {
		return cnDnAdditionalList;
	}

	public void setCnDnAdditionalList(List<CnDnAdditionalDetails> cnDnAdditionalList) {
		this.cnDnAdditionalList = cnDnAdditionalList;
	}

	public String getCnDnType() {
		return cnDnType;
	}

	public void setCnDnType(String cnDnType) {
		this.cnDnType = cnDnType;
	}
	
	public String getDeleteYn() {
		return deleteYn;
	}

	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}

	public String getInvCategory() {
		return invCategory;
	}

	public void setInvCategory(String invCategory) {
		this.invCategory = invCategory;
	}

	public String getFooterNote() {
		return footerNote;
	}

	public void setFooterNote(String footerNote) {
		this.footerNote = footerNote;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getShipToPincode() {
		return shipToPincode;
	}

	public void setShipToPincode(String shipToPincode) {
		this.shipToPincode = shipToPincode;
	}

	public String getGeneratedThrough() {
		return generatedThrough;
	}

	public void setGeneratedThrough(String generatedThrough) {
		this.generatedThrough = generatedThrough;
	}

	public String getIsCustomerRegistered() {
		return isCustomerRegistered;
	}

	public void setIsCustomerRegistered(String isCustomerRegistered) {
		this.isCustomerRegistered = isCustomerRegistered;
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
	public java.sql.Timestamp getUploadToJiogstTime() {
		return UploadToJiogstTime;
	}

	public void setUploadToJiogstTime(java.sql.Timestamp uploadToJiogstTime) {
		UploadToJiogstTime = uploadToJiogstTime;
	}

	public java.sql.Timestamp getSaveToGstnTime() {
		return saveToGstnTime;
	}

	public void setSaveToGstnTime(java.sql.Timestamp saveToGstnTime) {
		this.saveToGstnTime = saveToGstnTime;
	}

	public java.sql.Timestamp getSubmitToGstnTime() {
		return submitToGstnTime;
	}

	public void setSubmitToGstnTime(java.sql.Timestamp submitToGstnTime) {
		this.submitToGstnTime = submitToGstnTime;
	}

	public java.sql.Timestamp getFileToGstnTime() {
		return fileToGstnTime;
	}

	public void setFileToGstnTime(java.sql.Timestamp fileToGstnTime) {
		this.fileToGstnTime = fileToGstnTime;
	}

	public double getOtherCharges() {
		return otherCharges;
	}

	public void setOtherCharges(double otherCharges) {
		this.otherCharges = otherCharges;
	}

	public String getModeOfCreation() {
		return modeOfCreation;
	}

	public void setModeOfCreation(String modeOfCreation) {
		this.modeOfCreation = modeOfCreation;
	}

	public int getIterationNo() {
		return iterationNo;
	}

	public void setIterationNo(int iterationNo) {
		this.iterationNo = iterationNo;
	}

	public String getLastRRType() {
		return lastRRType;
	}

	public void setLastRRType(String lastRRType) {
		this.lastRRType = lastRRType;
	}

	public String getLastRR() {
		return lastRR;
	}

	public void setLastRR(String lastRR) {
		this.lastRR = lastRR;
	}

	public String getLastRRInvoiceNumber() {
		return lastRRInvoiceNumber;
	}

	public void setLastRRInvoiceNumber(String lastRRInvoiceNumber) {
		this.lastRRInvoiceNumber = lastRRInvoiceNumber;
	}

	public Integer getLocationStoreId() {
		return locationStoreId;
	}

	public void setLocationStoreId(Integer locationStoreId) {
		this.locationStoreId = locationStoreId;
	}

	public String getRrTypeForCreation() {
		return rrTypeForCreation;
	}

	public void setRrTypeForCreation(String rrTypeForCreation) {
		this.rrTypeForCreation = rrTypeForCreation;
	}

	public String getCreateDocType() {
		return createDocType;
	}

	public void setCreateDocType(String createDocType) {
		this.createDocType = createDocType;
	}

	public String getPlaceOfSupply() {
		return placeOfSupply;
	}

	public void setPlaceOfSupply(String placeOfSupply) {
		this.placeOfSupply = placeOfSupply;
	}

	public String getCndnIsAdditionalChargePresent() {
		return cndnIsAdditionalChargePresent;
	}

	public void setCndnIsAdditionalChargePresent(String cndnIsAdditionalChargePresent) {
		this.cndnIsAdditionalChargePresent = cndnIsAdditionalChargePresent;
	}

	
	
	
	
}
