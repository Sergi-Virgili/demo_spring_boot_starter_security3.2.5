package com.f5.securitybasic.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Service
public class JWTService {

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generate(UserDetails user) {

        Instant now = Instant.now();
        return Jwts.builder()
                .subject(user.getUsername())
                .header()
                .add("typ", "JWT")
                .and()
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(3600)))
                //TODO in production implements EC Key
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Key generateKey() {
        byte[] key = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(key);
    }
}
