package com.jesusromero.enterprise.employee.roles.application.service;

import com.jesusromero.enterprise.employee.roles.domain.exception.RoleNotFoundException;
import com.jesusromero.enterprise.employee.roles.domain.model.Role;
import com.jesusromero.enterprise.employee.roles.domain.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleApplicationService {

    private final RoleRepository roleRepository;

    public RoleApplicationService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
    }
}
