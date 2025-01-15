package br.com.estudo.microservice.authservice.controllers.impl;

import br.com.estudo.microservice.authservice.controllers.AuthController;
import br.com.estudo.microservice.authservice.security.JWTAuthenticationImpl;
import br.com.estudo.microservice.authservice.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final JWTUtils jwtUtils;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(final AuthenticateRequest request) throws Exception {
        return ResponseEntity.ok(
                new JWTAuthenticationImpl(jwtUtils, authenticationConfiguration.getAuthenticationManager()).authenticate(request)
        );
    }
}
