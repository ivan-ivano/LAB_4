package com.example.LAB_4.entity.accommodation.cottagevillage;


import com.example.LAB_4.entity.amenity.Amenity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "cottage_village")
@NoArgsConstructor
@AllArgsConstructor

public class CottageVillage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "cottageVillage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cottage> cottages;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Amenity> amenities;

    public CottageVillage(String name, List<Cottage> cottages, List<Amenity> amenities) {
        this.name = name;
        this.cottages = cottages;
        this.amenities = amenities;
    }
}