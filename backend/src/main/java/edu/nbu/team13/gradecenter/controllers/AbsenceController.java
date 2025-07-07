package edu.nbu.team13.gradecenter.controllers;

import edu.nbu.team13.gradecenter.dtos.AbsenceDto;
import edu.nbu.team13.gradecenter.dtos.AbsenceResponseDto;
import edu.nbu.team13.gradecenter.entities.Absence;
import edu.nbu.team13.gradecenter.services.AbsenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/absences")
public class AbsenceController {
    private final AbsenceService absenceService;

    public AbsenceController(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody AbsenceDto absenceDto) {
        absenceService.create(absenceDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<AbsenceResponseDto>> getAllAbsences() {
        List<AbsenceResponseDto> absences = absenceService.findAll();
        return ResponseEntity.ok(absences);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AbsenceResponseDto> getAbsenceById(@PathVariable Long id) {
        Absence absence = absenceService.findById(id);
        return ResponseEntity.ok(new AbsenceResponseDto(absence));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Absence> update(@PathVariable Long id, @RequestBody AbsenceDto absenceDto) {
        Absence updated = absenceService.update(id, absenceDto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        absenceService.delete(id);
    }
}