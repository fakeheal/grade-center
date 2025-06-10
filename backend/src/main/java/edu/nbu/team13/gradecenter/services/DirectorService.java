package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.DirectorDto;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.exceptions.DirectorAlreadyExists;
import edu.nbu.team13.gradecenter.exceptions.InvalidUserRole;
import edu.nbu.team13.gradecenter.repositories.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;

    private final SchoolService schoolService;
    private final UserService userService;

    /**
     * Creates a new director.
     *
     * @param dto the director data transfer object containing the necessary information.
     * @return the created User entity representing the director.
     */
    public User create(DirectorDto dto) {
        if (schoolService.hasDirector(dto.getSchoolId())) {
            throw new DirectorAlreadyExists(dto.getSchoolId());
        }
        return userService.create(dto, UserRole.DIRECTOR);
    }


    /**
     * Retrieves a paginated list of directors based on the provided filters.
     *
     * @param firstName first name filter
     * @param lastName  last name filter
     * @param email     email filter
     * @param pageable  pagination information
     * @return a Page of User entities representing the directors
     */
    public Page<User> index(String firstName,
                            String lastName,
                            String email,
                            Pageable pageable) {
        return directorRepository.findDirectorsByFilters(firstName, lastName, email, pageable);
    }


    /**
     * Updates an existing director's information.
     *
     * @param id  the ID of the director to update
     * @param dto the director data transfer object containing the updated information
     * @return the updated User entity representing the director
     */
    public User update(Long id, DirectorDto dto) {
        return userService.update(id, dto);
    }


    /**
     * Deletes a director by their ID.
     *
     * @param id the ID of the director to delete
     */
    public void delete(Long id) {
        userService.delete(id);
    }

    /**
     * Finds a director by their ID.
     *
     * @param id the ID of the director to find
     * @return the User entity representing the director, or null if not found
     */
    public User findById(Long id) {
        User user = userService.findById(id);
        if (user.getRole() != UserRole.DIRECTOR) {
            throw new InvalidUserRole(id, UserRole.DIRECTOR.name());
        }
        return user;
    }
}