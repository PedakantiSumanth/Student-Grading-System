package com.sumanth.student_grading_system.dto;

public class GradeResponse {

    private Integer studentId;
    private String name;
    private String rollNumber;
    private String department;
    private Integer year;
    private String section;
    private double average;
    private String grade;

    public GradeResponse(Integer studentId, String name, String rollNumber,
            String department, Integer year, String section,
            double average, String grade) {

        this.studentId = studentId;
        this.name = name;
        this.rollNumber = rollNumber;
        this.department = department;
        this.year = year;
        this.section = section;
        this.average = average;
        this.grade = grade;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getDepartment() {
        return department;
    }

    public Integer getYear() {
        return year;
    }

    public String getSection() {
        return section;
    }

    public double getAverage() {
        return average;
    }

    public String getGrade() {
        return grade;
    }
}