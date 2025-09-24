package com.avinashee0012.crio_video_rental_basic.service.impl;

import com.avinashee0012.crio_video_rental_basic.dto.MessageResponseDto;
import com.avinashee0012.crio_video_rental_basic.dto.VideoRequestDto;
import com.avinashee0012.crio_video_rental_basic.dto.VideoResponseDto;
import com.avinashee0012.crio_video_rental_basic.entity.Video;
import com.avinashee0012.crio_video_rental_basic.repository.UserRepo;
import com.avinashee0012.crio_video_rental_basic.repository.VideoRepo;
import com.avinashee0012.crio_video_rental_basic.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private VideoRepo videoRepo;

    @Override
    public MessageResponseDto addVideo(VideoRequestDto request) {
        Video video = Video.builder()
                .title(request.getTitle())
                .director(request.getDirector())
                .genre(request.getGenre())
                .isAvailable(request.isAvailable())
                .build();
        Video savedVideo = videoRepo.save(video);
        return MessageResponseDto.builder()
                .message("Video added successfully with ID: " + savedVideo.getId())
                .build();
    }

    @Override
    public MessageResponseDto updateVideo(Long id, VideoRequestDto request) {
        Video video = videoRepo.findById(id).orElse(null);
        if (video != null) {
            video.setTitle(request.getTitle());
            video.setDirector(request.getDirector());
            video.setGenre(request.getGenre());
            video.setAvailable(request.isAvailable());

            videoRepo.save(video);

            return MessageResponseDto.builder()
                    .message("Video updated successfully with ID: " + video.getId())
                    .build();
        }
        return MessageResponseDto.builder()
                .message("Video update failed!")
                .build();
    }

    @Override
    public MessageResponseDto deleteVideo(Long id) {
        Video video = videoRepo.findById(id).orElse(null);
        if (video == null) {
            return MessageResponseDto.builder()
                    .message("Video deletion failed! Video not found with ID: " + id)
                    .build();
        }
        videoRepo.deleteById(id);
        return MessageResponseDto.builder()
                .message("Video deleted successfully with ID: " + id)
                .build();
    }

    @Override
    public List<VideoResponseDto> getAvailableVideos() {
        List<Video> videos = videoRepo.findByIsAvailableTrue();
        if (videos != null && !videos.isEmpty()) {
            return videos.stream()
                    .map(video -> VideoResponseDto.builder()
                        .id(video.getId())
                        .title(video.getTitle())
                        .director(video.getDirector())
                        .genre(video.getGenre())
                        .build())
                    .toList();
        }
        return new ArrayList<>();
    }

    @Override
    public VideoResponseDto getVideoById(Long id) {
        Video video = videoRepo.findById(id).orElse(null);
        if (video != null) {
            return VideoResponseDto.builder()
                    .id(video.getId())
                    .title(video.getTitle())
                    .director(video.getDirector())
                    .genre(video.getGenre())
                    .build();
        }
        return null;
    }

    @Override
    public MessageResponseDto rentVideo() {
        return MessageResponseDto.builder()
                .message("Video rentals coming soon!")
                .build();
    }
}
