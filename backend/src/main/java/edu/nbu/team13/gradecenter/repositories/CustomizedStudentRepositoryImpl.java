package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.entities.Student;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.repositories.utils.PaginationUtil;
import edu.nbu.team13.gradecenter.repositories.utils.PredicateBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
        Root<Student> studentRoot = cq.from(Student.class);

        List<Predicate> predicates = PredicateBuilder.buildWherePredicates(cb, studentRoot, filters);
        predicates.add(cb.equal(studentRoot.get("user").get("role"), UserRole.STUDENT));
        cq.where(predicates.toArray(new Predicate[0]));

        List<Student> students = entityManager.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        // --- Count Query ---
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Student> countRoot = countQuery.from(Student.class);
        List<Predicate> countPredicates = PredicateBuilder.buildWherePredicates(cb, countRoot, filters);
        countPredicates.add(cb.equal(countRoot.get("user").get("role"), UserRole.STUDENT));
        countQuery.select(cb.count(countRoot));
        countQuery.where(countPredicates.toArray(new Predicate[0]));

        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(students, pageable, total);
    }
}
