package com.example.LAB_4.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Accommodation is not available for the selected dates")
public class BookingUnavailableException extends RuntimeException {
    public BookingUnavailableException(String message) {
        super(message);
    }
}