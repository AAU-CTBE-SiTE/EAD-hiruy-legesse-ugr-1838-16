package com.site.hospital_management.model;

import lombok.Data;

@Data
public class Patient {
    int id;
    String name;
    int age;
    double weight;
    String diagnosis;

    public Patient(int id, String name, int age, double weight, String diagnosis) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.diagnosis = diagnosis;
    }
}
