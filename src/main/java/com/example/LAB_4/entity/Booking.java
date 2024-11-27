package com.example.LAB_4.entity;

import com.example.LAB_4.entity.accommodation.Accommodation;
import com.example.LAB_4.entity.accommodation.cottagevillage.Cottage;
import com.example.LAB_4.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "bookings")
@NoArgsConstructor
@AllArgsConstructor

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    private Accommodation accommodation;

    @Column(nullable = false)
    private LocalDate startDate;
    private LocalDate endDate;

    private int days;
    private int numberOfPersons;
    private double pricePerDay;
    private double totalPrice;

    public Booking(User user, Accommodation accommodation, LocalDate startDate, LocalDate endDate, double pricePerDay, int numberOfPersons) {
        this.user = user;
        this.accommodation = accommodation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = startDate.until(endDate).getDays();
        this.pricePerDay = pricePerDay;
        this.numberOfPersons = numberOfPersons;
        this.totalPrice = calculateTotalPrice();
    }

    private double calculateTotalPrice() {
        if (accommodation instanceof Cottage) {
            if ((startDate.getMonthValue() == 3 && endDate.getMonthValue() == 3) ||
                    (startDate.getMonthValue() == 11 && endDate.getMonthValue() == 11)) {
                return days * pricePerDay * 0.8;
            }

            return days * pricePerDay;
        }
        return days * pricePerDay;
    }

}