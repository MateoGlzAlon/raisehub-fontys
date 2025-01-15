package com.fontys.crowdfund.business;

import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOPayment;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOPayment;
import com.fontys.crowdfund.persistence.specialdto.OutputDonationNotification;
import com.fontys.crowdfund.persistence.specialdto.ProfilePaymentDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PaymentService {

    // Get all payments and convert them to DTOs
    List<OutputDTOPayment> getAllPayments();

    // Get payment by ID
    OutputDTOPayment getPaymentById(long id);

    // Create a new payment
    OutputDTOPayment createPayment(InputDTOPayment paymentDTO);

    void deletePaymentById(int id);

    List<OutputDTOPayment> getPaymentsByProjectId(int id);

    List<OutputDonationNotification> getPaymentNotificationsByProjectId(int id);

    Page<ProfilePaymentDTO> getPaymentsByUserIdForProfile(int id, String filter, int page, int size);

    Integer getTotalPaymentsByUserId(int userId, String time);
}
