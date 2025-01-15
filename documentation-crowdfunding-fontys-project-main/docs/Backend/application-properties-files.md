---
sidebar_position: 11
sidebar_label: Spring Boot Profiles
---

# Managing Environment-Specific `application.properties` Files

Spring Boot allows the use of multiple `application.properties` files to manage different environments such as local development, Docker deployment, and testing. This document explains how to configure and structure `application.properties` files for each environment and utilize profiles to switch between them.

---

## **1. Local Development Configuration**

This configuration is used during local development. It connects to a MySQL database hosted locally (e.g., via Docker) and uses sensitive credentials stored in a `.env` file.

```properties title="application-local.properties"
# Import configuration from .env
spring.config.import=optional:file:.env[.properties]

# Database configuration for MySQL (hosted locally)
spring.datasource.url=jdbc:mysql://localhost:3307/crowdfund_db
spring.datasource.username=${MYSQL_USERNAME}
spring.datasource.password=${MYSQL_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA configuration (Hibernate)
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.connection.autocommit=false

# JWT secret
jwt.secret=${JWT_SECRET}
```

### **Key Points:**
- **Database:** Connects to a locally running MySQL database on port `3307`.
- **Environment Variables:** Credentials (`MYSQL_USERNAME`, `MYSQL_PASSWORD`, `JWT_SECRET`) are loaded from an `.env` file.
- **Hibernate:** Automatically updates the schema and logs SQL queries for debugging.

---

## **2. Docker Deployment Configuration**

This configuration is tailored for a Dockerized environment, where the application communicates with other containers (e.g., a MySQL container). 

```properties title="application-docker.properties"
# Import configuration from .env
spring.config.import=optional:file:.env[.properties]

# Database configuration for MySQL (hosted in Docker)
spring.datasource.url=jdbc:mysql://raisehub_database:3306/crowdfund_db
spring.datasource.username=${MYSQL_USERNAME}
spring.datasource.password=${MYSQL_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA configuration (Hibernate)
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.connection.autocommit=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Server configuration
server.port=8080

# Connection pool settings
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.max-lifetime=1800000

# Logging configuration
logging.level.root=INFO
logging.level.org.springframework=DEBUG
logging.file.name=/tmp/spring-docker.log

# JWT secret
jwt.secret=${JWT_SECRET}
```

### **Key Points:**
- **Database:** Connects to a MySQL container (`raisehub_database`) via the Docker network.
- **Connection Pooling:** Tuned for containerized environments using HikariCP.
- **Logging:** Logs application events to `/tmp/spring-docker.log`.
- **Port:** The application listens on port `8080`.

---

## **3. Testing Configuration**

This configuration is used for [unit tests](../Backend/testing/unit_testing.md) and [integration tests](../Backend/testing/integration_testing.md). It relies on an in-memory H2 database for fast and isolated testing.

```properties title="application-test.properties"
# H2 in-memory database configuration
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

### **Key Points:**
- **In-Memory Database:** H2 is used for lightweight and temporary database operations during testing.
- **Schema Initialization:** The schema is recreated for every test run (`ddl-auto=create`).
- **Fast Execution:** No external dependencies; everything runs in memory.

---

## **4. Switching Between Configurations**

### **Using Spring Profiles**

Spring Boot profiles allow you to load different configuration files based on the active profile. Profile-specific files must follow the naming convention `application-{profile}.properties`.

1. **Set Active Profile:**
   - Use the `spring.profiles.active` property to specify the profile:
     ```properties
     spring.profiles.active=local
     ```
   - In Docker, set `spring.profiles.active=docker`.

2. **Profile-Specific Files:**
   - `application-local.properties` for local development.
   - `application-docker.properties` for Docker environments.
   - `application-test.properties` for testing.

### **How Profiles Work:**

- The `spring.profiles.active` property determines which profile-specific file Spring Boot uses.
- For example:
  - `spring.profiles.active=local` loads `application-local.properties`.
  - `spring.profiles.active=docker` loads `application-docker.properties`.
  - Default behavior loads the base `application.properties` if no profile is specified.

---

### **Summary**

| **Environment** | **File Name**                | **Key Configuration**                                            |
|------------------|------------------------------|-------------------------------------------------------------------|
| Local Development | `application-local.properties` | Connects to a locally hosted MySQL database.                     |
| Docker Deployment | `application-docker.properties` | Uses Dockerized MySQL database.                                  |
| Testing           | `application-test.properties`  | Uses an in-memory H2 database for fast and isolated testing.      |

This setup ensures that the application is flexible and maintains environment-specific configurations in an organized and maintainable way.