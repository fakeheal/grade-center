package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Class;
import edu.nbu.team13.gradecenter.entities.SchoolYear;
import edu.nbu.team13.gradecenter.entities.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {

    Timetable findBySchoolYearAndClazz(SchoolYear schoolYear, Class clazz);

}
