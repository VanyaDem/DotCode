package com.testtask.dotcode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.dotcode.domain.entity.User;
import com.testtask.dotcode.dto.UserDto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static com.testtask.dotcode.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "/application-test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    @SneakyThrows
    @Test
    void addUser_should_return_201_status() {
        var dto = getUser();

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(objectMapper, dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @SneakyThrows
    @Test
    void addUser_adds_new_customer_to_DB() {
        var dto = getUser();

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(objectMapper, dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        var customer = entityManager.find(User.class, 1L);
        assertNotNull(customer);

    }

    @SneakyThrows
    @Test
    @Transactional
    void addUser_should_return_409_status_if_email_already_exist() {
        entityManager.persist(getUserList().get(0));
        var dto = getUser();

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(objectMapper, dto)))
                .andExpect(MockMvcResultMatchers.status().isConflict());

    }

    @SneakyThrows
    @Test
    void getAll_should_return_200_status() {
        mockMvc.perform(MockMvcRequestBuilders.get("/users?page=0&size=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @SneakyThrows
    @Test
    @Transactional
    void getAll_should_returns_users() {
        getUserList().stream().peek(c -> c.setId(null)).forEach(entityManager::persist);

        var res = mockMvc.perform(MockMvcRequestBuilders.get("/users?page=0&size=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<UserDto> users = Arrays.asList(objectMapper.readValue(res, UserDto[].class));

        assertEquals(3, users.size());

    }

    @SneakyThrows
    @Test
    @Transactional
    void getAll_should_returns_custom_header() {
        getUserList().stream().peek(c -> c.setId(null)).forEach(entityManager::persist);

        var res = mockMvc.perform(MockMvcRequestBuilders.get("/users?page=0&size=2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getHeader("X-Total-Pages");


        assertEquals("2", res);

    }

    @SneakyThrows
    @Test
    @Transactional
    void getAll_should_returns_users_in_pages() {
        getUserList().stream().peek(c -> c.setId(null)).forEach(entityManager::persist);

        var firstPage = mockMvc.perform(MockMvcRequestBuilders.get("/users?page=0&size=2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<UserDto> page1 = Arrays.asList(objectMapper.readValue(firstPage, UserDto[].class));

        var secondPage = mockMvc.perform(MockMvcRequestBuilders.get("/users?page=1&size=2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<UserDto> page2 = Arrays.asList(objectMapper.readValue(secondPage, UserDto[].class));

        assertEquals(2, page1.size());
        assertEquals(1, page2.size());

    }

    @SneakyThrows
    @Test
    @Transactional
    void getById_should_return_200_status() {

        getUserList().stream().peek(c -> c.setId(null)).forEach(entityManager::persist);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @SneakyThrows
    @Test
    @Transactional
    void getById_returns_customer() {
        getUserList().stream().peek(c -> c.setId(null)).forEach(entityManager::persist);

        var res = mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto user = objectMapper.readValue(res, UserDto.class);

        assertEquals(1, user.getId());
        assertEquals("Ivan", user.getFirstName());
        assertEquals("Demydenko", user.getLastName());
        assertEquals("foo@email.com", user.getEmail());

    }

    @SneakyThrows
    @Test
    @Transactional
    void updateUser_should_return_200_status() {
        getUserList().stream().peek(c -> c.setId(null)).forEach(entityManager::persist);

        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setFirstName("Ivanko");
        dto.setLastName("Demyd");
        dto.setEmail("fooooo@email.com");

        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(objectMapper, dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @SneakyThrows
    @Test
    @Transactional
    void updateUser_updates_user_in_db() {
        getUserList().stream().peek(c -> c.setId(null)).forEach(entityManager::persist);

        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setFirstName("Ivanko");
        dto.setLastName("Demyd");
        dto.setEmail("fooooo@email.com");

        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(objectMapper, dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        var user = entityManager.find(User.class, 1L);

        assertNotNull(user);
        assertEquals("fooooo@email.com", user.getEmail());
        assertEquals("Ivanko", user.getFirstName());
        assertEquals("Demyd", user.getLastName());
    }

    @SneakyThrows
    @Test
    @Transactional
    void deleteUser_should_returns_204_status() {
        getUserList().stream().peek(c -> c.setId(null)).forEach(entityManager::persist);

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @SneakyThrows
    @Test
    @Transactional
    void deleteUser_should_delete_user_from_db() {
        getUserList().stream().peek(c -> c.setId(null)).forEach(entityManager::persist);

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        User user = entityManager.find(User.class, 1L);

        assertNull(user);
    }


}
