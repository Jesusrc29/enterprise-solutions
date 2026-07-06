package com.jesusromero.enterprise.employee.roles.domain.repository;

import com.jesusromero.enterprise.employee.roles.domain.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository {

    Optional<Role> findById(Long id);

    Optional<Role> findByName(String name);

    List<Role> findAll();

    Set<Role> findByNames(Set<String> names);
}
