package edu.nbu.team13.gradecenter.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "students")
public class Student {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Getter
    @Setter
    @Column(name = "grade", nullable = false)
    private Long grade;

    @Getter
    @Setter
    @Column(name = "class_id", nullable = false)
    private Long classId;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}