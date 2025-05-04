package edu.nbu.team13.gradecenter.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClassDto {
    private String name;
    private Long schoolId;
    private Long grade;

    public ClassDto() {
    }
    public ClassDto(String name, Long schoolId, Long grade) {
        this.name = name;
        this.schoolId = schoolId;
        this.grade = grade;
    }
}
