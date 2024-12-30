package com.example.tourbookingservice.client;

import com.example.tourbookingservice.config.FeignClientConfig;
import com.example.tourbookingservice.dto.TourDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tour-service", url = "${spring.tour.service.url}", configuration = FeignClientConfig.class)
public interface TourServiceClient {

    @GetMapping("/api/v1/tours/{id}")
    TourDTO getTourById(@PathVariable("id") Long id);

    @PatchMapping("/api/v1/tours/{id}/decrease-seats")
    void decreaseAvailableSeats(@PathVariable("id") Long id);

    @PatchMapping("/api/v1/tours/{id}/increase-seats")
    void increaseAvailableSeats(@PathVariable("id") Long id);

    @GetMapping("/api/v1/tours/{id}/exists")
    Boolean isTourExists(@PathVariable("id") Long id);
}
