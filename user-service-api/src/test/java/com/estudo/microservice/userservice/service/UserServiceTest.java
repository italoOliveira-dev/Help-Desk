package com.estudo.microservice.userservice.service;

import com.estudo.microservice.userservice.entity.User;
import com.estudo.microservice.userservice.mapper.UserMapper;
import com.estudo.microservice.userservice.repository.UserRepository;
import models.exceptions.ResourceNotFoundException;
import models.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
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
}