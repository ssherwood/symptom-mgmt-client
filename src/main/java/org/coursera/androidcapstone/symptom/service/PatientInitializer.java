package org.coursera.androidcapstone.symptom.service;

import java.util.Arrays;

import org.coursera.androidcapstone.symptom.domain.Patient;
import org.coursera.androidcapstone.symptom.repo.PatientRepository;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class PatientInitializer {

	@Autowired
	PatientInitializer(PatientRepository patients) {
		Assert.notNull(patients, "Patient repository should not be null");
	
		patients.deleteAll();
			
		patients.save(Arrays.asList(
			new Patient("patient1", "a071123252", "Hatsune", "Miku", "555-867-5309", "hatsune.miku@coursera.com", LocalDate.parse("1982-11-17")),
			new Patient("patient2", "a071526313", "Jack", "Dawson", "555-777-9311", "jack.dawson@coursera.com", LocalDate.parse("1934-03-14")),
			new Patient("patient3", "a874521963", "David", "Manning", "555-664-1353", "david.manning@coursera.com", LocalDate.parse("1979-06-02")),
			new Patient("patient4", "a972533206", "Allegra", "Coleman", "555-821-7665", "allegra.coleman@coursera.com", LocalDate.parse("1985-09-17"))
		));
	}
}
