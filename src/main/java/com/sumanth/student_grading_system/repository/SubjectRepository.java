package com.sumanth.student_grading_system.repository;

import com.sumanth.student_grading_system.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository
        extends JpaRepository<Subject, Integer> {

    boolean existsBySubjectCode(String subjectCode);

    boolean existsBySubjectCodeAndIdNot(
            String subjectCode,
            Integer id
    );
}

