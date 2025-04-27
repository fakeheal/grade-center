package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.repositories.utils.PaginationUtil;
import edu.nbu.team13.gradecenter.repositories.utils.PredicateBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public class CustomizedSchoolRepositoryImpl implements  CustomizedSchoolRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<School> findByOptionalFilters(String name, String address, Pageable pageable){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        PaginationUtil<School> pgUtil = new PaginationUtil<>(entityManager, cb, School.class);

        Map<String, String> filters = new java.util.HashMap<>();
        if (name != null) filters.put("name", name);
        if (address != null) filters.put("address", address);

        // --- Main Query --
        List<School> schools = pgUtil.mainQuery(filters, pageable);

        // --- Count Query ---
        Long total = pgUtil.countQuery(filters);

        return new PageImpl<>(schools, pageable, total);
    }
}
