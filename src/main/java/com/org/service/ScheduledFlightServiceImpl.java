package com.org.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.dao.ScheduledFlightDao;
import com.org.exceptions.RecordNotFoundException;
import com.org.exceptions.ScheduledFlightNotFoundException;
import com.org.model.ScheduledFlight;

@Service
public class ScheduledFlightServiceImpl implements ScheduledFlightService {

	@Autowired
	ScheduledFlightDao dao;


	@Autowired
	BookingService bookingService;

	/*
	 * Service method to add new Scheduled flight to database
	 */
	@Override
	public ScheduledFlight addScheduledFlight(ScheduledFlight scheduledFlight) {
		return dao.save(scheduledFlight);
	}

	/*
	 * Service method to modify existing Scheduled flight in database
	 */
	@Override
	public ScheduledFlight modifyScheduledFlight(ScheduledFlight scheduleFlight) {
		ScheduledFlight updateScheduleFlight = dao.findById(scheduleFlight.getScheduleFlightId()).get();
		dao.save(updateScheduleFlight);
		return scheduleFlight;
	}

	/*
	 * Service method to remove existing Scheduled flight from database
	 */
	@Override
	public String removeScheduledFlight(long flightId) throws RecordNotFoundException {
		if (flightId == 0)
			throw new RecordNotFoundException("Enter flight Id");
		Optional<ScheduledFlight> scheduleFlight = dao.findById(flightId);
		if (!scheduleFlight.isPresent())
			throw new RecordNotFoundException("Enter a valid Flight Id");
		else {
			// try {
			// cancelBookings(flightId);
			// } catch (RecordNotFoundException e) {
			// System.out.println("No Bookings Found");
			// }
			dao.deleteById(flightId);
		}
		return "Scheduled flight with ID " + flightId + " is not found";
	}

	// @Override
	// public boolean cancelBookings(BigInteger flightId) throws
	// RecordNotFoundException {
	// Iterable<Booking> bookingList = bookingService.displayAllBooking();
	// for (Booking booking : bookingList) {
	// if (booking.getScheduleFlight().getScheduleFlightId().equals(flightId)) {
	// bookingService.deleteBooking(booking.getBookingId());
	// }
	// }
	// return true;
	// }

	/*
	 * Service method to view all Scheduled flights in database
	 */
	@Override
	public Iterable<ScheduledFlight> viewAllScheduledFlights() {
		return dao.findAll();
	}

	/*
	 * Service method to view a Scheduled flight by ID from database
	 */
	@Override
	public ScheduledFlight viewScheduledFlight(long flightId) throws ScheduledFlightNotFoundException {
		if (flightId == 0)
			throw new ScheduledFlightNotFoundException("Enter flight Id");
		Optional<ScheduledFlight> scheduleFlight = dao.findById(flightId);
		if (!scheduleFlight.isPresent())
			throw new ScheduledFlightNotFoundException("Enter a valid Flight Id");
		else
			return scheduleFlight.get();
	}

}
