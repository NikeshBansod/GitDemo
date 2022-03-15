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

import org.hibernate.validator.constraints.SafeHtml;

/**
 * @author Pradeep.Gangapuram
 *
 */

@Entity
@Table(name = "gst_unique_quantity_master")
public class UnitOfMeasurement implements Serializable {

	private static final long serialVersionUID = -4322847886163397105L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "QUANTITY_CODE")
	private String quantityCode;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "QUANTITY_DESCRIPTION")
	private String quantityDescription;

	
	//@Transient
	//private String stateInString;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuantityCode() {
		return quantityCode;
	}

	public void setQuantityCode(String quantityCode) {
		this.quantityCode = quantityCode;
	}

	public String getQuantityDescription() {
		return quantityDescription;
	}

	public void setQuantityDescription(String quantityDescription) {
		this.quantityDescription = quantityDescription;
	}

}
