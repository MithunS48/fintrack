package com.fintrack.fintrack.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import java.util.Date;

@Service

public class JWTService {

    @Value("${jwt.secret}")
    private String secretKey;
    protected SecretKey getSecretKey()
    {
        byte[] key=secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(key);
    }

    protected String generateToken(UserDetails userDetails)
    {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date() )
                .expiration(new Date(System.currentTimeMillis()+1000*60*15))
                .signWith(getSecretKey())
                .compact();
    }

    public boolean isExpiry(String token)
    {
        return extractToken(token).getExpiration().before(new Date());
    }

    public String extractSubject(String token)
    {
        return extractToken(token).getSubject();
    }

    public boolean isValidToken(String token,UserDetails userDetails)
    {
        String userName=extractSubject(token);
        return userName.equals(userDetails.getUsername()) && !isExpiry(token);
    }






    public Claims extractToken(String token)
    {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
