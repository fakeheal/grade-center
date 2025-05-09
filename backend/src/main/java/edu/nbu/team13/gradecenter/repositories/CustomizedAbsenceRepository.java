package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Absence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface CustomizedAbsenceRepository {

    Page<Absence> findByOptionalFilters(Long studentId, Long teacherId, Long subjectId, LocalDate date,
                                        Long schoolYearId, String reason, Boolean Excused, Pageable pageable);


}
