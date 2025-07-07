package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.TimetableDto;
import edu.nbu.team13.gradecenter.dtos.TimetableSubjectDto;
import edu.nbu.team13.gradecenter.entities.Class;
import edu.nbu.team13.gradecenter.entities.*;
import edu.nbu.team13.gradecenter.exceptions.InvalidDayOfWeek;
import edu.nbu.team13.gradecenter.exceptions.TeacherDoesNotTeachSubject;
import edu.nbu.team13.gradecenter.repositories.TimetableRepository;
import edu.nbu.team13.gradecenter.repositories.TimetableSubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimetableService {
    private final TimetableRepository timetableRepository;
    private final TimetableSubjectRepository timetableSubjectRepository;

    private final SchoolYearService schoolYearService;
    private final ClassService classService;

    private final SubjectService subjectService;
    private final TeacherService teacherService;

    @Transactional
    public Timetable create(TimetableDto timetableDto) {
        SchoolYear schoolYear = schoolYearService.findById(timetableDto.getSchoolYearId());
        Class clazz = classService.findById(timetableDto.getClassId());

        Timetable timetable = timetableRepository.findBySchoolYearAndClazz(schoolYear, clazz);

        if (timetable == null) {
            timetable = new Timetable();
            timetable.setSchoolYear(schoolYear);
            timetable.setClazz(clazz);
            timetable = timetableRepository.save(timetable);
        } else {
            timetableSubjectRepository.deleteByTimetable(timetable);
        }

        for (TimetableSubjectDto subjectDto : timetableDto.getSubjects()) {
            Subject subject = subjectService.findById(subjectDto.getSubjectId());
            Teacher teacher = teacherService.findById(subjectDto.getTeacherId());

            if (subjectDto.getDayOfWeek() < 1 || subjectDto.getDayOfWeek() > 7) {
                throw new InvalidDayOfWeek(subjectDto.getDayOfWeek());
            }

            if (!teacher.getSubjects().contains(subject)) {
                throw new TeacherDoesNotTeachSubject(teacher.getId(), subject.getName());
            }

            TimetableSubject timetableSubject = new TimetableSubject();
            timetableSubject.setSubject(subject);
            timetableSubject.setTeacher(teacher);
            timetableSubject.setDayOfWeek(subjectDto.getDayOfWeek());
            timetableSubject.setStartTime(subjectDto.getStartTime());
            timetableSubject.setTimetable(timetable);
            TimetableSubject newTimetableSubject = timetableSubjectRepository.save(timetableSubject);
            timetable.getSubjects().add(newTimetableSubject);
        }

        return timetable;
    }

    public Timetable findBySchoolYearIdAndClassId(Long schoolYearId, Long classId) {
        SchoolYear schoolYear = schoolYearService.findById(schoolYearId);
        Class clazz = classService.findById(classId);

        return timetableRepository.findBySchoolYearAndClazz(schoolYear, clazz);
    }
}
