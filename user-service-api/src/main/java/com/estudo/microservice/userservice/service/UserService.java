package com.estudo.microservice.userservice.service;

import com.estudo.microservice.userservice.mapper.UserMapper;
import com.estudo.microservice.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.responses.UserResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse findById(final String id) {
        return userMapper.fromEntity(
                userRepository.findById(id).orElse(null)
        );
    }
}
