package com.avinashee0012.crio_video_rental_basic.service;

import com.avinashee0012.crio_video_rental_basic.dto.LoginRequestDto;
import com.avinashee0012.crio_video_rental_basic.dto.RegisterRequestDto;
import com.avinashee0012.crio_video_rental_basic.dto.MessageResponseDto;

public interface UserService {
    MessageResponseDto register(RegisterRequestDto request);
    MessageResponseDto login(LoginRequestDto request);
}
