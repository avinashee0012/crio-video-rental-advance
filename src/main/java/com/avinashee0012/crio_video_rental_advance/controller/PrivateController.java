package com.avinashee0012.crio_video_rental_advance.controller;

import com.avinashee0012.crio_video_rental_advance.dto.MessageResponseDto;
import com.avinashee0012.crio_video_rental_advance.dto.VideoRequestDto;
import com.avinashee0012.crio_video_rental_advance.dto.VideoResponseDto;
import com.avinashee0012.crio_video_rental_advance.entity.User;
import com.avinashee0012.crio_video_rental_advance.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/private")
public class PrivateController {
    @Autowired
    private VideoService videoService;

    // ############# COMMON ENDPOINTS #############

    @GetMapping("/videos")
    public ResponseEntity<List<VideoResponseDto>> listVideos() {
        return ResponseEntity.ok(videoService.getAvailableVideos());
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<VideoResponseDto> getVideoById(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.getVideoById(id));
    }

    // ############# CUSTOMER ENDPOINTS #############

    @GetMapping("/videos/{videoId}/rent")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<MessageResponseDto> rentVideo(@PathVariable Long videoId, @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(videoService.rentVideo(videoId, currentUser));
    }

    @GetMapping("/videos/{videoId}/return")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<MessageResponseDto> returnVideo(@PathVariable Long videoId, @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(videoService.returnVideo(videoId, currentUser));
    }

    // ############# ADMIN ENDPOINTS #############

    @PostMapping("/admin/videos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponseDto> addVideo(@RequestBody VideoRequestDto request) {
        return ResponseEntity.ok(videoService.addVideo(request));
    }

    @PutMapping("/admin/videos/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponseDto> updateVideo(@PathVariable Long id, @RequestBody VideoRequestDto request) {
        return ResponseEntity.ok(videoService.updateVideo(id, request));
    }

    @DeleteMapping("/admin/videos/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponseDto> deleteVideo(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.deleteVideo(id));
    }

}
