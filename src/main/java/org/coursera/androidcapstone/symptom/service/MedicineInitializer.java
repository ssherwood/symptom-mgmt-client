package org.coursera.androidcapstone.symptom.service;

import java.util.Arrays;

import org.coursera.androidcapstone.symptom.domain.Medicine;
import org.coursera.androidcapstone.symptom.repo.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class MedicineInitializer {

	@Autowired
	MedicineInitializer(MedicineRepository medicines) {
		Assert.notNull(medicines, "Medicine repository should not be null");
	
		medicines.deleteAll();
			
		medicines.save(Arrays.asList(
			new Medicine("Fentanyl", "Actiq, Duragesic , Fentora , Lazanda, Sublimaze", 2),
			new Medicine("Hydrocodone", "Norco, Vicodin", 2),
			new Medicine("Hydromorphone", "Dilaudid, Exalgo", 2),
			new Medicine("Morphine", "Astramorph, Avinza", 2),
			new Medicine("Oxycodone", "OxyContin, Percocet, Vicodin", 2),
			new Medicine("Methadone", "Dolophine, Methadose", 2),
			new Medicine("Tramadol", "Ultram", 4),
			new Medicine("Carbamazepine", "Tegretol", 0),
			new Medicine("Gabapentin", "Neurontin", 0),
			new Medicine("Levetiracetam", "Keppra", 0),
			new Medicine("Oxcarbazepine", "Trileptal", 0),
			new Medicine("Phenytoin", "Dilantin", 0),
			new Medicine("Pregabalin", "Lyrica", 5),
			new Medicine("Topiramate", "Topamax", 0),
			new Medicine("Zonisamide", "Zonegran", 0)
		));
	}
}
