package edu.nbu.team13.gradecenter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DirectorAlreadyExists extends RuntimeException {
    public DirectorAlreadyExists(Long schoolId) {
        super("A director for school " + schoolId + " already exists");
    }
}
