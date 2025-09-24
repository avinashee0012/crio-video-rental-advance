package com.avinashee0012.crio_video_rental_basic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoResponseDto {
    private Long id;
    private String title;
    private String director;
    private String genre;
}
