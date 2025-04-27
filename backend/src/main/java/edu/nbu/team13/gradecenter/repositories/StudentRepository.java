package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long>, CustomizedStudentRepository{
}
