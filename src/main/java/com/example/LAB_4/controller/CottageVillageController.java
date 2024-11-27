package com.example.LAB_4.controller;

import com.example.LAB_4.dto.CottageVillageDTO;
import com.example.LAB_4.entity.accommodation.cottagevillage.CottageVillage;
import com.example.LAB_4.repository.CottageVillageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/cottage_villages")
@RestController
@RequiredArgsConstructor
public class CottageVillageController {

    private final CottageVillageRepo cottageVillageRepo;

    @GetMapping("/all")
    public List<CottageVillage> getAllCottageVillages() {
        return cottageVillageRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CottageVillage> getCottageVillageById(@PathVariable long id) {
        return cottageVillageRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public void createCottageVillage(@RequestBody CottageVillageDTO cottageVillageDTO) {
        CottageVillage cottageVillage = new CottageVillage(cottageVillageDTO.getName(), cottageVillageDTO.getCottages(), cottageVillageDTO.getAmenities());
        cottageVillageRepo.save(cottageVillage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CottageVillage> updateCottageVillage(@PathVariable long id, @RequestBody CottageVillage cottageVillageDetails) {
        return cottageVillageRepo.findById(id)
                .map(cottageVillage -> {
                    cottageVillage.setName(cottageVillageDetails.getName());
                    CottageVillage updatedCottageVillage = cottageVillageRepo.save(cottageVillage);
                    return ResponseEntity.ok(updatedCottageVillage);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCottageVillage(@PathVariable long id) {
        return cottageVillageRepo.findById(id)
                .map(cottageVillage -> {
                    cottageVillageRepo.delete(cottageVillage);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}