package org.coursera.androidcapstone.symptom.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
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
public class Doctor implements Serializable {

	private static final long serialVersionUID = 20141101L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private String username;
	
	@NotNull
	@Column(length=10)
	private Integer nationalProviderNumber;
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@ManyToMany
	private List<Patient> patients;
	
	/**
	 * Default Constructor
	 */
	protected Doctor() {
	}
	
	/**
	 * 
	 * @param username
	 * @param nationalProviderNumber
	 * @param firstName
	 * @param lastName
	 */
	public Doctor(String username, Integer nationalProviderNumber, String firstName, String lastName) {
		this.username = username;
		this.nationalProviderNumber = nationalProviderNumber;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	//
	
	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getNationalProviderNumber() {
		return nationalProviderNumber;
	}

	public void setNationalProviderNumber(Integer nationalProviderNumber) {
		this.nationalProviderNumber = nationalProviderNumber;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
}
