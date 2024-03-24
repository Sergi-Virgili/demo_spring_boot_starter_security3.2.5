package com.f5.securitybasic.services;

import com.f5.securitybasic.controllers.AuthResponse;
import com.f5.securitybasic.controllers.RegisterRequest;
import com.f5.securitybasic.exceptions.UsernameExistingException;
import com.f5.securitybasic.persistense.entities.RoleEnum;
import com.f5.securitybasic.persistense.entities.UserEntity;
import com.f5.securitybasic.persistense.repositories.AuthRepository;
import com.f5.securitybasic.persistense.repositories.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final RoleRepository roleRepository;

    private final JWTService jwtService;

    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthRepository authRepository, RoleRepository repository, JWTService jwtService, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.roleRepository = repository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(RegisterRequest request) {
        UserEntity user = new UserEntity(request.username(),
                passwordEncoder.encode(request.password()),
                Set.of(roleRepository.findByRoleEnum(RoleEnum.USER)
                        .orElseThrow(() -> new RuntimeException("role doesnt exist")))
                );

        if (authRepository.findByUsername(request.username()).isPresent()) throw new UsernameExistingException("username exists");

        var createdUser = authRepository.save(user);

        String token = jwtService.generate(createdUser);

        return new AuthResponse(token, user.getUsername(), user.getRoles().stream().map(role -> role.getRoleEnum().name()).toList());
    }

}
