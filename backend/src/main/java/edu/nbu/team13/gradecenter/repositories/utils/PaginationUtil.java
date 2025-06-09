package edu.nbu.team13.gradecenter.repositories.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public class PaginationUtil<T> {
    private final EntityManager entityManager;
    private final CriteriaBuilder cb;
    private final Class<T> entityClass;

    public PaginationUtil(EntityManager entityManager, CriteriaBuilder cb, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.cb = cb;
        this.entityClass = entityClass;
    }

    public List<T> mainQuery(Map<String, String> filters, Pageable pageable) {
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> school = cq.from(entityClass);

        List<Predicate> predicates = PredicateBuilder.buildWherePredicates(cb, school, filters);
        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<T> query = entityManager.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }

    public Long countQuery( Map<String, String> filters) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(entityClass);

        List<Predicate> countPredicates = PredicateBuilder.buildWherePredicates(cb, countRoot, filters);
        countQuery.select(cb.count(countRoot));
        countQuery.where(countPredicates.toArray(new Predicate[0]));

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
