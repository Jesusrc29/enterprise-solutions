package com.jesusromero.enterprise.employee.roles.domain.exception;

import com.jesusromero.enterprise.employee.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends BusinessException {

    public RoleNotFoundException(String roleName) {
        super(HttpStatus.NOT_FOUND, "Role not found for name " + roleName);
    }

    public RoleNotFoundException(Long roleId) {
        super(HttpStatus.NOT_FOUND, "Role not found for id " + roleId);
    }
}
