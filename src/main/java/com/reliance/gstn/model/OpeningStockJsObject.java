package com.reliance.gstn.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

public class OpeningStockJsObject implements Serializable{
	
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
	private Double openingStock;

	@SafeHtml(message = "HTML elements not allowed")
	private Double openingStockValue ;

	@SafeHtml(message = "HTML elements not allowed")
	private Integer storeId;

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

	

	public Double getCurrentStockValue() {
		return currentStockValue;
	}

	public void setCurrentStockValue(Double currentStockValue) {
		this.currentStockValue = currentStockValue;
	}

	

	public Double getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(Double currentStock) {
		this.currentStock = currentStock;
	}

	public Double getOpeningStock() {
		return openingStock;
	}

	public void setOpeningStock(Double openingStock) {
		this.openingStock = openingStock;
	}

	public Double getOpeningStockValue() {
		return openingStockValue;
	}

	public void setOpeningStockValue(Double openingStockValue) {
		this.openingStockValue = openingStockValue;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	
	
	

}
