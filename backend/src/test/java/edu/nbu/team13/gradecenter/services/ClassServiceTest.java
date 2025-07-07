package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.ClassDto;
import edu.nbu.team13.gradecenter.entities.Class;
import edu.nbu.team13.gradecenter.repositories.ClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ClassServiceTest {
    @Mock
    private ClassRepository classRepository;
    private ClassService classService;
    private ClassDto classDto;

    @BeforeEach
    void setUp() {
        classService = new ClassService(classRepository);
        classDto = new ClassDto();
        classDto.setName("Test Class");
        classDto.setSchoolId(1L);
        classDto.setGrade(11L);

    }

    @Test
    public void create() {
        var cls = new Class();
        cls.FromDto(classDto);

        Mockito.when(classRepository.save(any())).thenReturn(cls);

        var result = classService.create(classDto);
        assertEquals(cls.getGrade(),result.getGrade());

    }
    @Test
    public void update() {
        var cls = new Class();
        cls.FromDto(classDto);
        cls.setGrade(12L);

        Mockito.when(classRepository.findById(1L)).thenReturn(Optional.of(cls));
        Mockito.when(classRepository.save(any())).thenReturn(cls);


        var result = classService.update(1L,classDto);
        assertEquals(cls.getGrade(),result.getGrade());
    }
    @Test
    public void findById() {
        var cls = new Class();
        cls.FromDto(classDto);

        Mockito.when(classRepository.findById(1L)).thenReturn(Optional.of(cls));

        var result = classService.findById(1L);
        assertEquals(cls.getGrade(),result.getGrade());

    }
    @Test
    public void delete() {
        var cls = new Class();
        cls.FromDto(classDto);
        cls.setGrade(12L);

        Mockito.when(classRepository.findById(1L)).thenReturn(Optional.of(cls));
        classService.delete(1L);
    }
}
