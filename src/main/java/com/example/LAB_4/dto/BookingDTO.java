package com.example.LAB_4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {

    private long userId;
    private long accommodationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfPersons;

}