package com.numeriquepro.thymeleaf_spring_jpa_security.service.impl;

import com.numeriquepro.thymeleaf_spring_jpa_security.dao.RoleRepository;
import com.numeriquepro.thymeleaf_spring_jpa_security.dao.UserRepository;
import com.numeriquepro.thymeleaf_spring_jpa_security.dto.UserDto;
import com.numeriquepro.thymeleaf_spring_jpa_security.entity.RoleEntity;
import com.numeriquepro.thymeleaf_spring_jpa_security.entity.UserEntity;
import com.numeriquepro.thymeleaf_spring_jpa_security.mapper.UserMapper;
import com.numeriquepro.thymeleaf_spring_jpa_security.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto findByEmail(String email) {
        return UserMapper.toUserDto(userRepository.findByEmail(email));
    }

    @Override
    public UserDto save(UserDto userDto) {
        userDto.setId(null);

        // Mapper de base (sans les rôles)
        UserEntity userEntity = UserMapper.toUserEntity(userDto);

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setCreatedAt(new Date());

        //  Traitement des rôles
        Set<String> roleNames = userDto.getRoles();
        if (roleNames == null || roleNames.isEmpty()) {
            roleNames = Set.of("CLIENT"); // valeur par défaut
        }

        Set<RoleEntity> roles = roleNames.stream()
                .map(name -> {
                    String fullName = "ROLE_" + name.toUpperCase();
                    return roleRepository.findByName(fullName)
                            .orElseGet(() -> {
                                // Si le rôle n'existe pas, on le crée
                                RoleEntity newRole = new RoleEntity();
                                newRole.setName(fullName);
                                return roleRepository.save(newRole);
                            });
                })
                .collect(Collectors.toSet());
        userEntity.setRoles(roles);

        // 5. Sauvegarde
        UserEntity saved = userRepository.save(userEntity);

        // 6. Retour DTO
        return UserMapper.toUserDto(saved);
    }

}
