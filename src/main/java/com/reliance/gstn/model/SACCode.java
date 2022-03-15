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

/**
 * @author Nikesh.Bansod
 *
 */

@Entity
@Table(name = "gst_sac_master")
public class SACCode  implements Serializable{

	private static final long serialVersionUID = 6439394276448586503L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "sac_code")
	private String sacCode;
	
	@Column(name = "sac_description")
	private String sacDescription;
	
	@Column(name = "sgst_or_ugst")
	private Double sgstOrUgst;
	
	@Column(name = "cgst")
	private Double cgst;
	
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

	public String getSacCode() {
		return sacCode;
	}

	public void setSacCode(String sacCode) {
		this.sacCode = sacCode;
	}

	public String getSacDescription() {
		return sacDescription;
	}

	public void setSacDescription(String sacDescription) {
		this.sacDescription = sacDescription;
	}

	public Double getSgstOrUgst() {
		return sgstOrUgst;
	}

	public void setSgstOrUgst(Double sgstOrUgst) {
		this.sgstOrUgst = sgstOrUgst;
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
