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
@Table(name = "gst_mode_of_transport")
public class ModeOfTransport implements Serializable{

	private static final long serialVersionUID = -6635732437761277224L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	
	@Column(name = "mode_of_transport")
	private String modeOfTransport;


	public String getModeOfTransport() {
		return modeOfTransport;
	}


	public void setModeOfTransport(String modeOfTransport) {
		this.modeOfTransport = modeOfTransport;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public ModeOfTransport(int id) {
		super();
		this.id = id;
	}


	
	
	

}
