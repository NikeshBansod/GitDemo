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
import javax.persistence.Transient;

/**
 * @author Nikesh.Bansod
 *
 */
@Entity
@Table(name = "gst_pincode_master")
public class PinCode implements Serializable {

	private static final long serialVersionUID = -6311662208712099682L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "PINCODE")
	private Integer pinCode;
	
	@Column(name = "DISTRICT")
	private String district;
	
	@Column(name = "STATE")
	private Integer stateId;
	
	@Transient
	private String stateInString;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPinCode() {
		return pinCode;
	}

	public void setPinCode(Integer pinCode) {
		this.pinCode = pinCode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getStateInString() {
		return stateInString;
	}

	public void setStateInString(String stateInString) {
		this.stateInString = stateInString;
	}

	
	
	

}
