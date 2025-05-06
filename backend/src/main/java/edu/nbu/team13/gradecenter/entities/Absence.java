package edu.nbu.team13.gradecenter.entities;

import edu.nbu.team13.gradecenter.dtos.AbsenceDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "absences")
public class Absence {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

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
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Getter
    @Setter
    @Column(name = "school_year_id", nullable = false)
    private Long schoolYearId;

    @Getter
    @Setter
    @Column(name = "reason", nullable = false)
    private String reason;

    @Getter
    @Setter
    @Column(name = "excused", nullable = false)
    private Boolean excused;

    public Absence() {}
    public void FromDto(AbsenceDto dto){
        this.date = dto.getDate();
        this.studentId = dto.getStudentId();
        this.teacherId = dto.getTeacherId();
        this.subjectId = dto.getSubjectId();
        this.schoolYearId = dto.getSchoolYearId();
        this.reason = dto.getReason();
        this.excused = dto.getExcused();

    }
}
