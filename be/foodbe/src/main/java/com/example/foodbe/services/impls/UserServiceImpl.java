package com.example.foodbe.services.impls;

import com.example.foodbe.exception_handler.NotFoundException;
import com.example.foodbe.request.user.UserCreateDTO;
import com.example.foodbe.response.user.UserResponseDTO;
import com.example.foodbe.mapper.UserMapper;
import com.example.foodbe.models.AppUser;
import com.example.foodbe.repositories.UserRepository;
import com.example.foodbe.services.UserService;
import com.example.foodbe.utils.ConstantUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder bCryptPasswordEncoder;


    @Override
    public void deleteById(Long id) {
        AppUser appUser = userRepository.findById(id)
                .orElseThrow(()->new NotFoundException(ConstantUtils.ExceptionMessage.NOT_FOUND +id));
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponseDTO> findAll() {
        List<AppUser> users= userRepository.findAll();

        return users.stream().map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO findById(Long id) {
        AppUser user= userRepository.findById(id)
                .orElseThrow(()->new NotFoundException(ConstantUtils.ExceptionMessage.NOT_FOUND +id));
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDTO create(UserCreateDTO userCreateDTO) {
        if (userRepository.existsByEmail(userCreateDTO.getEmail())) {
            throw new NotFoundException(ConstantUtils.ExceptionMessage.NOT_FOUND +userCreateDTO.getEmail());
        }
        String newPass=  bCryptPasswordEncoder.encode(userCreateDTO.getPassword());
        userCreateDTO.setPassword(newPass);
        AppUser user= userMapper.toEntity(userCreateDTO);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public AppUser findByEmail(String email) {
        AppUser appUser= userRepository.findByEmail(email)
                .orElseThrow(() ->new NotFoundException(ConstantUtils.ExceptionMessage.NOT_FOUND +email));
        return appUser;
    }
    // optional dùng khi dữ liệu trả về có thể null( search,..)
    // trả về đúng và bắt execptin khi dữ liệu trả về phải có mới hoạt đọng tiếp
}
