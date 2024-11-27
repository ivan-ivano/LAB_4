package com.example.LAB_4.controller;

import com.example.LAB_4.dto.HotelDTO;
import com.example.LAB_4.entity.accommodation.hotel.Hotel;
import com.example.LAB_4.repository.HotelRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/hotels")
@RestController
@RequiredArgsConstructor
public class HotelController {

    private final HotelRepo hotelRepo;

    @GetMapping("/all")
    public List<Hotel> getAllHotels() {
        return hotelRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable long id) {
        return hotelRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public Hotel createHotel(@RequestBody HotelDTO hotelDTO) {
        Hotel hotel = new Hotel(hotelDTO.getName(), hotelDTO.getRooms(), hotelDTO.getAmenities());
        return hotelRepo.save(hotel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable long id, @RequestBody Hotel hotelDetails) {
        return hotelRepo.findById(id)
                .map(hotel -> {
                    hotel.setName(hotelDetails.getName());
                    hotel.setAmenities(hotelDetails.getAmenities());
                    Hotel updatedHotel = hotelRepo.save(hotel);
                    return ResponseEntity.ok(updatedHotel);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteHotel(@PathVariable long id) {
        return hotelRepo.findById(id)
                .map(hotel -> {
                    hotelRepo.delete(hotel);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}