package edu.nbu.team13.gradecenter.controllers;

import edu.nbu.team13.gradecenter.dtos.ParentDto;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.services.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parents")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService svc;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody ParentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(svc.create(dto));
    }

    @GetMapping
    public ResponseEntity<Page<User>> index(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pg = PageRequest.of(page, size);
        return ResponseEntity.ok(
                svc.index(firstName, lastName, email, pg));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id,
                                       @RequestBody ParentDto dto) {
        return ResponseEntity.ok(svc.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> show(@PathVariable Long id) {
        return ResponseEntity.ok(svc.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        svc.delete(id);
    }
}
