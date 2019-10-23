package com.hackerone.demo.reporting.utils;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
	
	private String message;
	private Integer status;
	private ZonedDateTime timeStamp;
	private String errorCode;
	private String path;

}
