package com.sumanth.student_grading_system.controller;

import com.sumanth.student_grading_system.entity.Subject;
import com.sumanth.student_grading_system.service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public Subject getSubjectById(@PathVariable Integer id) {
        return subjectService.getSubjectById(id);
    }

    @PostMapping
    public Subject saveSubject(@RequestBody Subject subject) {
        return subjectService.saveSubject(subject);
    }

    @PutMapping("/{id}")
    public Subject updateSubject(
            @PathVariable Integer id,
            @RequestBody Subject subject) {

        Subject existingSubject = subjectService.getSubjectById(id);

        existingSubject.setSubjectCode(subject.getSubjectCode());
        existingSubject.setSubjectName(subject.getSubjectName());
        existingSubject.setMaxMarks(subject.getMaxMarks());

        return subjectService.saveSubject(existingSubject);
    }

    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable Integer id) {
        subjectService.deleteSubject(id);
    }
}

