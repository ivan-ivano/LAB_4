package com.example.LAB_4.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CottageDTO {

    private String name;
    private String type;
    private double price;
    private int maxAdult;
    private int maxChildren;
    private double dailyExpense;
    private long cottageVillageId;
}