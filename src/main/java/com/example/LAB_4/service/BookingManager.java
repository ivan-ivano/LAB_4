package com.example.LAB_4.service;

import com.example.LAB_4.entity.Booking;
import com.example.LAB_4.entity.accommodation.Accommodation;
import com.example.LAB_4.entity.user.User;
import com.example.LAB_4.exceptions.BookingUnavailableException;
import com.example.LAB_4.exceptions.InvalidBookingPeriodException;
import com.example.LAB_4.exceptions.UserAlreadyBookedAccommodationException;
import com.example.LAB_4.repository.BookingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingManager {

    private final BookingRepo bookingRepo;

    public List<Booking> getBookings() {
        return bookingRepo.findAll();
    }

    public ArrayList<Double> calculateIncomeAndExpenses() {

        ArrayList<Double> incomeAndExpenses = new ArrayList<>();

        double totalIncome = bookingRepo.findAll().stream()
                .mapToDouble(Booking::getTotalPrice)
                .sum();

        double totalExpenses = bookingRepo.findAll().stream()
                .mapToDouble(booking -> {
                    double accommodationExpense = booking.getAccommodation().getDailyExpense() * booking.getDays();
                    double amenitiesExpense = booking.getAccommodation().getAmenities().stream()
                            .mapToDouble(amenity -> amenity.getDailyExpense())
                            .sum() * booking.getDays();
                    return accommodationExpense + amenitiesExpense;
                })
                .sum();

        incomeAndExpenses.add(totalIncome);
        incomeAndExpenses.add(totalExpenses);

        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Expenses: " + totalExpenses);
        System.out.println("Total Profit: " + (totalIncome - totalExpenses));
        return incomeAndExpenses;
    }

    public Booking createBooking(User user, Accommodation accommodation, LocalDate start, LocalDate end, int numberOfPersons) {
        if (start.isAfter(end)) {
            throw new InvalidBookingPeriodException("The start date cannot be after the end date.");
        }

        if (hasUserBookedAccommodation(user, accommodation)) {
            throw new UserAlreadyBookedAccommodationException("User has already booked this accommodation.");
        }

        if (isAvailable(accommodation, start, end)) {
            double pricePerDay = calculateDailyRate(accommodation);

            Booking booking = new Booking(user, accommodation, start, end, pricePerDay, numberOfPersons);
            bookingRepo.save(booking);
            return booking;
        } else {
            throw new BookingUnavailableException("Accommodation is not available for the selected dates.");
        }
    }

    private double calculateDailyRate(Accommodation accommodation) {
        double basePrice = accommodation.getPrice();

        double amenitiesCost = accommodation.getAmenities().stream()
                .mapToDouble(amenity -> amenity.getPrice())
                .sum();

        return basePrice + amenitiesCost;
    }

    public boolean isAvailable(Accommodation accommodation, LocalDate start, LocalDate end) {
        return bookingRepo.findAll().stream()
                .noneMatch(booking -> booking.getAccommodation().equals(accommodation) &&
                        isDateOverlap(booking, start, end));
    }

    private boolean hasUserBookedAccommodation(User user, Accommodation accommodation) {
        System.out.println(bookingRepo.findAll());
        return bookingRepo.findAll().stream()
                .anyMatch(booking -> booking.getUser().equals(user) && booking.getAccommodation().equals(accommodation));
    }

    private boolean isDateOverlap(Booking booking, LocalDate start, LocalDate end) {
        return (booking.getStartDate().isBefore(end) && booking.getEndDate().isAfter(start));
    }

    public boolean checkAvailability(Accommodation accommodation, LocalDate start, LocalDate end) {
        if (isAvailable(accommodation, start, end)) {
            return true;
        } else {
            System.out.println(accommodation.getName() + "Accommodation is not available for the selected dates.");
        }
        return false;
    }
}

