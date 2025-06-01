package edu.nbu.team13.gradecenter.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class TeacherDto extends UserDto {
    private Long userId;
}

