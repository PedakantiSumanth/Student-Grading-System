package com.sumanth.student_grading_system.service;

import com.sumanth.student_grading_system.entity.Subject;
import com.sumanth.student_grading_system.repository.MarkRepository;
import com.sumanth.student_grading_system.repository.SubjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    private final MarkRepository markRepository;


    public SubjectService(
            SubjectRepository subjectRepository,
            MarkRepository markRepository) {

        this.subjectRepository =
                subjectRepository;

        this.markRepository =
                markRepository;
    }


    public List<Subject> getAllSubjects() {

        return subjectRepository.findAll();
    }


    public Subject getSubjectById(Integer id) {

        return subjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Subject not found"
                        )
                );
    }


    public Subject saveSubject(Subject subject) {

        if (subject.getSubjectCode() == null ||
                subject.getSubjectCode().trim().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Subject code is required"
            );
        }


        if (subject.getSubjectName() == null ||
                subject.getSubjectName().trim().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Subject name is required"
            );
        }


        if (subject.getMaxMarks() == null ||
                subject.getMaxMarks() <= 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Max marks must be greater than 0"
            );
        }


        if (subject.getId() == null) {

            if (subjectRepository.existsBySubjectCode(
                    subject.getSubjectCode())) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Subject code already exists"
                );
            }

        } else {

            if (subjectRepository
                    .existsBySubjectCodeAndIdNot(
                            subject.getSubjectCode(),
                            subject.getId())) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Subject code already exists"
                );
            }
        }


        return subjectRepository.save(subject);
    }


    public void deleteSubject(Integer id) {

        if (!subjectRepository.existsById(id)) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Subject not found"
            );
        }


        if (markRepository.existsBySubjectId(id)) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot delete subject because marks exist for this subject"
            );
        }


        subjectRepository.deleteById(id);
    }
}

