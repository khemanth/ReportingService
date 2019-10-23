#Reporting Service

* Create REST services for performing CRUD operations on the Reports Object.


* Reports entity contains the columns :

      	reportId	( String - UUID Auto-Generated )
      	title		( String - Title of the Report )
     	description	( String - Description of the Report )
      	createdBy	( String - Created by user )
      	createdDate	( ZonedDatetime - Created date-time )
      	updatedBy	( String - Updated by user  )
      	updatedDate	( ZonedDatetime - Updated date-time  )
      
 
* Enabled the basic-authentication,which requires the caller to pass a header parameter "ACCESS_TOKEN".


* Currently I have hard-coded the token to be static string "NZGAGBDFQLN3CRZPF2O0".


* The Reports are stored in inMemory H2 database


* To start the application download the jar provided and navigate to the location in terminal and use below command : 

		java -jar reporting-0.0.1-SNAPSHOT.jar


* To access the services use the Swagger UI for the application :

		http://localhost:8080/swagger-ui.html#
	
* Postman collection for calling the service

		https://www.getpostman.com/collections/310a4bb2ab38e2996962


