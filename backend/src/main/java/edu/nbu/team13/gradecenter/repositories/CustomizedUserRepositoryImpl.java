package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.User;
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
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);

        List<Predicate> predicates = PredicateBuilder.buildLikePredicates(cb, user, filters);
        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<User> query = entityManager.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<User> users = query.getResultList();

        // --- Count Query ---
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);

        List<Predicate> countPredicates = PredicateBuilder.buildLikePredicates(cb, countRoot, filters);
        countQuery.select(cb.count(countRoot));
        countQuery.where(countPredicates.toArray(new Predicate[0]));

        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(users, pageable, total);
    }
}
