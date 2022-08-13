package com.org.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class RecordAlreadyPresentException extends RuntimeException {
	public RecordAlreadyPresentException() {
	}
	public RecordAlreadyPresentException(String s) {
		super(s);
	}
}
