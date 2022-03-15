/**
 * 
 */
package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikesh.Bansod
 *
 */
public class CompleteInvoice implements Serializable{

	private static final long serialVersionUID = 8361709194650346978L;


	private InvoiceDetails invoiceDetails;
	
	private GSTINDetails gstinDetails;
	
	private UserMaster user;
	
	private Map<String,Map<String,Double>> gstMap = new HashMap<String,Map<String,Double>>();
	
	private String customerStateCode;
	
	private String amtInWords;
	
	private String renderData;
	//need for dashborad invoice number 18 dec 2018
	private String invoiceNoForDashboard;

	public InvoiceDetails getInvoiceDetails() {
		return invoiceDetails;
	}

	public void setInvoiceDetails(InvoiceDetails invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}

	public GSTINDetails getGstinDetails() {
		return gstinDetails;
	}

	public void setGstinDetails(GSTINDetails gstinDetails) {
		this.gstinDetails = gstinDetails;
	}

	public UserMaster getUser() {
		return user;
	}

	public void setUser(UserMaster user) {
		this.user = user;
	}

	public Map<String, Map<String, Double>> getGstMap() {
		return gstMap;
	}

	public void setGstMap(Map<String, Map<String, Double>> gstMap) {
		this.gstMap = gstMap;
	}

	public String getCustomerStateCode() {
		return customerStateCode;
	}

	public void setCustomerStateCode(String customerStateCode) {
		this.customerStateCode = customerStateCode;
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

	public String getInvoiceNoForDashboard() {
		return invoiceNoForDashboard;
	}

	public void setInvoiceNoForDashboard(String invoiceNoForDashboard) {
		this.invoiceNoForDashboard = invoiceNoForDashboard;
	}

}
