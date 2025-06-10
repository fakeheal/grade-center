package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Student;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.repositories.utils.PaginationUtil;
import edu.nbu.team13.gradecenter.repositories.utils.PredicateBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CustomizedParentRepositoryImpl implements CustomizedParentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Page<User> findParentsByFilters(String firstName, String lastName,
                                           String email, Pageable pageable) {

        CriteriaBuilder cb   = em.getCriteriaBuilder();
        PaginationUtil<User> pgUtil = new PaginationUtil<>(em, cb, User.class);

        Map<String,String> filters = new HashMap<>();
        if (firstName != null) filters.put("firstName", firstName);
        if (lastName  != null) filters.put("lastName",  lastName);
        if (email     != null) filters.put("email",     email);

        // --- Add Role Filter ---
        filters.put("role", UserRole.PARENT.name());

        // --- Main Query ---
        List<User> data = pgUtil.mainQuery(filters, pageable);

        // --- Count Query ---
        Long total = pgUtil.countQuery(filters);

        return new PageImpl<>(data, pageable, total);
    }
}

