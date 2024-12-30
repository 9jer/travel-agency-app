package com.example.tourservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotEmpty(message = "Name should not be empty!")
    private String name;

    private String description;

    @NotNull(message = "Price cannot be empty")
    @DecimalMin(value = "0.0", message = "Price should be greater than 0!")
    private BigDecimal price;

    @NotNull(message = "Duration cannot be empty")
    @Min(value = 1, message = "duration should be greater than 0!")
    private Integer duration;

    @NotNull(message = "Available cannot be empty")
    private Boolean available;

    @NotNull(message = "Available seats cannot be empty")
    private Integer availableSeats;
}
