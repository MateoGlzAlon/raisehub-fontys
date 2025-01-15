package com.fontys.crowdfund.controller.impl;

import com.fontys.crowdfund.controller.PaymentController;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOPayment;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOPayment;
import com.fontys.crowdfund.business.PaymentService;
import com.fontys.crowdfund.persistence.specialdto.OutputDonationNotification;
import com.fontys.crowdfund.persistence.specialdto.ProfilePaymentDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentControllerImpl implements PaymentController {

    private PaymentService paymentService;

    @Override
    @GetMapping
    public List<OutputDTOPayment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<OutputDTOPayment> getPaymentById(@PathVariable int id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @Override
    @GetMapping("/projects/{id}")
    public ResponseEntity<List<OutputDTOPayment>> getPaymentsToProjectById(@PathVariable int id) {
        return ResponseEntity.ok(paymentService.getPaymentsByProjectId(id));
    }

    @Override
    @GetMapping("/projects/notifications/{id}")
    public ResponseEntity<List<OutputDonationNotification>> getPaymentNotificationsToProjectById(@PathVariable int id) {
        return ResponseEntity.ok(paymentService.getPaymentNotificationsByProjectId(id));
    }

    @Override
    @GetMapping("/profile/{id}")
    public ResponseEntity<Page<ProfilePaymentDTO>> getPaymentsByUserIdForProfile(
            @PathVariable int id,
            @RequestParam String filter,
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(paymentService.getPaymentsByUserIdForProfile(id, filter, page, size));
    }



    @Override
    @GetMapping("/totalPayments/{userId}")
    public ResponseEntity<Integer> getTotalPaymentsByUserId(@PathVariable int userId, String time) {
        return ResponseEntity.ok(paymentService.getTotalPaymentsByUserId(userId, time));
    }

    @Override
    @PostMapping
    public ResponseEntity<OutputDTOPayment> createPayment(@RequestBody InputDTOPayment paymentDTO) {
        return ResponseEntity.ok(paymentService.createPayment(paymentDTO));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable int id) {
        paymentService.deletePaymentById(id);
        return ResponseEntity.ok().build();
    }
}
