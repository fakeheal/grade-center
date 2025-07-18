package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.SchoolDto;
import edu.nbu.team13.gradecenter.dtos.TeacherDto;
import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.exceptions.EmailNotAvailable;
import edu.nbu.team13.gradecenter.exceptions.UserNotFound;
import edu.nbu.team13.gradecenter.repositories.TeacherRepository;
import edu.nbu.team13.gradecenter.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class TeacherServiceTest {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    @Autowired
    private SchoolService schoolService;

    private School school;
    @BeforeEach
    void setUp() {
        teacherRepository.deleteAll();
        userRepository.deleteAll();

        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setName("Test School");
        schoolDto.setAddress("Test Address");
        school = schoolService.create(schoolDto);
    }

    @Test
    void createTeacher() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "teacher@email.com";
        String password = "password";

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setSchoolId(school.getId());
        teacherDto.setFirstName(firstName);
        teacherDto.setLastName(lastName);
        teacherDto.setEmail(email);
        teacherDto.setPassword(password);
        TeacherDto newTeacher = teacherService.create(teacherDto);
        User user = userService.findById(newTeacher.getUserId());

        assertThat(user.getFirstName()).isEqualTo(firstName);
        assertThat(user.getLastName()).isEqualTo(lastName);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getId()).isEqualTo(newTeacher.getUserId());
    }

    @Test
    void cannotCreateTeacherWithExistingEmail() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "teacher@email.com";
        String password = "password";

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setSchoolId(school.getId());
        teacherDto.setFirstName(firstName);
        teacherDto.setLastName(lastName);
        teacherDto.setEmail(email);
        teacherDto.setPassword(password);
        teacherService.create(teacherDto);
        assertThat(teacherRepository.findAll().size()).isEqualTo(1);

        assertThatException()
                .isThrownBy(() -> teacherService.create(teacherDto))
                .isInstanceOf(EmailNotAvailable.class);
        assertThat(teacherRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void canUpdateTeacher() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "teacher@email.com";
        String password = "password";

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setSchoolId(1L);
        teacherDto.setFirstName(firstName);
        teacherDto.setLastName(lastName);
        teacherDto.setEmail(email);
        teacherDto.setPassword(password);
        TeacherDto newTeacher = teacherService.create(teacherDto);

        String updatedFirstName = "Jane";
        String updatedLastName = "Smith";

        TeacherDto updatedTeacherDto = new TeacherDto();
        updatedTeacherDto.setSchoolId(school.getId());
        updatedTeacherDto.setFirstName(updatedFirstName);
        updatedTeacherDto.setLastName(updatedLastName);
        updatedTeacherDto.setEmail(email);
        TeacherDto updatedTeacher = teacherService.update(newTeacher.getId(), updatedTeacherDto);
        User user = userService.findById(updatedTeacher.getUserId());
        assertThat(user.getFirstName()).isEqualTo(updatedFirstName);
        assertThat(user.getLastName()).isEqualTo(updatedLastName);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getId()).isEqualTo(updatedTeacher.getUserId());
    }

    @Test
    void cannotUpdateTeacherWithEmailAlreadyInUse() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "teacher@email.com";
        String password = "password";

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setSchoolId(school.getId());
        teacherDto.setFirstName(firstName);
        teacherDto.setLastName(lastName);
        teacherDto.setEmail(email);
        teacherDto.setPassword(password);
        TeacherDto newTeacher = teacherService.create(teacherDto);

        String anotherEmail = "teacher2@email.com";
        teacherDto.setEmail(anotherEmail);
        teacherService.create(teacherDto);

        assertThat(teacherRepository.findAll().size()).isEqualTo(2);
        assertThatException()
                .isThrownBy(() -> teacherService.update(newTeacher.getUserId(), teacherDto))
                .isInstanceOf(EmailNotAvailable.class);
    }

    @Test
    void canDeleteTeacherAndRelatedUser() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "teacher@email.com";
        String password = "password";

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setSchoolId(school.getId());
        teacherDto.setFirstName(firstName);
        teacherDto.setLastName(lastName);
        teacherDto.setEmail(email);
        teacherDto.setPassword(password);
        TeacherDto newTeacher = teacherService.create(teacherDto);

        assertThat(teacherRepository.findAll().size()).isEqualTo(1);

        teacherService.delete(newTeacher.getUserId());

        assertThat(teacherRepository.findAll().size()).isEqualTo(0);
        assertThatException()
                .isThrownBy(() -> userService.findById(newTeacher.getUserId()))
                .isInstanceOf(UserNotFound.class);
    }

    @Test
    void canFindTeachersById() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "teacher@email.com";
        String password = "password";

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setSchoolId(school.getId());
        teacherDto.setFirstName(firstName);
        teacherDto.setLastName(lastName);
        teacherDto.setEmail(email);
        teacherDto.setPassword(password);
        TeacherDto newTeacher = teacherService.create(teacherDto);
        TeacherDto found = teacherService.findByUserId(newTeacher.getUserId());
        assertThat(found.getId()).isEqualTo(newTeacher.getUserId());
    }
}
