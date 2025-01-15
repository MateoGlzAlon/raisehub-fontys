package com.fontys.crowdfund.testBusiness;

import com.fontys.crowdfund.business.impl.PaymentServiceImpl;
import com.fontys.crowdfund.repository.PaymentRepository;
import com.fontys.crowdfund.repository.ProjectImagesRepository;
import com.fontys.crowdfund.repository.ProjectRepository;
import com.fontys.crowdfund.repository.UserRepository;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOPayment;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOPayment;
import com.fontys.crowdfund.persistence.entity.PaymentEntity;
import com.fontys.crowdfund.persistence.entity.ProjectEntity;
import com.fontys.crowdfund.persistence.entity.UserEntity;
import com.fontys.crowdfund.persistence.specialdto.OutputDonationNotification;
import com.fontys.crowdfund.persistence.specialdto.ProfilePaymentDTO;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectImagesRepository projectImagesRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private static UserEntity u1;
    private static ProjectEntity p1;
    private static PaymentEntity payment;

    @BeforeAll
    static void setUp1() {
        u1 = UserEntity.builder()
                .id(1)
                .email("user@email.com")
                .name("user name")
                .password("user password")
                .build();

        p1 = ProjectEntity.builder()
                .id(1)
                .description("project description")
                .dateCreated(new Date())
                .moneyRaised(150f)
                .fundingGoal(1000f)
                .type("project type")
                .location("project location")
                .user(u1)
                .build();

        payment = PaymentEntity.builder()
                .id(1)
                .paymentDate(new Date())
                .amount(50)
                .project(p1)
                .user(u1)
                .build();

    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should get all payments")
    void get_all_payments() {
        // Arrange
        PaymentEntity payment1 = new PaymentEntity();
        payment1.setId(1); // Set an ID
        payment1.setProject(p1);
        payment1.setUser(u1);
        PaymentEntity payment2 = new PaymentEntity();
        payment2.setId(2); // Set an ID
        payment2.setProject(p1);
        payment2.setUser(u1);

        when(paymentRepository.findAll()).thenReturn(List.of(payment1, payment2));

        // Act
        List<OutputDTOPayment> payments = paymentService.getAllPayments();

        // Assert
        assertEquals(2, payments.size());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should add payment and return output DTO")
    void add_payment() {
        // Arrange
        InputDTOPayment inputPayment = new InputDTOPayment();
        inputPayment.setBackerId(1);
        inputPayment.setPaymentDate(new Date());
        inputPayment.setProjectId(1);
        PaymentEntity savedPayment = new PaymentEntity();
        savedPayment.setId(1);
        UserEntity userEntity = UserEntity.builder().id(1).email("test@example.com").name("Test User").build();

        when(userRepository.findById(inputPayment.getBackerId())).thenReturn(userEntity);
        when(projectRepository.findById(inputPayment.getProjectId())).thenReturn(Optional.of(p1));
        when(paymentRepository.save(any(PaymentEntity.class))).thenAnswer(invocation -> {
            PaymentEntity payment2 = invocation.getArgument(0);
            payment2.setId(1); // Mock ID generation
            payment2.setUser(userEntity);
            return payment2;
        });

        // Act
        OutputDTOPayment result = paymentService.createPayment(inputPayment);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getPaymentId());
        verify(userRepository, times(1)).findById(inputPayment.getBackerId());
        verify(paymentRepository, times(1)).save(any(PaymentEntity.class));
    }

    @Test
    @DisplayName("Should return empty list if no payments found")
    void get_payments_no_payments() {
        // Arrange
        when(paymentRepository.findAll()).thenReturn(List.of());

        // Act
        List<OutputDTOPayment> payments = paymentService.getAllPayments();

        // Assert
        assertTrue(payments.isEmpty());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should delete payment by ID")
    void delete_payment_by_id() {
        // Arrange
        when(paymentRepository.findById(1L)).thenReturn(Optional.ofNullable(payment));
        doNothing().when(projectRepository).deleteById(1);

        // Act
        paymentService.deletePaymentById(1);

        // Assert
        verify(paymentRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should get payments by project Id")
    void get_payments_by_project_id() {
        // Arrange
        when(paymentRepository.getPaymentsByProjectId(1)).thenReturn(List.of(payment));

        // Act
        List<OutputDTOPayment> payments = paymentService.getPaymentsByProjectId(1);

        // Assert
        assertEquals(1, payments.size());
        assertEquals(payment.getId(), payments.get(0).getPaymentId());
        verify(paymentRepository, times(1)).getPaymentsByProjectId(1);
    }


    @Test
    @DisplayName("Should get payment notifications by project Id")
    void get_payment_notifications_by_project_id() {
        // Arrange
        when(paymentRepository.getPaymentsByProjectId(1)).thenReturn(List.of(payment));

        // Act
        List<OutputDonationNotification> payments = paymentService.getPaymentNotificationsByProjectId(1);

        // Assert
        assertEquals(1, payments.size());
        verify(paymentRepository, times(1)).getPaymentsByProjectId(1);
    }

    @Test
    void get_payments_for_profile_this_month() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 5); // Example pageable setup
        when(paymentRepository.getPaymentsForThisMonthByUserId(1, pageable)).thenReturn(new PageImpl<>(List.of(payment)));

        // Act
        Page<ProfilePaymentDTO> payments = paymentService.getPaymentsByUserIdForProfile(1, "This_month", 0, 5);

        // Assert
        assertEquals(1, payments.getContent().size());
        verify(paymentRepository, times(1)).getPaymentsForThisMonthByUserId(1, pageable);
    }


    @Test
    void get_payments_for_profile_this_year() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 5); // Example pageable setup
        when(paymentRepository.getPaymentsForThisYearByUserId(1, pageable)).thenReturn(new PageImpl<>(List.of(payment)));

        // Act
        Page<ProfilePaymentDTO> payments = paymentService.getPaymentsByUserIdForProfile(1, "This_year", 0, 5);

        // Assert
        assertEquals(1, payments.getContent().size());
        verify(paymentRepository, times(1)).getPaymentsForThisYearByUserId(1, pageable);
    }

    @Test
    @DisplayName("Should throw exception when getting payments by user for an unsupported filter")
    void get_payments_for_profile_exception() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 5); // Example pageable setup
        when(paymentRepository.getPaymentsForAllTimeByUser(1, pageable)).thenReturn(new PageImpl<>(List.of(payment)));

        // Act & Assert
        assertThrows(
                IllegalStateException.class,
                () -> paymentService.getPaymentsByUserIdForProfile(1, "Other_text", 0, 5),
                "Expected IllegalArgumentException for unsupported filter"
        );
    }


    @Test
    @DisplayName("Should get payments by user to be used in profile")
    void get_payments_for_profile_all_time() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 5); // Example pageable setup
        Page<PaymentEntity> paymentPage = new PageImpl<>(List.of(payment));
        when(paymentRepository.getPaymentsForAllTimeByUser(1, pageable)).thenReturn(paymentPage);

        // Act
        Page<ProfilePaymentDTO> payments = paymentService.getPaymentsByUserIdForProfile(1, "All_time", 0, 5);

        // Assert
        assertEquals(1, payments.getContent().size());
        verify(paymentRepository, times(1)).getPaymentsForAllTimeByUser(1, pageable);
    }



    @Test
    @DisplayName("Should get payment notifications by project Id")
    void get_payments_by_id() {
        // Arrange
        when(paymentRepository.findById(1L)).thenReturn(Optional.ofNullable(payment));

        // Act
        OutputDTOPayment payments = paymentService.getPaymentById(1L);

        // Assert
        assertEquals(1, payments.getPaymentId());
    }

    @Test
    void testGetTotalPaymentsByUserId_AllTime() {
        // Arrange
        int userId = 1;
        String time = "All_time";
        Integer expectedTotal = 100;  // Example expected value

        // Mock the repository method for "All_time"
        when(paymentRepository.getTotalPaymentsByUserId(userId, null)).thenReturn(expectedTotal);

        // Act
        Integer result = paymentService.getTotalPaymentsByUserId(userId, time);

        // Assert
        assertEquals(expectedTotal, result, "Total payments for All_time should be returned");
        verify(paymentRepository).getTotalPaymentsByUserId(userId, null);  // Verify the call
    }

    @Test
    void testGetTotalPaymentsByUserId_ThisMonth() {
        // Arrange
        int userId = 1;
        String time = "This_month";
        Integer expectedTotal = 50;  // Example expected value

        // Mock the repository method for "This_month"
        when(paymentRepository.getTotalPaymentsByUserId(userId, "month")).thenReturn(expectedTotal);

        // Act
        Integer result = paymentService.getTotalPaymentsByUserId(userId, time);

        // Assert
        assertEquals(expectedTotal, result, "Total payments for This_month should be returned");
        verify(paymentRepository).getTotalPaymentsByUserId(userId, "month");  // Verify the call
    }

    @Test
    void testGetTotalPaymentsByUserId_ThisYear() {
        // Arrange
        int userId = 1;
        String time = "This_year";
        Integer expectedTotal = 200;  // Example expected value

        // Mock the repository method for "This_year"
        when(paymentRepository.getTotalPaymentsByUserId(userId, "year")).thenReturn(expectedTotal);

        // Act
        Integer result = paymentService.getTotalPaymentsByUserId(userId, time);

        // Assert
        assertEquals(expectedTotal, result, "Total payments for This_year should be returned");
        verify(paymentRepository).getTotalPaymentsByUserId(userId, "year");  // Verify the call
    }

    @Test
    void testGetTotalPaymentsByUserId_Default() {
        // Arrange
        int userId = 1;
        String time = "Invalid_time";  // An invalid time value
        Integer expectedTotal = 0;  // Example expected value for default case

        // Mock the repository method for an invalid time
        when(paymentRepository.getTotalPaymentsByUserId(userId, null)).thenReturn(expectedTotal);

        // Act
        Integer result = paymentService.getTotalPaymentsByUserId(userId, time);

        // Assert
        assertEquals(expectedTotal, result, "Total payments for an invalid time should be returned");
        verify(paymentRepository).getTotalPaymentsByUserId(userId, null);  // Verify the call
    }

    @Test
    void testCreatePayment_ProjectNotFound() {
        // Arrange
        InputDTOPayment paymentDTO = mock(InputDTOPayment.class);
        when(paymentDTO.getProjectId()).thenReturn(1);  // Provide projectId

        // Mocking the behavior when the project is not found
        when(projectRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.createPayment(paymentDTO);  // This should throw an exception
        });

        assertEquals("Project not found", exception.getMessage(), "The exception message should match.");
        verify(projectRepository, times(2)).findById(1);  // Allow the method to be called twice
    }

}
