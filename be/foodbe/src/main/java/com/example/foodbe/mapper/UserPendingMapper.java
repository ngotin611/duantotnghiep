package com.example.foodbe.mapper;

import com.example.foodbe.models.UserPending;
import com.example.foodbe.request.user.UserCreateDTO;
import org.springframework.stereotype.Component;

@Component
public class UserPendingMapper {
    public UserCreateDTO toUserDto(UserPending userPending){
        return UserCreateDTO.builder()
                .name(userPending.getName())
                .email(userPending.getEmail())
                .password(userPending.getPassword())
                .confirmPassword(userPending.getConfirmPassword())
                .phone(userPending.getPhone())
                .status(userPending.getStatus())
                .role(userPending.getRole())
                .build();
    }
}
