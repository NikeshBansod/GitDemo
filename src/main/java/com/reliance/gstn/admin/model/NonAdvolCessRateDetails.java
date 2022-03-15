package com.reliance.gstn.admin.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="gst_non_advol_cess_rate")
public class NonAdvolCessRateDetails implements Serializable {

	private static final long serialVersionUID = 1176788631655798367L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "Tax rate cannot be empty")
	@Column(name = "CESS_RATE")
	private Double cessRate;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getCessRate() {
		return cessRate;
	}

	public void setCessRate(Double cessRate) {
		this.cessRate = cessRate;
	}

	
	
}
