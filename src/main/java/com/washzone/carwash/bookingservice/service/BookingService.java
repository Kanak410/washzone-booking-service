package com.washzone.carwash.bookingservice.service;


import com.washzone.carwash.bookingservice.dto.BookingRequestDTO;
import com.washzone.carwash.bookingservice.dto.BookingResponseDTO;
import com.washzone.carwash.bookingservice.dto.WasherDTO;
import com.washzone.carwash.bookingservice.exceptions.UserNotFoundException;
import com.washzone.carwash.bookingservice.feign.WasherClient;
import com.washzone.carwash.bookingservice.model.*;
import com.washzone.carwash.bookingservice.repository.BookingRepository;
import com.washzone.carwash.bookingservice.repository.CustomerRepository;
import com.washzone.carwash.bookingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private WasherClient washerClient;

    public BookingResponseDTO createBooking(String email,BookingRequestDTO bookingRequestDTO) {
        BookingModel bookingModel = new BookingModel(bookingRequestDTO.getCarModel(), bookingRequestDTO.getCarNumber(), BookingStatus.INITIATED);

        CustomerModel customer = getCustomer(email);
        bookingModel.setCustomer(customer);
        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setBooking(bookingModel);// call payment service
        paymentModel.setPaymentMethod(PaymentMethod.CASH);
        paymentModel.setStatus(TransactionStatus.PAID);
        if(paymentModel.getStatus() == TransactionStatus.PAID){

            // call washer service
            bookingModel.setWasherEmail("carwash@gmail.com");


            bookingModel.setStatus(BookingStatus.CONFIRMED);
        }
        else{
            bookingModel.setStatus(BookingStatus.CANCELLED);
        }

        bookingRepository.save(bookingModel);

        return new BookingResponseDTO(bookingModel.getId(),bookingModel.getBookingTime(),bookingModel.getCarModel(),bookingModel.getCarNumber(),bookingModel.getStatus());

    }

    private CustomerModel getCustomer(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User with email = "+email+" not found"));
        Long id = user.getId();
        CustomerModel customer = customerRepository.findByUserId(id).orElseThrow(() -> new UserNotFoundException("Customer " +
                "with " + email + " not found"));
        return customer;
    }
    public BookingResponseDTO getBooking(Long id) {
        BookingModel bookingModel=bookingRepository.findById(id).
                orElseThrow(() ->new UserNotFoundException("Booking not found with ID: "+id));
        return mapToBookingResponseDTO(bookingModel);


    }

    private BookingResponseDTO mapToBookingResponseDTO(BookingModel bookingModel) {
        return new BookingResponseDTO();
    }

    public List<BookingResponseDTO> getBookings(String email) {
        List<BookingModel> bookings=bookingRepository.findAllByCustomerEmail(email);
        return bookings.stream().map(this::mapToBookingResponseDTO)
                .collect(Collectors.toList());
    }

    public BookingResponseDTO cancelBooking(Long id) {
        BookingModel bookingModel = bookingRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Booking not found with ID: " + id));
        bookingModel.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(bookingModel);
        return mapToBookingResponseDTO(bookingModel);


    }
    public BookingResponseDTO confirmBooking(Long id) {
        BookingModel bookingModel = bookingRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Booking not found with ID: " + id));
        if(bookingModel.getStatus().equals(BookingStatus.CONFIRMED)){
            throw new IllegalStateException("Booking is already confirmed");
        }
        bookingModel.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(bookingModel);
        return mapToBookingResponseDTO(bookingModel);
    }
    public void confirmBooking(Long bookingId, String email) {
        BookingModel booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getCustomer().getEmail().equals(email)) {
            throw new SecurityException("You are not allowed to confirm this booking.");
        }

    }
    public BookingResponseDTO createBooking(BookingRequestDTO requestDTO,String email) {
        WasherDTO washer = washerClient.getWasherByEmail(requestDTO.getWasherEmail());

        if (washer != null && washer.isAvailable()) {
            BookingModel booking = new BookingModel(
                    requestDTO.getCarModel(),
                    requestDTO.getCarNumber(),
                    BookingStatus.CONFIRMED
            );

            CustomerModel customer = getCustomer(email);
            booking.setCustomer(customer);
            booking.setWasherEmail(washer.getEmail());

            // mock payment (or call payment service)
            PaymentModel payment = new PaymentModel();
            payment.setBooking(booking);
            payment.setPaymentMethod(PaymentMethod.CASH);
            payment.setStatus(TransactionStatus.PAID);
            booking.setStatus(BookingStatus.CONFIRMED);

            bookingRepository.save(booking);
            return new BookingResponseDTO(
                    booking.getId(),
                    booking.getBookingTime(),
                    booking.getCarModel(),
                    booking.getCarNumber(),
                    booking.getStatus()
            );
        } else {
            throw new RuntimeException("No washer available.");
        }
    }

}







