package edu.nbu.team13.gradecenter.exceptions;

public class InvalidSubject extends RuntimeException {
    public InvalidSubject(Long id, String name) {
        super(
            String.format("Subject with id %d and name '%s' is not valid.", id, name)
        );
    }
}
