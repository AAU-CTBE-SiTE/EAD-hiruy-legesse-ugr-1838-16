package com.site.hospital_management.service;

import com.site.hospital_management.annotation.Loggable;
import com.site.hospital_management.model.Patient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for Patient operations.
 * Methods annotated with @Loggable are intercepted by LoggingAspect.
 * This layer is also intercepted by MyAspect via the serviceMethods() pointcut.
 */
@Service
public class PatientService {

    private final List<Patient> patients = new ArrayList<>(List.of(
            new Patient(1, "biruk",   44, 77.3, "broken hip"),
            new Patient(2, "selome",  66, 63.4, "food poisoning"),
            new Patient(3, "makeda",  34, 70.0, "asthma"),
            new Patient(4, "mahider", 24, 55.0, "vertigo")
    ));

    @Loggable
    public List<Patient> getAllPatients() {
        return patients;
    }

    @Loggable
    public Optional<Patient> getPatientById(int id) {
        return patients.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    @Loggable
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    @Loggable
    public boolean updatePatient(int id, Patient updated) {
        for (Patient p : patients) {
            if (p.getId() == id) {
                p.setName(updated.getName());
                p.setAge(updated.getAge());
                p.setWeight(updated.getWeight());
                p.setDiagnosis(updated.getDiagnosis());
                return true;
            }
        }
        return false;
    }

    @Loggable
    public boolean deletePatient(int id) {
        return patients.removeIf(p -> p.getId() == id);
    }

    /** Intentionally throws — used to demonstrate @AfterThrowing */
    @Loggable
    public Patient triggerError() {
        throw new RuntimeException("Simulated service-layer exception for AOP demo");
    }
}

