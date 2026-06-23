package com.swifttech.sr.ps.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

public final class SpecificationHelper {

    private SpecificationHelper() {
    }

    public static String likePattern(String searchText) {
        return "%" + searchText.toLowerCase() + "%";
    }

    public static Predicate likeIgnoreCase(CriteriaBuilder cb, Expression<String> path, String searchText) {
        return cb.like(cb.lower(path), likePattern(searchText));
    }
}
