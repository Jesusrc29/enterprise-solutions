package com.jesusromero.enterprise.employee.users.api.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record UpdateUserRolesRequest(
        @NotEmpty(message = "must not be empty")
        Set<String> roles
) {
}
