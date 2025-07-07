package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Class;
import edu.nbu.team13.gradecenter.entities.Subject;
import edu.nbu.team13.gradecenter.entities.Timetable;
import edu.nbu.team13.gradecenter.entities.TimetableSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimetableSubjectRepository extends JpaRepository<TimetableSubject, Long> {
    void deleteByTimetable(Timetable timetable);

    Optional<TimetableSubject> findBySubjectAndDayOfWeekAndStartTimeAndTimetable_Clazz(Subject subject, int dayOfWeek, int startTime, Class clazz);
}