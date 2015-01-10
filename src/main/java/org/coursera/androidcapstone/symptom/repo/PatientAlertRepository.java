package org.coursera.androidcapstone.symptom.repo;

import org.coursera.androidcapstone.symptom.domain.PatientAlert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * 
 */
public interface PatientAlertRepository extends PagingAndSortingRepository<PatientAlert, Long> {

	@PreAuthorize("hasRole('ROLE_DOCTOR')")
	Page<PatientAlert> findByAcknowledgedAtIsNull(Pageable pageable);

	
	Iterable<PatientAlert> findByPatient_IdAndAcknowledgedAtIsNull(@Param("patientId") Long patientId);
	
	@PreAuthorize("hasRole('ROLE_DOCTOR')")
	Page<PatientAlert> findByAcknowledgedAtIsNullOrderByAlertedAtDesc(Pageable pageable);
	
	@PreAuthorize("hasRole('ROLE_DOCTOR')")
	Page<PatientAlert> findByPatient_Doctors_IdAndAcknowledgedAtIsNull(@Param("doctorId") Long doctorId, Pageable pageable);
}
