package com.example.LAB_4.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Client has already booked this accommodation")
public class UserAlreadyBookedAccommodationException extends RuntimeException {
    public UserAlreadyBookedAccommodationException(String message) {
        super(message);
    }
}