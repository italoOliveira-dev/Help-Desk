package com.estudo.microservice.userservice.service;

import com.estudo.microservice.userservice.entity.User;
import com.estudo.microservice.userservice.mapper.UserMapper;
import com.estudo.microservice.userservice.repository.UserRepository;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.estudo.microservice.userservice.creator.CreatorUtils.generateMock;
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
        Mockito.when(userMapper.fromEntity(Mockito.any(User.class))).thenReturn(generateMock(UserResponse.class));

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

    @Test
    void saveShouldSuccessfullySaveUser() {
        final var request = generateMock(CreateUserRequest.class);
        Mockito.when(userMapper.fromRequest(request)).thenReturn(new User());
        Mockito.when(passwordEncoder.encode(request.password())).thenReturn("encoded");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        userService.save(request);

        Mockito.verify(userMapper).fromRequest(request);
        Mockito.verify(passwordEncoder).encode(request.password());
        Mockito.verify(userRepository).save(Mockito.any(User.class));
        Mockito.verify(userRepository).findByEmail(request.email());
    }

    @Test
    void saveShouldThrowDataIntegrityViolationExceptionWhenEmailAlreadyExists() {
        final var request = generateMock(CreateUserRequest.class);
        final var entity = generateMock(User.class);
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(entity));

        assertThrows(DataIntegrityViolationException.class, () -> {
            userService.save(request);
        });

        Mockito.verify(userRepository).findByEmail(request.email());
        Mockito.verify(userMapper, Mockito.times(0)).fromRequest(request);
        Mockito.verify(passwordEncoder, Mockito.times(0)).encode(request.password());
        Mockito.verify(userRepository, Mockito.times(0)).save(Mockito.any(User.class));
    }
}