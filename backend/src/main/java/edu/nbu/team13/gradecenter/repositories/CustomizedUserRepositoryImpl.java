package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.User;
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

public class CustomizedUserRepositoryImpl implements CustomizedUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<User> findByOptionalFilters(String firstName, String lastName, String email, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        Map<String, String> filters = new java.util.HashMap<>();
        if (firstName != null) filters.put("firstName", firstName);
        if (lastName != null) filters.put("lastName", lastName);
        if (email != null) filters.put("email", email);

        // --- Main Query ---
        PaginationUtil<User> pgUtil = new PaginationUtil<>(entityManager, cb, User.class);
        List<User> users = pgUtil.mainQuery(filters,pageable);

        // --- Count Query ---
        Long total = pgUtil.countQuery(filters);

        return new PageImpl<>(users, pageable, total);
    }
}
