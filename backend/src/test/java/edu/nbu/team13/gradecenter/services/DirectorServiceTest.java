package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.DirectorDto;
import edu.nbu.team13.gradecenter.dtos.GradeDto;
import edu.nbu.team13.gradecenter.entities.Grade;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.entities.enums.UserRole;
import edu.nbu.team13.gradecenter.repositories.DirectorRepository;
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
public class DirectorServiceTest {
    @Mock
    private DirectorRepository directorRepository;
    @Mock
    private SchoolService schoolService;
    @Mock
    private UserService userService;
    private DirectorService directorService;
    private DirectorDto directorDto;

    @BeforeEach
    void setUp() {
        directorService = new DirectorService(directorRepository, schoolService, userService);
        directorDto = new DirectorDto();
        directorDto.setId(1L);
        directorDto.setFirstName("John");
        directorDto.setLastName("Doe");
        directorDto.setSchoolId(1L);
        directorDto.setEmail("main@main.bg");
        directorDto.setPassword("password");
    }
    @Test
    public void create() {
        var director = new User();
        director.FromDto(directorDto);

        Mockito.when(schoolService.hasDirector(any())).thenReturn(false);
        Mockito.when(userService.create(any(),any())).thenReturn(director);

        var result = directorService.create(directorDto);
        assertEquals(director.getEmail(),result.getEmail());

    }
    @Test
    public void update() {
        var director = new User();
        directorDto.setEmail("test@test.bg");
        director.FromDto(directorDto);



        Mockito.when(userService.update(any(),any())).thenReturn(director);

        var result = directorService.update(1L,directorDto);
        assertEquals(directorDto.getEmail(),result.getEmail());
    }
    @Test
    public void findById() {
        var director = new User();
        director.setRole(UserRole.DIRECTOR);
        director.FromDto(directorDto);

        Mockito.when(userService.findById(any())).thenReturn(director);

        var result = directorService.findById(1L);
        assertEquals(directorDto.getEmail(),result.getEmail());

    }
    @Test
    public void delete() {
        var director = new User();
        director.FromDto(directorDto);

        directorService.delete(1L);
    }
}

