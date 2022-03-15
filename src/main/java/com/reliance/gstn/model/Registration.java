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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

/**
 * @author Nikesh.Bansod
 *
 */

@Entity
@Table(name = "registration")
public class Registration implements Serializable {

	private static final long serialVersionUID = 1176788631655798367L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message = "sp_name cannot be empty")//MessageFormat.format("This message is for {0} in {1}", "foo", "bar")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "sp_name")
	private String spName;

	@NotEmpty(message = "cp_name cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "cp_name")
	private String cpName;

	@NotEmpty(message = "contact_no cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "contact_no")
	private String contactNo;

	@NotEmpty(message = "primary_email cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "primary_email")
	private String primaryEmail;

	@NotEmpty(message = "password cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "password")
	private String password;

	@NotNull(message = "GSTN Number cannot be empty")
	@Column(name = "gstn_reg_number")
	private long gstnRegNumber;

	@NotEmpty(message = "PAN cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "pan_number")
	private String panNumber;

	@Column(name = "created_on", updatable=false)
	private java.sql.Timestamp createdOn;

	@Column(name = "created_by", updatable=false)
	private String createdBy;

	@Column(name = "updated_on")
	private java.sql.Timestamp updatedOn;

	@Column(name = "updated_by")
	private String updatedBy;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getGstnRegNumber() {
		return gstnRegNumber;
	}

	public void setGstnRegNumber(long gstnRegNumber) {
		this.gstnRegNumber = gstnRegNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public java.sql.Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(java.sql.Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public java.sql.Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(java.sql.Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	
}
