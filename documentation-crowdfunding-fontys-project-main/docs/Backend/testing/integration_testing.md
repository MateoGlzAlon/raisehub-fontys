---
 sidebar_position: 2
 sidebar_label: Integration Testing
---

# Integration Testing
## Overview
Integration testing evaluates how different parts of the application work together. This guidefocuses on integration tests for the **Project** API endpoints, demonstrating the use of Spring Bootand the H2 in-memory database for testing.
## Configuration
### application-test.properties
The application uses an H2 in-memory database for testing, configured as follows:
```properties title="application-test.properties"w
# Database Configuration
spring.datasource.url=jdbc:h2:mem:public
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
# JWT secret
jwt.secret=${JWT_SECRET}
```
The data is loaded via a file called data.sql located in `test/resources/data.sql`.
### Key Features
- **H2 In-Memory Database**: Ensures tests do not affect production data.
- **DDL Auto Create**: Automatically generates the database schema.
- **SQL Initialization**: Allows preloading test data when necessary.
## Example Integration Test
### Test: `find_project_by_id_endpoint`
This test verifies that the `/projects/1` endpoint returns the correct project details.
```java
@Test
void find_project_by_id_endpoint() throws Exception {
    mockMvc.perform(get("/projects/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.name", is("Community Garden Project")));
}
```
#### Key Details
- **HTTP Method**: `GET`
- **Endpoint**: `/projects/1`
- **Assertions**:
  - Status code is `200`.
  - JSON response contains the project ID and name.