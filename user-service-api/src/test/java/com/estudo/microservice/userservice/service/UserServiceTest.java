package com.estudo.microservice.userservice.service;

import com.estudo.microservice.userservice.entity.User;
import com.estudo.microservice.userservice.mapper.UserMapper;
import com.estudo.microservice.userservice.repository.UserRepository;
import models.exceptions.ResourceNotFoundException;
import models.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void findByIdShouldReturnUserResponseWhenIdExists() {
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(new User()));
        Mockito.when(userMapper.fromEntity(Mockito.any(User.class))).thenReturn(Mockito.mock(UserResponse.class));

        final var response = userService.findById("1");
        assertNotNull(response);
        assertEquals(UserResponse.class, response.getClass());
        Mockito.verify(userRepository, Mockito.times(1)).findById("1");
        Mockito.verify(userMapper, Mockito.times(1)).fromEntity(Mockito.any(User.class));
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findById("1"));
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(userMapper, Mockito.times(0)).fromEntity(Mockito.any(User.class));
    }

    @Test
    void findAllShouldReturnListOfUserResponse() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(new User(), new User()));
        Mockito.when(userMapper.fromEntity(Mockito.any(User.class))).thenReturn(Mockito.mock(UserResponse.class));

        final var response = userService.findAll();
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(UserResponse.class, response.get(0).getClass());

        Mockito.verify(userRepository, Mockito.times(1)).findAll();
        Mockito.verify(userMapper, Mockito.times(2)).fromEntity(Mockito.any(User.class));
    }
}