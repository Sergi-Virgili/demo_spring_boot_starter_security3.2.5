package com.f5.securitybasic.config;

import com.f5.securitybasic.persistense.entities.PermissionEntity;
import com.f5.securitybasic.persistense.entities.RoleEntity;
import com.f5.securitybasic.persistense.entities.RoleEnum;
import com.f5.securitybasic.persistense.entities.UserEntity;
import com.f5.securitybasic.persistense.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DataSeeder {

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void seed() {
        PermissionEntity permissionRead = new PermissionEntity(1L, "READ");
        PermissionEntity permissionCreate = new PermissionEntity(2L, "CREATE");

        RoleEntity roleUser = new RoleEntity(1L, RoleEnum.USER, Set.of(permissionRead));
        RoleEntity roleAdmin = new RoleEntity(2L, RoleEnum.ADMIN, Set.of(permissionRead, permissionCreate));


        UserEntity userUser = new UserEntity(1L, "user_user","secret",true,
                true,true,true, Set.of(roleUser));

        UserEntity userAdmin = new UserEntity(2L, "user_admin", "secret", true,
                true, true, true, Set.of(roleAdmin));

        userRepository.saveAll(List.of(userUser, userAdmin));
    }
}
