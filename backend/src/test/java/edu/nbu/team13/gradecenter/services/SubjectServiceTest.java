package edu.nbu.team13.gradecenter.services;

import edu.nbu.team13.gradecenter.dtos.SchoolDto;
import edu.nbu.team13.gradecenter.dtos.SubjectDto;
import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.entities.Subject;
import edu.nbu.team13.gradecenter.exceptions.SubjectNotFound;
import edu.nbu.team13.gradecenter.repositories.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class SubjectServiceTest {
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SchoolService schoolService;

    private School school;

    @BeforeEach
    void setUp() {
        subjectRepository.deleteAll();

        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setName("Test School");
        schoolDto.setAddress("Test Address");
        school = schoolService.create(schoolDto);
    }

    @Test
    void findAll() {
        List<Subject> subjects = subjectService.findAll(this.school.getId());
        assertNotNull(subjects);
        assertEquals(0, subjects.size());

        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSchoolId(school.getId());
        subjectDto.setName("Математика");
        subjectService.create(subjectDto);

        subjects = subjectService.findAll(this.school.getId());
        assertNotNull(subjects);
        assertEquals(1, subjects.size());
        assertEquals("Математика", subjects.getFirst().getName());
    }

    @Test
    void create() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSchoolId(school.getId());
        subjectDto.setName("Математика");

        Subject createdSubject = subjectService.create(subjectDto);
        assertNotNull(createdSubject);
        assertEquals("Математика", createdSubject.getName());
        assertEquals(school.getId(), createdSubject.getSchool().getId());
    }

    @Test
    void update() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSchoolId(school.getId());
        subjectDto.setName("Математика");

        Subject createdSubject = subjectService.create(subjectDto);
        assertNotNull(createdSubject);
        assertEquals("Математика", createdSubject.getName());

        subjectDto.setName("Физика");
        Subject updatedSubject = subjectService.update(createdSubject.getId(), subjectDto);
        assertNotNull(updatedSubject);
        assertEquals("Физика", updatedSubject.getName());
        assertEquals(school.getId(), updatedSubject.getSchool().getId());

        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setName("New School");
        schoolDto.setAddress("New Address");
        School newSchool = schoolService.create(schoolDto);
        subjectDto.setSchoolId(newSchool.getId());
        Subject updatedSubjectWithNewSchool = subjectService.update(updatedSubject.getId(), subjectDto);
        assertNotNull(updatedSubjectWithNewSchool);
        assertEquals("Физика", updatedSubjectWithNewSchool.getName());
        assertEquals(newSchool.getId(), updatedSubjectWithNewSchool.getSchool().getId());
    }

    @Test
    void delete() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSchoolId(school.getId());
        subjectDto.setName("Математика");

        Subject createdSubject = subjectService.create(subjectDto);
        assertNotNull(createdSubject);
        assertEquals("Математика", createdSubject.getName());

        subjectService.delete(createdSubject.getId());
        assertThrows(SubjectNotFound.class, () -> subjectService.findById(createdSubject.getId()));
    }

    @Test
    void findById() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSchoolId(school.getId());
        subjectDto.setName("Математика");

        Subject createdSubject = subjectService.create(subjectDto);
        assertNotNull(createdSubject);
        assertEquals("Математика", createdSubject.getName());

        Subject foundSubject = subjectService.findById(createdSubject.getId());
        assertNotNull(foundSubject);
        assertEquals(createdSubject.getId(), foundSubject.getId());
        assertEquals("Математика", foundSubject.getName());
    }
}