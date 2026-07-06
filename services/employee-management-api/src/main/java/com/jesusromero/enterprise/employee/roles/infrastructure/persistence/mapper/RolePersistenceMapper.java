package com.jesusromero.enterprise.employee.roles.infrastructure.persistence.mapper;

import com.jesusromero.enterprise.employee.config.MapStructConfig;
import com.jesusromero.enterprise.employee.roles.domain.model.Role;
import com.jesusromero.enterprise.employee.roles.infrastructure.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface RolePersistenceMapper {

    Role toDomain(RoleEntity roleEntity);

    RoleEntity toEntity(Role role);
}
