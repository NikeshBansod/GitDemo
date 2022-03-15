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

import org.hibernate.validator.constraints.SafeHtml;

/**
 * @author Pradeep.Gangapuram
 *
 */

@Entity
@Table(name = "gst_offer_master")
public class ManageOffers implements Serializable {

	private static final long serialVersionUID = -5551342299868727843L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "OFFER_NAME")
	private String offerName;
	
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "OFFER_TYPE")
	private String offerType;
	
	
	@Column(name = "OFFER_TYPE_ID")
	private Integer offerTypeId;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "OFFER_UNIT")
	private String discountIn;

	
	@Column(name = "OFFER_VALUE")
	private Double discountValue;

	@Column(name = "REF_ORG_ID")
	private Integer refOrgId;
	
	@Transient
	private String discountValidDateInString;
	
	@Column(name = "OFFER_VALID_DATE")
	private java.sql.Timestamp discountValidDate;
	
	@Column(name = "created_on", updatable=false)
	private java.sql.Timestamp createdOn;

	@Column(name = "created_by", updatable=false)
	private String createdBy;

	@Column(name = "updated_on")
	private java.sql.Timestamp updatedOn;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "REF_USER_ID")
	private Integer referenceId;
	
	@Column(name = "STATUS")
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getDiscountIn() {
		return discountIn;
	}
	
	public void setDiscountIn(String discountIn) {
		this.discountIn = discountIn;
	}

	public String getOfferType() {
		return offerType;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	public Integer getOfferTypeId() {
		return offerTypeId;
	}

	public void setOfferTypeId(Integer offerTypeId) {
		this.offerTypeId = offerTypeId;
	}

	public java.sql.Timestamp getDiscountValidDate() {
		return discountValidDate;
	}

	public void setDiscountValidDate(java.sql.Timestamp discountValidDate) {
		this.discountValidDate = discountValidDate;
	}

	public Double getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(Double discountValue) {
		this.discountValue = discountValue;
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

	public String getDiscountValidDateInString() {
		return discountValidDateInString;
	}

	public void setDiscountValidDateInString(String discountValidDateInString) {
		this.discountValidDateInString = discountValidDateInString;
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

	
}
