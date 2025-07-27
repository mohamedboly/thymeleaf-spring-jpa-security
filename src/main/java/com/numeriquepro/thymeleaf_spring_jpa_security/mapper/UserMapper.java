package com.numeriquepro.thymeleaf_spring_jpa_security.mapper;


import com.numeriquepro.thymeleaf_spring_jpa_security.dto.UserDto;
import com.numeriquepro.thymeleaf_spring_jpa_security.entity.UserEntity;

import java.util.List;


public interface UserMapper {
    static UserEntity toUserEntity(UserDto userDto){
        if (userDto == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setUsername(userDto.getEmail()); // Assuming username is the same as email
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setRole(userDto.getRole());

        return userEntity;
    }
    static  UserDto toUserDto(UserEntity userEntity){
        if (userEntity == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setUsername(userEntity.getEmail());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        userDto.setRole(userEntity.getRole());

        return userDto;
    }
    static List<UserEntity> toListUserEntity(List<UserDto> UserDtoList){
        if (UserDtoList == null) {
            return null;
        }
        return UserDtoList.stream()
                .map(UserMapper::toUserEntity)
                .toList();
    }
    static List<UserDto> toListUserDto(List<UserEntity> UserEntityList){
        if (UserEntityList == null) {
            return null;
        }
        return UserEntityList.stream()
                .map(UserMapper::toUserDto)
                .toList();
    }
}