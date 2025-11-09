package com.example.login.services;

import com.example.login.entity.UserAccount;
import com.example.login.model.LoginRequest;
import com.example.login.model.LoginResponse;
import com.example.login.model.ResetPasswordRequest;
import com.example.login.model.SignupRequest;
import com.example.login.repository.UserAccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserAccountRepository userAccountRepository,
                       PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(SignupRequest request) {
        userAccountRepository.findByEmailIgnoreCase(request.email()).ifPresent(account -> {
            throw new IllegalArgumentException("Email already registered");
        });

        UserAccount account = new UserAccount();
        account.setEmail(request.email().toLowerCase());
        account.setPasswordHash(passwordEncoder.encode(request.password()));
        userAccountRepository.save(account);
    }

    public LoginResponse login(LoginRequest request) {
        UserAccount account = userAccountRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), account.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return new LoginResponse(account.getId(), account.getEmail());
    }

    public void resetPassword(ResetPasswordRequest request) {
        UserAccount account = userAccountRepository.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        account.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        userAccountRepository.save(account);
    }
}


