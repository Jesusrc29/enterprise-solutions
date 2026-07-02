package com.jesusromero.enterprise.employee.auth.api.dto;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn
) {
}
