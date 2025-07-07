package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.AbsenceDto;
import edu.nbu.team13.gradecenter.dtos.SchoolDto;
import edu.nbu.team13.gradecenter.entities.Absence;
import edu.nbu.team13.gradecenter.repositories.AbsenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AbsenceServiceTest {
    @Mock
    private AbsenceRepository absenceRepository;
    private AbsenceService absenceService;
    private AbsenceDto absenceDto;

    @BeforeEach
    void setUp() {
        absenceService = new AbsenceService(absenceRepository);
        absenceDto = new AbsenceDto();
        absenceDto.setDate(LocalDate.of(2020, 1, 1));
        absenceDto.setReason("Test Reason");
        absenceDto.setExcused(true);
        absenceDto.setTeacherId(1L);
        absenceDto.setStudentId(1L);
    }

    @Test
    public void create() {
        var abscence = new Absence();
        abscence.FromDto(absenceDto);

        Mockito.when(absenceRepository.save(any())).thenReturn(abscence);

        var result = absenceService.create(absenceDto);
        assertEquals(abscence.getReason(),result.getReason());

    }
    @Test
    public void update() {
        var abscence = new Absence();
        abscence.FromDto(absenceDto);
        abscence.setReason("Updated Reason");

        Mockito.when(absenceRepository.save(any())).thenReturn(abscence);
        Mockito.when(absenceRepository.findById(1L)).thenReturn(Optional.of(abscence));

        var result = absenceService.update(1L,absenceDto);
        assertEquals(abscence.getReason(),result.getReason());
    }
    @Test
    public void findById() {
        var abscence = new Absence();
        abscence.FromDto(absenceDto);
        Mockito.when(absenceRepository.findById(1L)).thenReturn(Optional.of(abscence));

        var result = absenceService.findById(1L);
        assertEquals(abscence.getReason(),result.getReason());

    }
    @Test
    public void delete() {
        var abscence = new Absence();
        abscence.FromDto(absenceDto);
        Mockito.when(absenceRepository.findById(1L)).thenReturn(Optional.of(abscence));
        absenceService.delete(1L);
    }
}
