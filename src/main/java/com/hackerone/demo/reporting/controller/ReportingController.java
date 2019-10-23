/**
 * 
 */
package com.hackerone.demo.reporting.controller;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.ws.rs.Consumes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hackerone.demo.reporting.domain.Reports;
import com.hackerone.demo.reporting.exception.ReportingServiceException;
import com.hackerone.demo.reporting.service.ReportService;
import com.hackerone.demo.reporting.utils.CommonUtils;
import com.hackerone.demo.reporting.utils.ReportingServiceConstants;
import com.hackerone.demo.reporting.utils.SuccessResponse;

/**
 * @author neo
 *
 */

 @RestController
 public class ReportingController {

	private final Logger LOGGER = LogManager.getLogger(ReportingController.class);

	@Autowired
	ReportService reportService;

	@PostMapping("/report/details/save")
	public ResponseEntity<?> createReport(@Valid @RequestBody Reports report) throws ReportingServiceException {
		String reportId = null;

		if (Objects.nonNull(report)) {
			reportId = reportService.createReportDetails(report);
	        LOGGER.info(ReportingServiceConstants.REPORT_CREATED_SUCCESSFULLY + reportId );
		} else {
	        LOGGER.info(ReportingServiceConstants.REQUEST_BODY_EMPTY);
			throw new ReportingServiceException(HttpStatus.NO_CONTENT, ReportingServiceConstants.REQUEST_BODY_EMPTY);
		}

		SuccessResponse response = new SuccessResponse(
				(ReportingServiceConstants.REPORT_CREATED_SUCCESSFULLY) + reportId, HttpStatus.OK.value(),
				ZonedDateTime.now());
		return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);

	}

	@GetMapping("/report/details")
	private ResponseEntity<?> getAllReports() throws ReportingServiceException {

		List<Reports> report = reportService.getAllReports();
		if (report.size() != 0)
			return ResponseEntity.ok(report);
		else {
			throw new ReportingServiceException(HttpStatus.NOT_FOUND, ReportingServiceConstants.NO_REPORTS_IN_DB);

		}
	}

	@GetMapping("/report/details/{reportId}")
	private ResponseEntity<?> getReport(@PathVariable("reportId") String reportId) throws ReportingServiceException {

		if (Objects.nonNull(reportId)) {
			Reports report = reportService.getReportDetails(reportId);
			if (Objects.nonNull(report))
				return ResponseEntity.ok(report);
		}
		throw new ReportingServiceException(HttpStatus.NOT_FOUND,
				ReportingServiceConstants.NO_REPORT_FOUND_WITH_ID + reportId);

	}

	//@PatchMapping("/report/details/update/{reportId}")
	@PutMapping("/report/details/update/{reportId}")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<?> updateReport(@PathVariable("reportId") String reportId, @RequestBody Reports report)
			throws ReportingServiceException, JsonMappingException, JsonProcessingException {

		Reports updatedRecord = null;

		if (Objects.nonNull(report)) {
			Reports recordOptional = reportService.getReportDetails(reportId);

			if (Objects.nonNull(recordOptional)) {
				CommonUtils.copyNonNullProperties(report, recordOptional);
				updatedRecord = reportService.updateReportDetails(recordOptional, reportId);
				return ResponseEntity.ok(updatedRecord);
			} else {
				throw new ReportingServiceException(HttpStatus.NOT_FOUND,
						ReportingServiceConstants.NO_REPORT_FOUND_WITH_ID + reportId);

			}
		}
		throw new ReportingServiceException(HttpStatus.EXPECTATION_FAILED,
				ReportingServiceConstants.FAILED_TO_UPDATE + reportId);

	}

	@DeleteMapping("/report/details/delete/{reportId}")
	private ResponseEntity<?> deleteReport(@PathVariable("reportId") String reportId) throws ReportingServiceException {

		if (Objects.nonNull(reportId)) {

			Reports recordOptional = reportService.getReportDetails(reportId);
			if (Objects.nonNull(recordOptional)) {
				if (reportService.deleteReportDetails(reportId)) {
					SuccessResponse response = new SuccessResponse(
							(ReportingServiceConstants.REPORT_DELETED_SUCCESSFULLY) + reportId, HttpStatus.OK.value(),
							ZonedDateTime.now());
					return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
				}
			} else {
				throw new ReportingServiceException(HttpStatus.NOT_FOUND,
						ReportingServiceConstants.NO_REPORT_FOUND_TO_DELETE + reportId);

			}
		}
		throw new ReportingServiceException(HttpStatus.EXPECTATION_FAILED,
				ReportingServiceConstants.FAILED_TO_DELETE + reportId);

	}

}
