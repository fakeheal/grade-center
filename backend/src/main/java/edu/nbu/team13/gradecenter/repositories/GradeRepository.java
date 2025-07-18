package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Grade;
import edu.nbu.team13.gradecenter.entities.School;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long>, CustomizedGradeRepository{
    List<Grade> findByStudentId(Long studentId);

    @Modifying
    @Query("DELETE FROM Grade g WHERE g.studentId = :studentId")
    void deleteByStudentId(Long studentId);
}
