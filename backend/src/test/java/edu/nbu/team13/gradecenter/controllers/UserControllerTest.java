package edu.nbu.team13.gradecenter.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nbu.team13.gradecenter.dtos.UserDto;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setEmail("test@example.com");
        sampleUser.setFirstName("Test");
        sampleUser.setLastName("User");
        sampleUser.setPassword("password123");
    }

    @Test
    void testCreateUser() throws Exception {
        UserDto dto = new UserDto(null, "test@example.com", "Test", "User", "password123");
        Mockito.when(userService.create(any(UserDto.class))).thenReturn(sampleUser);

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testGetUserById() throws Exception {
        Mockito.when(userService.findById(1L)).thenReturn(sampleUser);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testUpdateUser() throws Exception {
        UserDto dto = new UserDto(null, "updated@example.com", "Updated", "Name", "pass");
        sampleUser.setEmail("updated@example.com");
        Mockito.when(userService.update(Mockito.eq(1L), any(UserDto.class))).thenReturn(sampleUser);

        mockMvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testListUsers() throws Exception {
        Page<User> page = new PageImpl<>(List.of(sampleUser), PageRequest.of(0, 10), 1);
        Mockito.when(userService.search(any(), any(), any(), any())).thenReturn(page);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].email").value("test@example.com"));
    }
}
