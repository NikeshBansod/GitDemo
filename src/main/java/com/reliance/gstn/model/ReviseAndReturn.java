/**
 * 
 */
package com.reliance.gstn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Nikesh.Bansod
 *
 */
@Entity
@Table(name = "gst_revise_and_return_master")
public class ReviseAndReturn implements Serializable{

	private static final long serialVersionUID = 4604861876236976516L;
	
	@Id
	@Column(name = "id")
	private Integer id;
	
	
	@Column(name = "rr_type")
	private String rrType;
	
	@Column(name = "rr_type_name")
	private String rrTypeName;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getRrType() {
		return rrType;
	}


	public void setRrType(String rrType) {
		this.rrType = rrType;
	}


	public String getRrTypeName() {
		return rrTypeName;
	}


	public void setRrTypeName(String rrTypeName) {
		this.rrTypeName = rrTypeName;
	}
	
	

}
