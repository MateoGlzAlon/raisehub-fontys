package com.fontys.crowdfund.controller.impl;

import com.fontys.crowdfund.business.LoginService;
import com.fontys.crowdfund.controller.LoginController;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOLogin;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOLogin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
public class LoginControllerImpl implements LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<OutputDTOLogin> login(@RequestBody @Valid InputDTOLogin loginRequest) {
        OutputDTOLogin loginResponse = loginService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
    }
}
