package com.sumanth.student_grading_system.controller;

import com.sumanth.student_grading_system.entity.Mark;
import com.sumanth.student_grading_system.service.MarkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/marks")
public class MarkController {

    private final MarkService markService;

    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @GetMapping
    public List<Mark> getAllMarks() {
        return markService.getAllMarks();
    }

    @GetMapping("/{id}")
    public Mark getMarkById(@PathVariable Integer id) {
        return markService.getMarkById(id);
    }

    @PostMapping
    public Mark saveMark(@RequestBody Mark mark) {
        return markService.saveMark(mark);
    }

    @PutMapping("/{id}")
    public Mark updateMark(
            @PathVariable Integer id,
            @RequestBody Mark mark) {

        Mark existingMark = markService.getMarkById(id);

        existingMark.setStudentId(mark.getStudentId());
        existingMark.setSubjectId(mark.getSubjectId());
        existingMark.setMarks(mark.getMarks());

        return markService.saveMark(existingMark);
    }

    @DeleteMapping("/{id}")
    public void deleteMark(@PathVariable Integer id) {
        markService.deleteMark(id);
    }
}

