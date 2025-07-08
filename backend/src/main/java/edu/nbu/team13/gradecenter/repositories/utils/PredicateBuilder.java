package edu.nbu.team13.gradecenter.repositories.utils;

import jakarta.persistence.criteria.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds dynamic WHERE predicates based on a map of filters.
 * - String fields → case-insensitive LIKE
 * - Numeric/primitive types → exact match
 * - Enums, booleans, dates → exact match after parsing
 */
public class PredicateBuilder {

    public static <T> List<Predicate> buildWherePredicates(
            CriteriaBuilder cb,
            Root<T> root,
            Map<String, String> filters
    ) {
        List<Predicate> predicates = new ArrayList<>();
        Map<String, Join<?, ?>> joinsCache = new HashMap<>();

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String fieldPath = entry.getKey();
            String rawValue = entry.getValue();
            if (rawValue == null || rawValue.isEmpty()) {
                continue;
            }

            Path<?> path = resolvePath(root, joinsCache, fieldPath);
            Class<?> javaType = path.getJavaType();

            if (String.class.equals(javaType)) {
                // case-insensitive LIKE on strings
                predicates.add(cb.like(
                        cb.lower(path.as(String.class)),
                        "%" + rawValue.toLowerCase() + "%"
                ));
            } else if (Number.class.isAssignableFrom(javaType)
                    || (javaType.isPrimitive() && !boolean.class.equals(javaType))) {
                // exact match on numbers
                Number num = parseNumber(javaType, rawValue);
                predicates.add(cb.equal(path, num));
            } else {
                // enums, booleans, dates, etc.
                Object typed = convertTo(javaType, rawValue);
                predicates.add(cb.equal(path, typed));
            }
        }

        return predicates;
    }

    /**
     * Parses a numeric string into the given Number subtype.
     */
    private static Number parseNumber(Class<?> javaType, String value) {
        if (Long.class.equals(javaType) || long.class.equals(javaType)) {
            return Long.valueOf(value);
        } else if (Integer.class.equals(javaType) || int.class.equals(javaType)) {
            return Integer.valueOf(value);
        } else if (Short.class.equals(javaType) || short.class.equals(javaType)) {
            return Short.valueOf(value);
        } else if (Byte.class.equals(javaType) || byte.class.equals(javaType)) {
            return Byte.valueOf(value);
        } else if (BigDecimal.class.equals(javaType)) {
            return new BigDecimal(value);
        }
        throw new IllegalArgumentException("Unsupported numeric type: " + javaType.getName());
    }

    /**
     * Converts a string into an instance of the given type.
     * Supports enums, booleans, and ISO dates (LocalDate).
     */
    private static Object convertTo(Class<?> javaType, String value) {
        if (Enum.class.isAssignableFrom(javaType)) {
            @SuppressWarnings("unchecked")
            Class<Enum> enumType = (Class<Enum>) javaType;
            return Enum.valueOf(enumType, value);
        }
        if (Boolean.class.equals(javaType) || boolean.class.equals(javaType)) {
            return Boolean.valueOf(value);
        }
        if (LocalDate.class.equals(javaType)) {
            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException("Invalid date format for " + javaType + ": " + value, ex);
            }
        }
        // add more converters as needed (UUID, Instant, etc.)
        throw new IllegalArgumentException("Unsupported type: " + javaType.getName());
    }

    /**
     * Resolves a (possibly nested) field path like "department.name" into a Criteria Path.
     * Caches joins to avoid duplicate JOINs.
     */
    private static Path<?> resolvePath(
            Root<?> root,
            Map<String, Join<?, ?>> joinsCache,
            String fieldPath
    ) {
        String[] parts = fieldPath.split("\\.");
        Path<?> path = root;

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (i < parts.length - 1) {
                // join intermediate attribute
                String joinKey = String.join(".", java.util.Arrays.copyOfRange(parts, 0, i + 1));
                Join<?, ?> join = joinsCache.get(joinKey);
                if (join == null) {
                    if (path instanceof Root<?>) {
                        join = ((Root<?>) path).join(part, JoinType.LEFT);
                    } else if (path instanceof Join<?, ?>) {
                        join = ((Join<?, ?>) path).join(part, JoinType.LEFT);
                    } else {
                        throw new IllegalStateException("Cannot join on path part: " + part);
                    }
                    joinsCache.put(joinKey, join);
                }
                path = join;
            } else {
                // last part: attribute itself
                path = path.get(part);
            }
        }

        return path;
    }
}
