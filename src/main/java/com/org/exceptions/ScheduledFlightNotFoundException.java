package com.org.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ScheduledFlightNotFoundException extends RuntimeException {

	public ScheduledFlightNotFoundException(String str) {
		super(str);
	}
	public ScheduledFlightNotFoundException() {
	}

}
