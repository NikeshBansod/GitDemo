package com.reliance.gstn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "gst_inventory_details")
public class InventoryDetailsEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
     
    @Column(name = "PRODUCT_ID")
    private Integer productId;      
  
    @Column(name = "CREDIT_DEBIT_VALUES")
    private Double creditDebitValues;
 
    @SafeHtml(message = "HTML elements not allowed")
    @Column(name = "UPDATE_TYPE")
    private String updateType;

    @Column(name = "UPDATED_ON")
    private java.sql.Timestamp updatedOn;

    @SafeHtml(message = "HTML elements not allowed")
    @Column(name = "NARATION")
    private String narration;

    @SafeHtml(message = "HTML elements not allowed")
    @Column(name = "SELECTED_REASON")
    private String selectedReason;

    @Column(name = "QUANTITY")
    private Double quantity;

    @Column(name = "TRANSACTION_DATE")
    private java.sql.Timestamp transactionDate;
    
    @Column(name = "ACTION")
    private String actionFrom;
    
    @Column(name = "DOCUMENT_NO")
    private String documentNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Double getCreditDebitValues() {
		return creditDebitValues;
	}

	public void setCreditDebitValues(Double creditDebitValues) {
		this.creditDebitValues = creditDebitValues;
	}

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	public java.sql.Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(java.sql.Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getSelectedReason() {
		return selectedReason;
	}

	public void setSelectedReason(String selectedReason) {
		this.selectedReason = selectedReason;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public java.sql.Timestamp getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(java.sql.Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getActionFrom() {
		return actionFrom;
	}

	public void setActionFrom(String actionFrom) {
		this.actionFrom = actionFrom;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

}
