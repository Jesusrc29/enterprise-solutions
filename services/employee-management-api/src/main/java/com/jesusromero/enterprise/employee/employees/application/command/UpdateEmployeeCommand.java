package com.jesusromero.enterprise.employee.employees.application.command;

import java.time.LocalDate;

public record UpdateEmployeeCommand(
        String documentType,
        String documentNumber,
        String firstName,
        String lastName,
        String email,
        String phone,
        String jobTitle,
        String department,
        LocalDate hireDate
) {
}
