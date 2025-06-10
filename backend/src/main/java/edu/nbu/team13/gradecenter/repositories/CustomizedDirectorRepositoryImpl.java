package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.repositories.utils.PaginationUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
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
public class CustomizedDirectorRepositoryImpl implements CustomizedDirectorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Page<User> findDirectorsByFilters(String firstName,
                                             String lastName,
                                             String email,
                                             Pageable pageable) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        PaginationUtil<User> pgUtil = new PaginationUtil<>(em, cb, User.class);

        Map<String, String> filters = new HashMap<>();
        if (firstName != null) filters.put("firstName", firstName);
        if (lastName != null) filters.put("lastName", lastName);
        if (email != null) filters.put("email", email);

        filters.put("role", UserRole.DIRECTOR.name());


        // ---- Main query ----
        List<User> directors = pgUtil.mainQuery(filters, pageable);

        // --- Count Query ---
        Long total = pgUtil.countQuery(filters);

        return new PageImpl<>(directors, pageable, total);
    }
}
