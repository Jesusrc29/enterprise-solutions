package com.jesusromero.enterprise.employee.users.application.service;

import com.jesusromero.enterprise.employee.roles.domain.exception.RoleNotFoundException;
import com.jesusromero.enterprise.employee.roles.domain.model.Role;
import com.jesusromero.enterprise.employee.roles.domain.repository.RoleRepository;
import com.jesusromero.enterprise.employee.users.application.command.CreateUserCommand;
import com.jesusromero.enterprise.employee.users.application.command.UpdateUserRolesCommand;
import com.jesusromero.enterprise.employee.users.domain.exception.DuplicateUserException;
import com.jesusromero.enterprise.employee.users.domain.exception.EmptyRolesException;
import com.jesusromero.enterprise.employee.users.domain.exception.UserNotFoundException;
import com.jesusromero.enterprise.employee.users.domain.model.User;
import com.jesusromero.enterprise.employee.users.domain.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class UserApplicationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserApplicationService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(CreateUserCommand command) {
        ensureUsernameAvailable(command.username(), null);
        ensureEmailAvailable(command.email(), null);

        Set<Role> roles = resolveRoles(command.roles());

        User user = new User(
                null,
                command.username(),
                command.email(),
                passwordEncoder.encode(command.password()),
                true,
                roles,
                null,
                null
        );

        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public Page<User> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User disableUser(Long id) {
        User currentUser = getUserById(id);
        return userRepository.save(copyWithEnabled(currentUser, false));
    }

    public User enableUser(Long id) {
        User currentUser = getUserById(id);
        return userRepository.save(copyWithEnabled(currentUser, true));
    }

    public User assignRoles(Long id, UpdateUserRolesCommand command) {
        User currentUser = getUserById(id);
        Set<Role> roles = resolveRoles(command.roles());

        User updatedUser = new User(
                currentUser.id(),
                currentUser.username(),
                currentUser.email(),
                currentUser.passwordHash(),
                currentUser.enabled(),
                roles,
                currentUser.createdAt(),
                currentUser.updatedAt()
        );

        return userRepository.save(updatedUser);
    }

    private User copyWithEnabled(User user, boolean enabled) {
        return new User(
                user.id(),
                user.username(),
                user.email(),
                user.passwordHash(),
                enabled,
                user.roles(),
                user.createdAt(),
                user.updatedAt()
        );
    }

    private void ensureUsernameAvailable(String username, Long currentUserId) {
        userRepository.findByUsername(username)
                .filter(user -> !user.id().equals(currentUserId))
                .ifPresent(user -> {
                    throw new DuplicateUserException("username", username);
                });
    }

    private void ensureEmailAvailable(String email, Long currentUserId) {
        userRepository.findByEmail(email)
                .filter(user -> !user.id().equals(currentUserId))
                .ifPresent(user -> {
                    throw new DuplicateUserException("email", email);
                });
    }

    private Set<Role> resolveRoles(Set<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
            throw new EmptyRolesException();
        }

        Set<String> normalizedRoleNames = roleNames.stream()
                .map(String::trim)
                .filter(role -> !role.isEmpty())
                .collect(LinkedHashSet::new, LinkedHashSet::add, LinkedHashSet::addAll);

        if (normalizedRoleNames.isEmpty()) {
            throw new EmptyRolesException();
        }

        Set<Role> resolvedRoles = roleRepository.findByNames(normalizedRoleNames);

        if (resolvedRoles.size() != normalizedRoleNames.size()) {
            normalizedRoleNames.stream()
                    .filter(roleName -> resolvedRoles.stream().noneMatch(role -> role.name().equals(roleName)))
                    .findFirst()
                    .ifPresent(roleName -> {
                        throw new RoleNotFoundException(roleName);
                    });
        }

        return resolvedRoles;
    }
}
