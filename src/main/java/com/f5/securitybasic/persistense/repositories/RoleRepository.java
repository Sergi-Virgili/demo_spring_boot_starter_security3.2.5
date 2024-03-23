package com.f5.securitybasic.persistense.repositories;

import com.f5.securitybasic.persistense.entities.RoleEntity;
import com.f5.securitybasic.persistense.entities.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRoleEnum(RoleEnum rol);
}
