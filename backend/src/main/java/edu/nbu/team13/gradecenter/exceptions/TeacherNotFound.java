package edu.nbu.team13.gradecenter.exceptions;

public class TeacherNotFound extends RuntimeException {
    public TeacherNotFound(Long id) {
        super("Teacher not found with ID: " + id);
    }
}
