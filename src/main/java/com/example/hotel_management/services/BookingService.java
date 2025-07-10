package com.example.hotel_management.services;

import com.example.hotel_management.dtos.BookingDTO;
import com.example.hotel_management.dtos.Response;

public interface BookingService {

    Response getAllBookings();
    Response createBooking(BookingDTO bookingDTO);
    Response findBookingByReferenceNo(String  bookingReference);
    Response updateBooking(BookingDTO bookingDTO);
}
