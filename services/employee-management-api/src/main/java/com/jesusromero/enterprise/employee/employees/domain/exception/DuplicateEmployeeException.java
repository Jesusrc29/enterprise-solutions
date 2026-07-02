package com.jesusromero.enterprise.employee.employees.domain.exception;

import com.jesusromero.enterprise.employee.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class DuplicateEmployeeException extends BusinessException {

    public DuplicateEmployeeException(String field, String value) {
        super(HttpStatus.CONFLICT, "Employee already exists with " + field + " " + value);
    }
}
