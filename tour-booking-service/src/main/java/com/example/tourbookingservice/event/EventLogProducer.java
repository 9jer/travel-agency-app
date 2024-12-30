package com.example.tourbookingservice.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventLogProducer {

    private final KafkaTemplate<String, EventLogDto> kafkaTemplate;

    public void sendEventLog(String topic, EventLogDto eventLogDto) {
        log.info("Start - Sending EventLog with booking create {} to Kafka topic booking-notifications", eventLogDto);
        kafkaTemplate.send(topic, eventLogDto);
        log.info("End - Sending EventLog with booking create {} to Kafka topic booking-notifications", eventLogDto);
    }
}
