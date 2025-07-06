package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.configurations.ModelMapperConfig;
import edu.nbu.team13.gradecenter.dtos.TeacherDto;
import edu.nbu.team13.gradecenter.entities.Subject;
import edu.nbu.team13.gradecenter.entities.Teacher;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.exceptions.TeacherNotFound;
import edu.nbu.team13.gradecenter.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final UserService userService;

    private final SubjectService subjectService;

    private final ModelMapperConfig modelMapperConfig;

    public TeacherService(TeacherRepository teacherRepository, UserService userService, SubjectService subjectService, ModelMapperConfig modelMapperConfig) {
        this.teacherRepository = teacherRepository;
        this.userService = userService;
        this.subjectService = subjectService;
        this.modelMapperConfig = modelMapperConfig;
    }

    /**
     * Creates a new teacher.
     *
     * @param teacherDto the teacher data transfer object
     * @return the created teacher
     */
    public TeacherDto create(TeacherDto teacherDto) {
        Teacher newTeacher = new Teacher();

        User user = userService.create(teacherDto, UserRole.TEACHER);
        newTeacher.setUser(user);


        Teacher teacher = teacherRepository.save(newTeacher);
        this.syncSubjects(teacher, teacherDto.getSubjects());

        return modelMapperConfig
                .modelMapper()
                .map(teacher, TeacherDto.class);
    }

    /**
     * Updates an existing teacher.
     *
     * @param id         the ID of the teacher to update
     * @param teacherDto the teacher data transfer object
     * @return the updated teacher
     */
    public TeacherDto update(Long id, TeacherDto teacherDto) {
        User user = userService.findById(id);
        Teacher teacher = teacherRepository.findByUserId(id)
                .orElseThrow(() -> new TeacherNotFound(id));

        userService.update(user.getId(), teacherDto);

        this.syncSubjects(teacher, teacherDto.getSubjects());

        return modelMapperConfig
                .modelMapper()
                .map(teacherRepository.save(teacher), TeacherDto.class);
    }

    /**
     * Synchronizes the subjects of a teacher with the provided set of subjects.
     *
     * @param teacher       the teacher whose subjects are to be synchronized
     * @param inputSubjects the set of subjects to synchronize with
     */
    @Transactional
    public void syncSubjects(Teacher teacher, Set<Subject> inputSubjects) {
        Set<Long> subjectIds = inputSubjects.stream()
                .map(Subject::getId)
                .collect(Collectors.toSet());

        Set<Subject> fetchedSubjects = new HashSet<>(subjectService.findAllById(subjectIds));

        teacher.setSubjects(fetchedSubjects);

        teacherRepository.save(teacher);
    }

    /**
     * Deletes a teacher by ID.
     *
     * @param id the ID of the teacher to delete
     */
    public void delete(Long id) {
        User user = userService.findById(id);
        Teacher teacher = teacherRepository.findByUserId(id)
                .orElseThrow(() -> new TeacherNotFound(id));

        teacher.getSubjects().clear();
        teacherRepository.delete(teacher);
        userService.delete(user.getId());
    }

    /**
     * Finds a teacher by User ID.
     *
     * @param id the User ID of the teacher to find
     * @return the found teacher
     */
    public TeacherDto findByUserId(Long id) {
        return modelMapperConfig
                .modelMapper()
                .map(
                        teacherRepository.findByUserId(id)
                                .orElseThrow(() -> new TeacherNotFound(id)), TeacherDto.class
                );
    }

    /**
     * Finds a teacher by ID.
     *
     * @param id the ID of the teacher to find
     * @return the found teacher entity
     */
    public Teacher findById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFound(id));
    }

    /**
     * Finds teachers by optional filters - first name, last name, email, school ID, and user ID.
     *
     * @param firstName search filter for first name
     * @param lastName  search filter for last name
     * @param email     search filter for email
     * @param schoolId  search filter for school ID
     * @param userId    search filter for user ID
     * @param pageable  pagination information
     * @return a page of teachers matching the filters
     */
    public Page<Teacher> search(String firstName, String lastName, String email, Long schoolId, Long userId, Pageable pageable) {
        return teacherRepository.findByOptionalFilters(
                firstName,
                lastName,
                email,
                schoolId,
                userId,
                pageable
        );
    }
}
