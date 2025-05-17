package br.com.estudo.microservice.helpdeskbff.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String secret;

    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject() != null ? claims.getSubject() : null;
    }

    public List<GrantedAuthority> getAuthorities(Claims claims) {
        if (claims.get("authorities") != null) {

            @SuppressWarnings("unchecked")
           var authorities = (List<Map<String, String>>) claims.get("authorities");

           return authorities.stream()
                   .map(authority -> (GrantedAuthority) () -> authority.get("authority"))
                   .toList();
        }

        throw new RuntimeException("Authorities not found");
    }
}
