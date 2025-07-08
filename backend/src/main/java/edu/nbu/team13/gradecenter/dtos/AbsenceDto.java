package edu.nbu.team13.gradecenter.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AbsenceDto {
    private Long studentId;
    private Long classId;
    private Long subjectId;
    private LocalDate date;
    private Integer hour;
    private Boolean excused;
    private String reason;
}
