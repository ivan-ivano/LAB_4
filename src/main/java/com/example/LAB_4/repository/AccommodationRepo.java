package com.example.LAB_4.repository;

import com.example.LAB_4.entity.accommodation.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepo extends JpaRepository<Accommodation, Long> {
}
