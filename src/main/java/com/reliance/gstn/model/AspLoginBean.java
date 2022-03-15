package com.reliance.gstn.model;

import java.io.Serializable;

public class AspLoginBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5319803975292337091L;

	private String username;
	private String password;
	private String pancard;
	private String userType;
	private String requestType;
	private String serviceURL;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPancard() {
		return pancard;
	}
	public void setPancard(String pancard) {
		this.pancard = pancard;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
	public String getServiceURL() {
		return serviceURL;
	}
	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}
	
	
}
