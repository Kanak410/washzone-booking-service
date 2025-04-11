package com.washzone.carwash.bookingservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {

    private String carModel;
    private String carNumber;
    private String washerEmail;



}
