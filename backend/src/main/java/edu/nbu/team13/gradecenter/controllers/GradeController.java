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
    public ResponseEntity<List<GradeResponseDto>> getAllGrades() {
        List<GradeResponseDto> grades = gradeService.findAll();
        return ResponseEntity.ok(grades);
    }
}
