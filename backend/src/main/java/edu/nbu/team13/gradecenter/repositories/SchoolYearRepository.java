package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.SchoolYear;
import edu.nbu.team13.gradecenter.repositories.CustomizedSchoolYearRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolYearRepository extends JpaRepository<SchoolYear, Long>,
        CustomizedSchoolYearRepository {

    boolean existsByYearAndTermAndSchoolId(short year, byte term, Long schoolId);
}
