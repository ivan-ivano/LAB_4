package com.example.LAB_4.repository;

import com.example.LAB_4.entity.amenity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface AmenityRepo extends JpaRepository<Amenity, Long>, JpaSpecificationExecutor<Amenity> {
}
