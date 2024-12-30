package com.example.tourbookingservice.repositories;

import com.example.tourbookingservice.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
