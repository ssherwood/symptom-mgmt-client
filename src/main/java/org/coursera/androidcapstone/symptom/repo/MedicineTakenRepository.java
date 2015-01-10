package org.coursera.androidcapstone.symptom.repo;

import org.coursera.androidcapstone.symptom.domain.MedicineTaken;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 
 */
//@RepositoryRestResource(exported = false)
public interface MedicineTakenRepository extends PagingAndSortingRepository<MedicineTaken, Long> {
}
