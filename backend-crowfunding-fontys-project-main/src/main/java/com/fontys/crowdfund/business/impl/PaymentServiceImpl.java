package com.fontys.crowdfund.business.impl;

import com.fontys.crowdfund.repository.PaymentRepository;

import com.fontys.crowdfund.business.PaymentService;
import com.fontys.crowdfund.repository.ProjectRepository;
import com.fontys.crowdfund.repository.UserRepository;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOPayment;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOPayment;
import com.fontys.crowdfund.persistence.entity.PaymentEntity;
import com.fontys.crowdfund.persistence.entity.ProjectEntity;
import com.fontys.crowdfund.persistence.specialdto.OutputDonationNotification;
import com.fontys.crowdfund.persistence.specialdto.ProfilePaymentDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    // Get all payments and convert them to DTOs
    @Override
    public List<OutputDTOPayment> getAllPayments() {
        List<OutputDTOPayment> outputDTOPayments = new ArrayList<>();

        for (PaymentEntity paymentEntity : paymentRepository.findAll()) {
            outputDTOPayments.add(createOutputDTOPayment(paymentEntity));
        }

        return outputDTOPayments;
    }



    // Get payment by ID
    @Override
    public OutputDTOPayment getPaymentById(long id) {
        return paymentRepository.findById(id)
                .map(this::createOutputDTOPayment)
                .orElse(null);
    }

    // Create a new payment
    @Override
    public OutputDTOPayment createPayment(InputDTOPayment paymentDTO) {

        PaymentEntity payment = PaymentEntity.builder()
                .user(userRepository.findById(paymentDTO.getBackerId()))
                .project(projectRepository.findById(paymentDTO.getProjectId()).orElse(null))
                .amount(paymentDTO.getAmountFunded())
                .paymentDate(paymentDTO.getPaymentDate())
                .build();


        ProjectEntity project = projectRepository.findById(paymentDTO.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        project.setMoneyRaised(project.getMoneyRaised() + paymentDTO.getAmountFunded());
        projectRepository.save(project);

        return createOutputDTOPayment(paymentRepository.save(payment));
    }

    @Override
    public void deletePaymentById(int id) {

        paymentRepository.deleteById((long) id);

    }

    @Override
    public List<OutputDTOPayment> getPaymentsByProjectId(int id) {

        List<OutputDTOPayment> outputDTOPayments = new ArrayList<>();

        for (PaymentEntity paymentEntity : paymentRepository.getPaymentsByProjectId(id)) {
            outputDTOPayments.add(createOutputDTOPayment(paymentEntity));
        }

        return outputDTOPayments;
    }

    @Override
    public List<OutputDonationNotification> getPaymentNotificationsByProjectId(int id) {
        List<OutputDonationNotification> outputDTOPaymentNotification = new ArrayList<>();

        for (PaymentEntity paymentEntity : paymentRepository.getPaymentsByProjectId(id)) {

            OutputDonationNotification outputDonationNotification = OutputDonationNotification.builder()
                    .backerName(paymentEntity.getUser().getName())
                    .paymentDate(paymentEntity.getPaymentDate())
                    .amount(paymentEntity.getAmount())
                    .build();

            outputDTOPaymentNotification.add(outputDonationNotification);
        }

        return outputDTOPaymentNotification;
    }

    @Override
    public Page<ProfilePaymentDTO> getPaymentsByUserIdForProfile(int id, String filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PaymentEntity> paymentEntities = switch (filter) {
            case "This_month" -> paymentRepository.getPaymentsForThisMonthByUserId(id, pageable);
            case "This_year" -> paymentRepository.getPaymentsForThisYearByUserId(id, pageable);
            case "All_time" -> paymentRepository.getPaymentsForAllTimeByUser(id, pageable);
            default -> throw new IllegalStateException("Unexpected value: " + filter);
        };

        return paymentEntities.map(paymentEntity -> ProfilePaymentDTO.builder()
                .id(paymentEntity.getId())
                .projectId(paymentEntity.getProject().getId())
                .projectName(paymentEntity.getProject().getName())
                .projectOwnerName(paymentEntity.getUser().getName())
                .paymentDate(paymentEntity.getPaymentDate())
                .amountFunded(paymentEntity.getAmount())
                .projectCoverImage(paymentRepository.getImageCover(paymentEntity.getProject().getId()))
                .build());
    }



    @Override
    public Integer getTotalPaymentsByUserId(int userId, String time) {
        String timeVar = switch (time) {
            case "All_time" -> null;
            case "This_month" -> "month";
            case "This_year" -> "year";
            default -> null;
        };

        return paymentRepository.getTotalPaymentsByUserId(userId, timeVar);
    }



    private OutputDTOPayment createOutputDTOPayment(PaymentEntity paymentEntity) {

        return OutputDTOPayment.builder()
                .paymentId(paymentEntity.getId())
                .paymentDate(paymentEntity.getPaymentDate())
                .projectId(paymentEntity.getProject().getId())
                .backerEmail(paymentEntity.getUser().getEmail())
                .amountFunded(paymentEntity.getAmount())
                .build();
    }

}
