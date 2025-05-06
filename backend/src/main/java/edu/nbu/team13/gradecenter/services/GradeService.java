package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.ClassDto;
import edu.nbu.team13.gradecenter.dtos.GradeDto;
import edu.nbu.team13.gradecenter.entities.Class;
import edu.nbu.team13.gradecenter.entities.Grade;
import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.repositories.ClassRepository;
import edu.nbu.team13.gradecenter.repositories.GradeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GradeService {
    private final GradeRepository gradeRepository;

    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public Grade create(GradeDto gradeDto) {
        Grade grd = new Grade();
        grd.setDate(gradeDto.getDate());
        grd.setStudentId(gradeDto.getStudentId());
        grd.setSchoolYearId(gradeDto.getSchoolYearId());
        grd.setSubjectId(gradeDto.getSubjectId());
        grd.setTeacherId(gradeDto.getTeacherId());
        grd.setValue(gradeDto.getValue());

        // Save the Class entity to the database
        return gradeRepository.save(grd);
    }
    public Grade update(Long id, GradeDto gradeDto) {
        // Find the existing Class by ID
        Grade existingGrade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));

        // Update the Class's properties
        existingGrade.setDate(gradeDto.getDate());
        existingGrade.setStudentId(gradeDto.getStudentId());
        existingGrade.setSchoolYearId(gradeDto.getSchoolYearId());
        existingGrade.setSubjectId(gradeDto.getSubjectId());
        existingGrade.setTeacherId(gradeDto.getTeacherId());
        existingGrade.setValue(gradeDto.getValue());

        // Save the updated Class entity to the database
        return gradeRepository.save(existingGrade);
    }
    public void delete(Long id) {
        // Find the existing school by ID
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));

        // Delete the school entity from the database
        gradeRepository.delete(grade);
    }
    public Grade findById(Long id) {
        // Find the existing school by ID
        return gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));
    }
    public Page<Grade> search(Long studentId, Long teacherId, Long subjectId, Long schoolYearId, Double value, LocalDate date, Pageable pageable) {
        return gradeRepository.findByOptionalFilters(studentId, teacherId, subjectId, schoolYearId, value, date, pageable);
    }
}
