package com.example.services;

import com.example.repositories.EventLogRepository;
import com.example.tourbookingservice.event.EventLog;
import com.example.tourbookingservice.event.EventLogDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventLogService {

    private final EventLogRepository eventLogRepository;

    @KafkaListener(topics = "booking-notifications", groupId = "notificationService")
    public void listen(EventLogDto eventLogDto) {
        log.info("Got message from booking-notifications topic: {}", eventLogDto);

        EventLog eventLog = new EventLog();

        eventLog.setEventType(eventLogDto.getEventType());
        eventLog.setBookingId(eventLogDto.getBookingId());
        eventLog.setTourId(eventLogDto.getTourId());
        eventLog.setStatus(eventLogDto.getStatus());
        eventLog.setCreatedAt(eventLogDto.getCreatedAt());

        eventLogRepository.save(eventLog);

        log.info("Added EventLog {} from booking-notifications topic into database", eventLogDto);
    }
}
