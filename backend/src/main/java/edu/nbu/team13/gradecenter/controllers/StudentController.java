package edu.nbu.team13.gradecenter.controllers;

import edu.nbu.team13.gradecenter.dtos.StudentDto;
import edu.nbu.team13.gradecenter.dtos.StudentResponseDto;
import edu.nbu.team13.gradecenter.entities.Student;
import edu.nbu.team13.gradecenter.services.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping
    public ResponseEntity<Page<StudentResponseDto>> index(
            @RequestParam(required = false) Long grade,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> students = studentService.search(grade,classId,userId, pageable);
        return ResponseEntity.ok(students.map(StudentResponseDto::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> show(@PathVariable Long id) {
        Student student = studentService.findById(id);
        return ResponseEntity.ok(new StudentResponseDto(student));
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody StudentDto studentDto) {
        Student created = studentService.create(studentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        Student updated = studentService.update(id,studentDto);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        studentService.delete(id);
    }
}