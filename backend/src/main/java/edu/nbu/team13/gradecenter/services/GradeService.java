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

    public List<GradeResponseDto> findAll() {
        return gradeRepository.findAll().stream()
                .map(grade -> {
                    GradeResponseDto dto = new GradeResponseDto(grade);
                    // Populate full student, teacher, subject details
                    studentRepository.findById(grade.getStudentId()).ifPresent(s -> {
                        dto.getStudent().setFirstName(s.getUser().getFirstName());
                        dto.getStudent().setLastName(s.getUser().getLastName());
                    });
                    teacherRepository.findById(grade.getTeacherId()).ifPresent(t -> {
                        dto.getTeacher().setFirstName(t.getUser().getFirstName());
                        dto.getTeacher().setLastName(t.getUser().getLastName());
                    });
                    subjectRepository.findById(grade.getSubjectId()).ifPresent(sub -> {
                        dto.getSubject().setName(sub.getName());
                    });
                    return dto;
                })
                .collect(Collectors.toList());
    }
}