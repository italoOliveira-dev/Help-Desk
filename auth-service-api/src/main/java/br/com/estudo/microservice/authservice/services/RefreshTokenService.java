package br.com.estudo.microservice.authservice.services;

import br.com.estudo.microservice.authservice.models.RefreshToken;
import br.com.estudo.microservice.authservice.repositories.RefreshTokenRepository;
import br.com.estudo.microservice.authservice.security.dtos.UserDetailsDTO;
import br.com.estudo.microservice.authservice.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import models.exceptions.RefreshTokenExpiredException;
import models.exceptions.ResourceNotFoundException;
import models.responses.RefreshTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.expiration-sec.refresh-token}")
    private Long expirationSec;

    private final RefreshTokenRepository repository;
    private final UserDetailsServiceImpl userDetailsService;
    private final JWTUtils jwtUtils;

    public RefreshToken save(final String username) {
        return repository.save(RefreshToken.builder()
                .id(UUID.randomUUID().toString())
                .username(username)
                .createdAt(now())
                .expiresAt(now().plusSeconds(expirationSec))
                .build());
    }

    public RefreshTokenResponse refreshToken(final String refreshTokenId) {
        final var refreshToken = repository.findById(refreshTokenId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("RefreshToken id: %s not found", refreshTokenId)));

        if(refreshToken.getExpiresAt().isBefore(now())) {
            throw new RefreshTokenExpiredException("Refresh token expired. Id: " + refreshTokenId);
        }

        return new RefreshTokenResponse(jwtUtils.generateToken((UserDetailsDTO) userDetailsService.loadUserByUsername(refreshToken.getUsername())));
    }
}
