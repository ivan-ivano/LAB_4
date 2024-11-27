package com.example.LAB_4.repository;

import com.example.LAB_4.entity.accommodation.cottagevillage.CottageVillage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CottageVillageRepo extends JpaRepository<CottageVillage, Long> {
}
