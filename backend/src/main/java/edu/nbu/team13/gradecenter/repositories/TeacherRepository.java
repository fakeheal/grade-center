package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long>, CustomizedTeacherRepository {
    Optional<Teacher> findByUserId(Long id);
}
