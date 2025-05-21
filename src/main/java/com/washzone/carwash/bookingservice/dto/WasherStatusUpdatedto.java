package com.washzone.carwash.bookingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WasherStatusUpdatedto {
    private String email;
    private boolean isAvailable;
}
