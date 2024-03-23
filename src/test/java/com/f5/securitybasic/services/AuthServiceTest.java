package com.f5.securitybasic.services;

import com.f5.securitybasic.controllers.AuthResponse;
import com.f5.securitybasic.controllers.RegisterRequest;
import com.f5.securitybasic.exceptions.UsernameExistingException;
import com.f5.securitybasic.persistense.entities.RoleEntity;
import com.f5.securitybasic.persistense.entities.RoleEnum;
import com.f5.securitybasic.persistense.entities.UserEntity;
import com.f5.securitybasic.persistense.repositories.AuthRepository;
import com.f5.securitybasic.persistense.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthRepository authRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest validRequest;
    private UserEntity userEntity;
    private RoleEntity role;

    @BeforeEach
    void setUp() {
        validRequest = new RegisterRequest("user", "password");
        role = new RoleEntity();
        role.setRoleEnum(RoleEnum.USER);
        userEntity = new UserEntity(validRequest.username(),
                passwordEncoder.encode(validRequest.password()),
                Set.of(role));

        when(roleRepository.findByRoleEnum(RoleEnum.USER)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(authRepository.findByUsername(anyString())).thenReturn(Optional.empty());
    }

    @Test
    void register_WithValidRequest_ReturnsAuthResponse() {
        AuthResponse response = authService.register(validRequest);

        assertNotNull(response);
        assertEquals("aqui va el toquen", response.token());

        verify(authRepository, times(1)).save(any(UserEntity.class));

    }

    @Test
    void register_WithExistingUsername_ThrowsUsernameExistingException() {
        when(authRepository.findByUsername(validRequest.username())).thenReturn(Optional.of(userEntity));


        assertThrows(UsernameExistingException.class, () -> {
            authService.register(validRequest);
        });

        verify(authRepository, never()).save(any(UserEntity.class));
    }
}
