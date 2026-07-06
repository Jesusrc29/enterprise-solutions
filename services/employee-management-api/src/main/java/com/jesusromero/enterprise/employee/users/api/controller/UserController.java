package com.jesusromero.enterprise.employee.users.api.controller;

import com.jesusromero.enterprise.employee.users.api.dto.CreateUserRequest;
import com.jesusromero.enterprise.employee.users.api.dto.UpdateUserRolesRequest;
import com.jesusromero.enterprise.employee.users.api.dto.UserResponse;
import com.jesusromero.enterprise.employee.users.api.mapper.UserApiMapper;
import com.jesusromero.enterprise.employee.users.application.service.UserApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserApplicationService userApplicationService;
    private final UserApiMapper userApiMapper;

    public UserController(
            UserApplicationService userApplicationService,
            UserApiMapper userApiMapper
    ) {
        this.userApplicationService = userApplicationService;
        this.userApiMapper = userApiMapper;
    }

    @GetMapping
    @Operation(summary = "List users with pagination")
    public ResponseEntity<Page<UserResponse>> listUsers(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<UserResponse> response = userApplicationService.listUsers(pageable)
                .map(userApiMapper::toResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userApiMapper.toResponse(userApplicationService.getUserById(id)));
    }

    @PostMapping
    @Operation(
            summary = "Create a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created"),
                    @ApiResponse(responseCode = "400", description = "Validation error"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "409", description = "Duplicate user data")
            }
    )
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse response = userApiMapper.toResponse(
                userApplicationService.createUser(userApiMapper.toCreateCommand(request))
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}/disable")
    @Operation(summary = "Disable user")
    public ResponseEntity<UserResponse> disableUser(@PathVariable Long id) {
        return ResponseEntity.ok(userApiMapper.toResponse(userApplicationService.disableUser(id)));
    }

    @PatchMapping("/{id}/enable")
    @Operation(summary = "Enable user")
    public ResponseEntity<UserResponse> enableUser(@PathVariable Long id) {
        return ResponseEntity.ok(userApiMapper.toResponse(userApplicationService.enableUser(id)));
    }

    @PutMapping("/{id}/roles")
    @Operation(summary = "Replace user roles")
    public ResponseEntity<UserResponse> assignRoles(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRolesRequest request
    ) {
        UserResponse response = userApiMapper.toResponse(
                userApplicationService.assignRoles(id, userApiMapper.toUpdateRolesCommand(request))
        );

        return ResponseEntity.ok(response);
    }
}
