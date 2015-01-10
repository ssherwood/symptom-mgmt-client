package org.coursera.androidcapstone.symptom.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

/**
 * 
 */
@Entity
public class Medicine implements Serializable {

	private static final long serialVersionUID = 20141101L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private String genericName;
	private String brandName;
	private Integer csaSchedule;

	@ManyToMany(mappedBy="medicine")
    private List<Patient> patients;
	
	/**
	 * Default Constructor
	 */
	protected Medicine() {
	}
	
	/**
	 * 
	 * @param genericName
	 * @param brandName
	 * @param csaSchedule
	 */
	public Medicine(String genericName, String brandName, Integer csaSchedule) {
		this.genericName = genericName;
		this.brandName = brandName;
		this.csaSchedule = csaSchedule;
	}
	
	///
	
	public Long getId() {
		return id;
	}
	
	public String getGenericName() {
		return genericName;
	}
	
	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}
	
	public String getBrandName() {
		return brandName;
	}
	
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public int getCsaSchedule() {
		return csaSchedule;
	}

	public void setCsaSchedule(int csaSchedule) {
		this.csaSchedule = csaSchedule;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
}
