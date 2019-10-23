package com.hackerone.demo.reporting.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hackerone.demo.reporting.domain.Reports;
import com.hackerone.demo.reporting.exception.ReportingServiceException;

@Service
public interface ReportService {
	
	String createReportDetails(Reports report) throws ReportingServiceException;
	
	Reports getReportDetails(String reportId) throws ReportingServiceException;
	
	List<Reports> getAllReports() ;

	Reports updateReportDetails(Reports report,String reportId) throws ReportingServiceException;

	boolean deleteReportDetails(String reportId) throws ReportingServiceException;

}
