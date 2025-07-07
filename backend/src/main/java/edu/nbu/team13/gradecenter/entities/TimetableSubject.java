package edu.nbu.team13.gradecenter.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Data
@Table(name = "timetable_subjects")
public class TimetableSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @ManyToOne
    @JsonIgnore
    private Timetable timetable;

    @Setter
    @Getter
    @ManyToOne
    private Subject subject;

    @Setter
    @Getter
    @ManyToOne
    private Teacher teacher;

    @Setter
    @Getter
    @Column(name = "day")
    private byte dayOfWeek;


    @Setter
    @Getter
    @Column(name = "start_time")
    private int startTime;
}
