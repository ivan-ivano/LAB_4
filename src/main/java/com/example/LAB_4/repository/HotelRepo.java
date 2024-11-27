package com.example.LAB_4.repository;

import com.example.LAB_4.entity.accommodation.hotel.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepo extends JpaRepository<Hotel, Long> {
}
