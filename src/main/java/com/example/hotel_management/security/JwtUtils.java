package com.example.hotel_management.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Slf4j
public class JwtUtils {

    private static final long EXPIRATION_TIME = 100L * 60L * 60L * 24L * 30L * 6L;

    private SecretKey secretKey;

    @Value("${secret.jwt.string}")
    private String secretJwtString;

    @PostConstruct
    private void init() {
        byte[] encodedKey = secretJwtString.getBytes(StandardCharsets.UTF_8);
        this.secretKey = new SecretKeySpec(encodedKey, "HmacSHA256");
    }

    public String generateJwtToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getEmailFromJwtToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpried(token));
    }

    private boolean isTokenExpried(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
