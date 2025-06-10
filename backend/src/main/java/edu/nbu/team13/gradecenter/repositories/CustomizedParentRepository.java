package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.User;
import org.springframework.data.domain.*;

public interface CustomizedParentRepository {
    Page<User> findParentsByFilters(String firstName,
                                    String lastName,
                                    String email,
                                    Pageable pageable);
}
