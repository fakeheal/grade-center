package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomizedUserRepository {
    Page<User> findByOptionalFilters(String firstName, String lastName, String email, Pageable pageable);
}
