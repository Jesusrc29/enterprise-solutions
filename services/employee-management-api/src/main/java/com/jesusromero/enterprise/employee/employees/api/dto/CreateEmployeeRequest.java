package com.jesusromero.enterprise.employee.employees.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateEmployeeRequest(
        @NotBlank(message = "must not be blank")
        String documentType,
        @NotBlank(message = "must not be blank")
        String documentNumber,
        @NotBlank(message = "must not be blank")
        String firstName,
        @NotBlank(message = "must not be blank")
        String lastName,
        @Email(message = "must be a well-formed email address")
        @NotBlank(message = "must not be blank")
        String email,
        String phone,
        String jobTitle,
        String department,
        @NotNull(message = "must not be null")
        LocalDate hireDate
) {
}
