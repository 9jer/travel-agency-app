package com.example.apigatewaymobile.routes;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Routes {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("tour_service", r -> r.path("/api/v1/tours/**")
                        .uri("lb://tour-service"))

                .route("booking_service", r -> r.path("/api/v1/bookings/**")
                        .uri("lb://tour-booking-service"))

                .route("auth_service", r -> r.path("/api/v1/auth/**")
                        .uri("lb://tour-user-service"))

                .build();
    }
}
