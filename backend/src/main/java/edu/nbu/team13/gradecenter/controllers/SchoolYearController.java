package edu.nbu.team13.gradecenter.controllers;

import edu.nbu.team13.gradecenter.dtos.SchoolYearDto;
import edu.nbu.team13.gradecenter.entities.SchoolYear;
import edu.nbu.team13.gradecenter.services.SchoolYearService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/school-years")
@RequiredArgsConstructor
public class SchoolYearController {

    private final SchoolYearService svc;

    @PostMapping
    public ResponseEntity<SchoolYear> create(@RequestBody SchoolYearDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(svc.create(dto));
    }

    @GetMapping
    public ResponseEntity<Page<SchoolYear>> index(
            @RequestParam(required = false) Short year,
            @RequestParam(required = false) Byte  term,
            @RequestParam(required = false) Long  schoolId,
            @RequestParam(defaultValue = "0") int  page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pg = PageRequest.of(page, size);
        return ResponseEntity.ok(svc.index(year, term, schoolId, pg));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolYear> update(@PathVariable Long id,
                                             @RequestBody SchoolYearDto dto) {
        return ResponseEntity.ok(svc.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { svc.delete(id); }
}
