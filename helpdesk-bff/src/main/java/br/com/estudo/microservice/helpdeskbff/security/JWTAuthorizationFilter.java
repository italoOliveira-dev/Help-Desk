package br.com.estudo.microservice.helpdeskbff.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.List;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JWTUtils jwtUtils;
    private final String[] publicRoutes;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtils jwtUtils, String[] publicRoutes) {
        super(authenticationManager);
        this.jwtUtils = jwtUtils;
        this.publicRoutes = publicRoutes;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isPublicRouter(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) {
            chain.doFilter(request, response);
            return;
        }

        if (authHeader.startsWith("Bearer ")) {
            try {
                UsernamePasswordAuthenticationToken auth = getAuthentication(request);
                if (auth != null) SecurityContextHolder.getContext().setAuthentication(auth);
            }catch (Exception e) {
                throw new RuntimeException("Erro ao autenticar o token", e);
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isPublicRouter(String requestURI) {
        for (String route : this.publicRoutes) {
            if (requestURI.startsWith(route)) return true;
        }
        return false;
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);

        String username = jwtUtils.getUsername(token);
        Claims claims = jwtUtils.getClaims(token);
        List<GrantedAuthority> authorities = jwtUtils.getAuthorities(claims);

        return username != null ? new UsernamePasswordAuthenticationToken(username, null, authorities) : null;
    }
}
