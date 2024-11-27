package com.example.LAB_4.repository;

import com.example.LAB_4.entity.accommodation.hotel.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room, Long> {
}
