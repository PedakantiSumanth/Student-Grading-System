package com.sumanth.student_grading_system.service;

import com.sumanth.student_grading_system.dto.GradeResponse;
import com.sumanth.student_grading_system.entity.Mark;
import com.sumanth.student_grading_system.entity.Student;
import com.sumanth.student_grading_system.entity.Subject;
import com.sumanth.student_grading_system.repository.MarkRepository;
import com.sumanth.student_grading_system.repository.StudentRepository;
import com.sumanth.student_grading_system.repository.SubjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GradeService {

    private final MarkRepository markRepository;

    private final StudentRepository studentRepository;

    private final SubjectRepository subjectRepository;


    public GradeService(
            MarkRepository markRepository,
            StudentRepository studentRepository,
            SubjectRepository subjectRepository) {

        this.markRepository = markRepository;

        this.studentRepository = studentRepository;

        this.subjectRepository = subjectRepository;
    }


    public double calculateAverage(Integer studentId) {

        List<Mark> marks =
                markRepository.findAll();


        double totalObtained = 0;

        double totalMaximum = 0;


        for (Mark mark : marks) {

            if (mark.getStudentId().equals(studentId)) {

                Subject subject =
                        subjectRepository.findById(
                                mark.getSubjectId()
                        ).orElse(null);


                if (subject != null) {

                    totalObtained =
                            totalObtained +
                            mark.getMarks();


                    totalMaximum =
                            totalMaximum +
                            subject.getMaxMarks();
                }
            }
        }


        if (totalMaximum == 0) {

            return 0;
        }


        return (totalObtained / totalMaximum) * 100;
    }


    public String calculateGrade(double average) {

        if (average >= 90) {

            return "A+";

        } else if (average >= 80) {

            return "A";

        } else if (average >= 70) {

            return "B";

        } else if (average >= 60) {

            return "C";

        } else if (average >= 50) {

            return "D";

        } else {

            return "F";
        }
    }


    public GradeResponse getGrade(Integer studentId) {

        Student student =
                studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Student not found"
                        )
                );


        double average =
                calculateAverage(studentId);


        String grade =
                calculateGrade(average);


        return new GradeResponse(
                student.getId(),
                student.getName(),
                student.getRollNumber(),
                student.getDepartment(),
                student.getYear(),
                student.getSection(),
                average,
                grade
        );
    }
}

