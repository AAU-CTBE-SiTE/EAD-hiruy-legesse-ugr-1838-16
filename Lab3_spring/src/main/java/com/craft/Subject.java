package com.craft;

public class Subject {
    private String subjectName;

    public Subject() {
    }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "Subject{subjectName='" + subjectName + "'}";
    }
}