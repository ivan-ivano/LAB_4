package com.example.LAB_4.handler;

import com.example.LAB_4.exceptions.BookingUnavailableException;
import com.example.LAB_4.exceptions.InvalidBookingPeriodException;
import com.example.LAB_4.exceptions.UserAlreadyBookedAccommodationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidBookingPeriodException.class)
    public ResponseEntity<String> handleInvalidBookingPeriod(InvalidBookingPeriodException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyBookedAccommodationException.class)
    public ResponseEntity<String> handleClientAlreadyBookedAccommodation(UserAlreadyBookedAccommodationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BookingUnavailableException.class)
    public ResponseEntity<String> handleBookingUnavailable(BookingUnavailableException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}