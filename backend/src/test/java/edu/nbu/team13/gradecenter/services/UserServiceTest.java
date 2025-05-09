package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.UserDto;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.exceptions.EmailNotAvailable;
import edu.nbu.team13.gradecenter.exceptions.UserNotFound;
import edu.nbu.team13.gradecenter.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void testCreateUserSuccess() {
        UserDto dto = new UserDto(null, "test@example.com", "John", "Doe", "password123");
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User created = userService.create(dto);

        assertEquals(dto.getEmail(), created.getEmail());
        assertEquals(UserRole.STUDENT, created.getRole());
    }

    @Test
    void testCreateUserDuplicateEmailThrows() {
        UserDto dto = new UserDto(null, "exists@example.com", "Jane", "Doe", "password123");
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(EmailNotAvailable.class, () -> userService.create(dto));
    }

    @Test
    void testFindUserByIdSuccess() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User found = userService.findById(1L);
        assertEquals("test@example.com", found.getEmail());
    }

    @Test
    void testFindUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFound.class, () -> userService.findById(1L));
    }

    @Test
    void testUpdateUserSuccess() {
        UserDto dto = new UserDto(null, "updated@example.com", "Alice", "Smith", "pass");
        User user = new User();
        user.setId(1L);
        user.setEmail("old@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("updated@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User updated = userService.update(1L, dto);
        assertEquals("updated@example.com", updated.getEmail());
    }

    @Test
    void testUpdateUserWithExistingEmailThrows() {
        UserDto dto = new UserDto(null, "taken@example.com", "Bob", "Brown", "pass");
        User user = new User();
        user.setId(1L);
        user.setEmail("old@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("taken@example.com")).thenReturn(true);

        assertThrows(EmailNotAvailable.class, () -> userService.update(1L, dto));
    }

    @Test
    void testDeleteUserSuccess() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.delete(1L));
        verify(userRepository, times(1)).delete(user);
    }
}
