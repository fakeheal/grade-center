package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Teacher;
import edu.nbu.team13.gradecenter.repositories.utils.PaginationUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public class CustomizedTeacherRepositoryImpl implements CustomizedTeacherRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Teacher> findByOptionalFilters(String firstName, String lastName, String email, Long schoolId, Long userId, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        PaginationUtil<Teacher> pgUtil = new PaginationUtil<>(entityManager, cb, Teacher.class);

        Map<String, String> filters = new java.util.HashMap<>();
        //@TODO: Search by firstName, lastName, email, schoolId inside USER
//        if (schoolId != null) filters.put("schoolId", schoolId.toString());
        if (userId != null) filters.put("userId", userId.toString());

        // --- Main Query --
        List<Teacher> teachers = pgUtil.mainQuery(filters, pageable);

        // --- Count Query ---
        Long total = pgUtil.countQuery(filters);

        return new PageImpl<>(teachers, pageable, total);
    }
}
