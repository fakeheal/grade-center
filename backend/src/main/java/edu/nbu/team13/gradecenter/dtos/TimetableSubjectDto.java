package edu.nbu.team13.gradecenter.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TimetableSubjectDto {
    private Long subjectId;
    private Long teacherId;
    private byte dayOfWeek;
    private int startTime;

    public TimetableSubjectDto() {
    }

    public TimetableSubjectDto(Long subjectId, Long teacherId, byte dayOfWeek, int startTime) {
        this.subjectId = subjectId;
        this.teacherId = teacherId;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

}
