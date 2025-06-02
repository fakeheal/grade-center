package edu.nbu.team13.gradecenter.exceptions;


public class SchoolYearNotFound extends RuntimeException {
    public SchoolYearNotFound(Long id) {
        super("School year not found: " + id);
    }
}
