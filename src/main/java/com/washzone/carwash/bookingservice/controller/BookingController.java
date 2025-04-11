package com.washzone.carwash.bookingservice.controller;

import com.washzone.carwash.bookingservice.dto.BookingRequestDTO;
import com.washzone.carwash.bookingservice.dto.BookingResponseDTO;
import com.washzone.carwash.bookingservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/booking")
    public BookingResponseDTO booking(Authentication authentication, @RequestBody BookingRequestDTO bookingRequestDTO) {
        String email = authentication.getName();
        return bookingService.createBooking(email,bookingRequestDTO);


    }
    @GetMapping("/booking/{id}")
    public BookingResponseDTO getBooking(@PathVariable Long id) {
        return bookingService.getBooking(id);
    }
    @GetMapping("/booking/user")
    public List<BookingResponseDTO> getBookings(Authentication authentication) {
        String email = authentication.getName();
        return bookingService.getBookings(email);
    }
    @PutMapping("/bookings/{id}/cancel")
    public BookingResponseDTO cancelBooking(@PathVariable Long id) {
        return bookingService.cancelBooking(id);
    }
    @GetMapping("/boookings/{id}/confirm")
    public ResponseEntity<String> confirmBooking(@PathVariable Long id) {
        bookingService.confirmBooking(id);
        return ResponseEntity.ok("Booking confirmation");
    }


}
