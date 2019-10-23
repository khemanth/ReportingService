package com.hackerone.demo.reporting.exception;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RestException extends RuntimeException {


	private static final long serialVersionUID = 4L;
	
	private Object[] args;
	private String message;
	private HttpStatus httpStatus;
	
	public Object[] getArgs() {
		return this.args = Arrays.copyOf(args,args.length);
	}
	
	public RestException( String message, HttpStatus httpStatus, Object[] args) {

		this.message= message;
		this.httpStatus=httpStatus;
		this.args= Arrays.copyOf(args,args.length);
	}

}
