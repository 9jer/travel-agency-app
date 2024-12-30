package com.example.tourbookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TourDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private Boolean available;
    private Integer availableSeats;
}
