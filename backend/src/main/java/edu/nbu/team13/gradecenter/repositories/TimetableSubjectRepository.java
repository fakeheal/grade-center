package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Timetable;
import edu.nbu.team13.gradecenter.entities.TimetableSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimetableSubjectRepository extends JpaRepository<TimetableSubject, Long> {
    void deleteByTimetable(Timetable timetable);

}
