package com.jesusromero.enterprise.employee.auth.domain.model;

public record AccessToken(
        String accessToken,
        String tokenType,
        long expiresIn
) {
}
