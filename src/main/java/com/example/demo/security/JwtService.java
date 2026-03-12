package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Value("${security.jwt.secret}")
    private String secret;
    @Value("${security.jwt.expiration-ms:3600000}")
    private long expirationMs;

    public String generateToken(Long userId, String role) {
        return Jwts.builder().subject(userId.toString()).claim("role", role)
                .issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key()).compact();
    }
    public Claims parse(String token) { return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload(); }
    public Long extractUserId(String token) { return Long.valueOf(parse(token).getSubject()); }
    private SecretKey key(){ return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)); }
}
