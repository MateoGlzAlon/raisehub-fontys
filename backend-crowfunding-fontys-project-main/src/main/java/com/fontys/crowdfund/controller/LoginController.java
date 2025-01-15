package com.fontys.crowdfund.controller;

import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOLogin;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOLogin;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoginController {

    @PostMapping("/tokens")
    ResponseEntity<OutputDTOLogin> login(@RequestBody @Valid InputDTOLogin loginRequest);
}
