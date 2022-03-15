/**
 * 
 */
package com.reliance.gstn.admin.model;

/**
 * @author Nikesh.Bansod
 *
 */
public class AdminFeedbackDetails {


	private String userId;
	private String orgniazationName;
	private String pan;
	private String feedbackDesc;
	private java.sql.Timestamp createdDate;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getOrgniazationName() {
		return orgniazationName;
	}
	public void setOrgniazationName(String orgniazationName) {
		this.orgniazationName = orgniazationName;
	}
	
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	
	public String getFeedbackDesc() {
		return feedbackDesc;
	}
	public void setFeedbackDesc(String feedbackDesc) {
		this.feedbackDesc = feedbackDesc;
	}
	
	public java.sql.Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(java.sql.Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	

}
