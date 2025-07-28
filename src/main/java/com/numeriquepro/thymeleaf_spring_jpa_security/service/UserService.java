package com.numeriquepro.thymeleaf_spring_jpa_security.service;

import com.numeriquepro.thymeleaf_spring_jpa_security.dto.LoginDto;
import com.numeriquepro.thymeleaf_spring_jpa_security.dto.UserDto;

public interface UserService {

    UserDto findByEmail(String email);
    UserDto save(UserDto userDto);

}
