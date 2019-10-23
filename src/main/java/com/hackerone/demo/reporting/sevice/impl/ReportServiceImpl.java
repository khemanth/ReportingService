package com.hackerone.demo.reporting.sevice.impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackerone.demo.reporting.domain.Reports;
import com.hackerone.demo.reporting.exception.ReportingServiceException;
import com.hackerone.demo.reporting.exception.RestException;
import com.hackerone.demo.reporting.repository.ReportsRepository;
import com.hackerone.demo.reporting.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	ReportsRepository reportsRepository;
	
	public String createReportDetails(Reports report) throws ReportingServiceException {

		Reports record = null;
		report.setCreatedDate(Objects.isNull(report.getCreatedDate()) ? ZonedDateTime.now() : report.getCreatedDate());
		report.setUpdatedBy(Objects.isNull(report.getUpdatedBy()) ? "ADMIN" : report.getUpdatedBy());

		record = reportsRepository.save(report);

		return record.getReportId();
	}
	
	public Reports getReportDetails(String reportId) throws RestException {

		Reports record = reportsRepository.getByReportId(reportId);
		return record;

	}
	
	public List<Reports> getAllReports() throws RestException {
		
		List<Reports> record  = reportsRepository.findAll();	
		return record;
		
	}
	
	public Reports updateReportDetails(Reports report,String reportId) {
		
		report.setReportId(reportId);
		report.setUpdatedDate(Objects.isNull(report.getUpdatedDate()) ? ZonedDateTime.now() : report.getUpdatedDate());
		report.setUpdatedBy(Objects.isNull(report.getUpdatedBy()) ? "ADMIN" : report.getUpdatedBy());

		Reports record  = reportsRepository.save(report);
		return record;
		
	}
	
	public boolean deleteReportDetails(String reportId) {

		boolean result = false;
		
		reportsRepository.deleteById(reportId);
		result = true;

		return result;

	}

}
