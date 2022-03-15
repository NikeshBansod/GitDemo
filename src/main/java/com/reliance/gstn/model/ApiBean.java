package com.reliance.gstn.model;

import java.io.Serializable;

public class ApiBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5319803975292337091L;

	private String userName;
	private String securityKey;
	private String location;
	private String appCode;
	private String stateCode;
	private String userIp;
	private String sourceDevice;
	private String sourceDeviceString;
	private String gstinverifyUrl;
	private String gstinOtp;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSecurityKey() {
		return securityKey;
	}
	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getSourceDevice() {
		return sourceDevice;
	}
	public void setSourceDevice(String sourceDevice) {
		this.sourceDevice = sourceDevice;
	}
	public String getSourceDeviceString() {
		return sourceDeviceString;
	}
	public void setSourceDeviceString(String sourceDeviceString) {
		this.sourceDeviceString = sourceDeviceString;
	}
	public String getGstinverifyUrl() {
		return gstinverifyUrl;
	}
	public void setGstinverifyUrl(String gstinverifyUrl) {
		this.gstinverifyUrl = gstinverifyUrl;
	}
	public String getGstinOtp() {
		return gstinOtp;
	}
	public void setGstinOtp(String gstinOtp) {
		this.gstinOtp = gstinOtp;
	}
	
	
}
