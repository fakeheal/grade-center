package edu.nbu.team13.gradecenter.exceptions;

public class EmailNotAvailable extends RuntimeException {
    public EmailNotAvailable(String email) {
        super("Email is already in use: " + email);
    }
}
