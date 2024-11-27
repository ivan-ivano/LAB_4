package com.example.LAB_4.dto;

import com.example.LAB_4.entity.accommodation.hotel.Room;
import com.example.LAB_4.entity.amenity.Amenity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {

    private String name;
    private List<Room> rooms;
    private List<Amenity> amenities;

}
