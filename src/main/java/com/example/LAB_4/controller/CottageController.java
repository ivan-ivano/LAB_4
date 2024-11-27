package com.example.LAB_4.controller;

import com.example.LAB_4.dto.CottageDTO;
import com.example.LAB_4.entity.accommodation.cottagevillage.Cottage;
import com.example.LAB_4.entity.accommodation.cottagevillage.CottageVillage;
import com.example.LAB_4.entity.amenity.Amenity;
import com.example.LAB_4.repository.AmenityRepo;
import com.example.LAB_4.repository.CottageRepo;
import com.example.LAB_4.repository.CottageVillageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/cottages")
@RestController
@RequiredArgsConstructor
public class CottageController {

    private final CottageRepo cottageRepo;
    private final CottageVillageRepo cottageVillageRepo;
    private final AmenityRepo amenityRepo;

    @GetMapping("/all")
    public List<Cottage> getAllCottages() {
        return cottageRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cottage> getCottageById(@PathVariable long id) {
        return cottageRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public void createCottage(@RequestBody CottageDTO cottageDTO) {
        CottageVillage cottageVillage = cottageVillageRepo.findById(cottageDTO.getCottageVillageId())
                .orElseThrow(() -> new RuntimeException("CottageVillage not found"));
        Cottage cottage = new Cottage(
                cottageDTO.getName(),
                cottageDTO.getType(),
                cottageDTO.getPrice(),
                cottageDTO.getMaxAdult(),
                cottageDTO.getMaxChildren(),
                cottageDTO.getDailyExpense(),
                cottageVillage
        );
        cottageRepo.save(cottage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cottage> updateCottage(@PathVariable long id, @RequestBody Cottage cottageDetails) {
        return cottageRepo.findById(id)
                .map(cottage -> {
                    cottage.setName(cottageDetails.getName());
                    cottage.setType(cottageDetails.getType());
                    cottage.setPrice(cottageDetails.getPrice());
                    cottage.setMaxAdult(cottageDetails.getMaxAdult());
                    cottage.setMaxChildren(cottageDetails.getMaxChildren());
                    cottage.setDailyExpense(cottageDetails.getDailyExpense());
                    return ResponseEntity.ok(cottageRepo.save(cottage));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCottage(@PathVariable long id) {
        return cottageRepo.findById(id)
                .map(cottage -> {
                    cottageRepo.delete(cottage);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{cottageId}/amenities/amenityId")
    public ResponseEntity<String> addAmenityToCottage(@PathVariable long cottageId, @PathVariable long amenityId) {
        Cottage cottage = cottageRepo.findById(cottageId)
                .orElseThrow(() -> new RuntimeException("Cottage not found with ID: " + cottageId));
        Amenity amenity = amenityRepo.findById(amenityId)
                .orElseThrow(() -> new RuntimeException("Amenity not found with ID: " + amenityId));

        if (!cottage.getAmenities().contains(amenity)) {
            cottage.getAmenities().add(amenity);
            cottageRepo.save(cottage);
            return ResponseEntity.ok("Amenity added to the cottage successfully.");
        } else {
            return ResponseEntity.status(400).body("Amenity already exists in the cottage.");
        }
    }


}