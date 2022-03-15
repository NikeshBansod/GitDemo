/**
 * 
 */
package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.hibernate.validator.constraints.SafeHtml;


/**
 * @author Rupali J
 *
 */

@Entity
@Table(name = "gst_user_gstin_mapping")
public class UserGSTINMapping implements Serializable {

	private static final long serialVersionUID = -4322847886163397105L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "REF_GSTIN_ID")
	private Integer gstinId;

	@Column(name = "REF_USER_ID")
	private Integer referenceUserId;
	
	@OneToOne
	@JoinColumn(name="REF_ADDRESS_ID")
	private GstinLocation gstinAddressMapping;
		
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch=FetchType.EAGER)
	@JoinTable(name="gst_gstin_user_map",joinColumns = @JoinColumn(name = "gstin_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<UserMaster> gstinUserSet = new ArrayList<UserMaster>(); 
		
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
	
	@Column(name = "REF_ORG_ID")
	private Integer refOrgId;
	
	@Transient
	private List<Integer> gstinUserIds;
	
	@Transient
	@SafeHtml(message = "HTML elements not allowed")
	private String gstinNo;
	
	@Transient
	@SafeHtml(message = "HTML elements not allowed")
	private StringBuilder userName;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGstinId() {
		return gstinId;
	}

	public void setGstinId(Integer gstinId) {
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
	
	public GstinLocation getGstinAddressMapping() {
		return gstinAddressMapping;
	}

	public void setGstinAddressMapping(GstinLocation gstinAddressMapping) {
		this.gstinAddressMapping = gstinAddressMapping;
	}

	public List<UserMaster> getGstinUserSet() {
		return gstinUserSet;
	}

	public void setGstinUserSet(List<UserMaster> gstinUserSet) {
		this.gstinUserSet = gstinUserSet;
	}
	
	public List<Integer> getGstinUserIds() {
		return gstinUserIds;
	}

	public void setGstinUserIds(List<Integer> gstinUserIds) {
		this.gstinUserIds = gstinUserIds;
	}

	public Integer getRefOrgId() {
		return refOrgId;
	}

	public void setRefOrgId(Integer refOrgId) {
		this.refOrgId = refOrgId;
	}

	public String getGstinNo() {
		return gstinNo;
	}

	public void setGstinNo(String gstinNo) {
		this.gstinNo = gstinNo;
	}

	public StringBuilder getUserName() {
		return userName;
	}

	public void setUserName(StringBuilder userName) {
		this.userName = userName;
	}

}
