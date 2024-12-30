package com.example.tourservice.services;

import com.example.tourservice.models.Tour;
import com.example.tourservice.repositories.TourRepository;
import com.example.tourservice.util.TourException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TourService {

    private final TourRepository tourRepository;

    public List<Tour> findAll() {
        return tourRepository.findAll();
    }

    public Tour findById(Long id) {
        return tourRepository.findById(id).orElseThrow(()-> new TourException("Tour not found"));
    }

    @Transactional
    public Tour save(Tour tour) {
        if (tour.getDescription() == null || tour.getDescription().trim().isEmpty()) {
            tour.setDescription("There is no description here");
        }

        tour.setCreatedAt(LocalDateTime.now());
        return tourRepository.save(tour);
    }

    @Transactional
    public Tour update(Long id, Tour updatedTour) {
        return tourRepository.findById(id)
                .map(tour -> {
                    tour.setName(updatedTour.getName());

                    if (tour.getDescription() == null || tour.getDescription().trim().isEmpty()) {
                        tour.setDescription("There is no description here");
                    } else {
                        tour.setDescription(updatedTour.getDescription());
                    }

                    tour.setPrice(updatedTour.getPrice());
                    tour.setDuration(updatedTour.getDuration());
                    tour.setAvailable(updatedTour.getAvailable());
                    tour.setUpdatedAt(LocalDateTime.now());
                    return tourRepository.save(tour);
                 })
                .orElseThrow(()-> new TourException("Tour not found"));
    }

    @Transactional
    public void decreaseSeats(Long tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found"));
        if (tour.getAvailableSeats() <= 0) {
            throw new RuntimeException("No available seats");
        }
        tour.setAvailableSeats(tour.getAvailableSeats() - 1);
        tourRepository.save(tour);
    }

    @Transactional
    public void increaseSeats(Long tourId) {
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new RuntimeException("Tour not found"));
        tour.setAvailableSeats(tour.getAvailableSeats() + 1);
        tourRepository.save(tour);
    }

    public Boolean existsById(Long id) {
        return tourRepository.existsById(id);
    }

    @Transactional
    public void deleteTourById(Long id) {
        tourRepository.deleteById(id);
    }
}
