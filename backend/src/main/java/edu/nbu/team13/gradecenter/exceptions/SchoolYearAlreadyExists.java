package edu.nbu.team13.gradecenter.exceptions;

public class SchoolYearAlreadyExists extends RuntimeException {
    public SchoolYearAlreadyExists(short year, byte term, Long schoolId) {
        super("School year " + year + " term " + term +
                " already exists for school " + schoolId);
    }
}
