package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long>, CustomizedSchoolRepository{
}
