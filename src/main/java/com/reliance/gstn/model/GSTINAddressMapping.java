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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

/**
 * @author Pradeep.n.reddy
 *
 */

@Entity
@Table(name = "gst_gstin_address_map")
public class GSTINAddressMapping implements Serializable {

	private static final long serialVersionUID = 630324460667814717L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Valid
	@OneToOne
	//@JoinColumn(name="REF_ADDRESS_ID" , nullable = false, insertable=false,updatable=false)
	@JoinColumn(name="ID" , nullable = false)
	private GSTINDetails gSTINDetails;
	
	/*@Column(name = "REF_GSTIN_ID")
	private Integer referenceGSTINId;*/
	
	
	
//	@NotEmpty(message = "Address cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PIN_CODE")
	private Integer pinCode;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "CITY")
	private String city;

	@NotEmpty(message = "State cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "STATE")
	private String state;
	
	@NotEmpty(message = "Country cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "CREATED_ON", updatable=false)
	private java.sql.Timestamp createdOn;

	@Column(name = "CREATED_BY", updatable=false)
	private String createdBy;

	@Column(name = "UPDATED_ON")
	private java.sql.Timestamp updatedOn;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPinCode() {
		return pinCode;
	}

	public void setPinCode(Integer pinCode) {
		this.pinCode = pinCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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


	
	
	
}
