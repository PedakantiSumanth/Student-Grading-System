package com.sumanth.student_grading_system.service;

import com.sumanth.student_grading_system.entity.Student;
import com.sumanth.student_grading_system.repository.MarkRepository;
import com.sumanth.student_grading_system.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final MarkRepository markRepository;


    public StudentService(
            StudentRepository studentRepository,
            MarkRepository markRepository) {

        this.studentRepository =
                studentRepository;

        this.markRepository =
                markRepository;
    }


    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }


    public Student getStudentById(Integer id) {

        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Student not found"
                        )
                );
    }


    public Student saveStudent(Student student) {

        if (student.getName() == null ||
                student.getName().trim().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Student name is required"
            );
        }


        if (student.getRollNumber() == null ||
                student.getRollNumber().trim().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Roll number is required"
            );
        }


        if (student.getDepartment() == null ||
                student.getDepartment().trim().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Department is required"
            );
        }


        if (student.getSection() == null ||
                student.getSection().trim().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Section is required"
            );
        }


        if (student.getYear() == null ||
                student.getYear() < 1 ||
                student.getYear() > 4) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Year must be between 1 and 4"
            );
        }


        if (student.getEmail() == null ||
                student.getEmail().trim().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email is required"
            );
        }


        if (student.getId() == null) {

            if (studentRepository.existsByRollNumber(
                    student.getRollNumber())) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Roll number already exists"
                );
            }


            if (studentRepository.existsByEmail(
                    student.getEmail())) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Email already exists"
                );
            }

        } else {

            if (studentRepository
                    .existsByRollNumberAndIdNot(
                            student.getRollNumber(),
                            student.getId())) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Roll number already exists"
                );
            }


            if (studentRepository
                    .existsByEmailAndIdNot(
                            student.getEmail(),
                            student.getId())) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Email already exists"
                );
            }
        }


        return studentRepository.save(student);
    }


    public void deleteStudent(Integer id) {

        if (!studentRepository.existsById(id)) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Student not found"
            );
        }


        if (markRepository.existsByStudentId(id)) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot delete student because marks exist for this student"
            );
        }


        studentRepository.deleteById(id);
    }
}

