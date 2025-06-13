package com.sidyenni.chatapp.utils;

import com.sidyenni.chatapp.exceptions.InvalidTokenException;
import com.sidyenni.chatapp.exceptions.TokenValidationFailedException;
import com.sidyenni.chatapp.models.User;
import com.sidyenni.chatapp.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long jwtExpirationMs = 86400000; // 1 day
    private final UserRepository userRepository;

    public JwtUtil(SecretKey secretKey, UserRepository userRepository) {
        this.secretKey = secretKey;
        this.userRepository = userRepository;
    }

    public String generateToken(Long userId, String username) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public Boolean validateToken(String token) throws InvalidTokenException, TokenValidationFailedException {

        if (token == null || token.isBlank()) {
            throw new InvalidTokenException("Invalid token");
        }

        String username = getUsernameFromToken(token);

        if (username == null || username.isBlank()) {
            throw new TokenValidationFailedException("Token Validation Failed. Email is null or blank");
        }

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty() || userOptional.get().isDeleted()) {
            throw new TokenValidationFailedException("Token Validation Failed. User not found or deleted");
        }

        return true;
    }

    private String getUsernameFromToken(String token) throws InvalidTokenException {

        Claims claims;

        try {
            claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token");
        }

        return claims.getSubject();
    }


}
