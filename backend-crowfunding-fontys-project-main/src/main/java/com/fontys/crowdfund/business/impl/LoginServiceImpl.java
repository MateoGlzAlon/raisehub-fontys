package com.fontys.crowdfund.business.impl;

import com.fontys.crowdfund.business.LoginService;
import com.fontys.crowdfund.exception.InvalidCredentialsException;
import com.fontys.crowdfund.config.security.token.AccessTokenEncoder;
import com.fontys.crowdfund.config.security.token.impl.AccessTokenImpl;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOLogin;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOLogin;
import com.fontys.crowdfund.repository.UserRepository;
import com.fontys.crowdfund.persistence.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    public OutputDTOLogin login(InputDTOLogin loginRequest) {
        UserEntity user = userRepository.findByEmail(loginRequest.getUsername());

        if (user == null) {
            throw new InvalidCredentialsException();
        }

        if (!matchesPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = generateAccessToken(user);

        return OutputDTOLogin.builder().accessToken(accessToken).build();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(UserEntity user) {
        Long studentId = Long.valueOf(user.getId());
        List<String> role = new ArrayList<>();
        role.add(user.getRole());

        return accessTokenEncoder.encode(new AccessTokenImpl(studentId, role));
    }

}
