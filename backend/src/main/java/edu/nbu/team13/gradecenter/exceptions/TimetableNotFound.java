package edu.nbu.team13.gradecenter.exceptions;

public class TimetableNotFound extends RuntimeException {
    public TimetableNotFound(Long id) {
        super("Class not found with ID: " + id);
    }
}
