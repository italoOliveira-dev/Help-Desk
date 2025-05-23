package br.com.estudo.microservice.authservice.security;

import br.com.estudo.microservice.authservice.security.dtos.UserDetailsDTO;
import br.com.estudo.microservice.authservice.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Log4j2
@RequiredArgsConstructor
public class JWTAuthenticationImpl {

    private final JWTUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(final AuthenticateRequest request) {
        try {
            log.info("Authenticating user: {}", request.email());
            final var authResult = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            return buildAuthenticationResponse((UserDetailsDTO) authResult.getPrincipal());
        } catch (BadCredentialsException ex) {
            log.error("Error on authenticate user: {}", request.email());
            throw new BadCredentialsException("Email or password invalid");
        }
    }

    protected  AuthenticationResponse buildAuthenticationResponse(final UserDetailsDTO dto) {
        log.info("Successfully authenticated user: {}", dto.getUsername());
        final var token = jwtUtils.generateToken(dto);
        return AuthenticationResponse.builder()
                .type("Bearer")
                .token(token)
                .build();
    }
}
