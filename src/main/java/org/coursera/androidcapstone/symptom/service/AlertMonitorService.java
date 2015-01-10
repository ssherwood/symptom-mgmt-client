package org.coursera.androidcapstone.symptom.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.coursera.androidcapstone.symptom.domain.AlertType;
import org.coursera.androidcapstone.symptom.domain.EatingStatus;
import org.coursera.androidcapstone.symptom.domain.PainStatus;
import org.coursera.androidcapstone.symptom.domain.Patient;
import org.coursera.androidcapstone.symptom.domain.PatientAlert;
import org.coursera.androidcapstone.symptom.domain.PatientCheckin;
import org.coursera.androidcapstone.symptom.repo.PatientAlertRepository;
import org.coursera.androidcapstone.symptom.repo.PatientCheckinRepository;
import org.coursera.androidcapstone.symptom.repo.PatientRepository;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class AlertMonitorService {
	
	private static final long SCHEDULED_RATE = 15 * 60 * 1000; // repeat every 15 mins
 
	private static long TOO_LONG_SEVERE_PAIN = 12 * 60 * 60 * 1000; // 12 hours as milliseconds
	private static long TOO_LONG_MODERATE_PAIN = 16 * 60 * 60 * 1000;
	private static long TOO_LONG_NO_EAT = 12 * 60 * 60 * 1000;
	
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	PatientCheckinRepository patientCheckinRepository;
	
	@Autowired
	PatientAlertRepository patientAlertRepository;
	
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	
    @Scheduled(fixedRate = SCHEDULED_RATE)
    public void reportCurrentTime() {
        System.out.println("The time is now " + dateFormat.format(new Date()));
        
        List<Patient> patientList = (List<Patient>) patientRepository.findAll();
        
        for (Patient p : patientList) {
        	System.out.println("Checking up on " + p.getFirstName() + " " + p.getLastName());
        	
        	List<PatientAlert> unackedAlerts = (List<PatientAlert>) patientAlertRepository.findByPatient_IdAndAcknowledgedAtIsNull(p.getId());

        	boolean isAlreadAlertedOnSeverePain = false;
        	boolean isAlreadAlertedOnExtendedModeratePain = false;
        	boolean isAlreadAlertedOnNotEating = false;
        	
    		for (PatientAlert alert : unackedAlerts) {
    			if (AlertType.EXTENDED_SEVERE_PAIN.equals(alert.getAlertType())) {
    				isAlreadAlertedOnSeverePain = true;
    			}
    			else if (AlertType.EXTENDED_MODERATE_TO_SEVERE_PAIN.equals(alert.getAlertType())) {
    				isAlreadAlertedOnExtendedModeratePain = true;
    			}
    			else if (AlertType.UNABLE_TO_EAT.equals(alert.getAlertType())) {
    				isAlreadAlertedOnNotEating = true;
    			}
    		}

        	List<PatientCheckin> patientCheckinsList = (List<PatientCheckin>) patientCheckinRepository.findByPatient_IdOrderByCheckinAtDesc(p.getId());
        	
        	if (!isAlreadAlertedOnSeverePain)
        		checkForTooLongSeverePain(p, patientCheckinsList);
        	
        	if (!isAlreadAlertedOnExtendedModeratePain)
        		checkForTooLongExtendedModeratePain(p, patientCheckinsList);
        	
        	if (!isAlreadAlertedOnNotEating)
        		checkForTooLongWithoutEating(p, patientCheckinsList);
        }    
    }

    
	private void checkForTooLongSeverePain(Patient p, List<PatientCheckin> patientCheckinsList) {
		int iteration = 0;
		long severePainDuration = 0L;
		
		if (patientCheckinsList.size() > 0) {
			PatientCheckin c = patientCheckinsList.get(iteration);
			
			if (c.getPainStatus() == PainStatus.SEVERE) {
				severePainDuration = LocalDateTime.now().toDateTime().getMillis() - c.getCheckinAt().toDateTime().getMillis();
				
				while (severePainDuration < TOO_LONG_SEVERE_PAIN) {
					++iteration;
					
					if (patientCheckinsList.size() <= iteration) {
						break;
					}
					
					c = patientCheckinsList.get(iteration);
							
				    if (c.getPainStatus() != PainStatus.SEVERE) {
				    	break; // no need to check any further back
				    }
				    else {
				    	severePainDuration = LocalDateTime.now().toDateTime().getMillis() - c.getCheckinAt().toDateTime().getMillis();
				    }
				}
			}
			
			if (severePainDuration >= TOO_LONG_SEVERE_PAIN) {
				System.out.println("ALERT!!! Patient " + p.getFirstName() + " " + p.getLastName() + " has been in SEVERE pain for " + (severePainDuration / 1000 / 60) + " minutes!");
				patientAlertRepository.save(new PatientAlert(AlertType.EXTENDED_SEVERE_PAIN, p));
			}
		}
	}
    
	private void checkForTooLongExtendedModeratePain(Patient p, List<PatientCheckin> patientCheckinsList) {
		int iteration = 0;
		long severeOrModeratePainDuration = 0L;
		
		if (patientCheckinsList.size() > 0) {
			PatientCheckin c = patientCheckinsList.get(iteration);
			
			if (c.getPainStatus() == PainStatus.SEVERE || c.getPainStatus() == PainStatus.MODERATE) {
				severeOrModeratePainDuration = LocalDateTime.now().toDateTime().getMillis() - c.getCheckinAt().toDateTime().getMillis();
				
				while (severeOrModeratePainDuration < TOO_LONG_MODERATE_PAIN) {
					++iteration;
					
					if (patientCheckinsList.size() <= iteration) {
						break;
					}
					
					c = patientCheckinsList.get(iteration);
							
				    if (c.getPainStatus() == PainStatus.CONTROLLED) {
				    	break; // no need to check any further back
				    }
				    else {
				    	severeOrModeratePainDuration = LocalDateTime.now().toDateTime().getMillis() - c.getCheckinAt().toDateTime().getMillis();
				    }
				}
			}
			
			if (severeOrModeratePainDuration >= TOO_LONG_MODERATE_PAIN) {
				System.out.println("ALERT!!! Patient " + p.getFirstName() + " " + p.getLastName() + " has been in MODERATE pain for " + (severeOrModeratePainDuration / 1000 / 60) + " minutes!");
				patientAlertRepository.save(new PatientAlert(AlertType.EXTENDED_MODERATE_TO_SEVERE_PAIN, p));
			}
		}
	}


	private void checkForTooLongWithoutEating(Patient p, List<PatientCheckin> patientCheckinsList) {
		int iteration = 0;
		long notEatingDuration = 0L;
		
		if (patientCheckinsList.size() > 0) {
			PatientCheckin c = patientCheckinsList.get(iteration);
			
			if (c.getEatingStatus() == EatingStatus.NOT_EATING) {
				notEatingDuration = LocalDateTime.now().toDateTime().getMillis() - c.getCheckinAt().toDateTime().getMillis();
				
				while (notEatingDuration < TOO_LONG_NO_EAT) {
					++iteration;
					
					if (patientCheckinsList.size() <= iteration) {
						break;
					}
					
					c = patientCheckinsList.get(iteration);
							
				    if (c.getEatingStatus() != EatingStatus.NOT_EATING) {
				    	break; // no need to check any further back
				    }
				    else {
				    	notEatingDuration = LocalDateTime.now().toDateTime().getMillis() - c.getCheckinAt().toDateTime().getMillis();
				    }
				}
			}
			
			if (notEatingDuration >= TOO_LONG_NO_EAT) {
				System.out.println("ALERT!!! Patient " + p.getFirstName() + " " + p.getLastName() + " has not eaten for " + (notEatingDuration / 1000 / 60) + " minutes!");
				patientAlertRepository.save(new PatientAlert(AlertType.UNABLE_TO_EAT, p));
			}
		}
	}
}
