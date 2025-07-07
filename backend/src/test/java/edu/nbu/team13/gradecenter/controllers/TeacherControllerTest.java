package edu.nbu.team13.gradecenter.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nbu.team13.gradecenter.dtos.SchoolDto;
import edu.nbu.team13.gradecenter.dtos.TeacherDto;
import edu.nbu.team13.gradecenter.entities.School;
import edu.nbu.team13.gradecenter.repositories.TeacherRepository;
import edu.nbu.team13.gradecenter.services.SchoolService;
import edu.nbu.team13.gradecenter.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TeacherControllerTest {
    @Autowired
    private TeacherRepository teacherRepository;

    @MockitoBean
    private TeacherService teacherService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void itCanDisplayTeacher() throws Exception {
        String firstName = "John";
        String lastName = "Doe";
        String email = "teacher@email.com";
        String password = "password";

        TeacherDto teacher = new TeacherDto();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setEmail(email);
        teacher.setPassword(password);
        teacher.setSchoolId(school.getId());

        teacherService.create(teacher);

        when(teacherService.findByUserId(1L)).thenReturn(teacher);

        mockMvc.perform(MockMvcRequestBuilders.get("/teachers/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(firstName))
                .andExpect(jsonPath("$.lastName").value(lastName))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.schoolId").value(school.getId()))
                .andDo(print());
    }

    @Test
    void itCanCreateTeacher() throws Exception {
        String firstName = "John";
        String lastName = "Doe";
        String email = "teacher2@email.com";
        String password = "password";

        TeacherDto teacher = new TeacherDto();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setEmail(email);
        teacher.setPassword(password);
        teacher.setSchoolId(school.getId());

        given(teacherService.create(any(TeacherDto.class))).willReturn(teacher);

        String json = objectMapper.writeValueAsString(teacher);

        mockMvc.perform(post("/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(firstName))
                .andExpect(jsonPath("$.lastName").value(lastName))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.schoolId").value(school.getId()))

                .andExpect(jsonPath("$.password").doesNotExist())
                .andDo(print());

    }

    @Test
    void itCanUpdateTeacher() throws Exception {
        String firstName = "John";
        String lastName = "Doe";
        String email = "teacher@email.com";
        String password = "password";

        TeacherDto teacher = new TeacherDto();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setEmail(email);
        teacher.setPassword(password);
        teacher.setSchoolId(school.getId());

        teacherService.create(teacher);

        TeacherDto updatedTeacher = new TeacherDto();
        updatedTeacher.setId(1L);
        updatedTeacher.setFirstName("Jane");
        updatedTeacher.setLastName("Doe");
        updatedTeacher.setEmail(email);
        updatedTeacher.setSchoolId(school.getId());
        updatedTeacher.setUserId(1L);

        given(teacherService.update(eq(1L), any(TeacherDto.class))).willReturn(updatedTeacher);

        String json = objectMapper.writeValueAsString(updatedTeacher);

        mockMvc.perform(put("/teachers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.schoolId").value(school.getId()))
                .andDo(print());
    }
}
