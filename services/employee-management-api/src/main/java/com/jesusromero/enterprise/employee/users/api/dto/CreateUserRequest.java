package com.jesusromero.enterprise.employee.users.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record CreateUserRequest(
        @NotBlank(message = "must not be blank")
        String username,
        @Email(message = "must be a well-formed email address")
        @NotBlank(message = "must not be blank")
        String email,
        @NotBlank(message = "must not be blank")
        String password,
        @NotEmpty(message = "must not be empty")
        Set<String> roles
) {
}
