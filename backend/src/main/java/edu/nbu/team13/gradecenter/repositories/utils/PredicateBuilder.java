package edu.nbu.team13.gradecenter.repositories.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PredicateBuilder {

    /**
     * Builds LIKE predicates for each non-null string field.
     *
     * @param cb      CriteriaBuilder
     * @param root    Root of the entity
     * @param filters Map of fieldName → value
     * @return list of Predicates
     */
    public static <T> List<Predicate> buildLikePredicates(
            CriteriaBuilder cb,
            Root<T> root,
            Map<String, String> filters
    ) {
        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String field = entry.getKey();
            String value = entry.getValue();

            if (value != null && !value.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%"));
            }
        }

        return predicates;
    }
}