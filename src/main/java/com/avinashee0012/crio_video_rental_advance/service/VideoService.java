package com.avinashee0012.crio_video_rental_basic.service;

import com.avinashee0012.crio_video_rental_basic.dto.MessageResponseDto;
import com.avinashee0012.crio_video_rental_basic.dto.VideoRequestDto;
import com.avinashee0012.crio_video_rental_basic.dto.VideoResponseDto;

import java.util.List;

public interface VideoService {
    MessageResponseDto addVideo(VideoRequestDto request);
    MessageResponseDto updateVideo(Long id, VideoRequestDto request);
    MessageResponseDto deleteVideo(Long id);

    List<VideoResponseDto> getAvailableVideos();
    VideoResponseDto getVideoById(Long id);
    MessageResponseDto rentVideo();
}
