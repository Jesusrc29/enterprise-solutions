package com.jesusromero.enterprise.employee.employees.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Employee(
        Long id,
        String documentType,
        String documentNumber,
        String firstName,
        String lastName,
        String email,
        String phone,
        String jobTitle,
        String department,
        EmployeeStatus status,
        LocalDate hireDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
