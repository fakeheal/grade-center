package edu.nbu.team13.gradecenter.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parent_students",
        uniqueConstraints = @UniqueConstraint(columnNames = {"parent_id","student_id"}))
@Getter @Setter
public class ParentStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -- who is the parent -- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private User parent;

    /* -- who is the child (must be role STUDENT) -- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;
}
