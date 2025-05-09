package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.entities.Grade;
import edu.nbu.team13.gradecenter.entities.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface CustomizedGradeRepository {
    Page<Grade> findByOptionalFilters(Long studentId, Long teacherId,Long subjectId, Long schoolYearId, Double value, LocalDate date, Pageable pageable);
}
