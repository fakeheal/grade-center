package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.School;
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

    public Page<School> findByOptionalFilters(String name, String address, Pageable pageable){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        Map<String, String> filters = new java.util.HashMap<>();
        if (name != null) filters.put("name", name);
        if (address != null) filters.put("address", address);

        // --- Main Query ---
        CriteriaQuery<School> cq = cb.createQuery(School.class);
        Root<School> school = cq.from(School.class);

        List<Predicate> predicates = PredicateBuilder.buildLikePredicates(cb, school, filters);
        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<School> query = entityManager.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<School> schools = query.getResultList();

        // --- Count Query ---
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<School> countRoot = countQuery.from(School.class);

        List<Predicate> countPredicates = PredicateBuilder.buildLikePredicates(cb, countRoot, filters);
        countQuery.select(cb.count(countRoot));
        countQuery.where(countPredicates.toArray(new Predicate[0]));

        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(schools, pageable, total);
    }
}
