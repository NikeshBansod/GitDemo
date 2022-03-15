package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author @kshay Mohite
 *
 */
public class CompletePurchaseEntry implements Serializable {

	private static final long serialVersionUID = 8104246375412549044L;

	//Supplier PART A
	private PurchaseEntryDetails purchaseEntryDetails;
	
	//Purchaser PART B & C
	private UserMaster user;	
	
	private GSTINDetails gstinDetails;
	
	private String amtInWords;
	
	private String renderData;

	private Map<String,Map<String,Double>> gstMap = new HashMap<String,Map<String,Double>>();
	
	
	public PurchaseEntryDetails getPurchaseEntryDetails() {
		return purchaseEntryDetails;
	}

	public void setPurchaseEntryDetails(PurchaseEntryDetails purchaseEntryDetails) {
		this.purchaseEntryDetails = purchaseEntryDetails;
	}

	public UserMaster getUser() {
		return user;
	}

	public void setUser(UserMaster user) {
		this.user = user;
	}

	public GSTINDetails getGstinDetails() {
		return gstinDetails;
	}

	public void setGstinDetails(GSTINDetails gstinDetails) {
		this.gstinDetails = gstinDetails;
	}

	public String getAmtInWords() {
		return amtInWords;
	}

	public void setAmtInWords(String amtInWords) {
		this.amtInWords = amtInWords;
	}

	public String getRenderData() {
		return renderData;
	}

	public void setRenderData(String renderData) {
		this.renderData = renderData;
	}

	public Map<String, Map<String, Double>> getGstMap() {
		return gstMap;
	}

	public void setGstMap(Map<String, Map<String, Double>> gstMap) {
		this.gstMap = gstMap;
	}
	
	
	
}
