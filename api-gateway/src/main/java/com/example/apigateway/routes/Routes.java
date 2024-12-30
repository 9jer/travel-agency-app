package com.example.apigateway.routes;


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

                .route("tour_service_swagger", r -> r.path("/aggregate/tour-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregate/tour-service/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://tour-service"))

                .route("user_service", r -> r.path("/api/v1/users/**")
                        .uri("lb://tour-user-service"))

                .route("user_service_swagger", r -> r.path("/aggregate/tour-user-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregate/tour-user-service/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://tour-user-service"))

                .route("auth_service", r -> r.path("/api/v1/auth/**")
                        .uri("lb://tour-user-service"))

                .route("booking_service", r -> r.path("/api/v1/bookings/**")
                        .uri("lb://tour-booking-service"))

                .route("booking_service_swagger", r -> r.path("/aggregate/tour-booking-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregate/tour-booking-service/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://tour-booking-service"))

                .build();
    }
}
