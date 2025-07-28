package com.numeriquepro.thymeleaf_spring_jpa_security.service.impl;

import com.numeriquepro.thymeleaf_spring_jpa_security.dao.UserRepository;
import com.numeriquepro.thymeleaf_spring_jpa_security.dto.LoginDto;
import com.numeriquepro.thymeleaf_spring_jpa_security.dto.UserDto;
import com.numeriquepro.thymeleaf_spring_jpa_security.entity.UserEntity;
import com.numeriquepro.thymeleaf_spring_jpa_security.mapper.UserMapper;
import com.numeriquepro.thymeleaf_spring_jpa_security.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public UserDto findByEmail(String email) {
        return UserMapper.toUserDto(userRepository.findByEmail(email));
    }

    @Override
    public UserDto save(UserDto userDto) {
        userDto.setId(null); // Ensure new user has no ID
        UserEntity userEntity = UserMapper.toUserEntity(userDto);


        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setCreatedAt(new Date());
        if (userEntity.getRole() == null) {
            userEntity.setRole("CLIENT");
        }


        return UserMapper.toUserDto(userRepository.save(userEntity));
    }


}
