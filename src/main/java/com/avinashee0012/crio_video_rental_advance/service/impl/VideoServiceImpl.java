package com.avinashee0012.crio_video_rental_advance.service.impl;

import com.avinashee0012.crio_video_rental_advance.dto.MessageResponseDto;
import com.avinashee0012.crio_video_rental_advance.dto.VideoRequestDto;
import com.avinashee0012.crio_video_rental_advance.dto.VideoResponseDto;
import com.avinashee0012.crio_video_rental_advance.entity.Rental;
import com.avinashee0012.crio_video_rental_advance.entity.User;
import com.avinashee0012.crio_video_rental_advance.entity.Video;
import com.avinashee0012.crio_video_rental_advance.repository.RentalRepo;
import com.avinashee0012.crio_video_rental_advance.repository.UserRepo;
import com.avinashee0012.crio_video_rental_advance.repository.VideoRepo;
import com.avinashee0012.crio_video_rental_advance.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private VideoRepo videoRepo;
    @Autowired
    private RentalRepo rentalRepo;

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
    public MessageResponseDto rentVideo(Long videoId, User currentUser) {
        // Fetch video by ID
        Video video = videoRepo.findById(videoId).orElse(null);
        if (video == null) {
            return MessageResponseDto.builder()
                    .message("Video not found with ID: " + videoId)
                    .build();
        }
        // Check if video is available and user has less than 3 rentals
        if (video.isAvailable() && currentUser.getRentals().size() < 2) {
            // Update video availability to false
            video.setAvailable(false);
            // Create a rental record associating the video with the user
            Rental rental = Rental.builder()
                    .video(video)
                    .user(currentUser)
                    .rentalDate(LocalDate.now())
                    .build();
            // save changes
            videoRepo.save(video);
            userRepo.save(currentUser);
            rentalRepo.save(rental);
            // return success message
            return MessageResponseDto.builder()
                    .message("Video '" + video.getTitle() + "' rented successfully.")
                    .build();
        }
        return MessageResponseDto.builder()
                .message("Video is currently not available for rent.")
                .build();
    }

    @Override
    public MessageResponseDto returnVideo(Long videoId, User currentUser) {
        // Fetch video by ID
        Video video = videoRepo.findById(videoId).orElse(null);
        if (video == null) {
            return MessageResponseDto.builder()
                    .message("Video not found with ID: " + videoId)
                    .build();
        }
        // Remove the rental record associating the video with the user
        Rental rental = rentalRepo.findByVideoId(videoId).orElse(null);
        if(rental != null) {
            rental.setReturned(true);
            rental.setReturnDate(LocalDate.now());
            video.setAvailable(true);
            // save changes
            videoRepo.save(video);
            rentalRepo.save(rental);
            return MessageResponseDto.builder()
                    .message("Video '" + video.getTitle() + "' returned successfully.")
                    .build();
        }
        return MessageResponseDto.builder()
                .message("Video " + video.getTitle() + " not rented by user.")
                .build();
    }
}
