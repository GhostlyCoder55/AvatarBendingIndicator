package com.avatarbendingindicator.service;

import com.avatarbendingindicator.dto.request.LoginRequest;
import com.avatarbendingindicator.dto.request.RegisterRequest;
import com.avatarbendingindicator.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
