package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Grade;
import edu.nbu.team13.gradecenter.entities.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long>, CustomizedGradeRepository{
}
