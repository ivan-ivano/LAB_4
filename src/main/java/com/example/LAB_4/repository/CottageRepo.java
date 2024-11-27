package com.example.LAB_4.repository;

import com.example.LAB_4.entity.accommodation.cottagevillage.Cottage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CottageRepo extends JpaRepository<Cottage, Long> {
}
