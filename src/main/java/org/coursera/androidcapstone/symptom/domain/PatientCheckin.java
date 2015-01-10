package org.coursera.androidcapstone.symptom.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 */
@Entity
public class PatientCheckin implements Serializable {

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
	private LocalDateTime checkinAt;
	 
	@NotNull
	@Enumerated(EnumType.STRING)
    private PainStatus painStatus;
	
	@NotNull
	@Enumerated(EnumType.STRING)
    private EatingStatus eatingStatus;
	
	@OneToMany(mappedBy="patientCheckin")
	private List<MedicineTaken> medicineTaken;
	
    /**
     * 
     */
    protected PatientCheckin() {
    }

    ///
    
    @PrePersist
    void createdAt() {
    	this.checkinAt = LocalDateTime.now();
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

	public LocalDateTime getCheckinAt() {
		return checkinAt;
	}

	public void setCheckinAt(LocalDateTime checkinAt) {
		this.checkinAt = checkinAt;
	}

	public PainStatus getPainStatus() {
		return painStatus;
	}

	public void setPainStatus(PainStatus painStatus) {
		this.painStatus = painStatus;
	}

	public EatingStatus getEatingStatus() {
		return eatingStatus;
	}

	public void setEatingStatus(EatingStatus eatingStatus) {
		this.eatingStatus = eatingStatus;
	}

	public List<MedicineTaken> getMedicineTaken() {
		return medicineTaken;
	}

	public void setMedicineTaken(List<MedicineTaken> medicineTaken) {
		this.medicineTaken = medicineTaken;
	}
}