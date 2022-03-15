package com.reliance.gstn.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

public class InventoryProductTable implements Serializable{
	 
	private static final long serialVersionUID = 1L;

	@SafeHtml(message = "HTML elements not allowed")
	private Integer id;	
	
	@SafeHtml(message = "HTML elements not allowed")
	private String name;

	@NotEmpty(message = "Unit Of Measurement cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	private String unitOfMeasurement;
	
	@SafeHtml(message = "HTML elements not allowed")
	private String otherUOM;

	@SafeHtml(message = "HTML elements not allowed")
	private Double currentStock;

	@SafeHtml(message = "HTML elements not allowed")
	private Double currentStockValue ;

	@SafeHtml(message = "HTML elements not allowed")
	private Integer storeId;

	@SafeHtml(message = "HTML elements not allowed")
	private Double modifiedQty;

	@SafeHtml(message = "HTML elements not allowed")
	private Double modifiedStockValue ;
	
	private int fromStoreId;
	private int  toStoreId;
	
	@SafeHtml(message = "HTML elements not allowed")
	private String transactionDate;
	
	private String actionFrom;
	
	private String documentNo;
	
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

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public String getOtherUOM() {
		return otherUOM;
	}

	public void setOtherUOM(String otherUOM) {
		this.otherUOM = otherUOM;
	}



	public Double getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(Double currentStock) {
		this.currentStock = currentStock;
	}

	public Double getCurrentStockValue() {
		return currentStockValue;
	}

	public void setCurrentStockValue(Double currentStockValue) {
		this.currentStockValue = currentStockValue;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	

	public Double getModifiedQty() {
		return modifiedQty;
	}

	public void setModifiedQty(Double modifiedQty) {
		this.modifiedQty = modifiedQty;
	}

	public Double getModifiedStockValue() {
		return modifiedStockValue;
	}

	public void setModifiedStockValue(Double modifiedStockValue) {
		this.modifiedStockValue = modifiedStockValue;
	}

	public int getFromStoreId() {
		return fromStoreId;
	}

	public void setFromStoreId(int fromStoreId) {
		this.fromStoreId = fromStoreId;
	}

	public int getToStoreId() {
		return toStoreId;
	}

	public void setToStoreId(int toStoreId) {
		this.toStoreId = toStoreId;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
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
