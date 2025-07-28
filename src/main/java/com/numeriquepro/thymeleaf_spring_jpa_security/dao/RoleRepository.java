package com.numeriquepro.thymeleaf_spring_jpa_security.dao;

import com.numeriquepro.thymeleaf_spring_jpa_security.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}
