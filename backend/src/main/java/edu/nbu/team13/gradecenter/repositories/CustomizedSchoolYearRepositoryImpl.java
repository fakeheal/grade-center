package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.SchoolYear;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomizedSchoolYearRepositoryImpl
        implements CustomizedSchoolYearRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Page<SchoolYear> findByFilters(Short year, Byte term,
                                          Long schoolId, Pageable pg) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SchoolYear> cq = cb.createQuery(SchoolYear.class);
        Root<SchoolYear> root = cq.from(SchoolYear.class);

        List<Predicate> predicates = new ArrayList<>();
        if (year     != null) predicates.add(cb.equal(root.get("year"), year));
        if (term     != null) predicates.add(cb.equal(root.get("term"), term));
        if (schoolId != null) predicates.add(cb.equal(root.get("schoolId"), schoolId));

        cq.where(predicates.toArray(new Predicate[0]));

        List<SchoolYear> data = em.createQuery(cq)
                .setFirstResult((int) pg.getOffset())
                .setMaxResults(pg.getPageSize())
                .getResultList();

        CriteriaQuery<Long> cnt = cb.createQuery(Long.class);
        Root<SchoolYear> cRoot = cnt.from(SchoolYear.class);
        cnt.select(cb.count(cRoot)).where(predicates.toArray(new Predicate[0]));
        long total = em.createQuery(cnt).getSingleResult();

        return new PageImpl<>(data, pg, total);
    }
}
