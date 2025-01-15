---
sidebar_position: 1
---

# Application Configuration

Below is the configuration file that outlines the setup for using JPA within the application:

``` properties, title="src/main/resources/application.properties"
# Database configuration for MySQL (I host it in Docker)
spring.datasource.url=jdbc:mysql://localhost:3307/crowdfund_db
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA configuration (Hibernate)
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.connection.autocommit=false
```