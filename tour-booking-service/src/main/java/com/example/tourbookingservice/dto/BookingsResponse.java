package com.example.tourbookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class BookingsResponse {
    private List<GetBookingDTO> bookings;
}
