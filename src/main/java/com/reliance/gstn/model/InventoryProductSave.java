package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.List;

public class InventoryProductSave implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
	private String inventoryType;
	private List<InventoryProductTable> productList;
	private String narration;
	private String reason;
	
	private Integer uniqueSequenceid;
	
	public String getInventoryType() {
		return inventoryType;
	}
	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}
	public List<InventoryProductTable> getProductList() {
		return productList;
	}
	public void setProductList(List<InventoryProductTable> productList) {
		this.productList = productList;
	}
	public String getNarration() {
		return narration;
	}
	public void setNarration(String narration) {
		this.narration = narration;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getUniqueSequenceid() {
		return uniqueSequenceid;
	}
	public void setUniqueSequenceid(Integer uniqueSequenceid) {
		this.uniqueSequenceid = uniqueSequenceid;
	}

	
}
