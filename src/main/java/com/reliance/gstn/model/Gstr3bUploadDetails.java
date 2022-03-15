package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "gst_gstr3b_upload_details")
public class Gstr3bUploadDetails implements Serializable{
	
	private static final long serialVersionUID = 1176788631655798367L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "gstin")
	private String gstin;
	
	@Column(name = "fp_month")
	private String fpMonth;
	
	@Column(name = "fp_year")
	private String fpYear;
	
	@Column(name = "fp_period")
	private String fpPeriod;
	
	@Column(name = "gstr_type")
	private String gstrType;

	@Column(name = "upload_date")
	private java.sql.Timestamp uploadDate;
	
	@Column(name = "ack_no")
	private String ackNo;
	
	@Column(name = "txn_id")
	private String transactionId;
	
	@Column(name = "status")
	private String status;
		
	@Transient
	private Map<Integer, String> fpMonthsArray;
	
	@Column(name="upload_type")
	private String uploadType;
	
	@Column(name="action_taken")
	private String actionTaken;
	
	public String getId() {
		return id;
	}

	public String getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getFpMonth() {
		return fpMonth;
	}

	public void setFpMonth(String fpMonth) {
		this.fpMonth = fpMonth;
	}

	public String getFpYear() {
		return fpYear;
	}

	public void setFpYear(String fpYear) {
		this.fpYear = fpYear;
	}
	
	public String getFpPeriod() {
		return fpPeriod;
	}

	public void setFpPeriod(String fpPeriod) {
		this.fpPeriod = fpPeriod;
	}

	public String getGstrType() {
		return gstrType;
	}

	public void setGstrType(String gstrType) {
		this.gstrType = gstrType;
	}

	public java.sql.Timestamp getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(java.sql.Timestamp uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	public String getAckNo() {
		return ackNo;
	}

	public void setAckNo(String ackNo) {
		this.ackNo = ackNo;
	}

	public String getTransactionId() {
		return transactionId;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Map<Integer, String> getFpMonthsArray() {
		return fpMonthsArray;
	}

	public void setFpMonthsArray(Map<Integer, String> fpMonthsArray) {
		this.fpMonthsArray = fpMonthsArray;
	}

	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	
	
		
}
