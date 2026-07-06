package com.jesusromero.enterprise.employee.users.domain.exception;

import com.jesusromero.enterprise.employee.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class EmptyRolesException extends BusinessException {

    public EmptyRolesException() {
        super(HttpStatus.BAD_REQUEST, "User must have at least one role");
    }
}
