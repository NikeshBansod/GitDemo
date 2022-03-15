package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name="gst_gstin_location_master")
public class GstinLocation implements Serializable{

private static final long serialVersionUID = 630324460667814717L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name="gstin_location")
	private String gstinLocation;
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name="unique_sequence",updatable=false)
	private String uniqueSequence;
	
	@Column(name="ref_gstin_id")
	private Integer refGstinId;
	
	/*@Valid
	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name="gst_gstin_location_master",joinColumns = @JoinColumn(name = "gstin_id"),
            inverseJoinColumns = @JoinColumn(name = "ref_gstin_id"))
	private GSTINDetails gSTINDetails;*/
	
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name="gstin_store")
	private String gstinStore;
	
	
	public String getUniqueSequence() {
		return uniqueSequence;
	}

	public void setUniqueSequence(String uniqueSequence) {
		this.uniqueSequence = uniqueSequence;
	}

	public Integer getRefGstinId() {
		return refGstinId;
	}

	public void setRefGstinId(Integer refGstinId) {
		this.refGstinId = refGstinId;
	}

	public String getGstinStore() {
		return gstinStore;
	}

	public void setGstinStore(String gstinStore) {
		this.gstinStore = gstinStore;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGstinLocation() {
		return gstinLocation;
	}

	public void setGstinLocation(String gstinLocation) {
		this.gstinLocation = gstinLocation;
	}


	
}
