package com.washzone.carwash.bookingservice.repository;

import com.washzone.carwash.bookingservice.dto.WasherDTO;
import com.washzone.carwash.bookingservice.model.User;
import com.washzone.carwash.bookingservice.model.WasherModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface WasherRepository extends JpaRepository<WasherModel, Long> {

    Optional<WasherModel> findByEmail(String email);
}
