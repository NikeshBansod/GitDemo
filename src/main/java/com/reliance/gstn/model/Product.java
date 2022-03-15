/**
 * 
 */
package com.reliance.gstn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.google.gson.annotations.Expose;

/**
 * @author Nikesh.Bansod
 *
 */

@Entity
@Table(name = "gst_product_master")
public class Product implements Serializable {

	private static final long serialVersionUID = -7223003499243317108L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@NotEmpty(message = "Product Name cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "PRODUCT_NAME")
	private String name;

	@NotEmpty(message = "HSN Code cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "HSN_CODE")
	private String hsnCode;

	@NotEmpty(message = "HSN Description cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "HSN_DESC")
	private String hsnDescription;

	@NotEmpty(message = "Unit Of Measurement cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "UNIT_OF_MEASUREMENT")
	private String unitOfMeasurement;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "OTHER_UOM")
	private String otherUOM;

	@NotNull(message = "Product Rate cannot be empty")
	@Column(name = "PRODUCT_RATE")
	private Double productRate;

	// @Pattern(regexp="0|0.25|3|5|12|18|28")
	@NotNull(message = "Product IGST cannot be empty")
	@Column(name = "PRODUCT_IGST")
	private Double productIgst;

	@Column(name = "REF_USER_ID")
	private Integer referenceId;

	@Column(name = "CREATED_ON", updatable = false)
	private java.sql.Timestamp createdOn;

	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;

	@Column(name = "UPDATED_ON")
	private java.sql.Timestamp updatedOn;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "REF_ORG_ID")
	private Integer refOrgId;

	@Column(name = "HSN_CODE_PK_ID")
	private Integer hsnCodePkId;

	@Transient
	private String tempUom;

	@Column(name = "ADVOL_CESS")
	private Double advolCess;

	@Column(name = "NON_ADVOL_CESS")
	private Double nonAdvolCess;

	@Column(name = "PURCHASE_RATE")
	private Double purchaseRate;

	@Column(name = "PURCHASE_UOM")
	private String purchaseUOM;

	@Column(name = "PURCHASE_OTHER_UOM")
	private String purchaseOtherUOM;

	@Column(name = "STORE_ID")
	private Integer storeId;

	@Column(name = "OPENING_STOCK")
	private Double openingStock;

	@Column(name = "OPENING_STOCK_VALUE")
	private Double openingStockValue;

	@Column(name = "CURRENT_STOCK")
	private Double currentStock;

	@Column(name = "CURRENT_STOCK_VALUE")
	private Double currentStockValue;

	@Column(name = "INVENTORY_UPDATE")
	private String inventoryUpdateFlag;

	@Transient
	private String existingName;
	
	@Transient
	private String storeName;

	@Transient
	private String gstin;
	
	@Transient
	private String tempCreditOrDebit;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public String getHsnDescription() {
		return hsnDescription;
	}

	public void setHsnDescription(String hsnDescription) {
		this.hsnDescription = hsnDescription;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public Double getProductRate() {
		return productRate;
	}

	public void setProductRate(Double productRate) {
		this.productRate = productRate;
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

	public Integer getRefOrgId() {
		return refOrgId;
	}

	public void setRefOrgId(Integer refOrgId) {
		this.refOrgId = refOrgId;
	}

	public String getOtherUOM() {
		return otherUOM;
	}

	public void setOtherUOM(String otherUOM) {
		this.otherUOM = otherUOM;
	}

	public Double getProductIgst() {
		return productIgst;
	}

	public void setProductIgst(Double productIgst) {
		this.productIgst = productIgst;
	}

	public Integer getHsnCodePkId() {
		return hsnCodePkId;
	}

	public void setHsnCodePkId(Integer hsnCodePkId) {
		this.hsnCodePkId = hsnCodePkId;
	}

	public String getTempUom() {
		return tempUom;
	}

	public void setTempUom(String tempUom) {
		this.tempUom = tempUom;
	}

	public double getAdvolCess() {
		return advolCess;
	}

	public void setAdvolCess(double advolCess) {
		this.advolCess = advolCess;
	}

	public double getNonAdvolCess() {
		return nonAdvolCess;
	}

	public void setNonAdvolCess(double nonAdvolCess) {
		this.nonAdvolCess = nonAdvolCess;
	}

	public Double getPurchaseRate() {
		return purchaseRate;
	}

	public void setPurchaseRate(Double purchaseRate) {
		this.purchaseRate = purchaseRate;
	}

	public String getPurchaseUOM() {
		return purchaseUOM;
	}

	public void setPurchaseUOM(String purchaseUOM) {
		this.purchaseUOM = purchaseUOM;
	}

	public String getPurchaseOtherUOM() {
		return purchaseOtherUOM;
	}

	public void setPurchaseOtherUOM(String purchaseOtherUOM) {
		this.purchaseOtherUOM = purchaseOtherUOM;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Double getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(Double currentStock) {
		this.currentStock = currentStock;
	}

	public Double getOpeningStockValue() {
		return openingStockValue;
	}

	public void setOpeningStockValue(Double openingStockValue) {
		this.openingStockValue = openingStockValue;
	}

	public Double getOpeningStock() {
		return openingStock;
	}

	public void setOpeningStock(Double openingStock) {
		this.openingStock = openingStock;
	}

	public Double getCurrentStockValue() {
		return currentStockValue;
	}

	public void setCurrentStockValue(Double currentStockValue) {
		this.currentStockValue = currentStockValue;
	}

	public String getInventoryUpdateFlag() {
		return inventoryUpdateFlag;
	}

	public void setInventoryUpdateFlag(String inventoryUpdateFlag) {
		this.inventoryUpdateFlag = inventoryUpdateFlag;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public void setAdvolCess(Double advolCess) {
		this.advolCess = advolCess;
	}

	public void setNonAdvolCess(Double nonAdvolCess) {
		this.nonAdvolCess = nonAdvolCess;
	}
	
	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}
	
	

	public String getTempCreditOrDebit() {
		return tempCreditOrDebit;
	}

	public void setTempCreditOrDebit(String tempCreditOrDebit) {
		this.tempCreditOrDebit = tempCreditOrDebit;
	}

	
	public String getExistingName() {
		return existingName;
	}

	public void setExistingName(String existingName) {
		this.existingName = existingName;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if (obj == null || getClass() != obj.getClass())
			isEqual = false;

		Product other = (Product) obj;
		boolean refOrgIdDescision = false;

		if (null == this.refOrgId || null == other.refOrgId) {
			refOrgIdDescision = true;
		} else {
			refOrgIdDescision = (this.refOrgId.intValue() == other.refOrgId.intValue());
		}

		if (refOrgIdDescision && this.name.equalsIgnoreCase(other.name)) {
			isEqual = true;
		}
		return isEqual;
	}

}
