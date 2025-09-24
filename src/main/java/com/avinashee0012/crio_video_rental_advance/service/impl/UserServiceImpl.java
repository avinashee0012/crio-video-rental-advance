package com.avinashee0012.crio_video_rental_basic.service.impl;

import com.avinashee0012.crio_video_rental_basic.dto.LoginRequestDto;
import com.avinashee0012.crio_video_rental_basic.dto.RegisterRequestDto;
import com.avinashee0012.crio_video_rental_basic.dto.MessageResponseDto;
import com.avinashee0012.crio_video_rental_basic.entity.User;
import com.avinashee0012.crio_video_rental_basic.enums.Role;
import com.avinashee0012.crio_video_rental_basic.repository.UserRepo;
import com.avinashee0012.crio_video_rental_basic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public MessageResponseDto register(RegisterRequestDto request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();
        userRepo.save(user);
        return MessageResponseDto.builder()
                .message("User registered successfully")
                .build();
    }

    @Override
    public MessageResponseDto login(LoginRequestDto request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        return MessageResponseDto.builder()
                .message("User logged in successfully")
                .build();
    }

}
