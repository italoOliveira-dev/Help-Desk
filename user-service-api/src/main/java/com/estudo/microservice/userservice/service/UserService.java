package com.estudo.microservice.userservice.service;

import com.estudo.microservice.userservice.entity.User;
import com.estudo.microservice.userservice.mapper.UserMapper;
import com.estudo.microservice.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse findById(final String id) {
        return userMapper.fromEntity(find(id));
    }

    public void save(CreateUserRequest request) {
        verifyIfEmailAlreadyExists(request.email(), null);
        repository.save(
                userMapper.fromRequest(request)
                        .withPassword(passwordEncoder.encode(request.email()))
        );
    }

    public List<UserResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(userMapper::fromEntity)
                .toList();
    }

    public UserResponse update(final String id, final UpdateUserRequest request) {
        User entity = find(id);
        verifyIfEmailAlreadyExists(request.email(), id);
        final var newEntity = userMapper.update(request, entity);
        final var password = request.password() != null ? passwordEncoder.encode(request.password()) : newEntity.getPassword();
        return userMapper.fromEntity(
                repository.save(newEntity).withPassword(password)
        );
    }

    private User find(final String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Object not found! Id: " + id + ", Type: " + User.class.getSimpleName()
        ));
    }

    private void verifyIfEmailAlreadyExists(final String email, final String id) {
        repository.findByEmail(email)
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new DataIntegrityViolationException(String.format("Email [ %s ] already exists!", email));
                });
    }
}
