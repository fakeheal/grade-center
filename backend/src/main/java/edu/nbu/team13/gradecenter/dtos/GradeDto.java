package edu.nbu.team13.gradecenter.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeDto {
    private Long studentId;
    private Long teacherId;
    private Long subjectId;
    private Double value;
    private java.time.LocalDate date;
    private Long schoolYearId;
}

