package com.example.tourbookingservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Bookings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    @NotNull(message = "User id should not be empty!")
    private Long userId;

    @Column(name = "tour_id")
    @NotNull(message = "Tour id should not be empty!")
    private Long tourId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotNull(message = "status should not be empty!")
    private BookingStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
