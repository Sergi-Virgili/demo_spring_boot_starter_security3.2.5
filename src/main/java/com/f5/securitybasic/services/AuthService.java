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

    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthRepository authRepository, RoleRepository repository, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.roleRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(RegisterRequest request) {
        UserEntity user = new UserEntity(request.username(),
                passwordEncoder.encode(request.password()),
                Set.of(roleRepository.findByRoleEnum(RoleEnum.USER)
                        .orElseThrow(() -> new RuntimeException("role doesnt exist")))
                );

        if (authRepository.findByUsername(request.username()).isPresent()) throw new UsernameExistingException("username exists");
        authRepository.save(user);

        return new AuthResponse("aqui va el toquen");
    }

}
