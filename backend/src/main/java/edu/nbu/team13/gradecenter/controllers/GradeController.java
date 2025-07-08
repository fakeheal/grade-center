package edu.nbu.team13.gradecenter.controllers;

import edu.nbu.team13.gradecenter.dtos.GradeDto;
import edu.nbu.team13.gradecenter.dtos.GradeResponseDto;
import edu.nbu.team13.gradecenter.services.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grades")
public class GradeController {
    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody GradeDto gradeDto) {
        gradeService.create(gradeDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<GradeResponseDto>> getAllGrades(@RequestParam(required = false) Long studentId) {
        List<GradeResponseDto> grades;
        if (studentId != null) {
            grades = gradeService.findByStudentId(studentId);
        } else {
            grades = gradeService.findAll();
        }
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeResponseDto> getGradeById(@PathVariable Long id) {
        return ResponseEntity.ok(new GradeResponseDto(gradeService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeResponseDto> updateGrade(@PathVariable Long id, @RequestBody GradeDto gradeDto) {
        return ResponseEntity.ok(new GradeResponseDto(gradeService.update(id, gradeDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
