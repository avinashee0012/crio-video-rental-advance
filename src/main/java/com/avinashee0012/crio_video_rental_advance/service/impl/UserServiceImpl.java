package com.avinashee0012.crio_video_rental_advance.service.impl;

import com.avinashee0012.crio_video_rental_advance.dto.LoginRequestDto;
import com.avinashee0012.crio_video_rental_advance.dto.RegisterRequestDto;
import com.avinashee0012.crio_video_rental_advance.dto.MessageResponseDto;
import com.avinashee0012.crio_video_rental_advance.entity.User;
import com.avinashee0012.crio_video_rental_advance.enums.Role;
import com.avinashee0012.crio_video_rental_advance.repository.UserRepo;
import com.avinashee0012.crio_video_rental_advance.service.JwtService;
import com.avinashee0012.crio_video_rental_advance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

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
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            return MessageResponseDto.builder()
                    .message(jwtService.generateToken(request.getEmail()))
                    .build();
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

}
