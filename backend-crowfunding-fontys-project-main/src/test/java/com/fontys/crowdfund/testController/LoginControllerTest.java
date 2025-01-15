package com.fontys.crowdfund.testController;

import com.fontys.crowdfund.business.LoginService;
import com.fontys.crowdfund.controller.impl.LoginControllerImpl;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOLogin;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @InjectMocks
    private LoginControllerImpl loginController;

    @Mock
    private LoginService loginService;

    private InputDTOLogin testLoginRequest;
    private OutputDTOLogin testLoginResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testLoginRequest = new InputDTOLogin("test@example.com", "password123");
        testLoginResponse = OutputDTOLogin.builder().accessToken("mockAccessToken").build();
    }

    @Test
    void testLogin_Success() {
        // Arrange
        when(loginService.login(testLoginRequest)).thenReturn(testLoginResponse);

        // Act
        ResponseEntity<OutputDTOLogin> response = loginController.login(testLoginRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("mockAccessToken", response.getBody().getAccessToken());
        verify(loginService, times(1)).login(testLoginRequest);
    }

    @Test
    void testLogin_InvalidCredentials() {
        // Arrange
        when(loginService.login(testLoginRequest)).thenThrow(new RuntimeException("Invalid Credentials"));

        // Act & Assert
        try {
            loginController.login(testLoginRequest);
        } catch (RuntimeException e) {
            assertEquals("Invalid Credentials", e.getMessage());
        }
        verify(loginService, times(1)).login(testLoginRequest);
    }
}
