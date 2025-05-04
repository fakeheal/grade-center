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

    public Student create(StudentDto schoolDto) {
        // Convert SchoolDto to School entity
        Student student = new Student();
        student.setGrade(schoolDto.getGrade());
        student.setSchoolId(schoolDto.getSchoolId());
        student.setUserId(schoolDto.getUserId());
        student.setClassId(schoolDto.getClassId());

        // Save the school entity to the database
        return studentRepository.save(student);
    }
    public Student update(Long id, StudentDto schoolDto) {
        // Find the existing school by ID
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));

        // Update the school's properties
        existingStudent.setGrade(schoolDto.getGrade());
        existingStudent.setSchoolId(schoolDto.getSchoolId());
        existingStudent.setUserId(schoolDto.getUserId());
        existingStudent.setClassId(schoolDto.getClassId());

        // Save the updated school entity to the database
        return studentRepository.save(existingStudent);
    }
    public void delete(Long id) {
        // Find the existing school by ID
        Student existingSchool = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));

        // Delete the school entity from the database
        studentRepository.delete(existingSchool);
    }
    public Student findById(Long id) {
        // Find the existing school by ID
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));
    }
    public Page<Student> search(Long grade,Long classId,Long userId,Long schoolId, Pageable pageable) {
        return studentRepository.findByOptionalFilters(grade,classId,userId,schoolId, pageable);
    }
}
