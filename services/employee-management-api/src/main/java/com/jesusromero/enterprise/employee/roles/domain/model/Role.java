package com.jesusromero.enterprise.employee.roles.domain.model;

import java.time.LocalDateTime;

public record Role(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt
) {
}
