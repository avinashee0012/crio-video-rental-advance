package com.avinashee0012.crio_video_rental_basic.repository;

import com.avinashee0012.crio_video_rental_basic.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepo extends JpaRepository<Video, Long> {
    List<Video> findByIsAvailableTrue();
}
