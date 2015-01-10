package org.coursera.androidcapstone.symptom.repo;

import org.coursera.androidcapstone.symptom.domain.Doctor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 
 */
public interface DoctorRepository extends PagingAndSortingRepository<Doctor, Long> {

	
}
