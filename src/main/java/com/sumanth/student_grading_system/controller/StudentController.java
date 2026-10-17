package com.sumanth.student_grading_system.controller;

import com.sumanth.student_grading_system.entity.Student;
import com.sumanth.student_grading_system.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Integer id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    public Student saveStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @PutMapping("/{id}")
    public Student updateStudent(
            @PathVariable Integer id,
            @RequestBody Student student) {

        Student existingStudent = studentService.getStudentById(id);

        existingStudent.setRollNumber(student.getRollNumber());
        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setDepartment(student.getDepartment());
        existingStudent.setYear(student.getYear());
        existingStudent.setSection(student.getSection());

        return studentService.saveStudent(existingStudent);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
    }
}