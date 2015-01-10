package org.coursera.androidcapstone.symptom.domain;

import org.joda.time.LocalDateTime;
import org.springframework.data.rest.core.config.Projection;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Projection used to flatten Patient Alerts for easier list displays
 * 
 */
@Projection(name = "alertDetail", types = PatientAlert.class)
public interface PatientAlertProjection {

	Patient getPatient();
    
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	LocalDateTime getAlertedAt();
    
	AlertType getAlertType();
    
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	LocalDateTime getAcknowledgedAt();
	
	//Doctor getAcknowledgedBy();
	
	String getNotes();
}
