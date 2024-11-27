package com.example.LAB_4.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmenityDTO {

    private String name;
    private double price;
    private int extraAdult;
    private int extraChild;
    private double dailyExpense;
}
