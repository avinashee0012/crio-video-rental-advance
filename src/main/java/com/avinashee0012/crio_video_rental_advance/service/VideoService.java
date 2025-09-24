package com.avinashee0012.crio_video_rental_advance.service;

import com.avinashee0012.crio_video_rental_advance.dto.MessageResponseDto;
import com.avinashee0012.crio_video_rental_advance.dto.VideoRequestDto;
import com.avinashee0012.crio_video_rental_advance.dto.VideoResponseDto;

import java.util.List;

public interface VideoService {
    MessageResponseDto addVideo(VideoRequestDto request);
    MessageResponseDto updateVideo(Long id, VideoRequestDto request);
    MessageResponseDto deleteVideo(Long id);

    List<VideoResponseDto> getAvailableVideos();
    VideoResponseDto getVideoById(Long id);
    MessageResponseDto rentVideo();
}
