package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="gst_login_attempt_details")
public class LoginAttempt implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -996420891819535550L;

	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id ;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "no_of_attempt")
	private Integer noOfAttempts;
	
	@Column(name = "last_attempt_time")
	private Date loginAttemptTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getNoOfAttempts() {
		return noOfAttempts;
	}

	public void setNoOfAttempts(Integer noOfAttempts) {
		this.noOfAttempts = noOfAttempts;
	}

	public Date getLoginAttemptTime() {
		return loginAttemptTime;
	}

	public void setLoginAttemptTime(Date loginAttemptTime) {
		this.loginAttemptTime = loginAttemptTime;
	}
	
	
}
