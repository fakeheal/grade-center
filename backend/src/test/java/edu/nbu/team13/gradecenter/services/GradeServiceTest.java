package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.GradeDto;
import edu.nbu.team13.gradecenter.entities.Class;
import edu.nbu.team13.gradecenter.entities.Grade;
import edu.nbu.team13.gradecenter.repositories.GradeRepository;
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
public class GradeServiceTest {
    @Mock
    private GradeRepository gradeRepository;
    private GradeService gradeService;
    private GradeDto gradeDto;

    @BeforeEach
    void setUp() {
        gradeService = new GradeService(gradeRepository);
        gradeDto = new GradeDto();
        gradeDto.setDate(LocalDate.of(2020, 1, 1));
        gradeDto.setValue(6.0);
        gradeDto.setTeacherId(1L);
        gradeDto.setStudentId(1L);
        gradeDto.setSchoolYearId(1L);
    }
    @Test
    public void create() {
        var grade = new Grade();
        grade.FromDto(gradeDto);

        Mockito.when(gradeRepository.save(any())).thenReturn(grade);

        var result = gradeService.create(gradeDto);
        assertEquals(grade.getValue(),result.getValue());

    }
    @Test
    public void update() {
        var grade = new Grade();
        grade.FromDto(gradeDto);

        Mockito.when(gradeRepository.save(any())).thenReturn(grade);

        Mockito.when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        Mockito.when(gradeRepository.save(any())).thenReturn(grade);


        var result = gradeService.update(1L,gradeDto);
        assertEquals(grade.getValue(),result.getValue());
    }
    @Test
    public void findById() {
        var grade = new Grade();
        grade.FromDto(gradeDto);

        Mockito.when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));

        var result = gradeService.findById(1L);
        assertEquals(grade.getValue(),result.getValue());

    }
    @Test
    public void delete() {
        var grade = new Grade();
        grade.FromDto(gradeDto);

        Mockito.when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        gradeService.delete(1L);
    }

}
