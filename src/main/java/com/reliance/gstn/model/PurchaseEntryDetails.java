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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
/**
 * @author @kshay Mohite
 *
 */
@Entity
@Table(name = "gst_purchase_entry_details")
public class PurchaseEntryDetails implements Serializable {

	private static final long serialVersionUID = -7726561053862601149L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purchase_entry_details_id")
	private int purchaseEntryDetailsId;

	@Column(name = "gstn_state_id")
	private Integer gstnStateId;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "gstn_id")
	private String gstnStateIdInString;

	@Column(name = "location")
	private String location;
	
	@Column(name = "invoice_for")
	private String invoiceFor;

	@Column(name = "dealer_type")
	private String dealerType;	

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "supplier_gstin")
	private String supplierGstin;
	
	@NotEmpty(message = "Name of the Supplier cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "supplier_name")
	private String supplierName;

	@NotEmpty(message = "Pincode of Supplier cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "supplier_pincode")
	private String supplierPincode;

	@NotEmpty(message = "State of Supplier cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "supplier_state")
	private String supplierState;

	@Column(name = "supplier_state_code")
	private String supplierStateCode;

	@Column(name = "supplier_state_code_id")
	private String supplierStateCodeId;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "supplier_address")
	private String supplierAddress;
	
	@Column(name = "purchase_date")
	private java.sql.Timestamp purchaseDate;
	
	@Transient
	@SafeHtml(message = "HTML elements not allowed")
	private String purchaseDateInString;	

	@NotEmpty(message = "Invoice Number cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "invoice_number")
	private String invoiceNumber;
	
	@Column(name = "purchase_entry_invoice_number")
	private String purchaseEntryGeneratedInvoiceNumber;
	
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

	@Column(name = "reverse_charge_ischecked")
	private String reverseChargeApplicable;
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "supplier_doc_invoice_no")
	private String supplierDocInvoiceNo;
	
	@Column(name = "supplier_doc_invoice_date")
	private java.sql.Timestamp supplierDocInvoiceDate;

	@Transient
	@SafeHtml(message = "HTML elements not allowed")
	private String supplierDocInvoiceDateInString;

	@Column(name = "billToShip_IsChecked")
	private String billToShipIsChecked;	

	@Transient
	@SafeHtml(message = "HTML elements not allowed")
	private String billingAddress;

	@NotEmpty(message = "Shipping Address cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "purchaser_shipping_address")
	private String purchaserShippingAddress;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "po_id")
	private String poDetails;

	@Column(name = "amount_after_discount")
	private double amountAfterDiscount;

	@Column(name = "total_tax")
	private double totalTax;

	@Column(name = "invoice_value")
	private double invoiceValue;
	
	@Column(name = "ref_user_id")
	private Integer referenceId;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "ref_org_uId")
	private Integer orgUId;

	@Column(name = "invoice_value_after_round_off")
	private double invoiceValueAfterRoundOff;
	
	@Column(name = "delete_status")
	private String deleteYn = "N";	

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="gst_purchase_entry_service_mapping",joinColumns = @JoinColumn(name = "purchase_entry_details_id"),
            inverseJoinColumns = @JoinColumn(name = "service_good_id",nullable = true))
	private List<PurchaseEntryServiceOrGoodDetails> serviceList = new ArrayList<PurchaseEntryServiceOrGoodDetails>();

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="gst_purchase_entry_add_chgs_mapping",joinColumns = @JoinColumn(name = "purchase_entry_details_id",nullable = true),
            inverseJoinColumns = @JoinColumn(name = "purchase_entry_additional_charge_id",nullable = true))
	private List<PurchaseEntryAdditionalChargeDetails> addChargesList = new ArrayList<PurchaseEntryAdditionalChargeDetails>();
	
	@Column(name = "footer_note")
	@SafeHtml(message = "HTML elements not allowed")
	private String footerNote;	

	@Column(name = "created_on", updatable=false)
	private java.sql.Timestamp createdOn;

	@Column(name = "created_by", updatable=false)
	private String createdBy;

	@Column(name = "updated_on")
	private java.sql.Timestamp updatedOn;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "place_of_supply_name")
	private String placeOfSupplyName;

	@Column(name = "place_of_supply_code")
	private String placeOfSupplyCode;

	@Column(name = "place_of_supply_id")
	private String placeOfSupplyId;

	
	
	

	public int getPurchaseEntryDetailsId() {
		return purchaseEntryDetailsId;
	}

	public void setPurchaseEntryDetailsId(int purchaseEntryDetailsId) {
		this.purchaseEntryDetailsId = purchaseEntryDetailsId;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getInvoiceFor() {
		return invoiceFor;
	}

	public void setInvoiceFor(String invoiceFor) {
		this.invoiceFor = invoiceFor;
	}

	public String getDealerType() {
		return dealerType;
	}

	public void setDealerType(String dealerType) {
		this.dealerType = dealerType;
	}

	public String getSupplierGstin() {
		return supplierGstin;
	}

	public void setSupplierGstin(String supplierGstin) {
		this.supplierGstin = supplierGstin;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierPincode() {
		return supplierPincode;
	}

	public void setSupplierPincode(String supplierPincode) {
		this.supplierPincode = supplierPincode;
	}

	public String getSupplierState() {
		return supplierState;
	}

	public void setSupplierState(String supplierState) {
		this.supplierState = supplierState;
	}

	public String getSupplierStateCode() {
		return supplierStateCode;
	}

	public void setSupplierStateCode(String supplierStateCode) {
		this.supplierStateCode = supplierStateCode;
	}

	public String getSupplierStateCodeId() {
		return supplierStateCodeId;
	}

	public void setSupplierStateCodeId(String supplierStateCodeId) {
		this.supplierStateCodeId = supplierStateCodeId;
	}

	public String getSupplireAddress() {
		return supplierAddress;
	}

	public void setSupplireAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public java.sql.Timestamp getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(java.sql.Timestamp purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getPurchaseDateInString() {
		return purchaseDateInString;
	}

	public void setPurchaseDateInString(String purchaseDateInString) {
		this.purchaseDateInString = purchaseDateInString;
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

	public void setInvoicePeriodFromDateInString(
			String invoicePeriodFromDateInString) {
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

	public String getReverseChargeApplicable() {
		return reverseChargeApplicable;
	}

	public void setReverseChargeApplicable(String reverseChargeApplicable) {
		this.reverseChargeApplicable = reverseChargeApplicable;
	}

	public String getSupplierDocInvoiceNo() {
		return supplierDocInvoiceNo;
	}

	public void setSupplierDocInvoiceNo(String supplierDocInvoiceNo) {
		this.supplierDocInvoiceNo = supplierDocInvoiceNo;
	}

	public java.sql.Timestamp getSupplierDocInvoiceDate() {
		return supplierDocInvoiceDate;
	}

	public void setSupplierDocInvoiceDate(java.sql.Timestamp supplierDocInvoiceDate) {
		this.supplierDocInvoiceDate = supplierDocInvoiceDate;
	}

	public String getBillToShipIsChecked() {
		return billToShipIsChecked;
	}

	public void setBillToShipIsChecked(String billToShipIsChecked) {
		this.billToShipIsChecked = billToShipIsChecked;
	}

	public String getPurchaserShippingAddress() {
		return purchaserShippingAddress;
	}

	public void setPurchaserShippingAddress(String purchaserShippingAddress) {
		this.purchaserShippingAddress = purchaserShippingAddress;
	}

	public String getPoDetails() {
		return poDetails;
	}

	public void setPoDetails(String poDetails) {
		this.poDetails = poDetails;
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

	public Integer getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Integer referenceId) {
		this.referenceId = referenceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getOrgUId() {
		return orgUId;
	}

	public void setOrgUId(Integer orgUId) {
		this.orgUId = orgUId;
	}

	public double getInvoiceValueAfterRoundOff() {
		return invoiceValueAfterRoundOff;
	}

	public void setInvoiceValueAfterRoundOff(double invoiceValueAfterRoundOff) {
		this.invoiceValueAfterRoundOff = invoiceValueAfterRoundOff;
	}

	public String getDeleteYn() {
		return deleteYn;
	}

	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}

	public String getSupplierAddress() {
		return supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public List<PurchaseEntryServiceOrGoodDetails> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<PurchaseEntryServiceOrGoodDetails> serviceList) {
		this.serviceList = serviceList;
	}

	public List<PurchaseEntryAdditionalChargeDetails> getAddChargesList() {
		return addChargesList;
	}

	public void setAddChargesList(
			List<PurchaseEntryAdditionalChargeDetails> addChargesList) {
		this.addChargesList = addChargesList;
	}

	public String getFooterNote() {
		return footerNote;
	}

	public void setFooterNote(String footerNote) {
		this.footerNote = footerNote;
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

	public String getSupplierDocInvoiceDateInString() {
		return supplierDocInvoiceDateInString;
	}

	public void setSupplierDocInvoiceDateInString(
			String supplierDocInvoiceDateInString) {
		this.supplierDocInvoiceDateInString = supplierDocInvoiceDateInString;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getPurchaseEntryGeneratedInvoiceNumber() {
		return purchaseEntryGeneratedInvoiceNumber;
	}

	public void setPurchaseEntryGeneratedInvoiceNumber(
			String purchaseEntryGeneratedInvoiceNumber) {
		this.purchaseEntryGeneratedInvoiceNumber = purchaseEntryGeneratedInvoiceNumber;
	}

	public String getPlaceOfSupplyName() {
		return placeOfSupplyName;
	}

	public void setPlaceOfSupplyName(String placeOfSupplyName) {
		this.placeOfSupplyName = placeOfSupplyName;
	}

	public String getPlaceOfSupplyCode() {
		return placeOfSupplyCode;
	}

	public void setPlaceOfSupplyCode(String placeOfSupplyCode) {
		this.placeOfSupplyCode = placeOfSupplyCode;
	}

	public String getPlaceOfSupplyId() {
		return placeOfSupplyId;
	}

	public void setPlaceOfSupplyId(String placeOfSupplyId) {
		this.placeOfSupplyId = placeOfSupplyId;
	}
	
	

	/*@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "discount_type")
	private String discountType;

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
*/
	/*	 
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
	 */
}
