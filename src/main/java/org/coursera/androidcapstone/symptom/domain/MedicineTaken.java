package org.coursera.androidcapstone.symptom.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 */
@Entity
public class MedicineTaken implements Serializable {

	private static final long serialVersionUID = 20141101L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private PatientCheckin patientCheckin;
	
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	private LocalDateTime takenAt;
	 
	@ManyToOne
	private Medicine medicine;

	// cheating?
	private String medicineName;
	
    /**
     * 
     */
    protected MedicineTaken() {
    }

    ///
    
	public Long getId() {
		return id;
	}

	public PatientCheckin getPatientCheckin() {
		return patientCheckin;
	}

	public void setPatientCheckin(PatientCheckin patientCheckin) {
		this.patientCheckin = patientCheckin;
	}

	public LocalDateTime getTakenAt() {
		return takenAt;
	}

	public void setTakenAt(LocalDateTime takenAt) {
		this.takenAt = takenAt;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public String getMedicineName() {
		return medicine.getGenericName(); // todo this is an experiment - and it works!  this adds the value to the projection
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
}
