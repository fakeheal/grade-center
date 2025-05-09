package edu.nbu.team13.gradecenter.exceptions;

public class SubjectNotFound extends RuntimeException {
    public SubjectNotFound(Long id) {
        super("Subject not found with ID: " + id);
    }
}
