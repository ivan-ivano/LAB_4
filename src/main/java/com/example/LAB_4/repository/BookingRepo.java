package com.example.LAB_4.repository;

import com.example.LAB_4.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
}
