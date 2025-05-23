package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomizedStudentRepository {
    Page<Student> findByOptionalFilters(Long grade, Long classId, Long userId, Pageable pageable);
}
