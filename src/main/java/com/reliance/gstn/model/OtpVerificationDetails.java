package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author rupali.jagdale
 *
 */

@Entity
@Table(name = "gst_otp_record_details")
public class OtpVerificationDetails  implements Serializable{

	private static final long serialVersionUID = -7993354017763915223L;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "CONTACT_NO")
	private String mobileNo;
	
	@Column(name = "OTP")
	private String otp;
	
	@Column(name = "LAST_OTP_TIME")
	private Date otpTime;
	
	@Column(name = "NO_OF_ATTEMPT")
	private Integer noOfAttempts; 

	@Column(name = "OTP_COUNTER")
	private Integer OtpCounter;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Date getOtpTime() {
		return otpTime;
	}

	public void setOtpTime(Date otpTime) {
		this.otpTime = otpTime;
	}
	
	public Integer getNoOfAttempts() {
		return noOfAttempts;
	}

	public void setNoOfAttempts(Integer noOfAttempts) {
		this.noOfAttempts = noOfAttempts;
	}
	
	public Integer getOtpCounter() {
		return OtpCounter;
	}

	public void setOtpCounter(Integer otpCounter) {
		OtpCounter = otpCounter;
	}
	
}
