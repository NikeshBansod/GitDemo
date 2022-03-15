package com.reliance.gstn.pushnotification.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name="gst_push_notification_details")
public class PushNotificationDetails implements Serializable {

	/**
	 * @author Aman1.Bansal
	 */
	private static final long serialVersionUID = -4030609081073805702L;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message = "Section cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "contact_number")
	private String contactNumber;
	
	@NotEmpty(message = "Section cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "imei_number")
	private String imeiNumber;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "customer_type")
	private String cutomerType;
	
	@Column(name = "vertical_type")
	private String verticalType;
	
	@Column(name = "eway_bill_usage")
	private String eWayBillUsage;
	
	@Column(name = "data_send")
	private String dataSend;
	
	@Column(name = "created_on", updatable=false)
	private java.sql.Timestamp createdOn;

	@Column(name = "updated_on")
	private java.sql.Timestamp updatedOn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCutomerType() {
		return cutomerType;
	}

	public void setCutomerType(String cutomerType) {
		this.cutomerType = cutomerType;
	}

	public String getVerticalType() {
		return verticalType;
	}

	public void setVerticalType(String verticalType) {
		this.verticalType = verticalType;
	}

	public String geteWayBillUsage() {
		return eWayBillUsage;
	}

	public void seteWayBillUsage(String eWayBillUsage) {
		this.eWayBillUsage = eWayBillUsage;
	}

	public String getDataSend() {
		return dataSend;
	}

	public void setDataSend(String dataSend) {
		this.dataSend = dataSend;
	}

	public java.sql.Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(java.sql.Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public java.sql.Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(java.sql.Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String addOrUpdateDetails(String contactNumber2, String imeiNumber2) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
