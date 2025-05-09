package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.TeacherDto;
import edu.nbu.team13.gradecenter.entities.Teacher;
import edu.nbu.team13.gradecenter.exceptions.TeacherNotFound;
import edu.nbu.team13.gradecenter.repositories.TeacherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    /**
     * Creates a new teacher.
     *
     * @param teacherDto the teacher data transfer object
     * @return the created teacher
     */
    public Teacher create(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        teacher.setSchoolId(teacher.getSchoolId());
        teacher.setUserId(teacherDto.getUserId());

        return teacherRepository.save(teacher);
    }

    /**
     * Updates an existing teacher.
     *
     * @param id         the ID of the teacher to update
     * @param teacherDto the teacher data transfer object
     * @return the updated teacher
     */
    public Teacher update(Long id, TeacherDto teacherDto) {
        Teacher existingTeacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFound(id));

        existingTeacher.setSchoolId(teacherDto.getSchoolId());
        existingTeacher.setUserId(teacherDto.getUserId());

        return teacherRepository.save(existingTeacher);
    }

    /**
     * Deletes a teacher by ID.
     *
     * @param id the ID of the teacher to delete
     */
    public void delete(Long id) {
        Teacher existingTeacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFound(id));

        teacherRepository.delete(existingTeacher);
    }

    /**
     * Finds a teacher by ID.
     *
     * @param id the ID of the teacher to find
     * @return the found teacher
     */
    public Teacher findById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFound(id));
    }

    /**
     * Finds all teachers with optional filters.
     *
     * @param schoolId id of the school to filter by (optional)
     * @param userId   id of the user to filter by (optional)
     * @param pageable pagination information
     * @return a page of teachers
     */
    public Page<Teacher> search(Long schoolId, Long userId, Pageable pageable) {
        return teacherRepository.findByOptionalFilters(schoolId, userId, pageable);
    }
}
