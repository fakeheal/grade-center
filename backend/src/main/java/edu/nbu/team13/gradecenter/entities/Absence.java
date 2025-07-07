package edu.nbu.team13.gradecenter.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "absences")
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Class classEntity;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "hour", nullable = false)
    private Integer hour;

    @Column(name = "excused", nullable = false)
    private Boolean excused;

    @Column(name = "reason")
    private String reason;
}
