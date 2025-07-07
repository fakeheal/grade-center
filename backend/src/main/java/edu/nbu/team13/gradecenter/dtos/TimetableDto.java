package edu.nbu.team13.gradecenter.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TimetableDto {
    private Long id;

    private Long schoolYearId;

    private Long classId;

    @JsonProperty("subjects")
    private TimetableSubjectDto[] subjects;
}