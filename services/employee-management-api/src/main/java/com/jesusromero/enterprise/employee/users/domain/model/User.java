package com.jesusromero.enterprise.employee.users.domain.model;

import com.jesusromero.enterprise.employee.roles.domain.model.Role;

import java.time.LocalDateTime;
import java.util.Set;

public record User(
        Long id,
        String username,
        String email,
        String passwordHash,
        boolean enabled,
        Set<Role> roles,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
