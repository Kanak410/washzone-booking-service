package com.washzone.carwash.bookingservice.service;

import com.washzone.carwash.bookingservice.dto.WasherDTO;
import com.washzone.carwash.bookingservice.model.WasherModel;
import com.washzone.carwash.bookingservice.repository.WasherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WasherService {
    @Autowired
    private WasherRepository washerRepository;


    public WasherDTO getWasherByEmail(String email) {
        WasherModel washer = washerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Washer not found"));
        return new WasherDTO(washer.getName(), washer.getEmail(), washer.isAvailable());
    }
}
