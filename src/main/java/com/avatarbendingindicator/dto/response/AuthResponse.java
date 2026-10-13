package com.avatarbendingindicator.dto.response;

import com.avatarbendingindicator.model.Role;

public record AuthResponse(
        String token,
        String tokenType,
        String username,
        Role role
) {
    public static AuthResponse bearer(String token, String username, Role role) {
        return new AuthResponse(token, "Bearer", username, role);
    }
}
