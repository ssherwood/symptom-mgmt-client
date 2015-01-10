package org.coursera.androidcapstone.symptom.repo;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.coursera.androidcapstone.symptom.Application;
import org.coursera.androidcapstone.symptom.domain.Doctor;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class DoctorRepositoryTest {

	@Autowired
	DoctorRepository repository;

	@Test
	public void findsAllOrders() {
		Iterable<Doctor> doctors = repository.findAll();
		assertThat(doctors, is(Matchers.<Doctor> iterableWithSize(2)));
	}
}
