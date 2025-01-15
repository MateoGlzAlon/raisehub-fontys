package com.fontys.crowdfund.testController;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fontys.crowdfund.controller.impl.NotificationsControllerImpl;
import com.fontys.crowdfund.persistence.websocketnotification.NotificationMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

class NotificationControllerTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private NotificationsControllerImpl notificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    void testSendNotificationToUsers() {
        // Arrange
        NotificationMessage message = NotificationMessage.builder().build();

        // Mock the messagingTemplate behavior
        doNothing().when(messagingTemplate).convertAndSend("/project/payment", message);

        // Act
        ResponseEntity<Void> response = notificationController.sendNotificationToUsers(message);
        // Assert
        assertEquals(201, response.getStatusCode().value());  // Verify that the status code is 201 Created.
        verify(messagingTemplate, times(1)).convertAndSend("/project/payment", message);  // Verify the interaction with SimpMessagingTemplate.
    }
}
