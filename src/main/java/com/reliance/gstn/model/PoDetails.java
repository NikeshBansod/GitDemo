package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "gst_po_master")
public class PoDetails implements Serializable {

	private static final long serialVersionUID = 1176788631655798367L;
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="PO_NO")
	@NotEmpty
	@SafeHtml(message = "HTML elements not allowed")
	@Length(max=40,message="Po Number length should be maximum 40 characters")
	private String poNo;
	
	@Column(name="PO_VALID_FROM_DATE")
	private java.sql.Timestamp poValidFromDate;
	
	@Column(name="PO_VALID_TO_DATE")
	private java.sql.Timestamp poValidToDate;
	
	@Column(name="REF_ORG_ID")
	private Integer orgRefId;
	
	@Transient
	private String poValidFromDateTemp;
	
	@Transient
	private String poValidToDateTemp;
	
	@OneToOne
	@JoinColumn(name="customer_id")
	private CustomerDetails poCustomerName;
	
	@OneToOne
	@JoinColumn(name="po_product_id")
	private Product poAssocProductName;
	
	@OneToOne
	@JoinColumn(name="po_service_id")
	private ManageServiceCatalogue poAssocServiceName;
	
	/*
	@Column(name="customer_id")
	@NotEmpty
	private Integer poCustomerName;
	
	@Column(name="po_product_id")
	@NotEmpty
	private Integer poAssocProductName;
	
	@Column(name="po_service_id")
	@NotEmpty
	private Integer poAssocServiceName;
*/
	@Column(name = "CREATED_ON", updatable=false)
	private Date createdOn;

	@Column(name = "CREATED_BY", updatable=false)
	private String createdBy;

	@Column(name = "UPDATED_ON")
	private Date updatedOn;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "status" )
	private String status="1";
	
	@Column(name = "REF_USER_ID")
	private Integer refUserId;
	
	@Column(name="po_sel_type")
	private String SelType;
	
	
	public String getSelType() {
		return SelType;
	}

	public void setSelType(String selType) {
		SelType = selType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public java.sql.Timestamp getPoValidFromDate() {
		return poValidFromDate;
	}

	public void setPoValidFromDate(java.sql.Timestamp poValidFromDate) {
		this.poValidFromDate = poValidFromDate;
	}

	public java.sql.Timestamp getPoValidToDate() {
		return poValidToDate;
	}

	public void setPoValidToDate(java.sql.Timestamp poValidToDate) {
		this.poValidToDate = poValidToDate;
	}

	public CustomerDetails getPoCustomerName() {
		return poCustomerName;
	}

	public void setPoCustomerName(CustomerDetails poCustomerName) {
		this.poCustomerName = poCustomerName;
	}
	
	public ManageServiceCatalogue getPoAssocServiceName() {
		return poAssocServiceName;
	}

	public void setPoAssocServiceName(ManageServiceCatalogue poAssocServiceName) {
		this.poAssocServiceName = poAssocServiceName;
	}

	public Product getPoAssocProductName() {
		return poAssocProductName;
	}

	public void setPoAssocProductName(Product poAssocProductName) {
		this.poAssocProductName = poAssocProductName;
	}
	
/*
	public Integer getPoCustomerName() {
		return poCustomerName;
	}

	public void setPoCustomerName(Integer poCustomerName) {
		this.poCustomerName = poCustomerName;
	}
	
	public Integer getPoAssocServiceName() {
		return poAssocServiceName;
	}

	public void setPoAssocServiceName(Integer poAssocServiceName) {
		this.poAssocServiceName = poAssocServiceName;
	}

	public Integer getPoAssocProductName() {
		return poAssocProductName;
	}

	public void setPoAssocProductName(Integer poAssocProductName) {
		this.poAssocProductName = poAssocProductName;
	}
*/
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
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

	public Integer getRefUserId() {
		return refUserId;
	}

	public void setRefUserId(Integer refUserId) {
		this.refUserId = refUserId;
	}
	
	public String getPoValidFromDateTemp() {
		return poValidFromDateTemp;
	}

	public void setPoValidFromDateTemp(String poValidFromDateTemp) {
		this.poValidFromDateTemp = poValidFromDateTemp;
	}

	public String getPoValidToDateTemp() {
		return poValidToDateTemp;
	}

	public void setPoValidToDateTemp(String poValidToDateTemp) {
		this.poValidToDateTemp = poValidToDateTemp;
	}

	public Integer getOrgRefId() {
		return orgRefId;
	}

	public void setOrgRefId(Integer orgRefId) {
		this.orgRefId = orgRefId;
	}
	
	
}
