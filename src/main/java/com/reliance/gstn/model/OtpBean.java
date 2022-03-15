package com.reliance.gstn.model;

import java.io.Serializable;

public class OtpBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8427249500475117674L;
	
	private String mobileNo;
	private String otpNo;
	
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getOtpNo() {
		return otpNo;
	}
	public void setOtpNo(String otpNo) {
		this.otpNo = otpNo;
	}
	
	
}
