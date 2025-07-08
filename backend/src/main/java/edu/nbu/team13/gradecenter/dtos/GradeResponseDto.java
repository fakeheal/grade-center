package edu.nbu.team13.gradecenter.dtos;

import edu.nbu.team13.gradecenter.entities.Grade;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GradeResponseDto {
    private Long id;
    private StudentResponseDto student;
    private TeacherResponseDto teacher;
    private SubjectResponseDto subject;
    private Double value;
    private LocalDate date;
    private Long schoolYearId;

    public GradeResponseDto(Grade grade) {
        this.id = grade.getId();
        this.value = grade.getValue();
        this.date = grade.getDate();
        this.schoolYearId = grade.getSchoolYearId();
        // Populate nested DTOs - assuming you have services to fetch these
        // For now, just setting IDs, you'll need to fetch full objects in service/controller
        this.student = new StudentResponseDto(grade.getStudentId()); // Placeholder
        this.teacher = new TeacherResponseDto(grade.getTeacherId()); // Placeholder
        this.subject = new SubjectResponseDto(grade.getSubjectId()); // Placeholder
    }

    // Placeholder DTOs for nested objects
    @Getter
    @Setter
    public static class StudentResponseDto {
        private Long id;
        private String firstName;
        private String lastName;

        public StudentResponseDto(Long id) { this.id = id; }
        // Add constructor/logic to fetch full student details if needed
    }

    @Getter
    @Setter
    public static class TeacherResponseDto {
        private Long id;
        private String firstName;
        private String lastName;

        public TeacherResponseDto(Long id) { this.id = id; }
        // Add constructor/logic to fetch full teacher details if needed
    }

    @Getter
    @Setter
    public static class SubjectResponseDto {
        private Long id;
        private String name;

        public SubjectResponseDto(Long id) { this.id = id; }
        // Add constructor/logic to fetch full subject details if needed
    }
}
