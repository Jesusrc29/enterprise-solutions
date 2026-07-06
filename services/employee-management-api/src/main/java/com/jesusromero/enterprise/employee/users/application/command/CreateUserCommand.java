package com.jesusromero.enterprise.employee.users.application.command;

import java.util.Set;

public record CreateUserCommand(
        String username,
        String email,
        String password,
        Set<String> roles
) {
}
