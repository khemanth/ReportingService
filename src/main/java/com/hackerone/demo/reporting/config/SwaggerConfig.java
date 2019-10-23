package com.hackerone.demo.reporting.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
    public Docket api(
    		@Value("Reporting Service REST API") String title,
    		@Value("Reporting Service REST API documentation for HackerOne Demo ") String description,
    		@Value("") String apiVersion,
    		@Value("") String termsOfServiceUrl,
    		@Value("Hemanth Kumar") String contactName,
    		@Value("https://github.com/") String contactUrl,
    		@Value("khemanth142@gmail.com") String contactEmail,
    		@Value("") String license,
    		@Value("") String licenseUrl
    		) {
		
		ApiInfo apiInfo = new ApiInfo(title, description, apiVersion, termsOfServiceUrl,
				new Contact(contactName, contactUrl, contactEmail), license, licenseUrl, Collections.emptyList());
		// Adding Header
		ParameterBuilder aParameterBuilder = new ParameterBuilder();
		aParameterBuilder.name("ACCESS_TOKEN").modelRef(new ModelRef("string")).parameterType("header")
				.defaultValue("NZGAGBDFQLN3CRZPF2O0").required(true).build();
		List<Parameter> aParameters = new ArrayList<Parameter>();
		aParameters.add(aParameterBuilder.build());
		

		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.hackerone.demo")).build().apiInfo(apiInfo)
				.useDefaultResponseMessages(false)
				.pathMapping("").globalOperationParameters(aParameters);

	}


}
