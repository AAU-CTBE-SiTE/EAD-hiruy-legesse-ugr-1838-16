package com.craft;

import java.util.List;

public class Student {
    private int id;
    private String name;
    private int age;
    private String city;
    private String country;
    private Address address;
    private List<Subject> subjects;

    public Student() {
    }

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public List<Subject> getSubjects() { return subjects; }
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public String getHello() {
        return "Hello Springers";
    }

    public void getDetails() {
        System.out.println("Student Details: " + this);
    }

    @Override
    public String toString() {
        return "Student{id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", address=" + address +
                ", subjects=" + subjects + '}';
    }
}