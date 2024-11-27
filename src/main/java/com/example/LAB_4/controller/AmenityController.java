package com.example.LAB_4.controller;

import com.example.LAB_4.dto.AmenityDTO;
import com.example.LAB_4.entity.amenity.Amenity;
import com.example.LAB_4.repository.AmenityRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/amenity")
@Slf4j

public class AmenityController {

    private final AmenityRepo amenityRepo;

    @GetMapping("/all")
    public List<Amenity> getAllAmenities() {
        return amenityRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Amenity> getAmenityById(@PathVariable long id) {
        return amenityRepo.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public void addAmenity(@RequestBody AmenityDTO amenityDTO) {
        Amenity amenity = new Amenity(amenityDTO.getName(), amenityDTO.getPrice(), amenityDTO.getExtraAdult(), amenityDTO.getExtraChild(), amenityDTO.getDailyExpense());
        amenityRepo.save(amenity);
    }

    @DeleteMapping("/delete")
    public void deleteAmenity(@RequestParam long id) {
        amenityRepo.deleteById(id);
    }

    @PutMapping("/{id}")
    public void updateAmenity(@PathVariable long id, @RequestBody AmenityDTO amenityDTO) {
        Amenity amenity = amenityRepo.findById(id).orElseThrow();
        amenity.setName(amenityDTO.getName());
        amenity.setPrice(amenityDTO.getPrice());
        amenity.setExtraAdult(amenityDTO.getExtraAdult());
        amenity.setExtraChild(amenityDTO.getExtraChild());
        amenity.setDailyExpense(amenityDTO.getDailyExpense());
        amenityRepo.save(amenity);
    }
}
