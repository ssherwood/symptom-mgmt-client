package org.coursera.androidcapstone.symptom.repo;

import org.coursera.androidcapstone.symptom.domain.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * 
 */
public interface PatientRepository extends PagingAndSortingRepository<Patient, Long> {

	@PreAuthorize("hasRole('ROLE_DOCTOR')")
	@Query("SELECT p FROM Patient p WHERE CONCAT(p.firstName, p.lastName) LIKE %:searchTerm%")
	Page<Patient> findByName(@Param("searchTerm") String searchTerm, Pageable pageable);
	
	@PreAuthorize("hasRole('ROLE_DOCTOR')")
	Page<Patient> findByDoctors_Id(@Param("doctorId") Long doctorId, Pageable pageable);
	
	@PreAuthorize("hasRole('ROLE_DOCTOR')")
	Page<Patient> findByDoctors_IdAndFirstNameContainingOrLastNameContainingAllIgnoreCase(@Param("doctorId") Long doctorId, @Param("searchTerm1") String searchTerm1, @Param("searchTerm2") String searchTerm2, Pageable pageable);
	
	@PreAuthorize("hasRole('ROLE_DOCTOR')")
	@Query("SELECT p FROM Patient p INNER JOIN p.doctors doctor WHERE doctor.id = :doctorId AND LOWER(CONCAT(p.firstName, p.lastName)) LIKE %:searchTerm%")
	Page<Patient> findByNameForDoctor(@Param("doctorId") Long doctorId, @Param("searchTerm") String searchTerm, Pageable pageable);
}
