package com.example.LAB_4.controller;

import com.example.LAB_4.entity.accommodation.Accommodation;
import com.example.LAB_4.repository.AccommodationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/accommodations")
@RestController
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationRepo accommodationRepo;

    @GetMapping("/all")
    public List<Accommodation> getAllAccommodations() {
        return accommodationRepo.findAll();
    }

    @GetMapping()
    public ResponseEntity<Accommodation> getAccommodationById(@RequestBody long id) {
        return accommodationRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public Accommodation createAccommodation(@RequestBody Accommodation accommodation) {
        return accommodationRepo.save(accommodation);
    }

    @PutMapping("/update")
    public ResponseEntity<Accommodation> updateAccommodation(@RequestBody long id, @RequestBody Accommodation accommodationDetails) {
        return accommodationRepo.findById(id)
                .map(accommodation -> {
                    accommodation.setName(accommodationDetails.getName());
                    accommodation.setType(accommodationDetails.getType());
                    accommodation.setPrice(accommodationDetails.getPrice());
                    accommodation.setMaxAdult(accommodationDetails.getMaxAdult());
                    accommodation.setMaxChildren(accommodationDetails.getMaxChildren());
                    accommodation.setDailyExpense(accommodationDetails.getDailyExpense());
                    accommodation.setAmenities(accommodationDetails.getAmenities());
                    Accommodation updatedAccommodation = accommodationRepo.save(accommodation);
                    return ResponseEntity.ok(updatedAccommodation);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping()
    public ResponseEntity<Object> deleteAccommodation(@RequestBody long id) {
        return accommodationRepo.findById(id)
                .map(accommodation -> {
                    accommodationRepo.delete(accommodation);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}