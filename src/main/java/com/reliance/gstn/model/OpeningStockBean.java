/**
 * 
 */
package com.reliance.gstn.model;

import java.io.Serializable;

/**
 * @author Dibyendu.Mukherjee
 *
 */
public class OpeningStockBean implements Serializable{
	
	private static final long serialVersionUID = -6458201358070745185L;
	
	private Integer gstnId ;
	private Integer storeId ;
	private Double openingStock;
	private Double openingStockValue;
	private Double currentStock;
	public Double getOpeningStock() {
		return openingStock;
	}
	public void setOpeningStock(Double openingStock) {
		this.openingStock = openingStock;
	}
	public Double getCurrentStock() {
		return currentStock;
	}
	public void setCurrentStock(Double currentStock) {
		this.currentStock = currentStock;
	}
	private Double currentStockValue;
	private String inventoryUpdate;
	
	
	
	public Integer getGstnId() {
		return gstnId;
	}
	public void setGstnId(Integer gstnId) {
		this.gstnId = gstnId;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	
	public Double getOpeningStockValue() {
		return openingStockValue;
	}
	public void setOpeningStockValue(Double openingStockValue) {
		this.openingStockValue = openingStockValue;
	}
	
	public Double getCurrentStockValue() {
		return currentStockValue;
	}
	public void setCurrentStockValue(Double currentStockValue) {
		this.currentStockValue = currentStockValue;
	}
	public String getInventoryUpdate() {
		return inventoryUpdate;
	}
	public void setInventoryUpdate(String inventoryUpdate) {
		this.inventoryUpdate = inventoryUpdate;
	}
	
	
	

}
