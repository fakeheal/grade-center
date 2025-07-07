package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.ParentDto;
import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.repositories.ParentRepository;
import edu.nbu.team13.gradecenter.repositories.ParentStudentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ParentServiceTest {
    @Mock
    private ParentRepository parentRepository;
    @Mock
    private ParentStudentRepository parentStudentRepository;
    @Mock
    private UserService userService;
    private ParentService parentService;
    private ParentDto parentDto;

    @BeforeEach
    void setUp() {
        parentService = new ParentService(parentRepository,parentStudentRepository,userService);
        parentDto = new ParentDto();
        parentDto.setEmail("email@mail.com");
        parentDto.setPassword("password");
        parentDto.setFirstName("firstName");
        parentDto.setLastName("lastName");
        parentDto.setSchoolId(1L);
    }
    @Test
    public void create() {
        var parent = new User();
        parent.FromDto(parentDto);
        var school = new School();
        school.setId(1L);
        parent.setSchool(new edu.nbu.team13.gradecenter.entities.School());

        Mockito.when(userService.create(any(), any())).thenReturn(parent);

        var result = parentService.create(parentDto);
        assertEquals(parent.getEmail(),result.getEmail());

    }
    @Test
    public void update() {
        var parent = new User();
        parent.FromDto(parentDto);
        var school = new School();
        school.setId(1L);

        Mockito.when(userService.update(any(), any())).thenReturn(parent);

        var result = parentService.update(1L,parentDto);
        assertEquals(parent.getEmail(),result.getEmail());
    }
}
