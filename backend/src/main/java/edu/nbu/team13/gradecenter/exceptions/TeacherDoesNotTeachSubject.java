package edu.nbu.team13.gradecenter.exceptions;

public class TeacherDoesNotTeachSubject extends RuntimeException {
    public TeacherDoesNotTeachSubject(Long id, String subjectName) {
        super("Teacher with ID " + id + " does not teach the subject " + subjectName + ".");
    }
}
