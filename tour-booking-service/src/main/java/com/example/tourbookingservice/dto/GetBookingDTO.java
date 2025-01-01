package com.example.tourbookingservice.dto;

import com.example.tourbookingservice.models.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class GetBookingDTO {

    private Long id;

    @NotNull(message = "User id should not be empty!")
    private Long userId;

    @NotNull(message = "Tour id should not be empty!")
    private Long tourId;

    @NotNull(message = "status should not be empty!")
    private BookingStatus status;
}
