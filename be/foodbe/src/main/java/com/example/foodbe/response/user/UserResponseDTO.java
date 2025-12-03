package com.example.foodbe.response.user;

import com.example.foodbe.models.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String name;
    private int age;
    private String email;
    private String address;
    private String phone;
    private Role role;
}
