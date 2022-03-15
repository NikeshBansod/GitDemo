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
@Table(name = "gst_master_desc")
public class MasterDescDetails implements Serializable {

	private static final long serialVersionUID = -6311662208712099682L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "master_desc")
	private String masterDesc;
	
	@Column(name = "master_prim")
	private boolean isPrimUser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMasterDesc() {
		return masterDesc;
	}

	public void setMasterDesc(String masterDesc) {
		this.masterDesc = masterDesc;
	}

	public boolean isPrimUser() {
		return isPrimUser;
	}

	public void setPrimUser(boolean isPrimUser) {
		this.isPrimUser = isPrimUser;
	}
		
	
	
}