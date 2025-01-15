---
sidebar_position: 1
sidebar_label: Unit Testing
---

# Unit Testing

## Overview

Unit testing is a key part of the software development lifecycle. It ensures that individual components or units of code perform as expected. This guide explains how to create unit tests for both service and controller layers, focusing on **User** functionality.

## Directory Structure

Based on the provided project structure, unit tests are organized into:

- `testBusiness`
  - Contains tests for service layer components.
  - Examples: `LoginServiceTest`, `PaymentServiceTest`, `ProjectServiceTest`, `UserServiceTest`.
- `testController`
  - Contains tests for controller layer components.
  - Examples: `LoginControllerTest`, `PaymentControllerTest`, `ProjectControllerTest`, `UserControllerTest`.

### Example Test Classes

#### 1. Service Layer: Testing Business Logic

```java
@Test
@DisplayName("Should return a user by Id")
void get_user_by_id() {
    // Arrange
    when(userRepository.findById(1)).thenReturn(savedUser);

    // Act
    OutputDTOUser u1 = userService.getUserById(1);

    // Assert
    assertEquals(u1.getEmail(), outputUser.getEmail());
}
```

This test verifies that the `UserService` fetches user details based on their ID:
- **Arrange**: Mock the repository to return a predefined user.
- **Act**: Call the service method.
- **Assert**: Confirm the result matches the expected user data.

#### 2. Controller Layer: Testing API Endpoints

```java
@Test
void testGetUserById() {
    // Arrange
    when(userService.getUserById(1)).thenReturn(testUser);

    // Act
    ResponseEntity<OutputDTOUser> response = userController.getUserById(1);

    // Assert
    assertEquals(200, response.getStatusCode().value());
    assertEquals("John Doe", response.getBody().getName());
    verify(userService, times(1)).getUserById(1);
}
```

This test validates the behavior of the `UserController` when retrieving user data:
- **Arrange**: Mock the service layer to return test data.
- **Act**: Invoke the controller endpoint.
- **Assert**: Verify the HTTP response status, body content, and service method invocation.

### Testing Strategy

- **Service Layer Tests**: Concentrate on business logic, mocking external dependencies like repositories.
- **Controller Layer Tests**: Check the HTTP request-response cycle and confirm proper delegation to service methods.