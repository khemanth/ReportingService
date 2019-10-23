package com.hackerone.demo.reporting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hackerone.demo.reporting.domain.Reports;

@Repository
public interface ReportsRepository extends JpaRepository<Reports, String>{
	
void deleteByReportId(@Param(value="reportId") String reportId);

Reports getByReportId(@Param(value="reportId") String reportId);


}
