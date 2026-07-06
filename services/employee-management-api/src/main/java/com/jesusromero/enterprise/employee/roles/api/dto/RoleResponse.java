package com.jesusromero.enterprise.employee.roles.api.dto;

import java.time.LocalDateTime;

public record RoleResponse(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt
) {
}
