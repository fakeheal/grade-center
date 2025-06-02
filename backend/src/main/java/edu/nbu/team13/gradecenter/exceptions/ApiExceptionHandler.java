package edu.nbu.team13.gradecenter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(EmailNotAvailable.class)
    public ResponseEntity<?> handleEmailExists(EmailNotAvailable ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "error", "email_already_exists",
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<?> handleNotFound(UserNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "error", "resource_not_found",
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(DirectorAlreadyExists.class)
    public ResponseEntity<?> handleDirectorExists(DirectorAlreadyExists ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "error", "director_already_exists",
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(StudentNotFound.class)
    public ResponseEntity<?> handleStudentNotFound(StudentNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "error", "student_not_found",
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(SchoolYearAlreadyExists.class)
    public ResponseEntity<?> handleYearExists(SchoolYearAlreadyExists ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "error", "school_year_exists",
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(SchoolYearNotFound.class)
    public ResponseEntity<?> handleYearNF(SchoolYearNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "error", "school_year_not_found",
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(InvalidInput.class)
    public ResponseEntity<?> handleBad(InvalidInput ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "error", "invalid_input",
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "internal_error",
                "message", ex.getMessage()
        ));
    }
}
