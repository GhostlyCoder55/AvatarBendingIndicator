package com.avatarbendingindicator.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app")
public record AppProperties(
        Jwt jwt,
        Admin admin
) {
    public record Jwt(
            @NotBlank String secret,
            @Positive long expirationMs
    ) {
    }

    public record Admin(
            @NotBlank String username,
            @NotBlank String email,
            @NotBlank String password
    ) {
    }
}
