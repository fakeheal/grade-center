package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Class;
import edu.nbu.team13.gradecenter.entities.Grade;
import edu.nbu.team13.gradecenter.repositories.utils.PaginationUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CustomizedGradeRepositoryImpl implements CustomizedGradeRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Grade> findByOptionalFilters(Long studentId, Long teacherId, Long subjectId, Long schoolYearId, Double value, LocalDate date, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        Map<String, String> filters = new java.util.HashMap<>();
        if (studentId != null) filters.put("studentId", studentId.toString());
        if (teacherId != null) filters.put("teacherId", teacherId.toString());
        if (subjectId != null) filters.put("subjectId", subjectId.toString());
        if (schoolYearId != null) filters.put("schoolYearId", schoolYearId.toString());
        if (value != null) filters.put("value", value.toString());
        if (date != null) filters.put("date", date.toString());

        // --- Main Query ---
        PaginationUtil<edu.nbu.team13.gradecenter.entities.Grade> pgUtil = new PaginationUtil<>(entityManager, cb, edu.nbu.team13.gradecenter.entities.Grade.class);
        List<Grade> grades = pgUtil.mainQuery(filters,pageable);

        // --- Count Query ---
        Long total = pgUtil.countQuery(filters);

        return new PageImpl<>(grades, pageable, total);
    }
}
