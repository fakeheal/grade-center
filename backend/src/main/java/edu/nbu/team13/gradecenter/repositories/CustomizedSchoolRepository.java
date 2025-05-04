package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomizedSchoolRepository {
    Page<School> findByOptionalFilters(String name, String address, Pageable pageable);
}
