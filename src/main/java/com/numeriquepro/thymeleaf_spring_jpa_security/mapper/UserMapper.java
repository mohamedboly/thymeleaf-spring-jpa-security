package com.numeriquepro.thymeleaf_spring_jpa_security.mapper;


import com.numeriquepro.thymeleaf_spring_jpa_security.dto.UserDto;
import com.numeriquepro.thymeleaf_spring_jpa_security.entity.RoleEntity;
import com.numeriquepro.thymeleaf_spring_jpa_security.entity.UserEntity;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public interface UserMapper {

    // Mapping DTO → Entity (sans gérer les relations complexes comme les rôles liés à une DB)
    static UserEntity toUserEntity(UserDto userDto){
        if (userDto == null) return null;

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setUsername(userDto.getEmail()); // Email = username
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setAddress(userDto.getAddress());
        userEntity.setPhone(userDto.getPhone());

        // ATTENTION : ici on ne doit pas créer RoleEntity avec id null.
        // On laisse la logique de rôle au service (ex: UserServiceImpl)
        userEntity.setRoles(new HashSet<>());

        return userEntity;
    }

    // Mapping Entity → DTO
    static UserDto toUserDto(UserEntity userEntity){
        if (userEntity == null) return null;

        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setUsername(userEntity.getEmail());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        userDto.setAddress(userEntity.getAddress());
        userDto.setPhone(userEntity.getPhone());

        if (userEntity.getRoles() != null) {
            userDto.setRoles(userEntity.getRoles().stream()
                    .map(RoleEntity::getName)
                    .map(name -> name.replaceFirst("^ROLE_", "")) // remove "ROLE_" prefix for display
                    .collect(Collectors.toSet()));
        }

        return userDto;
    }

    static List<UserEntity> toListUserEntity(List<UserDto> UserDtoList){
        if (UserDtoList == null) return null;
        return UserDtoList.stream().map(UserMapper::toUserEntity).toList();
    }

    static List<UserDto> toListUserDto(List<UserEntity> UserEntityList){
        if (UserEntityList == null) return null;
        return UserEntityList.stream().map(UserMapper::toUserDto).toList();
    }
}
