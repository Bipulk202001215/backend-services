package com.example.login.services;

import com.example.login.entity.AuthToken;
import com.example.login.entity.UserAccount;
import com.example.login.model.LoginRequest;
import com.example.login.model.LoginResponse;
import com.example.login.model.ResetPasswordRequest;
import com.example.login.model.SignupRequest;
import com.example.login.repository.AuthTokenRepository;
import com.example.login.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTests {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private AuthTokenRepository authTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private SignupRequest signupRequest;
    private UserAccount existingAccount;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest("user@example.com", "password123");
        existingAccount = new UserAccount();
        existingAccount.setId(UUID.randomUUID());
        existingAccount.setEmail("user@example.com");
        existingAccount.setPasswordHash("hashed");
    }

    @Test
    void signupCreatesNewAccount() {
        when(userAccountRepository.findByEmailIgnoreCase(signupRequest.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(signupRequest.password())).thenReturn("encoded");

        authService.signup(signupRequest);

        ArgumentCaptor<UserAccount> captor = ArgumentCaptor.forClass(UserAccount.class);
        verify(userAccountRepository).save(captor.capture());
        UserAccount saved = captor.getValue();
        assertThat(saved.getEmail()).isEqualTo(signupRequest.email());
        assertThat(saved.getPasswordHash()).isEqualTo("encoded");
    }

    @Test
    void signupWithExistingEmailThrows() {
        when(userAccountRepository.findByEmailIgnoreCase(signupRequest.email())).thenReturn(Optional.of(existingAccount));

        assertThatThrownBy(() -> authService.signup(signupRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Email already registered");
    }

    @Test
    void loginReturnsExistingTokenWhenPresent() {
        LoginRequest loginRequest = new LoginRequest("user@example.com", "password123");
        AuthToken existingToken = new AuthToken(existingAccount.getId());

        when(userAccountRepository.findByEmailIgnoreCase(loginRequest.email())).thenReturn(Optional.of(existingAccount));
        when(passwordEncoder.matches(loginRequest.password(), existingAccount.getPasswordHash())).thenReturn(true);
        when(authTokenRepository.findFirstByUserIdOrderByCreatedAtDesc(existingAccount.getId()))
                .thenReturn(Optional.of(existingToken));

        LoginResponse response = authService.login(loginRequest);

        assertThat(response.token()).isEqualTo(existingToken.getToken());
        verify(authTokenRepository, never()).save(any(AuthToken.class));
    }

    @Test
    void loginThrowsForInvalidCredentials() {
        LoginRequest loginRequest = new LoginRequest("user@example.com", "password123");
        when(userAccountRepository.findByEmailIgnoreCase(loginRequest.email())).thenReturn(Optional.of(existingAccount));
        when(passwordEncoder.matches(loginRequest.password(), existingAccount.getPasswordHash())).thenReturn(false);

        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid credentials");
    }

    @Test
    void resetPasswordUpdatesHashAndInvalidatesTokens() {
        ResetPasswordRequest request = new ResetPasswordRequest("user@example.com", "newPassword123");
        when(userAccountRepository.findByEmailIgnoreCase(request.email())).thenReturn(Optional.of(existingAccount));
        when(passwordEncoder.encode(request.newPassword())).thenReturn("newEncoded");

        authService.resetPassword(request);

        verify(userAccountRepository).save(existingAccount);
        verify(authTokenRepository).deleteByUserId(existingAccount.getId());
        assertThat(existingAccount.getPasswordHash()).isEqualTo("newEncoded");
    }
}


