package br.com.estudo.microservice.helpdeskbff.config;

import br.com.estudo.microservice.helpdeskbff.security.JWTAuthorizationFilter;
import br.com.estudo.microservice.helpdeskbff.security.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfig;
    private final JWTUtils jwtUtils;

    public static final String[] SWAGGER_WHITELIST = {"/swagger-ui/index.html", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/webjars/**"};
    public static final String[] POST_WHITELIST = {"/api/auth/login", "/api/auth/refresh-token"};
    public static final String[] PUBLIC_ROUTES = {"/api/auth/login", "/api/auth/refresh-token", "/swagger-ui/index.html", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/webjars/**"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .addFilterBefore(
                        new JWTAuthorizationFilter(authenticationConfig.getAuthenticationManager(), jwtUtils, PUBLIC_ROUTES),
                        JWTAuthorizationFilter.class
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()
                        .requestMatchers(POST_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }
}
