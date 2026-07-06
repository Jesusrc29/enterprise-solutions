package com.jesusromero.enterprise.employee.roles.infrastructure.persistence;

import com.jesusromero.enterprise.employee.roles.domain.model.Role;
import com.jesusromero.enterprise.employee.roles.domain.repository.RoleRepository;
import com.jesusromero.enterprise.employee.roles.infrastructure.persistence.mapper.RolePersistenceMapper;
import com.jesusromero.enterprise.employee.roles.infrastructure.persistence.repository.RoleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RolePersistenceRepository implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;
    private final RolePersistenceMapper rolePersistenceMapper;

    public RolePersistenceRepository(
            RoleJpaRepository roleJpaRepository,
            RolePersistenceMapper rolePersistenceMapper
    ) {
        this.roleJpaRepository = roleJpaRepository;
        this.rolePersistenceMapper = rolePersistenceMapper;
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleJpaRepository.findById(id)
                .map(rolePersistenceMapper::toDomain);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleJpaRepository.findByName(name)
                .map(rolePersistenceMapper::toDomain);
    }

    @Override
    public List<Role> findAll() {
        return roleJpaRepository.findAll().stream()
                .map(rolePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Set<Role> findByNames(Set<String> names) {
        return roleJpaRepository.findByNameIn(names).stream()
                .map(rolePersistenceMapper::toDomain)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
