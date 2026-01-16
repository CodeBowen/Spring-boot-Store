package com.codewithmosh.store.auth;

import com.codewithmosh.store.users.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;

    public Jwt generateAccessTokenToken(User user) {
        return generateToken(user, jwtConfig.getAccessTokenExpirationInSeconds());
    }

    public Jwt generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpirationInSeconds());
    }

    public Jwt parseToken(String token) {
        try {
            var claims = Jwts.parser()
                    .verifyWith(jwtConfig.getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            var secretKey = jwtConfig.getSecretKey();
            return new Jwt(claims, secretKey);
        }
        catch (JwtException e) {
            return null;
        }
    }

    private Jwt generateToken(User user, long tokenExpirationInSeconds) {
        var claims = Jwts.claims()
                .subject(user.getId().toString())
                .add("email", user.getEmail())
                .add("name", user.getName())
                .add("role", user.getRole())
                .issuedAt(Date.from(LocalDateTime.now().atZone(TimeZone.getDefault().toZoneId()).toInstant()))
                .expiration(Date.from(LocalDateTime.now().atZone(TimeZone.getDefault().toZoneId()).toInstant().plusSeconds(tokenExpirationInSeconds)))
                .build();

        return new Jwt(claims, jwtConfig.getSecretKey());
    }
}
