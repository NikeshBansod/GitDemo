package com.reliance.gstn.model;

public class InventoryDetails{

    private Integer id;

    private Integer productId;      
  
    private Double creditDebitValues;
 
    private String updateType;

    private java.sql.Timestamp updatedOn;

    private String narration;

    private String selectedReason;
    
    private Double quantity;
    
    private java.sql.Timestamp transactionDate;
    
    private String actionFrom;
    
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
