package com.avinashee0012.crio_video_rental_basic.controller;

import com.avinashee0012.crio_video_rental_basic.dto.LoginRequestDto;
import com.avinashee0012.crio_video_rental_basic.dto.RegisterRequestDto;
import com.avinashee0012.crio_video_rental_basic.dto.MessageResponseDto;
import com.avinashee0012.crio_video_rental_basic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<MessageResponseDto> register(@RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponseDto> login(@RequestHeader("Authorization") String authHeader) {
        String base64Credentials = authHeader.substring("Basic ".length()).trim();
        byte[] credDecoded = java.util.Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, java.nio.charset.StandardCharsets.UTF_8);
        String[] values = credentials.split(":", 2);
        LoginRequestDto request = new LoginRequestDto(values[0], values[1]);
        return ResponseEntity.ok(userService.login(request));
    }
}
