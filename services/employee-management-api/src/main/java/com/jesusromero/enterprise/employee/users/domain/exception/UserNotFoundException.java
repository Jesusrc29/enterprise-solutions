package com.jesusromero.enterprise.employee.users.domain.exception;

import com.jesusromero.enterprise.employee.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(Long userId) {
        super(HttpStatus.NOT_FOUND, "User not found for id " + userId);
    }
}
