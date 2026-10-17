package com.sumanth.student_grading_system.service;

import com.sumanth.student_grading_system.entity.Mark;
import com.sumanth.student_grading_system.entity.Subject;
import com.sumanth.student_grading_system.repository.MarkRepository;
import com.sumanth.student_grading_system.repository.SubjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MarkService {

    private final MarkRepository markRepository;

    private final SubjectRepository subjectRepository;


    public MarkService(
            MarkRepository markRepository,
            SubjectRepository subjectRepository) {

        this.markRepository = markRepository;

        this.subjectRepository = subjectRepository;
    }


    public List<Mark> getAllMarks() {

        return markRepository.findAll();
    }


    public Mark getMarkById(Integer id) {

        return markRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Mark not found"
                        )
                );
    }


    public Mark saveMark(Mark mark) {

        if (mark.getMarks() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Marks cannot be empty"
            );
        }


        if (mark.getStudentId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Student ID is required"
            );
        }


        if (mark.getSubjectId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Subject ID is required"
            );
        }


        Subject subject =
                subjectRepository.findById(
                        mark.getSubjectId()
                )
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Subject not found"
                        )
                );


        Integer maxMarks =
                subject.getMaxMarks();


        if (mark.getMarks() < 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Marks cannot be negative"
            );
        }


        if (mark.getMarks() > maxMarks) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Marks cannot be greater than subject maximum marks: "
                            + maxMarks
            );
        }


        if (mark.getId() == null) {

            if (
                markRepository.existsByStudentIdAndSubjectId(
                        mark.getStudentId(),
                        mark.getSubjectId()
                )
            ) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Mark already exists for this student and subject"
                );
            }

        } else {

            if (
                markRepository
                    .existsByStudentIdAndSubjectIdAndIdNot(
                        mark.getStudentId(),
                        mark.getSubjectId(),
                        mark.getId()
                    )
            ) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Mark already exists for this student and subject"
                );
            }
        }


        return markRepository.save(mark);
    }


    public void deleteMark(Integer id) {

        if (!markRepository.existsById(id)) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Mark not found"
            );
        }


        markRepository.deleteById(id);
    }
}