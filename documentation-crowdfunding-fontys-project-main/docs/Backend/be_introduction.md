---
sidebar_position: 1
sidebar_label: Introduction
---

# Back-end Introduction

This section provides an overview of the back-end architecture and tools used for the "RaiseHub" project. It covers the technologies, development practices, and structure of the back-end codebase, along with instructions for setting up the development environment.

## Overview

The back-end of the "RaiseHub" application is a robust REST API built using modern Java development frameworks. It handles data management, business logic, and API services to support the front-end application. The goal is to deliver secure, scalable, and efficient back-end services.

## Technologies Used

The back-end is built using the following technologies:
- **Spring Boot**: A Java-based framework for building production-ready applications with minimal configuration.
- **Gradle**: For project build automation and dependency management.
- **Spring Data JPA**: Simplifies database interactions and ORM (Object-Relational Mapping).
- **MySQL**: A relational database to store user and project data.
- **Docker**: For containerizing the application, ensuring consistency across environments.
- **SonarQube**: For static code analysis to improve code quality.

## Project Structure

The codebase follows a modular and layered structure, organized into different folders:
- **`src/main/java/`**: Contains the application code, structured as follows:
   - **`controller/`**: Manages HTTP requests and responses.
   - **`bussiness/`**: Contains business logic.
   - **`repository/`**: Interfaces with the database using JPA.
   - **`persistence/`**: Defines the application's data entities and DTOs.
- **`src/main/resources/`**: Stores configuration files such as `application.properties`.
- **`tests/`**: Includes unit tests and integration tests for validating application functionality.

## Setting Up the Back-end Development Environment

Follow these steps to set up the back-end for development:

1. **Prerequisites**:
   - Java 17
   - Gradle 8.1
   - MySQL
        

2. **Clone the Repository**:
   ```bash
   git clone https://github.com/MateoGlzAlon/ui-crowfunding-fontys-project.git
   ```

3. **Set Up the Database**:
   - Create a MySQL database named `crowdfund_db`.
   - Update the `application.properties` file with your database credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3307/crowdfund_db
     spring.datasource.username=${MYSQL_PASSWORD}
     spring.datasource.password=${MYSQL_USERNAME}
     ```
    The default port is 3307, you can change that in the application.properties file

    **[You can review the environment variables here](environment_variables.md)**

4. **Create an .env file**:
   Create an .env file in the root directory for the environment variables
   ```bash, title=".env"
    JWT_SECRET=<value, eg: DFEOXMB4XAQBIGLBN9FOQNMUTK3EZUYQLRIJ1R1ZNRQH0MPP>
    MYSQL_USERNAME=<value, eg: admin>
    MYSQL_PASSWORD=<value, eg: admin>
   ```

5. **Build the Application**:
   Run the following Gradle command to build the project:
   ```bash
   ./gradlew build
   ```

6. **Run the Application**:
   Start the Spring Boot application using:
   ```bash
   ./gradlew bootRun
   ```

##  Run with Docker (optional):
   - Download the image from the DockerHub repository:
     [DockerHub repository](https://hub.docker.com/r/mateogonzalezz/raisehub_backend/tags)
   - Run the container with the environment variables:
     ```bash
     docker run -d -e JWT_SECRET=<value> -e MYSQL_USERNAME=<value> -e MYSQL_PASSWORD=<value> --name raisehub_backend_container -p 8080:8080 mateogonzalezz/raisehub_backend:<tag>
     ```

    **[You can review the environment variables here](environment_variables.md)**
    
The application will be accessible at `http://localhost:8080` by default.

