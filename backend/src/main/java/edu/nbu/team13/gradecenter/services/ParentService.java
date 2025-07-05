package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.ParentDto;
import edu.nbu.team13.gradecenter.dtos.ParentResponseDto;
import edu.nbu.team13.gradecenter.entities.ParentStudent;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.exceptions.InvalidUserRole;
import edu.nbu.team13.gradecenter.repositories.ParentRepository;
import edu.nbu.team13.gradecenter.repositories.ParentStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final ParentRepository parentRepository;
    private final ParentStudentRepository parentStudentRepository;
    private final UserService userService;

    /**
     * Creates a new parent user and links it to the specified students.
     *
     * @param dto the ParentDto containing parent details and student IDs
     * @return the created User object representing the parent
     */
    @Transactional
    public User create(ParentDto dto) {
        User user = userService.create(dto, UserRole.PARENT);

        if (dto.getStudentIds() != null) {
            saveLinks(user, dto.getStudentIds());
        }

        return user;
    }

    /**
     * Retrieves a paginated list of parents based on the provided filters.
     *
     * @param fn    first name filter
     * @param ln    last name filter
     * @param email email filter
     * @param pg    pagination information
     * @return a Page of User objects representing parents
     */
    public Page<User> index(String fn, String ln, String email, Pageable pg) {
        return parentRepository.findParentsByFilters(fn, ln, email, pg);
    }

    /**
     * Updates an existing parent user and links it to the specified students.
     *
     * @param id  the ID of the parent to update
     * @param dto the ParentDto containing updated parent details and student IDs
     * @return the updated User object representing the parent
     */
    @Transactional
    public User update(Long id, ParentDto dto) {
        User user = userService.update(id, dto);

        parentStudentRepository.deleteAllByParentId(id);

        if (dto.getStudentIds() != null) {
            List<Long> filteredStudentIds = dto.getStudentIds().stream()
                .filter(studentId -> !studentId.equals(id))
                .collect(Collectors.toList());
            saveLinks(user, filteredStudentIds);
        }

        return user;
    }

    /**
     * Deletes a parent user by its ID.
     *
     * @param id the ID of the parent to delete
     */
    @Transactional
    public void delete(Long id) {
        parentStudentRepository.deleteAllByParentId(id);
        userService.delete(id);
    }

    /**
     * Saves links between a parent and their students.
     *
     * @param parent     the parent user
     * @param studentIds the list of student IDs to link with the parent
     */
    private void saveLinks(User parent, List<Long> studentIds) {
        // Ensure studentIds are unique to prevent duplicate entries
        List<Long> distinctStudentIds = studentIds.stream().distinct().collect(Collectors.toList());

        for (Long sid : distinctStudentIds) {
            User student = userService.findById(sid);
            if (!student.getRole().equals(UserRole.STUDENT)) {
                throw new InvalidUserRole(sid, UserRole.STUDENT.name());
            }

            ParentStudent parentStudent = new ParentStudent();
            parentStudent.setParent(parent);
            parentStudent.setStudent(student);
            parentStudentRepository.save(parentStudent);
        }
    }

    /**
     * Finds a parent by its ID.
     *
     * @param id the ID of the parent to find
     * @return the User object representing the parent
     */
    public ParentResponseDto findById(Long id) {
        User user = userService.findById(id);
        if (user.getRole() != UserRole.PARENT) {
            throw new InvalidUserRole(id, UserRole.PARENT.name());
        }
        List<Long> studentIds = parentStudentRepository.findAllByParentId(id)
                .stream()
                .map(ps -> ps.getStudent().getId())
                .collect(Collectors.toList());
        return new ParentResponseDto(user, studentIds);
    }

    public List<User> getStudentsForParent(Long parentId) {
        return parentStudentRepository.findAllByParentId(parentId).stream()
                .map(ParentStudent::getStudent)
                .collect(Collectors.toList());
    }
}
