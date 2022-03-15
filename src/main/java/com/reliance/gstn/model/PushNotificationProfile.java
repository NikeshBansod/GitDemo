/**
 * 
 */
package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Aman1.Bansal
 *
 */
public class PushNotificationProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	private Set<String> state;
	private String ewayBill;
	private String verticalType;
	private String customerType;


	public Set<String> getState() {
		return state;
	}

	public void setState(Set<String> state) {
		this.state = state;
	}

	public String getEwayBill() {
		return ewayBill;
	}

	public void setEwayBill(String ewayBill) {
		this.ewayBill = ewayBill;
	}

	public String getVerticalType() {
		return verticalType;
	}

	public void setVerticalType(String verticalType) {
		this.verticalType = verticalType;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

}
