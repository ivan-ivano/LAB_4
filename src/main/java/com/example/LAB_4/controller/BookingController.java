package com.example.LAB_4.controller;

import com.example.LAB_4.dto.BookingDTO;
import com.example.LAB_4.entity.Booking;
import com.example.LAB_4.entity.accommodation.Accommodation;
import com.example.LAB_4.entity.user.User;
import com.example.LAB_4.exceptions.BookingUnavailableException;
import com.example.LAB_4.exceptions.InvalidBookingPeriodException;
import com.example.LAB_4.exceptions.UserAlreadyBookedAccommodationException;
import com.example.LAB_4.repository.AccommodationRepo;
import com.example.LAB_4.repository.BookingRepo;
import com.example.LAB_4.repository.UserRepo;
import com.example.LAB_4.service.BookingManager;
import com.example.LAB_4.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingManager bookingManager;
    private final AccommodationRepo accommodationRepo;
    private final UserRepo userRepo;
    private final BookingRepo bookingRepo;
    private final MailSenderService mailSenderService;


    @PostMapping()
    public ResponseEntity<String> createBooking(@RequestBody BookingDTO bookingDTO) {
        User user = userRepo.findById(bookingDTO.getUserId()).orElseThrow(()
                -> new IllegalArgumentException("User not found with ID: " + bookingDTO.getUserId()));
        Accommodation accommodation = accommodationRepo.findById(bookingDTO.getAccommodationId()).orElseThrow();
        LocalDate start = bookingDTO.getStartDate();
        LocalDate end = bookingDTO.getEndDate();
        int numberOfPersons = bookingDTO.getNumberOfPersons();

        try {
            Booking booking = bookingManager.createBooking(user, accommodation, start, end, numberOfPersons);
            mailSenderService.send(user.getEmail(), "Booking Confirmation", "booking successfully created with ID: " + booking.getId());

            return ResponseEntity.ok("booking successfully created with ID: " + booking.getId());
        } catch (InvalidBookingPeriodException | UserAlreadyBookedAccommodationException |
                 BookingUnavailableException e) {
            return ResponseEntity.status(e instanceof BookingUnavailableException ? 404 : 400).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingManager.getBookings();
        return ResponseEntity.ok(bookings);
    }


    @GetMapping("/availability")
    public ResponseEntity<String> checkAvailability(@RequestParam("accommodationId") long accommodationId,
                                                    @RequestParam("startDate") String startDate,
                                                    @RequestParam("endDate") String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);


            Accommodation accommodation = accommodationRepo.findById(accommodationId)
                    .orElseThrow(() -> new IllegalArgumentException("Accommodation not found for ID: " + accommodationId));


            if (bookingManager.checkAvailability(accommodation, start, end)) {
                return ResponseEntity.ok("Accommodation is available for the selected dates.");
            } else {
                return ResponseEntity.status(404).body("Accommodation is not available for the selected dates.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error occurred while checking availability." + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable long id) {
        return bookingRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable long id, @RequestBody BookingDTO bookingDetails) {
        return bookingRepo.findById(id)
                .map(booking -> {
                    booking.setStartDate(bookingDetails.getStartDate());
                    booking.setEndDate(bookingDetails.getEndDate());
                    booking.setNumberOfPersons(bookingDetails.getNumberOfPersons());

                    User user = userRepo.findById(bookingDetails.getUserId())
                            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + bookingDetails.getUserId()));
                    booking.setUser(user);

                    Accommodation accommodation = accommodationRepo.findById(bookingDetails.getAccommodationId())
                            .orElseThrow(() -> new IllegalArgumentException("Accommodation not found with ID: " + bookingDetails.getAccommodationId()));
                    booking.setAccommodation(accommodation);

                    Booking updatedBooking = bookingRepo.save(booking);
                    return ResponseEntity.ok(updatedBooking);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteBooking(@RequestParam long id) {
        return bookingRepo.findById(id)
                .map(booking -> {
                    bookingRepo.delete(booking);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/finance")
    public ResponseEntity<Object> calculateFinance() {
        ArrayList<Double> finance = bookingManager.calculateIncomeAndExpenses();
        mailSenderService.send("ivan888pro@gmail.com", "Finance Report",
                "Income: " + finance.get(0)
                + " Expenses: " + finance.get(1)
                + " Profit: " + (finance.get(0) - finance.get(1)));
        return ResponseEntity.ok("Income: " + finance.get(0)
                + " Expenses: " + finance.get(1)
                + " Profit: " + (finance.get(0) - finance.get(1)));
    }


}