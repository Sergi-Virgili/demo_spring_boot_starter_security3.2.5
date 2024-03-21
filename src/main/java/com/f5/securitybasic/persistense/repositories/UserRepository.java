package com.f5.securitybasic.persistense.repositories;

import com.f5.securitybasic.persistense.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
