package com.example.tourservice.controllers;

import com.example.tourservice.dto.TourDTO;
import com.example.tourservice.dto.ToursResponse;
import com.example.tourservice.models.Tour;
import com.example.tourservice.services.TourService;
import com.example.tourservice.util.ErrorsUtil;
import com.example.tourservice.util.TourErrorResponse;
import com.example.tourservice.util.TourException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tours")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;
    private final ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ToursResponse getAllTours() {
        return new ToursResponse(tourService.findAll().stream()
                .map(this::convertTourtoTourDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public TourDTO getTourById(@PathVariable("id") Long id) {
        return convertTourtoTourDTO(tourService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Tour> createTour(@RequestBody @Valid TourDTO tourDTO, BindingResult bindingResult) {
        Tour tour = convertTourDTOtoTour(tourDTO);

        if(bindingResult.hasErrors()){
            ErrorsUtil.returnAllErrors(bindingResult);
        }

        return new ResponseEntity<>(tourService.save(tour), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tour> updateTour(@PathVariable("id") Long id,
                                           @RequestBody @Valid TourDTO tourDTO, BindingResult bindingResult) {
        Tour tour = convertTourDTOtoTour(tourDTO);

        if(bindingResult.hasErrors()){
            ErrorsUtil.returnAllErrors(bindingResult);
        }

        return new ResponseEntity<>(tourService.update(id, tour), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable("id") Long id) {
        tourService.deleteTourById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/decrease-seats")
    public ResponseEntity<Void> decreaseAvailableSeats(@PathVariable(name = "id") Long id) {
        tourService.decreaseSeats(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/increase-seats")
    public ResponseEntity<Void> increaseAvailableSeats(@PathVariable(name = "id") Long id) {
        tourService.increaseSeats(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/exists")
    public Boolean isTourExists(@PathVariable("id") Long id) {
        return tourService.existsById(id);
    }

    private Tour convertTourDTOtoTour(TourDTO tourDTO) {
        return modelMapper.map(tourDTO, Tour.class);
    }

    private TourDTO convertTourtoTourDTO(Tour tour) {
        return modelMapper.map(tour, TourDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<Object> handleException(TourException e) {
        TourErrorResponse response = new TourErrorResponse(
            e.getMessage(),
            System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
