package com.org.service;

import com.org.exceptions.RecordNotFoundException;
import com.org.exceptions.ScheduledFlightNotFoundException;
import com.org.model.ScheduledFlight;

public interface ScheduledFlightService {
	public ScheduledFlight addScheduledFlight(ScheduledFlight scheduledFlight);

	public ScheduledFlight modifyScheduledFlight(ScheduledFlight scheduledFlight);

	public String removeScheduledFlight(long id) throws RecordNotFoundException;

	public Iterable<ScheduledFlight> viewAllScheduledFlights();

	public ScheduledFlight viewScheduledFlight(long id) throws ScheduledFlightNotFoundException;
	// boolean cancelBookings(BigInteger flightId) throws RecordNotFoundException;

}
