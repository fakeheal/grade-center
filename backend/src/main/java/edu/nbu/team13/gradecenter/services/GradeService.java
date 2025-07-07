package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.GradeDto;
import edu.nbu.team13.gradecenter.dtos.GradeResponseDto;
import edu.nbu.team13.gradecenter.entities.Grade;
import edu.nbu.team13.gradecenter.entities.Student;
import edu.nbu.team13.gradecenter.entities.Subject;
import edu.nbu.team13.gradecenter.entities.Teacher;
import edu.nbu.team13.gradecenter.repositories.GradeRepository;
import edu.nbu.team13.gradecenter.repositories.StudentRepository;
import edu.nbu.team13.gradecenter.repositories.SubjectRepository;
import edu.nbu.team13.gradecenter.repositories.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeService {
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository, TeacherRepository teacherRepository, SubjectRepository subjectRepository) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    public Grade findById(Long id) {
        return gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));
    }

    @Transactional
    public Grade create(GradeDto gradeDto) {
        Grade grade = new Grade();
        grade.setStudentId(gradeDto.getStudentId());
        grade.setTeacherId(gradeDto.getTeacherId());
        grade.setSubjectId(gradeDto.getSubjectId());
        grade.setValue(gradeDto.getValue());
        grade.setDate(gradeDto.getDate());
        grade.setSchoolYearId(gradeDto.getSchoolYearId());
        return gradeRepository.save(grade);
    }

    @Transactional
    public Grade update(Long id, GradeDto gradeDto) {
        Grade existingGrade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));

        existingGrade.setStudentId(gradeDto.getStudentId());
        existingGrade.setTeacherId(gradeDto.getTeacherId());
        existingGrade.setSubjectId(gradeDto.getSubjectId());
        existingGrade.setValue(gradeDto.getValue());
        existingGrade.setDate(gradeDto.getDate());
        existingGrade.setSchoolYearId(gradeDto.getSchoolYearId());

        return gradeRepository.save(existingGrade);
    }

    @Transactional
    public void delete(Long id) {
        Grade existingGrade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));
        gradeRepository.delete(existingGrade);
    }

    public List<GradeResponseDto> findAll() {
        return gradeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<GradeResponseDto> findByStudentId(Long studentId) {
        return gradeRepository.findByStudentId(studentId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private GradeResponseDto convertToDto(Grade grade) {
        GradeResponseDto dto = new GradeResponseDto(grade);

        studentRepository.findById(grade.getStudentId()).ifPresent(s -> {
            GradeResponseDto.StudentResponseDto studentDto = new GradeResponseDto.StudentResponseDto(s.getId());
            studentDto.setFirstName(s.getUser().getFirstName());
            studentDto.setLastName(s.getUser().getLastName());
            dto.setStudent(studentDto);
        });

        teacherRepository.findById(grade.getTeacherId()).ifPresent(t -> {
            GradeResponseDto.TeacherResponseDto teacherDto = new GradeResponseDto.TeacherResponseDto(t.getId());
            teacherDto.setFirstName(t.getUser().getFirstName());
            teacherDto.setLastName(t.getUser().getLastName());
            dto.setTeacher(teacherDto);
        });

        subjectRepository.findById(grade.getSubjectId()).ifPresent(sub -> {
            GradeResponseDto.SubjectResponseDto subjectDto = new GradeResponseDto.SubjectResponseDto(sub.getId());
            subjectDto.setName(sub.getName());
            dto.setSubject(subjectDto);
        });
        return dto;
    }
}