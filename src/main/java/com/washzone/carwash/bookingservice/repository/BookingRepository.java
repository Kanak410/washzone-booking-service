package com.washzone.carwash.bookingservice.repository;

import com.washzone.carwash.bookingservice.model.BookingModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookingModel,Long> {
    List<BookingModel> findAllByCustomerEmail(String email);
}
