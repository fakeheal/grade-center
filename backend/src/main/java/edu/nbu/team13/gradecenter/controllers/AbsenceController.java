package edu.nbu.team13.gradecenter.controllers;

import edu.nbu.team13.gradecenter.dtos.AbsenceDto;
import edu.nbu.team13.gradecenter.dtos.GradeDto;
import edu.nbu.team13.gradecenter.entities.Absence;
import edu.nbu.team13.gradecenter.entities.Grade;
import edu.nbu.team13.gradecenter.services.AbsenceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/absences")
public class AbsenceController {
    AbsenceService absenceService;
    public AbsenceController(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @GetMapping
    public ResponseEntity<Page<Absence>> index(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long teacherId,
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) Long schoolYearId,
            @RequestParam(required = false) String reason,
            @RequestParam(required = false) Boolean excused,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Absence> absences = absenceService.search(studentId, teacherId, subjectId,date, schoolYearId,reason, excused,pageable);
        return ResponseEntity.ok(absences);
    }

    @PostMapping
    public ResponseEntity<Absence> create(@RequestBody AbsenceDto absenceDto) {
        Absence created = absenceService.create(absenceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Absence> show(@PathVariable Long id) {
        Absence absence = absenceService.findById(id);
        return ResponseEntity.ok(absence);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Absence> update(@PathVariable Long id, @RequestBody AbsenceDto absenceDto) {
        Absence updated = absenceService.update(id, absenceDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        absenceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
