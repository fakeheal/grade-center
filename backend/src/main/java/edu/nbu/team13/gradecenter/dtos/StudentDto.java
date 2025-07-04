package edu.nbu.team13.gradecenter.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {
    private Long grade;
    private Long classId;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long schoolId;
}