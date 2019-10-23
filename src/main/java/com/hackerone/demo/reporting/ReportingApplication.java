package com.hackerone.demo.reporting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class ReportingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportingApplication.class, args);
	}

}
