package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Class;
import edu.nbu.team13.gradecenter.entities.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomizedClassRepository {
    Page<Class> findByOptionalFilters(String name, Long grade,Long schoolId, Pageable pageable);
}
