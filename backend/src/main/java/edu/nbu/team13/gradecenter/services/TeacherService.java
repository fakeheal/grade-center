package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.configurations.ModelMapperConfig;
import edu.nbu.team13.gradecenter.dtos.TeacherDto;
import edu.nbu.team13.gradecenter.entities.Teacher;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.exceptions.TeacherNotFound;
import edu.nbu.team13.gradecenter.repositories.TeacherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    private final UserService userService;

    private final ModelMapperConfig modelMapperConfig;

    public TeacherService(TeacherRepository teacherRepository, UserService userService, ModelMapperConfig modelMapperConfig) {
        this.teacherRepository = teacherRepository;
        this.userService = userService;
        this.modelMapperConfig = modelMapperConfig;
    }

    /**
     * Creates a new teacher.
     *
     * @param teacherDto the teacher data transfer object
     * @return the created teacher
     */
    public TeacherDto create(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();

        User user = userService.create(teacherDto, UserRole.TEACHER);
        teacher.setUser(user);

        return modelMapperConfig
                .modelMapper()
                .map(teacherRepository.save(teacher), TeacherDto.class);
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
        return modelMapperConfig
                .modelMapper()
                .map(teacherRepository.save(teacher), TeacherDto.class);
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

        userService.delete(user.getId());
        teacherRepository.delete(teacher);
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
