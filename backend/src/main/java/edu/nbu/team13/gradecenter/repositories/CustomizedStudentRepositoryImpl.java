package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.entities.Student;
import edu.nbu.team13.gradecenter.repositories.utils.PaginationUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public class CustomizedStudentRepositoryImpl implements CustomizedStudentRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Student> findByOptionalFilters(Long grade, Long classId, Long userId, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        PaginationUtil<Student> pgUtil = new PaginationUtil<>(entityManager, cb, Student.class);

        Map<String, String> filters = new java.util.HashMap<>();
        if (grade != null) filters.put("grade", grade.toString());
        if (classId != null) filters.put("classId", classId.toString());
        if (userId != null) filters.put("userId", userId.toString());



        // --- Main Query --
        List<Student> students = pgUtil.mainQuery(filters, pageable);

        // --- Count Query ---
        Long total = pgUtil.countQuery(filters);

        return new PageImpl<>(students, pageable, total);
    }
}
