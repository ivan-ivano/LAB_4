package com.example.LAB_4.entity.accommodation;

import com.example.LAB_4.entity.amenity.Amenity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public abstract class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "accommodation_amenity",
            joinColumns = @JoinColumn(name = "accommodation_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<Amenity> amenities = new ArrayList<>();

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int maxAdult;

    @Column(nullable = false)
    private int maxChildren;

    @Column(nullable = false)
    private double dailyExpense;

    public Accommodation(String name, String type, double price, int maxAdult, int maxChildren, double dailyExpense) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.maxAdult = maxAdult;
        this.maxChildren = maxChildren;
        this.dailyExpense = dailyExpense;
    }

}
