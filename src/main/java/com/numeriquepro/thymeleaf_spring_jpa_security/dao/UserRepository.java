package com.numeriquepro.thymeleaf_spring_jpa_security.dao;

import com.numeriquepro.thymeleaf_spring_jpa_security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

}