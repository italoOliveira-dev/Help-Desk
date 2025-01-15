package com.estudo.microservice.userservice.controller.impl;

import com.estudo.microservice.userservice.entity.User;
import com.estudo.microservice.userservice.mapper.UserMapper;
import com.estudo.microservice.userservice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.requests.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.estudo.microservice.userservice.creator.CreatorUtils.generateMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerImplTest {

    public static final String BASE_URI = "/api/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserMapper userMapper;

    @Test
    void findByIdShouldReturnIsOk() throws Exception {
        final var entity = generateMock(User.class);
        final var userId = userRepository.save(entity).getId();

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value(entity.getName()))
                .andExpect(jsonPath("$.email").value(entity.getEmail()))
                .andExpect(jsonPath("$.password").value(entity.getPassword()))
                .andExpect(jsonPath("$.profiles").isArray());

        userRepository.deleteById(userId);
    }

    @Test
    void findByIdShouldReturnNotFound() throws Exception {

        mockMvc.perform(get("/api/users/{id}", "123"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value("Object not found! Id: 123, Type: User"))
                .andExpect(jsonPath("$.path").value("/api/users/123"));
    }

    @Test
    void findAllShouldReturnIsOk() throws Exception {
        final var entity1 = generateMock(User.class);
        final var entity2 = generateMock(User.class);

        userRepository.saveAll(List.of(entity1, entity2));

        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0]").isNotEmpty())
                .andExpect(jsonPath("$.[1]").isNotEmpty());

        userRepository.deleteAll(List.of(entity1, entity2));
    }

    @Test
    void saveShouldReturnCreated() throws Exception {
        final var validEmail = "validemail@email.com";
        final var request = generateMock(CreateUserRequest.class).withEmail(validEmail);
        final var json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        userRepository.findByEmail(validEmail).ifPresent(user -> userRepository.delete(user));
    }

    @Test
    void saveShouldReturnBadRequest() throws Exception {
        final var request = generateMock(CreateUserRequest.class).withEmail("newEmail.com");
        final var json = objectMapper.writeValueAsString(request);

        mockMvc.perform(
                post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value("Exception in validation attributes"))
                .andExpect(jsonPath("$.path").value(BASE_URI))
                .andExpect(jsonPath("$.errors[?(@.fieldName=='email' && @.message=='Invalid email')]").exists());

    }

    @Test
    void saveShouldReturnConflictWhenEmailAlreadyExists() throws Exception {
        final var existsEmail = "exists@email.com";
        final var requestExists = generateMock(CreateUserRequest.class).withEmail(existsEmail);
        User user = userRepository.save(userMapper.fromRequest(requestExists));

        final var newRequest = generateMock(CreateUserRequest.class).withEmail(existsEmail);
        final var json = objectMapper.writeValueAsString(newRequest);

        mockMvc.perform(
                post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.CONFLICT.getReasonPhrase()))
                .andExpect(jsonPath("$.message").value(String.format("Email [ %s ] already exists!", existsEmail)))
                .andExpect(jsonPath("$.path").value(BASE_URI));

        userRepository.delete(user);
    }
}