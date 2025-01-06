package com.estudo.microservice.userservice.controller.impl;

import com.estudo.microservice.userservice.controller.UserController;
import com.estudo.microservice.userservice.entity.User;
import com.estudo.microservice.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<User> findById(String id) {
        return ResponseEntity.ok(userService.findById(id));
    }
}
