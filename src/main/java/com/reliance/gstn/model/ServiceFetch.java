/**
 * 
 */
package com.reliance.gstn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import com.google.gson.annotations.Expose;

/**
 * @author Nikesh.Bansod
 *
 */

@Entity
@Table(name = "gst_service_master")
public class ServiceFetch implements Serializable {

	private static final long serialVersionUID = -5551342299868727843L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@NotEmpty(message = "Service Name cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "SERVICE_NAME")
	private String name;

	@NotEmpty(message = "SAC Code cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "SAC_CODE")
	private String sacCode;

	@NotEmpty(message = "SAC Description cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "SAC_DESC")
	private String sacDescription;



	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "UNIT_OF_MEASUREMENT")
	private String unitOfMeasurement;
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "OTHER_UOM")
	private String otherUOM;

	@NotNull(message = "Service Rate cannot be empty")
	@Column(name = "SERVICE_RATE")
	private Double serviceRate;
	
//	@Pattern(regexp="{0|0.25|3|5|12|18|28}")
	@NotNull(message = "Service IGST cannot be empty")
	@Column(name = "SERVICE_IGST")
	private Double serviceIgst;
	
	@Column(name = "REF_ORG_ID")
	private Integer refOrgId;

	@Column(name = "CREATED_ON", updatable=false)
	private java.sql.Timestamp createdOn;

	@Column(name = "CREATED_BY", updatable=false)
	private String createdBy;

	@Column(name = "UPDATED_ON")
	private java.sql.Timestamp updatedOn;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@NotNull(message = "RefernceUserId cannot be empty")
	@Column(name = "REF_USER_ID")
	private Integer referenceId;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "SAC_CODE_PK_ID")
	private Integer sacCodePkId;

	@Transient
	private String tempUom;
	
	@Column(name = "ADVOL_CESS")
	private Double advolCess;
	
	@Column(name = "NON_ADVOL_CESS")
	private Double nonAdvolCess;
	
	@NotNull(message = "STORE_ID cannot be empty")
	@Column(name = "STORE_ID")
	private Integer storeId ;
	
	@Expose
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn (name="store_id",nullable=true,insertable=false,updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	private GstinLocationFetch gstinLocationServiceFetch;
	
	@Transient
	private String storeName;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSacCode() {
		return sacCode;
	}

	public void setSacCode(String sacCode) {
		this.sacCode = sacCode;
	}

	public String getSacDescription() {
		return sacDescription;
	}

	public void setSacDescription(String sacDescription) {
		this.sacDescription = sacDescription;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public Double getServiceRate() {
		return serviceRate;
	}

	public void setServiceRate(Double serviceRate) {
		this.serviceRate = serviceRate;
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

	public Integer getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Integer referenceId) {
		this.referenceId = referenceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRefOrgId() {
		return refOrgId;
	}

	public void setRefOrgId(Integer refOrgId) {
		this.refOrgId = refOrgId;
	}

	public String getOtherUOM() {
		return otherUOM;
	}

	public void setOtherUOM(String otherUOM) {
		this.otherUOM = otherUOM;
	}

	public Double getServiceIgst() {
		return serviceIgst;
	}

	public void setServiceIgst(Double serviceIgst) {
		this.serviceIgst = serviceIgst;
	}

	public Integer getSacCodePkId() {
		return sacCodePkId;
	}

	public void setSacCodePkId(Integer sacCodePkId) {
		this.sacCodePkId = sacCodePkId;
	}

	public String getTempUom() {
		return tempUom;
	}

	public void setTempUom(String tempUom) {
		this.tempUom = tempUom;
	}

	
	public Double getAdvolCess() {
		return advolCess;
	}

	public void setAdvolCess(Double advolCess) {
		this.advolCess = advolCess;
	}

	public Double getNonAdvolCess() {
		return nonAdvolCess;
	}

	public void setNonAdvolCess(Double nonAdvolCess) {
		this.nonAdvolCess = nonAdvolCess;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public GstinLocationFetch getGstinLocationServiceFetch() {
		return gstinLocationServiceFetch;
	}

	public void setGstinLocationServiceFetch(
			GstinLocationFetch gstinLocationServiceFetch) {
		this.gstinLocationServiceFetch = gstinLocationServiceFetch;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEqual=false;
		if (obj == null||getClass() != obj.getClass())
			isEqual=false;
		
		ServiceFetch other = (ServiceFetch) obj;
		boolean refOrgIdDescision=false;
		
		if(null==this.refOrgId||null==other.refOrgId){
			refOrgIdDescision=true;
		}else{
			refOrgIdDescision=(this.refOrgId.intValue()==other.refOrgId.intValue());
		}
		
		if (refOrgIdDescision && this.name.equalsIgnoreCase(other.name)) 
		{
			isEqual = true;
		}
		return isEqual;
	}
	
}
