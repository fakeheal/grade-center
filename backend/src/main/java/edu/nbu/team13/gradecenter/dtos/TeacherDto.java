package edu.nbu.team13.gradecenter.dtos;

import edu.nbu.team13.gradecenter.entities.Subject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class TeacherDto extends UserDto {
    private Long userId;
    private Long grade;

    private Set<Subject> subjects = new HashSet<>();
}

