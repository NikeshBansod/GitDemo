package com.reliance.gstn.admin.model;

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


@Entity
@Table(name="gst_hsn_master")
public class HSNDetails implements Serializable {

	private static final long serialVersionUID = 1176788631655798367L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	//@NotEmpty(message = "Section cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "section")
	private String section;

	//@NotEmpty(message = "Chapter cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "chapter")
	private String chapter;

	@NotEmpty(message = "hsn Code cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "hsn_code")
	private String hsnCode;
	
	@NotEmpty(message = "hsn Description cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "hsn_description")
	private String hsnDesc;
	
	//@NotNull(message = "SGST OR UGST cannot be empty")
	@Column(name = "sgst_or_ugst")
	private Double sgstUgst;
	
	//@NotNull(message = "CGST cannot be empty")
	@Column(name = "cgst")
	private Double cgst;
	
	@NotNull(message = "IGST cannot be empty")
	@Column(name = "igst")
	private Double igst;
	
	@Column(name = "state_id")
	private Integer stateId;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public String getHsnDesc() {
		return hsnDesc;
	}

	public void setHsnDesc(String hsnDesc) {
		this.hsnDesc = hsnDesc;
	}

	public Double getSgstUgst() {
		return sgstUgst;
	}

	public void setSgstUgst(Double sgstUgst) {
		this.sgstUgst = sgstUgst;
	}

	public Double getCgst() {
		return cgst;
	}

	public void setCgst(Double cgst) {
		this.cgst = cgst;
	}

	public Double getIgst() {
		return igst;
	}

	public void setIgst(Double igst) {
		this.igst = igst;
	}
		
	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}
	

}
