package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Class;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.repositories.utils.PaginationUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public class CustomizedClassRepositoryImpl implements CustomizedClassRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Class>  findByOptionalFilters(String name, Long grade,Long schoolId, Pageable pageable){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        Map<String, String> filters = new java.util.HashMap<>();
        if (name != null) filters.put("name", name);
        if (grade != null) filters.put("grade", grade.toString());
        if (schoolId != null) filters.put("schoolId", schoolId.toString());

        // --- Main Query ---
        PaginationUtil<Class> pgUtil = new PaginationUtil<>(entityManager, cb, Class.class);
        List<Class> users = pgUtil.mainQuery(filters,pageable);

        // --- Count Query ---
        Long total = pgUtil.countQuery(filters);

        return new PageImpl<>(users, pageable, total);
    }
}
