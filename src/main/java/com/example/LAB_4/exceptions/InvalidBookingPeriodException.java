package com.example.LAB_4.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid booking period")
public class InvalidBookingPeriodException extends RuntimeException {
    public InvalidBookingPeriodException(String message) {
        super(message);
    }
}