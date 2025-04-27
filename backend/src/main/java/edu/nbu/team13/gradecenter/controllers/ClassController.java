package edu.nbu.team13.gradecenter.controllers;

import edu.nbu.team13.gradecenter.dtos.ClassDto;
import edu.nbu.team13.gradecenter.dtos.SchoolDto;
import edu.nbu.team13.gradecenter.entities.Class;
import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.services.ClassService;
import edu.nbu.team13.gradecenter.services.SchoolService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classes")
public class ClassController {
    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    public ResponseEntity<Page<Class>> index(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long grade,
            @RequestParam(required = false) Long schoolId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Class> classes = classService.search(name, grade,schoolId, pageable);
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Class> show(@PathVariable Long id) {
        Class cls = classService.findById(id);
        return ResponseEntity.ok(cls);
    }

    @PostMapping
    public ResponseEntity<Class> create(@RequestBody ClassDto classDto) {
        Class created = classService.create(classDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Class> update(@PathVariable Long id, @RequestBody ClassDto classDto) {
        Class updated = classService.update(id,classDto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
}
