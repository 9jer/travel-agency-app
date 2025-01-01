package com.example.tourbookingservice.controllers;

import com.example.tourbookingservice.dto.BookingDTO;
import com.example.tourbookingservice.dto.BookingsResponse;
import com.example.tourbookingservice.dto.GetBookingDTO;
import com.example.tourbookingservice.models.Booking;
import com.example.tourbookingservice.models.BookingStatus;
import com.example.tourbookingservice.util.BookingErrorResponse;
import com.example.tourbookingservice.util.BookingException;
import com.example.tourbookingservice.util.ErrorsUtil;
import com.example.tourbookingservice.services.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final ModelMapper modelMapper;

    @GetMapping
    public BookingsResponse getAllBookings() {
        return new BookingsResponse(bookingService.findAll().stream()
                .map(this::convertToGetBookingDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public GetBookingDTO getBookingById(@PathVariable(name = "id") Long id) {
        return convertToGetBookingDTO(bookingService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody @Valid BookingDTO bookingDTO, BindingResult bindingResult) {
        Booking booking = convertToEntity(bookingDTO);

        if(bindingResult.hasErrors()) {
            ErrorsUtil.returnAllErrors(bindingResult);
        }

        Booking savedBooking = bookingService.create(booking);
        return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable(name = "id") Long id, @RequestParam(name = "status") BookingStatus bookingStatus) {
        Booking updatedBooking = bookingService.updateBookingStatus(id, bookingStatus);

        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }

    private BookingDTO convertToDTO(Booking booking) {
        return modelMapper.map(booking, BookingDTO.class);
    }

    private Booking convertToEntity(BookingDTO bookingDTO) {
        Booking booking = new Booking();

        booking.setUserId(bookingDTO.getUserId());
        booking.setTourId(bookingDTO.getTourId());
        booking.setStatus(bookingDTO.getStatus());

        return booking;
    }

    private GetBookingDTO convertToGetBookingDTO(Booking booking) {
        return modelMapper.map(booking, GetBookingDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<Object> handleException(BookingException e) {
        BookingErrorResponse response = new BookingErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}