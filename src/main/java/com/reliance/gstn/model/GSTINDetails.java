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
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;

/**
 * @author Pradeep.Gangapuram
 *
 */

@Entity
@Table(name = "gst_gstin_master")
public class GSTINDetails implements Serializable {

	private static final long serialVersionUID = -4322847886163397105L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	/*@NotEmpty(message = "gstinNo cannot be empty")*/
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "GSTIN_USER_NAME")
	private String gstinUname;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "GSTIN_NO")
	@Pattern(regexp="[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{3}$")
//	@Pattern(regexp="[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{1}Z[0-9A-Za-z]{1}$")
	private String gstinNo;

	/*@NotEmpty(message = "state cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")*/
	@Column(name = "STATE")
	private Integer state;

	@Column(name = "REF_USER_ID")
	private long referenceId;
	
	@Valid
	@OneToOne
	//@JoinColumn(name="REF_ADDRESS_ID" , nullable = false, insertable=false,updatable=false)
	@JoinColumn(name="REF_ADDRESS_ID" , nullable = false)
	private GSTINAddressMapping gstinAddressMapping;

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
	
	
	@Transient
	private String stateInString;

	//@OneToMany(cascade = CascadeType.ALL,mappedBy="gstinDetails",orphanRemoval = true)
	@Valid
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name="gst_gstin_location_map",joinColumns = @JoinColumn(name = "gstin_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id"))
	private List<GstinLocation> gstinLocationSet = new ArrayList<GstinLocation>(); 
	
	@Column(name="UNIQUE_SEQUENCE")
	private String uniqueSequence;
	
	@Column(name="UPLOAD_TYPE")
	private String uploadType = "Manual";
	
	@Column(name="GSTN_USER_ID")
	private String gstnUserId ;
	
	@Column(name="GROSS_TURN_OVER")
	private String grossTurnover;
	
	@Column(name="CUREENT_TURN_OVER")
	private String currentTurnover;	
	
	@Column(name="GSTN_NICKNAME")
	private String gstnnickname;	
	
	@Transient
	private boolean isGstnUId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getGstinUname() {
		return gstinUname;
	}

	public void setGstinUname(String gstinUname) {
		this.gstinUname = gstinUname;
	}

	public String getGstinNo() {
		return gstinNo;
	}

	public void setGstinNo(String gstinNo) {
		this.gstinNo = gstinNo;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(long referenceId) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStateInString() {
		return stateInString;
	}

	public void setStateInString(String stateInString) {
		this.stateInString = stateInString;
	}

	public GSTINAddressMapping getGstinAddressMapping() {
		return gstinAddressMapping;
	}

	public void setGstinAddressMapping(GSTINAddressMapping gstinAddressMapping) {
		this.gstinAddressMapping = gstinAddressMapping;
	}

	public List<GstinLocation> getGstinLocationSet() {
		return gstinLocationSet;
	}

	public void setGstinLocationSet(List<GstinLocation> gstinLocationSet) {
		this.gstinLocationSet = gstinLocationSet;
	}

	public String getUniqueSequence() {
		return uniqueSequence;
	}

	public void setUniqueSequence(String uniqueSequence) {
		this.uniqueSequence = uniqueSequence;
	}

	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

	public String getGstnUserId() {
		return gstnUserId;
	}

	public void setGstnUserId(String gstnUserId) {
		this.gstnUserId = gstnUserId;
	}

	public String getGrossTurnover() {
		return grossTurnover;
	}

	public void setGrossTurnover(String grossTurnover) {
		this.grossTurnover = grossTurnover;
	}

	public String getCurrentTurnover() {
		return currentTurnover;
	}

	public void setCurrentTurnover(String currentTurnover) {
		this.currentTurnover = currentTurnover;
	}

	public boolean isGstnUId() {
		return isGstnUId;
	}

	public void setGstnUId(boolean isGstnUId) {
		this.isGstnUId = isGstnUId;
	}

	public String getGstnnickname() {
		return gstnnickname;
	}

	public void setGstnnickname(String gstnnickname) {
		this.gstnnickname = gstnnickname;
	}
	
	
}
