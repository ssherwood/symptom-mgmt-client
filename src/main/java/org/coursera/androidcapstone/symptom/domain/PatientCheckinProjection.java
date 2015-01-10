package org.coursera.androidcapstone.symptom.domain;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.data.rest.core.config.Projection;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Projection used to flatten Patient Check-ins for easier list displays
 * 
 */
@Projection(name = "checkinDetail", types = PatientCheckin.class)
public interface PatientCheckinProjection {

	Patient getPatient();
    
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	LocalDateTime getCheckinAt();
    
	PainStatus getPainStatus();
	
	EatingStatus getEatingStatus();
	
	List<MedicineTaken> getMedicineTaken();
}
