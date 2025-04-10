package br.com.estudo.microservice.helpdeskbff.controller.impl;

import br.com.estudo.microservice.helpdeskbff.controller.UserController;
import br.com.estudo.microservice.helpdeskbff.service.UserService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponse> findById(String id) {

        return ResponseEntity.ok(userService.findById(id));
    }

    @Override
    public ResponseEntity<Void> save(final CreateUserRequest createUserRequest) {
        userService.save(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @Override
    public ResponseEntity<List<UserResponse>> findAll() {

        return ResponseEntity.ok(userService.findAll());
    }

    @Override
    public ResponseEntity<UserResponse> update(final String id, final UpdateUserRequest updateUserRequest) {

        return ResponseEntity.ok(userService.update(id, updateUserRequest));
    }
}
