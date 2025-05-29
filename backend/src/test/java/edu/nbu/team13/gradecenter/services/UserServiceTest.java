package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.SchoolDto;
import edu.nbu.team13.gradecenter.dtos.UserDto;
import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.exceptions.EmailNotAvailable;
import edu.nbu.team13.gradecenter.exceptions.UserNotFound;
import edu.nbu.team13.gradecenter.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private School school;

    @Autowired
    private SchoolService schoolService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setName("Test School");
        schoolDto.setAddress("Test Address");
        school = schoolService.create(schoolDto);
    }

    @Test
    void createUser() {
        String firstName = "Jane";
        String lastName = "Doe";
        String email = "user@email.com";
        String password = "password";

        UserDto userDto = new UserDto();
        userDto.setSchoolId(school.getId());
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);
        userDto.setPassword(password);
        User newUser = userService.create(userDto, UserRole.STUDENT);
        User user = userService.findById(newUser.getId());

        assertThat(user.getFirstName()).isEqualTo(firstName);
        assertThat(user.getLastName()).isEqualTo(lastName);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getId()).isEqualTo(newUser.getId());
    }

    @Test
    void cannotCreateUserWithExistingEmail() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "teacher@email.com";
        String password = "password";

        UserDto userDto = new UserDto();
        userDto.setSchoolId(school.getId());
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);
        userDto.setPassword(password);
        userService.create(userDto, UserRole.TEACHER);
        assertThat(userRepository.findAll().size()).isEqualTo(1);

        assertThatException()
                .isThrownBy(() -> userService.create(userDto, UserRole.TEACHER))
                .isInstanceOf(EmailNotAvailable.class);
        assertThat(userRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void canUpdateUser() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "user@email.com";
        String password = "password";

        UserDto userDto = new UserDto();
        userDto.setSchoolId(school.getId());
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);
        userDto.setPassword(password);
        User newUser = userService.create(userDto, UserRole.TEACHER);

        String updatedFirstName = "Jane";
        String updatedLastName = "Smith";

        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setId(newUser.getId());
        updatedUserDto.setFirstName(updatedFirstName);
        updatedUserDto.setLastName(updatedLastName);
        updatedUserDto.setEmail(email);
        updatedUserDto.setPassword(password);
        updatedUserDto.setSchoolId(newUser.getSchool().getId());
        User updatedUser = userService.update(updatedUserDto.getId(), updatedUserDto);
        User user = userService.findById(updatedUser.getId());
        assertThat(user.getFirstName()).isEqualTo(updatedFirstName);
        assertThat(user.getLastName()).isEqualTo(updatedLastName);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getId()).isEqualTo(updatedUser.getId());
    }

    @Test
    void cannotUpdateUerWithEmailAlreadyInUse() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "user@email.com";
        String password = "password";

        UserDto userDto = new UserDto();
        userDto.setSchoolId(school.getId());
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);
        userDto.setPassword(password);
        User newUser = userService.create(userDto, UserRole.TEACHER);
        assertThat(userRepository.findAll().size()).isEqualTo(1);

        String anotherEmail = "user2@email.com";
        userDto.setEmail(anotherEmail);
        userService.create(userDto, UserRole.TEACHER);

        assertThat(userRepository.findAll().size()).isEqualTo(2);
        assertThatException()
                .isThrownBy(() -> userService.update(newUser.getId(), userDto))
                .isInstanceOf(EmailNotAvailable.class);
    }

    @Test
    void canDeleteUser() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "user@email.com";
        String password = "password";

        UserDto userDto = new UserDto();
        userDto.setSchoolId(school.getId());
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);
        userDto.setPassword(password);

        User newUser = userService.create(userDto, UserRole.TEACHER);
        assertThat(userRepository.findAll().size()).isEqualTo(1);

        userService.delete(newUser.getId());

        assertThat(userRepository.findAll().size()).isEqualTo(0);
        assertThatException()
                .isThrownBy(() -> userService.findById(newUser.getId()))
                .isInstanceOf(UserNotFound.class);
    }

    @Test
    void canFindUserById() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "user@email.com";
        String password = "password";

        UserDto userDto = new UserDto();
        userDto.setSchoolId(school.getId());
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);
        userDto.setPassword(password);

        User newUser = userService.create(userDto, UserRole.TEACHER);
        User found = userService.findById(newUser.getId());

        assertThat(found.getFirstName()).isEqualTo(firstName);
        assertThat(found.getLastName()).isEqualTo(lastName);
        assertThat(found.getEmail()).isEqualTo(email);
        assertThat(found.getId()).isEqualTo(newUser.getId());
        assertThat(found.getSchool().getId()).isEqualTo(school.getId());
        assertThat(found.getRole()).isEqualTo(UserRole.TEACHER);
    }
}