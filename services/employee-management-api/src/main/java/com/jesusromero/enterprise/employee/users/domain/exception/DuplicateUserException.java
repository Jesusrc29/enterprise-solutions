package com.jesusromero.enterprise.employee.users.domain.exception;

import com.jesusromero.enterprise.employee.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class DuplicateUserException extends BusinessException {

    public DuplicateUserException(String field, String value) {
        super(HttpStatus.CONFLICT, "User already exists with " + field + " " + value);
    }
}
