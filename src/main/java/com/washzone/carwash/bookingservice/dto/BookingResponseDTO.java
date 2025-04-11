package com.washzone.carwash.bookingservice.dto;

import com.washzone.carwash.bookingservice.model.BookingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private Long Id;
    private LocalDateTime bookingTime;
    private String carModel;
    private String carNumber;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

}
