package edu.nbu.team13.gradecenter.controllers;

import edu.nbu.team13.gradecenter.dtos.SubjectDto;
import edu.nbu.team13.gradecenter.entities.Subject;
import edu.nbu.team13.gradecenter.repositories.views.SubjectTeacherView;
import edu.nbu.team13.gradecenter.services.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Subject>> index(
            @RequestParam() Long schoolId
    ) {
        List<Subject> subjects = subjectService.findAll(schoolId);
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> show(@PathVariable Long id) {
        Subject subject = subjectService.findById(id);
        return ResponseEntity.ok(subject);
    }

    @PostMapping
    public ResponseEntity<Subject> create(@RequestBody SubjectDto subjectDto) {
        Subject created = subjectService.create(subjectDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> update(@PathVariable Long id, @RequestBody SubjectDto subjectDto) {
        Subject updated = subjectService.update(id, subjectDto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // @TODO: Delete related records - e.g. grades, students, etc.
        subjectService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<List<SubjectTeacherView>> getSubjectsWithTeachers(@PathVariable Long id) {
        List<SubjectTeacherView> subjects = subjectService.findAllWithTeachers(id);
        return ResponseEntity.ok(subjects);
    }

}
