package com.fontys.crowdfund.testController;

import com.fontys.crowdfund.business.PaymentService;
import com.fontys.crowdfund.controller.impl.PaymentControllerImpl;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOPayment;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOPayment;
import com.fontys.crowdfund.persistence.specialdto.OutputDonationNotification;
import com.fontys.crowdfund.persistence.specialdto.ProfilePaymentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PaymentControllerTest {

    @InjectMocks
    private PaymentControllerImpl paymentController;

    @Mock
    private PaymentService paymentService;

    private OutputDTOPayment testPayment;
    private InputDTOPayment inputPayment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testPayment = OutputDTOPayment.builder()
                .paymentId(1)
                .projectId(1)
                .backerEmail("test@example.com")
                .amountFunded(100.0)
                .paymentDate(new Date())
                .build();

        inputPayment = InputDTOPayment.builder()
                .backerId(1)
                .projectId(1)
                .amountFunded(100.0F)
                .paymentDate(new Date())
                .build();
    }

    @Test
    void testGetAllPayments() {
        // Arrange
        when(paymentService.getAllPayments()).thenReturn(Arrays.asList(testPayment));

        // Act
        List<OutputDTOPayment> payments = paymentController.getAllPayments();

        // Assert
        assertEquals(1, payments.size());
        assertEquals("test@example.com", payments.get(0).getBackerEmail());
        verify(paymentService, times(1)).getAllPayments();
    }

    @Test
    void testGetPaymentById() {
        // Arrange
        when(paymentService.getPaymentById(1)).thenReturn(testPayment);

        // Act
        ResponseEntity<OutputDTOPayment> response = paymentController.getPaymentById(1);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals("test@example.com", response.getBody().getBackerEmail());
        verify(paymentService, times(1)).getPaymentById(1);
    }

    @Test
    void testGetPaymentsToProjectById() {
        // Arrange
        when(paymentService.getPaymentsByProjectId(1)).thenReturn(Arrays.asList(testPayment));

        // Act
        ResponseEntity<List<OutputDTOPayment>> response = paymentController.getPaymentsToProjectById(1);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        verify(paymentService, times(1)).getPaymentsByProjectId(1);
    }

    @Test
    void testGetPaymentNotificationsToProjectById() {
        // Arrange
        OutputDonationNotification notification = OutputDonationNotification.builder()
                .backerName("John Doe")
                .paymentDate(new Date())
                .amount(100.0F)
                .build();
        when(paymentService.getPaymentNotificationsByProjectId(1)).thenReturn(Arrays.asList(notification));

        // Act
        ResponseEntity<List<OutputDonationNotification>> response = paymentController.getPaymentNotificationsToProjectById(1);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        assertEquals("John Doe", response.getBody().get(0).getBackerName());
        verify(paymentService, times(1)).getPaymentNotificationsByProjectId(1);
    }

    @Test
    void testGetPaymentsByUserIdForProfile() {
        // Arrange
        int userId = 1;
        String filter = "This_month";
        int page = 1;
        int size = 5;

        ProfilePaymentDTO profilePayment = ProfilePaymentDTO.builder()
                .id(1)
                .projectId(1)
                .projectName("Test Project")
                .projectOwnerName("John Doe")
                .paymentDate(new Date())
                .amountFunded(100.0F)
                .build();

        Page<ProfilePaymentDTO> profilePaymentPage = new PageImpl<>(List.of(profilePayment));
        when(paymentService.getPaymentsByUserIdForProfile(userId, filter, page, size)).thenReturn(profilePaymentPage);

        // Act
        ResponseEntity<Page<ProfilePaymentDTO>> response = paymentController.getPaymentsByUserIdForProfile(userId, filter, page, size);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().getContent().size());
        ProfilePaymentDTO returnedPayment = response.getBody().getContent().get(0);
        assertEquals("Test Project", returnedPayment.getProjectName());
        assertEquals(1, returnedPayment.getProjectId());
        assertEquals("John Doe", returnedPayment.getProjectOwnerName());

        verify(paymentService, times(1)).getPaymentsByUserIdForProfile(userId, filter, page, size);
    }


    @Test
    void testGetTotalPayments() {
        when(paymentService.getTotalPaymentsByUserId(1, "Time")).thenReturn(100);

        // Act
        ResponseEntity<Integer> response = paymentController.getTotalPaymentsByUserId(1, "Time");

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(100, response.getBody());
        verify(paymentService, times(1)).getTotalPaymentsByUserId(1, "Time");
    }

    @Test
    void testCreatePayment() {
        // Arrange
        when(paymentService.createPayment(inputPayment)).thenReturn(testPayment);

        // Act
        ResponseEntity<OutputDTOPayment> response = paymentController.createPayment(inputPayment);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals("test@example.com", response.getBody().getBackerEmail());
        verify(paymentService, times(1)).createPayment(inputPayment);
    }

    @Test
    void testDeletePayment() {
        // Arrange
        doNothing().when(paymentService).deletePaymentById(1);

        // Act
        ResponseEntity<Void> response = paymentController.deletePayment(1);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        verify(paymentService, times(1)).deletePaymentById(1);
    }
}
