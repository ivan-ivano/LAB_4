package com.example.LAB_4.controller;

import com.example.LAB_4.dto.RoomDTO;
import com.example.LAB_4.entity.accommodation.hotel.Hotel;
import com.example.LAB_4.entity.accommodation.hotel.Room;
import com.example.LAB_4.entity.amenity.Amenity;
import com.example.LAB_4.repository.AmenityRepo;
import com.example.LAB_4.repository.HotelRepo;
import com.example.LAB_4.repository.RoomRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/rooms")
@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomRepo roomRepo;
    private final HotelRepo hotelRepo;
    private final AmenityRepo amenityRepo;

    @GetMapping("/all")
    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable long id) {
        return roomRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public void createRoom(@RequestBody RoomDTO roomDTO) {
        Hotel hotel = hotelRepo.findById(roomDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        Room room = new Room(
                roomDTO.getName(),
                roomDTO.getType(),
                roomDTO.getPrice(),
                roomDTO.getMaxAdult(),
                roomDTO.getMaxChildren(),
                roomDTO.getDailyExpense(),
                hotel
        );
        roomRepo.save(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable long id, @RequestBody Room roomDetails) {
        return roomRepo.findById(id)
                .map(room -> {
                    room.setName(roomDetails.getName());
                    room.setType(roomDetails.getType());
                    room.setPrice(roomDetails.getPrice());
                    room.setMaxAdult(roomDetails.getMaxAdult());
                    room.setMaxChildren(roomDetails.getMaxChildren());
                    room.setDailyExpense(roomDetails.getDailyExpense());
                    Room updatedRoom = roomRepo.save(room);
                    return ResponseEntity.ok(updatedRoom);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRoom(@PathVariable long id) {
        return roomRepo.findById(id)
                .map(room -> {
                    roomRepo.delete(room);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{roomId}/amenities/{amenityId}")
    public ResponseEntity<String> addAmenityToRoom(@PathVariable long roomId, @PathVariable long amenityId) {
        Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + roomId));
        Amenity amenity = amenityRepo.findById(amenityId)
                .orElseThrow(() -> new RuntimeException("Amenity not found with ID: " + amenityId));

        if (!room.getAmenities().contains(amenity)) {
            room.getAmenities().add(amenity);
            roomRepo.save(room);
            return ResponseEntity.ok("Amenity added to the room successfully.");
        } else {
            return ResponseEntity.status(400).body("Amenity already exists in the room.");
        }
    }

}