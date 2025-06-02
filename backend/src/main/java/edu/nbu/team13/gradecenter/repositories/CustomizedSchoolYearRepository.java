package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.SchoolYear;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomizedSchoolYearRepository {
    Page<SchoolYear> findByFilters(Short year, Byte term,
                                   Long schoolId, Pageable pageable);
}
