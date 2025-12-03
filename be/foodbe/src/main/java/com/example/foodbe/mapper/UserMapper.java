package com.example.foodbe.mapper;


import com.example.foodbe.request.user.UserCreateDTO;
import com.example.foodbe.response.user.UserResponseDTO;
import com.example.foodbe.models.AppUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public AppUser toEntity(UserCreateDTO userCreateDTO){
        return AppUser.builder()
                .name(userCreateDTO.getName())
                .age(userCreateDTO.getAge())
                .email(userCreateDTO.getEmail())
                .password(userCreateDTO.getPassword())
                .address(userCreateDTO.getAddress())
                .phone(userCreateDTO.getPhone())
                .role(userCreateDTO.getRole())
                .build();
    }

    public UserResponseDTO toDto(AppUser user){
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .email(user.getEmail())
                .address(user.getAddress())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }
}
