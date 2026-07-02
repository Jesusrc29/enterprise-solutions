package com.jesusromero.enterprise.employee.roles.infrastructure.persistence.repository;

import com.jesusromero.enterprise.employee.roles.infrastructure.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);
}
