/**
 * 
 */
package com.reliance.gstn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Rupali.J
 *
 */

@Entity
@Table(name = "gst_nic_login_details")
public class NicUserDetails implements Serializable {

	private static final long serialVersionUID = -4322847886163397105L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@NotEmpty(message = "User ID cannot be empty")
	@Column(name = "NIC_USER_ID")
	private String userId;

	@NotEmpty(message = "PASSWORD cannot be empty")
	@Column(name = "NIC_PASSWORD")
	// @Length(min=1,max=500,message="Password length should minimum 8 and
	// maximum 25 characters")
	private String password;

	@Column(name = "REF_USER_ID")
	private Integer referenceId;

	@Column(name = "GSTIN")
	private String gstin;

	@Column(name = "CREATED_ON", updatable=false)
	private java.sql.Timestamp createdOn;

	@Column(name = "CREATED_BY", updatable=false)
	private String createdBy;

	@Column(name = "UPDATED_ON")
	private java.sql.Timestamp updatedOn;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "EWAYBILL_CLIENT_ID")
	private String ewayBillClientId;

	@Column(name = "EWAYBILL_SECRETKEY")
	private String ewayBillSecretKey;

	@Column(name = "REF_ORG_UID")
	private Integer ref_org_uId;

	@Transient
	private String clientid;
	@Transient
	private String secretKey;

	@Transient
	private String authToken;

	@Transient
	private String data;

	@Column(name = "AUTH_USER_ID")
	private String authUserId;
	
	@Column(name = "APP_CODE")
	private String appCode;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Integer referenceId) {
		this.referenceId = referenceId;
	}

	public java.sql.Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(java.sql.Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public java.sql.Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(java.sql.Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getEwayBillClientId() {
		return ewayBillClientId;
	}

	public void setEwayBillClientId(String ewayBillClientId) {
		this.ewayBillClientId = ewayBillClientId;
	}

	public String getEwayBillSecretKey() {
		return ewayBillSecretKey;
	}

	public void setEwayBillSecretKey(String ewayBillSecretKey) {
		this.ewayBillSecretKey = ewayBillSecretKey;
	}

	public Integer getRef_org_uId() {
		return ref_org_uId;
	}

	public void setRef_org_uId(Integer ref_org_uId) {
		this.ref_org_uId = ref_org_uId;
	}

	public String getAuthUserId() {
		return authUserId;
	}

	public void setAuthUserId(String authUserId) {
		this.authUserId = authUserId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	
	
	

}
