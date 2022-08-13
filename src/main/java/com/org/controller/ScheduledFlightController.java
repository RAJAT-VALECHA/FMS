package com.org.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.exceptions.RecordNotFoundException;
import com.org.exceptions.ScheduledFlightNotFoundException;
import com.org.model.ScheduledFlight;
import com.org.service.AirportService;
import com.org.service.ScheduledFlightService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/scheduledFlight")
public class ScheduledFlightController {
	/*
	 * Creating Service object
	 */
	@Autowired
	ScheduledFlightService scheduleFlightService;

	@Autowired
	AirportService airportService;



	/*
	 * Controller for adding Scheduled Flights
	 */
	@PostMapping("/add")
	public ResponseEntity<String> addSF(@RequestBody ScheduledFlight scheduledFlight){
		scheduleFlightService.addScheduledFlight(scheduledFlight);
		return new ResponseEntity<String>("added Flight." ,HttpStatus.OK);
	}

	@PutMapping("/modify")
	public ResponseEntity<?> modifyScheduleFlight(@ModelAttribute ScheduledFlight scheduleFlight) {
		ScheduledFlight modifySFlight = scheduleFlightService.modifyScheduledFlight(scheduleFlight);
		if (modifySFlight == null) {
			return new ResponseEntity<>("Flight not modified", HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<ScheduledFlight>(modifySFlight, HttpStatus.OK);
		}

	}

	/*
	 * Controller for deleting existing Scheduled Flights
	 */
	@DeleteMapping("/delete")
	public String deleteSF(@RequestParam long flightId) throws RecordNotFoundException {
		return scheduleFlightService.removeScheduledFlight(flightId);
	}

	/*
	 * Controller for viewing a Scheduled Flight by ID
	 */
	@GetMapping("/search")
	@ExceptionHandler(ScheduledFlightNotFoundException.class)
	public ResponseEntity<?> viewSF(@RequestParam long flightId) throws ScheduledFlightNotFoundException {
		ScheduledFlight searchSFlight = scheduleFlightService.viewScheduledFlight(flightId);
		if (searchSFlight == null) {
			return new ResponseEntity<>("Flight not present", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<ScheduledFlight>(searchSFlight, HttpStatus.OK);
		}
	}

	/*
	 * Controller for viewing all Scheduled Flights
	 */
	@GetMapping("/viewAll")
	public Iterable<ScheduledFlight> viewAllSF() {
		return scheduleFlightService.viewAllScheduledFlights();
	}
	

}
