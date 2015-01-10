package org.coursera.androidcapstone.symptom.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 */
@Entity
public class Patient implements Serializable {

	private static final long serialVersionUID = 20141101L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private String username;
	
	@NotNull
	@Column(length=10)
	private String medicalId;
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	private String phoneNumber;
	
	private String emailAddress;
	
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;

    @ManyToMany(mappedBy="patients")
    private List<Doctor> doctors;
    
	@ManyToMany
    private Set<Medicine> medicine;
    
	@OneToMany(mappedBy="patient")
	private List<PatientCheckin> checkins;
	
    @OneToMany(mappedBy="patient")
    private List<PatientAlert> alerts;
    
	/**
	 * Default Constructor
	 */
	protected Patient() {
	}
	
	/**
	 * 
	 * @param username
	 * @param firstName
	 * @param lastName
	 * @param phoneNumber
	 * @param emailAddress
	 * @param dateOfBirth
	 */
	public Patient(String username, String medicalId, String firstName, String lastName, String phoneNumber, String emailAddress, LocalDate dateOfBirth) {
		this.username = username;
		this.medicalId = medicalId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.dateOfBirth = dateOfBirth;
	}
	
	/// Java ceremony below...
	
	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMedicalId() {
		return medicalId;
	}

	public void setMedicalId(String medicalId) {
		this.medicalId = medicalId;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public Set<Medicine> getMedicine() {
		return medicine;
	}

	public void setMedicine(Set<Medicine> medicine) {
		this.medicine = medicine;
	}

	public List<PatientCheckin> getCheckins() {
		return checkins;
	}

	public void setCheckins(List<PatientCheckin> checkins) {
		this.checkins = checkins;
	}

	public List<PatientAlert> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<PatientAlert> alerts) {
		this.alerts = alerts;
	}
}
