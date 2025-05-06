package edu.nbu.team13.gradecenter.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class AbsenceDto {
    private Long studentId;
    private Long teacherId;
    private Long subjectId;
    private LocalDate date;
    private Long schoolYearId;
    private String reason;
    private Boolean Excused;
}
