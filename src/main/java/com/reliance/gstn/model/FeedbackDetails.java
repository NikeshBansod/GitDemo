/**
 * 
 */
package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Nikesh.Bansod
 *
 */
@Entity
@Table(name = "gst_feedback_master")
public class FeedbackDetails implements Serializable {

	private static final long serialVersionUID = -6311662208712099682L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "org_id")
	private Integer orgId;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@SafeHtml
	@Column(name = "feedback_desc")
	private String feedbackDesc;
	
	@Column(name = "master_desc")
	private Integer masterDescDetails;
	
	@Column(name = "created_on", updatable=false)
	private java.sql.Timestamp createdOn;
	
	@Column(name = "image_1_path")
	private String filePathWithName1;
	
	@Column(name = "image_2_path")
	private String filePathWithName2;
	
	@Column(name = "image_3_path")
	private String filePathWithName3;
	

	

	public String getFilePathWithName2() {
		return filePathWithName2;
	}

	public void setFilePathWithName2(String filePathWithName2) {
		this.filePathWithName2 = filePathWithName2;
	}

	public String getFilePathWithName3() {
		return filePathWithName3;
	}

	public void setFilePathWithName3(String filePathWithName3) {
		this.filePathWithName3 = filePathWithName3;
	}

	

	public String getFilePathWithName1() {
		return filePathWithName1;
	}

	public void setFilePathWithName1(String filePathWithName1) {
		this.filePathWithName1 = filePathWithName1;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFeedbackDesc() {
		return feedbackDesc;
	}

	public void setFeedbackDesc(String feedbackDesc) {
		this.feedbackDesc = feedbackDesc;
	}

	public Integer getMasterDescDetails() {
		return masterDescDetails;
	}

	public void setMasterDescDetails(Integer masterDescDetails) {
		this.masterDescDetails = masterDescDetails;
	}

	public java.sql.Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(java.sql.Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	
	
	
}