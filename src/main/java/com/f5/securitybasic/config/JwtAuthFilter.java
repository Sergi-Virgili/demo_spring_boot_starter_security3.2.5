package com.f5.securitybasic.config;

import com.f5.securitybasic.persistense.entities.UserEntity;
import com.f5.securitybasic.services.AuthService;
import com.f5.securitybasic.services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    JWTService jwtService;

    @Autowired
    AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtains authorization header
        String authHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Obtains token from header
        String jwt = authHeader.split(" ")[1];

        // Obtains username from subject -> this validates token too
        String username = jwtService.extractUsername(jwt);

        UserEntity user = authService.findByUsername(username).get();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities()
        );
        // Set auth in Security Context
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Chain continue
         filterChain.doFilter(request,response);
    }
}
