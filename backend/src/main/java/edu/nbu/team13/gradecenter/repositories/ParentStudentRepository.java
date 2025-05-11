package edu.nbu.team13.gradecenter.repositories;
import edu.nbu.team13.gradecenter.entities.ParentStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentStudentRepository extends JpaRepository<ParentStudent, Long> { }