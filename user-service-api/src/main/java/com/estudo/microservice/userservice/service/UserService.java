package com.estudo.microservice.userservice.service;

import com.estudo.microservice.userservice.entity.User;
import com.estudo.microservice.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(final String id) {
        return userRepository.findById(id).orElse(null);
    }
}
