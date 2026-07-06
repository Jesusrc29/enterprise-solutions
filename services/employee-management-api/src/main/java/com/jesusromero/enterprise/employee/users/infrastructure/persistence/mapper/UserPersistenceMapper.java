package com.jesusromero.enterprise.employee.users.infrastructure.persistence.mapper;

import com.jesusromero.enterprise.employee.config.MapStructConfig;
import com.jesusromero.enterprise.employee.users.domain.model.User;
import com.jesusromero.enterprise.employee.users.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class, uses = UserRolePersistenceMapper.class)
public interface UserPersistenceMapper {

    @Mapping(target = "enabled", expression = "java(toEnabled(userEntity.getEnabled()))")
    User toDomain(UserEntity userEntity);

    @Mapping(target = "enabled", expression = "java(toEnabledFlag(user.enabled()))")
    UserEntity toEntity(User user);

    default boolean toEnabled(Integer enabled) {
        return enabled != null && enabled == 1;
    }

    default Integer toEnabledFlag(boolean enabled) {
        return enabled ? 1 : 0;
    }
}
