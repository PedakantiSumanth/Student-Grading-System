package com.sumanth.student_grading_system.repository;

import com.sumanth.student_grading_system.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository
        extends JpaRepository<Student, Integer> {

    boolean existsByRollNumber(String rollNumber);

    boolean existsByEmail(String email);

    boolean existsByRollNumberAndIdNot(
            String rollNumber,
            Integer id
    );

    boolean existsByEmailAndIdNot(
            String email,
            Integer id
    );
}

