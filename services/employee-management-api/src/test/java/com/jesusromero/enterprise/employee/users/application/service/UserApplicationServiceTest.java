package com.jesusromero.enterprise.employee.users.application.service;

import com.jesusromero.enterprise.employee.roles.domain.model.Role;
import com.jesusromero.enterprise.employee.roles.domain.repository.RoleRepository;
import com.jesusromero.enterprise.employee.users.application.command.CreateUserCommand;
import com.jesusromero.enterprise.employee.users.application.command.UpdateUserRolesCommand;
import com.jesusromero.enterprise.employee.users.domain.exception.DuplicateUserException;
import com.jesusromero.enterprise.employee.users.domain.exception.EmptyRolesException;
import com.jesusromero.enterprise.employee.users.domain.model.User;
import com.jesusromero.enterprise.employee.users.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserApplicationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserApplicationService userApplicationService;

    @Test
    void shouldCreateUserSuccessfully() {
        Role employeeRole = new Role(3L, "EMPLOYEE", "Employee role", LocalDateTime.now());
        User savedUser = new User(
                2L,
                "jsmith",
                "jsmith@example.com",
                "encoded-password",
                true,
                Set.of(employeeRole),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(userRepository.findByUsername("jsmith")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("jsmith@example.com")).thenReturn(Optional.empty());
        when(roleRepository.findByNames(Set.of("EMPLOYEE"))).thenReturn(new LinkedHashSet<>(Set.of(employeeRole)));
        when(passwordEncoder.encode("ChangeMe123")).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userApplicationService.createUser(
                new CreateUserCommand("jsmith", "jsmith@example.com", "ChangeMe123", Set.of("EMPLOYEE"))
        );

        assertThat(result.id()).isEqualTo(2L);
        assertThat(result.username()).isEqualTo("jsmith");
        assertThat(result.email()).isEqualTo("jsmith@example.com");
        assertThat(result.enabled()).isTrue();
        assertThat(result.roles()).extracting(Role::name).containsExactly("EMPLOYEE");
    }

    @Test
    void shouldRejectDuplicateUsername() {
        User existingUser = new User(
                5L,
                "jsmith",
                "existing@example.com",
                "encoded-password",
                true,
                Set.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(userRepository.findByUsername("jsmith")).thenReturn(Optional.of(existingUser));

        assertThatThrownBy(() -> userApplicationService.createUser(
                new CreateUserCommand("jsmith", "jsmith@example.com", "ChangeMe123", Set.of("EMPLOYEE"))
        )).isInstanceOf(DuplicateUserException.class)
                .hasMessage("User already exists with username jsmith");
    }

    @Test
    void shouldRejectDuplicateEmail() {
        User existingUser = new User(
                5L,
                "existing-user",
                "jsmith@example.com",
                "encoded-password",
                true,
                Set.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(userRepository.findByUsername("jsmith")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("jsmith@example.com")).thenReturn(Optional.of(existingUser));

        assertThatThrownBy(() -> userApplicationService.createUser(
                new CreateUserCommand("jsmith", "jsmith@example.com", "ChangeMe123", Set.of("EMPLOYEE"))
        )).isInstanceOf(DuplicateUserException.class)
                .hasMessage("User already exists with email jsmith@example.com");
    }

    @Test
    void shouldListUsers() {
        Page<User> usersPage = new PageImpl<>(Set.of(
                new User(
                        2L,
                        "jsmith",
                        "jsmith@example.com",
                        "encoded-password",
                        true,
                        Set.of(new Role(3L, "EMPLOYEE", "Employee role", LocalDateTime.now())),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                )
        ).stream().toList());

        when(userRepository.findAll(PageRequest.of(0, 10))).thenReturn(usersPage);

        Page<User> result = userApplicationService.listUsers(PageRequest.of(0, 10));

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().getFirst().username()).isEqualTo("jsmith");
    }

    @Test
    void shouldAssignRoles() {
        User currentUser = new User(
                2L,
                "jsmith",
                "jsmith@example.com",
                "encoded-password",
                true,
                Set.of(new Role(3L, "EMPLOYEE", "Employee role", LocalDateTime.now())),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        Set<Role> updatedRoles = new LinkedHashSet<>(Set.of(
                new Role(1L, "ADMIN", "System administrator", LocalDateTime.now()),
                new Role(3L, "EMPLOYEE", "Employee role", LocalDateTime.now())
        ));

        when(userRepository.findById(2L)).thenReturn(Optional.of(currentUser));
        when(roleRepository.findByNames(Set.of("ADMIN", "EMPLOYEE"))).thenReturn(updatedRoles);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userApplicationService.assignRoles(
                2L,
                new UpdateUserRolesCommand(Set.of("ADMIN", "EMPLOYEE"))
        );

        assertThat(result.roles()).extracting(Role::name)
                .containsExactlyInAnyOrder("ADMIN", "EMPLOYEE");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldRejectEmptyRolesAssignment() {
        User currentUser = new User(
                2L,
                "jsmith",
                "jsmith@example.com",
                "encoded-password",
                true,
                Set.of(new Role(3L, "EMPLOYEE", "Employee role", LocalDateTime.now())),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(userRepository.findById(2L)).thenReturn(Optional.of(currentUser));

        assertThatThrownBy(() -> userApplicationService.assignRoles(2L, new UpdateUserRolesCommand(Set.of())))
                .isInstanceOf(EmptyRolesException.class)
                .hasMessage("User must have at least one role");
    }
}
