package com.fontys.crowdfund.testBusiness;

import com.fontys.crowdfund.business.impl.LoginServiceImpl;
import com.fontys.crowdfund.config.security.token.AccessTokenEncoder;
import com.fontys.crowdfund.config.security.token.impl.AccessTokenImpl;
import com.fontys.crowdfund.exception.InvalidCredentialsException;
import com.fontys.crowdfund.repository.UserRepository;
import com.fontys.crowdfund.persistence.dto.inputdto.InputDTOLogin;
import com.fontys.crowdfund.persistence.dto.outputdto.OutputDTOLogin;
import com.fontys.crowdfund.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    @InjectMocks
    private LoginServiceImpl loginService;

    private static UserEntity user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = UserEntity.builder()
                .id(1)
                .email("user@example.com")
                .password("$2a$10$hashedpassword")
                .role("user")
                .build();
    }

    @Test
    @DisplayName("Should login successfully and return access token")
    void login_successfully() {
        // Arrange
        InputDTOLogin loginRequest = InputDTOLogin.builder()
                .username("user@example.com")
                .password("password")
                .build();

        when(userRepository.findByEmail(loginRequest.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(accessTokenEncoder.encode(any(AccessTokenImpl.class))).thenReturn("mockedAccessToken");

        // Act
        OutputDTOLogin result = loginService.login(loginRequest);

        // Assert
        assertNotNull(result);
        assertEquals("mockedAccessToken", result.getAccessToken());
        verify(userRepository, times(1)).findByEmail(loginRequest.getUsername());
        verify(passwordEncoder, times(1)).matches(loginRequest.getPassword(), user.getPassword());
        verify(accessTokenEncoder, times(1)).encode(any(AccessTokenImpl.class));
    }

    @Test
    @DisplayName("Should throw InvalidCredentialsException if user is not found")
    void login_user_not_found() {
        // Arrange
        InputDTOLogin loginRequest = InputDTOLogin.builder()
                .username("unknown@example.com")
                .password("password")
                .build();

        when(userRepository.findByEmail(loginRequest.getUsername())).thenReturn(null);

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> loginService.login(loginRequest));
        verify(userRepository, times(1)).findByEmail(loginRequest.getUsername());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(accessTokenEncoder, never()).encode(any(AccessTokenImpl.class));
    }

    @Test
    @DisplayName("Should throw InvalidCredentialsException if password is incorrect")
    void login_incorrect_password() {
        // Arrange
        InputDTOLogin loginRequest = InputDTOLogin.builder()
                .username("user@example.com")
                .password("wrongPassword")
                .build();

        when(userRepository.findByEmail(loginRequest.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> loginService.login(loginRequest));
        verify(userRepository, times(1)).findByEmail(loginRequest.getUsername());
        verify(passwordEncoder, times(1)).matches(loginRequest.getPassword(), user.getPassword());
        verify(accessTokenEncoder, never()).encode(any(AccessTokenImpl.class));
    }

    @Test
    @DisplayName("Should generate access token correctly")
    void generate_access_token() {
        // Arrange
        InputDTOLogin loginRequest = InputDTOLogin.builder()
                .username("user@example.com")
                .password("password")
                .build();

        when(userRepository.findByEmail(loginRequest.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(accessTokenEncoder.encode(any(AccessTokenImpl.class))).thenReturn("mockedAccessToken");

        // Act
        String token = loginService.login(loginRequest).getAccessToken();

        // Assert
        assertNotNull(token);
        assertEquals("mockedAccessToken", token);
        verify(userRepository, times(1)).findByEmail(loginRequest.getUsername());
        verify(passwordEncoder, times(1)).matches(loginRequest.getPassword(), user.getPassword());
        verify(accessTokenEncoder, times(1)).encode(any(AccessTokenImpl.class));
    }

}
