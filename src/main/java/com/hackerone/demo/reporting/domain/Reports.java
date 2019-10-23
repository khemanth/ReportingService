package com.hackerone.demo.reporting.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Data
@Table(name="REPORTS")
public class Reports implements Serializable{
	
	private static final long serialVersionUID =1L;
	
	@Id
	@GeneratedValue(generator="reportId")
	@GenericGenerator(name="reportId", strategy="org.hibernate.id.UUIDGenerator")
	@Column(name="REPORT_ID")
	private String reportId;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="CREATED_DATE")
	private ZonedDateTime createdDate;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;
	
	@Column(name="UPDATED_DATE")
	private ZonedDateTime updatedDate;
	

}
