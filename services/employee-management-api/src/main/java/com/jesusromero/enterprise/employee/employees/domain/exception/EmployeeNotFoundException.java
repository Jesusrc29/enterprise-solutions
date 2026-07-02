package com.jesusromero.enterprise.employee.employees.domain.exception;

import com.jesusromero.enterprise.employee.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class EmployeeNotFoundException extends BusinessException {

    public EmployeeNotFoundException(Long employeeId) {
        super(HttpStatus.NOT_FOUND, "Employee not found for id " + employeeId);
    }
}
