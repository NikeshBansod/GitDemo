package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.SafeHtml;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="gst_gstin_location_master")
public class GstinLocationFetch implements Serializable{

	
	private static final long serialVersionUID = -7670549039913886657L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name="gstin_location")
	private String gstinLocation;
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name="unique_sequence")
	private String uniqueSequence;
	
	@Column(name="ref_gstin_id")
	private Integer refGstinId;
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name="gstin_store")
	private String gstinStore;
	

    @OneToMany(mappedBy = "gstinLocationfetch")
    private Set<ProductFetch> products;
    
    @OneToMany(mappedBy = "gstinLocationServiceFetch")
    private Set<ServiceFetch> servicefetch;
    
    
    @Expose
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn (name="ref_gstin_id",nullable=true,insertable=false,updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	private GSTINDetailsFetch gstindetailsfetch;
	
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

	public Set<ProductFetch> getProducts() {
		return products;
	}

	public void setProducts(Set<ProductFetch> products) {
		this.products = products;
	}
	
	public GSTINDetailsFetch getGstindetailsfetch() {
		return gstindetailsfetch;
	}

	public void setGstindetailsfetch(GSTINDetailsFetch gstindetailsfetch) {
		this.gstindetailsfetch = gstindetailsfetch;
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

	public Set<ServiceFetch> getServicefetch() {
		return servicefetch;
	}

	public void setServicefetch(Set<ServiceFetch> servicefetch) {
		this.servicefetch = servicefetch;
	}
	

	
}
