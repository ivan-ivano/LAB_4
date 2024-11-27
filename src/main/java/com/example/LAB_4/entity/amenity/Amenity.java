package com.example.LAB_4.entity.amenity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "amenities")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int extraAdult;

    @Column(nullable = false)
    private int extraChild;

    @Column(nullable = false)
    private double dailyExpense;

    public Amenity(String name, double price, int extraAdult, int extraChild, double dailyExpense) {
        this.name = name;
        this.price = price;
        this.extraAdult = extraAdult;
        this.extraChild = extraChild;
        this.dailyExpense = dailyExpense;
    }
}