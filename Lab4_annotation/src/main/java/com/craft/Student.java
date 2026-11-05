package com.craft;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Student {

    private int id;
    private String name;
    private int age;
    private Address address;
    private List<Subject> subjects;

    // Constructor injection: Spring supplies every parameter
    @Autowired
    public Student(@Value("#{101}") int id,
                   @Value("John Doe") String name,
                   @Value("#{20}") int age,
                   Address address,
                   List<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.subjects = subjects;
        System.out.println("Student Parameterized Constructor Called");
    }

    public void getDetails() {
        System.out.println("Student Name: " + name);
        int index = 1;
        for (Subject subject : subjects) {
            System.out.println("Subject " + index + ": " + subject.getSubjectName());
            index++;
        }
    }
}