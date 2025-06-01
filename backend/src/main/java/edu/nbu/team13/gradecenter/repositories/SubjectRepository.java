package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
    List<Subject> findAllBySchoolId(Long schoolId);
}
