package edu.nbu.team13.gradecenter.repositories.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.constant.Constable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PredicateBuilder {

    private static Map<String, Constable> nonStringFields = Map.of(
            "schoolId", Long.class,
            "year", Short.class,
            "term", Byte.class
    );

    /**
     * Builds LIKE predicates for each non-null string field.
     *
     * @param cb      CriteriaBuilder
     * @param root    Root of the entity
     * @param filters Map of fieldName → value
     * @return list of Predicates
     */
    public static <T> List<Predicate> buildWherePredicates(
            CriteriaBuilder cb,
            Root<T> root,
            Map<String, String> filters
    ) {
        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String field = entry.getKey();
            String value = entry.getValue();

            if (value != null && !value.isEmpty()) {
                // Check if the field is a non-string field
                if (nonStringFields.containsKey(field)) {
                    Constable fieldType = nonStringFields.get(field);
                    if (fieldType == Long.class) {
                        predicates.add(cb.equal(root.get(field), Long.valueOf(value)));
                    } else if (fieldType == Short.class) {
                        predicates.add(cb.equal(root.get(field), Short.valueOf(value)));
                    } else if (fieldType == Byte.class) {
                        predicates.add(cb.equal(root.get(field), Byte.valueOf(value)));
                    }
                } else {
                    predicates.add(cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%"));
                }
            }
        }

        return predicates;
    }
}