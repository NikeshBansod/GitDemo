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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

/**
 * @author Nikesh.Bansod
 *
 */

@Entity
@Table(name = "gst_organization_master")
public class OrganizationMasterUnification implements Serializable {

	private static final long serialVersionUID = 472991166259976266L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

//	@NotEmpty(message = "Organization Name cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "ORG_NAME")
	@Length(min=2,max=100,message="Organization Name length should minimum 2 and maximum 100 characters")
	private String orgName;

	@NotEmpty(message = "PAN cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "PAN")
	private String panNumber;

//	@NotEmpty(message = "Organization Type cannot be empty")
	@Column(name = "ORG_TYPE")
	private String orgType;

	@Column(name = "OTHER_ORG_TYPE")
	@Length(max=200)
	private String otherOrgType;
	
//	@NotEmpty(message = "Billing For cannot be empty")
/*	@Column(name = "BILLING_TYPE")
	private String billingFor;  */

//	@NotEmpty(message = "Nature of Business cannot be empty")
	@Column(name = "NATURE_OF_BUSINESS")
	private String natureOfBusiness;
	
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "ADDRESS1")
	private String address1;

	@Column(name = "PIN_CODE")
	private Integer pinCode;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "CITY")
	private String city;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "STATE")
	private String state;

	@Column(name = "LANDLINE_NO")
	private String landlineNo;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "GSTIN_NO")
	private String gstinNumber;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "GSTIN_STATE")
	private String gstinState;
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "GSTIN_USER")
	private String gstinUser;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REF_USER_ID", nullable = false)
	private UserMasterUnification userMasterUnification;

	@Column(name = "CREATED_ON", updatable=false)
	private java.sql.Timestamp createdOn;

	@Column(name = "CREATED_BY", updatable=false)
	private String createdBy;

	@Column(name = "UPDATED_ON")
	private java.sql.Timestamp updatedOn;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name="OTHER_NATURE_OF_BUSINESS")
	@SafeHtml(message = "HTML elements not allowed")
	@Length(max=200)
	private String otherNatureOfBusiness;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "REGD_OFFICE_DETAILS")
	private String regdOfficeDetails;
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "CIN")
	private String cin;
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "PRINCIPAL_PLACE_OF_BUSINESS")
	private String principalPlaceOfBusiness;
	
	//@Column(name = "TERMS_CONDITIONS_FLAG")
	@Transient
	private String termsConditionsFlag;
	
	@Column(name = "TERMS_CONDITIONS_FLAG")
	private String termsConditionsFlagHidden;
	
	@Column(name = "LOGO_UPLOAD_FLAG")
	private String logoUploadFlag;
	
	@Column(name = "LOGO_IMG_PATH")
	private String logoImagePath;
	
	@Transient
	private String stateCode;
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "FOOTER")
	private String footer;
	
	@Column(name = "COMPANY_ID")
	private Double companyId;
	
	@Column(name = "REGISTRATION_STATUS")
	private boolean regstatus;
	
	@Column(name = "ORGEMAIL_ID")
	private String orgEmailId;
	
	@Column(name = "ADDRESS2")
	private String address2;
	
	@Column(name = "ADHAR_ESIGN")
	private String adharEsign;
	
	@Column(name = "STD_CODE")
	private String stdCode;
	
	@Column(name = "ORG_CONTACTNO")
	private String orgContactNo;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/*public String getBillingFor() {
		return billingFor;
	}

	public void setBillingFor(String billingFor) {
		this.billingFor = billingFor;
	}*/

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
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

	public String getLandlineNo() {
		return landlineNo;
	}

	public void setLandlineNo(String landlineNo) {
		this.landlineNo = landlineNo;
	}

	public String getGstinNumber() {
		return gstinNumber;
	}

	public void setGstinNumber(String gstinNumber) {
		this.gstinNumber = gstinNumber;
	}

	public String getGstinState() {
		return gstinState;
	}

	public void setGstinState(String gstinState) {
		this.gstinState = gstinState;
	}

	public String getGstinUser() {
		return gstinUser;
	}

	public void setGstinUser(String gstinUser) {
		this.gstinUser = gstinUser;
	}


	public UserMasterUnification getUserMasterUnification() {
		return userMasterUnification;
	}

	public void setUserMasterUnification(UserMasterUnification userMasterUnification) {
		this.userMasterUnification = userMasterUnification;
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

	public String getOtherOrgType() {
		return otherOrgType;
	}

	public void setOtherOrgType(String otherOrgType) {
		this.otherOrgType = otherOrgType;
	}

	public String getNatureOfBusiness() {
		return natureOfBusiness;
	}

	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}

	public String getOtherNatureOfBusiness() {
		return otherNatureOfBusiness;
	}

	public void setOtherNatureOfBusiness(String otherNatureOfBusiness) {
		this.otherNatureOfBusiness = otherNatureOfBusiness;
	}

	public String getRegdOfficeDetails() {
		return regdOfficeDetails;
	}

	public void setRegdOfficeDetails(String regdOfficeDetails) {
		this.regdOfficeDetails = regdOfficeDetails;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getPrincipalPlaceOfBusiness() {
		return principalPlaceOfBusiness;
	}

	public void setPrincipalPlaceOfBusiness(String principalPlaceOfBusiness) {
		this.principalPlaceOfBusiness = principalPlaceOfBusiness;
	}

	public String getTermsConditionsFlag() {
		return termsConditionsFlag;
	}

	public void setTermsConditionsFlag(String termsConditionsFlag) {
		this.termsConditionsFlag = termsConditionsFlag;
	}

	public String getTermsConditionsFlagHidden() {
		return termsConditionsFlagHidden;
	}

	public void setTermsConditionsFlagHidden(String termsConditionsFlagHidden) {
		this.termsConditionsFlagHidden = termsConditionsFlagHidden;
	}

	public String getLogoUploadFlag() {
		return logoUploadFlag;
	}

	public void setLogoUploadFlag(String logoUploadFlag) {
		this.logoUploadFlag = logoUploadFlag;
	}

	public String getLogoImagePath() {
		return logoImagePath;
	}

	public void setLogoImagePath(String logoImagePath) {
		this.logoImagePath = logoImagePath;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public Double getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Double companyId) {
		this.companyId = companyId;
	}

	public boolean isRegstatus() {
		return regstatus;
	}

	public void setRegstatus(boolean regstatus) {
		this.regstatus = regstatus;
	}

	public String getOrgEmailId() {
		return orgEmailId;
	}

	public void setOrgEmailId(String orgEmailId) {
		this.orgEmailId = orgEmailId;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAdharEsign() {
		return adharEsign;
	}

	public void setAdharEsign(String adharEsign) {
		this.adharEsign = adharEsign;
	}

	public String getStdCode() {
		return stdCode;
	}

	public void setStdCode(String stdCode) {
		this.stdCode = stdCode;
	}

	public String getOrgContactNo() {
		return orgContactNo;
	}

	public void setOrgContactNo(String orgContactNo) {
		this.orgContactNo = orgContactNo;
	}
	
	
}
