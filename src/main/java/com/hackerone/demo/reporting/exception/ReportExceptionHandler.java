package com.hackerone.demo.reporting.exception;

import static java.util.Objects.nonNull;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.hackerone.demo.reporting.utils.ErrorResponse;

@ControllerAdvice
public class ReportExceptionHandler {


	@SuppressWarnings("serial")
	final Map<Class<?>, HttpStatus> STATUS_BY_EXCEPTION_CLASS = new HashMap<Class<?>, HttpStatus>() {
		{
			put(HttpMessageNotReadableException.class, HttpStatus.BAD_REQUEST);
			put(HttpRequestMethodNotSupportedException.class, HttpStatus.METHOD_NOT_ALLOWED);
			put(MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST);
			put(IllegalArgumentException.class, HttpStatus.BAD_REQUEST);
			put(NullPointerException.class, HttpStatus.INTERNAL_SERVER_ERROR);
			put(InvalidFormatException.class, HttpStatus.NOT_ACCEPTABLE);
			put(ConstraintViolationException.class, HttpStatus.NOT_ACCEPTABLE);
			put(TypeMismatchException.class, HttpStatus.BAD_REQUEST);
			put(MissingServletRequestParameterException.class, HttpStatus.NOT_FOUND);
		}
	};

	@ExceptionHandler(InvalidFormatException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleInvalidFormatException(HttpServletRequest request,
			HttpServletResponse servletResponse, InvalidFormatException exception, Locale locale) {
		HttpStatus status = STATUS_BY_EXCEPTION_CLASS.get(InvalidFormatException.class);
		servletResponse.setStatus(status.value());
		String fieldName = null;
		String targetFieldType = null;
		// Get the Error Field Name
		for (InvalidFormatException.Reference reference : exception.getPath()) {
			fieldName = reference.getFieldName();
		}
		targetFieldType = exception.getTargetType().getTypeName().toString();
		if (nonNull(targetFieldType) && targetFieldType.contains(".")) {
			targetFieldType = targetFieldType.substring(targetFieldType.lastIndexOf(".") + 1);
		}

		ErrorResponse errorResponse = new ErrorResponse("InvalidFormatException " + targetFieldType + " " + fieldName,
				status.value(), ZonedDateTime.now(), status.getReasonPhrase(), request.getServletPath());
		return new ResponseEntity<>(errorResponse, status);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class, HttpMessageNotReadableException.class })
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleBindingErrors(final HttpServletRequest request,
			final HttpServletResponse response, final Exception ex, Locale locale) {
		ErrorResponse errorResponse = null;
		HttpStatus status = STATUS_BY_EXCEPTION_CLASS.get(ex.getClass());
		if (status == null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		List<String> errors = new ArrayList<String>();
		if (ex.getCause() instanceof HttpMessageNotReadableException) {
			if (ex.getCause().getCause() instanceof InvalidFormatException) {
				return handleInvalidFormatException(request, response,
						(InvalidFormatException) ex.getCause().getCause(), locale);
			}
		} else if (ex.getCause() instanceof InvalidFormatException) {
			return handleInvalidFormatException(request, response, (InvalidFormatException) ex.getCause(), locale);
		} else if (ex instanceof MethodArgumentNotValidException) {
			errors = handleMethodArgumentViolationException(request, response, (MethodArgumentNotValidException) ex,
					locale);
			errorResponse = new ErrorResponse(errors.get(0), status.value(), ZonedDateTime.now(),
					status.getReasonPhrase(), request.getServletPath());
		} else {
			response.setStatus(status.value());

			errorResponse = new ErrorResponse("BindingError", status.value(), ZonedDateTime.now(),
					status.getReasonPhrase(), request.getServletPath());
		}
		return new ResponseEntity<>(errorResponse, status);
	}

	private List<String> handleMethodArgumentViolationException(HttpServletRequest request,
			HttpServletResponse response, MethodArgumentNotValidException methodArgumentNotValidException,
			Locale locale) {
		BindingResult result = methodArgumentNotValidException.getBindingResult();
		List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
		HttpStatus status = STATUS_BY_EXCEPTION_CLASS.get(MethodArgumentNotValidException.class);
		response.setStatus(status.value());
		List<String> errors = new ArrayList<String>();

		// Building the constraint errors based on validation violations
		for (org.springframework.validation.FieldError fieldError : fieldErrors) {
			errors.add(fieldError.getDefaultMessage());
		}
		return errors;
	}

	@ExceptionHandler(RestException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleCustomException(final HttpServletRequest request,
			final HttpServletResponse response, final RestException ex, Locale locale) {
		HttpStatus status = ex.getHttpStatus();
		if (status == null)
			status = HttpStatus.INTERNAL_SERVER_ERROR;

		response.setStatus(status.value());

		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), status.value(), ZonedDateTime.now(),
				status.getReasonPhrase(), request.getServletPath());
		return new ResponseEntity<>(errorResponse, status);
	}

	@ExceptionHandler({ MissingServletRequestParameterException.class, ConstraintViolationException.class })
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(final HttpServletRequest request,
			final HttpServletResponse response, final MissingServletRequestParameterException ex, Locale locale) {
		HttpStatus status = STATUS_BY_EXCEPTION_CLASS.get(ConstraintViolationException.class);
		if (status == null)
			status = HttpStatus.INTERNAL_SERVER_ERROR;

		response.setStatus(status.value());

		ErrorResponse errorResponse = new ErrorResponse("Missing required Input parameter " + ex.getParameterName(),
				status.value(), ZonedDateTime.now(), status.getReasonPhrase(), request.getServletPath());

		return new ResponseEntity<>(errorResponse, status);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleMethodNotSupportedException(final HttpServletRequest request,
			final HttpServletResponse response, final HttpRequestMethodNotSupportedException ex, Locale locale) {
		HttpStatus status = STATUS_BY_EXCEPTION_CLASS.get(HttpRequestMethodNotSupportedException.class);
		if (status == null)
			status = HttpStatus.INTERNAL_SERVER_ERROR;

		response.setStatus(status.value());
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), status.value(), ZonedDateTime.now(),
				status.getReasonPhrase(), request.getServletPath());
		return new ResponseEntity<>(errorResponse, status);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleAllException(final HttpServletRequest request,
			final HttpServletResponse response, final Exception ex, Locale locale) {
		HttpStatus status = STATUS_BY_EXCEPTION_CLASS.get(ex.getClass());
		if (status == null)
			status = HttpStatus.INTERNAL_SERVER_ERROR;

		ErrorResponse errorResponse = new ErrorResponse(
				Objects.nonNull(ex.getMessage()) ? "Unexpected error occured while processing the request"
						: ex.getMessage(),
				status.value(), ZonedDateTime.now(), status.getReasonPhrase(), request.getServletPath());
		return new ResponseEntity<>(errorResponse, status);
	}

	@ExceptionHandler(ReportingServiceException.class)
	@ResponseBody
	public ResponseEntity<ErrorResponse> handleReportingServiceException(final HttpServletRequest request,
			final HttpServletResponse response, final ReportingServiceException ex, Locale locale) {
		HttpStatus status = ex.getHttpStatus();
		if (status == null)
			status = HttpStatus.INTERNAL_SERVER_ERROR;

		response.setStatus(status.value());
		ErrorResponse errorResponse = new ErrorResponse(ex.getErrorMessage(), status.value(), ZonedDateTime.now(),
				status.getReasonPhrase(), request.getServletPath());
		return new ResponseEntity<ErrorResponse>(errorResponse, status);
	}

}
