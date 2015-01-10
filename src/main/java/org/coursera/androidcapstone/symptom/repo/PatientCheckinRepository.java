package org.coursera.androidcapstone.symptom.repo;

import org.coursera.androidcapstone.symptom.domain.PatientCheckin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * 
 */
public interface PatientCheckinRepository extends PagingAndSortingRepository<PatientCheckin, Long> {

	Iterable<PatientCheckin> findByPatient_IdOrderByCheckinAtDesc(@Param("patientId") Long patientId);
	
	Page<PatientCheckin> findByPatient_Id(@Param("patientId") Long patientId, Pageable pageable);
}
