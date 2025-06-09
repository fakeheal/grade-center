package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.SchoolYear;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.repositories.utils.PaginationUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        PaginationUtil<SchoolYear> pgUtil = new PaginationUtil<>(em, cb, SchoolYear.class);

        Map<String, String> filters = new HashMap<>();
        if (year     != null) filters.put("year", year.toString());
        if (term     != null) filters.put("term", term.toString());
        if (schoolId != null) filters.put("schoolId", schoolId.toString());

        List<SchoolYear> schoolYears = pgUtil.mainQuery(filters, pg);
        Long total = pgUtil.countQuery(filters);

        return new PageImpl<>(schoolYears, pg, total);
    }
}
