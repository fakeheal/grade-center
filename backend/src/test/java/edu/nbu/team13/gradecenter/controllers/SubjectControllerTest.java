package edu.nbu.team13.gradecenter.controllers;

import edu.nbu.team13.gradecenter.dtos.SchoolDto;
import edu.nbu.team13.gradecenter.dtos.SubjectDto;
import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.entities.Subject;
import edu.nbu.team13.gradecenter.services.SchoolService;
import edu.nbu.team13.gradecenter.services.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SubjectControllerTest {
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private MockMvc mockMvc;

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
    void index() throws Exception {
        assertDoesNotThrow(() -> {
            mockMvc.perform(MockMvcRequestBuilders.get("/subjects?schoolId=" + school.getId()))
                    .andExpect(status().isOk());
        });

        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setName("Математика");
        subjectDto.setSchoolId(school.getId());
        subjectService.create(subjectDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/subjects?schoolId=" + school.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Математика"));
    }

    @Test
    void show() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setName("Физика");
        subjectDto.setSchoolId(school.getId());
        Subject createdSubject = subjectService.create(subjectDto);

        assertDoesNotThrow(() -> {
            mockMvc.perform(MockMvcRequestBuilders.get("/subjects/" + createdSubject.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Физика"));
        });
    }

    @Test
    void create() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setName("Химия");
        subjectDto.setSchoolId(school.getId());

        assertDoesNotThrow(() -> {
            mockMvc.perform(MockMvcRequestBuilders.post("/subjects")
                    .contentType("application/json")
                    .content("{\"name\":\"Химия\", \"schoolId\":" + school.getId() + "}"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name").value("Химия"));
        });
    }

    @Test
    void update() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setName("Биология");
        subjectDto.setSchoolId(school.getId());
        Subject createdSubject = subjectService.create(subjectDto);

        assertDoesNotThrow(() -> {
            mockMvc.perform(MockMvcRequestBuilders.put("/subjects/" + createdSubject.getId())
                    .contentType("application/json")
                    .content("{\"name\":\"Физика\", \"schoolId\":" + school.getId() + "}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Физика"));
        });
    }

    @Test
    void delete() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setName("География");
        subjectDto.setSchoolId(school.getId());
        Subject createdSubject = subjectService.create(subjectDto);

        assertDoesNotThrow(() -> {
            mockMvc.perform(MockMvcRequestBuilders.delete("/subjects/" + createdSubject.getId()))
                    .andExpect(status().isNoContent());
        });
    }
}