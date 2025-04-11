package com.washzone.carwash.bookingservice.repository;

import com.washzone.carwash.bookingservice.model.BookingModel;
import com.washzone.carwash.bookingservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<BookingModel> findAllByEmail(String email);
}
