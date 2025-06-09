package edu.nbu.team13.gradecenter.repositories;

import edu.nbu.team13.gradecenter.dtos.SchoolDto;
import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.services.SchoolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SchoolService schoolService;

    private School school;

    @BeforeEach
    void setUp() {
        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setName("Test School");
        schoolDto.setAddress("Test Address");
        school = schoolService.create(schoolDto);
    }

    @Test
    void existsByEmailReturnsTrueWhenUserIsPresent() {
        String email = "test@test.com";

        User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setRole(UserRole.STUDENT);
        user.setSchool(school);
        entityManager.persistAndFlush(user);

        boolean exists = userRepository.existsByEmail(email);
        assertThat(exists).isTrue();
    }

    @Test
    void existsByEmailReturnsFalseWhenUserIsNotPresent() {
        String email = "test@test.com";

        User user = new User();
        user.setEmail("email@emai.com");
        user.setPassword("password");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setRole(UserRole.STUDENT);
        user.setSchool(school);
        entityManager.persistAndFlush(user);

        boolean exists = userRepository.existsByEmail(email);
        assertThat(exists).isFalse();
    }
}
