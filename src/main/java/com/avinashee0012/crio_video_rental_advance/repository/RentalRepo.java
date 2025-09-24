package com.avinashee0012.crio_video_rental_advance.repository;

import com.avinashee0012.crio_video_rental_advance.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentalRepo extends JpaRepository<Rental, Long> {
    Optional<Rental> findByUserId(Long userId);
    Optional<Rental> findByVideoId(Long videoId);
}
