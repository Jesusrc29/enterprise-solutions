package com.jesusromero.enterprise.employee.roles.api.mapper;

import com.jesusromero.enterprise.employee.config.MapStructConfig;
import com.jesusromero.enterprise.employee.roles.api.dto.RoleResponse;
import com.jesusromero.enterprise.employee.roles.domain.model.Role;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface RoleApiMapper {

    RoleResponse toResponse(Role role);
}
