package edu.nbu.team13.gradecenter.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "grades")
public class Grade {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Getter
    @Setter
    @Column(name = "teacher_id", nullable = false)
    private Long teacherId;

    @Getter
    @Setter
    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Getter
    @Setter
    @Column(name = "`value`", nullable = false)
    private Double value;

    @Getter
    @Setter
    @Column(name = "date", nullable = false)
    private java.time.LocalDate date;

    @Getter
    @Setter
    @Column(name = "school_year_id", nullable = false)
    private Long schoolYearId;

    public Grade() {}
    public void FromDto(edu.nbu.team13.gradecenter.dtos.GradeDto dto){
        this.date = dto.getDate();
        this.studentId = dto.getStudentId();
        this.teacherId = dto.getTeacherId();
        this.subjectId = dto.getSubjectId();
        this.schoolYearId = dto.getSchoolYearId();
        this.value = dto.getValue();
    }
}