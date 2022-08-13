package com.org.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception {
	public UserNotFoundException() {

	}
	public UserNotFoundException(String s) {
		super(s);
	}
}
