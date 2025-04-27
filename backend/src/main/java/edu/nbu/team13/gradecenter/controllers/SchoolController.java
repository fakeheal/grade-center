package edu.nbu.team13.gradecenter.controllers;

import edu.nbu.team13.gradecenter.dtos.SchoolDto;
import edu.nbu.team13.gradecenter.dtos.UserDto;
import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.services.SchoolService;
import edu.nbu.team13.gradecenter.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schools")
public class SchoolController {
    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }
    @GetMapping
    public ResponseEntity<Page<School>> index(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<School> users = schoolService.search(name, address, pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<School> show(@PathVariable Long id) {
        School school = schoolService.findById(id);
        return ResponseEntity.ok(school);
    }

    @PostMapping
    public ResponseEntity<School> create(@RequestBody SchoolDto schoolDto) {
        School created = schoolService.create(schoolDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @PostMapping("/update")
    public ResponseEntity<School> update(@PathVariable Long id, @RequestBody SchoolDto schoolDto) {
        School updated = schoolService.update(id,schoolDto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
}
