package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.SchoolDto;
import edu.nbu.team13.gradecenter.dtos.StudentDto;
import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.entities.Student;
import edu.nbu.team13.gradecenter.repositories.SchoolRepository;
import edu.nbu.team13.gradecenter.repositories.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(StudentDto studentDto) {
        // Convert StudentDto to Student entity
        Student student = new Student();
        student.setGrade(studentDto.getGrade());
        student.setUserId(studentDto.getUserId());
        student.setClassId(studentDto.getClassId());

        // Save the student entity to the database
        return studentRepository.save(student);
    }
    public Student update(Long id, StudentDto studentDto) {
        // Find the existing student by ID
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Update the student's properties
        existingStudent.setGrade(studentDto.getGrade());
        existingStudent.setUserId(studentDto.getUserId());
        existingStudent.setClassId(studentDto.getClassId());

        // Save the updated student entity to the database
        return studentRepository.save(existingStudent);
    }
    public void delete(Long id) {
        // Find the existing student by ID
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Delete the student entity from the database
        studentRepository.delete(existingStudent);
    }
    public Student findById(Long id) {
        // Find the existing student by ID
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }
    public Page<Student> search(Long grade,Long classId,Long userId, Pageable pageable) {
        return studentRepository.findByOptionalFilters(grade,classId,userId, pageable);
    }
}
