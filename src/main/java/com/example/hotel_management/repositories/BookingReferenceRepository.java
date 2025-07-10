package com.example.hotel_management.repositories;

import com.example.hotel_management.entities.BookingReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingReferenceRepository extends JpaRepository<BookingReference, Integer> {
    Optional<BookingReference> findByReferenceNo(String referenceNo);
}
