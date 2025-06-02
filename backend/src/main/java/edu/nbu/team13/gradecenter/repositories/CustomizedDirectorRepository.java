package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.User;
import org.springframework.data.domain.*;

public interface CustomizedDirectorRepository {
    Page<User> findDirectorsByFilters(String firstName,
                                      String lastName,
                                      String email,
                                      Pageable pageable);
}
