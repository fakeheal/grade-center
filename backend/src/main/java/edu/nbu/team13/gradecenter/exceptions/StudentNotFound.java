package edu.nbu.team13.gradecenter.exceptions;

public class StudentNotFound extends RuntimeException {
    public StudentNotFound(Long id) {
        super("Student not found: " + id);
    }
}
