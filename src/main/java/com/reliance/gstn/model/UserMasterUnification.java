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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

/**
 * @author Nikesh.Bansod
 *
 */

@Entity
@Table(name = "gst_user_master")

public class UserMasterUnification implements Serializable {

	private static final long serialVersionUID = -4322847886163397105L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@NotEmpty(message = "User ID cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "USER_ID")
	@Length(min=4,max=10,message="User Id length should minimum 4 and maximum 10 characters")
	private String userId;

	@NotEmpty(message = "PASSWORD cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "PASSWORD")
	@Length(min=8,max=500,message="Password length should minimum 8 and maximum 25 characters")
	@Pattern(regexp="(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,25}")
	private String password;

	@Column(name = "USER_ROLE")
	private String userRole;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "USER_NAME")
	private String userName;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "USER_EMAIL_ID")
	private String emailId;
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "DEFAULT_EMAIL_ID")
	private String defaultEmailId;

	@NotEmpty(message = "Contact Number cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "USER_CONTACT_NO")
	private String contactNo;

	@Column(name = "REFERENCE_ID")
	private Integer referenceId;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "userMasterUnification")
	private OrganizationMasterUnification organizationMasterUnification;

	@Column(name = "CREATED_ON", updatable = false)
	private java.sql.Timestamp createdOn;

	@Column(name = "CREATED_BY", updatable = false)
	private String createdBy;

	@Column(name = "UPDATED_ON")
	private java.sql.Timestamp updatedOn;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "STATUS")
	private String status;
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "USER_AADHAR_NO")
	private String secUserAadhaarNo;
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "UNIQUE_SEQUENCE")
	private String uniqueSequence;
	
	@Column(name="MOB_LOGIN")
	private Integer mobLoginCount=0;		
	
	@Column(name="DESK_LOGIN")
	private Integer deskLoginCount=0;	
	
	@Column(name = "LAST_LOGGED_IN")
	private java.sql.Timestamp lastLogin;
	
	@Transient
	private String loginStatus;
	
	@Transient
	private String loginMsg;

	@Transient
	private String loggedInDevice;
	
	@Transient
	private String IMEINo;
	
	@Transient
	private String dataSend;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="USERID_UNIFICATION")
	private Double userIdUnifi;
	
	@Column(name="IS_ADMIN")
	private boolean isadmin;
	
	@Column(name="DEPARTMENT")
	private String department;
	
	@Column(name="DESIGNATION")
	private String designation;
	
	@Column(name="USER_LANDLINE")
	private String userLandlineNo;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="PREFIX")
	private String prefix;
	
	@Column(name="USER_STDCODE")
	private String userStdCode;
	
	
	@Column(name="INVOICE_SEQUENCE_TYPE")
	private String invoiceSequenceType;	
		
		
	public String getDataSend() {
		return dataSend;
	}

	public void setDataSend(String dataSend) {
		this.dataSend = dataSend;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getDefaultEmailId() {
		return defaultEmailId;
	}

	public void setDefaultEmailId(String defaultEmailId) {
		this.defaultEmailId = defaultEmailId;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public Integer getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Integer referenceId) {
		this.referenceId = referenceId;
	}



	public OrganizationMasterUnification getOrganizationMasterUnification() {
		return organizationMasterUnification;
	}

	public void setOrganizationMasterUnification(
			OrganizationMasterUnification organizationMasterUnification) {
		this.organizationMasterUnification = organizationMasterUnification;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSecUserAadhaarNo() {
		return secUserAadhaarNo;
	}

	public void setSecUserAadhaarNo(String secUserAadhaarNo) {
		this.secUserAadhaarNo = secUserAadhaarNo;
	}

	public String getUniqueSequence() {
		return uniqueSequence;
	}

	public void setUniqueSequence(String uniqueSequence) {
		this.uniqueSequence = uniqueSequence;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getLoginMsg() {
		return loginMsg;
	}

	public void setLoginMsg(String loginMsg) {
		this.loginMsg = loginMsg;
	}

	public Integer getMobLoginCount() {
		return mobLoginCount;
	}

	public void setMobLoginCount(Integer mobLoginCount) {
		this.mobLoginCount = mobLoginCount;
	}

	public Integer getDeskLoginCount() {
		return deskLoginCount;
	}

	public void setDeskLoginCount(Integer deskLoginCount) {
		this.deskLoginCount = deskLoginCount;
	}

	public java.sql.Timestamp getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(java.sql.Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLoggedInDevice() {
		return loggedInDevice;
	}

	public void setLoggedInDevice(String loggedInDevice) {
		this.loggedInDevice = loggedInDevice;
	}

	public String getIMEINo() {
		return IMEINo;
	}

	public void setIMEINo(String iMEINo) {
		IMEINo = iMEINo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	

	public Double getUserIdUnifi() {
		return userIdUnifi;
	}

	public void setUserIdUnifi(Double userIdUnifi) {
		this.userIdUnifi = userIdUnifi;
	}

	public boolean isIsadmin() {
		return isadmin;
	}

	public void setIsadmin(boolean isadmin) {
		this.isadmin = isadmin;
	}

	public String getInvoiceSequenceType() {
		return invoiceSequenceType;
	}

	public void setInvoiceSequenceType(String invoiceSequenceType) {
		this.invoiceSequenceType = invoiceSequenceType;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getUserLandlineNo() {
		return userLandlineNo;
	}

	public void setUserLandlineNo(String userLandlineNo) {
		this.userLandlineNo = userLandlineNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getUserStdCode() {
		return userStdCode;
	}

	public void setUserStdCode(String userStdCode) {
		this.userStdCode = userStdCode;
	}

	
	
}
