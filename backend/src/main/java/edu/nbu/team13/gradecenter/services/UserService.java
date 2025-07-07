package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.UserDto;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.exceptions.EmailNotAvailable;
import edu.nbu.team13.gradecenter.exceptions.UserNotFound;
import edu.nbu.team13.gradecenter.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final SchoolService schoolService;

    public UserService(UserRepository userRepository, SchoolService schoolService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.schoolService = schoolService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Finds all users.
     *
     * @return List of User objects
     */
    public Page<User> search(String firstName, String lastName, String email, Pageable pageable) {
        return userRepository.findByOptionalFilters(firstName, lastName, email, pageable);
    }


    /**
     * Finds a user by ID.
     *
     * @param id the id of the user to find
     * @return User object
     */
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));
    }


    /**
     * Creates a new user.
     *
     * @param userDto User data transfer object
     * @return User object
     */
    public User create(UserDto userDto, UserRole role) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailNotAvailable(userDto.getEmail());
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        user.setSchool(this.schoolService.findById(userDto.getSchoolId()));
        user.setRole(role);

        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

        return userRepository.save(user);
    }


    /**
     * Updates an existing user.
     *
     * @param id      the id of the user to update
     * @param userDto User data transfer object
     * @return User object
     */
    public User update(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));

        if (!user.getEmail().equals(userDto.getEmail()) && userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailNotAvailable(userDto.getEmail());
        }

        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setSchool(this.schoolService.findById(userDto.getSchoolId()));

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        }

        return userRepository.save(user);
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the id of the user to delete
     */
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound(0L));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}
