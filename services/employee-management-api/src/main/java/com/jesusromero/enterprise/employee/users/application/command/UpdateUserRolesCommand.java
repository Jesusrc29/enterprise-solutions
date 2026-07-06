package com.jesusromero.enterprise.employee.users.application.command;

import java.util.Set;

public record UpdateUserRolesCommand(
        Set<String> roles
) {
}
