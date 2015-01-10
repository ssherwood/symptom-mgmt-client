package org.coursera.androidcapstone.symptom.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 */
@Entity
public class PatientAlert implements Serializable {

	private static final long serialVersionUID = 20141101L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @ManyToOne
    private Patient patient;
    
	@NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	private LocalDateTime alertedAt;
	
    @Enumerated(EnumType.STRING)
    private AlertType alertType;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime acknowledgedAt;

    @OneToOne
	private Doctor acknowledgedBy;
    
    private String notes;
    
    /**
     * 
     */
    protected PatientAlert() {
    }
    
    public PatientAlert(AlertType alertType, Patient patient) {
    	this.alertType = alertType;
    	this.alertedAt = LocalDateTime.now();
    	this.patient = patient;
    }

    ///
    
	public Long getId() {
		return id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public LocalDateTime getAlertedAt() {
		return alertedAt;
	}

	public void setAlertedAt(LocalDateTime alertedAt) {
		this.alertedAt = alertedAt;
	}

	public AlertType getAlertType() {
		return alertType;
	}

	public void setAlertType(AlertType alertType) {
		this.alertType = alertType;
	}

	public LocalDateTime getAcknowledgedAt() {
		return acknowledgedAt;
	}

	public void setAcknowledgedAt(LocalDateTime acknowledgedAt) {
		this.acknowledgedAt = acknowledgedAt;
	}

	public Doctor getAcknowledgedBy() {
		return acknowledgedBy;
	}

	public void setAcknowledgedBy(Doctor acknowledgedBy) {
		this.acknowledgedBy = acknowledgedBy;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
