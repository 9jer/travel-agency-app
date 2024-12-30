package com.example.tourbookingservice.services;

import com.example.tourbookingservice.event.EventLogDto;
import com.example.tourbookingservice.models.Booking;
import com.example.tourbookingservice.models.BookingStatus;
import com.example.tourbookingservice.util.BookingException;
import com.example.tourbookingservice.client.TourServiceClient;
import com.example.tourbookingservice.client.TourUserServiceClient;
import com.example.tourbookingservice.dto.TourDTO;
import com.example.tourbookingservice.event.EventLogProducer;
import com.example.tourbookingservice.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TourServiceClient tourServiceClient;
    private final TourUserServiceClient tourUserServiceClient;
    private final EventLogProducer eventLogProducer;


    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking findById(Long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new BookingException("Booking not found"));
    }

    @Transactional
    public Booking create(Booking booking) {

        Boolean userExists = tourUserServiceClient.userExists(booking.getUserId());

        if(userExists == null || !userExists) {
            throw new BookingException("User not found");
        }

        Boolean tourExists = tourServiceClient.isTourExists(booking.getTourId());

        if(tourExists == null || !tourExists) {
            throw new BookingException("Tour not found");
        }

        TourDTO tour = tourServiceClient.getTourById(booking.getTourId());

        if(tour.getAvailableSeats() <= 0){
            throw new BookingException("No available seats for this tour");
        }

        if(booking.getStatus() == BookingStatus.CONFIRMED){
            tourServiceClient.decreaseAvailableSeats(booking.getTourId());
        }

        //booking.setStatus(BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());
        booking = bookingRepository.save(booking);

        EventLogDto eventLogDto = new EventLogDto("CREATE BOOKING", booking.getId(),
                booking.getTourId(), booking.getStatus().toString(), LocalDateTime.now());
        eventLogProducer.sendEventLog("booking-notifications", eventLogDto);

        return booking;
    }

    @Transactional
    public Booking updateBookingStatus(Long bookingId, BookingStatus bookingStatus) {
        Booking booking = findById(bookingId);
        booking.setStatus(bookingStatus);
        TourDTO tour = tourServiceClient.getTourById(booking.getTourId());

        if(booking.getStatus() == BookingStatus.CANCELLED){
            tourServiceClient.increaseAvailableSeats(booking.getTourId());
        } else if(booking.getStatus() == BookingStatus.CONFIRMED){
            if(tour.getAvailableSeats() <= 0){
                throw new BookingException("No available seats for this tour");
            }

            tourServiceClient.decreaseAvailableSeats(booking.getTourId());
        }

        booking.setUpdatedAt(LocalDateTime.now());
        booking = bookingRepository.save(booking);

        EventLogDto eventLogDto = new EventLogDto("UPDATE BOOKING STATUS", booking.getId(),
                booking.getTourId(), booking.getStatus().toString(), LocalDateTime.now());
        eventLogProducer.sendEventLog("booking-notifications", eventLogDto);

        return booking;
    }

}
