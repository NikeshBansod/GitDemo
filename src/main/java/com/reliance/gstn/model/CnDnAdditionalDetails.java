/**
 * 
 */
package com.reliance.gstn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.SafeHtml;

/**
 * @author Nikesh.Bansod
 *
 */
@Entity
@Table(name = "gst_invoice_cn_dn_additional_details")
public class CnDnAdditionalDetails implements Serializable{

	private static final long serialVersionUID = -2406539957822246385L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_cn_dn_additional_pk_id")
	private Integer id;
	
	@Column(name = "cn_dn_type")
	private String cnDnType;
	
	@Column(name = "created_on", updatable=false)
	private java.sql.Timestamp createdOn;

	@Column(name = "created_by", updatable=false)
	private String createdBy;
	
	@Column(name = "iteration_no")
	private Integer iterationNo;
	
	@Column(name = "reason")
	private String reason;
	
	@Column(name = "regime")
	private String regime;
	
	@Column(name = "invoice_value")
	private double invoiceValue;
	
	@Column(name = "invoice_date")
	private java.sql.Timestamp invoiceDate;
	
	@Column(name = "invoice_number")
	private String invoiceNumber;
	
	@Column(name = "invoice_value_after_round_off")
	private double invoiceValueAfterRoundOff;
	
	@Column(name = "amount_after_discount")
	private double amountAfterDiscount;

	@Column(name = "total_tax")
	private double totalTax;
	
	@Column(name = "ref_org_uId")
	private Integer orgUId;
	
	@Column(name = "generated_through")
	@SafeHtml(message = "HTML elements not allowed")
	private String generatedThrough;
	
	@Column(name = "footer")
	@SafeHtml(message = "HTML elements not allowed")
	private String footer;
	
	@Column(name = "is_additional_charge_present")
	@SafeHtml(message = "HTML elements not allowed")
	private String isAdditionalChargePresent = "Y";

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

	public double getInvoiceValue() {
		return invoiceValue;
	}

	public void setInvoiceValue(double invoiceValue) {
		this.invoiceValue = invoiceValue;
	}

	public java.sql.Timestamp getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(java.sql.Timestamp invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public double getInvoiceValueAfterRoundOff() {
		return invoiceValueAfterRoundOff;
	}

	public void setInvoiceValueAfterRoundOff(double invoiceValueAfterRoundOff) {
		this.invoiceValueAfterRoundOff = invoiceValueAfterRoundOff;
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

	public Integer getOrgUId() {
		return orgUId;
	}

	public void setOrgUId(Integer orgUId) {
		this.orgUId = orgUId;
	}

	public String getGeneratedThrough() {
		return generatedThrough;
	}

	public void setGeneratedThrough(String generatedThrough) {
		this.generatedThrough = generatedThrough;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getIsAdditionalChargePresent() {
		return isAdditionalChargePresent;
	}

	public void setIsAdditionalChargePresent(String isAdditionalChargePresent) {
		this.isAdditionalChargePresent = isAdditionalChargePresent;
	}
	
	
}
