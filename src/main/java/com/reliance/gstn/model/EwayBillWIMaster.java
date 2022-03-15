package com.reliance.gstn.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Vivek2.Dubey
 *
 */
@Entity
@Table(name="ewb_wi_master")
public class EwayBillWIMaster {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="master_name")
	private String masterType;
	
	@Column(name="master_type")
	private String masterSubType;
	
	@Column(name="master_code")
	private String masterCode;
	
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="parent_id")
	EwayBillWIMaster parent;
	
	@OneToMany(mappedBy="parent")
	Set<EwayBillWIMaster> ewayBillMasterl;
	
	@Column(name="is_active")
	private String isActive;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMasterType() {
		return masterType;
	}

	public void setMasterType(String masterType) {
		this.masterType = masterType;
	}

	public String getMasterSubType() {
		return masterSubType;
	}

	public void setMasterSubType(String masterSubType) {
		this.masterSubType = masterSubType;
	}

	public String getMasterCode() {
		return masterCode;
	}

	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}

	public EwayBillWIMaster getParent() {
		return parent;
	}

	public void setParent(EwayBillWIMaster parent) {
		this.parent = parent;
	}

	public Set<EwayBillWIMaster> getEwayBillMasterl() {
		return ewayBillMasterl;
	}

	public void setEwayBillMasterl(Set<EwayBillWIMaster> ewayBillMasterl) {
		this.ewayBillMasterl = ewayBillMasterl;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
	
}
