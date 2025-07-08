package edu.nbu.team13.gradecenter.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GradeDto {
    private Long studentId;
    private Long teacherId;
    private Long subjectId;
    private Double value;
    private LocalDate date;
    private Long schoolYearId;
}