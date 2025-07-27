package com.numeriquepro.thymeleaf_spring_jpa_security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto implements Serializable {

    private Long id;
    private String username;
    @Size(min = 6, message = "Minimum Password length is 6 characters")
    private String confirmPassword;
    private String password;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private String address;
    private String phone;
    private String role = "CLIENT";
    @NotEmpty
    @Email
    private String email;




}
