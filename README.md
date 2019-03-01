# RESTFUL API for performing CRUD operations on a Country resource

RESTFUL API for performing CRUD operations on a Country resource. The project uses Spring Boot, Java 8 and JUnit/Mockito 
for testing.
 
 ## Building Project
 1. Open project in IDE of choice
 2. Set your datasource properties in `applications.properties`
 3. Run `Application`
 4. **N/B:** There is no explicit `/login` endpoint. Spring security internally maps requests to the `/login` URL for 
 authentication and authorization
 5. Run tests with `mvn clean verify`
 
API documentation will be available at http://localhost:8080/swagger-ui.html