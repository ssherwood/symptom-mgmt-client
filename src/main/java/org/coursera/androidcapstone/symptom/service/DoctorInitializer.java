package org.coursera.androidcapstone.symptom.service;

import java.util.Arrays;

import org.coursera.androidcapstone.symptom.domain.Doctor;
import org.coursera.androidcapstone.symptom.repo.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DoctorInitializer {

	@Autowired
	DoctorInitializer(DoctorRepository doctors) {
		Assert.notNull(doctors, "Doctor repository should not be null");
	
		doctors.deleteAll();
			
		doctors.save(Arrays.asList(
			new Doctor("doctor1", 1234567890, "Frank", "Burns"),
			new Doctor("doctor2", 1122334455, "Hawkeye", "Pierce")
		));
	}
}
