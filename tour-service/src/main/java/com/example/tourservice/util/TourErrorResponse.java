package com.example.tourservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TourErrorResponse {
    private String message;
    private Long timestamp;
}
