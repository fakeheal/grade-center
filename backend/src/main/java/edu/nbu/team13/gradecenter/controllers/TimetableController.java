package edu.nbu.team13.gradecenter.controllers;

import edu.nbu.team13.gradecenter.dtos.TimetableDto;
import edu.nbu.team13.gradecenter.entities.Timetable;
import edu.nbu.team13.gradecenter.services.TimetableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/timetables")
public class TimetableController {
    private final TimetableService timetableService;

    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @GetMapping("/{schoolYearId}/{classId}")
    public ResponseEntity<Timetable> show(@PathVariable Long schoolYearId, @PathVariable Long classId) {
        Timetable timetable = timetableService.findBySchoolYearIdAndClassId(schoolYearId, classId);

        if(timetable == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(timetable);
    }

    @PostMapping
    public ResponseEntity<Timetable> create(@RequestBody TimetableDto timetableDto) {
        Timetable created = timetableService.create(timetableDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
