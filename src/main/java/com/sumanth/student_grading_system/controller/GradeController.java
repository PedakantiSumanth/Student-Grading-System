package com.sumanth.student_grading_system.controller;

import com.sumanth.student_grading_system.dto.GradeResponse;
import com.sumanth.student_grading_system.service.GradeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("/{studentId}")
    public GradeResponse getGrade(@PathVariable Integer studentId) {

        return gradeService.getGrade(studentId);
    }
}