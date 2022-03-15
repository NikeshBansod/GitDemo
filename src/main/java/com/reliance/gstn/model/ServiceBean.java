/**
 * 
 */
package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

/**
 * @author Dibyendu.Mukherjee
 *
 */
public class ServiceBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6744258984648365336L;

	@NotEmpty(message = "Service Name cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	private String name;

	@NotEmpty(message = "SAC Code cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	private String sacCode;

	@NotEmpty(message = "SAC Description cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	private String sacDescription;

	/*@NotEmpty(message = "Unit Of Measurement cannot be empty")*/
	@SafeHtml(message = "HTML elements not allowed")
	private String unitOfMeasurement;
	
	@SafeHtml(message = "HTML elements not allowed")
	private String otherUOM;

	@NotNull(message = "Service Rate cannot be empty")
	private Double serviceRate;
	
	@NotNull(message = "Service IGST cannot be empty")
	private Double serviceIgst;
	
	private Integer refOrgId;

	private java.sql.Timestamp createdOn;

	private String createdBy;
	
	private java.sql.Timestamp updatedOn;

	private String updatedBy;

	private Integer referenceId;
	
	private String status;
	
	private Integer sacCodePkId;

	private String tempUom;
	
	private Double advolCess;
	
	private Double nonAdvolCess;
	
	@NotEmpty(message = "Stores cannot be empty")
	List<StoresBean> storesBean = new ArrayList<StoresBean>();
	
	

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

	public String getOtherUOM() {
		return otherUOM;
	}

	public void setOtherUOM(String otherUOM) {
		this.otherUOM = otherUOM;
	}

	public Double getServiceRate() {
		return serviceRate;
	}

	public void setServiceRate(Double serviceRate) {
		this.serviceRate = serviceRate;
	}

	public Double getServiceIgst() {
		return serviceIgst;
	}

	public void setServiceIgst(Double serviceIgst) {
		this.serviceIgst = serviceIgst;
	}

	public Integer getRefOrgId() {
		return refOrgId;
	}

	public void setRefOrgId(Integer refOrgId) {
		this.refOrgId = refOrgId;
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

	public List<StoresBean> getStoresBean() {
		return storesBean;
	}

	public void setStoresBean(List<StoresBean> storesBean) {
		this.storesBean = storesBean;
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



}
