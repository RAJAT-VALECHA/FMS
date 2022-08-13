package com.org.service;

import org.springframework.http.ResponseEntity;

import com.org.model.Booking;

public interface BookingService {

	public ResponseEntity<?> createBooking(Booking newBooking,long uid);

//	public Booking updateBooking(Booking newBooking);

	public String cancelBooking(long bookingId);

	public Iterable<Booking> displayAllBooking();

	public ResponseEntity<?> findBookingById(long bookingId);
}
