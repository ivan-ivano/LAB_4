package com.example.LAB_4.dto;

import com.example.LAB_4.entity.accommodation.cottagevillage.Cottage;
import com.example.LAB_4.entity.amenity.Amenity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CottageVillageDTO {

    private String name;
    private List<Cottage> cottages;
    private List<Amenity> amenities;

}
