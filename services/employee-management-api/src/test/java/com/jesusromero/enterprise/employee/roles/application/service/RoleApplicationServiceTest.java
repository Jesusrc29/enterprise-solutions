package com.jesusromero.enterprise.employee.roles.application.service;

import com.jesusromero.enterprise.employee.roles.domain.model.Role;
import com.jesusromero.enterprise.employee.roles.domain.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleApplicationServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleApplicationService roleApplicationService;

    @Test
    void shouldListRoles() {
        when(roleRepository.findAll()).thenReturn(List.of(
                new Role(1L, "ADMIN", "System administrator", LocalDateTime.now()),
                new Role(2L, "HR_MANAGER", "Human resources manager", LocalDateTime.now()),
                new Role(3L, "EMPLOYEE", "Employee role", LocalDateTime.now())
        ));

        List<Role> result = roleApplicationService.listRoles();

        assertThat(result).hasSize(3);
        assertThat(result).extracting(Role::name)
                .containsExactly("ADMIN", "HR_MANAGER", "EMPLOYEE");
    }
}
