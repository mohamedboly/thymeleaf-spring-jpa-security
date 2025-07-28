package com.numeriquepro.thymeleaf_spring_jpa_security.auth;

import com.numeriquepro.thymeleaf_spring_jpa_security.dto.UserDto;

import com.numeriquepro.thymeleaf_spring_jpa_security.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import  org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailServiceImpl(UserService userService ) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto userDto = userService.findByEmail(email);

        if (userDto == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }


        Set<GrantedAuthority> authorities = userDto.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role)) // ou role si c'est déjà une string
                .collect(Collectors.toSet());

        return new User(
                userDto.getUsername(),
                userDto.getPassword(),
                authorities
        );
    }

}
