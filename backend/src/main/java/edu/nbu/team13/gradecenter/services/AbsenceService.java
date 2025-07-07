package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.AbsenceDto;
import edu.nbu.team13.gradecenter.dtos.AbsenceResponseDto;
import edu.nbu.team13.gradecenter.entities.Absence;
import edu.nbu.team13.gradecenter.entities.Class;
import edu.nbu.team13.gradecenter.entities.Subject;
import edu.nbu.team13.gradecenter.repositories.AbsenceRepository;
import edu.nbu.team13.gradecenter.repositories.ClassRepository;
import edu.nbu.team13.gradecenter.repositories.StudentRepository;
import edu.nbu.team13.gradecenter.repositories.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbsenceService {
    private final AbsenceRepository absenceRepository;
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;

    public AbsenceService(AbsenceRepository absenceRepository, StudentRepository studentRepository, ClassRepository classRepository, SubjectRepository subjectRepository) {
        this.absenceRepository = absenceRepository;
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
        this.subjectRepository = subjectRepository;
    }

    @Transactional
    public Absence create(AbsenceDto absenceDto) {
        Absence absence = new Absence();
        absence.setStudent(studentRepository.findById(absenceDto.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found")));
        absence.setClassEntity(classRepository.findById(absenceDto.getClassId()).orElseThrow(() -> new RuntimeException("Class not found")));
        absence.setSubject(subjectRepository.findById(absenceDto.getSubjectId()).orElseThrow(() -> new RuntimeException("Subject not found")));
        absence.setDate(absenceDto.getDate());
        absence.setHour(absenceDto.getHour());
        absence.setExcused(absenceDto.getExcused());
        absence.setReason(absenceDto.getReason());
        return absenceRepository.save(absence);
    }

    @Transactional
    public Absence update(Long id, AbsenceDto absenceDto) {
        Absence existingAbsence = absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence not found"));

        existingAbsence.setStudent(studentRepository.findById(absenceDto.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found")));
        existingAbsence.setClassEntity(classRepository.findById(absenceDto.getClassId()).orElseThrow(() -> new RuntimeException("Class not found")));
        existingAbsence.setSubject(subjectRepository.findById(absenceDto.getSubjectId()).orElseThrow(() -> new RuntimeException("Subject not found")));
        existingAbsence.setDate(absenceDto.getDate());
        existingAbsence.setHour(absenceDto.getHour());
        existingAbsence.setExcused(absenceDto.getExcused());
        existingAbsence.setReason(absenceDto.getReason());

        return absenceRepository.save(existingAbsence);
    }

    @Transactional
    public void delete(Long id) {
        Absence absence = absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence not found"));
        absenceRepository.delete(absence);
    }

    public List<AbsenceResponseDto> findAll() {
        return absenceRepository.findAll().stream()
                .map(absence -> {
                    AbsenceResponseDto dto = new AbsenceResponseDto(absence);
                    dto.getStudent().setFirstName(absence.getStudent().getUser().getFirstName());
                    dto.getStudent().setLastName(absence.getStudent().getUser().getLastName());
                    dto.getClassEntity().setName(absence.getClassEntity().getName());
                    dto.getClassEntity().setGrade(absence.getClassEntity().getGrade());
                    dto.getSubject().setName(absence.getSubject().getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Absence findById(Long id) {
        return absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence not found"));
    }
}
