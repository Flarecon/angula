package com.example.angula.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * 8; // 8 hours
    private final String SECRET_KEY = "193d98f43d1a1066362e849ffe160c7e3b1525c324de7d9d2de46913fa33fb466487de9c6b8e7dad93befcb903b073f76da747a0f0add857dbd6b9b51446f9e6";
    private final SecretKey JWT_KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String username){
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
            .signWith(JWT_KEY, SignatureAlgorithm.HS512)
            .compact();
    }
    
    public String checkExpiryAndExtractUsername(String token) {
        if(token == null) return null;
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(JWT_KEY).build()
                .parseClaimsJws(token).getBody();
        if(claims.getExpiration().before(new Date())){
            return null;
        }
        return claims.getSubject();
    }
}
