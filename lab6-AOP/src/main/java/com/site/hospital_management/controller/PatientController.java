package com.site.hospital_management.controller;

import com.site.hospital_management.model.Patient;
import com.site.hospital_management.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patient")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable int id) {
        return patientService.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public String addPatient(@RequestBody Patient patient) {
        patientService.addPatient(patient);
        return "Patient added successfully!";
    }

    @PutMapping("/update/{id}")
    public String updatePatient(@PathVariable int id, @RequestBody Patient updatedPatient) {
        return patientService.updatePatient(id, updatedPatient)
                ? "Patient updated successfully!"
                : "Patient not found";
    }

    @DeleteMapping("/delete/{id}")
    public String deletePatient(@PathVariable int id) {
        return patientService.deletePatient(id)
                ? "Patient deleted successfully!"
                : "Patient not found";
    }

    /** Triggers an exception — demonstrates @AfterThrowing advice */
    @GetMapping("/patient/error")
    public Patient triggerError() {
        return patientService.triggerError();
    }
}
