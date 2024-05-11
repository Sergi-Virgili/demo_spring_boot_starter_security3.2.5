package com.f5.securitybasic.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generate(UserDetails user) {

        Map<String,Object> extraClaims = new HashMap<>();

        extraClaims.put("authorities", user.getAuthorities());

        Instant now = Instant.now();
        return Jwts.builder()
                .subject(user.getUsername())
                .claims(extraClaims)
                .header()
                .add("typ", "JWT")
                .and()
                .issuedAt(Date.from(now))

                //TODO in production implements EC Key
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Key generateKey() {
        byte[] key = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(key);
    }

    public String extractUsername(String token) {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token);

        String username = claims.getBody().getSubject();
        return username;
    }
}
