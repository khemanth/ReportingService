package com.hackerone.demo.reporting.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReportingServiceException extends Exception {
	
	private static final long serialVersionUID = 3L;

	private HttpStatus httpStatus;
	private String errorMessage;
	
	public ReportingServiceException(HttpStatus httpStatus, String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}

}
