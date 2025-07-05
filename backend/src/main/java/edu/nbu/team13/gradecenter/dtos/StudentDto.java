package edu.nbu.team13.gradecenter.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto extends UserDto{
    private Long grade;
    private Long classId;
    private Long schoolId;
}