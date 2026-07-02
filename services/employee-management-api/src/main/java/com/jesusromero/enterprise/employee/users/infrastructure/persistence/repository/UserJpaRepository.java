package com.jesusromero.enterprise.employee.users.infrastructure.persistence.repository;

import com.jesusromero.enterprise.employee.users.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}
