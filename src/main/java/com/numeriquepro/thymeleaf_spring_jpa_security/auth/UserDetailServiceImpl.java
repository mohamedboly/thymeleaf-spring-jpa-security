package com.numeriquepro.thymeleaf_spring_jpa_security.auth;

import com.numeriquepro.thymeleaf_spring_jpa_security.dto.UserDto;

import com.numeriquepro.thymeleaf_spring_jpa_security.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailServiceImpl(UserService userService ) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto userDto = userService.findByEmail(email);
        if(userDto != null) {
            return User.withUsername(userDto.getUsername())
                    .password(userDto.getPassword())
                    .roles(userDto.getRole().replaceFirst("^ROLE_", ""))
                    .build();
        }else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

    }
}
