package com.example.LAB_4.entity.accommodation.cottagevillage;

import com.example.LAB_4.entity.accommodation.Accommodation;
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

public class Cottage extends Accommodation {
    @ManyToOne
    @JoinColumn(name = "cottage_village_id", nullable = false)
    private CottageVillage cottageVillage;

    public Cottage(String name, String type, double price, int maxAdult, int maxChildren, double dailyExpense, CottageVillage cottageVillage) {
        super(name, type, price, maxAdult, maxChildren, dailyExpense);
        this.cottageVillage = cottageVillage;
    }
}
