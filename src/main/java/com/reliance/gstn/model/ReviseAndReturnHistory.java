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

import org.hibernate.validator.constraints.SafeHtml;

/**
 * @author Nikesh.Bansod
 *
 */
@Entity
@Table(name = "gst_revise_and_return_history")
public class ReviseAndReturnHistory implements Serializable {

	private static final long serialVersionUID = -6227537965525967357L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "rr_type")
	private String rrType;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "document_type")
	private String documentType;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "created_document_no")
	private String createdDocNo;

	@Column(name = "created_document_pk_id")
	private Integer createdDocumentPkId;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "original_invoice_no")
	private String originalInvoiceNo;

	@Column(name = "original_invoice_pk_id")
	private Integer originalInvoicePkId;
	
	@Column(name = "iteration_no")
	private Integer iterationNo;

	@Column(name = "ref_org_id")
	private Integer refOrgId;

	@Column(name = "created_on", updatable = false)
	private java.sql.Timestamp createdOn;

	@Column(name = "created_by", updatable = false)
	private String createdBy;
	
	@Column(name = "cndn_iteration_no")
	private Integer cnDnIterationNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRrType() {
		return rrType;
	}

	public void setRrType(String rrType) {
		this.rrType = rrType;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getCreatedDocNo() {
		return createdDocNo;
	}

	public void setCreatedDocNo(String createdDocNo) {
		this.createdDocNo = createdDocNo;
	}

	public Integer getCreatedDocumentPkId() {
		return createdDocumentPkId;
	}

	public void setCreatedDocumentPkId(Integer createdDocumentPkId) {
		this.createdDocumentPkId = createdDocumentPkId;
	}

	public String getOriginalInvoiceNo() {
		return originalInvoiceNo;
	}

	public void setOriginalInvoiceNo(String originalInvoiceNo) {
		this.originalInvoiceNo = originalInvoiceNo;
	}

	public Integer getOriginalInvoicePkId() {
		return originalInvoicePkId;
	}

	public void setOriginalInvoicePkId(Integer originalInvoicePkId) {
		this.originalInvoicePkId = originalInvoicePkId;
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

	public Integer getIterationNo() {
		return iterationNo;
	}

	public void setIterationNo(Integer iterationNo) {
		this.iterationNo = iterationNo;
	}

	public Integer getCnDnIterationNo() {
		return cnDnIterationNo;
	}

	public void setCnDnIterationNo(Integer cnDnIterationNo) {
		this.cnDnIterationNo = cnDnIterationNo;
	}
	
	

}
