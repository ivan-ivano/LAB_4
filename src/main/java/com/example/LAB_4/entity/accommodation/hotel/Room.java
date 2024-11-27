package com.example.LAB_4.entity.accommodation.hotel;

import com.example.LAB_4.entity.accommodation.Accommodation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Room extends Accommodation {
    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonIgnore
    private Hotel hotel;

    public Room(String name, String type, double price, int maxAdult, int maxChildren, double dailyExpense, Hotel hotel) {
        super(name, type, price, maxAdult, maxChildren, dailyExpense);
        this.hotel = hotel;
    }

}
