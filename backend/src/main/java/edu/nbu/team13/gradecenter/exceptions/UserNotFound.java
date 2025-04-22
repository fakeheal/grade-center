package edu.nbu.team13.gradecenter.exceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound(Long id) {
        super("User not found with ID: " + id);
    }
}
