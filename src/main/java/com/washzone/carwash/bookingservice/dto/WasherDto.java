package com.washzone.carwash.bookingservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WasherDTO {
    private String name;
    private String email;
    private boolean isAvailable;
}
