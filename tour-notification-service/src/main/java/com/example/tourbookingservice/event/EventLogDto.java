package com.example.tourbookingservice.event;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventLogDto {
    private String eventType;
    private Long bookingId;
    private Long tourId;
    private String status;
    private LocalDateTime createdAt;
}