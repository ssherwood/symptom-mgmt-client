package org.coursera.androidcapstone.symptom;

import java.util.List;

import org.coursera.androidcapstone.symptom.model.Doctor;

public class DoctorResponse {

	private List<Doctor> doctors;

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}
}
