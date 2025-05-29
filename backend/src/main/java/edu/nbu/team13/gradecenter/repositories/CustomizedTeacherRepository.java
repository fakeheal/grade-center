package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomizedTeacherRepository {
    Page<Teacher> findByOptionalFilters(String firstName, String lastName, String email, Long schoolId, Long userId, Pageable pageable);
}
