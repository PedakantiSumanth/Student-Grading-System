package com.sumanth.student_grading_system.repository;

import com.sumanth.student_grading_system.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkRepository
        extends JpaRepository<Mark, Integer> {

    boolean existsByStudentIdAndSubjectId(
            Integer studentId,
            Integer subjectId
    );

    boolean existsByStudentIdAndSubjectIdAndIdNot(
            Integer studentId,
            Integer subjectId,
            Integer id
    );

    boolean existsByStudentId(Integer studentId);

    boolean existsBySubjectId(Integer subjectId);
}

