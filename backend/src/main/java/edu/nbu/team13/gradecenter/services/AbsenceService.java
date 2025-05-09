package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.AbsenceDto;
import edu.nbu.team13.gradecenter.entities.Absence;
import edu.nbu.team13.gradecenter.repositories.AbsenceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AbsenceService {
    private final AbsenceRepository absenceRepository;

    public AbsenceService(AbsenceRepository absenceRepository) {
        this.absenceRepository = absenceRepository;
    }

    public Absence create(AbsenceDto absenceDto) {
        Absence grd = new Absence();
        grd.FromDto(absenceDto);

        // Save the Class entity to the database
        return absenceRepository.save(grd);
    }
    public Absence update(Long id, AbsenceDto absenceDto) {
        // Find the existing Class by ID
        Absence existingAbsence = absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence not found"));

        // Update the Class's properties
        existingAbsence.FromDto(absenceDto);

        // Save the updated Class entity to the database
        return absenceRepository.save(existingAbsence);
    }
    public void delete(Long id) {
        // Find the existing school by ID
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence not found"));

        // Delete the school entity from the database
        absenceRepository.delete(absence);
    }
    public Absence findById(Long id) {
        // Find the existing school by ID
        return absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence not found"));
    }
    public Page<Absence> search(Long studentId, Long teacherId, Long subjectId, LocalDate date,
                                Long schoolYearId, String reason, Boolean Excused, Pageable pageable) {
        return absenceRepository.findByOptionalFilters(studentId, teacherId, subjectId, date,
                schoolYearId, reason, Excused, pageable);
    }
}