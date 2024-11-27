package com.example.LAB_4.entity.accommodation.hotel;

import com.example.LAB_4.entity.amenity.Amenity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "hotels")
@NoArgsConstructor
@AllArgsConstructor

public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonManagedReference
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Room> rooms;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Amenity> amenities;

    public Hotel(String name, List<Room> rooms, List<Amenity> amenities) {
        this.name = name;
        this.rooms = rooms;
        this.amenities = amenities;
    }
}
