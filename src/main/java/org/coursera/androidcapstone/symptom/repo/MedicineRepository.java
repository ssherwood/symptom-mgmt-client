package org.coursera.androidcapstone.symptom.repo;

import org.coursera.androidcapstone.symptom.domain.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 
 */
@RepositoryRestResource(collectionResourceRel = "medicine", path = "medicine")
public interface MedicineRepository extends PagingAndSortingRepository<Medicine, Long> {

	Page<Medicine> findByCsaScheduleLessThan(@Param("csaScheduleClass") Integer csaScheduleClass, Pageable pageable); 
}
