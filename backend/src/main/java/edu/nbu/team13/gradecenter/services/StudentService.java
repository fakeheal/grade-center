package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.StudentDto;
import edu.nbu.team13.gradecenter.dtos.UserDto;
import edu.nbu.team13.gradecenter.entities.Student;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.repositories.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final UserService userService;

    public StudentService(StudentRepository studentRepository, UserService userService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
    }

    @Transactional
    public Student create(StudentDto studentDto) {
        // Create UserDto from StudentDto
        UserDto userDto = new UserDto();
        userDto.setFirstName(studentDto.getFirstName());
        userDto.setLastName(studentDto.getLastName());
        userDto.setEmail(studentDto.getEmail());
        userDto.setPassword(studentDto.getPassword());
        userDto.setSchoolId(studentDto.getSchoolId());

        // Create the User entity
        User user = userService.create(userDto, UserRole.STUDENT);

        // Convert StudentDto to Student entity
        Student student = new Student();
        student.setGrade(studentDto.getGrade());
        student.setUser(user); // Link to the newly created user
        student.setClassId(studentDto.getClassId());

        // Save the student entity to the database
        return studentRepository.save(student);
    }

    @Transactional
    public Student update(Long id, StudentDto studentDto) {
        // Find the existing student by ID
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Update the associated User entity
        UserDto userDto = new UserDto();
        userDto.setId(existingStudent.getUser().getId()); // Ensure the correct user is updated
        userDto.setFirstName(studentDto.getFirstName());
        userDto.setLastName(studentDto.getLastName());
        userDto.setEmail(studentDto.getEmail());
        userDto.setPassword(studentDto.getPassword()); // Handle password update if provided
        userDto.setSchoolId(studentDto.getSchoolId());
        userService.update(existingStudent.getUser().getId(), userDto);

        // Update the student's properties
        existingStudent.setGrade(studentDto.getGrade());
        existingStudent.setClassId(studentDto.getClassId());

        // Save the updated student entity to the database
        return studentRepository.save(existingStudent);
    }

    @Transactional
    public void delete(Long id) {
        // Find the existing student by ID
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Delete the student entity from the database
        studentRepository.delete(existingStudent);

        // Delete the associated User entity
        userService.delete(existingStudent.getUser().getId());
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
