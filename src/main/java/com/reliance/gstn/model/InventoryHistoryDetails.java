package com.reliance.gstn.model;

import java.io.Serializable;

public class InventoryHistoryDetails implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	
	private String naration;
	private String reason;
	private String quantity;
	private String transaction_Date;
	private String action;
	private String document_No;
	private String uom;
	private String product_Rate;
	private String purchase_Rate;
	private String purchase_Uom;
	private String opening_Stock;
	private String opening_Stock_Value;
	private String current_Stock;
	private String current_Stock_Value;
	private String gstin_Location;
	private String gstin_No;
	private String gstin_User_Name;
	
	public String getNaration() {
		return naration;
	}
	
	public void setNaration(String naration) {
		this.naration = naration;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getQuantity() {
		return quantity;
	}
	
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public String getTransaction_Date() {
		return transaction_Date;
	}
	public void setTransaction_Date(String transaction_Date) {
		this.transaction_Date = transaction_Date;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getDocument_No() {
		return document_No;
	}
	
	public void setDocument_No(String document_No) {
		this.document_No = document_No;
	}
	
	public String getUom() {
		return uom;
	}
	
	public void setUom(String uom) {
		this.uom = uom;
	}
	
	public String getProduct_Rate() {
		return product_Rate;
	}
	
	public void setProduct_Rate(String product_Rate) {
		this.product_Rate = product_Rate;
	}
	
	public String getPurchase_Rate() {
		return purchase_Rate;
	}
	public void setPurchase_Rate(String purchase_Rate) {
		this.purchase_Rate = purchase_Rate;
	}
	
	public String getPurchase_Uom() {
		return purchase_Uom;
	}
	
	public void setPurchase_Uom(String purchase_Uom) {
		this.purchase_Uom = purchase_Uom;
	}
	
	public String getOpening_Stock() {
		return opening_Stock;
	}
	
	public void setOpening_Stock(String opening_Stock) {
		this.opening_Stock = opening_Stock;
	}
	
	public String getOpening_Stock_Value() {
		return opening_Stock_Value;
	}
	
	public void setOpening_Stock_Value(String opening_Stock_Value) {
		this.opening_Stock_Value = opening_Stock_Value;
	}
	
	public String getCurrent_Stock() {
		return current_Stock;
	}
	
	public void setCurrent_Stock(String current_Stock) {
		this.current_Stock = current_Stock;
	}
	
	public String getCurrent_Stock_Value() {
		return current_Stock_Value;
	}
	public void setCurrent_Stock_Value(String current_Stock_Value) {
		this.current_Stock_Value = current_Stock_Value;
	}
	
	public String getGstin_Location() {
		return gstin_Location;
	}
	public void setGstin_Location(String gstin_Location) {
		this.gstin_Location = gstin_Location;
	}
	
	public String getGstin_No() {
		return gstin_No;
	}
	
	public void setGstin_No(String gstin_No) {
		this.gstin_No = gstin_No;
	}
	
	public String getGstin_User_Name() {
		return gstin_User_Name;
	}
	
	public void setGstin_User_Name(String gstin_User_Name) {
		this.gstin_User_Name = gstin_User_Name;
	}
	
	
	
	
}
