package com.example.tourservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tours")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Name should not be empty!")
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull(message = "Price cannot be empty")
    @Column(name = "price")
    @DecimalMin(value = "0.0", message = "Price should be greater than 0!")
    private BigDecimal price;

    @NotNull(message = "Duration cannot be empty")
    @Column(name = "duration")
    @Min(value = 1, message = "duration should be greater than 0!")
    private Integer duration;

    @NotNull(message = "Available cannot be empty")
    @Column(name = "available")
    private Boolean available;

    @NotNull(message = "Available seats cannot be empty")
    @Column(name = "available_seats")
    private Integer availableSeats;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
