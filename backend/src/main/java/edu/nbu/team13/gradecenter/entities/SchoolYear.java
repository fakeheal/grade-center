package edu.nbu.team13.gradecenter.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/**
 * Represents an academic year in a given school
 * e.g. 2023/2024.
 */
@Getter @Setter
@Entity
@Table(
        name = "school_years",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"year", "term", "school_id"}   // every school can have that year only once
        )
)
public class SchoolYear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)           // 2024 means 2024/2025 school year
    private Short year;

    @Column(nullable = false)           // 1 or 2
    private Byte term;

    /** FK to future School entity (for now we store the ID only) */
    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @CreationTimestamp
    @Column(name = "created_on", updatable = false, nullable = false)
    private Instant createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on", nullable = false)
    private Instant updatedOn;
}
