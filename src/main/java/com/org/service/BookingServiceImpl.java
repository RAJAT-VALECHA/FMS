package com.org.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.org.dao.BookingDao;
import com.org.dao.ScheduledFlightDao;
import com.org.exceptions.RecordNotFoundException;
import com.org.model.Booking;
import com.org.model.ScheduledFlight;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingDao bookingDao;

	@Autowired
	ScheduledFlightDao scheduledFlightDao;
	
	@Autowired
	UserService userService;
	@Override
	public ResponseEntity<?> createBooking(Booking newBooking,long uid) {
		
		List<ScheduledFlight> findall=(List<ScheduledFlight>) scheduledFlightDao.findAll();
		for(ScheduledFlight s:findall) {
			if(s.getDstnAirport().getAirportCode().equals(newBooking.getDstnAirport())&&s.getSrcAirport().getAirportCode().equals(newBooking.getSrcAirport())) {
				if(s.getAvailableSeats()-newBooking.getNoOfPassengers()>0) {
					s.setAvailableSeats(s.getAvailableSeats()-newBooking.getNoOfPassengers());
					bookingDao.save(newBooking);
					newBooking.setUser(userService.findUserById(uid));
					break;
				}
				else 
				{
					return new ResponseEntity<String>("Insufficient no of seats in the Flights", HttpStatus.NOT_FOUND);

				}
			}
			else {
				return new ResponseEntity<String>("No Flights available", HttpStatus.NOT_FOUND);

			}
		}
		return new ResponseEntity<Booking>(newBooking, HttpStatus.OK);
	}

	
//	@Override
//	public Booking updateBooking(Booking changedBooking) {
//		Optional<Booking> findBookingById = bookingDao.findById(changedBooking.getBookingId());
//		if (findBookingById.isPresent()) {
//			bookingDao.save(changedBooking);
//		} else
//			throw new RecordNotFoundException(
//					"Booking with Booking Id: " + changedBooking.getBookingId() + " not exists!!");
//		return changedBooking;
//	}

	@Override
	public String cancelBooking(long bookingId) {
		
		Optional<Booking> findBookingById = bookingDao.findById(bookingId);
		if (findBookingById.isPresent()) {
			bookingDao.deleteById(bookingId);
			return "Booking Deleted!!";
		} else
			throw new RecordNotFoundException("Booking not found for the entered BookingID");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.org.service.BookingService#displayAllBooking() show all booking
	 */
	@Override
	public Iterable<Booking> displayAllBooking() {

		return bookingDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.org.service.BookingService#findBookingById(java.math.BigInteger)
	 * find booking by ID
	 */
	@Override
	public ResponseEntity<?> findBookingById(long bookingId) {
		Optional<Booking> findById = bookingDao.findById(bookingId);
		try {
			if (findById.isPresent()) {
				Booking findBooking = findById.get();
				return new ResponseEntity<Booking>(findBooking, HttpStatus.OK);
			} else
				throw new RecordNotFoundException("No record found with ID " + bookingId);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
