package com.org.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class AdminNotFoundException extends Exception {

	public AdminNotFoundException() {

	}
	public AdminNotFoundException(String s) {
		super(s);
	}
}