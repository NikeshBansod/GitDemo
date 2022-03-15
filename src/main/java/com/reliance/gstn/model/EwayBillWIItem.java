package com.reliance.gstn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "ewb_wi_item")
public class EwayBillWIItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2820750654186422965L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Long itemId;

	@NotEmpty(message = "Product Name cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "product_name")
	private String productName;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "product_desc")
	private String productDesc;

	@NotEmpty(message = "HSN Code cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "hsn_code")
	private String hsnCode;

	@Column(name = "quantity")
	private Double quantity;

	// @NotEmpty(message = "Quantity Unit cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "quantity_unit")
	private String quantityUnit;

	@Column(name = "sgst_rate")
	private Double sgstRate;

	@Column(name = "cgst_rate")
	private Double cgstRate;

	@Column(name = "igst_rate")
	private Double igstRate;

	@Column(name = "cess_rate")
	private Double cessRate;
	
	

	@Column(name = "cess_ad_vol")
	private Integer cessAdVol;

	@Column(name = "taxable_amount")
	private Double taxableAmount;
	
	
	@Column(name = "sgst_value")
	private Double sgstValue;

	@Column(name = "cgst_value")
	private Double cgstValue;

	@Column(name = "igst_value")
	private Double igstValue;

	@Column(name = "cess_value")
	private Double cessValue;
	
	@Column(name = "cgstsgst_rate")
	private Double cgstsgstRate;

	@Column(name = "cessadvol_rate")
	private Double cessadvolrate;
	
	@Column(name = "cessadvol_value")
	private Double cessadvolAmount;
	
	@Column(name = "cessnonadvol_rate")
	private Double cessnonadvolrate;
	
	@Column(name = "cessnonadvol_value")
	private Double cessnonadvolAmount;

	@ManyToOne
	@JoinColumn(name = "eway_bill_id")
	private EwayBillWITransaction ewayBillWITransaction;

	@Column(name = "CREATED_ON", updatable=false)
	private java.sql.Timestamp createdOn;

	@Column(name = "CREATED_BY", updatable=false)
	private String createdBy;

	@Column(name = "UPDATED_ON")
	private java.sql.Timestamp updatedOn;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
	private Integer hsnId;

	
	

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getQuantityUnit() {
		return quantityUnit;
	}

	public void setQuantityUnit(String quantityUnit) {
		this.quantityUnit = quantityUnit;
	}

	public Double getSgstRate() {
		return sgstRate;
	}

	public void setSgstRate(Double sgstRate) {
		this.sgstRate = sgstRate;
	}

	public Double getCgstRate() {
		return cgstRate;
	}

	public void setCgstRate(Double cgstRate) {
		this.cgstRate = cgstRate;
	}

	public Double getIgstRate() {
		return igstRate;
	}

	public void setIgstRate(Double igstRate) {
		this.igstRate = igstRate;
	}

	public Double getCessRate() {
		return cessRate;
	}

	public void setCessRate(Double cessRate) {
		this.cessRate = cessRate;
	}

	public Integer getCessAdVol() {
		return cessAdVol;
	}

	public void setCessAdVol(Integer cessAdVol) {
		this.cessAdVol = cessAdVol;
	}

	public Double getTaxableAmount() {
		return taxableAmount;
	}

	public void setTaxableAmount(Double taxableAmount) {
		this.taxableAmount = taxableAmount;
	}

	public EwayBillWITransaction getEwayBillWITransaction() {
		return ewayBillWITransaction;
	}

	public void setEwayBillWITransaction(EwayBillWITransaction ewayBillWITransaction) {
		this.ewayBillWITransaction = ewayBillWITransaction;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof EwayBillWIItem))
			return false;
		return itemId != null && itemId.equals(((EwayBillWIItem) obj).itemId);
	}

	public Integer getHsnId() {
		return hsnId;
	}

	public void setHsnId(Integer hsnId) {
		this.hsnId = hsnId;
	}

	public Double getSgstValue() {
		return sgstValue;
	}

	public void setSgstValue(Double sgstValue) {
		this.sgstValue = sgstValue;
	}

	public Double getCgstValue() {
		return cgstValue;
	}

	public void setCgstValue(Double cgstValue) {
		this.cgstValue = cgstValue;
	}

	public Double getIgstValue() {
		return igstValue;
	}

	public void setIgstValue(Double igstValue) {
		this.igstValue = igstValue;
	}

	public Double getCessValue() {
		return cessValue;
	}

	public void setCessValue(Double cessValue) {
		this.cessValue = cessValue;
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

	public Double getCessadvolAmount() {
		return cessadvolAmount;
	}

	public void setCessadvolAmount(Double cessadvolAmount) {
		this.cessadvolAmount = cessadvolAmount;
	}

	public Double getCessnonadvolrate() {
		return cessnonadvolrate;
	}

	public void setCessnonadvolrate(Double cessnonadvolrate) {
		this.cessnonadvolrate = cessnonadvolrate;
	}

	public Double getCessnonadvolAmount() {
		return cessnonadvolAmount;
	}

	public void setCessnonadvolAmount(Double cessnonadvolAmount) {
		this.cessnonadvolAmount = cessnonadvolAmount;
	}
	
	
	
	

}
