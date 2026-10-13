package com.avatarbendingindicator.service.impl;

import com.avatarbendingindicator.dto.request.LoginRequest;
import com.avatarbendingindicator.dto.request.RegisterRequest;
import com.avatarbendingindicator.dto.response.AuthResponse;
import com.avatarbendingindicator.exception.DuplicateResourceException;
import com.avatarbendingindicator.exception.InvalidCredentialsException;
import com.avatarbendingindicator.model.Role;
import com.avatarbendingindicator.model.UserAccount;
import com.avatarbendingindicator.repository.UserAccountRepository;
import com.avatarbendingindicator.security.JwtService;
import com.avatarbendingindicator.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(
            UserAccountRepository userAccountRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userAccountRepository.existsByUsernameIgnoreCase(request.username())) {
            throw new DuplicateResourceException("Username is already taken.");
        }
        if (userAccountRepository.existsByEmailIgnoreCase(request.email())) {
            throw new DuplicateResourceException("Email is already registered.");
        }
        UserAccount user = new UserAccount();
        user.setUsername(request.username());
        user.setEmail(request.email().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        user.setEnabled(true);
        UserAccount saved = userAccountRepository.save(user);
        log.info("Registered user {}", saved.getUsername());
        return AuthResponse.bearer(jwtService.generateToken(saved), saved.getUsername(), saved.getRole());
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        UserAccount user = userAccountRepository.findByUsernameIgnoreCase(request.username())
                .filter(UserAccount::isEnabled)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password."));
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid username or password.");
        }
        return AuthResponse.bearer(jwtService.generateToken(user), user.getUsername(), user.getRole());
    }
}
