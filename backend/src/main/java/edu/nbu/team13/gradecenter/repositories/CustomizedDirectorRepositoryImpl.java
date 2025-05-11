package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.repositories.utils.PredicateBuilder;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.*;

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
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root     = cq.from(User.class);

        Map<String, String> filters = new HashMap<>();
        if (firstName != null) filters.put("firstName", firstName);
        if (lastName  != null) filters.put("lastName",  lastName);
        if (email     != null) filters.put("email",     email);

        List<Predicate> predicates =
                PredicateBuilder.buildLikePredicates(cb, root, filters);


        predicates.add(cb.equal(root.get("role"), UserRole.DIRECTOR));

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<User> query = em.createQuery(cq)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        List<User> directors = query.getResultList();

        // ---- Count query ----
        CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
        Root<User> countRoot = countCq.from(User.class);
        countCq.select(cb.count(countRoot))
                .where(predicates.toArray(new Predicate[0]));

        Long total = em.createQuery(countCq).getSingleResult();

        return new PageImpl<>(directors, pageable, total);
    }
}
