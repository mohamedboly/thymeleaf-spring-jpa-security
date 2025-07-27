package com.numeriquepro.thymeleaf_spring_jpa_security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String lastName;
    private String firstName;
    private String address;
    private String phone;
    @Column(unique = true, nullable = false)
    private String email;
    private String role; // e.g., "ROLE_USER", "ROLE_ADMIN"
    private Date createdAt; // to store the date when the user was created
}
