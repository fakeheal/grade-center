package edu.nbu.team13.gradecenter.dtos;

import edu.nbu.team13.gradecenter.entities.Absence;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AbsenceResponseDto {
    private Long id;
    private StudentResponseDto student;
    private ClassResponseDto classEntity;
    private SubjectResponseDto subject;
    private LocalDate date;
    private Integer hour;
    private Boolean excused;
    private String reason;

    public AbsenceResponseDto(Absence absence) {
        this.id = absence.getId();
        this.date = absence.getDate();
        this.hour = absence.getHour();
        this.excused = absence.getExcused();
        this.reason = absence.getReason();

        // Populate nested DTOs - placeholders for now
        this.student = new StudentResponseDto(absence.getStudent().getId());
        this.classEntity = new ClassResponseDto(absence.getClassEntity().getId());
        this.subject = new SubjectResponseDto(absence.getSubject().getId());
    }

    @Getter
    @Setter
    public static class StudentResponseDto {
        private Long id;
        private String firstName;
        private String lastName;

        public StudentResponseDto(Long id) { this.id = id; }
    }

    @Getter
    @Setter
    public static class ClassResponseDto {
        private Long id;
        private String name;
        private Long grade;

        public ClassResponseDto(Long id) { this.id = id; }
    }

    @Getter
    @Setter
    public static class SubjectResponseDto {
        private Long id;
        private String name;

        public SubjectResponseDto(Long id) { this.id = id; }
    }
}