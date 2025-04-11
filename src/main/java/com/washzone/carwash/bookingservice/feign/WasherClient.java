package com.washzone.carwash.bookingservice.feign;


import com.washzone.carwash.bookingservice.dto.WasherDTO;
import com.washzone.carwash.bookingservice.model.WasherModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "washer-service"

)
public interface WasherClient {

    @GetMapping("/api/washers/email/{email}")
    WasherDTO getWasherByEmail(@PathVariable String email);


}