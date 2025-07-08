package edu.nbu.team13.gradecenter.dtos;

import edu.nbu.team13.gradecenter.entities.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentResponseDto {
    private Long id;
    private Long grade;
    private Long classId;
    private String firstName;
    private String lastName;
    private String email;
    private Long userId;

    public StudentResponseDto(Student student) {
        this.id = student.getId();
        this.grade = student.getGrade();
        this.classId = student.getClassId();
        if (student.getUser() != null) {
            this.firstName = student.getUser().getFirstName();
            this.lastName = student.getUser().getLastName();
            this.email     = student.getUser().getEmail();
            this.userId = student.getUser().getId();
        }
    }
}
