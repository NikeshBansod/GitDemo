package com.reliance.gstn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "gst_customer_master")
public class CustomerDetails implements Serializable {

	private static final long serialVersionUID = 1176788631655798367L;
	

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message = "Customer Name cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "CUST_NAME")
	private String custName;

	@NotEmpty(message = "Customer Type cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "CUST_TYPE")
	private String custType;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "CUST_EMAIL_ID")
	private String custEmail;

//	@NotEmpty(message = "Contact No cannot be empty")   // Contact no is optional - 2018-02-15
	@SafeHtml(message = "HTML elements not allowed")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Only 10 Numerical values are allowed")
	@Column(name = "CUST_CONTACT_NO")
	private String contactNo;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "CUST_ADDRESS1")
	private String custAddress1;

	/*@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "CUST_ADDRESS2")
	private String custAddress2;*/

//	@NotEmpty(message = "pin Code  cannot be empty") // Can not use for integer 
	@NotNull(message= "pin Code  cannot be empty")
	@Column(name = "CUST_PIN_CODE")
	private Integer pinCode;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "CUST_CITY")
	private String custCity;
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "CUST_STATE")
	private String custState;
	
	/*@Column(name = "CUST_GENDER")
	private String gender;*/

	@Column(name = "CREATED_ON", updatable=false)
	private java.sql.Timestamp createdOn;

	@Column(name = "CREATED_BY", updatable=false)
	private String createdBy;

	@Column(name = "UPDATED_ON")
	private java.sql.Timestamp updatedOn;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "REF_USER_ID")
	private Integer refId;
	
	@Column(name = "CUST_GSTIN_ID")
//	@Pattern(regexp="[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{1}Z[0-9A-Za-z]{1}$|^$")
	@Pattern(regexp="[0-9]{2}[A-Za-z]{5}[0-9]{4}[A-Za-z]{1}[0-9A-Za-z]{3}$|^$")
	@SafeHtml(message = "HTML elements not allowed")
	private String custGstId;
	
	@NotEmpty(message = "COUNTRY cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "CUST_COUNTRY")
	private String custCountry;
	
	
	@NotEmpty(message = "Status cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "status" )
	private String status="1";
	
	@Transient
	private String isAvailable;
	
	@Column(name = "REF_ORG_ID")
	private Integer refOrgId;
	
	@Column(name = "CUST_GSTIN_STATE")
	@SafeHtml(message = "HTML elements not allowed")
	private String custGstinState;
	
	@Column(name = "PARTY_TYPE")
	@SafeHtml(message = "HTML elements not allowed")
	private String custPartyType;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getCustEmail() {
		return custEmail;
	}

	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getCustAddress1() {
		return custAddress1;
	}

	public void setCustAddress1(String custAddress1) {
		this.custAddress1 = custAddress1;
	}
	/*public String getCustAddress2() {
		return custAddress2;
	}

	public void setCustAddress2(String custAddress2) {
		this.custAddress2 = custAddress2;
	}*/

	public Integer getPinCode() {
		return pinCode;
	}

	public void setPinCode(Integer pinCode) {
		this.pinCode = pinCode;
	}

	public String getCustCity() {
		return custCity;
	}

	public void setCustCity(String custCity) {
		this.custCity = custCity;
	}

	public String getCustState() {
		return custState;
	}

	public void setCustState(String custState) {
		this.custState = custState;
	}

	/*public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
*/
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

	
	public Integer getRefId() {
		return refId;
	}

	public void setRefId(Integer refId) {
		this.refId = refId;
	}

	public String getCustGstId() {
		return custGstId;
	}

	public void setCustGstId(String custGstId) {
		this.custGstId = custGstId;
	}

	public String getCustCountry() {
		return custCountry;
	}

	public void setCustCountry(String custCountry) {
		this.custCountry = custCountry;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Integer getRefOrgId() {
		return refOrgId;
	}

	public void setRefOrgId(Integer refOrgId) {
		this.refOrgId = refOrgId;
	}

	public String getCustGstinState() {
		return custGstinState;
	}

	public void setCustGstinState(String custGstinState) {
		this.custGstinState = custGstinState;
	}


	public String getCustPartyType() {
		return custPartyType;
	}

	public void setCustPartyType(String custPartyType) {
		this.custPartyType = custPartyType;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEqual=false;
		if (obj == null||getClass() != obj.getClass())
			isEqual=false;
		
		CustomerDetails other = (CustomerDetails) obj;
		
		this.contactNo=this.contactNo==null?"":this.contactNo;
		other.contactNo=other.contactNo==null?"":other.contactNo;
		boolean refOrgIdDescision=false;
		
		if(null==this.refOrgId||null==other.refOrgId){
			refOrgIdDescision=true;
		}else{
			refOrgIdDescision=(this.refOrgId.intValue()==other.refOrgId.intValue());
		}
		
		
		if (this.contactNo.equals(other.contactNo)
				&& this.custName.equalsIgnoreCase(other.custName)
				&& refOrgIdDescision) {
			isEqual = true;
		}
	
		
		return isEqual;
	}
	
	
	
}
