package com.jesusromero.enterprise.employee.users.api.mapper;

import com.jesusromero.enterprise.employee.config.MapStructConfig;
import com.jesusromero.enterprise.employee.roles.domain.model.Role;
import com.jesusromero.enterprise.employee.users.api.dto.CreateUserRequest;
import com.jesusromero.enterprise.employee.users.api.dto.UpdateUserRolesRequest;
import com.jesusromero.enterprise.employee.users.api.dto.UserResponse;
import com.jesusromero.enterprise.employee.users.application.command.CreateUserCommand;
import com.jesusromero.enterprise.employee.users.application.command.UpdateUserRolesCommand;
import com.jesusromero.enterprise.employee.users.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.LinkedHashSet;
import java.util.Set;

@Mapper(config = MapStructConfig.class)
public interface UserApiMapper {

    CreateUserCommand toCreateCommand(CreateUserRequest request);

    UpdateUserRolesCommand toUpdateRolesCommand(UpdateUserRolesRequest request);

    @Mapping(target = "roles", expression = "java(toRoleNames(user.roles()))")
    UserResponse toResponse(User user);

    default Set<String> toRoleNames(Set<Role> roles) {
        return roles.stream()
                .map(Role::name)
                .collect(LinkedHashSet::new, LinkedHashSet::add, LinkedHashSet::addAll);
    }
}
