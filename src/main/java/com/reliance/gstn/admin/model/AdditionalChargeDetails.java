package com.reliance.gstn.admin.model;

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
@Table(name="gst_add_charges_master")
public class AdditionalChargeDetails implements Serializable {

	private static final long serialVersionUID = 1176788631655798367L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message = "Section cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "CHARGE_NAME")
	private String chargeName;
	
	@Column(name = "CHARGE_VALUE")
	private Double chargeValue;
	
/*	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "CHARGE_TYPE")
	private String chargesIn;
	
	@Column(name = "RATE_OF_TAX")
	private Double rateOfTax;*/
		
	@Column(name = "CREATED_ON", updatable=false)
	private java.sql.Timestamp createdOn;

	@Column(name = "CREATED_BY", updatable=false)
	private String createdBy;

	@Column(name = "UPDATED_ON")
	private java.sql.Timestamp updatedOn;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "REF_USER_ID")
	private Integer referenceId;
	
	@Column(name = "STATUS")
	private String status="1";
	
	@Column(name= "REF_ORG_ID")
	private Integer refOrgId;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChargeName() {
		return chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	public Double getChargeValue() {
		return chargeValue;
	}

	public void setChargeValue(Double chargeValue) {
		this.chargeValue = chargeValue;
	}

	/*public String getChargesIn() {
		return chargesIn;
	}

	public void setChargesIn(String chargesIn) {
		this.chargesIn = chargesIn;
	}

	public Double getRateOfTax() {
		return rateOfTax;
	}

	public void setRateOfTax(Double rateOfTax) {
		this.rateOfTax = rateOfTax;
	}*/

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

	public Integer getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Integer referenceId) {
		this.referenceId = referenceId;
	}
	
	public Integer getRefOrgId() {
		return refOrgId;
	}

	public void setRefOrgId(Integer refOrgId) {
		this.refOrgId = refOrgId;
	}

}
