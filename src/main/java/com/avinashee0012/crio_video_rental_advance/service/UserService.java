package com.avinashee0012.crio_video_rental_advance.service;

import com.avinashee0012.crio_video_rental_advance.dto.LoginRequestDto;
import com.avinashee0012.crio_video_rental_advance.dto.RegisterRequestDto;
import com.avinashee0012.crio_video_rental_advance.dto.MessageResponseDto;

public interface UserService {
    MessageResponseDto register(RegisterRequestDto request);
    MessageResponseDto login(LoginRequestDto request);
}
