package com.example.foodbe.services;

import com.example.foodbe.models.AppUser;
import com.example.foodbe.request.user.UserCreateDTO;
import com.example.foodbe.response.user.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponseDTO create(UserCreateDTO userCreateDTO);
    void deleteById(Long id);
    UserResponseDTO findById(Long id);
    List<UserResponseDTO> findAll();
    AppUser findByEmail(String email);
}