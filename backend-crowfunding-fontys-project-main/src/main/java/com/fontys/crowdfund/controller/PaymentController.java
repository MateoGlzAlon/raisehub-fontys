package com.fontys.crowdfund.controller;

import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOPayment;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOPayment;
import com.fontys.crowdfund.persistence.specialdto.OutputDonationNotification;
import com.fontys.crowdfund.persistence.specialdto.ProfilePaymentDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface PaymentController {

    @GetMapping
    List<OutputDTOPayment> getAllPayments();

    @GetMapping("/{id}")
    ResponseEntity<OutputDTOPayment> getPaymentById(@PathVariable int id);

    @GetMapping("/projects/{id}")
    ResponseEntity<List<OutputDTOPayment>> getPaymentsToProjectById(@PathVariable int id);

    @GetMapping("/projects/{id}")
    ResponseEntity<List<OutputDonationNotification>> getPaymentNotificationsToProjectById(@PathVariable int id);

    @GetMapping("/projects/profile/{id}")
    ResponseEntity<Page<ProfilePaymentDTO>> getPaymentsByUserIdForProfile(@PathVariable int id, @RequestParam String filter, @RequestParam int page, @RequestParam int size);

    @GetMapping("/totalPayments/{userId}")
    ResponseEntity<Integer> getTotalPaymentsByUserId(@PathVariable int userId, @RequestParam(defaultValue = "") String time);

    @PostMapping
    ResponseEntity<OutputDTOPayment> createPayment(@RequestBody InputDTOPayment paymentDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletePayment(@PathVariable int id);



}
