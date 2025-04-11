package com.washzone.carwash.bookingservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.washzone.carwash.bookingservice.dto.BookingRequestDTO;
import com.washzone.carwash.bookingservice.service.BookingService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Entity
@Table (name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime bookingTime;
    @Column(nullable = false)
    private String carModel;
    @Column(nullable = false,unique = true)
    private String carNumber;

    @Enumerated(EnumType.STRING)
    private  BookingStatus status;




    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private CustomerModel customer;


    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    @JsonManagedReference
    private PaymentModel payment;


    private String washerEmail;

    public BookingModel(String carModel, String carNumber, BookingStatus status) {
        this.carModel = carModel;
        this.carNumber = carNumber;
        this.status = status;
        this.bookingTime = LocalDateTime.now();

    }




}
