package edu.nbu.team13.gradecenter.exceptions;

public class InvalidUserRole extends RuntimeException {
    public InvalidUserRole(Long id, String role) {
        super(
                String.format("User with ID %d does not have the role %s", id, role)
        );
    }
}
