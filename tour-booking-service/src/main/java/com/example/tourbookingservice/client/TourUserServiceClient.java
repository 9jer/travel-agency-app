package com.example.tourbookingservice.client;

import com.example.tourbookingservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("tour-user-service")
public interface TourUserServiceClient {

    @GetMapping("/api/v1/users/{id}/exists")
    Boolean userExists(@PathVariable Long id);
}
