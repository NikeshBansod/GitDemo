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

/**
 * @author Pradeeo.Gangapuram
 *
 */

@Entity
@Table(name = "gst_gstin_user_mapping")
public class GSTINUserMapping implements Serializable {

	private static final long serialVersionUID = -4322847886163397105L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "REF_GSTIN_ID")
	private String gstinId;

	@Column(name = "REF_USER_ID")
	private Integer referenceUserId;

	@Column(name = "CREATED_ON", updatable=false)
	private java.sql.Timestamp createdOn;

	@Column(name = "CREATED_BY", updatable=false)
	private String createdBy;

	@Column(name = "UPDATED_ON")
	private java.sql.Timestamp updatedOn;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "REFERENCE_ID")
	private Integer referenceId;
	
	@Transient
	private String userName;
	
	@Transient
	private StringBuilder gstinNo;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGstinId() {
		return gstinId;
	}

	public void setGstinId(String gstinId) {
		this.gstinId = gstinId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getReferenceUserId() {
		return referenceUserId;
	}

	public void setReferenceUserId(Integer referenceUserId) {
		this.referenceUserId = referenceUserId;
	}

	public Integer getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Integer referenceId) {
		this.referenceId = referenceId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public StringBuilder getGstinNo() {
		return gstinNo;
	}

	public void setGstinNo(StringBuilder gstinNoList) {
		this.gstinNo = gstinNoList;
	}
	
	

}
