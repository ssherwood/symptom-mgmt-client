package org.coursera.androidcapstone.symptom;

import java.util.List;

import org.coursera.androidcapstone.symptom.model.Patient;


public class PatientResponse {

	private List<Patient> patients;

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
}
